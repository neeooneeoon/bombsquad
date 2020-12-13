package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oneeightfive.bombsquad.Audio.BGM;
import com.oneeightfive.bombsquad.Audio.Sounds;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Database.Score;

public class MainMenu implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;

    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    private Boolean isPlay = true;
    private Boolean isHighScore = false;
    private Boolean isQuit = false;

    private final TextureRegion bg;
    private final BitmapFont font;

    private final BGM bgm;
    private final Sounds sounds;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    private float delayTimer = 0;

    public MainMenu(BombSquad game) {
        this.game = game;
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.position.set(BombSquad.V_WIDTH/2f, BombSquad.V_HEIGHT/2f, 0);
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        stage = new Stage(viewport, batch);

        bg = new TextureRegion(new Texture("textures/menubg.jpg"), 0, 0, 3344, 1254);

        bgm = new BGM();
        bgm.playMenu();
        sounds = new Sounds();

        FreeTypeFontGenerator genFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramFont.size = 24;
        paramFont.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        paramFont.borderColor = new Color(Color.BLACK);
        paramFont.borderWidth = 1;
        font = genFont.generateFont(paramFont);
        genFont.dispose();
    }

    public void drawTitles() {
        float xTitle = WIDTH / 2f - 75f;
        if (isPlay) {
            font.setColor(Color.RED);
            font.draw(batch, "Play", xTitle, HEIGHT / 2f - 10f);
            font.setColor(Color.WHITE);
            font.draw(batch, "High Scores", xTitle, HEIGHT / 2f - 45f);
            font.draw(batch, "Quit", xTitle, HEIGHT / 2f - 80f);
        } else if (isHighScore) {
            font.setColor(Color.RED);
            font.draw(batch, "High Scores", xTitle, HEIGHT / 2f - 45f);
            font.setColor(Color.WHITE);
            font.draw(batch, "Play", xTitle, HEIGHT / 2f - 10f);
            font.draw(batch, "Quit", xTitle, HEIGHT / 2f - 80f);
        } else if (isQuit) {
            font.setColor(Color.RED);
            font.draw(batch, "Quit", xTitle, HEIGHT / 2f - 80f);
            font.setColor(Color.WHITE);
            font.draw(batch, "High Scores", xTitle, HEIGHT / 2f - 45f);
            font.draw(batch, "Play", xTitle, HEIGHT / 2f - 10f);
        }
    }

    public void handleInput(float delta) {
        delayTimer += delta;
        if(delayTimer >= 0.15) {
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                sounds.playSelected();
                delayTimer = 0;
                while (delayTimer <=1) {
                    delayTimer += delta;
                }
                if (isPlay) {
                    Score.current = 0;
                    game.setScreen(new PlayScreen(game,1));
                    dispose();
                } else if (isHighScore) {
                    game.setScreen(new HighScore(game));
                    dispose();
                } else if (isQuit) {
                    dispose();
                    game.dispose();
                    Gdx.app.exit();
                    System.exit(0);
                }
                delayTimer = 0;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                sounds.playSelect();
                if (isPlay) {
                    isPlay = false;
                    isHighScore = true;
                    isQuit = false;
                } else if (isHighScore) {
                    isPlay = false;
                    isHighScore = false;
                    isQuit = true;
                } else if (isQuit) {
                    isPlay = true;
                    isHighScore = false;
                    isQuit = false;
                }
                delayTimer = 0;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                sounds.playSelect();
                if (isPlay) {
                    isPlay = false;
                    isHighScore = false;
                    isQuit = true;
                } else if (isHighScore) {
                    isPlay = true;
                    isHighScore = false;
                    isQuit = false;
                } else if (isQuit) {
                    isPlay = false;
                    isHighScore = true;
                    isQuit = false;
                }
                delayTimer = 0;
            }
        }
    }

    public void update(float delta) {
        handleInput(delta);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();
        batch.draw(bg, 0, 0, viewport.getWorldWidth(),viewport.getWorldHeight());
        drawTitles();
        batch.end();

        batch.setProjectionMatrix(camera.combined);
        stage.draw();
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
        font.dispose();
    }
}
