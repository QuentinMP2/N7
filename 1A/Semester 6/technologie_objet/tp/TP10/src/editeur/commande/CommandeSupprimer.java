package editeur.commande;

import editeur.Ligne;

/** Mettre le curseur à la première position de la ligne courante.
 * @author Quentin Pointeau
 * @version 1.0
 */
public class CommandeSupprimer extends CommandeLigne {

    /** Initialiser la ligne sur laquelle travaille cette commande.
     * @param l la ligne
     */
    //@ requires l != null;	// la ligne doit être définie
    public CommandeSupprimer(Ligne l) {
        super(l);
    }

    @Override
    public void executer() {
        ligne.supprimer();
    }

    @Override
    public boolean estExecutable() {
        return ligne.getLongueur() > 0;
    }
}
