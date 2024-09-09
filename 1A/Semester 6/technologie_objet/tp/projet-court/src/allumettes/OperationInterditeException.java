package allumettes;

/** Classe de OperationInterditeException, levée lorsqu'un joueur triche.
 * @author  Quentin Pointeau
 */
public class OperationInterditeException extends RuntimeException {

    /** Construction d'une OperationInterditeException.
     * @param message   message d'exception
     */
    public OperationInterditeException(String message) {
        super(message);
    }

}
