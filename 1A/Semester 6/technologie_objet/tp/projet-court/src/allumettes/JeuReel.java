package allumettes;

/** Classe représentant le jeu réel des allumettes.
 * @author  Quentin Pointeau
 */
public class JeuReel implements Jeu {

    /** Nombre d'allumettes restantes. */
    private int nombreAllumettes;

    /** Construit un jeu réel à partir d'un nombre initial d'allumettes.
     * @param nombreAllumettesInitial   nombre initial d'allumettes pour le jeu
     */
    public JeuReel(int nombreAllumettesInitial) {
        this.nombreAllumettes = nombreAllumettesInitial;
    }

    public JeuReel() {
        this.nombreAllumettes = 13;
    }

    @Override
    public int getNombreAllumettes() {
        return nombreAllumettes;
    }

    @Override
    public void retirer(int nbPrises) throws CoupInvalideException {
        if (nbPrises > this.nombreAllumettes) {
            throw new CoupInvalideException(nbPrises, "(> " + this.nombreAllumettes
                    + ")");

        } else if (nbPrises < 1) {
            throw new CoupInvalideException(nbPrises, "(< 1)");

        } else if (nbPrises > Jeu.PRISE_MAX) {
            throw new CoupInvalideException(nbPrises, "(> " + Jeu.PRISE_MAX + ")");

        } else {
            this.nombreAllumettes -= nbPrises;
        }
    }
}
