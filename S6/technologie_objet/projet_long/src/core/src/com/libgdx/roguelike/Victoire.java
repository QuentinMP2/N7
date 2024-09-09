package com.libgdx.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class Victoire implements Screen {

        Stage stage;
        SpriteBatch batch;

        /** L'image qui va se deplacer. */
        Texture Victoire;


        @Override
        public void show() {
            // Creation du joueur avec sa photo.*/
            Victoire = new Texture("Victoire.png");

            stage = new Stage();

            Gdx.input.setInputProcessor(stage);
            batch = new SpriteBatch();
        }

        @Override
        public void render(float delta)  {
            Gdx.gl.glClearColor(1,1,1,0);
            ScreenUtils.clear(1,1,1,0);
            batch.begin();
            stage.draw();
            batch.draw(Victoire, 0, 0);



            batch.end();
            // resize(width,height);
        }

        @Override
        public void resize(int width, int height) {
            // Gdx.graphics.setWindowedMode(width, height);
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void hide() {
        }

        @Override
        public void dispose() {
        }
    }

