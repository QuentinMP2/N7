package allumettes;

import org.junit.*;
import static org.junit.Assert.*;

public class StrategieRapideTest {

    /** Déclaration du jeu. */
    private Jeu jeu;

    /** Définition de la stratégie de jeu. */
    private StrategieRapide strategieRapide;

    @Before
    public void setUp() {
        jeu = new JeuReel(13);
        strategieRapide = new StrategieRapide();
    }

    /** Tester la prise si le nombre d'allumettes et strictement supérieur à PRISE_MAX. */
    @Test
    public void testerSuperieurPriseMax() {
        assertEquals(strategieRapide.getPrise(jeu), Jeu.PRISE_MAX);
    }

    /** Tester la prise si le nombre d'allumettes et égal à PRISE_MAX. */
    @Test
    public void testerEgalPriseMax() {
        jeu = new JeuReel(3);
        assertEquals(strategieRapide.getPrise(jeu), Jeu.PRISE_MAX);
    }

    /** Tester la prise si le nombre d'allumettes et strictement inférieur à PRISE_MAX. */
    @Test
    public void testerInferieurPriseMax() {
        jeu = new JeuReel(2);
        assertEquals(strategieRapide.getPrise(jeu), jeu.getNombreAllumettes());
        jeu = new JeuReel(1);
        assertEquals(strategieRapide.getPrise(jeu), jeu.getNombreAllumettes());
    }
}
