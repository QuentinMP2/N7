package com.libgdx.roguelike.personnage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Monstre extends Personnage {

    /** Texture du joueur. */
    private static final Texture TEXTURE_MONSTRE =
            new Texture(Gdx.files.internal("monstre.png"));

    /** Construire un monstre.
     *
     * @param x abscisse du monstre
     * @param y ordonnée du monstre
     */
    public Monstre(float x, float y) {
        super(50, TEXTURE_MONSTRE, x, y);
    }

    @Override
    public boolean estJoueur() {
        return false;
    }

}
