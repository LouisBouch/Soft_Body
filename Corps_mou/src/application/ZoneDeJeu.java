package application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fichiers.Gestionnaire;
import objets.Composantes;
import objets.Links;
import objets.Niveau;
import objets.Objets;
import objets.ObstacleCercle;
import objets.ObstacleTige;
import physique.IterationPhys;
import physique.Vecteur2D;

public class ZoneDeJeu extends JPanel implements Runnable{
	private static final long serialVersionUID = -5291272142771295791L;
	private static boolean running = false;
	private static boolean ready = true;
	private static boolean test = false;
	private static boolean editing = false;

	private double tempsEcoule;
	private int lignesColonnes = 0;
	private int nbCases = 2;
	private int lastPressed;
	
	Composantes[][] compo = new Composantes[lignesColonnes][lignesColonnes];
//	Objets[] objets;
	ArrayList<Objets> objets = new ArrayList<Objets>();
	Niveau niveau;
	Gestionnaire gestion;

	private Vecteur2D posIni;
	private Vecteur2D posIniObs;
	private Vecteur2D posFinObs;
	private double deltaT = 0.006;
	private int calParFrame = 10;//Plus la valeur est grande, plus il y a des calculs physique avnt que l'image soit repainte
	private double dT = (int) ((deltaT / (double) calParFrame) * 1E6) / 1E6;
	private long sleep = 6;

	Thread simulation;

