package com.oneeightfive.bombsquad.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oneeightfive.bombsquad.BombSquad;

public class HUD implements Disposable {

    public Stage stage;
    public OrthographicCamera camera;
    private final Viewport viewport;

    public int score = 0;
    public int bombs = 5;
    public int lives = 3;
    public int timeLeft = 300;
    public float timeCount = 0;

    private final Label livesLabel;
    private final Label bombsLabel;
    private final Label timeLabel;
    private final Label scoreLabel;

    public Boolean timeUp = false;

    public HUD(SpriteBatch batch) {
        camera = new OrthographicCamera();
        camera.position.set(BombSquad.V_WIDTH/2f, BombSquad.V_HEIGHT/2f, 0);
        viewport = new FitViewport(1280,720, camera);
        stage = new Stage(viewport, batch);

        FreeTypeFontGenerator genFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramFont.size = 24;
        paramFont.borderColor = new Color(Color.BLACK);
        paramFont.borderWidth = 1;
        BitmapFont font = genFont.generateFont(paramFont);
        genFont.dispose();

        Table topTable = new Table();
        topTable.top();
        topTable.setFillParent(true);

        livesLabel = new Label("LIVES: " + lives, new Label.LabelStyle(font, Color.WHITE));
        bombsLabel = new Label("BOMBS: " + bombs, new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new Label("TIME: " + timeLeft, new Label.LabelStyle(font, Color.WHITE));

        topTable.add(livesLabel).expandX().padTop(-6);
        topTable.add(bombsLabel).expandX().padTop(-6);
        topTable.add(timeLabel).expandX().padTop(-6);

        Table bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.setFillParent(true);

        scoreLabel = new Label("SCORE: " + score, new Label.LabelStyle(font, Color.WHITE));

        bottomTable.add(scoreLabel).expandX().padBottom(-6);

        stage.addActor(topTable);
        stage.addActor(bottomTable);
        //stage.setDebugAll(true);
    }

    public void update(int lives, int bombs, int score, float delta) {
        bombsLabel.setText("BOMBS: " + bombs);
        livesLabel.setText("LIVES: " + lives);
        scoreLabel.setText("SCORE: " + score);

        timeCount += delta;
        if(timeCount >= 1) {
            if(timeLeft > 0) {
                timeLeft--;
            } else {
                timeUp = true;
            }
            timeCount = 0;
        }
        timeLabel.setText("TIME: " + timeLeft);
    }

    @Override
    public void dispose() {

    }
}
