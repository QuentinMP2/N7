package com.libgdx.roguelike.Portes;

import com.libgdx.roguelike.Case;
import com.libgdx.roguelike.salles.Salle;

public abstract class Porte {

    /* Longueur de la porte. */
    public static final int LONGUEUR = 10;

    /* Hauteur de la porte. */
    public static final int HAUTEUR = 10;

    /** Case de la salle derrière la porte. */
    private final Case caseSalleSuivante;

    /** Salle derrière la porte */
    private final Salle salleSuivante;

    /**
     * Construire une porte à partir de l'emplacement de la salle
     * sur laquelle elle donne et du chemin de son modèle graphique
     * (affiche d'une porte verticale representé par un élement
     * vertical ou horizontal)
     * @param casePorte l'emplacement de la salle derrière la porte
     * (du point de vue de la salle courante)
     */
    public Porte(Case casePorte, Salle salleSuivante) {
        this.caseSalleSuivante = casePorte;
        this.salleSuivante = salleSuivante;
    }

    public Case getCaseSalleSuivante() {
        return this.caseSalleSuivante;
    }

    public Salle getSalleSuivante() {return  this.salleSuivante;}
}