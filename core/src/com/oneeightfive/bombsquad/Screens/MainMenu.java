package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oneeightfive.bombsquad.Audio.BGM;
import com.oneeightfive.bombsquad.Audio.Sounds;
import com.oneeightfive.bombsquad.BombSquad;

public class MainMenu implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;

    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    private final Boolean isPlay = true;
    private final Boolean isHighScore = false;
    private final Boolean isQuit = false;

    private final TextureRegion bg;

    private final BGM bgm;
    private final Sounds sounds;

    public MainMenu(BombSquad game) {
        this.game = game;
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.position.set(BombSquad.V_WIDTH/2f, BombSquad.V_HEIGHT/2f, 0);
        viewport = new StretchViewport(1280, 720, camera);
        stage = new Stage(viewport, batch);

        bg = new TextureRegion(new Texture("textures/menubg.jpg"), 0, 0, 3344, 1254);

        bgm = new BGM();
        bgm.playMenu();

        sounds = new Sounds();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if (isPlay) {
                game.setScreen(new PlayScreen(game));
                dispose();
            } else if (isHighScore) {
                //game.setScreen(new HighScoreScreen(game));
                dispose();
            } else if (isQuit) {
                game.dispose();
                dispose();
            }
        }
    }

    public void update() {
        handleInput();

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
        update();

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
        bgm.dispose();
        sounds.dispose();
    }
}
