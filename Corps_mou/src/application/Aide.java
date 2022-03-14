package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Aide extends JPanel {
	private static final long serialVersionUID = -7846463161460219269L;
	
	private String[] nomImages = {"Modes", "Boutons", "Raccourcis"};
	private int posImage = 0;

	public Aide() {
		setBackground(Color.LIGHT_GRAY);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		ImageIcon image = new ImageIcon("C:\\Users\\dadel\\OneDrive\\Desktop\\Workspace\\Corps_mou\\images\\" + nomImages[posImage] + ".png");
		image.paintIcon(getFocusCycleRootAncestor(), g2d, 0, 0);
	}
	public void prev() {
		posImage -= 1;
		if (posImage < 0) posImage = 0;
		repaint();
	}
	public void proch() {
		posImage += 1;
		if (posImage >= nomImages.length) posImage = nomImages.length - 1;
		repaint();
	}
	
}
