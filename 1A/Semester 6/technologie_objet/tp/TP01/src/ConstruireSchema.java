/** Construction du graphe illustré dans le sujet du TP01
 * @author Quentin Pointeau
 * @version 1.0
 */

public class ConstruireSchema {

    /** Méthode principale */
    public static void main(String[] args) {
        // Créer les trois points
        Point p1 = new Point(3,2);
        Point p2 = new Point(6,9);
        Point p3 = new Point(11,4);

        // Créer les trois segments
        Segment s1 = new Segment(p1, p2);
        Segment s2 = new Segment(p2, p3);
        Segment s3 = new Segment(p1, p3);

        // Définir le barycentre
        double Gx = (p1.getX() + p2.getX() + p3.getX())/3;
        double Gy = (p1.getY() + p2.getY() + p3.getY())/3;
        Point G = new Point(Gx, Gy);

        // Affichages
        System.out.println();

        System.out.print("p1 = ");
        p1.afficher();
        System.out.println();
        System.out.print("p2 = ");
        p2.afficher();
        System.out.println();
        System.out.print("p3 = ");
        p3.afficher();
        System.out.println();
        System.out.print("Le barycentre G = ");
        G.afficher();
        System.out.println();

        System.out.println();

        System.out.print("s1 = ");
        s1.afficher();
        System.out.print("s2 = ");
        s2.afficher();
        System.out.print("s3 = ");
        s3.afficher();
    }
}