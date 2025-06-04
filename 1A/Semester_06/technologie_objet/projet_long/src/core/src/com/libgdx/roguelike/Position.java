package com.libgdx.roguelike;

/**
 * Classe définissant une position dans le cadre
 * de la génération procédurale d'un étage.
 * Une position correspond à un couple de cases dont :
 * - une case 'libre' adjacente à une case 'source'
 * - une case 'source' qui correspond à une case 'occupee'
 * à partir de laquelle on a construit la nouvelle position.
 */
public class Position {

    /** Case source correspondant à la case occupée. */
    private final Case caseSource;

    /** Case libre adjacente à la case source. */
    private final Case caseLibre;

    /**
     * Construire une position à partir c'une case libre
     * et d'une case source.
     * @param caseLibre case 'libre' adjacente à une case source
     * @param caseSource case occupée par une salle
     */
    public Position(Case caseLibre, Case caseSource) {
        this.caseLibre = caseLibre;
        this.caseSource = caseSource;
    }

    public Case getCaseLibre() {
        return this.caseLibre;
    }

    public Case getCaseSource() {
        return this.caseSource;
    }
    
}