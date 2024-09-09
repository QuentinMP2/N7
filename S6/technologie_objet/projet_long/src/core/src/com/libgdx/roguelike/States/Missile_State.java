package com.libgdx.roguelike.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Missile_State {

    /** Vitesse de déplacement du Missile_State. */
    public static final int VITESSE = 1000;

    /** Position par défaut du Missile_State. */

    /** Texture du Missile_State. */
    private static Texture texture;

    /** Coordonnées du Missile_State. */
    static float x, y;

    public boolean enlever = false;

    /** Construire un Missile_State.
     *
     *
     */
    public Missile_State(float abscisse,float ordonnee) {
        x = abscisse;
        y = ordonnee;


        if (texture == null) {
            texture = new Texture("Missile.jpg");
        }
    }

    /** Mise à jour de la position du Missile_State.
     *
     * @param deltaTps delta de temps
     */
    public void maj(float deltaTps) {
        x += VITESSE * deltaTps;
        if (x > Gdx.graphics.getHeight())
            enlever = true;

    }

    /** Dessiner le Missile_State à l'écran.
     *
     * @param batch écran
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}



