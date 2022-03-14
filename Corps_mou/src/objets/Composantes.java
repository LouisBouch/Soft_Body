package objets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import physique.Vecteur2D;

public class Composantes extends Objets{
	private static final long serialVersionUID = 6269854407718889323L;
	
	private static int diam = 25;//diam en pixels
	private double mass = 1;//mass en kg
	private boolean enCollision = false;//Permet de savoir si le compo est en contacte avec quelque chose
	private ArrayList<Links> links = new ArrayList<Links>();
	private String uniqueId;
	private Vecteur2D force = new Vecteur2D();
	

	Ellipse2D.Double comps = new Ellipse2D.Double();
	/**
	 * Crée une composante
	 * @param comp La composante
	 */
	public Composantes(Composantes comp) {
		super(comp.getPos());
		mass = comp.getMass();
		uniqueId = comp.uniqueId;
	}
	/**
	 * Crée une composante
	 * @param v Position de la composante
	 * @param mass La masse de la composante
	 */
	public Composantes(Vecteur2D v, double mass) {
		super(v);
		this.mass = mass;
	}
	/**
	 * Crée une composante
	 * @param v Position de la composante
	 */
	public Composantes(Vecteur2D v) {
		super(v);
	}
	/**
	 * Constructeur avec vitesse
	 * @param pos Position
	 * @param vit Vitesse
	 */
	public Composantes(Vecteur2D pos, Vecteur2D vit) {
		super(pos);
		this.vit = vit;
	}
	/**
	 * Crée une composante
	 * @param x Position de la composante en X
	 * @param y Position de la composante en Y
	 * @param id Id unique pour chaque composante
	 */
	public Composantes(double x, double y, String id) {
		super(new Vecteur2D(x, y));
		uniqueId = id;
	}
	/**
	 * Permet de dessiner les composantes
	 */
	@Override
	public void dessiner(Graphics2D g2d) {
		comps.setFrame(Math.round(pos.getX()), Math.round(pos.getY()), diam, diam);
		if (uniqueId != null) g2d.setColor(Color.blue);
		else g2d.setColor(Color.yellow);
		g2d.fill(comps);
//		g2d.setColor(Color.yellow);
//		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
//		g2d.drawString(Integer.toString(((int)(vit.getY()/50 * 100))), (int) pos.getX() , (int) pos.getY());
//		if (uniqueId != null) g2d.drawString(uniqueId, (int) Math.round(pos.getX()) , (int) Math.round(pos.getY()));	
	}
	
	/**
	 * Permet de décrire l'objet
	 */
	public String toString() {
		return "posX: " + pos.getX() + ", posY: " + pos.getY() + " ID: " + uniqueId;
	}
	/**
	 * Permet d'obtenir le diamètre de la composante
	 * @return Le diamètre (en pixels)
	 */
	public static int getDiam() {
		return diam;
	}
	/**
	 * Permet d'btenir la mass de la composante
	 * @return La masse
	 */
	public double getMass() {
		return mass;
	}
	/**
	 * Permet d'obtenir le ID unique de la composante
	 * @return Le ID
	 */
	public String getUniqueId() {
		return uniqueId;
	}
	/**
	 * Permet de savoir s'il y a eu une collision
	 * @return Si la composante est en collision
	 */
	public boolean isEnCollision() {
		return enCollision;
	}
	/**
	 * Permet d'établir s'il y a eu une collision
	 * @param enCollision Établit l'état
	 */
	public void setEnCollision(boolean enCollision) {
		this.enCollision = enCollision;
	}
	/**
	 * Ajoute une liaison à la composante
	 * @param link Le lien
	 */
	public void addLink(Links link) {
		links.add(link);
	}
	/**
	 * Obtient le nombre de liens de la composante
	 * @return Les liens
	 */
	public ArrayList<Links> getLinks() {
		return links;
	}
	/**
	 * Établie la forc appliqué sur la composante
	 * @param force Force apliqué sur la composante
	 */
	public void setForce(Vecteur2D force) {
		this.force = force;
	}
	/**
	 * Permet d'avoir les forces appliqueés sur la composante
	 * @return Les forces
	 */
	public Vecteur2D getForce() {
		return force;
	}
	/**
	 * Obtient le point central de la composante
	 * @return Le point central
	 */
	public Vecteur2D getPosCentre() {
		return new Vecteur2D(pos.getX() + diam / 2.0, pos.getY() + diam / 2.0);
	}
}
