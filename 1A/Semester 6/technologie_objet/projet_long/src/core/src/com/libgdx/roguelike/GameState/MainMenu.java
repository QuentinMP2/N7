package com.libgdx.roguelike.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.roguelike.managers.GameStateManager;

public class MainMenu extends GameState {
    private Texture background;
    private BitmapFont gameTitleText, startText, optionText, exitText;
    private GlyphLayout gameTitleGlyph, startGlyph, optionGlyph, exitGlyph;
    private boolean actionlocked = false;

    private GameStateManager gameStateManager;

    // Constructeur pour initialiser l'état du MainMenu
    public MainMenu(GameStateManager gsm) {
        super(gsm);
        this.gameStateManager = gsm;

        // Initialiser les textures et les polices
        this.background = new Texture("images/Photo2.jpg");

        // Initialiser le texte et le layout du titre
        this.gameTitleText = new BitmapFont();
        this.gameTitleText.getData().setScale(1.5f);
        this.gameTitleGlyph = new GlyphLayout();
        this.gameTitleGlyph.setText(this.gameTitleText, "Roguelike");

        // Initialiser le texte et le layout du bouton commencer
        this.startText = new BitmapFont();
        this.startText.getData().setScale(1.5f);
        this.startGlyph = new GlyphLayout();
        this.startGlyph.setText(this.startText, "Commencer");

        // Initialiser le texte et le layout du bouton options
        this.optionText = new BitmapFont();
        this.optionText.getData().setScale(1.5f);
        this.optionGlyph = new GlyphLayout();
        this.optionGlyph.setText(this.optionText, "Options");

        // Initialiser le texte et le layout du bouton quitter
        this.exitText = new BitmapFont();
        this.exitText.getData().setScale(1.5f);
        this.exitGlyph = new GlyphLayout();
        this.exitGlyph.setText(this.exitText, "Quitter");
    }

    // Méthode pour vérifier si un bouton est touché
    private boolean isTouched(float x, float y, GlyphLayout glyph, float buttonX, float buttonY) {
        float buttonWidth = glyph.width;
        float buttonHeight = glyph.height;
        // Afficher les coordonnées et la taille du bouton pour le débogage
        System.out.println(buttonX);
        System.out.println(buttonWidth);
        System.out.println(buttonY);
        System.out.println(buttonHeight);
        return x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY - buttonHeight && y <= buttonY + buttonHeight;
    }

    // Méthode pour gérer l'entrée utilisateur
    public void handleInput() {
        if (Gdx.input.isTouched() && !actionlocked) {
            float touchX = Gdx.input.getX();
            // Afficher les coordonnées du toucher pour le débogage
            System.out.println(touchX);
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convertir au système de coordonnées y-down
            System.out.println(touchY);

            // Calculer les positions des boutons
            float startX = (Gdx.graphics.getWidth() - startGlyph.width) / 2;
            float startY = Gdx.graphics.getHeight() / 2 - startGlyph.height / 2;

            float optionX = (Gdx.graphics.getWidth() - optionGlyph.width) / 2;
            float optionY = Gdx.graphics.getHeight() / 2 - optionGlyph.height / 2 - 100;

            float exitX = (Gdx.graphics.getWidth() - exitGlyph.width) / 2;
            float exitY = Gdx.graphics.getHeight() / 2 - exitGlyph.height / 2 - 200;

            // Vérifier si les boutons sont touchés
            if (isTouched(touchX, touchY, startGlyph, startX, startY)) {
                gsm.push(new PlayState(gsm));
            } else if (isTouched(touchX, touchY, optionGlyph, optionX, optionY)) {
                System.out.println("Options selected");
            } else if (isTouched(touchX, touchY, exitGlyph, exitX, exitY)) {
                Gdx.app.exit();
            }
            actionlocked = true;
        } else if (!Gdx.input.isTouched()) {
            actionlocked = false;
        }
    }

    // Méthode pour mettre à jour l'état
    public void update(float delta) {
        handleInput();
    }

    // Méthode pour afficher le contenu à l'écran
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(this.background, 0, 0);
        gameTitleText.draw(sb, gameTitleGlyph, (Gdx.graphics.getWidth() - gameTitleGlyph.width) / 2, Gdx.graphics.getHeight() - 150);
        startText.draw(sb, startGlyph, (Gdx.graphics.getWidth() - startGlyph.width) / 2, Gdx.graphics.getHeight() / 2);
        optionText.draw(sb, optionGlyph, (Gdx.graphics.getWidth() - optionGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 100);
        exitText.draw(sb, exitGlyph, (Gdx.graphics.getWidth() - exitGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 200);
        sb.end();
    }

    // Méthode pour libérer les ressources
    public void dispose() {
        this.background.dispose();
        this.gameTitleText.dispose();
        this.startText.dispose();
        this.optionText.dispose();
        this.exitText.dispose();
    }
}
