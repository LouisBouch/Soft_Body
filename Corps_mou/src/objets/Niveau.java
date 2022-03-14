package objets;

import java.util.ArrayList;

public class Niveau {
	private ArrayList<Objets> objets;
	private ArrayList<Links> links = new ArrayList<Links>();;
	private Composantes[][] compo;
	
	/**
	 * Crée le niveau
	 * @param compo Les composantes du corps mou
	 * @param objets Les obstacles
	 */
	public Niveau(Composantes[][] compo, ArrayList<Objets>  objets) {
		this.objets = objets;
		this.compo = compo;
	}
	/**
	 * Permet de lier les différentes composantes pour le corps mou
	 * @param composantes Le corps mou
	 */
	public void link(Composantes[][] composantes) {
		for (int i = 0; i < composantes.length; i++) {
			for (int o = 0; o < composantes[0].length; o++) {
				if (i - 1 >= 0) {
					links.add(new Links(composantes[i][o], composantes[i - 1][o]));		
				}
				if (i - 1 >= 0 && o + 1 < composantes[0].length) {
					links.add(new Links(composantes[i][o], composantes[i - 1][o + 1]));
				}
				if (o + 1 < composantes[0].length) {
					links.add(new Links(composantes[i][o], composantes[i][o + 1]));
				}
				if (i + 1 < composantes.length && o + 1 < composantes[0].length) {
					links.add(new Links(composantes[i][o], composantes[i + 1][o + 1]));
				}
			}
		}
	}
	/**
	 * Peremt d'obetnir les liens
	 * @return Les liens
	 */
	public ArrayList<Links> getLinks() {
		return links;
	}
	/**
	 * Permet d'obtenir les obstacles de la simulation
	 * @return Les objets
	 */
	public ArrayList<Objets>  getObjets() {
		return objets;
	}
	/**
	 * Permet d'obtenir les composantes du cors mou du niveau
	 * @return Les composantes
	 */
	public Composantes[][] getCompo() {
		return compo;
	}
	/**
	 * Print les forces des composantes
	 */
	public void getForces() {
		String rep = "";
		for (int i = 0; i < compo.length; i++) {
			for (int o = 0; o < compo[0].length; o++) {
				rep += compo[o][i].getUniqueId() + ": " + compo[o][i].getForce() + ", ";
			}
			rep += "\n";
		}
		System.out.println(rep);
	}
	

}
