package allumettes;

import java.util.Objects;

/** Lance une partie des 13 allumettes en fonction des arguments fournis
 * sur la ligne de commande.
 * @author	Xavier Crégut
 * @version	$Revision: 1.5 $
 */
public class Jouer {

	/** Nombre initial d'allumettes dans la partie. */
	private static final int NB_ALLUMETTES = 13;

	/** Nombre de joueurs. */
	private static final int NB_JOUEURS = 2;

	/** Lancer une partie. En argument sont donnés les deux joueurs sous
	 * la forme nom@stratégie.
	 * @param args la description des deux joueurs
	 */
	public static void main(String[] args) {
		Joueur joueur1;
		Joueur joueur2;
        ModeArbitrage arbitrage = ModeArbitrage.MEFIANT;

		try {
			verifierNombreArguments(args);

			// Configuration de l'arbitre et des joueurs.
			if (args.length == NB_JOUEURS + 1) {
				arbitrage = setModeArbitrage(args[0]);
				joueur1 = initialiserJoueur(args[1]);
				joueur2 = initialiserJoueur(args[2]);

			} else {
				joueur1 = initialiserJoueur(args[0]);
				joueur2 = initialiserJoueur(args[1]);
			}

			// Initialisation du jeu réel.
			Jeu jeu = new JeuReel(NB_ALLUMETTES);

			// Initialisation de l'arbitre.
			Arbitre arbitre = new Arbitre(joueur1, joueur2, arbitrage);

			arbitre.arbitrer(jeu);


		} catch (ConfigurationException e) {
			System.out.println();
			System.out.println("Erreur : " + e.getMessage());
			afficherUsage();
			System.exit(1);
		}
	}

	/** Afficher des indications sur la manière d'exécuter cette classe. */
	public static void afficherUsage() {
		System.out.println("\n" + "Usage :"
				+ "\n\t" + "java allumettes.Jouer joueur1 joueur2"
				+ "\n\t\t" + "joueur est de la forme nom@stratégie"
				+ "\n\t\t" + "strategie = naif | rapide | expert | humain | tricheur"
				+ "\n"
				+ "\n\t" + "Exemple :"
				+ "\n\t" + "	java allumettes.Jouer Xavier@humain "
				+ "Ordinateur@naif"
				+ "\n"
		);
	}

	private static void verifierNombreArguments(String[] args) {
		final int nbJoueurs = 2;
		if (args.length < nbJoueurs) {
			throw new ConfigurationException("Trop peu d'arguments : " + args.length);
		}
		if (args.length > nbJoueurs + 1) {
			throw new ConfigurationException("Trop d'arguments : " + args.length);
		}
	}

	/** Définit le mode d'arbitrage.
	 * @param option	chaîne de caractères associée à l'option "-confiant"
	 * @return l'arbitrage confiant ou méfiant selon option
	 */
	private static ModeArbitrage setModeArbitrage(String option) {
		if (!Objects.equals(option, "-confiant")) {
			throw new ConfigurationException("L'option \"-confiant\" doit "
					+ "obligatoirement être le premier argument de la ligne de commande "
					+ "si celle-ci est choisie.");
		}

		return ModeArbitrage.CONFIANT;
	}

	private static Joueur initialiserJoueur(String paramJoueur) {
		String[] nomJoueurEtStrategie = paramJoueur.split("@");
		// nomJoueurEtStrategie[0] : nom du joueur
		// nomJoueurEtStrategie[1] : stratégie du joueur
		if (nomJoueurEtStrategie.length != 2) {
			throw new ConfigurationException("Le joueur n'est pas valide !");
		}

		Joueur joueur;
        switch (nomJoueurEtStrategie[1]) {
			case "rapide":
				joueur = new Joueur(nomJoueurEtStrategie[0], new StrategieRapide());
				break;

			case "naif":
				joueur = new Joueur(nomJoueurEtStrategie[0], new StrategieNaif());
				break;

			case "expert":
				joueur = new Joueur(nomJoueurEtStrategie[0], new StrategieExpert());
				break;

			case "humain":
				joueur = new Joueur(
						nomJoueurEtStrategie[0],
						new StrategieHumain(nomJoueurEtStrategie[0])
				);
				break;

			case "tricheur":
				joueur = new Joueur(nomJoueurEtStrategie[0], new StrategieTricheur());
				break;

			default:
				throw new ConfigurationException("La stratégie "
						+ nomJoueurEtStrategie[1] + " n'est pas une stratégie valide.");
		}

		return joueur;
	}
}