	/**
	 * Create the panel.
	 */
	public ZoneDeJeu() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (test && e.getButton() == MouseEvent.BUTTON1) {
					compo[0][0].setPos(new Vecteur2D(e.getX(), e.getY()));
					compo[0][0].setVit(new Vecteur2D());
					repaint();
				}
				if (editing && e.getButton() == MouseEvent.BUTTON1) {
					if (posIniObs == null ) {
						posIniObs = new Vecteur2D(e.getX(), e.getY());
					}
					else {
						if (lastPressed == 1) {
							objets.add(new ObstacleTige(posIniObs, posFinObs));
						}
						if (lastPressed == 2) {
							objets.add(new ObstacleCercle(posIniObs, posFinObs));
						}
						posIniObs = null;
						posIniObs = null;

					}
					repaint();
				}
				if (e.getButton() == MouseEvent.BUTTON3 && editing) {
					posIni.setXY(new Vecteur2D(e.getX(), e.getY()));
					setComposants(lignesColonnes);
					repaint();
				}
				if (e.getButton() == MouseEvent.BUTTON3 && !editing) {
					objets.add(new Composantes(new Vecteur2D(e.getX(), e.getY()), Double.POSITIVE_INFINITY));
					repaint();
				}
			}
		});
		posIni = new Vecteur2D(0, 0);
		setBackground(Color.gray);		
	}//Fin zonedejeu
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString(Double.toString((int)(tempsEcoule * 1000)/1000.0), 1800, 925);
		if (niveau != null) {
			for (Links link: niveau.getLinks()) {
				link.dessiner(g2d);
			}
		}
		if (editing) {
			g2d.setStroke(new BasicStroke((int) 3));
			int posX = this.getWidth() / 2 - 50 * nbCases / 2;
			for (int i = 0; i < nbCases; i++) {
				ImageIcon image = new ImageIcon("C:\\Users\\dadel\\OneDrive\\Desktop\\Workspace\\Corps_mou\\images\\" + i + ".png");
				image.paintIcon(getFocusCycleRootAncestor(), g2d, posX + i*50, 0);
				Rectangle2D uneCase = new Rectangle2D.Double(posX + i*50, 0, 50, 50);
				g2d.drawString(i + 1 + ".", posX + i*50 + 2, 50 -2);
				g2d.draw(uneCase);
			}
			g2d.setColor(new Color(0, 0 ,0, 75));
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 350));
			g2d.drawString("EDITING", 240, 550);
			g2d.setFont(new Font("Dialog", Font.PLAIN, 12));
			g2d.setColor(Color.black);

			if (posIniObs != null && posFinObs != null) {
				if (lastPressed == 1) {
					ObstacleTige tige = new ObstacleTige(posIniObs, posFinObs);
					tige.dessiner(g2d);
				}
				if (lastPressed == 2) {
					ObstacleCercle cercle = new ObstacleCercle(posIniObs, posFinObs);
					cercle.dessiner(g2d);
				}
			}
		}
		if (objets != null) {
			for (Objets objet: objets) {
				objet.dessiner(g2d);
			}
		}
		for (Composantes[] lignes: compo) {
			for (Composantes colonnes: lignes) {
				colonnes.dessiner(g2d); 
			}
		}
	}
	/**
	 * Repaint pour garder le tout à jour
	 */
	public void run() {
		while(running) {
			if (!editing) {
				for (int i = 0; i < calParFrame; i++) {
					IterationPhys.Iteration(dT, niveau);
					tempsEcoule += deltaT;
				}
			}
			else {
				posFinObs = new Vecteur2D(MouseInfo.getPointerInfo().getLocation()).soustrait(new Vecteur2D(this.getLocationOnScreen()));
			}
			repaint();
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}

	}
	/**
	 * Fait un pas de l'itération physique
	 */
	public void unPas() {
		for (int i = 0; i < calParFrame; i++) {
			IterationPhys.Iteration(dT, niveau);
			tempsEcoule += deltaT;
		}
		repaint();
	}
	/**
	 * Fait un petitpas de l'itération physique
	 */
	public void unPetitPas() {
		IterationPhys.Iteration(dT, niveau);
		tempsEcoule += dT;
		repaint();
	}

	/**
	 * Démarre l'application
	 */
	public void demarrer() {
		if (!running) {
			running = true;
			simulation = new Thread(this);
			simulation.start();		
		}

	}
	/**
	 *Arrête l'application
	 */
	public void stop() {
		if (running) {
			running = false;
		}

	}
	/**
	 *Continu le jeu
	 */
	public void continuer() {
		demarrer();
	}
	/**
	 * Réinitialize le jeu
	 */
	public void reset() {
		posIniObs = null;
		tempsEcoule = 0;
		setComposants(lignesColonnes);
		repaint();
	}
	/**
	 * Crée un gestionnnaire de fichier pour ouvoir sauvegarder le niveau
	 */
	public void creerGestionnaire() {
		gestion = new Gestionnaire();
//		gestion.creationFichier();
	}
	/**
	 * Sauvegarde le niveau
	 */
	public void sauvgarder() {
		gestion.sauvegarder(niveau);
	}
	/**
	 * Réinitialise le niveau
	 */
	public void reinitialiser() {
		gestion.reinitialiser(niveau);
		charger();
	}
	/**
	 * Remet le niveau à 0
	 */
	public void restart() {
		ready = true;
		posIni.setXY(0, 0);
		objets.clear();
		setComposants(lignesColonnes);
		stop();
		repaint();
	}
	/**
	 * Charge le niveau en lisant le fgichier "objets" sur le bureau
	 */
	public void charger() {
		if (!editing) {
			String ligne = "";
			while (true) {
				ligne = gestion.lire();
				if (ligne != "FIN") {
					if (ligne.equals("COMPOSANTES")) {
						String coord= gestion.lire();
						posIni.setXY(Double.parseDouble(coord.substring(1, coord.indexOf(","))), Double.parseDouble(coord.substring(coord.indexOf(",") + 1, coord.length() - 1)));
						lignesColonnes = Integer.parseInt(gestion.lire());
						reset();
					}
					if (ligne.equals("OBJETS")) {
						objets.clear();
						for (int i = 0; i < gestion.repetitions("---"); i++){
							String objet = gestion.lire();
							if (objet.equals("Tige")) {
								String coord = gestion.lire();
								objets.add(new ObstacleTige(new Vecteur2D(Double.parseDouble(coord.substring(1, coord.indexOf(","))), Double.parseDouble(coord.substring(coord.indexOf(",") + 1, coord.length() - 1))),
										Double.parseDouble(gestion.lire()) , Double.parseDouble(gestion.lire()), Double.parseDouble(gestion.lire())));
								gestion.lire();
								
							}
							else if (objet.equals("Cercle")) {
								String coord1 = gestion.lire();
								String coord2 = gestion.lire();
								objets.add(new ObstacleCercle(new Vecteur2D(Double.parseDouble(coord1.substring(1, coord1.indexOf(","))), Double.parseDouble(coord1.substring(coord1.indexOf(",") + 1, coord1.length() - 1))),
										new Vecteur2D(Double.parseDouble(coord2.substring(1, coord2.indexOf(","))), Double.parseDouble(coord2.substring(coord2.indexOf(",") + 1, coord2.length() - 1))),
										Double.parseDouble(gestion.lire()) , Double.parseDouble(gestion.lire())));
								gestion.lire();
							}
						}
					}
				}
				else break;
			}
			System.out.println("Niveau chargé avec succès");
		}
	}//Fin charger
	/**
	 * Permet de changer le nombre de composants
	 * @param lignesColonnes Nombre de lignes/colonnes dans l'objet
	 */
	public void setComposants(int lignesColonnes) {
		compo = new Composantes[lignesColonnes][lignesColonnes];
		for (int i = 0; i < lignesColonnes; i++) {
			for (int o = 0; o < lignesColonnes; o++) {
				String id = Integer.toString(i) + (char) (o + 97);
				compo[i][o] = new Composantes(posIni.getX() + (i * 40), posIni.getY() + (o * 40), id);
			}
		}
		setNiveau();
		niveau.link(compo);
	}
	/**
	 * Crée le niveau
	 */
	public void setNiveau() {
		niveau = new Niveau(compo, objets);
	}
	/**
	 * Pose le nombre de pièce du cors mou
	 * @param nbComp Le nombres de composantes
	 */
	public void setNbComp(int nbComp) {
		this.lignesColonnes = nbComp;
	}
	/**
	 * Permet de savoir si la simulation roule
	 * @return L'état de la simulation
	 */
	public static boolean isRunning() {
		return running;
	}
	/**
	 * Variable pour le boutton start
	 * @return Si le jeu est prêt pour le boutton start
	 */
	public static boolean isReady() {
		return ready;
	}
	/**
	 * Initialize l'état du jeu
	 * @param ready Vrai ou Faux
	 */
	public static void setReady(boolean ready) {
		ZoneDeJeu.ready = ready;
	}
	/**
	 * Premt d'obntenir le niveau
	 * @return Le niveau
	 */
	public Niveau getNiveau() {
		return niveau;
	}
	/**
	 * Permet d'établir si la simulation est en mode test
	 * @param test Valeur de vérité du mode test
	 */
	public static void setTest(boolean test) {
		ZoneDeJeu.test = test;
	}
	/**
	 * Permet de savoir si le jeu est en mode test
	 * @return La valeur de vérité
	 */
	public static boolean isTest() {
		return test;
	}
	/**
	 * Permet de changer la vitesse du la composante seule en mode test
	 * @param vit La vitesse à ajouter
	 */
	public void setVit(Vecteur2D vit) {
		compo[0][0].setVit(compo[0][0].getVit().additionne(vit));;
	}
	/**
	 * Permet de savoir si le simulatiion est en mode édition
	 * @return La valeur de vérité du mode édition
	 */
	public static boolean isEditing() {
		return editing;
	}
	/**
	 * Permet d'initialiser/arrêter le mode édition
	 * @param editing
	 */
	public static void setEditing(boolean editing) {
		ZoneDeJeu.editing = editing;
	}
	/**
	 * Permet d'initialiser la position initiale de l'obstacle
	 * @param posIniObs La position
	 */
	public void setPosIniObs(Vecteur2D posIniObs) {
		this.posIniObs = posIniObs;
	}
	/**
	 * Permet d'initialiser la dernière valeur touché
	 * @param lastPressed Le chiffre correspondant
	 */
	public void setLastPressed(int lastPressed) {
		this.lastPressed = lastPressed;
	}
}
