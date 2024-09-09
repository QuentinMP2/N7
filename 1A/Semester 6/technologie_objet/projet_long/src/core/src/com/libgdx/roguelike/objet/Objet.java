package com.libgdx.roguelike.objet;

import com.libgdx.roguelike.personnage.Personnage;

public interface Objet {

    /** Permet au personnage d'utiliser l'objet dans son inventaire.
     * @param personnage sur lequel on utilise l'objet
     */
    void utiliser(Personnage personnage);
}
