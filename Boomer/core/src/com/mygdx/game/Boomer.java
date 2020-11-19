package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class Boomer extends Game {
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 260;
	public static final float PPM = 100;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();  //draw images to the screen
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	/**
	 * clean up to avoid memory issues
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
