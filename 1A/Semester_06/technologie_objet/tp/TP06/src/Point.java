import java.awt.Color;

/** Point modélise un point géométrique dans un plan équipé d'un
 * repère cartésien. Un point peut être affiché et translaté.
 * Sa distance par rapport à un autre point peut être obtenue.
 * Le point peut être dessiné sur un afficheur.
 *
 * @author  Xavier Crégut <Prénom.Nom@enseeiht.fr>
 */
public class Point extends ObjetGeometrique {
	private double x;		// abscisse
	private double y;		// ordonnée

	/** Construire un point à partir de son abscisse et de son ordonnée.
	 * @param vx abscisse
	 * @param vy ordonnée
	 */
	public Point(double vx, double vy) {
		super(Color.GREEN);
		this.x = vx;
		this.y = vy;
	}

	/** Obtenir l'abscisse du point.
	 * @return abscisse du point
	 */
	//@ pure
	public double getX() {
		return this.x;
	}

	/** Obtenir l'ordonnée du point.
	 * @return ordonnée du point
	 */
	//@ pure
	public double getY() {
		return this.y;
	}

	/** Changer l'abscisse du point.
	  * @param vx nouvelle abscisse
	  */
	public void setX(double vx) {
		this.x = vx;
	}

	/** Changer l'ordonnée du point.
	  * @param vy nouvelle ordonnée
	  */
	public void setY(double vy) {
		this.y = vy;
	}

	/** Afficher le point. */
	public void afficher() {
		System.out.print("(" + this.x + ", " + this.y + ")");
	}

	/** Distance par rapport à un autre point.
	 * @param autre l'autre point
	 * @return distance entre this et autre
	 */
	//@ pure
	public double distance(Point autre) {
		return Math.sqrt(Math.pow(autre.x - this.x, 2)
					+ Math.pow(autre.y - this.y, 2));
	}

   /** Translater le point.
	* @param dx déplacement suivant l'axe des X
	* @param dy déplacement suivant l'axe des Y
	*/
   @Override
	public void translater(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}

	/** Dessiner le point sur l'afficheur.
	 * @param afficheur l'afficheur à utiliser
	 */
	public void dessiner(afficheur.Afficheur afficheur) {
		afficheur.dessinerPoint(this.getX(), this.getY(), this.getCouleur());
	}

}
