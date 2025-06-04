package com.libgdx.roguelike.salles;

import com.libgdx.roguelike.Case;
import com.libgdx.roguelike.Portes.*;
import com.libgdx.roguelike.Position;

import java.util.ArrayList;


/**
 * Classe abstraite servant à factoriser le code
 * commun aux réalisations de l'interface Salle
 */
public abstract class SalleAdapter implements Salle {

    /** Portes de la salle. */
    private final ArrayList<Porte> portes;

    /** Emplacement de la salle sur la carte. */
    private final Case emplacement;

    /**
     * Construire une salle à partir d'une position.
     * @param position position determiné dans l'algorithme de la génération procédurale.
     * @param salleSource salle située derrière la porte venant d'être construite
     *                    (du point de vue de la nouvelle salle).
     */
    public SalleAdapter(Position position, Salle salleSource) {
        this.portes = new ArrayList<>(Salle.NB_PORTES);
        this.emplacement = position.getCaseLibre();
        this.portes.add((getPorteDirection(position.getCaseSource(), salleSource)));
        //this.portes.add(new Porte(position.getCaseSource(), salleSource));
    }

    /**
     * Construire la salle de départ à partir d'une case.
     * @param caseSalle une case
     */
    public SalleAdapter(Case caseSalle) {
        this.portes = new ArrayList<>();
        this.emplacement = caseSalle;
    }

    @Override
    public ArrayList<Porte> getPortes() {
        return this.portes;
    }

    @Override
    public Case getCase() {
        return this.emplacement;
    }

    @Override
    public Case getCoordonneesPorte(Porte porte) {
        Case casePorte = null;
        // Determiner les coordonnées fonction du mur sur lequel
        // doit se trouver la porte

        // Cas où la porte est sur le mur "Ouest"
        if (porte instanceof PorteOuest) {
            int xPorte = emplacement.x - (Porte.LONGUEUR / 2);
            int yPorte = emplacement.y + (Salle.HAUTEUR - Porte.HAUTEUR) / 2;
            casePorte = new Case(xPorte, yPorte);
        } else if (porte instanceof PorteNord) {
            // Cas où la porte est sur le mur "Nord"
            int xPorte = emplacement.x + (Salle.LONGUEUR - Porte.LONGUEUR) / 2;
            int yPorte = emplacement.y + Salle.HAUTEUR - Porte.HAUTEUR / 2;
            casePorte = new Case(xPorte, yPorte);
        } else if (porte instanceof PorteEst) {
            // Cas où la parte est sur le mur "Est"
            int xPorte = emplacement.x + (Salle.LONGUEUR - Porte.LONGUEUR / 2);
            int yPorte = emplacement.y + (Salle.HAUTEUR - Porte.HAUTEUR) / 2;
            casePorte = new Case(xPorte, yPorte);
        } else if (porte instanceof PorteSud) {
            // Cas où la porte est sur le mur "Sud"
            int xPorte = emplacement.x + (Salle.LONGUEUR - Porte.LONGUEUR) / 2;
            int yPorte = emplacement.y - (Porte.HAUTEUR / 2);
            casePorte = new Case(xPorte, yPorte);
        }
        return  casePorte;
    }

    @Override
    public Case getCoordonneesRelativesPorte(Porte porte) {
        // On utilise la même structure que la fonction précédente
        // mais, la valeur finale des coordonnées n'est plus la même.
        Case casePorte = null;
        // Determiner les coordonnées fonction du mur sur lequel
        // doit se trouver la porte

        // Cas où la porte est sur le mur "Ouest"
        if (porte instanceof PorteOuest) {
            casePorte = new Case(0, Salle.ORDONNEE_MAX/2);
        } else if (porte instanceof PorteNord) {
            // Cas où la porte est sur le mur "Nord"
            casePorte = new Case(Salle.ABSCISSE_MAX/2, Salle.ORDONNEE_MAX-100);
        } else if (porte instanceof PorteEst) {
            // Cas où la parte est sur le mur "Est"
            casePorte = new Case(Salle.ABSCISSE_MAX-100, Salle.ORDONNEE_MAX/2);
        } else if (porte instanceof PorteSud) {
            // Cas où la porte est sur le mur "Sud"
            casePorte = new Case(Salle.ABSCISSE_MAX/2, 0);
        }
        return  casePorte;
    }

    @Override
    public void ajouterPorte(Case caseSuivante, Salle salleSuivante) {
        this.portes.add(getPorteDirection(caseSuivante, salleSuivante));
    }

    @Override
    public Case getCoordonneesPortailSalleSuivante(){
        return null;
    }

    /**
     * Obtenir la porte correspondant à la direction indiquée par la case adjacente.
     * @param caseAdjacente case adjacente correspondant à une salle qu'on veut relier à la salle courante.
     * @param salleAdjacente salle adjacente que l'on souhaite relier avec une porte à la salle courante.
     * @return la porte donnant dans la bonne direction du point de vue de la salle courante.
     */
    private Porte getPorteDirection(Case caseAdjacente, Salle salleAdjacente){
        Porte porteChoisie = null;
        // Calculer les coordonnées du "vecteur de translation"
        int deltaX = caseAdjacente.x - emplacement.x ;
        int deltaY = caseAdjacente.y - emplacement.y;

        // Cas où la porte est sur le mur "Ouest"
        if (deltaX == - Salle.LONGUEUR && deltaY == 0) {
            porteChoisie = new PorteOuest(caseAdjacente, salleAdjacente);
        } else if (deltaX == 0 && deltaY == Salle.HAUTEUR) {
            // Cas où la porte est sur le mur "Nord"
            porteChoisie = new PorteNord(caseAdjacente, salleAdjacente);
        } else if (deltaX == Salle.LONGUEUR && deltaY == 0) {
            // Cas où la parte est sur le mur "Est"
            porteChoisie = new PorteEst(caseAdjacente, salleAdjacente);
        } else if (deltaX == 0 && deltaY == - Salle.HAUTEUR) {
            // Cas où la porte est sur le mur "Sud"
            porteChoisie = new PorteSud(caseAdjacente, salleAdjacente);
        }
        return porteChoisie;
    }
}