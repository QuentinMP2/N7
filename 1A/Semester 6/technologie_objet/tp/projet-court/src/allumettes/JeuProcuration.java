package allumettes;

/** Classe représentant le jeu par procuration (permet la triche).
 * @author  Quentin Pointeau
 */
public class JeuProcuration implements Jeu {

    /** Jeu réel sur lequel on effectue la procuration. */
    private final JeuReel jeuReel;

    /** Construit un jeu par procuration vers un jeu réel.
     * @param jeuReel le jeu réel sur lequel on s'appuie pour la procuration
     */
    public JeuProcuration(JeuReel jeuReel) {
        this.jeuReel = jeuReel;
    }

    @Override
    public int getNombreAllumettes() {
        return jeuReel.getNombreAllumettes();
    }

    @Override
    public void retirer(int nbPrises) throws CoupInvalideException {
        throw new OperationInterditeException("Opération interdite !");
    }
}
