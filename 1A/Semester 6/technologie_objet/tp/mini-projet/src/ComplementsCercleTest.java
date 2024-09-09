import java.awt.Color;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Classe de tests supplémentaires pour la classe Cercle.
 * @author	Quentin Pointeau
 * @version	1.0
 */
public class ComplementsCercleTest {

    // Définitions de points
    private Point A, B;

    // Définitions de cercles
    private Cercle C1;

    @Before public void setUp() {
        // Construire les points
        A = new Point(1, 1);
        B = new Point(0, 0);

        // Construire les cercles
        C1 = new Cercle(A, B);
    }

    @Test (expected = AssertionError.class)
    public void testerRayonNulCercle() {
        Cercle cercle = new Cercle(A, A);
    }

    @Test (expected = AssertionError.class)
    public void testerRayonNulCreerCercle() {
        Cercle cercle = Cercle.creerCercle(A, A);
    }

    @Test (expected = AssertionError.class)
    public void testerCouleurNulle() {
        Cercle cercle = new Cercle(A, B, null);
    }

    @Test (expected = AssertionError.class)
    public void testerPointNul() {
        Cercle cercle = new Cercle(A, null, Color.GREEN);
    }

    @Test public void testerAfficher() {
        C1.afficher();
    }
}