import java.awt.Color;

/**
 * <p>
 * Un cercle est une courbe plane fermée constituée des points situés à égale distance
 * d’un point nommé centre. La valeur de cette distance est appelée rayon du cercle.
 * On appelle diamètre la distance entre deux points diamétralement opposés. La valeur
 * du diamètre est donc le double de la valeur du rayon.
 * </p>
 * Les exigences sont les suivantes :
 * <p>
 *      - On peut translater un cercle en précisant un déplacement suivant l’axe des X et
 *        un déplacement suivant l’axe des Y
 * </p>
 * <p>
 *      - On peut obtenir le centre d’un cercle.
 * </p>
 * <p>
 *      - On peut obtenir le rayon d’un cercle.
 * </p>
 * <p>
 *      - On peut obtenir le diamètre d’un cercle.
 * </p>
 *      - On peut savoir si un point est à l’intérieur (au sens large) d’un cercle.
 * <p>
 *      - Un cercle est un Mesurable2D (interface Mesurable2D). À ce titre, on
 *        peut obtenir son périmètre et son aire (en fait, il s’agit de l’aire de la
 *        surface délimitée par le cercle).
 *        Le périmètre d’un cercle est donnée par la formule 2πR où R représente le rayon
 *        du cercle. L’aire est πR2.
 * </p>
 * <p>
 *      - Le cercle possède une couleur qui est utilisée pour dessiner sa circonférence.
 * </p>
 * <p>
 *      - On peut obtenir la couleur d’un cercle.
 * </p>
 * <p>
 *      - On peut changer la couleur d’un cercle.
 * </p>
 * <p>
 *      - On peut construire un cercle à partir d’un point qui désigne son centre et d’un
 *        réel correspondant à la valeur
 *        de son rayon. Sa couleur est considérée comme étant le bleu.
 * </p>
 * <p>
 *      - On peut construire un cercle à partir de deux points diamétralement opposés.
 *        Sa couleur est considérée comme étant le bleu.
 *        Par exemple, le cercle C2 est construit à partir des deux points C et D.
 * </p>
 * <p>
 *      - On peut construire un cercle à partir de deux points diamétralement opposés et
 *        de sa couleur.
 * </p>
 * <p>
 *      - Une méthode de classe creerCercle(Point, Point) permet de créer un cercle à
 *        partir de deux points, le premier correspond au centre du cercle et le deuxième
 *        est un point du cercle (de sa circonférence).
 *        Ces deux points forment donc un rayon du cercle. Le cercle est bleu.
 * </p>
 * <p>
 *      - Lorsqu’un cercle est affiché sur le terminal, il est affiché sous la forme
 *        suivante Cr@(a, b) où r est la valeur du rayon et (a, b) le centre du cercle.
 * </p>
 * <p>
 *      - On peut changer le rayon du cercle.
 * </p>
 * <p>
 *      - On peut changer le diamètre du cercle.
 * </p>
 * <p>
 *      - On ne doit pas pouvoir changer les caractéristiques d’un cercle sans passer par
 *        les opérations de modification que la classe propose.
 * </p>
 * <p>
 *      - La classe SujetCercleTest est une classe de test qui précise les exigences
 *        données ici. La classe Cercle doit donc réussir ces tests.
 * </p>
 *
 * @author Quentin Pointeau <quentin.pointeau@etu.inp-n7.fr>
 */
public class Cercle implements Mesurable2D {

    /** Constante mathématique PI. */
    public static final double PI = Math.PI;

    /** Centre du cercle. */
    private final Point centre;

    /** Rayon du cercle. */
    private double rayon;

    /** Couleur du cercle. */
    private Color couleur;


    /** Construire un cercle à partir de son centre et de son rayon.
     * @param centre    centre du cercle
     * @param rayon     rayon du cercle
     */
    public Cercle(Point centre, double rayon) {
        assert centre != null : "Le centre doit être non nul.";
        assert rayon > 0 : "Le rayon doit être strictement positif.";

        this.couleur = Color.blue;
        this.rayon = rayon;
        this.centre = new Point(centre.getX(), centre.getY());
    }

    /** Construire un cercle à partir de deux points diamétralement opposés.
     * @param point1    point diamétralement opposé à point2
     * @param point2    point diamétralement opposé à point1
     */
    public Cercle(Point point1, Point point2) {
        this(point1, point2, Color.BLUE);
    }

