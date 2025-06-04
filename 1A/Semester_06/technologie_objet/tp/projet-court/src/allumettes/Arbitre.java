package allumettes;

import java.util.Objects;

public class Arbitre {

    /** Premier joueur de la partie. */
    private final Joueur joueur1;

    /** Deuxième joueur de la partie. */
    private final Joueur joueur2;

    /** Jeu par procuration si l'arbitre est MEFIANT. */
    private ModeArbitrage arbitrage;

    /** Construit un arbitre à partir des deux joueurs.
     * @param j1        premier joueur de la partie
     * @param j2        deuxième joueur de la partie
     */
    public Arbitre(Joueur j1, Joueur j2) {
        this(j1, j2, ModeArbitrage.MEFIANT);
    }

    /** Construit un arbitre à partir des deux joueurs et d'un mode d'arbitrage.
     * @param j1        premier joueur de la partie
     * @param j2        deuxième joueur de la partie
     * @param arbitrage mode d'arbitrage
     */
    public Arbitre(Joueur j1, Joueur j2, ModeArbitrage arbitrage) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.arbitrage = arbitrage;
    }

    /** Arbitre une partie.
     * @param jeu   la partie
     */
    public void arbitrer(Jeu jeu) {
        Joueur tourJoueur = this.joueur1;   // Joueur 1 qui joue initialement
        int nombreAllumettesRetirees;
        JeuProcuration jeuProcuration = null;

        if (arbitrage == ModeArbitrage.MEFIANT) {
            jeuProcuration = new JeuProcuration((JeuReel) jeu);
        }

        try {
            while (jeu.getNombreAllumettes() > 0) {
                try {
                    System.out.println("Allumettes restantes : "
                            + jeu.getNombreAllumettes());

                    nombreAllumettesRetirees =
                            arbitrage == ModeArbitrage.CONFIANT
                            ? tourJoueur.getPrise(jeu)
                            : tourJoueur.getPrise(jeuProcuration);

                    String messageRetirerAllumettes = tourJoueur.getNom() + " prend "
                            + nombreAllumettesRetirees;
                    if (nombreAllumettesRetirees <= 1) {
                        System.out.println(messageRetirerAllumettes + " allumette.");
                    } else {
                        System.out.println(messageRetirerAllumettes + " allumettes.");
                    }

                    jeu.retirer(nombreAllumettesRetirees);
                    System.out.println();

                    tourJoueur = tourJoueur == this.joueur1 ? this.joueur2 : this.joueur1;

                } catch (CoupInvalideException e) {
                    System.out.println("Impossible ! Nombre invalide : "
                            + e.getCoup() + " " + e.getProbleme() + "\n");
                }
            }

            Joueur joueurPerdu = (tourJoueur == joueur1) ? joueur2 : joueur1;
            System.out.println(joueurPerdu.getNom() + " perd !");
            System.out.println(tourJoueur.getNom() + " gagne !");

        } catch (OperationInterditeException e) {
            System.out.println("Abandon de la partie car " + tourJoueur.getNom()
                    + " triche !");
        }
    }
}
