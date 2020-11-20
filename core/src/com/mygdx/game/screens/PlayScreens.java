package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Boomer;




public class PlayScreens implements Screen {
    Boomer game = new Boomer();
    private Viewport viewport;
    private OrthographicCamera gamecam;
    private TiledMap map;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer mapRenderer;
    Animation[] rolls;

    private float stateTime;

    public PlayScreens(Boomer game) {
        this.game = game;
        rolls = new Animation[4];

        TextureRegion[][] rollSpriteSheet  = TextureRegion.split(new Texture("output-onlinepngtools.png"), 32,32);

        TextureRegion[] left = new TextureRegion[3];
        for( int i =0; i < 3; i++){
            left[i] = rollSpriteSheet[0][i];
        }
        TextureRegion[] down = new TextureRegion[3];
        for( int i =3; i < 6; i++){
            down[i-3] = rollSpriteSheet[0][i];
        }
        TextureRegion[] right = new TextureRegion[3];
        for( int i =0; i < 3; i++){
            right[i] = rollSpriteSheet[1][i];
        }
        TextureRegion[] up = new TextureRegion[3];
        for( int i =3; i < 6; i++){
            up[i-3] = rollSpriteSheet[1][i];
        }
        rolls[0] = new Animation(0.5f, left);
        rolls[1] = new Animation(0.5f, right);
        rolls[2] = new Animation(0.5f, up);
        rolls[3] = new Animation(0.5f, down);
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            gamecam.position.y += 100*dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            gamecam.position.x += 100*dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            gamecam.position.y -= 100*dt;
        }if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            gamecam.position.x -= 100*dt;
        }


    }

    public void update(float dt) {
        handleInput(dt);


        gamecam.update();
        mapRenderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {




        stateTime += delta*3;
        Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw((TextureRegion) rolls[0].getKeyFrame(stateTime, true), 30, 30, 50, 50);
        game.batch.draw((TextureRegion) rolls[1].getKeyFrame(stateTime, true), 100, 30, 50, 50);
        game.batch.draw((TextureRegion) rolls[2].getKeyFrame(stateTime, true), 180, 30, 50, 50);
        game.batch.draw((TextureRegion) rolls[3].getKeyFrame(stateTime, true), 260, 30, 50, 50);
        game.batch.end();

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
