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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Sprites.Bomb;
import com.oneeightfive.bombsquad.Sprites.Bomberman;
import com.oneeightfive.bombsquad.World.WorldCreator;

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

    private final World gameWorld;
    private final Box2DDebugRenderer b2dr;

    public Bomberman.STATE playerDirection;
    public float playerX;
    public float playerY;

    public final Animation<TextureRegion> bombAnimation;
    public final Animation<TextureRegion> flameAnimation;
    public final static float animationSpeed = 0.1f;

    public WorldCreator worldCreator;
    private final Array<Fixture> worldBody = new Array<>();

    public float stateTimer;

    public PlayScreen(BombSquad game) {
        charactersAtlas = new TextureAtlas("characters.pack");
        stageAtlas = new TextureAtlas("stage.pack");
        menuAtlas = new TextureAtlas("menu.pack");
        weaponAtlas = new TextureAtlas("weapon.pack");

        this.game = game;
        this.batch = game.getBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(BombSquad.V_WIDTH, BombSquad.V_HEIGHT, gameCam);

        mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / BombSquad.PPM);
        gameWorld = new World(new Vector2(0, 0), true);
        gameCam.position.set(BombSquad.V_WIDTH / 2F - 0.5F, BombSquad.V_HEIGHT / 2F - 0.5F, 0);
        worldCreator = new WorldCreator(gameWorld, gameMap, worldBody);

        player = new Bomberman(this);
        playerDirection = Bomberman.STATE.DOWN;

        b2dr = new Box2DDebugRenderer();

        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Bomb_f03"), 0, 0, 48, 48));
        bombAnimation = new Animation<>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_f00"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F03"), 0, 0, 48, 48));
        frames.add(new TextureRegion(this.getWeaponAtlas().findRegion("Flame_F04"), 0, 0, 48, 48));
        flameAnimation = new Animation<>(animationSpeed, frames);
        frames.clear();
    }

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

    public void drawWeaponAnimation(Animation<TextureRegion> t, boolean looping, float x, float y) {
        batch.draw(t.getKeyFrame(stateTimer, looping), x, y, 48 / BombSquad.PPM, 48 / BombSquad.PPM);
    }

    public void createBomb() {
        for( Bomb bomb : player.bombs){
            if( bomb.x == (int)((player.getX() * 64 + 24) / BombSquad.PPM)
                    && bomb.y == (int)((player.getY() * 64 + 24) / BombSquad.PPM)){
                System.out.println("a");
                return;
            }
        }
        player.bombs.add(new Bomb(gameWorld, this, player.getX(), player.getY(), 2));
    }

    private void drawBombs() {
        for (Bomb bomb : player.bombs) {
            if (bomb.available) {
                if((bomb.x - 32 / BombSquad.PPM > playerX || bomb.x + 32 / BombSquad.PPM < playerX
                        || bomb.y - 32 / BombSquad.PPM > playerY || bomb.y + 32 / BombSquad.PPM < playerY)
                        && !bomb.defined) {
                    bomb.defineBomb();
                }
                if (bomb.timeLeft < 0) {
                    worldCreator.brickLayer.test(gameMap, bomb, worldBody);
                    bomb.blow();
                    worldCreator.deleteBrick(gameWorld, gameMap, worldBody);
                }
                drawWeaponAnimation(bombAnimation, true, bomb.x + 10 / 64f, bomb.y + 10 / 64f);
                bomb.timeLeft -= Gdx.graphics.getDeltaTime();
            }
            if (bomb.flame) {
                if (bomb.timeFlame <= 10 * animationSpeed) {
                    drawFlames(bomb);
                } else {
                    if(bomb.defined) {
                        gameWorld.destroyBody(bomb.b2body);
                    }
                    player.bombs.removeIndex(player.bombs.indexOf(bomb, true));
                }
            }
        }
    }

    private void drawFlames(Bomb bomb) {
        int radius = bomb.radius;
        drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 10) / BombSquad.PPM, (bomb.y * 64 + 10) / BombSquad.PPM);
        for (int j = 1; j <= radius; j++) {
            if (worldCreator.brickLayer.left >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 - 64 * j + 10) / BombSquad.PPM, (bomb.y * 64 + 10) / BombSquad.PPM);
            }

            if (worldCreator.brickLayer.up >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 10) / BombSquad.PPM, (bomb.y * 64 + 64 * j + 10) / BombSquad.PPM);
            }

            if (worldCreator.brickLayer.down >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 10) / BombSquad.PPM, (bomb.y * 64 - 64 * j + 10) / BombSquad.PPM);
            }

            if (worldCreator.brickLayer.right >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 64 * j + 10) / BombSquad.PPM, (bomb.y * 64 + 10) / BombSquad.PPM);
            }
        }

        bomb.timeFlame += Gdx.graphics.getDeltaTime();
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
            createBomb();
        }
    }

    public void update(float delta) {
        handleInput(delta);
        gameWorld.step(1 / 60f, 6, 2);
        mapRenderer.setView(gameCam);
        player.update(delta);
        gameCam.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.3f, 0.4f, 0.4f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        b2dr.render(gameWorld, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();

        drawBombs();
        player.draw(batch);

        batch.end();

        stateTimer += delta;
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
