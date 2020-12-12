package com.oneeightfive.bombsquad;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class BombSquad extends Game {
	private SpriteBatch batch;

	public static final int V_WIDTH = 32;
	public static final int V_HEIGHT = 14;
	public static final float PPM = 64;

	public static final short DEFAULT_BIT = 1;
	public static final short BOMBERMAN_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short ITEM_BIT = 8;
	public static final short WALL_BIT = 16;
	public static final short BOMB_BIT = 32;
	public static final short PREBOMB_BIT = 64;

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
