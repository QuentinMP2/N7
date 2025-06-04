import java.awt.Color;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Classe de test pour les exigences E12, E13, E14 de la classe Cercle.
 * @author	Quentin Pointeau
 * @version	1.1
 */
public class CercleTest {

    // Précision pour les comparaisons réelle
    public final static double EPSILON = 0.001;

    // Définitions de points
    private Point A, B;

    // Définitions de cercles
    private Cercle C1, C2;

    @Before public void setUp() {
        // Construire les points
        A = new Point(1, 1);
        B = new Point(0, 0);

        // Construire les cercles
        C1 = new Cercle(A, B);
        C2  = Cercle.creerCercle(A, B);
    }

    /** Vérifier si deux points ont les mêmes coordonnées.
     * @param p1 le premier point
     * @param p2 le deuxième point
     */
    static void memesCoordonnees(String message, Point p1, Point p2) {
        assertEquals(message + " (x)", p1.getX(), p2.getX(), EPSILON);
        assertEquals(message + " (y)", p1.getY(), p2.getY(), EPSILON);
    }

    @Test public void testerE12a() {
        memesCoordonnees(
                "E12 : Centre de C1 incorrect",
                new Point(0.5, 0.5),
                C1.getCentre()
        );
        assertEquals(
                "E12 : Rayon de C1 incorrect",
                Math.sqrt(2) / 2,
                C1.getRayon(),
                EPSILON
        );
        assertEquals(
                "E12 : Couleur de C1 incorrect",
                Color.blue,
                C1.getCouleur()
        );
    }

    @Test public void testerE12b() {
        memesCoordonnees(
                "E12 : Centre de C1 incorrect",
                new Point((A.getX() + B.getX()) / 2, (A.getY() + B.getY()) / 2),
                C1.getCentre()
        );
        assertEquals(
                "E12 : Rayon de C1 incorrect",
                Math.sqrt(2)/2,
                C1.getRayon(),
                EPSILON
        );
        assertEquals(
                "E12 : Couleur de C1 incorrect",
                Color.blue,
                C1.getCouleur()
        );
    }

    @Test public void testerE13() {
        C1.setCouleur(Color.cyan);
        memesCoordonnees(
                "E13 : Centre de C1 incorrect",
                new Point(0.5, 0.5),
                C1.getCentre()
        );
        assertEquals(
                "E13 : Rayon de C1 incorrect",
                Math.sqrt(2) / 2,
                C1.getRayon(),
                EPSILON
        );
        assertEquals(
                "E13 : Couleur de C1 incorrect",
                Color.cyan,
                C1.getCouleur()
        );
    }

    @Test public void testerE14() {
        memesCoordonnees(
                "E14 : Centre de C2 incorrect",
                A,
                C2.getCentre()
        );
        assertEquals(
                "E14 : Rayon de C2 incorrect",
                Math.sqrt(2),
                C2.getRayon(),
                EPSILON
        );
        assertEquals(
                "E14 : Couleur de C2 incorrect",
                Color.blue,
                C2.getCouleur()
        );
    }
}