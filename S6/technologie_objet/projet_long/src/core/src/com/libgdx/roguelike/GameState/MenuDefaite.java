package com.libgdx.roguelike.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.roguelike.managers.GameStateManager;

public class MenuDefaite extends GameState {
    private Texture background;
    private BitmapFont DefaiteTitleText, resumeText, InventaireText, exitText;
    private GlyphLayout DefaiteTitleGlyph, resumeGlyph, InventaireGlyph, exitGlyph;
    private boolean actionlocked = false;

    private GameStateManager gameStateManager;

    public MenuDefaite(GameStateManager gsm) {
        super(gsm);
        this.gameStateManager = gsm;
        // Initialisation des textures et des polices
        this.background = new Texture("images/Photo2.jpg");

        // Titre
        this.DefaiteTitleText = new BitmapFont();
        this.DefaiteTitleText.getData().setScale(1.5f);
        this.DefaiteTitleGlyph = new GlyphLayout();
        this.DefaiteTitleGlyph.setText(this.DefaiteTitleText, "Defaite");

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

            float exitX = (Gdx.graphics.getWidth() - exitGlyph.width) / 2;
            float exitY = Gdx.graphics.getHeight() / 2 - exitGlyph.height / 2 - 200;

            if (isTouched(touchX, touchY, exitGlyph, exitX, exitY)) {
                gsm.pop();
                gsm.pop();
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
        DefaiteTitleText.draw(sb, DefaiteTitleGlyph, (Gdx.graphics.getWidth() - DefaiteTitleGlyph.width) / 2, Gdx.graphics.getHeight() - 150);
        exitText.draw(sb, exitGlyph, (Gdx.graphics.getWidth() - exitGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 200);
        sb.end();
    }

    public void dispose() {
        this.background.dispose();
        this.DefaiteTitleText.dispose();
        this.exitText.dispose();
    }
}
