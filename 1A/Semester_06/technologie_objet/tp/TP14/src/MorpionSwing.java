import javax.management.JMException;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/** Programmation d'un jeu de Morpion avec une interface graphique Swing.
  *
  * REMARQUE : Dans cette solution, le patron MVC n'a pas été appliqué !
  * On a un modèle (?), une vue et un contrôleur qui sont fortement liés.
  *
  * @author	Xavier Crégut
  * @version	$Revision: 1.4 $
  */

public class MorpionSwing {

	// les images à utiliser en fonction de l'état du jeu.
	private static final Map<ModeleMorpion.Etat, ImageIcon> images
		= new HashMap<ModeleMorpion.Etat, ImageIcon>();
	static {
		images.put(ModeleMorpion.Etat.VIDE, new ImageIcon("blanc.jpg"));
		images.put(ModeleMorpion.Etat.CROIX, new ImageIcon("croix.jpg"));
		images.put(ModeleMorpion.Etat.ROND, new ImageIcon("rond.jpg"));
	}

// Choix de réalisation :
// ----------------------
//
//  Les attributs correspondant à la structure fixe de l'IHM sont définis
//	« final static » pour montrer que leur valeur ne pourra pas changer au
//	cours de l'exécution. Ils sont donc initialisés sans attendre
//  l'exécution du constructeur !

	private ModeleMorpion modele;	// le modèle du jeu de Morpion

//  Les éléments de la vue (IHM)
//  ----------------------------

	/** Fenêtre principale */
	private JFrame fenetre;

	/** Bouton pour quitter */
	private final JButton boutonQuitter = new JButton("Q");

	/** Bouton pour commencer une nouvelle partie */
	private final JButton boutonNouvellePartie = new JButton("N");

	/** Cases du jeu */
	private final JLabel[][] cases = new JLabel[3][3];

	/** Zone qui indique le joueur qui doit jouer */
	private final JLabel joueur = new JLabel();


// Le constructeur
// ---------------

	/** Construire le jeu de morpion */
	public MorpionSwing() {
		this(new ModeleMorpionSimple());
	}

	/** Construire le jeu de morpion */
	public MorpionSwing(ModeleMorpion modele) {
		// Initialiser le modèle
		this.modele = modele;

		// Créer les cases du Morpion
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j] = new JLabel();
			}
		}

		// Initialiser le jeu
		this.recommencer();

		// Construire la vue (présentation)
		//	Définir la fenêtre principale
		this.fenetre = new JFrame("Morpion");
		this.fenetre.setLocation(100, 200);
		Container contenu = this.fenetre.getContentPane();
		contenu.setLayout(new BorderLayout());

		// JMenuBar
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("Jeu", true);
		JMenuItem bNouvellePartie = new JMenuItem("Nouvelle partie");
		JMenuItem bQuitter = new JMenuItem("Quitter");
		menu.add(bNouvellePartie);
		menu.add(bQuitter);

		menuBar.add(menu);
		contenu.add(menuBar, BorderLayout.NORTH);


		// La grille du morpion
		Container grilleMorpion = new Container();
		grilleMorpion.setLayout(new GridLayout(3,3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++){
				grilleMorpion.add(cases[i][j]);
				class ListernerCase extends MouseAdapter {

					private int i, j;

					public ListernerCase(int i, int j) {
						this.i = i;
						this.j = j;
					}

					@Override
					public void mouseClicked(MouseEvent mouseEvent) {
						try {
							MorpionSwing.this.modele.cocher(i, j);
							MorpionSwing.this.joueur.setIcon(images.get(MorpionSwing.this.modele.getJoueur()));
							MorpionSwing.this.cases[i][j].setIcon(images.get(MorpionSwing.this.modele.getValeur(i, j)));

							if (MorpionSwing.this.modele.estTerminee()) {
								JOptionPane.showMessageDialog(MorpionSwing.this.fenetre, "La partie est finie !", "Partie terminée.", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (CaseOccupeeException e) {
							throw new RuntimeException("Case occupée !");
						}
					}
				};

				cases[i][j].addMouseListener(new ListernerCase(i, j));
			}
		}
		contenu.add(grilleMorpion, BorderLayout.CENTER);

		// Les boutons nouvelle partie et quitter
		Container boutons = new Container();
		boutons.setLayout(new FlowLayout());
		boutons.add(boutonNouvellePartie);
		boutons.add(joueur);
		boutons.add(boutonQuitter);

		contenu.add(boutons, BorderLayout.SOUTH);


		// Construire le contrôleur (gestion des événements)
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boutonNouvellePartie.addActionListener(ev -> recommencer());
		boutonQuitter.addActionListener(ev -> this.modele.quitter());

		bNouvellePartie.addActionListener(ev -> recommencer());
		bQuitter.addActionListener(ev -> this.modele.quitter());

		// afficher la fenêtre
		this.fenetre.pack();			// redimmensionner la fenêtre
		this.fenetre.setVisible(true);	// l'afficher
	}

// Quelques réactions aux interactions de l'utilisateur
// ----------------------------------------------------

	/** Recommencer une nouvelle partie. */
	public void recommencer() {
		this.modele.recommencer();

		// Vider les cases
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j].setIcon(images.get(this.modele.getValeur(i, j)));
			}
		}

		// Mettre à jour le joueur
		joueur.setIcon(images.get(modele.getJoueur()));
	}



// La méthode principale
// ---------------------

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MorpionSwing();
			}
		});
	}

}
