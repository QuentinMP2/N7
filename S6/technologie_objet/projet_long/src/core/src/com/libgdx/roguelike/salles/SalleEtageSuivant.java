package com.libgdx.roguelike.salles;

import com.libgdx.roguelike.Case;
import com.libgdx.roguelike.Position;

public class SalleEtageSuivant extends SalleAdapter{

    /**
     * Construire la salle menant à l'étage suivant à partir d'une "position"
     * et de sa salle précédente.
     * @param position case libre où l'on veut créer la salle de fin
     *                 et la case d'une salle existante de sorte que l'on
     *                 puisse créer une porte entre la case origine
     *                 et la nouvelle case.
     * @param sallePrec salle correspondant à la case origine.
     */
    public SalleEtageSuivant(Position position, Salle sallePrec){
        super(position, sallePrec);
    }

    @Override
    public Case getCoordonneesPortailSalleSuivante(){
        return new Case(Salle.ABSCISSE_MAX/2, Salle.ORDONNEE_MAX/2);
    }

}
