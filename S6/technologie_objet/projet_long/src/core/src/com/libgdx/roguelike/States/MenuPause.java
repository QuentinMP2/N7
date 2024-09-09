package com.libgdx.roguelike.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.roguelike.managers.GameStateManager;

public class MenuPause extends GameState {
    private Texture background;
    private BitmapFont pauseTitleText, resumeText, InventaireText, exitText;
    private GlyphLayout pauseTitleGlyph, resumeGlyph, InventaireGlyph, exitGlyph;
    private boolean actionlocked = false;

    private GameStateManager gameStateManager;

    private PlayState JeuEnCours;

    public MenuPause(GameStateManager gsm) {
        super(gsm);
        this.gameStateManager = gsm;
        // Initialisation des textures et des polices
        this.background = new Texture("images/pse.jpg");

        // Titre
        this.pauseTitleText = new BitmapFont();
        this.pauseTitleText.getData().setScale(1.5f);
        this.pauseTitleGlyph = new GlyphLayout();
        this.pauseTitleGlyph.setText(this.pauseTitleText, "Menu Pause");

        // Bouton Reprendre
        this.resumeText = new BitmapFont();
        this.resumeText.getData().setScale(1.5f);
        this.resumeGlyph = new GlyphLayout();
        this.resumeGlyph.setText(this.resumeText, "Reprendre");

        // Bouton Inventaires
        this.InventaireText = new BitmapFont();
        this.InventaireText.getData().setScale(1.5f);
        this.InventaireGlyph = new GlyphLayout();
        this.InventaireGlyph.setText(this.InventaireText, "Inventaire");

        // Bouton Revenir au Menu
        this.exitText = new BitmapFont();
        this.exitText.getData().setScale(1.5f);
        this.exitGlyph = new GlyphLayout();
        this.exitGlyph.setText(this.exitText, "Revenir au Menu Principal");
    }

    private boolean isTouched(float x, float y, GlyphLayout glyph, float buttonX, float buttonY) {
        float buttonWidth = glyph.width;
        float buttonHeight = glyph.height;
        return x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY-buttonHeight && y <= buttonY + buttonHeight;
    }

    public void handleInput() {
        if (Gdx.input.isTouched() && !actionlocked) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float resumeX = (Gdx.graphics.getWidth() - resumeGlyph.width) / 2;
            float resumeY = Gdx.graphics.getHeight() / 2 - resumeGlyph.height / 2;

            float InventaireX = (Gdx.graphics.getWidth() - InventaireGlyph.width) / 2;
            float InventaireY = Gdx.graphics.getHeight() / 2 - InventaireGlyph.height / 2 - 100;

            float exitX = (Gdx.graphics.getWidth() - exitGlyph.width) / 2;
            float exitY = Gdx.graphics.getHeight() / 2 - exitGlyph.height / 2 - 200;

            if (isTouched(touchX, touchY, resumeGlyph, resumeX, resumeY)) {
                gsm.pop();
            } else if (isTouched(touchX, touchY, InventaireGlyph, InventaireX, InventaireY)) {
                //Lancer l'inventaire
            } else if (isTouched(touchX, touchY, exitGlyph, exitX, exitY)) {
                gsm.push(new MainMenu(gsm));
            }
            actionlocked = true;
        } else if (!Gdx.input.isTouched()) {
            actionlocked = false;
        }
    }

    public void update(float delta) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(this.background, 0, 0);
        pauseTitleText.draw(sb, pauseTitleGlyph, (Gdx.graphics.getWidth() - pauseTitleGlyph.width) / 2, Gdx.graphics.getHeight() - 150);
        resumeText.draw(sb, resumeGlyph, (Gdx.graphics.getWidth() - resumeGlyph.width) / 2, Gdx.graphics.getHeight() / 2);
        InventaireText.draw(sb, InventaireGlyph, (Gdx.graphics.getWidth() - InventaireGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 100);
        exitText.draw(sb, exitGlyph, (Gdx.graphics.getWidth() - exitGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 200);
        sb.end();
    }

    public void dispose() {
        this.background.dispose();
        this.pauseTitleText.dispose();
        this.resumeText.dispose();
        this.InventaireText.dispose();
        this.exitText.dispose();
    }
}
