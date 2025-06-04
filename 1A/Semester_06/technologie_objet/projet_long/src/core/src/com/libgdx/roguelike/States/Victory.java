package com.libgdx.roguelike.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.managers.GameStateManager;

public class Victory extends GameState {

    Stage stage;
    SpriteBatch sb;
    private Texture background;
    private BitmapFont  exitText;
    private GlyphLayout exitGlyph;
    private boolean actionlocked = false;



    /** L'image qui va se déplacer. */
    Texture Victory;

    public Victory(GameStateManager gsm) {
        super(gsm);

        // Création du joueur avec sa photo.
        Victory = new Texture("Victoire.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.sb = new SpriteBatch();

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

    @Override
    public void handleInput() {
        // Implémentez ici la gestion des entrées utilisateur, si nécessaire.
        if (Gdx.input.isTouched() && !actionlocked) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float exitX = (Gdx.graphics.getWidth() - exitGlyph.width) / 2;
            float exitY = Gdx.graphics.getHeight() / 2 - exitGlyph.height / 2 - 200;

            if (isTouched(touchX, touchY, exitGlyph, exitX, exitY)) {
                gsm.push(new MainMenu(gsm));
            }
            actionlocked = true;
        } else if (!Gdx.input.isTouched()) {
            actionlocked = false;
        }
    }

    @Override
    public void update(float delta) {
        // Mettez à jour la logique de la scène de Victory, si nécessaire.
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        sb.begin();
        stage.draw();
        sb.draw(Victory, 0, 0);
        exitText.draw(sb, exitGlyph, (Gdx.graphics.getWidth() - exitGlyph.width) / 2, Gdx.graphics.getHeight() / 2 - 200);
        sb.end();
    }

    @Override
    public void dispose() {
        // Libération des ressources
        this.background.dispose();
        this.exitText.dispose();
    }
}