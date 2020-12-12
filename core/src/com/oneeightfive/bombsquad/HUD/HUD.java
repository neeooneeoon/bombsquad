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
    private Viewport viewport;

    public int score = 0;
    public int lives = 3;
    public int timeUp = 300;
    public int timeCount = 0;

    private Label livesTitle;
    private Label timeTitle;
    private Label scoreTitle;
    private Label livesLabel;
    private Label timeLabel;
    private Label scoreLabel;

    public HUD(SpriteBatch batch) {
        viewport = new FitViewport(BombSquad.V_WIDTH,BombSquad.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table topTable = new Table();
        topTable.top();
        topTable.setFillParent(true);

        FreeTypeFontGenerator genFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramFont.size = 18;
        paramFont.borderColor = new Color(Color.BLACK);
        paramFont.borderWidth = 1;

        BitmapFont font = genFont.generateFont(paramFont);
        genFont.dispose();

        livesTitle = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeTitle = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreTitle = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        topTable.add(livesLabel).expandX().padTop(10);
        topTable.add(timeLabel).expandX().padTop(10);
        topTable.add(scoreLabel).expandX().padTop(10);

        stage.addActor(topTable);
    }

    @Override
    public void dispose() {

    }
}
