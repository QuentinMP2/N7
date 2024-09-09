package editeur.commande;

import editeur.Ligne;
import menu.Menu;

/** Accéder à un sous-menu.
 * @author Quentin Pointeau
 */
public class CommandeSousMenu extends CommandeLigne {

    /** Le sous-menu. */
    private final Menu sousMenu;

    public CommandeSousMenu(Menu sousMenu, Ligne ligne) {
        super(ligne);
        this.sousMenu = sousMenu;
    }

    @Override
    public void executer() {
        do {
            // Afficher la ligne
            System.out.println();
            ligne.afficher();
            System.out.println();

            // Afficher le menu
            this.sousMenu.afficher();

            // Sélectionner une entrée dans le menu
            this.sousMenu.selectionner();

            // Valider l'entrée sélectionnée
            sousMenu.valider();

        } while (! sousMenu.estQuitte());
    }

    @Override
    public boolean estExecutable() {
        return true;
    }
}
