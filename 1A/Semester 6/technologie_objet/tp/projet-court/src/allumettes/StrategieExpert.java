package allumettes;

/** Classe représentant la statégie de jeu EXPERT.
 * @author  Quentin Pointeau
 */
public class StrategieExpert implements Strategie {

    @Override
    public int getPrise(Jeu jeu) {
        int allumettesRetirees = (jeu.getNombreAllumettes() - 1) % (Jeu.PRISE_MAX + 1);

        if (allumettesRetirees == 0) {
            allumettesRetirees++;
        }

        return allumettesRetirees;
    }

}
