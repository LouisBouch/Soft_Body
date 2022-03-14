package objets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import physique.Vecteur2D;

public class ObstacleTige extends Objets {
	private static final long serialVersionUID = 1L;
	
	private double longueurTige;
	private double orientationTigeDeg = 0;
	private double epaisseurTige = 3;//Épaisseur en pixels
	private Vecteur2D posFin;
	private Vecteur2D vecTige;
	
	/**
	 * Crée une tige obstacle
	 * @param v Sa position
	 * @param longueurTige Sa longueur
	 * @param orientationTigeDeg Son orientation
	 * @param epaisseurTige Son épaisseur
	 */
	public ObstacleTige(Vecteur2D v, double longueurTige, double orientationTigeDeg, double epaisseurTige) {
		super(v);
		this.longueurTige = longueurTige;
		this.orientationTigeDeg = orientationTigeDeg;
		this.epaisseurTige = epaisseurTige;
		posFin = new Vecteur2D(pos.getX() + longueurTige*Math.cos(Math.toRadians(orientationTigeDeg)),pos.getY() - longueurTige*Math.sin(Math.toRadians(orientationTigeDeg)));
		vecTige = posFin.soustrait(pos);
	}
	/**
	 * Crée une tige obstacle
	 * @param v Position initiale
	 * @param v2 Position finale
	 */
	public ObstacleTige(Vecteur2D v, Vecteur2D v2) {
		super(v);
		posFin = v2;
		vecTige = posFin.soustrait(pos);
		longueurTige = vecTige.getLongueur();
		orientationTigeDeg = Math.toDegrees(Math.atan(-vecTige.getY() / vecTige.getX()));
		if (vecTige.getX() < 0) orientationTigeDeg += 180;
	}
	/**
	 * Crée une tige obstacle
	 * @param v Sa position
	 * @param longueurTige Sa longueur
	 * @param orientationTigeDeg Son orientation
	 */
	public ObstacleTige(Vecteur2D v, double longueurTige, double orientationTigeDeg) {
		super(v);
		this.longueurTige = longueurTige;
		this.orientationTigeDeg = orientationTigeDeg;		
	}
	/**
	 * Crée une tige obstacle
	 * @param v Sa position 
	 * @param longueurTigeSa longueur
	 */
	public ObstacleTige(Vecteur2D v, double longueurTige) {
		super(v);
		this.longueurTige = longueurTige;
	}
	/**
	 * Permet de dessiner la tige
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke((int) epaisseurTige));
		g2d.drawLine((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), (int) Math.round(posFin.getX()), (int) Math.round(posFin.getY()));
		g2d.setStroke(new BasicStroke(1));
	}
	/**
	 * Décrit l'objet
	 */
	public String toString() {
		return "Tige\n" + this.getPos().toString() + "\n" + longueurTige + "\n" + orientationTigeDeg + "\n" + epaisseurTige;
	}
	/**
	 * Obtient la position finale de la tige
	 * @return La position finale
	 */
	public Vecteur2D getPosFin() {
		return posFin;
	}
	/**
	 * Obtient le vecteur de la tige
	 * @return Le vecteur de la tige
	 */
	public Vecteur2D getVecTige() {
		return vecTige;
	}
	/**
	 * Obtient la longueur de la tige
	 * @return La longueur de la tige
	 */
	public double getLongueurTige() {
		return longueurTige;
	}
	
	

}
