package com.libgdx.roguelike;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;

public class CombatMonstre implements Screen {

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

    private Game game; // Référence au jeu principal
    ArrayList<Missile> missiles;



    @Override
    public void show() {
        // Creation du joueur avec sa photo.*/
        player = new Texture("PersonnageCombat.png");
        /**Creation de l'arbre avec sa photo.*/
        monstre = new Texture("MonstreCombat.png");
        monstre2 = new Texture("monstre.png");
        PV5 = new Texture("PV5.jpg");
        PV4 = new Texture("PV4.jpg");
        PV3 = new Texture("PV3.jpg");
        PV2 = new Texture("PV2.jpg");
        PV1 = new Texture("PV1.jpg");
        PV0 = new Texture("PV0.jpg");
        missile_rect = new Rectangle(Missile.x, Missile.y, 100, 100);
        monstre_rect = new Rectangle(monstreX,monstreY, monstre.getWidth(), monstre.getHeight());
        stage = new Stage();
        missiles = new ArrayList<Missile>();
        /**Creation des rectangles autour des images.*/
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta)  {
        Gdx.gl.glClearColor(1,1,1,0);
        ScreenUtils.clear(1,1,1,0);
        batch.begin();
        stage.draw();

        /**Commandes pour dessiner le joueur et l'arbre à l'écran.*/
        batch.draw(player, joueurX, joueurY);
        if(PV == 0){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Victoire());

        }else{
            batch.draw(monstre, monstreX,monstreY);
        }
        switch (PV) {
            case 5 :
                batch.draw(PV5, 450, 1100);
                break;
            case 4 :
                batch.draw(PV4,1100, 800);
                break;
            case 3 :
                batch.draw(PV3, 1100, 800);
                break;
            case 2 :
                batch.draw(PV2, 1100, 800);
                break;
            case 1 :
                batch.draw(PV1, 1100, 800);
                break;
            default:
                batch.draw(PV0, 1100, 800);
                break;

        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            missiles.add(new Missile(joueurX+ player.getWidth(), joueurY + joueurY/3));


        }
        ArrayList<Missile> missilesAEnlever = new ArrayList<Missile>();
        for(Missile missile : missiles){
            missile.maj(delta);
            if(missile.enlever) {
                missilesAEnlever.add(missile);
                if(Missile.x - monstreX < 200){
                    PV = PV - 1;
                }else{

                }
            }
        }
        missiles.removeAll(missilesAEnlever);

        for(Missile missile : missiles) {
            missile.render(batch);
        }



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