package allumettes;

/** Classe représentant la statégie de jeu RAPIDE.
 * @author  Quentin Pointeau
 */
public class StrategieRapide implements Strategie {

    @Override
    public int getPrise(Jeu jeu) {
        return Math.min(jeu.getNombreAllumettes(), Jeu.PRISE_MAX);
    }

}
