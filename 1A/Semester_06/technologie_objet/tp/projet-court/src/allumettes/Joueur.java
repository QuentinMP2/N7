package allumettes;

public class Joueur {

    private final String nom;

    private Strategie strategie;

    /** Construit un joueur avec un nom et une stratégie.
     * @param nom       nom du joueur
     * @param strategie strategie du joueur
     */
    public Joueur(String nom, Strategie strategie) {
        this.nom = nom;
        this.strategie = strategie;
    }

    /** Définir une nouvelle stratégie.
     * @param nouvelleStrategie la nouvelle stratégie
     */
    public void setStrategie(Strategie nouvelleStrategie) {
        this.strategie = nouvelleStrategie;
    }

    /** Obtenir le nom du joueur.
     * @return  le nom du joueur
     */
    public String getNom() {
        return this.nom;
    }

    public int getPrise(Jeu jeu) throws CoupInvalideException {
        return strategie.getPrise(jeu);
    }

}
