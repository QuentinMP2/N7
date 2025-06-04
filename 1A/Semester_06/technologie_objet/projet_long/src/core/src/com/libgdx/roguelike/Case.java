package com.libgdx.roguelike;

import com.libgdx.roguelike.salles.Salle;

import java.util.ArrayList;
import java.util.List;

/** Classe définissant une case dans l'étage.
 * @author Quentin Pointeau
 * modifié par : Nicolaï Beuhorry
 */
public class Case {

    /** Limite maximale d'une case suivant l'axe des abscisses. */
    public final static int MAX_X = 1920 - Salle.LONGUEUR;

    /** Limite maximale d'une case suivant l'axe des ordonnées. */
    public final static int MAX_Y = 1080 - Salle.HAUTEUR;

    /** Coordonnée suivant l'axe des abscisses. */
    public final int x;

    /** Coordonnée suivant l'axe des ordonnées. */
    public final int y;

    /** Construire une case.
     * @param x  coordonnée sur l'axe des abscisses
     * @param y  coordonnée sur l'axe des ordonnées
     */
    // A COMPLETER AVEC DES EXCEPTIONS POUR PRENDRE EN COMPTE
    // QUE LES COORDONNEES NE SONT DEFINIES QUE PAR DES ENTIERS
    // COMPRIS DANS L'INTERVALLE ENTIER [0;500].
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Vérifie la validité d'une case.
     * N.B. : Une case est dite valide si son abscisse
     * est comprise dans l'intervalle entier [0, MAX_X]
     * et si son ordonnée est comprise dans l'intervalle entier
     * [0, Max_Y].
     * @return Vrai si la case est valide, Faux sinon.
     */
    public boolean estValide(ArrayList<Salle> sallesPresentes) {
        boolean bornesMaxValides = ((0 <= this.x) && (this.x < MAX_X))
                && ((0 <= this.y) && (this.y < MAX_Y));

        boolean conflitCaseOccupe = false;

        // Contrôler le conflit avec des salles déjà éxistante
        for (Salle salle : sallesPresentes) {
            if (isEqual(salle.getCase())) {
                conflitCaseOccupe = true;
            }
        }

        return bornesMaxValides && !conflitCaseOccupe;
    }

    /** Vérifie la validité d'une case.
     * N.B. : Une case est dite valide si son abscisse
     * est comprise dans l'intervalle entier [0, MAX_X]
     * et si son ordonnée est comprise dans l'intervalle entier
     * [0, Max_Y].
     * @return Vrai si la case est valide, Faux sinon.
     */
    public boolean estValide(ArrayList<Salle> sallesPresentes, List<Position> listePositions) {
        // Contrôler qu'il n'y ait pas de conflits dans les cases libres
        // de position
        boolean conflitPosition = false;
        for (Position position : listePositions) {
            if (isEqual(position.getCaseLibre())) {
                conflitPosition = true;
            }
        }

        return this.estValide(sallesPresentes) && !conflitPosition;
    }

    public boolean isEqual(Case autreCase) {
        return (this.x == autreCase.x) && (this.y == autreCase.y);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
