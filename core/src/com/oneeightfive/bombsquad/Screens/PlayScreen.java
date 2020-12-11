package com.oneeightfive.bombsquad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Sprites.Bomb;
import com.oneeightfive.bombsquad.Sprites.Bomberman;
import com.oneeightfive.bombsquad.Sprites.Enemies.Balloon;
import com.oneeightfive.bombsquad.Tools.WorldCreator;

public class PlayScreen implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;
    private final TextureAtlas charactersAtlas;
    private final TextureAtlas stageAtlas;
    private final TextureAtlas menuAtlas;
    private final TextureAtlas weaponAtlas;

    private final OrthographicCamera gameCam;
    private final FitViewport gamePort;

    private final TmxMapLoader mapLoader;
    private final TiledMap gameMap;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final Bomberman player;
    private Balloon balloon;

    private final World gameWorld;
    private final Box2DDebugRenderer b2dr;

    public Bomberman.STATE playerDirection;
    public float playerX;
    public float playerY;

    public final Animation<TextureRegion> bombAnimation;
    public final Animation<TextureRegion> flameAnimation;
    public final static float animationSpeed = 0.1f;

    public WorldCreator worldCreator;

    public float stateTimer;

    public PlayScreen(BombSquad game) {
        charactersAtlas = new TextureAtlas("characters.pack");
        stageAtlas = new TextureAtlas("stage.pack");
        menuAtlas = new TextureAtlas("menu.pack");
        weaponAtlas = new TextureAtlas("weapon.pack");

        this.game = game;
        this.batch = game.getBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(ResourceManager.V_WIDTH, ResourceManager.V_HEIGHT, gameCam);

        mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load("map2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / ResourceManager.PPM);
        gameWorld = new World(new Vector2(0, 0), true);
        gameCam.position.set(ResourceManager.V_WIDTH / 2 - 2,ResourceManager.V_HEIGHT /2 - 0.5F, 0);
        worldCreator = new WorldCreator(gameWorld, gameMap);

        player = new Bomberman(this);
        balloon = new Balloon(this, .32f, .32f);
        playerDirection = Bomberman.STATE.DOWN;

        b2dr = new Box2DDebugRenderer();

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f03"), 0, 0, 48, 48));
        bombAnimation = new Animation<TextureRegion>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_f00"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F03"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F04"), 0, 0, 48, 48));
        flameAnimation = new Animation<TextureRegion>(animationSpeed, frames);
        frames.clear();
    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.b2Body.setLinearVelocity(new Vector2(0, 250 * delta));
            player.currentState = Bomberman.STATE.UP;
            playerDirection = Bomberman.STATE.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2Body.setLinearVelocity(new Vector2(250 * delta, 0));
            player.currentState = Bomberman.STATE.RIGHT;
            playerDirection = Bomberman.STATE.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.b2Body.setLinearVelocity(new Vector2(-250 * delta, 0));
            player.currentState = Bomberman.STATE.LEFT;
            playerDirection = Bomberman.STATE.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.b2Body.setLinearVelocity(new Vector2(0, -250 * delta));
            player.currentState = Bomberman.STATE.DOWN;
            playerDirection = Bomberman.STATE.DOWN;
        } else {
            player.b2Body.setLinearVelocity(new Vector2(0, 0));
            player.currentState = Bomberman.STATE.STAY;
            playerDirection = Bomberman.STATE.STAY;
        }

        playerX = player.getX();
        playerY = player.getY();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //worldCreator.brickLayerCreator.test(gameMap, player);
            createBomb();
        }
    }

    public void update(float delta) {
        handleInput(delta);
        gameWorld.step(1 / 60f, 6, 2);
        mapRenderer.setView(gameCam);
        player.update(delta);
        balloon.update(delta);
        gameCam.update();
    }

    public void drawWeaponAnimation(Animation<TextureRegion> t, boolean looping, float x, float y) {
        batch.draw((TextureRegion) t.getKeyFrame(stateTimer, looping), x, y, 48 / ResourceManager.PPM, 48 / ResourceManager.PPM);
    }

    public void createBomb() {
        for (int i = 0; i < player.numberOfBombs; i++) {
            if (!player.bombs[i].available) {
                player.bombs[i] = new Bomb(gameWorld, this, player.getX(), player.getY(), 3);
                break;
            }
        }
    }

    private void drawBomb() {
        for (int i = 0; i < player.numberOfBombs; i++) {
            if (player.bombs[i].available) {
                if (player.bombs[i].timeLeft < 0) {
                    worldCreator.brickLayerCreator.test(gameMap,player.bombs[i]);
                    player.bombs[i].blow();
                }
                drawWeaponAnimation(bombAnimation, true, player.bombs[i].x + 10/64f, player.bombs[i].y + 10/64f);
                player.bombs[i].timeLeft -= Gdx.graphics.getDeltaTime();
            }
            if (player.bombs[i].flame) {
                if (player.bombs[i].timeFlame <= 2 * animationSpeed) {
                    drawFlame(i);
                } else {
                    player.bombs[i].clear();
                }
            }
        }
    }

    private void drawFlame(int i) {}


    public World getWorld() {
        return gameWorld;
    }

    public TextureAtlas getCharactersAtlas() {
        return charactersAtlas;
    }

    public TextureAtlas getStageAtlas() {
        return stageAtlas;
    }

    public TextureAtlas getMenuAtlas() {
        return menuAtlas;
    }

    public TextureAtlas getWeaponAtlas() {
        return weaponAtlas;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Bomberman getPlayer() {
        return player;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        //b2dr.render(gameWorld, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        batch.begin();
        player.draw(batch);
        balloon.draw(batch);
        drawBomb();
        batch.end();

        stateTimer += delta;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return gameMap;
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
