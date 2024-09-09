package com.libgdx.roguelike.salles;

import com.libgdx.roguelike.Position;
import com.libgdx.roguelike.personnage.Monstre;

import java.util.Random;

/** Classe représentant une salle de type MONSTRE. */
public class SalleMonstre extends SalleAdapter {

    /** Générateur aléatoire. */
    private static final Random GENERATEUR = new Random();

    /** Monstre dans la salle. */
    private Monstre monstre;

    /**
     * Construire une salle monstre.
     * @param position case libre où l'on veut créer la salle de fin
     *                 et la case d'une salle existante de sorte que l'on
     *                 puisse créer une porte entre la case origine
     *                 et la nouvelle case.
     * @param sallePrec salle correspondant à la case origine.
     */
    public SalleMonstre(Position position, Salle sallePrec) {
        super(position, sallePrec);
        this.monstre = new Monstre(GENERATEUR.nextFloat(1600) + 100, GENERATEUR.nextFloat(750) + 100);
    }

    /** Obtenir le monstre de la salle.
     *
     * @return le monstre de la salle
     */
    public Monstre getMonstre() {
        return this.monstre;
    }

}
