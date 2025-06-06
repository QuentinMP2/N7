import java.awt.Color;

/** Un segment est défini pas ses deux points qui constituent ses
 * extrémités.  Un segment peut être affiché et translaté.
 * Le segment peut être dessiné sur un afficheur.
 *
 * @author	Xavier Crégut
 * @version	1.9
 */
public class Segment extends ObjetGeometrique {

	private Point extremite1;
	private Point extremite2;

	/**  Construire un Segment à partir de ses deux points extrémités.
	 *  @param ext1	le premier point extrémité
	 *  @param ext2	le deuxième point extrémité
	 */
	public Segment(Point ext1, Point ext2) {
        super(Color.GREEN);
        this.extremite1 = ext1;
		this.extremite2 = ext2;
	}

   /** Translater le segment.
	* @param dx déplacement suivant l'axe des X
	* @param dy déplacement suivant l'axe des Y
	*/
	public void translater(double dx, double dy) {
		extremite1.translater(dx, dy);
		extremite2.translater(dx, dy);
	}

	/** Dessiner le segment sur l'afficheur.
	 * @param afficheur l'afficheur à utiliser
	 */
	public void dessiner(afficheur.Afficheur afficheur) {
		afficheur.dessinerLigne(this.extremite1.getX(), this.extremite1.getY(),
				this.extremite2.getX(), this.extremite2.getY(),
				this.getCouleur());

		// Dessiner les deux extrémités : est-ce bien normal ?
		this.extremite1.dessiner(afficheur);
		this.extremite2.dessiner(afficheur);
	}

	/** Obtenir la longueur du segment.
	 * @return la longueur du segment
	 */
	public double longueur() {
		return this.extremite1.distance(this.extremite2);
	}

	/** Afficher le segment.  Le segment est affiché sous la forme :
	 * <PRE>
	 *		[extremite1-extremite2]
	 * </PRE>
	 */
	public void afficher() {
		System.out.print("[");
		this.extremite1.afficher();
		System.out.print("-");
		this.extremite2.afficher();
		System.out.print("]");
	}

}
