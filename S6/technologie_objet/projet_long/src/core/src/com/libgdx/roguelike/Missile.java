package com.libgdx.roguelike;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Missile {

    /** Vitesse de déplacement du missile. */
    public static final int VITESSE = 1000;

    /** Position par défaut du missile. */

    /** Texture du missile. */
    private static Texture texture;

    /** Coordonnées du missile. */
    static float x, y;

    public boolean enlever = false;

    /** Construire un missile.
     *
     *
     */
    public Missile(float abscisse,float ordonnee) {
        x = abscisse;
        y = ordonnee;


        if (texture == null) {
            texture = new Texture("missile.jpg");
        }
    }

    /** Mise à jour de la position du missile.
     *
     * @param deltaTps delta de temps
     */
    public void maj(float deltaTps) {
        x += VITESSE * deltaTps;
        if (x > Gdx.graphics.getHeight())
            enlever = true;

    }

    /** Dessiner le missile à l'écran.
     *
     * @param batch écran
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}



