/** Tester le polymorphisme (principe de substitution) et la liaison
 * dynamique.
 * @author	Xavier Crégut
 * @version	1.5
 */
public class TestPolymorphisme {

	/** Méthode principale */
	public static void main(String[] args) {
		// Créer et afficher un point p1
		Point p1 = new Point(3, 4);	// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car les types apparent et réel sont les mêmes.
		p1.translater(10,10);		// Quel est le translater exécuté ?
		// C'est le translater de la classe Point.
		System.out.print("p1 = "); p1.afficher (); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// p1 = (3, 4)

		// Créer et afficher un point nommé pn1
		PointNomme pn1 = new PointNomme (30, 40, "PN1");
										// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car les types apparent et réel sont les mêmes.
		pn1.translater (10,10);		// Quel est le translater exécuté ?
		// C'est le translater de la classe PointNomme.
		System.out.print ("pn1 = "); pn1.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// PN1 : (30, 40)

		// Définir une poignée sur un point
		Point q;

		// Attacher un point à q et l'afficher
		q = p1;				// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car les types apparent et réel sont les mêmes.
		System.out.println ("> q = p1;");
		System.out.print ("q = "); q.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// > p = p1;
		// q = (3, 4)

		// Attacher un point nommé à q et l'afficher
		q = pn1;			// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car PointNomme est un sous-type de Point.
		System.out.println ("> q = pn1;");
		System.out.print ("q = "); q.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// > q = pn1;
		// q = PN1 : (30, 40)

		// Définir une poignée sur un point nommé
		PointNomme qn;

		// Attacher un point à q et l'afficher
		// qn = p1;			// Est-ce autorisé ? Pourquoi ?
		// Ce n'est pas autorisé, car Point (type de p1) n'est pas un sous-type de
		// PointNomme (type de qn)
		// System.out.println ("> qn = p1;");
		// System.out.print ("qn = "); qn.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// Erreur de compilation :
		// java: incompatible types: Point cannot be converted to PointNomme

		// Attacher un point nommé à qn et l'afficher
		qn = pn1;			// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car qn et pn1 ont le même type.
		System.out.println ("> qn = pn1;");
		System.out.print ("qn = "); qn.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// > qn = pn1;
		// qn = PN1 : (30, 40)

		double d1 = p1.distance (pn1);	// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car pn1 est de type PointNomme, sous-type de Point
		// (type de p1)
		System.out.println ("distance = " + d1);

		double d2 = pn1.distance (p1);	// Est-ce autorisé ? Pourquoi ?
		// Ce n'est pas autorisé, car Point (type de p1) n'est pas un sous-type de
		// PointNomme (type de pn1)
		System.out.println ("distance = " + d2);

		double d3 = pn1.distance (pn1);	// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé, car on utilise la méthode distance définie dans PointNomme.
		System.out.println ("distance = " + d3);

		System.out.println ("> qn = q;");
		// qn = q;				// Est-ce autorisé ? Pourquoi ?
		// Ce n'est pas autorisé, car Point (type de q) n'est pas un sous-type de
		// PointNomme (type de qn).
		System.out.print ("qn = "); qn.afficher(); System.out.println ();
										// Qu'est-ce qui est affiché ?
		// Erreur à la compilation :
		// java: incompatible types: Point cannot be converted to PointNomme

		System.out.println ("> qn = (PointNomme) q;");
		qn = (PointNomme) q;		// Est-ce autorisé ? Pourquoi ?
		// C'est autorisé par transtypage car q est de type réel PointNomme.
		System.out.print ("qn = "); qn.afficher(); System.out.println ();

		// System.out.println ("> qn = (PointNomme) p1;");
		// qn = (PointNomme) p1;		// Est-ce autorisé ? Pourquoi ?
		// Ce n'est pas autorisé par transtypage car p1 est de type réel Point.
		// System.out.print ("qn = "); qn.afficher(); System.out.println ();
	}

}
