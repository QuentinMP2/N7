package com.libgdx.roguelike.managers;

import com.libgdx.roguelike.States.GameState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> states = new Stack();

    public GameStateManager() {
    }

    public void push(GameState state) {
        this.states.push(state);
    }

    public void pop() {

        ((GameState) this.states.pop()).dispose();
    }

    public void set(GameState state) {
        while (!states.isEmpty()) {
            states.pop().dispose();
        }
        this.push(state);
    }

    public void handleInput() {
        ((GameState) this.states.peek()).handleInput();
    }

    public void update(float dt) {

        ((GameState) this.states.peek()).update(dt);
    }

    public void render(SpriteBatch sb) {

        ((GameState) this.states.peek()).render(sb);
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }
}