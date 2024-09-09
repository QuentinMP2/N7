/**
 * OccupeException indique qu'un créneau est déjà occupé.
 */
public class OccupeException extends Exception {

    public OccupeException(String message) {
        super(message);
    }

}
