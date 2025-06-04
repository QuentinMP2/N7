/** Classe modélisant une cellule qui contient un élément et la cellule suivante.
 * @author  Quentin Pointeau
 * @version 1.0
 */
public class Cellule<E> {

    /** Element de la cellule. */
    private E element;

    /** Cellule suivante. */
    private Cellule suivante;

    /** Construit une cellule à partir d'un élément et d'une cellule suivante.
     * @param x         élément de la cellule
     * @param suivante  cellule suivante
     */
    public Cellule(E x, Cellule suivante) {
        this.element = x;
        this.suivante = suivante;
    }

    /** Obtenir l'élément de la cellule.
     * @return  l'élément de la cellule
     */
    public E getElement() {
        return this.element;
    }

    /** Obtenir la cellule suivante.
     * @return  cellule suivante
     */
    public Cellule getSuivante() {
        return this.suivante;
    }

    /** Modifier l'élément de la cellule.
     * @param nouvelElement nouvelElement de la cellule
     */
    public void setElement(E nouvelElement) {
        this.element = nouvelElement;
    }

    /** Modifier la cellule suivante.
     * @param nouvelleSuivante   nouvelle cellule suivante
     */
    public void setSuivante(Cellule nouvelleSuivante) {
        assert suivante != null : "La nouvelle cellule ne doit pas être vide.";
        this.suivante = nouvelleSuivante;
    }
}
