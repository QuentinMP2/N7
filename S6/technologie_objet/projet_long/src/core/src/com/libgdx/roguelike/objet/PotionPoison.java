package com.libgdx.roguelike.objet;

import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.personnage.Personnage;


public class PotionPoison implements Objet {

    @Override
    public void utiliser(Personnage personnage) {
        personnage.removePV(10);

        if (personnage instanceof Joueur) {
            Joueur joueur = (Joueur) personnage;
            joueur.removeInventaire(this);
        }
    }
}
