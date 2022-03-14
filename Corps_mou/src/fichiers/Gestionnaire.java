package fichiers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import objets.Niveau;
import objets.Objets;

public class Gestionnaire {
	
	private BufferedWriter writer;
	private Scanner lecteur;
	private File fichier;
	
	/**
	 * Cr�e un gestionnaire de fichier
	 */
	public Gestionnaire() {	
		creationFichier();
		try {
			writer= new BufferedWriter(new FileWriter("C:\\Users\\dadel\\OneDrive\\Desktop\\objets.txt", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Cr�e un fichier s'il n'y en a pas
	 */
	public void creationFichier() {
		try {
			fichier = new File("C:\\Users\\dadel\\OneDrive\\Desktop\\objets.txt");
			if (fichier.createNewFile()) {
				System.out.println("Fichier cr��");
			}
			else {
				System.out.println("Fichier trouv�");
			}
		} 
		catch (IOException e){
			System.out.println("Une erreur s'est produite");
			e.printStackTrace();
		}
	}
	/**
	 * Sauvegarde le niveau
	 * @param niveau Le niveau � sauvegarder
	 */
	public void sauvegarder(Niveau niveau) {
		try {
			writer= new BufferedWriter(new FileWriter("C:\\Users\\dadel\\OneDrive\\Desktop\\objets.txt", false));
			//Section composantes
			writer.write("COMPOSANTES");
			writer.write("\n" + niveau.getCompo()[0][0].getPos().toString());
			writer.write("\n" + niveau.getCompo().length);
			
			//Section objets
			writer.write("\n\nOBJETS");
			for (Objets objet: niveau.getObjets()) {
				writer.write("\n" + objet.toString() + "\n---");
			}
			
			System.out.println("Fichier �dit� avec succ�s");
			writer.close();
		} 
		catch (IOException e){
			System.out.println("Une erreur s'est produite");
			e.printStackTrace();
		}
	}
	/**
	 * R�initialise le fichier
	 * @param niveau Le niveau � r�initialiser
	 */
	public void reinitialiser(Niveau niveau) {
		try {
			Writer writ= new BufferedWriter(new FileWriter("C:\\Users\\dadel\\OneDrive\\Desktop\\objets.txt", false));
			
			//Section composantes
			writ.write("COMPOSANTES");
			writ.write("\n" + "(0, 0)");
			writ.write("\n" + niveau.getCompo().length);
			
			//Section objets
			writ.write("\n\nOBJETS");
			
			System.out.println("Fichier r�initialis� avec succ�s");
			writ.close();
		} catch (IOException e) {
			System.out.println("Une erreur s'est produite");
			e.printStackTrace();
		}
		
	}
	/**
	 * Permet de lire  les lignes du fichier sauvegard�
	 */
	public String lire() {
		try {
			if (lecteur == null ) lecteur = new Scanner(fichier);
			if (lecteur.hasNext()) {
				String ligne = lecteur.nextLine();
				return ligne;
			}
			else {
				lecteur.close();
				lecteur = null;
				return "FIN";
			}

		}
		catch (FileNotFoundException e) {
			System.out.println("Le fichier est introuvable");
			return "//";
		}
		
	}
	/**
	 * Permet de trouver les r�p�tition d'une certaine chaine de charact�res dans le fichier
	 * @param rep La chaine de charact�res � trouver
	 * @return Le nombre de fois quelle est pr�sente
	 */
	public int repetitions(String rep) {
		Scanner chercheur = null;
		try {
			chercheur = new Scanner(fichier);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return (int) chercheur.findAll(rep).count();
	}
}
