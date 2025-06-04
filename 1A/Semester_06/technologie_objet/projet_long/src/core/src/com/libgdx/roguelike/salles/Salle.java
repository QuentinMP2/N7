package com.libgdx.roguelike.salles;

import com.libgdx.roguelike.Case;
import com.libgdx.roguelike.Portes.Porte;

import java.util.ArrayList;


public interface Salle {

    /** Hauteur d'une salle en pixel. */
    int HAUTEUR = 135; // UNIQUEMENT UTILE POUR L'AFFICHAGE TEST

    /** Longueur d'une salle en pixel. */
    int LONGUEUR = 240; // UNIQUEMENT UTILE POUR L'AFFICHAGE TEST

    /* Abscisse maximum dans le référentiel de la salle */
    int ABSCISSE_MAX = 1920;

    int ORDONNEE_MAX = 1080;

    // TAILLE RÉELLE D'UNE SALLE À L'ÉCRAN DÉFINIE PAR LA TAILLE DE LA FENETRE DE JEU
    // I.E. 1920 x 1080

    /** Nombre maximum de portes dans la salle. */
    int NB_PORTES = 4;

    /** Obtenir les portes d'une salle.
     * @return liste des portes de la salle
     */
    ArrayList<Porte> getPortes();

    /** Obtenir la case d'une salle
     * @return la case d'une salle
     */
    Case getCase();

    /**
     * Obtenir les coordonnées d'une porte de la salle dans un souci
     * d'affichage sur "la carte test" pour vérifier la génération procédurale.
     * Cette méthode pourra également concourir plus tard à l'affichage d'une carte pour le joueur.
     * @param porte la porte dont on veut connaître la coordonnée pour pouvoir l'afficher
     * @return les coordonnées de la porte afin de l'afficher sur l'interface graphique de libGDX
     */
    Case getCoordonneesPorte(Porte porte);

    /**
     * Obtenir les coordonnées relatives d'une porte du point de vue d'une salle (référentiel de la salle).
     * @param porte port dont on veut connaitre les coordonnées.
     * @return les coordonnées où afficher la porte indiquée sur la fenêtre de jeu.
     */
    Case getCoordonneesRelativesPorte(Porte porte);

    /**
     * Ajouter une porte à une salle.
     * @param caseSuivante la case sur laquelle va donner la porte.
     * @param salleSuivante la salle située derrière la porte.
     */
    void ajouterPorte(Case caseSuivante, Salle salleSuivante);

    /**
     * Obtenir les coordonnées relatives du téléporteur au centre de la salle étage suivant.
     * N.B. : le code de cette méthode est implanté uniquement dans la salle "SalleEtageSuivant".
     * @return les coordonnées relatives du téléporteur au centre de la salle étage suivant.
     */
    Case getCoordonneesPortailSalleSuivante();

    //Texture getBackground();
}
