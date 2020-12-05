package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Tools.WorldCreator;

public class PlayScreen implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;

    private OrthographicCamera gameCam;
    private FitViewport gamePort;

    private final TmxMapLoader mapLoader;
    private final TiledMap gameMap;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private World gameWorld;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(BombSquad game) {
        this.game = game;
        this.batch = game.getBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(ResourceManager.V_WIDTH / ResourceManager.PPM, ResourceManager.V_HEIGHT / ResourceManager.PPM, gameCam);

        mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load("bsMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / ResourceManager.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 5, gamePort.getWorldHeight() / 5, 0);

        gameWorld = new World(new Vector2(0, -10), true);
        new WorldCreator(gameWorld, gameMap);

        b2dr = new Box2DDebugRenderer();
    }

    public void update(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mapRenderer.render();
        b2dr.render(gameWorld, gameCam.combined);
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

    }
}
