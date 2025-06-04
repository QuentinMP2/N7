package allumettes;

import java.util.NoSuchElementException;
import java.util.Scanner;

/** Classe représentant la statégie de jeu HUMAIN.
 * @author  Quentin Pointeau
 */
public class StrategieHumain implements Strategie {

    /** SCANNER des joueurs humain. */
    private static final Scanner SCANNER = new Scanner(System.in);

    /** Nom du joueur humain. */
    private final String nomJoueur;

    /** Construire une stratégie humaine à partir du nom d'un joueur.
     * @param nomJoueur nom du joueur
     */
    public StrategieHumain(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    @Override
    public int getPrise(Jeu jeu) throws CoupInvalideException {

        while (true) {
            try {
                System.out.print(nomJoueur + ", combien d'allumettes ? ");

                if (SCANNER.hasNext("triche") && jeu.getNombreAllumettes() > 1) {
                    jeu.retirer(1);
                    System.out.println("[Une allumette en moins, plus que "
                            + jeu.getNombreAllumettes() + ". Chut !]");
                    SCANNER.nextLine();

                } else if (SCANNER.hasNext("triche")
                        && jeu.getNombreAllumettes() <= 1) {
                    System.out.println("[Vous ne pouvez pas tricher maintenant ! "
                            + "Chut !]");
                    SCANNER.nextLine();

                } else {
                    int prise = SCANNER.nextInt();
                    SCANNER.nextLine();
                    return prise;
                }

            } catch (NoSuchElementException e) {
                SCANNER.nextLine();
                System.out.println("Vous devez donner un entier.");
            }
        }
    }
}
