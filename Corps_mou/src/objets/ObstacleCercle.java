package objets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import physique.Vecteur2D;

public class ObstacleCercle extends Objets{
private static final long serialVersionUID = 1L;
	
	private Ellipse2D.Double cercle = new Ellipse2D.Double();
	private Vecteur2D vecCercle;
	private Vecteur2D posCentre;
	private double diametre;
	private double orientationCercle;
	
	/**
	 * Crée un cercle obstacle
	 * @param v Position initiale
	 * @param v2 Position finale
	 */
	public ObstacleCercle(Vecteur2D v, Vecteur2D v2) {//À faire: faire tourner le cercle autour d'un point et utiliser posFin et Pos pour les vrais positions
		super(v);
		diametre = v2.soustrait(pos).getLongueur();
		vecCercle = v2.soustrait(pos);
		posCentre = pos.additionne(vecCercle.divisionScalaire(2));
		orientationCercle = Math.toDegrees(Math.atan(vecCercle.getY() / vecCercle.getX())) - 90;
		if (vecCercle.getX() < 0) orientationCercle += 180;
	}
	/**
	 * Crée un cercle obstacle
	 * @param v Position initiale
	 * @param v2 Position finale
	 */
	public ObstacleCercle(Vecteur2D v, Vecteur2D posCentre, double diametre, double orientationCercle) {//À faire: faire tourner le cercle autour d'un point et utiliser posFin et Pos pour les vrais positions
		super(v);
		this.diametre = diametre;
		this.posCentre = posCentre;
		this.orientationCercle = orientationCercle;
	}
	/**
	 * Permet de dessiner la tige
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		AffineTransform mat = new AffineTransform();
        mat.rotate(Math.toRadians(orientationCercle), pos.getX(), pos.getY());
        cercle.setFrame(pos.getX() - diametre/2.0, pos.getY(), diametre, diametre);    
		g2d.setColor(Color.yellow);
		g2d.fill(mat.createTransformedShape(cercle));
		mat.setTransform(g2d.getTransform());
	}
	/**
	 * Décrit l'objet
	 */
	public String toString() {
		return "Cercle\n" + this.getPos().toString() + "\n" + posCentre + "\n" + diametre + "\n" + orientationCercle;
	}
	/**
	 * Obtient l'orientation du cercle
	 * @return Orientation du cercle
	 */
	public double getOrientationCercle() {
		return orientationCercle;
	}
	/**
	 * Obtient la position centrale du cercle
	 * @return
	 */
	public Vecteur2D getPosCentre() {
		return posCentre;
	}
	/**
	 * Obtient le diamètre du cercle
	 * @return Le diamètre (en pixels)
	 */
	public double getDiametre() {
		return diametre;
	}
	
	
}
