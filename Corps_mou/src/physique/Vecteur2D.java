package physique;

import java.awt.Point;

public class Vecteur2D {
	private double x;
	private double y;
	
	private static final double EPSILON = 1E-10;
	
	public Vecteur2D() {
		x = 0;
		y = 0;
	}
	public Vecteur2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vecteur2D(Vecteur2D v) {
		x = v.getX();
		y = v.getY();
	}
	public Vecteur2D(Point point) {
		x = point.getX();
		y = point.getY();
	}
	/**
	 * Additionne deux vecteurs
	 * @param v1 Premier vecteur
	 * @param v2 Deuxième vecteur
	 * @return Un nouveau vecteur consitué de l'addition des deux vecteur(v1, v2)
	 */
	public static Vecteur2D additionne(Vecteur2D v1, Vecteur2D v2) {
		return new Vecteur2D(v1.x + v2.x, v1.y + v2.y);
	}
	/**
	 * Additionne deux vecteurs
	 * @param v Vecteur à additionner au vecteur mère
	 * @return Un nouveau vecteur consitué de l'addition des deux vecteur(this, v)
	 */
	public Vecteur2D additionne(Vecteur2D v) {
		return additionne(this, v);
	}
	/**
	 * Additionne deux vecteurs
	 * @param v1 Premier vecteur
	 * @param x X à additionner
	 * @param y Y à additionner
	 * @return Un nouveau vecteur consitué de l'addition des deux vecteur(v1, v2)
	 */
	public static Vecteur2D additionne(Vecteur2D v1, double x, double y) {
		return new Vecteur2D(v1.x + x, v1.y + y);
	}
	/**
	 * Additionne deux vecteurs
	 * @param x X à additionner
	 * @param y Y à additionner
	 * @return Un nouveau vecteur consitué de l'addition des deux vecteur(this, v)
	 */
	public Vecteur2D additionne(double x, double y) {
		return additionne(this, x, y);
	}
	/**
	 * Soustrait deux vecteurs
	 * @param v1 Premier vecteur
	 * @param v2 Deuxième vecteur
	 * @return Un nouveau vecteur consitué de la soustraction de v2 à v1
	 */
	public static Vecteur2D soustrait(Vecteur2D v1, Vecteur2D v2) {
		return new Vecteur2D(v1.x - v2.x, v1.y - v2.y);
	}
	/**
	 * Soustrait deux vecteurs
	 * @param v Vecteur à soustraire au vecteur mère
	 * @return Un nouveau vecteur consitué de la soustraction des deux vecteur(this, v)
	 */
	public Vecteur2D soustrait(Vecteur2D v) {
		return soustrait(this, v);
	}
	/**
	 * Soustrait un vecteur et une pair d'entiers
	 * @param v Vecteur
	 * @param x Valeur en x à soustraire
	 * @param y Valeur en y à soustraire
	 * @return Un nouveau vecteur consitué de la soustraction de v2 à v1
	 */
	public static Vecteur2D soustrait(Vecteur2D v, double x, double y) {
		return new Vecteur2D(v.x - x, v.y - y);
	}
	/**
	 * Soustrait un vecteur et une parie d'entier
	 * @param x Valeur en x à soustraire
	 * @param y Valeur en y à soustraire
	 * @return Un nouveau vecteur consitué de la soustraction des deux vecteur(this, v)
	 */
	public Vecteur2D soustrait(double x, double y) {
		return soustrait(this, x, y);
	}
	/**
	 * Divise le vecteur par un scalaire
	 * @param v Vecteur
	 * @param k Scalaire
	 * @return Nouveau vecteur
	 */
	public static Vecteur2D divisionScalaire(Vecteur2D v, double k) {
		return new Vecteur2D(v.x/k, v.y/k);
	}
	/**
	 * Divise le vecteur par un scalaire
	 * @param k Scalaire
	 * @return Nouveau vecteur
	 */
	public Vecteur2D divisionScalaire(double k) {
		return divisionScalaire(this, k);
	}
	/**
	 * Multiplie un vecteur par un scalaire
	 * @param v Vecteur
	 * @param k Scalaire
	 * @return Nouveau vecteur
	 */
	public static Vecteur2D multiplicationScalaire(Vecteur2D v, double k) {
		return new Vecteur2D(v.x*k, v.y*k);
	}
	/**
	 * Multiplie un vecteur par un scalaire
	 * @param k Scalaire
	 * @return Nouveau vecteur
	 */
	public Vecteur2D multiplicationScalaire(double k) {
		return multiplicationScalaire(this, k);
	}
	/**
	 * Multiplie deux vecteurs
	 * @param v1 Premier vecteur
	 * @param v2 Deuxième vecteur
	 * @return Le nouveau vecteur
	 */
	public static double multiplicationVectorielle(Vecteur2D v1, Vecteur2D v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	/**
	 * Multiplie deux vecteurs
	 * @param v Le Vecteur qui multiplie
	 * @return Le nouveau vecteur
	 */
	public double multiplicationVectorielle(Vecteur2D v) {
		return multiplicationVectorielle(this, v);
	}
	/**
	 * Permet d'enlever toutes composantes appartenant au vecteur "v" du vecteur de base Ex: vBase:3, 4 - vEnlever 0, 1: Rep: 3, 0)
	 * @param v Enlever ces composantes du vecteur initiale
	 * @return Un nouveau vecteur sans les composantes du vecteur v
	 */
	public static Vecteur2D enleverComposantesEn(Vecteur2D vBase, Vecteur2D vEnlever) {
		double k = vBase.multiplicationVectorielle(vEnlever) / vEnlever.multiplicationVectorielle(vEnlever);
		return new Vecteur2D(vBase.x - k*vEnlever.x,vBase.y - k*vEnlever.y);
	}
	/**
	 * Permet d'enlever toutes composantes appartenant au vecteur "v" du vecteur de base
	 * @param v Enlever ces composantes du vecteur initiale
	 * @return Un nouveau vecteur sans les composantes du vecteur v
	 */
	public Vecteur2D enleverComposantesEn(Vecteur2D v) {
		return enleverComposantesEn(this, v);
	}
	/**
	 * Peremt de seulement garder les composantes dans la même direction que vGarder
	 * @param vBase Vecteur de base
	 * @param vGarder Direction à garder
	 * @return Nouveau vecteur modifié
	 */
	public static Vecteur2D garderComposantesEn(Vecteur2D vBase, Vecteur2D vGarder) {
		return new Vecteur2D(vBase.soustrait(vBase.enleverComposantesEn(vGarder)));
	}
	/**
	 * Peremt de seulement garder les composantes dans la même direction que vGarder
	 * @param v Vecteur de base
	 * @return Nouveau vecteur modifié
	 */
	public Vecteur2D garderComposantesEn(Vecteur2D v) {
		return garderComposantesEn(this, v);
	}
	/**
	 * Obtient un vecteur perpendiculaire à celui donné
	 * @param v Le Vecteur initial
	 * @return Un nouveau vecteur perpendiculaire
	 */
	public static Vecteur2D perpendiculaire(Vecteur2D v) {
		return new Vecteur2D(v.getY(), -v.getX());
	}
	/**
	 * Obtient un vecteur perpendiculaire à celui donné
	 * @return Un nouveau vecteur perpendiculaire
	 */
	public Vecteur2D perpendiculaire() {
		return perpendiculaire(this);
	}
	/**
	 * Obtient la longueur du vecteur
	 * @param v Vecteur
	 * @return la longueur du vecteur
	 */
	public static double getLongueur(Vecteur2D v) {
		return Math.sqrt(v.x*v.x + v.y*v.y);
	}
	/**
	 * Obtient la longueur du vecteur
	 * @return la longueur du vecteur
	 */
	public double getLongueur() {
		return getLongueur(this);
	}
	/**
	 * Normalise un vecteur
	 * @param v Vecteur
	 * @return Le vecteur normalisé
	 */
	public static Vecteur2D normalise(Vecteur2D v) {
		if (v.getLongueur() < EPSILON) return new Vecteur2D();
		return new Vecteur2D(v.divisionScalaire(v.getLongueur()));
	}
	/**
	 * Normalise un vecteur
	 * @return Le vecteur normalisé
	 */
	public Vecteur2D normalise() {
		return normalise(this);
	}
	/**
	 * Effectue la projection orthogonale entre deux veteur
	 * @param v1 Le vecteur en question
	 * @param v2 Le veteur sur lequel la projection est effectué
	 * @return La projection orthogonale de v1 sur v2
	 */
	public static Vecteur2D projectionOrthogonale(Vecteur2D v1, Vecteur2D v2) {
		return new Vecteur2D(v2.multiplicationScalaire(v1.multiplicationVectorielle(v2) / v2.multiplicationVectorielle(v2)));
	}
	/**
	 * Effectue la projection orthogonale entre deux veteur
	 * @param v Le vecteur en question
	 * @return La projection
	 */
	public Vecteur2D projectionOrthogonale(Vecteur2D v) {
		return projectionOrthogonale(this, v);
	}
	/**
	 * @return Position en X
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param Set la position en X
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return Position en Y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y Set la position en Y
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Set la position en X et Y individuelle
	 * @param x
	 * @param y
	 */
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Set la position en X et Y avec un vecteur
	 * @param v Vecteur position
	 */
	public void setXY(Vecteur2D v) {
		this.x = v.x;
		this.y = v.y;
	}
	/**
	 * Permett d'accéder à la valeur Epsilon. Cette valeur permet d'ajuster les réponses nécessitant beaucoup de précision. Faire +- Epsilon nous permets d'éviter certaines erreurs de précision dans certains cas précis. 
	 * @return Epsilon
	 */
	public static double getEpsilon() {
		return EPSILON;
	}
	/**
	 * Décrit le vecteur
	 */
	public String toString() {
		return "(" + (int) (x * 10000) / 10000.0 + ", " + (int) (y * 10000) / 10000.0 + ")";
	}
	

}
