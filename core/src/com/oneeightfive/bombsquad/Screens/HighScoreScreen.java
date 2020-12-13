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
import com.oneeightfive.bombsquad.Database.SQL;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HighScoreScreen implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;

    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    private final TextureRegion bg;
    private final BitmapFont font;

    private final BGM bgm;
    private final Sounds sounds;

    public static Map<String,Integer> highscores = new LinkedHashMap<>();

    private float delayTimer = 0;

    public HighScoreScreen(BombSquad game) {
        this.game = game;
        batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.position.set(BombSquad.V_WIDTH/2f, BombSquad.V_HEIGHT/2f, 0);
        int WIDTH = 1280;
        int HEIGHT = 720;
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        stage = new Stage(viewport, batch);

        bg = new TextureRegion(new Texture("textures/highscoresbg.jpg"), 0, 0, 1024, 578);

        FreeTypeFontGenerator genFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramFont.size = 24;
        paramFont.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        paramFont.borderColor = new Color(Color.BLACK);
        paramFont.borderWidth = 1;
        font = genFont.generateFont(paramFont);
        genFont.dispose();

        bgm = new BGM();
        bgm.playInGame();
        sounds = new Sounds();

        (new SQL()).listAll();

        for (Map.Entry<String, Integer> set : highscores.entrySet()) {
            System.out.println(set.getKey() + " " + set.getValue());
        }
    }

    public void handleInput(float delta) {
        delayTimer += delta;
        if(delayTimer >= 0.15) {
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)
                || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
            delayTimer = 0;
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
        batch.end();
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
        bgm.dispose();
        sounds.dispose();
        font.dispose();
    }
}
