package com.libgdx.roguelike.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.roguelike.managers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void handleInput();

    public abstract void update(float var1);

    public abstract void render(SpriteBatch var1);

    public abstract void dispose();
}
