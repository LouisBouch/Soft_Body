package physique;

import java.util.ArrayList;

import objets.Composantes;
import objets.Objets;
import objets.ObstacleCercle;
import objets.ObstacleTige;

public class GestionCollisions {
	private static ArrayList<Collisions> collision = new ArrayList<Collisions>();
	
	/**
	 * Itère les différentes possibilitées de collisions et les détectent
	 * @param compo La composante à vérifier
	 * @param composantes Les composantes avec lesquelles la composante initiale peut se heurter
	 * @param objets Les obstacles du jeu
	 * @return Un objet collision
	 */
	public static ArrayList<Collisions> detecteurCollisions(Composantes compo, Composantes[][] composantes, ArrayList<Objets> objets) {
		collision.clear();;
		for (Composantes[] lignes: composantes) {
			for (Composantes item: lignes) {
				collisionComp(compo, item);
			}
		}
		for (Objets item: objets) {
			if (item.getClass().equals(Composantes.class)) {
				collisionComp(compo, (Composantes) item);
			}
			if (item.getClass().equals(ObstacleTige.class)) {
				collisionTige(compo, (ObstacleTige) item);
			}
			if (item.getClass().equals(ObstacleCercle.class)) {
				collisionCercle(compo, (ObstacleCercle) item);
			}
		}
		return collision;
	}
	/**
	 * Effectue le calcul de distance entre deux composants (mètre)
	 * @param compo La première composante
	 * @param item La deuxième composante
	 * @return La distance entre les deux centres
	 */
	public static double distanceComps(Composantes compo, Composantes item) {
		return Math.sqrt(Math.pow(item.getPos().getX() - compo.getPos().getX(), 2) + Math.pow(item.getPos().getY() - compo.getPos().getY(), 2))/IterationPhys.getPixelsparm();	
	}
	/**
	 * Retourne la distance entre deux points (en mètres)
	 * @param v1 Le premier point
	 * @param v2 Le deuxième point
	 * @return La distance entre les deux points (en mètre)
	 */
	 public static double distancePoints(Vecteur2D v1, Vecteur2D v2) {
		 return v2.soustrait(v1).getLongueur()/IterationPhys.getPixelsparm();
	 }
	/**
	 * Effectue le calcul de distance entre deux composants (mètre)
	 * @param compo La composante
	 * @param tige La tige
	 * @return La distance entre les deux centres
	 */
	public static double distanceCompTige(Composantes compo, Objets tige) {
		Vecteur2D vecteurDebutTigeACompo = new Vecteur2D(compo.getPos().additionne(Composantes.getDiam() / 2.0, Composantes.getDiam() / 2.0).soustrait(tige.getPos()));
		return vecteurDebutTigeACompo.soustrait(vecteurDebutTigeACompo.projectionOrthogonale(((ObstacleTige) tige).getPosFin().soustrait(tige.getPos()))).getLongueur()/IterationPhys.getPixelsparm();
	}
	/**
	 * Détermine si la composante est entre les extrémités de la tige
	 * @param compo La composante
	 * @param tige La tige
	 * @return Si oui ou non elle se trouve entre les extrémités de la tige.
	 */
	public static boolean entreExtr(Composantes compo, ObstacleTige tige) {
		Vecteur2D vecteurDebutTigeACompo = new Vecteur2D(compo.getPos().soustrait(-Composantes.getDiam() / 2, -Composantes.getDiam() / 2).soustrait(tige.getPos()));
		Vecteur2D vecteurFinTigeACompo = new Vecteur2D(compo.getPos().soustrait(-Composantes.getDiam() / 2, -Composantes.getDiam() / 2).soustrait(tige.getPosFin()));
		Vecteur2D vecTige = new Vecteur2D(tige.getVecTige());
		double longueur = vecteurDebutTigeACompo.projectionOrthogonale(vecTige).getLongueur() + vecteurFinTigeACompo.projectionOrthogonale(vecTige).getLongueur();
		if (longueur >= tige.getLongueurTige() + Vecteur2D.getEpsilon()) return false;
		else return true;
	}
	/**
	 * Vérifie si la copmosante touche une des extrémités de la tige
	 * @param compo La composante 
	 * @param tige La tige 
	 * @return Retourne le point ou il y a une collision s'il y en a une
	 */
	public static Vecteur2D toucheExtr(Composantes compo, ObstacleTige tige) {
		if (tige.getPos().soustrait(compo.getPos().soustrait(-Composantes.getDiam() / 2, -Composantes.getDiam() / 2)).getLongueur() <= Composantes.getDiam()/2) return tige.getPos();
		else if (tige.getPosFin().soustrait(compo.getPos().soustrait(-Composantes.getDiam() / 2, -Composantes.getDiam() / 2)).getLongueur() <= Composantes.getDiam()/2) return tige.getPosFin();
		else return null;
	}
	/**
	 * Fait les étapes nécéssaire pour le calcul de la collision entre composantes
	 * @param compo Le composant initiale
	 * @param item L'item auquel il s'est heurté
	 */
	public static void collisionComp(Composantes compo, Composantes item) {
		double distanceCompo = distanceComps(compo, item);
		if (!compo.getUniqueId().equals(item.getUniqueId()) && distanceCompo <= Composantes.getDiam()/IterationPhys.getPixelsparm()){
			collision.add(new Collisions(compo, item));
			collision.get(collision.size() - 1).calculNormaleComp();
		}
	}
	/**
	 * Fait les étapes nécéssaire pour le calcul de la collision entre une composante et une tige
	 * @param compo La composante
	 * @param item La tige avec laquelle la composante entre en collision
	 */
	public static void collisionTige(Composantes compo, ObstacleTige item) {		
		if (distanceCompTige(compo, item) <= Composantes.getDiam() / IterationPhys.getPixelsparm() / 2) {
			if (entreExtr(compo, item)) {
				collision.add(new Collisions(compo, item));
				collision.get(collision.size() - 1).calculNormalTige();
			}
			else {
				Vecteur2D point = toucheExtr(compo, item);
				if (point != null) {
					collision.add(new Collisions(compo, item));
					collision.get(collision.size() - 1).calculNormalPoint(point);
				}
			}
		}
	}
	/**
	 * Fait les étapes nécéssaire pour le calcul de la collision entre une composante et un cercle
	 * @param compo La composante
	 * @param item Le cercle avec lequel la composante entre en collision
	 */
	public static void collisionCercle(Composantes compo, ObstacleCercle item) {		
		if (distancePoints(compo.getPosCentre(), item.getPosCentre()) <= (Composantes.getDiam() / 2.0 + item.getDiametre() / 2.0) / IterationPhys.getPixelsparm()) {
			collision.add(new Collisions(compo, item));
			collision.get(collision.size() - 1).calculNormalPoint(item.getPosCentre());
		}
	}
}
