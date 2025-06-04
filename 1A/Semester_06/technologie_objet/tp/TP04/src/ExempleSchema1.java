import afficheur.AfficheurSVG;
import afficheur.Ecran;

/** Construire le schéma proposé dans le sujet de TP avec des points,
  * et des segments.
  *
  * @author	Xavier Crégut
  * @version	$Revision: 1.7 $
  */
public class ExempleSchema1 {

	/** Construire le schéma et le manipuler.
	  * Le schéma est affiché.
	  * @param args les arguments de la ligne de commande
	  */
	public static void main(String[] args)
	{
		// Créer les trois segments
		Point p1 = new Point(3, 2);
		Point p2 = new Point(6, 9);
		Point p3 = new Point(11, 4);
		Segment s12 = new Segment(p1, p2);
		Segment s23 = new Segment(p2, p3);
		Segment s31 = new Segment(p3, p1);

		// Créer le barycentre
		double sx = p1.getX() + p2.getX() + p3.getX();
		double sy = p1.getY() + p2.getY() + p3.getY();
		Point barycentre = new Point(sx / 3, sy / 3);

		// Afficher le schéma
		System.out.println("Le schéma est composé de : ");
		s12.afficher();		System.out.println();
		s23.afficher();		System.out.println();
		s31.afficher();		System.out.println();
		barycentre.afficher();	System.out.println();

		// Construire un écran
		Ecran ecran = new Ecran("ExempleSchema1", 600, 400, 20);
		ecran.dessinerAxes();

		// Construire un afficheur SVG
		AfficheurSVG ecranSVG = new AfficheurSVG("ExempleSchema1 en SVG", "Schéma de l'exercice 2", 600, 400);

		// Construire un afficheur textuel
		AfficheurTexte ecranTexte = new AfficheurTexte("Schéma de l'exercice 2");

		// Dessiner le barycentre et les segments
		barycentre.dessiner(ecran);
		s12.dessiner(ecran);
		s23.dessiner(ecran);
		s31.dessiner(ecran);

		// Dessiner le barycentre et les segments en SVG
		barycentre.dessiner(ecranSVG);
		s12.dessiner(ecranSVG);
		s23.dessiner(ecranSVG);
		s31.dessiner(ecranSVG);

		// Dessiner le barycentre et les segments en textuel
		barycentre.dessiner(ecranTexte);
		s12.dessiner(ecranTexte);
		s23.dessiner(ecranTexte);
		s31.dessiner(ecranTexte);

		// Translater le schéma de 4 en X et -3 en Y
		barycentre.translater(4.0, -3.0);
		s12.translater(4.0, -3.0);
		s23.translater(4.0, -3.0);
		s31.translater(4.0, -3.0);

		// Dessiner le barycentre et les segments
		barycentre.dessiner(ecran);
		s12.dessiner(ecran);
		s23.dessiner(ecran);
		s31.dessiner(ecran);

		// Dessiner le barycentre et les segments en SVG
		barycentre.dessiner(ecranSVG);
		s12.dessiner(ecranSVG);
		s23.dessiner(ecranSVG);
		s31.dessiner(ecranSVG);

		// Dessiner le barycentre et les segments en textuel
		barycentre.dessiner(ecranTexte);
		s12.dessiner(ecranTexte);
		s23.dessiner(ecranTexte);
		s31.dessiner(ecranTexte);

		// Afficher le schéma SVG
		ecranSVG.ecrire();
		ecranSVG.ecrire("Schema");
	}

}
