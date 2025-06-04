import java.awt.*;
import java.util.ArrayList;

/** Un groupe contient plusieurs éléments de même type. */
public class Groupe extends ObjetGeometrique {

    /** Liste d'éléments. */
    private ArrayList<ObjetGeometrique> liste;

    /** Construire une liste d'ObjetGeometrique
     * @param couleur   couleur des objets géométriques
     */
    public Groupe(Color couleur) {
        super(couleur);
        this.liste = new ArrayList<ObjetGeometrique>();
    }

    @Override
    public void afficher() {
        for (ObjetGeometrique objet : this.liste) {
            objet.afficher();
            System.out.println();
        }
    }

    @Override
    public void translater(double dx, double dy) {
        for (ObjetGeometrique objet : this.liste) {
            objet.translater(dx, dy);
        }
    }

    /** Ajouter un objet géométrique au groupe.
     * @param objet     objet à ajouter au groupe
     */
    public void add(ObjetGeometrique objet) {
        this.liste.add(objet);
    }

    /** Obtenir un objet géométrique.
     * @param indice    indice de l'emplacement de l'objet géométrique à récupérer
     * @return          l'objet géométrique à l'indice donné
     */
    public ObjetGeometrique get(int indice) {
        return this.liste.get(indice);
    }
}
