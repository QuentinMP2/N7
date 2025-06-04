package allumettes;

import java.util.Random;

/** Classe représentant la statégie de jeu NAIF.
 * @author  Quentin Pointeau
 */
public class StrategieNaif implements Strategie {

    @Override
    public int getPrise(Jeu jeu) {
        Random random = new Random();
        return 1 + random.nextInt(Math.min(jeu.getNombreAllumettes(), Jeu.PRISE_MAX));
    }

}
