/** Classe des ensembles implémentés par liste chaînée.
 * @author Quentin Pointeau
 */
public class EnsembleChaine<E> implements Ensemble<E> {

    /** Première cellule de la liste chaînée. */
    private Cellule liste;

    /** Construire un ensemble à partir d'une liste chaînée.
     * @param liste première cellule de la liste chaînée
     */
    public EnsembleChaine(Cellule liste) {
        this.liste = liste;
    }

    /** Obtenir la première cellule de la liste.
     * @return  la première cellule de la liste
     */
    public Cellule getListe() {
        return this.liste;
    }

    @Override
    public int cardinal() {
        int cardinal = 0;
        Cellule curseur = this.liste;

        while (curseur != null) {
            cardinal++;
            curseur = curseur.getSuivante();
        }
        return cardinal;
    }

    @Override
    public boolean estVide() {
        return liste == null;
    }

    @Override
    public boolean contient(E x) {
        Cellule curseur = this.liste;

        while (curseur != null) {
            if (curseur.getElement() == x) {
                return true;
            }
            curseur = curseur.getSuivante();
        }

        return false;
    }

    @Override
    public void ajouter(E x) {
        if (!this.contient(x)) {
            this.liste = new Cellule(x, this.liste);
        }
    }

    @Override
    public void supprimer(E x) {
        Cellule curseurCourant = this.liste;
        Cellule curseurPrecedent = this.liste;

        if (this.liste != null && this.liste.getElement() == x) {
            this.liste = this.liste.getSuivante();
        }
        else {
            while (curseurCourant != null) {
                if (curseurCourant.getElement() == x) {
                    curseurPrecedent.setSuivante(curseurCourant.getSuivante());
                    curseurCourant = null;
                }
                else {
                    curseurPrecedent = curseurCourant;
                    curseurCourant = curseurCourant.getSuivante();
                }
            }
        }
    }
}
