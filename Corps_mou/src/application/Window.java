package application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import objets.Links;
import physique.IterationPhys;
import physique.Vecteur2D;
import java.awt.Font;

public class Window extends JFrame {
	private static final long serialVersionUID = 1887260594516146837L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(-10, 0, 1940 , 1050);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ZoneDeJeu zoneDeJeu = new ZoneDeJeu();
		zoneDeJeu.setBounds(5, 54, 1914, 952);
		zoneDeJeu.creerGestionnaire();
		
		Aide aide = new Aide();
		aide.setBounds(5, 54, 1914, 952);
		contentPane.add(aide);
		aide.setVisible(false);
		
		JLabel lblAide = new JLabel("Appuyez sur \"F3\" pour de l'aide");
		lblAide.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAide.setBounds(15, 39, 214, 14);
		contentPane.add(lblAide);

		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(15, 5, 85, 35);
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(110, 5, 89, 35);
		
		JButton btnPrev = new JButton("Page pr\u00E9c\u00E9dente");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aide.prev();
			}
		});
		btnPrev.setBounds(102, 5, 140, 35);
		btnPrev.setVisible(false);
		contentPane.add(btnPrev);
		
		JButton btnProch = new JButton("Page suivante");
		btnProch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aide.proch();
			}
		});
		btnProch.setBounds(1680, 5, 140, 35);
		btnProch.setVisible(false);
		contentPane.add(btnProch);
		
		JSlider sliderComps = new JSlider();
		sliderComps.setBounds(840, 17, 246, 31);
		sliderComps.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (ZoneDeJeu.isReady() || ZoneDeJeu.isEditing()) {
					zoneDeJeu.setNbComp(sliderComps.getValue());
					btnStop.setVisible(false);
					btnStart.setText("Start");
					zoneDeJeu.reset();
					zoneDeJeu.stop();
					ZoneDeJeu.setReady(true);
				}	
			}
		});
		sliderComps.setMinorTickSpacing(1);
		sliderComps.setMajorTickSpacing(5);
		sliderComps.setMaximum(10);
		sliderComps.setValue(5);
		sliderComps.setSnapToTicks(true);
		sliderComps.setPaintTicks(true);
		
		JSlider sliderLiens = new JSlider();
		sliderLiens.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Links.setK(10 * sliderLiens.getValue());
			}
		});
		sliderLiens.setMinorTickSpacing(10);
		sliderLiens.setSnapToTicks(true);
		sliderLiens.setMajorTickSpacing(50);
		sliderLiens.setPaintTicks(true);
		sliderLiens.setValue(30);
		sliderLiens.setBounds(564, 17, 200, 26);
		contentPane.add(sliderLiens);
		
		

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ZoneDeJeu.isReady()) {
					btnStart.setText("Reset");
					btnStop.setVisible(true);
					zoneDeJeu.demarrer();
					ZoneDeJeu.setReady(false);
				}
				else {
					if (!ZoneDeJeu.isEditing()) {
						btnStop.setVisible(false);
						btnStart.setText("Start");
						zoneDeJeu.reset();
						zoneDeJeu.stop();
						ZoneDeJeu.setReady(true);
					}
				}
			}
		});
		
		btnStop.setVisible(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ZoneDeJeu.isRunning()) {
					zoneDeJeu.stop();
					btnStop.setText("Continue");
					
				}
				else {
					btnStop.setText("Stop");
					zoneDeJeu.demarrer();	
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(zoneDeJeu);
		contentPane.add(btnStart);
		contentPane.add(btnStop);
		contentPane.add(sliderComps);
		
		JButton btnSauvegarde = new JButton("Sauvegarder");
		btnSauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.sauvgarder();
			}
		});
		btnSauvegarde.setBounds(1796, 5, 118, 38);
		contentPane.add(btnSauvegarde);
		
		JButton btnCharge = new JButton("Charger");
		btnCharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.charger();
				btnStop.setVisible(false);
				btnStart.setText("Start");
				zoneDeJeu.stop();
				ZoneDeJeu.setReady(true);
			}
		});
		btnCharge.setBounds(1699, 5, 89, 38);
		contentPane.add(btnCharge);
		
		JCheckBox chckbxToggleTestMode = new JCheckBox(" Test Mode");
		chckbxToggleTestMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxToggleTestMode.isSelected()) {
					ZoneDeJeu.setTest(true);
					zoneDeJeu.stop();
					zoneDeJeu.setNbComp(1);
					zoneDeJeu.reset();
				}
				else {
					ZoneDeJeu.setTest(false);
					zoneDeJeu.stop();
					zoneDeJeu.setNbComp(sliderComps.getValue());
					zoneDeJeu.reset();
				}
			}
		});
		chckbxToggleTestMode.setBounds(1093, 20, 97, 23);
		contentPane.add(chckbxToggleTestMode);
		
		JLabel lblNbComposantes = new JLabel("Nombre de rang\u00E9es dans le corps mou");
		lblNbComposantes.setBounds(852, 5, 224, 14);
		contentPane.add(lblNbComposantes);
		
		
		
		JLabel lblForceLiens = new JLabel("Force des liens");
		lblForceLiens.setDisplayedMnemonic(KeyEvent.VK_ENTER);
		lblForceLiens.setBounds(627, 5, 89, 14);
		contentPane.add(lblForceLiens);
		
		JCheckBox chckbxGravite = new JCheckBox("Gravit\u00E9");
		chckbxGravite.setSelected(true);
		chckbxGravite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxGravite.isSelected()) IterationPhys.setGravite(true);
				else IterationPhys.setGravite(false);
			}
		});
		chckbxGravite.setBounds(761, 20, 69, 23);
		contentPane.add(chckbxGravite);
		this.setLocation(-1930, 0);

		
		addKeyBinding(zoneDeJeu, KeyEvent.VK_C, 2, "Charger", (evt) -> {
			zoneDeJeu.charger();
			btnStop.setVisible(false);
			btnStart.setText("Start");
			zoneDeJeu.stop();
			ZoneDeJeu.setReady(true);
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_S, 2, "Sauvegarder", (evt) -> {
			zoneDeJeu.sauvgarder();
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_R, 2, "Réinitialiser", (evt) -> {
			zoneDeJeu.reinitialiser();
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_R, 0, "Restart", (evt) -> {
			btnStop.setVisible(false);
			zoneDeJeu.restart();
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_W, 0, "Commencer", (evt) -> {
			if (ZoneDeJeu.isReady()) {
				btnStart.setText("Reset");
				btnStop.setVisible(true);
				zoneDeJeu.demarrer();
				ZoneDeJeu.setReady(false);
			}
			else {
				if (!ZoneDeJeu.isEditing()) {
					btnStop.setVisible(false);
					btnStart.setText("Start");
					zoneDeJeu.reset();
					zoneDeJeu.stop();
					ZoneDeJeu.setReady(true);
				}
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_T, 0, "ToggleTest", (evt) -> {
			if (!ZoneDeJeu.isEditing()) {
				if (!chckbxToggleTestMode.isSelected()) {
					btnStop.setVisible(false);
					btnStart.setText("Start");
					ZoneDeJeu.setReady(true);
					chckbxToggleTestMode.setSelected(true);
					ZoneDeJeu.setTest(true);
					zoneDeJeu.stop();
					zoneDeJeu.setNbComp(1);
					zoneDeJeu.reset();
				}
				else {
					btnStop.setVisible(false);
					btnStart.setText("Start");
					ZoneDeJeu.setReady(true);
					chckbxToggleTestMode.setSelected(false);
					ZoneDeJeu.setTest(false);
					zoneDeJeu.stop();
					zoneDeJeu.setNbComp(sliderComps.getValue());
					zoneDeJeu.reset();
				}
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_G, 0, "ToggleGravité", (evt) -> {
			if (chckbxGravite.isSelected()) {
				chckbxGravite.setSelected(false);
				IterationPhys.setGravite(false);
			}
			else {
				chckbxGravite.setSelected(true);
				IterationPhys.setGravite(true);
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_F, 0, "Forces", (evt) -> {
			zoneDeJeu.getNiveau().getForces();
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_S, 0, "Stop", (evt) -> {
			if (ZoneDeJeu.isRunning()) {
				zoneDeJeu.stop();
				btnStop.setText("Continue");
				
			}
			else {
				btnStop.setText("Stop");
				zoneDeJeu.demarrer();	
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_ENTER, 0, "Next", (evt) -> {
			if (ZoneDeJeu.isRunning()) {
				zoneDeJeu.stop();
				btnStop.setText("Continue");		
			}
			else if (ZoneDeJeu.isReady()) {
				btnStart.setText("Reset");
				btnStop.setVisible(true);
				zoneDeJeu.demarrer();
				ZoneDeJeu.setReady(false);
			}
			else {
				if (!ZoneDeJeu.isReady()) zoneDeJeu.unPas();
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_ENTER, 2, "PetitNext", (evt) -> {
			if (ZoneDeJeu.isRunning()) {
				zoneDeJeu.stop();
				btnStop.setText("Continue");		
			}
			else {
				if (!ZoneDeJeu.isReady()) zoneDeJeu.unPetitPas();
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_LEFT, 0, "Gauche", (evt) -> {
			if (ZoneDeJeu.isTest()) zoneDeJeu.setVit(new Vecteur2D(-5, 0));	
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_RIGHT, 0, "Droite", (evt) -> {
			if (ZoneDeJeu.isTest())	zoneDeJeu.setVit(new Vecteur2D(5, 0));
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_UP, 0, "Haut", (evt) -> {
			if (ZoneDeJeu.isTest()) zoneDeJeu.setVit(new Vecteur2D(0, -5));	
			
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_DOWN, 0, "Bas", (evt) -> {
			if (ZoneDeJeu.isTest())	zoneDeJeu.setVit(new Vecteur2D(0, 5));	
		});
		
		addKeyBinding(lblAide, KeyEvent.VK_F3, 0, "Help", (evt) -> {
			if (ZoneDeJeu.isEditing()) {
				zoneDeJeu.reset();
				zoneDeJeu.stop();
				ZoneDeJeu.setEditing(false);
				ZoneDeJeu.setReady(true);
				chckbxToggleTestMode.setEnabled(true);
			}
			if (!aide.isVisible()) {
				lblAide.setText("Appuyez sur \"F3\" pour revenir au jeu");
				aide.setVisible(true);
				aide.repaint();
				zoneDeJeu.setVisible(false);
				zoneDeJeu.setEnabled(false);
				
				chckbxGravite.setVisible(false);
				chckbxToggleTestMode.setVisible(false);
				sliderComps.setVisible(false);
				sliderLiens.setVisible(false);
				lblForceLiens.setVisible(false);
				lblNbComposantes.setVisible(false);
				btnCharge.setVisible(false);
				btnSauvegarde.setVisible(false);
				btnStart.setVisible(false);
				btnStop.setVisible(false);
				
				btnPrev.setVisible(true);
				btnProch.setVisible(true);
				
				lblAide.setFont(new Font("Tahoma", Font.BOLD, 40));
				lblAide.setBounds(650, 0, 1000, 55);				
			}
			else {
				lblAide.setText("Appuyez sur \"F3\" pour de l'aide");
				aide.setVisible(false);
				zoneDeJeu.setVisible(true);
				zoneDeJeu.setEnabled(true);
				
				chckbxGravite.setVisible(true);
				chckbxToggleTestMode.setVisible(true);
				sliderComps.setVisible(true);
				sliderLiens.setVisible(true);
				lblForceLiens.setVisible(true);
				lblNbComposantes.setVisible(true);
				btnCharge.setVisible(true);
				btnSauvegarde.setVisible(true);
				btnStart.setVisible(true);
				
				btnPrev.setVisible(false);
				btnProch.setVisible(false);
				
				lblAide.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblAide.setBounds(15, 39, 214, 14);
			}
			zoneDeJeu.stop();
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_ESCAPE, 0, "ArretEdit", (evt) -> {
			if (ZoneDeJeu.isEditing()) {
				zoneDeJeu.reset();
				zoneDeJeu.stop();
				ZoneDeJeu.setEditing(false);
				ZoneDeJeu.setReady(true);
				chckbxToggleTestMode.setEnabled(true);
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_1, 0, "Place obstacleTige", (evt) -> {
			if ((ZoneDeJeu.isReady() || ZoneDeJeu.isEditing()) && !ZoneDeJeu.isTest()) {
				zoneDeJeu.reset();
				zoneDeJeu.demarrer();
				ZoneDeJeu.setReady(false);
				ZoneDeJeu.setEditing(true);
				zoneDeJeu.setLastPressed(1);
				chckbxToggleTestMode.setEnabled(false);
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_2, 0, "Place obstacleCercle", (evt) -> {
			if ((ZoneDeJeu.isReady() || ZoneDeJeu.isEditing()) && !ZoneDeJeu.isTest()) {
				zoneDeJeu.reset();
				zoneDeJeu.demarrer();
				ZoneDeJeu.setReady(false);
				ZoneDeJeu.setEditing(true);
				zoneDeJeu.setLastPressed(2);
				chckbxToggleTestMode.setEnabled(false);
			}
		});
		addKeyBinding(zoneDeJeu, KeyEvent.VK_BACK_SPACE, 0, "Réinitialiser début tige", (evt) -> {
			if (ZoneDeJeu.isEditing()) {
				zoneDeJeu.setPosIniObs(null);
			}
		});
		
		
		
		
		
		
	}
	/**
	 * Permet la création de keybindings
	 * @param comp La zone de jeu
	 * @param keyCode La touche de clavier
	 * @param mod Si shift ou autre doit être enfoncé pour le fonctionnement de la touche
	 * @param id La description de l'action
	 * @param act L'action effectué
	 */
	public static void addKeyBinding(JComponent comp, int keyCode, int mod, String id, ActionListener act) {
		InputMap input = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		input.put(KeyStroke.getKeyStroke(keyCode, mod, false), id);
		
		ActionMap action = comp.getActionMap();
		action.put(id, new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				act.actionPerformed(e);
			}
		});
	}//End addKeyBinding
	public static void keyBindings() {
		
	}
}
