package com.libgdx.roguelike.personnage;

import com.badlogic.gdx.graphics.Texture;

public abstract class Personnage {

    /** Nombre de points de vie maximum pour un personnage. */
    public final int PV_MAX;

    /** Coordonnées du personnage. */
    private float x, y;

    /* Nombre de points de vie actuel du personnage. */
    private int pv;

    /** Texture du personnage. */
    Texture texture;

    /** Construire un personnage.
     *
     * @param pvMax             nombre de PV maximum
     * @param texturePersonnage texture du personnage
     */
    protected Personnage(int pvMax, Texture texturePersonnage, float x, float y) {
        this.PV_MAX = pvMax;
        this.texture = texturePersonnage;
        this.x = x;
        this.y = y;
    }

    /** Obtenir l'abscisse du personnage.
     *
     * @return abscisse du personnage
     */
    public float getX() {
        return this.x;
    }

    /** Obtenir l'ordonnée du personnage.
     *
     * @return ordonnée du personnage
     */
    public float getY() {
        return this.y;
    }

    /** Définir l'abscisse du personnage.
     *
     * @param nouveauX nouvelle abscisse du personnage
     */
    public void setX(float nouveauX) {
        this.x = nouveauX;
    }

    /** Définir l'ordonnée du personnage.
     *
     * @param nouveauY nouvelle ordonnée du personnage
     */
    public void setY(float nouveauY) {
        this.y = nouveauY;
    }

    /** Ajouter des points de vie au joueur.
     * @param pv à ajouter
     */
    public void addPV(int pv) {
        if (pv > 0) {
            if ((pv + this.pv) < PV_MAX) {
                this.pv += pv;
            }
            if ((pv + this.pv) > PV_MAX) {
                this.pv = PV_MAX;
            }
        }
    }

    /** Retirer des points de vie au joueur.
     * @param pv à retirer
     */
    public void removePV(int pv) {
        if (pv > 0) {
            if ((this.pv - pv) > 0) {
                this.pv -= pv;
            }
            if ((this.pv - pv ) < 0) {
                this.pv = 0;
            }
        }
    }

    /** Savoir s'il s'agit d'un monstre ou d'un joueur.
     * @return true si le personnage est un joueur, false sinon
     */
    public abstract boolean estJoueur();

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture nouvelleTexture) {
        this.texture = nouvelleTexture;
    }

    /** Obtenir le nombre de points de vie actuel du joueur.
     * @return pv
     */
    public int getPV() {
        return this.pv;
    }

    /** Modifier le nombre de points de vie du joueur.
     * @param pv nouveau nmbre de points de vie du joueur
     */
    public void setPV(int pv) {
        if (pv > 0) {
            this.pv = pv;
        }
    }

}
