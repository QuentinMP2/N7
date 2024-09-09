package com.libgdx.roguelike.objet;

import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.personnage.Personnage;

public class PotionVie implements Objet {

    @Override
    public void utiliser(Personnage personnage) {
        personnage.addPV(10);

        if (personnage instanceof Joueur) {
            Joueur joueur = (Joueur) personnage;
            joueur.removeInventaire(this);
        }
    }

}
