/** Définition d'un ensemble ordonné. */
public interface EnsembleOrdonne<E> extends Ensemble<E> {

    /** Obtenir le minimum d'un ensemble ordonné.
     * @return  le minimum d'un ensemble ordonné
     */
    E min();

}
