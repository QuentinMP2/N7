import java.util.ArrayList;
import java.util.List;

/** Quelques outils (méthodes) sur les listes.  */
public class Outils {

	/** Retourne vrai ssi tous les éléments de la collection respectent le critère. */
	public static <E> boolean tous(
			List<E> elements,
			Critere<? super E> critere)
	{
		boolean tousSatisfaits = true;

        for (E element : elements) {
            if (!critere.satisfaitSur(element)) {
                tousSatisfaits = false;
                break;
            }
        }

		return tousSatisfaits;
	}


	/** Ajouter dans resultat tous les éléments de la source
	 * qui satisfont le critère aGarder.
	 */
	public static <T1, T2 extends T1> void filtrer(
			List<T2> source,
			Critere<? super T2> aGarder,
			List<T1> resultat)
	{
		for (T2 element : source) {
			if (aGarder.satisfaitSur(element)) {
				resultat.add(element);
			}
		}
	}

}
