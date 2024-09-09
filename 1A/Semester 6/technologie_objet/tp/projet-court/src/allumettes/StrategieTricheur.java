package allumettes;

/** Classe représentant la statégie de jeu TRICHEUR.
 * @author  Quentin Pointeau
 */
public class StrategieTricheur implements Strategie {

    @Override
    public int getPrise(Jeu jeu) throws CoupInvalideException {
        int nbAllumettesRetirees = 1;

        System.out.println("[Je triche...]");

        if (jeu.getNombreAllumettes() < Jeu.PRISE_MAX) {
            return nbAllumettesRetirees;
        }

        while (jeu.getNombreAllumettes() > 2) {
            jeu.retirer(1);
        }

        System.out.println("[Allumettes restantes : " + jeu.getNombreAllumettes() + "]");

        return nbAllumettesRetirees;
    }
}
