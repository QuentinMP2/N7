import afficheur.Afficheur;

/** AfficheurTexte fournit un afficheur sous format textuel.
 * @author Quentin Pointeau
 */
public class AfficheurTexte implements Afficheur {

    /** AfficheurTexte founit un afficheur textuel.
     * @param description   la description
     */
    public AfficheurTexte(String description) {
        System.out.println(description);
    }

    /** Afficher un cercle.
     * @param x         abscisse du centre du cercle
     * @param y         ordonnée du centre du cercle
     * @param rayon     rayon du cercle
     * @param c         couleur du cercle
     */
    public void dessinerCercle(double x, double y, double rayon, java.awt.Color c) {
        System.out.println("Cercle {");
        System.out.println("    centre_x = " + x);
        System.out.println("    centre_y = " + y);
        System.out.println("    rayon = " + rayon);
        System.out.println("    couleur = " + c);
        System.out.println("}");
    }

    /** Afficher un segment entre deux points.
     * @param x1        abscisse du premier point
     * @param y1        ordonnée du premier point
     * @param x2        abscisse du second point
     * @param y2        ordonnée du second point
     * @param c         couleur du point
     */
    public void dessinerLigne(double x1, double y1, double x2, double y2, java.awt.Color c) {
        System.out.println("Ligne {");
        System.out.println("    x1 = " + x1);
        System.out.println("    y1 = " + y1);
        System.out.println("    x2 = " + x2);
        System.out.println("    y2 = " + y2);
        System.out.println("    couleur = " + c);
        System.out.println("}");
    }

    /** Afficher un point.
     * @param x     abscisse du point
     * @param y     ordonnée du point
     * @param c     couleur du point
     */
    public void dessinerPoint(double x, double y, java.awt.Color c) {
        System.out.println("Point {");
        System.out.println("    x = " + x);
        System.out.println("    y = " + y);
        System.out.println("    couleur = " + c);
        System.out.println("}");
    }

    /** Afficher un texte.
     * @param x         abscisse du texte
     * @param y         ordonnée du texte
     * @param texte     texte à afficher
     * @param c         couleur du texte
     */
    public void dessinerTexte(double x, double y, java.lang.String texte, java.awt.Color c) {
        System.out.println("Texte {");
        System.out.println("    x = " + x);
        System.out.println("    y = " + y);
        System.out.println("    valeur = " + texte);
        System.out.println("    couleur = " + c);
        System.out.println("}");
    }
}
