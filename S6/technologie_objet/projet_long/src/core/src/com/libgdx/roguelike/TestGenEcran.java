package com.libgdx.roguelike;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.Portes.Porte;
import com.libgdx.roguelike.salles.Salle;
import com.libgdx.roguelike.salles.SalleDepart;
import com.libgdx.roguelike.salles.SalleEtageSuivant;
import com.libgdx.roguelike.salles.SalleMonstre;

import java.util.ArrayList;


public class TestGenEcran extends InputAdapter implements Screen {

    private JeuEcran jeuEcranSauvegarde;
    private Etage etageCourant;

    Stage stage;

    SpriteBatch batch;

    /** Textures des salles. */
    Texture salleMonstre, salleDepart, porteTexture;

    public TestGenEcran(JeuEcran jeuEcranASauvegarde, Etage etageCourant){
        this.jeuEcranSauvegarde = jeuEcranASauvegarde;
        this.etageCourant = etageCourant;
    }

    @Override
    public void show(){
        // Importer les textures
        salleMonstre = new Texture(Gdx.files.internal("salleMonstre.jpg"));
        salleDepart = new Texture(Gdx.files.internal("salleDepart.jpg"));
        porteTexture = new Texture(Gdx.files.internal("porte.jpg"));
        stage = new Stage();
        System.out.println("Nb salles : " + etageCourant.getSallesPresentes().size());

        batch = new SpriteBatch(); // Un SpriteBatch est un objet utilisé pour dessiner un grand nombre de sprites
        // en une seule opération de rendu. Plutôt que de dessiner chaque sprite individuellement, ce qui serait inefficace,
        // on peut utiliser un SpriteBatch pour regrouper les dessins et les envoyer à la carte graphique en une seule fois.
    }

    @Override
    public void render(float delta){
        //Cette commande a pour but de créer un écran et de rendre son fond blanc.
        Gdx.gl.glClearColor(1,1,1,0);
        ScreenUtils.clear(1,1,1,0);
        batch.begin();
        stage.draw();

        for (Salle salle : etageCourant.getSallesPresentes()) {
            if (salle instanceof SalleMonstre) {
                batch.draw(salleMonstre, salle.getCase().x, salle.getCase().y);
            } else if (salle instanceof SalleDepart || salle instanceof SalleEtageSuivant) {
                batch.draw(salleDepart, salle.getCase().x, salle.getCase().y);
            }
        }

        // On parcourt de nouveau toutes les salles pour bien afficher les portes
        // par-dessus l'image des salles
        for (Salle salle : etageCourant.getSallesPresentes()) {
            // Afficher les portes de chaque salle dans l'étage
            for (Porte porte : salle.getPortes()) {
                Case casePorte = salle.getCoordonneesPorte(porte);
                batch.draw(porteTexture, casePorte.x, casePorte.y);
            }
        }

        // Fonctionnalité pour quitter ce "mode carte" (qui était juste un test à la base lol)
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            navigateBackToGame();
        }

        batch.end();
    }

    private void navigateBackToGame(){
        ((Game) Gdx.app.getApplicationListener()).setScreen(jeuEcranSauvegarde);
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }
    @Override
    public void dispose(){

    }


}
