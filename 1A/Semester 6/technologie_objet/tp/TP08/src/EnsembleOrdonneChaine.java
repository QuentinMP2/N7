import static java.lang.Integer.MAX_VALUE;

public class EnsembleOrdonneChaine<E> extends EnsembleChaine<E> implements EnsembleOrdonne<E> {

    /** Construire un ensemble ordonné à partir d'une liste chaînée.
     * @param liste première cellule de la liste chaînée
     */
    public EnsembleOrdonneChaine(Cellule liste) {
        super(liste);
    }

    @Override
    public E min() {
        E min = (E) this.getListe().getElement();
        Cellule<E> curseur = this.getListe();

        while (curseur != null) {
            if (curseur.getElement() < min) {
                min = curseur.getElement();
            }
            curseur = curseur.getSuivante();
        }

        return min;
    }
}
