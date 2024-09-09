package com.libgdx.roguelike;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.roguelike.managers.GameStateManager;
import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.States.*;


// Déclaration de la classe LaunchGame qui étend ApplicationAdapter
public class LaunchGame extends ApplicationAdapter {
    // Déclaration des variables membres pour gérer les états du jeu et le rendu graphique
    private GameStateManager gsm;
    private SpriteBatch sb;

    public LaunchGame() {
    }

    // Méthode appelée lors de la création de l'application (initialisation)
    @Override
    public void create() {
        // Initialisation du gestionnaire d'états du jeu
        this.gsm = new GameStateManager();
        // Initialisation du SpriteBatch pour le rendu des sprites
        this.sb = new SpriteBatch();
        // Pousse l'état initial (menu principal) dans le gestionnaire d'états
        this.gsm.push(new MainMenu(gsm));
    }

    // Méthode appelée à chaque frame pour mettre à jour et rendre le jeu
    @Override
    public void render() {
        // Met à jour l'état actuel du jeu avec le temps écoulé depuis la dernière frame
        this.gsm.update(Gdx.graphics.getDeltaTime());
        // Rendu de l'état actuel du jeu en utilisant le SpriteBatch
        this.gsm.render(this.sb);
    }

    // Méthode appelée pour libérer les ressources lorsque l'application est fermée
    @Override
    public void dispose() {
        // Libération des ressources utilisées par le SpriteBatch
        this.sb.dispose();
    }
}
