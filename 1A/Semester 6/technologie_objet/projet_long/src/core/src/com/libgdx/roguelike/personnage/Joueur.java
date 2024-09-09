package com.libgdx.roguelike.personnage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.libgdx.roguelike.objet.Objet;
import com.libgdx.roguelike.objet.ObjetIntrouvableException;

public class Joueur extends Personnage {

    /** Texture du joueur. */
    private static final Texture TEXTURE_JOUEUR =
            new Texture(Gdx.files.internal("still.png"));

    /** Inventaire du joueur. */
    private final Inventaire inventaire;

    /** Construire un joueur. */
    public Joueur() {
        super(100, TEXTURE_JOUEUR, 0, 0);
        this.inventaire = new Inventaire();
    }

    @Override
    public boolean estJoueur() {
        return true;
    }

    /** Ajouter un objet à l'inventaire du joueur.
     *
     * @param objet objet à ajouter
     */
    public void addInventaire(Objet objet) {
        inventaire.add(objet);
    }

    /** Retirer un objet de l'inventaire.
     *
     * @param objet objet à retirer
     */
    public void removeInventaire(Objet objet) {
        try {
            inventaire.remove(objet);
        } catch (ObjetIntrouvableException exception) {
            throw new RuntimeException();
        }
    }
}
