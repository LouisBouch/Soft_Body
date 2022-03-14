package physique;

import objets.Composantes;
import objets.Objets;
import objets.ObstacleCercle;
import objets.ObstacleTige;

public class Collisions {
	
	private Vecteur2D normale = new Vecteur2D();
	private Vecteur2D vecDist = new Vecteur2D();
	
	private Objets objetPrincipal;
	private Objets objetCollisionne;
	
	private double distObjets;
	
	
	/**
	 * Crée un objet collision qui garde en mémoire les informations importante pour la colision
	 * @param objetPrincipal L'objet principale
	 * @param objetCollisionne L'item auquel il s'est heurté
	 */
	public Collisions(Objets objetPrincipal, Objets objetCollisionne) {
		this.objetPrincipal = objetPrincipal;
		this.objetCollisionne = objetCollisionne;
	}
	
	/**
	 * Calcul la normale lorsques deux composantes rondes forment la collision
	 */
	public void calculNormaleComp() {
		vecDist.setXY(objetCollisionne.getPos().soustrait(objetPrincipal.getPos()));
		distObjets = vecDist.getLongueur();
		normale.setXY(vecDist.normalise());
	}
	/**
	 * Calcul la normale quand il y a une collision avec une tige
	 */
	public void calculNormalTige() {
		Vecteur2D vecteurDebutTigeACompo = new Vecteur2D(objetPrincipal.getPos().additionne(Composantes.getDiam() / 2.0, Composantes.getDiam() / 2.0).soustrait(objetCollisionne.getPos()));
		vecDist.setXY(vecteurDebutTigeACompo.soustrait(vecteurDebutTigeACompo.projectionOrthogonale(((ObstacleTige) objetCollisionne).getPosFin().soustrait(objetCollisionne.getPos()))));	
		distObjets = vecDist.getLongueur();
		normale.setXY(vecDist.normalise());
	}
	/**
	 * Fait le calcul de la normale entre une composante et un point
	 * @param extr L'extrémité touché par la composante
	 */
	public void calculNormalPoint(Vecteur2D extr) {
		vecDist = objetPrincipal.getPos().additionne(Composantes.getDiam() / 2.0, Composantes.getDiam() / 2.0).soustrait(extr);
		distObjets = vecDist.getLongueur();
		normale.setXY(vecDist.normalise());
	}
	/**
	 * Ressort la composante de l'objet auquel elle s'est heurtée
	 * @param dist Distance entre les deux objets
	 * @param maxBeforeClip Distance de la collision
	 * @return La position qu'il faudra retirer à la composante
	 */
	public Vecteur2D pushBack(/*double dist, double maxBeforeClip*/) {
		//Retourne le changement de position
		if (objetCollisionne.getClass().equals(Composantes.class)) {
			return normale.multiplicationScalaire((distObjets - Composantes.getDiam())/IterationPhys.getPixelsparm());
		}
		if (objetCollisionne.getClass().equals(ObstacleTige.class)) {
			return normale.multiplicationScalaire(-(distObjets - Composantes.getDiam()/2.0)/IterationPhys.getPixelsparm());	
		}
		if (objetCollisionne.getClass().equals(ObstacleCercle.class)) {
			return normale.multiplicationScalaire(-(distObjets - Composantes.getDiam()/2.0 - ((ObstacleCercle) objetCollisionne).getDiametre() / 2.0)/IterationPhys.getPixelsparm());	
		}
		else return new Vecteur2D();
	}
	/**
	 * Permet d'obtenir la normale
	 * @return
	 */
	public Vecteur2D getNormale() {
		return normale;
	}
	/**
	 * Permet d'obtenir la vitesse de l'objet auquel la composante s'est heurtée
	 * @return La vitesse de la composante
	 */
	public Vecteur2D getVitesseObjetCollisionne() {
		return objetCollisionne.getVit().divisionScalaire(IterationPhys.getPixelsparm());
	}
	/**
	 * Permet d'obtenir l'objet auquel la composante s'est heurtée
	 * @return L'objet
	 */
	public Objets getObjetCollisionne() {
		return objetCollisionne;
	}
	/**
	 * Obtient la distance entre les objets
	 * @return La distance
	 */
	public double getDistObjets() {
		return distObjets;
	}

	
	

}
