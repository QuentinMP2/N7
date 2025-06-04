import afficheur.Afficheur;
import afficheur.Ecran;

import java.awt.Color;

/** Modélisation des objets géométriques.
 * @author Quentin Pointeau
 */
abstract public class ObjetGeometrique {

    //@ private invariant getCouleur() != null;
    //@ private invariant getCouleur() == couleur;	// invariant de liaison
    /** Couleur d'un objet géométrique. */
    private Color couleur;

    /** Construire un objet géométrique.
     * @param couleur   couleur de l'objet géométrique
     */
    public ObjetGeometrique(Color couleur) {
        assert couleur != null :
                "La couleur ne doit pas être nulle !";
        this.couleur = couleur;
    }

    /** Obtenir la couleur d'un objet géométrique.
     * @return  couleur de l'objet géométrique
     */
    public Color getCouleur() {
        return this.couleur;
    }

    /** Définir la nouvelle couleur d'un objet géométrique.
     * @param nouvelleCouleur   nouvelle couleur de l'objet géométrique
     */
    public void setCouleur(Color nouvelleCouleur) {
        assert nouvelleCouleur != null :
                "La nouvelle couleur ne doit pas être nulle !";
        this.couleur = nouvelleCouleur;
    }

    /** Afficher un objet géométrique. */
    public abstract void afficher();

    /** Translater un objet géométrique. */
    public abstract void translater(double dx, double dy);

    /** Dessiner un point géométrique.
     * @param ecran     type d'afficheur
     */
    public void dessiner(Afficheur ecran) {
    }
}
