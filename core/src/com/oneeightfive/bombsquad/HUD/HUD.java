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

public class HUD implements Disposable {

    public Stage stage;
    private final Viewport viewport;

    public int score = 0;
    public int lives = 3;
    public int timeUp = 300;
    public int timeCount = 0;

    private final Label livesTitle;
    private final Label bombsTitle;
    private final Label timeTitle;
    private final Label scoreTitle;
    private Label livesLabel;
    private Label timeLabel;
    private Label scoreLabel;

    public HUD(SpriteBatch batch) {
        viewport = new FitViewport(1280,720, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table topTable = new Table();
        topTable.top();
        topTable.setFillParent(true);

        FreeTypeFontGenerator genFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramFont.size = 24;
        paramFont.borderColor = new Color(Color.BLACK);
        paramFont.borderWidth = 1;

        BitmapFont font = genFont.generateFont(paramFont);
        genFont.dispose();

        livesTitle = new Label("LIVES", new Label.LabelStyle(font, Color.WHITE));
        bombsTitle = new Label("BOMBS", new Label.LabelStyle(font, Color.WHITE));
        timeTitle = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        

        topTable.add(livesTitle).expandX().padTop(20);
        topTable.add(bombsTitle).expandX().padTop(20);
        topTable.add(timeTitle).expandX().padTop(20);

        scoreTitle = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));


        stage.addActor(topTable);
        stage.setDebugAll(true);
    }

    @Override
    public void dispose() {

    }
}
