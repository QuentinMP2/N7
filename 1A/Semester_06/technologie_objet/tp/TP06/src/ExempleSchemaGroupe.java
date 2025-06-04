import afficheur.Ecran;
import afficheur.Afficheur;

import java.awt.*;
import java.util.ArrayList;

/** Construire le schéma proposé dans le sujet de TP avec des points,
 * des points nommés
 * et des segments.
 *
 * @author	Xavier Crégut
 * @version	$Revision: 1.7 $
 */
public class ExempleSchemaGroupe {

    /** Construire le schéma et le manipuler.
     * Le schéma est affiché.
     * Ensuite, il est translaté et affiché de nouveau.
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args)
    {
        // Créer les trois segments
        Point p1 = new PointNomme(3, 2, "A");
        Point p2 = new PointNomme(6, 9, "S");
        Point p3 = new Point(11, 4);
        Segment s12 = new Segment(p1, p2);
        Segment s23 = new Segment(p2, p3);
        Segment s31 = new Segment(p3, p1);

        // Créer le barycentre
        double sx = p1.getX() + p2.getX() + p3.getX();
        double sy = p1.getY() + p2.getY() + p3.getY();
        Point barycentre = new PointNomme(sx / 3, sy / 3, "C");

        // Définir le schéma (vide)
        Groupe schema = new Groupe(Color.GREEN);	// le schéma
        int nb = 0;		// Le nombre d'éléments dans le schéma

        // Peupler le schéma
        schema.add(s12);
        nb++;
        schema.add(s23);
        nb++;
        schema.add(s31);
        nb++;
        schema.add(barycentre);
        nb++;

        // Afficher le schéma
        System.out.println("Le schéma est composé de : ");
        schema.afficher();

        // Créer l'écran d'affichage
        Ecran ecran =
                new Ecran(
                        "ExempleSchemaTab",
                        600,
                        400,
                        20
                );
        ecran.dessinerAxes();

        // Dessiner le schéma sur l'écran graphique
        for (int i = 0; i < nb; i++) {
            schema.get(i).dessiner(ecran);
        }

        // Translater le schéma
        System.out.println("Translater le schéma de (4, -3) : ");
        schema.translater(4, -3);

        // Afficher le schéma
        System.out.println("Le schéma est composé de : ");
        schema.afficher();

        // Dessiner le schéma sur l'écran graphique
        for (int i = 0; i < nb; i++) {
            schema.get(i).dessiner(ecran);
        }

        // Forcer l'affichage du schéma (au cas où...)
        ecran.rafraichir();



        // Définir le schéma (vide)
        Groupe schema2 = new Groupe(Color.RED);	// le schéma
        int nb2 = 0;		// Le nombre d'éléments dans le schéma

        // Peupler le schéma
        schema2.add(s12);
        nb2++;
        schema2.add(s23);
        nb2++;
        schema2.add(s31);
        nb2++;
        schema2.add(barycentre);
        nb2++;

        // Dessiner le schéma sur l'écran graphique
        for (int i = 0; i < nb2; i++) {
            schema2.get(i).dessiner(ecran);
        }

        // Translater le schéma
        System.out.println("Translater le schéma de (4, -3) : ");
        schema2.translater(5, -8);

        // Dessiner le schéma sur l'écran graphique
        for (int i = 0; i < nb2; i++) {
            schema2.get(i).dessiner(ecran);
        }
    }
}
