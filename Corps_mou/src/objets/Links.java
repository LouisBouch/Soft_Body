package objets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import application.Dessinable;
import physique.IterationPhys;
import physique.Vecteur2D;

public class Links implements Dessinable{
	
	private Vecteur2D posIni;
	private Vecteur2D posFin;
	
	private Composantes compIni;
	private Composantes compFin;
	
	private double longueur;
	private static double k = 200;//Force de retour des liens
	private double repos;//Valeur au repos du ressort en mêtre
	
	/**
	 * Crée un lien/ressort pour relier les composantes
	 * @param compIni Le composantt initiale au lien lien est attaché
	 * @param compFin Le composant auquel l'autre extrémité du lien est attaché
	 */
	public Links(Composantes compIni, Composantes compFin) {
		this.posIni = new Vecteur2D(compIni.getPos());
		this.posFin = new Vecteur2D(compFin.getPos());
		
		this.compIni = compIni;
		this.compFin = compFin;
		
		compIni.addLink(this);
		compFin.addLink(this);
		
		longueur = Vecteur2D.getLongueur(compFin.getPos().soustrait(compIni.pos));
		repos = longueur / IterationPhys.getPixelsparm();
	}

	@Override
	public void dessiner(Graphics2D g2d) {
		double ajustement = Composantes.getDiam() / 2;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke((int) 3));
		g2d.drawLine((int) Math.round(posIni.getX() + ajustement), (int) Math.round(posIni.getY() + ajustement), (int) Math.round(posFin.getX() + ajustement), (int) Math.round(posFin.getY() + ajustement));
		
	}
	public void rafraichirLinks() {
		this.posIni = new Vecteur2D(compIni.getPos());
		this.posFin = new Vecteur2D(compFin.getPos());
		longueur = Vecteur2D.getLongueur(posFin.soustrait(posIni));
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Permet d'obtenir la force de rappel du lien
	 * @return La force de rappel
	 */
	public static double getK() {
		return k;
	}
	/**
	 * Permet d'initialiser la force de rappel du lien
	 * @param k La force de rappel
	 */
	public static void setK(double k) {
		Links.k = k;
	}
	/**
	 * Permet d'obtenir la composante initiale du lien
	 * @return La composante
	 */
	public Composantes getCompIni() {
		return compIni;
	}
	/**
	 * Permet d'obtenir la composante finale du lien
	 * @return La composante
	 */
	public Composantes getCompFin() {
		return compFin;
	}
	/**
	 * Permet d'obtenir la longueur du lien
	 * @return Le longueur du lien
	 */
	public double getLongueur() {
		return longueur;
	}
	/**
	 * Permet d'obtenir la valeur au repos du lien
	 * @return La valeur de repos
	 */
	public double getRepos() {
		return repos;
	}
	/**
	 * Initialise la valeur au repos du lien
	 * @param repos La valeur à utiliser
	 */
	public void setRepos(double repos) {
		this.repos = repos;
	}
	
	

}
