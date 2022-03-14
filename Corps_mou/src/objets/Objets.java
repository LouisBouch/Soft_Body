package objets;

import java.awt.Graphics2D;
import java.io.Serializable;

import application.Dessinable;
import physique.Vecteur2D;

public abstract class Objets implements Serializable, Dessinable {
	private static final long serialVersionUID = -8107454745591429490L;
	
	protected Vecteur2D pos = new Vecteur2D();
	protected Vecteur2D vit = new Vecteur2D();

	/**
	 * Crée un objet
	 * @param v Position de l'objet
	 */
	public Objets(Vecteur2D v) {
		pos.setXY(v);
	}
	/**
	 * Oblige les classes dérivées à posséder une classe dessiner
	 */
	public abstract void dessiner(Graphics2D g2d);
	/**
	 * Permet d'obtenir la position de l'objet
	 * @return La position
	 */
	public Vecteur2D getPos() {
		return pos;
	}
	/**
	 * Permet d'obtenir la position de l'objet
	 * @param pos Position à changer
	 */
	public void setPos(Vecteur2D pos) {
		this.pos = pos;
	}
	/**
	 * Permet d'initialiser la vitesse de l'objet
	 * @return
	 */
	public Vecteur2D getVit() {
		return vit;
	}
	/**
	 * Permet d'initialiser la position de l'objet
	 * @param vit Vitesse à changer
	 */
	public void setVit(Vecteur2D vit) {
		this.vit = vit;
	}
	/**
	 * Oblige les classes dérivées à posséder une classe toString
	 */
	public abstract String toString();
	
	

}
