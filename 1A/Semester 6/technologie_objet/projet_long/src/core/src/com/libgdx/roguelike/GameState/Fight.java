package com.libgdx.roguelike;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.States.GameState;
import com.libgdx.roguelike.managers.GameStateManager;

public class Fight extends GameState {

    /** Distance minimale pour les coordonnées à partir de laquelle le combat se déclenche. */
    static final float DIST_MIN = 70f;

    /** L'abscisse du joueur. */
    float joueurX = 50;

    /** L'ordonnée du joueur. */
    float joueurY = 300;

    /** L'abscisse du monstre. */
    float monstreX = 1100;

    /** L'ordonnée du monstre. */
    float monstreY = 300;

    int PV = 5;

    Stage stage;
    SpriteBatch batch;

    /** L'image qui va se deplacer. */
    Texture player;

    /** Les obstacles comme l'arbre par exemple. */
    Texture monstre;
    Texture PV5;
    Texture PV4;
    Texture PV3;
    Texture PV2;
    Texture PV1;
    Texture PV0;
    Texture monstre2;
    Rectangle missile_rect;
    Rectangle monstre_rect;

    int width = 700;
    int height = 500;

    ArrayList<Missile> missiles;

    public Fight(GameStateManager gsm) {
        super(gsm);
        init();
    }

    private void init() {
        // Creation du joueur avec sa photo.
        player = new Texture("still.png");
        // Creation du monstre avec sa photo.
        monstre = new Texture("monstre2.png");
        monstre2 = new Texture("monstre.png");
        PV5 = new Texture("PV5.jpg");
        PV4 = new Texture("PV4.jpg");
        PV3 = new Texture("PV3.jpg");
        PV2 = new Texture("PV2.jpg");
        PV1 = new Texture("PV1.jpg");
        PV0 = new Texture("PV0.jpg");
        missile_rect = new Rectangle(Missile.x, Missile.y, 100, 100);
        monstre_rect = new Rectangle(monstreX, monstreY, monstre.getWidth(), monstre.getHeight());
        stage = new Stage();
        missiles = new ArrayList<Missile>();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            missiles.add(new Missile(joueurY + joueurY / 3));
            if (PV > 0) {
                PV = PV - 1;
            }
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        for (Missile missile : missiles) {
            missile.maj(delta);
        }
        missiles.removeIf(missile -> missile.enlever);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        batch.begin();
        stage.draw();
        // Commandes pour dessiner le joueur et l'arbre à l'écran.
        batch.draw(player, joueurX, joueurY, 300, 400);
        if (PV == 0) {
            batch.draw(monstre, monstreX, monstreY, 300, 400);
        } else {
            batch.draw(monstre2, monstreX, monstreY, 300, 400);
        }

        switch (PV) {
            case 5:
                batch.draw(PV5, 450, 800);
                break;
            case 4:
                batch.draw(PV4, 450, 800);
                break;
            case 3:
                batch.draw(PV3, 450, 800);
                break;
            case 2:
                batch.draw(PV2, 450, 800);
                break;
            case 1:
                batch.draw(PV1, 450, 800);
                break;
            default:
                batch.draw(PV0, 450, 800);
                break;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            missiles.add(new Missile(joueurY + joueurY / 3));
            if (PV > 0) {
                PV = PV - 1;
            }
        }
        ArrayList<Missile> missilesAEnlever = new ArrayList<Missile>();
        for (Missile missile : missiles) {
            missile.maj(delta);
            if (missile.enlever) {
                missilesAEnlever.add(missile);
            }
        }
        missiles.removeAll(missilesAEnlever);

        for (Missile missile : missiles) {
            missile.render(batch);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Gdx.graphics.setWindowedMode(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        monstre.dispose();
        monstre2.dispose();
        PV5.dispose();
        PV4.dispose();
        PV3.dispose();
        PV2.dispose();
        PV1.dispose();
        PV0.dispose();
    }
}
