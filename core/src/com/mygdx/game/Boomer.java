package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.PlayScreens;

public class Boomer extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 800;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new PlayScreens(this));
    }

    @Override
    public void render() {
    	super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
