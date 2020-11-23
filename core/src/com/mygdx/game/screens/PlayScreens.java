package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Boomer;

enum DIRECTION {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    STAY
}

public class PlayScreens implements Screen {
    Boomer game;
    private float x;
    private float y;
    Animation[] rolls;

    DIRECTION direction = DIRECTION.STAY;

    private final static float PlayerAnimationSpeed = 0.18f;
    private float stateTime;

    public PlayScreens(Boomer game) {
        this.game = game;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("output-onlinepngtools.png"), 32, 32);


        TextureRegion[] left = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[0], 0, left, 0, 3);
        TextureRegion[] down = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[0], 3, down, 0, 3);
        TextureRegion[] right = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[1], 0, right, 0, 3);
        TextureRegion[] up = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[1], 3, up, 0, 3);
        TextureRegion[] stay = new TextureRegion[1];
        stay[0] = rollSpriteSheet[0][4];


        rolls[0] = new Animation(PlayerAnimationSpeed, left);
        rolls[1] = new Animation(PlayerAnimationSpeed, right);
        rolls[2] = new Animation(PlayerAnimationSpeed, up);
        rolls[3] = new Animation(PlayerAnimationSpeed, down);
        rolls[4] = new Animation(PlayerAnimationSpeed, stay);
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += 100 * dt;
            direction = DIRECTION.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 100 * dt;
            direction = DIRECTION.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= 100 * dt;
            direction = DIRECTION.DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 100 * dt;
            direction = DIRECTION.LEFT;
        }

        if( Gdx.input.isTouched()){
            System.out.println( Gdx.input.getX() + "\t" + Gdx.input.getY());
            //Gdx.app.exit();
        }

    }

    public void update(float dt) {
        handleInput(dt);
    }

    public void draw() {
        game.batch.begin();
        switch (direction) {
            case LEFT:
                game.batch.draw((TextureRegion) rolls[0].getKeyFrame(stateTime, true), x, y, 50, 50);
                break;
            case RIGHT:
                game.batch.draw((TextureRegion) rolls[1].getKeyFrame(stateTime, true), x, y, 50, 50);
                break;
            case UP:
                game.batch.draw((TextureRegion) rolls[2].getKeyFrame(stateTime, true), x, y, 50, 50);
                break;
            case DOWN:
                game.batch.draw((TextureRegion) rolls[3].getKeyFrame(stateTime, true), x, y, 50, 50);
                break;
            case STAY:
                game.batch.draw((TextureRegion) rolls[4].getKeyFrame(stateTime, true), x, y, 50, 50);
                break;
        }
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        stateTime += delta * 3;
        Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw();
        direction = DIRECTION.STAY;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
