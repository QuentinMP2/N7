package allumettes;

public interface Strategie {

    /** Demander le nombre d'allumettes à retirer en fonction de la stratégie du joueur.
     * @param jeu   jeu courant
     * @return      le nombre d'allumettes à retirer
     */
    int getPrise(Jeu jeu) throws CoupInvalideException;
}
