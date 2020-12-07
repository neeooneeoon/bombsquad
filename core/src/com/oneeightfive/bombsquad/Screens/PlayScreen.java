package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Sprites.Bomberman;
import com.oneeightfive.bombsquad.Tools.WorldCreator;

public class PlayScreen implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;
    private final TextureAtlas actorsAtlas;
    private final TextureAtlas actorsAtlas2;

    private final OrthographicCamera gameCam;
    private final FitViewport gamePort;

    private final TmxMapLoader mapLoader;
    private final TiledMap gameMap;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final Bomberman player;

    private final World gameWorld;
    private final Box2DDebugRenderer b2dr;

    public PlayScreen(BombSquad game) {
        actorsAtlas = new TextureAtlas("bsActors1.pack");
        actorsAtlas2 = new TextureAtlas("bsActors2.pack");

        this.game = game;
        this.batch = game.getBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(ResourceManager.V_WIDTH, ResourceManager.V_HEIGHT, gameCam);

        mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load("bsMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / ResourceManager.PPM);
        gameWorld = new World(new Vector2(0, 0), true);
        gameCam.position.set(ResourceManager.V_WIDTH / 2 - 2,ResourceManager.V_HEIGHT /2 - 0.5F, 0);
        new WorldCreator(gameWorld, gameMap);

        player = new Bomberman(this);

        b2dr = new Box2DDebugRenderer();
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.b2Body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2Body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.b2Body.applyLinearImpulse(new Vector2(-0.2f, 0), player.b2Body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.b2Body.applyLinearImpulse(new Vector2(0, 0.2f), player.b2Body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.b2Body.applyLinearImpulse(new Vector2(0, -0.2f), player.b2Body.getWorldCenter(), true);
    }

    public void update(float delta) {
        handleInput(delta);
        gameWorld.step(1 / 60f, 6, 2);
        mapRenderer.setView(gameCam);
        gameCam.update();
    }

    public World getWorld() {
        return gameWorld;
    }

    public TextureAtlas getActorsAtlas() {
        return actorsAtlas;
    }

    public TextureAtlas getActorsAtlas2() {
        return actorsAtlas2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        b2dr.render(gameWorld, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        gameMap.dispose();
        mapRenderer.dispose();
        gameWorld.dispose();
        b2dr.dispose();
    }
}