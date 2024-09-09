package com.libgdx.roguelike.States;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.Portes.Porte;
import com.libgdx.roguelike.salles.Salle;
import com.libgdx.roguelike.Etage;
import com.libgdx.roguelike.Case;

import com.libgdx.roguelike.salles.SalleDepart;
import com.libgdx.roguelike.salles.SalleEtageSuivant;
import com.libgdx.roguelike.salles.SalleMonstre;
import com.libgdx.roguelike.managers.GameStateManager;
import com.libgdx.roguelike.States.GameState;

public class TestGenEcranState extends GameState {

    private Etage etageCourant;

    Stage stage;
    SpriteBatch batch;

    /** Textures des salles. */
    Texture salleMonstre, salleDepart, porteTexture, salleEtageSuivant;

    public TestGenEcranState(GameStateManager gsm, Etage etageCourant) {
        super(gsm);
        this.etageCourant = etageCourant;

        // Initialisation des ressources et des éléments de la scène
        salleMonstre = new Texture(Gdx.files.internal("salleMonstre.jpg"));
        salleDepart = new Texture(Gdx.files.internal("salleDepart.jpg"));
        salleEtageSuivant = new Texture(Gdx.files.internal("salleEtageSuivant.jpg"));
        porteTexture = new Texture(Gdx.files.internal("porte.jpg"));
        stage = new Stage();
        batch = new SpriteBatch();
    }

    @Override
    public void handleInput() {
        // Fonctionnalité pour quitter ce "mode carte"
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        // Cette commande a pour but de créer un écran et de rendre son fond blanc.
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        batch.begin();
        stage.draw();

        for (Salle salle : etageCourant.getSallesPresentes()) {
            if (salle instanceof SalleMonstre) {
                batch.draw(salleMonstre, salle.getCase().x, salle.getCase().y);
            } else if (salle instanceof SalleDepart ) {
                batch.draw(salleDepart, salle.getCase().x, salle.getCase().y);
            }else if(salle instanceof SalleEtageSuivant){
                batch.draw(salleEtageSuivant, salle.getCase().x, salle.getCase().y);
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

        batch.end();
    }


    @Override
    public void dispose() {
        salleMonstre.dispose();
        salleDepart.dispose();
        porteTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}
