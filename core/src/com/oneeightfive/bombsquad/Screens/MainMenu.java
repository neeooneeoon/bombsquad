package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Sprites.Bomberman;

public class MainMenu implements Screen {
    private BombSquad game;
    private SpriteBatch batch;

    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    private Boolean isPlay = true;
    private Boolean isHighScore = false;
    private Boolean isQuit = false;

    private TextureRegion bg;

    public MainMenu(BombSquad game) {
        this.game = game;
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.position.set(BombSquad.V_WIDTH/2f, BombSquad.V_HEIGHT/2f, 0);
        viewport = new StretchViewport(1280, 720, camera);
        stage = new Stage(viewport, batch);

        bg = new TextureRegion(new Texture("textures/menubg.jpg"), 0, 0, 3344, 1254);

    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    public void update(float delta) {
        handleInput(delta);

        batch.begin();
        batch.draw(bg, 0, 0, viewport.getWorldWidth(),viewport.getWorldHeight());
        batch.end();

        batch.setProjectionMatrix(camera.combined);
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
