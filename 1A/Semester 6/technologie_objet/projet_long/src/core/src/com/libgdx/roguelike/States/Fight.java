package com.libgdx.roguelike.States;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.managers.GameStateManager;

public class Fight extends GameState {

    /** Distance minimale pour les coordonnées à partir de laquelle le combat se déclenche. */
    static final float DIST_MIN = 80f;

    /** L'abscisse du joueur. */
    float joueurX = 350;

    /** L'ordonnée du joueur. */
    float joueurY = 200;

    /** L'abscisse du monstre. */
    float monstreX = 1100;

    /** L'ordonnée du monstre. */
    float monstreY = 200;

    int PV = 5;

    Stage stage;
    SpriteBatch batch;

    /** L'image qui va se déplacer. */
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
    Rectangle Missile_State_rect;
    Rectangle monstre_rect;

    ArrayList<Missile_State> Missile_States;

    public Fight(GameStateManager gsm) {
        super(gsm);
        init();
    }

    private void init() {
        // Création du joueur avec sa photo.
        player = new Texture("PersonnageCombat.png");
        // Création de l'arbre avec sa photo.
        monstre = new Texture("MonstreCombat.png");
        monstre2 = new Texture("monstre.png");
        PV5 = new Texture("PV5.jpg");
        PV4 = new Texture("PV4.jpg");
        PV3 = new Texture("PV3.jpg");
        PV2 = new Texture("PV2.jpg");
        PV1 = new Texture("PV1.jpg");
        PV0 = new Texture("PV0.jpg");
        Missile_State_rect = new Rectangle(Missile_State.x, Missile_State.y, 100, 100);
        monstre_rect = new Rectangle(monstreX, monstreY, monstre.getWidth(), monstre.getHeight());
        stage = new Stage();
        Missile_States = new ArrayList<Missile_State>();
        // Création des rectangles autour des images.
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Missile_States.add(new Missile_State(joueurX + player.getWidth(), joueurY + joueurY / 3));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();

        ArrayList<Missile_State> Missile_StatesAEnlever = new ArrayList<Missile_State>();
        for (Missile_State Missile_State : Missile_States) {
            Missile_State.maj(delta);
            if (Missile_State.enlever) {
                Missile_StatesAEnlever.add(Missile_State);
                if (Missile_State.x - monstreX < 200) {
                    PV = PV - 1;
                }
            }
        }
        Missile_States.removeAll(Missile_StatesAEnlever);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        batch.begin();
        stage.draw();

        // Commandes pour dessiner le joueur et l'arbre à l'écran.
        batch.draw(player, joueurX, joueurY);
        if (PV == 0) {
            gsm.push(new Victory(gsm));
        } else {
            batch.draw(monstre, monstreX, monstreY);
        }
        switch (PV) {
            case 5:
                batch.draw(PV5, 450, 1100);
                break;
            case 4:
                batch.draw(PV4, 1100, 800);
                break;
            case 3:
                batch.draw(PV3, 1100, 800);
                break;
            case 2:
                batch.draw(PV2, 1100, 800);
                break;
            case 1:
                batch.draw(PV1, 1100, 800);
                break;
            default:
                batch.draw(PV0, 1100, 800);
                break;
        }

        for (Missile_State Missile_State : Missile_States) {
            Missile_State.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        // Libération des ressources
        player.dispose();
        monstre.dispose();
        PV5.dispose();
        PV4.dispose();
        PV3.dispose();
        PV2.dispose();
        PV1.dispose();
        PV0.dispose();
        monstre2.dispose();
        batch.dispose();
        stage.dispose();
    }
}
