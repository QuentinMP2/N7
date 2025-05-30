import afficheur.Ecran;
import java.awt.Color;

/**
  * Exemple d'utilisation de la classe Ecran.
  */
class ExempleEcran {

	public static void main(String[] args) {
		// Construire un écran
		Ecran ecran = new Ecran("FenetreEcran", 400, 250, 15);
		ecran.dessinerAxes();

		// Dessiner un point vert de coordonnées (1, 2)
		ecran.dessinerPoint(1.0,2.0, Color.GREEN);

		// Dessiner un segment rouge d'extrémités (6, 2) et (11, 9)
		ecran.dessinerLigne(6.0, 2.0, 11.0, 9.0, Color.RED);

		// Dessiner un cercle jaune de centre (4, 3) et rayon 2.5
		ecran.dessinerCercle(4.0, 3.0, 2.5, Color.YELLOW);

		// Dessiner le texte "Premier dessin" en bleu à la position (1, -2)
		ecran.dessinerTexte(1.0, -2.0, "Premier dessin", Color.BLUE);
	}

}