    /** Construire un cercle à partir de deux points diamétralement opposés et de sa
     * couleur.
     * @param point1    point diamétralement opposé à point2
     * @param point2    point diamétralement opposé à point1
     * @param couleur   couleur du cercle
     */
    public Cercle(Point point1, Point point2, Color couleur) {
        assert point1 != null :
                "Les points diamétralement opposés ne doivent pas être nuls.";
        assert point2 != null :
                "Les points diamétralement opposés ne doivent pas être nuls.";
        assert point1.distance(point2) > 0 :
                "Le rayon doit être strictement positif.";
        assert couleur != null :
                "La couleur doit être non nulle.";

        this.couleur = couleur;
        this.rayon = point1.distance(point2)/2;
        this.centre =
                new Point(
                        (point1.getX() + point2.getX()) / 2,
                        (point1.getY() + point2.getY()) / 2
                );
    }

    /** Construire un cercle à partir de son centre et d'un point de sa circonférence.
     * @param centre                centre du cercle
     * @param pointCirconference    point appartenant à la circonférence du cercle
     * @return                      nouveau cercle créé à partir de centre et de
     *                              pointCirconference
     */
    public static Cercle creerCercle(Point centre, Point pointCirconference) {
        assert centre != null :
                "Le centre doit être non nul.";
        assert pointCirconference != null :
                "Le point situé sur la circonférence du cercle doit être non nul.";
        assert centre.distance(pointCirconference) > 0 :
                "Le rayon doit être strictement positif.";

        Cercle cercle = new Cercle(centre, centre.distance(pointCirconference));
        cercle.setCouleur(Color.blue);
        return cercle;
    }

    /** Obtenir le centre d'un cercle.
     * @return centre du cercle
     */
    public Point getCentre() {
        return new Point(centre.getX(), centre.getY());
    }

    /** Obtenir le rayon d'un cercle.
     * @return rayon du cercle
     */
    public double getRayon() {
        return this.rayon;
    }

    /** Obtenir le diamètre d'un cercle.
     * @return diamètre du cercle
     */
    public double getDiametre() {
        return this.rayon * 2;
    }

    /** Obtenir le périmètre d'un cercle.
     * @return  périmètre du cercle
     */
    public double perimetre() {
        return 2 * PI * this.rayon;
    }

    /** Obtenir l'aire d'un cercle.
     * @return  l'aire du cercle
     */
    public double aire() {
        return PI * this.rayon * this.rayon;
    }

    /** Obtenir la couleur d'un cercle.
     * @return  la couleur du cercle
     */
    public Color getCouleur() {
        return this.couleur;
    }

    /** Changer le rayon d'un cercle.
     * @param nouveauRayon      nouveau rayon du cercle
     */
    public void setRayon(double nouveauRayon) {
        assert nouveauRayon > 0 :
                "Le nouveau rayon doit être strictement positif.";

        this.rayon = nouveauRayon;
    }

    /** Changer le diamètre d'un cercle.
     * @param nouveauDiametre       nouveau diamètre du cercle
     */
    public void setDiametre(double nouveauDiametre) {
        assert nouveauDiametre > 0 :
                "Le nouveau diamètre doit être strictement positif.";

        this.rayon = nouveauDiametre / 2;
    }

    /** Changer la couleur d'un cercle.
     * @param nouvelleCouleur       nouvelle couleur du cercle
     */
    public void setCouleur(Color nouvelleCouleur) {
        assert nouvelleCouleur != null :
                "La nouvelle couleur doit être non nulle.";

        this.couleur = nouvelleCouleur;
    }

    /** Translater un cercle selon un déplacement sur X et Y.
     * @param dx    déplacement sur X
     * @param dy    déplacement sur Y
     */
    public void translater(double dx, double dy) {
        this.centre.translater(dx, dy);
    }

    /** Teste si un point est à l'intérieur (au sens large) d'un cercle.
     * @param point     Point dont on souhaite savoir s'il est à l'intérieur du cercle
     * @return          True si point est à l'intérieur du cercle, False sinon
     */
    public boolean contient(Point point) {
        assert point != null :
                "Le point dont on teste l'appartenance doit être non nul.";

        return this.centre.distance(point) <= this.rayon;
    }

    /** Convertit un objet de la classe Cercle en une chaîne de caractères.
     * @return      chaîne de caractère associée à l'object Cercle
     */
    public String toString() {
        return "C" + this.rayon + "@" + this.centre.toString();
    }

    /** Afficher le cercle. */
    public void afficher() {
        System.out.print(this);
    }

}
