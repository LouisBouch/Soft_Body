package physique;

import java.util.ArrayList;

import objets.Composantes;
import objets.Links;
import objets.Niveau;
import objets.Objets;
import objets.ObstacleCercle;
import objets.ObstacleTige;

public class IterationPhys {
	int detlaT;

	private static Vecteur2D posM;
	private static Vecteur2D vitM;
	private static Vecteur2D accM;

	private static Composantes[][] composantes;
	private static Composantes[][] composantesFin;
	private static ArrayList<Objets> objets;

	private static final double PIXELSPARM = 50;
	
	private static boolean gravite = true;
//	private static boolean first = true;
	
	
	/**
	 * Calcul une itération physique
	 * @param deltaT Le pas de la simulation
	 * @param niveau Le niveau ou l'itération est calculé
	 */
	public static void Iteration(double deltaT, Niveau niveau) {
		composantes = niveau.getCompo();
		composantesFin = new Composantes[composantes.length][composantes[0].length];

		 int c = 0;//colonnes
		 int l = 0;//lignes
		
		objets = niveau.getObjets();
		for (Composantes[] lignes: composantes) {
			for (Composantes compo: lignes) {
				ArrayList<Collisions> collisions = new ArrayList<Collisions>();
				Vecteur2D normale;
				double e = 0.3;//Constante élasticité 0-1 (1 -> 100% élastique)
				double coefFrictionSurface = 0.2;
				
				
				if (gravite) accM = new Vecteur2D(0, 9.8);
				else accM = new Vecteur2D();
				if (Links.getK() > 0) {
					for (Links link : compo.getLinks()) {		
						accM.setXY(accM.additionne(forceRepositionnement(compo, link).divisionScalaire(compo.getMass())));
					}
				}
				vitM = new Vecteur2D(compo.getVit().divisionScalaire(PIXELSPARM));
				posM = new Vecteur2D(compo.getPos().divisionScalaire(PIXELSPARM));
				
//				if (first) {
//					vitM.setXY(10, -1);
//					first = false;
//				}

				collisions = GestionCollisions.detecteurCollisions(compo, composantes, objets);
				if (collisions.size() != 0) {
					for (Collisions coll: collisions) {
						posM.setXY(posM.additionne(coll.pushBack()));
						composantes[l][c].setPos(composantes[l][c].getPos().additionne(coll.pushBack()));
						
						
						normale = new Vecteur2D(coll.getNormale().multiplicationScalaire(-1));
						double compoImpul;
						if (coll.getObjetCollisionne().getClass() == Composantes.class) {
							compoImpul = vitM.soustrait(coll.getVitesseObjetCollisionne()).multiplicationScalaire(-(1 + e)/((1/((Composantes)coll.getObjetCollisionne()).getMass() + 1/compo.getMass()))).multiplicationVectorielle(normale);//https://physique.cmaisonneuve.qc.ca/svezina/nya/note_nya/NYA_XXI_Chap%203.11c.pdf
						}
						else {
							compoImpul = vitM.soustrait(coll.getVitesseObjetCollisionne()).multiplicationScalaire(-(1 + e)/((1/compo.getMass()))).multiplicationVectorielle(normale);
						}
						vitM.setXY(vitM.additionne(normale.multiplicationScalaire(compoImpul)));
						
						

						if (coll.getObjetCollisionne().getClass().equals(ObstacleCercle.class) || coll.getObjetCollisionne().getClass().equals(ObstacleTige.class)) {
							Vecteur2D surface;
							if (coll.getObjetCollisionne().getClass().equals(ObstacleCercle.class)) surface = new Vecteur2D(((ObstacleCercle) coll.getObjetCollisionne()).getPosCentre().soustrait(compo.getPosCentre()).perpendiculaire());
							else surface = new Vecteur2D(((ObstacleTige) coll.getObjetCollisionne()).getPosFin().soustrait(coll.getObjetCollisionne().getPos()));
							
							double forceFriction = calculForceNormale(compo, surface) * coefFrictionSurface;
							accM.setXY(accM.additionne(vitM.normalise().multiplicationScalaire(-forceFriction)));
						}
					}
					compo.setEnCollision(true);
				}
				else compo.setEnCollision(false);

				vitM.setXY(vitM.additionne(accM.multiplicationScalaire(deltaT)));
				posM.setXY(posM.additionne(vitM.multiplicationScalaire(deltaT)));
				compo.setForce(accM.multiplicationScalaire(compo.getMass()));
				

				composantesFin[l][c] = new Composantes(posM.multiplicationScalaire(PIXELSPARM), vitM.multiplicationScalaire(PIXELSPARM));
				c++;
			}
			c = 0;
			l++;
		}
		//Effectue tous les déplacements des composants
		for (int i = 0; i < composantes.length; i++) {
			for (int o = 0; o < composantes[0].length; o++) {
				composantes[i][o].setPos(composantesFin[i][o].getPos());
				composantes[i][o].setVit(composantesFin[i][o].getVit());
			}
		}
		for (Links link: niveau.getLinks()) {
			link.rafraichirLinks();
		}
	}
	/**
	 * Calcul la force exercé par le ressort sur la composante
	 * @param deltaT Le temps écoulé
	 * @param compo La composante
	 * @param link Le lien de la composante
	 * @return La force appliqué sur la composante
	 */
	public static Vecteur2D forceRepositionnement(Composantes compo, Links link) {
		Vecteur2D force = new Vecteur2D();
		Vecteur2D normale = new Vecteur2D();
		Vecteur2D vitRelative = new Vecteur2D();
		if (link.getCompIni().getUniqueId() != compo.getUniqueId()) {
			normale.setXY(link.getCompIni().getPos().soustrait(compo.getPos()).normalise());
			vitRelative.setXY(compo.getVit().soustrait(link.getCompIni().getVit()).garderComposantesEn(normale));
		}
		else {
			normale.setXY(link.getCompFin().getPos().soustrait(compo.getPos()).normalise());
			vitRelative.setXY(compo.getVit().soustrait(link.getCompFin().getVit()).garderComposantesEn(normale));
		}
		force.setXY(normale.multiplicationScalaire(Links.getK() * (link.getLongueur() / PIXELSPARM - link.getRepos())).soustrait(vitRelative.multiplicationScalaire(0.1)));
		if (Math.abs(force.getX()) < Vecteur2D.getEpsilon()) force.setX(0);
		if (Math.abs(force.getY()) < Vecteur2D.getEpsilon()) force.setY(0);
		return force;
	}
	/**
	 * Effectue le calcul de force normale pour le calcul de friction
	 */
	public static double calculForceNormale(Composantes compo, Vecteur2D surface) {
		double i =  Math.sqrt(surface.getX()*surface.getX() * compo.getMass() * accM.getY()*accM.getY() / Math.pow(surface.getX()*surface.getX() + surface.getY()*surface.getY(), 2));
		if (Math.abs(i) < Vecteur2D.getEpsilon()) i = 0;
		return surface.multiplicationScalaire(i).getLongueur();
	}
	/**
	 * Permet de réinitialiser les valeurs du niveau
	 * @param pos Position initiale des composantes
	 */
	public static void reset(Vecteur2D pos) {
		posM.setXY(pos);
		vitM.setXY(new Vecteur2D());
	}
	/**
	 * Active/désactive la gravité
	 * @param gravite Avec ou sans
	 */
	public static void setGravite(boolean gravite) {
		IterationPhys.gravite = gravite;
	}
	/**
	 * Valeur de conversion
	 * @return Retourne la valeur de conversion
	 */
	public static double getPixelsparm() {
		return PIXELSPARM;
	}	
}
