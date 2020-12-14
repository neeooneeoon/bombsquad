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
import com.oneeightfive.bombsquad.Audio.BGM;
import com.oneeightfive.bombsquad.Audio.Sounds;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Database.Score;
import com.oneeightfive.bombsquad.HUD.HUD;
import com.oneeightfive.bombsquad.Sprites.Bomb;
import com.oneeightfive.bombsquad.Sprites.Bomberman;
import com.oneeightfive.bombsquad.Sprites.Enemies.Balloon;
import com.oneeightfive.bombsquad.Sprites.Items.BombItem;
import com.oneeightfive.bombsquad.Sprites.Items.FlameItem;
import com.oneeightfive.bombsquad.Sprites.Items.SpeedItem;
import com.oneeightfive.bombsquad.World.DestroyedBrick;
import com.oneeightfive.bombsquad.World.WorldCreator;

public class PlayScreen implements Screen {
    private final BombSquad game;
    private final SpriteBatch batch;
    private final TextureAtlas charactersAtlas;
    private final TextureAtlas weaponAtlas;
    private final TextureAtlas itemAtlas;

    private final OrthographicCamera gameCam;
    private final FitViewport gamePort;

    private final TiledMap gameMap;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final HUD hud;

    private final Bomberman player;

    private Balloon balloon;
    private Balloon balloon2;
    private Balloon balloon3;
    private Balloon balloon4;
    private Balloon balloon5;

    private BombItem itemBomb;
    private FlameItem itemFlame;
    private SpeedItem itemSpeed;

    private final World gameWorld;
    private final Box2DDebugRenderer b2dr;

    public Bomberman.STATE playerDirection;
    public float playerX;
    public float playerY;
    public float previousPlayerX;
    public float previousPlayerY;

    public final Animation<TextureRegion> bombAnimation;
    public final Animation<TextureRegion> flameAnimation;
    public final static float animationSpeed = 0.1f;

    public WorldCreator worldCreator;
    private final Array<Fixture> worldBody = new Array<>();
    public Array<DestroyedBrick> destroyedBricks = new Array<>();

    private final BGM bgm;
    private final Sounds sounds;

    public int numberOfBricks = 50;
    public String mapPath = "maps/level1.tmx";

    public float stateTimer;
    public float delayTimer;

    public PlayScreen(BombSquad game, int level) {
        charactersAtlas = new TextureAtlas("textures/characters.pack");
        weaponAtlas = new TextureAtlas("textures/weapon.pack");
        itemAtlas = new TextureAtlas("textures/items.pack");

        this.game = game;
        this.batch = game.getBatch();

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(BombSquad.V_WIDTH, BombSquad.V_HEIGHT, gameCam);

        if(level == 2) {
            numberOfBricks = 60;
            mapPath = "maps/level2.tmx";
        }

        TmxMapLoader mapLoader = new TmxMapLoader();
        gameMap = mapLoader.load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1 / BombSquad.PPM);
        gameWorld = new World(new Vector2(0, 0), true);
        gameCam.position.set(BombSquad.V_WIDTH / 2F - 0.5F, BombSquad.V_HEIGHT / 2F - 0.5F, 0);
        worldCreator = new WorldCreator(gameWorld, gameMap, worldBody);

        player = new Bomberman(this);
        playerDirection = Bomberman.STATE.DOWN;

        enemyGen(level);

        itemGen(level);

        hud = new HUD(batch);

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

        bgm = new BGM();
        bgm.playInGame();

        sounds = new Sounds();

    }

    public World getWorld() {
        return gameWorld;
    }

    public TextureAtlas getCharactersAtlas() {
        return charactersAtlas;
    }

    public TextureAtlas getWeaponAtlas() {
        return weaponAtlas;
    }

    public TextureAtlas getItemAtlas() {
        return itemAtlas;
    }


    public Bomberman getPlayer() {
        return player;
    }

    public void enemyGen(int level) {
        if(level == 2) {
            balloon = new Balloon(this, 16f, 10f, gameMap);
            balloon2 = new Balloon(this, 16f, 12f, gameMap);
            balloon3 = new Balloon(this, 28f, 4f, gameMap);
            balloon4 = new Balloon(this, 30f, 2f, gameMap);
            balloon5 = new Balloon(this, 10f, 5f, gameMap);
        } else {
            balloon = new Balloon(this, 10f, 10f, gameMap);
            balloon2 = new Balloon(this, 12f, 12f, gameMap);
            balloon3 = new Balloon(this, 20f, 8f, gameMap);
            balloon4 = new Balloon(this, 30f, 4f, gameMap);
            balloon5 = new Balloon(this, 9f, 9f, gameMap);
        }
    }

    public void itemGen(int level) {
        if(level == 2) {
            itemBomb = new BombItem(this, 88/BombSquad.PPM,154/BombSquad.PPM);
        } else {
            itemBomb = new BombItem(this, 88/BombSquad.PPM,90/BombSquad.PPM);
        }
    }

    public void itemDraw() {
        itemBomb.draw(batch);
    }

    private void itemUpdate(float delta) {
        itemBomb.update();
    }

    public void enemyDraw() {
        balloon.draw(batch);
        balloon2.draw(batch);
        balloon3.draw(batch);
        balloon4.draw(batch);
        balloon5.draw(batch);
    }

    public void enemyUpdate(float delta) {
        balloon.update(delta);
        balloon2.update(delta);
        balloon3.update(delta);
        balloon4.update(delta);
        balloon5.update(delta);
    }

    public void drawWeaponAnimation(Animation<TextureRegion> t, boolean looping, float x, float y) {
        batch.draw(t.getKeyFrame(stateTimer, looping), x, y, 48 / BombSquad.PPM, 48 / BombSquad.PPM);
    }

    public void createBomb() {
        for( Bomb bomb : player.bombs){
            if( bomb.x == (int)((player.getX() * 64 + 24) / BombSquad.PPM)
                    && bomb.y == (int)((player.getY() * 64 + 24) / BombSquad.PPM)){
                return;
            }
        }
        player.numberOfBombs--;
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
                    bomb.FlameCollision(gameMap, destroyedBricks, worldBody, player);
                    bomb.blow();
                    sounds.playExplode();
                    player.numberOfBombs++;
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
            if (bomb.left >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 - 64 * j + 10) / BombSquad.PPM, (bomb.y * 64 + 10) / BombSquad.PPM);
            }

            if (bomb.up >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 10) / BombSquad.PPM, (bomb.y * 64 + 64 * j + 10) / BombSquad.PPM);
            }

            if (bomb.down >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 10) / BombSquad.PPM, (bomb.y * 64 - 64 * j + 10) / BombSquad.PPM);
            }

            if (bomb.right >= j - 1) {
                drawWeaponAnimation(flameAnimation, true, (bomb.x * 64 + 64 * j + 10) / BombSquad.PPM, (bomb.y * 64 + 10) / BombSquad.PPM);
            }
        }
        bomb.timeFlame += Gdx.graphics.getDeltaTime();
    }

    public void handleInput(float delta) {
        delayTimer += delta;
        previousPlayerX = player.b2Body.getPosition().x;
        previousPlayerY = player.b2Body.getPosition().y;

        if(player.currentState == Bomberman.STATE.DEAD) {
            if (player.lives > 0) {
                player.b2Body.setLinearVelocity(new Vector2(0, 0));
                player.b2Body.setTransform(new Vector2(96 / BombSquad.PPM, 750 / BombSquad.PPM), 0);
                playerDirection = Bomberman.STATE.DOWN;
                player.setPosition(0, 0);
                player.lives--;
            } else {
                player.b2Body.setLinearVelocity(new Vector2(0, 0));
                playerDirection = Bomberman.STATE.DEAD;
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.b2Body.setLinearVelocity(new Vector2(0, 250 * delta));
                playerDirection = Bomberman.STATE.UP;
                sounds.playFootstep();
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2Body.setLinearVelocity(new Vector2(250 * delta, 0));
                playerDirection = Bomberman.STATE.RIGHT;
                sounds.playFootstep();
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.b2Body.setLinearVelocity(new Vector2(-250 * delta, 0));
                playerDirection = Bomberman.STATE.LEFT;
                sounds.playFootstep();
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.b2Body.setLinearVelocity(new Vector2(0, -250 * delta));
                playerDirection = Bomberman.STATE.DOWN;
                sounds.playFootstep();
            } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && delayTimer > 0.15) {
                game.setScreen(new GameOver(game));
                dispose();
                delayTimer = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.P) && delayTimer > 0.15) {
                game.setScreen(new PlayScreen(game, 2));
                delayTimer = 0;
                dispose();
            } else {
                player.b2Body.setLinearVelocity(new Vector2(0, 0));
                playerDirection = Bomberman.STATE.STAY;
                sounds.stopFootstep();
            }

            playerX = player.getX();
            playerY = player.getY();

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (player.numberOfBombs > 0) {
                    createBomb();
                    sounds.playPlanted();
                }
            }
        }
    }

    public boolean isLose() {
        return hud.timeUp || player.lives == 0;
    }

    public boolean nextLevel() {
        return numberOfBricks <= 0;
    }

    public void update(float delta) {
        handleInput(delta);
        gameWorld.step(1 / 60f, 6, 2);
        mapRenderer.setView(gameCam);
        enemyUpdate(delta);
        itemUpdate(delta);
        player.update(delta);

        gameCam.update();
        if (isLose()) {
            game.setScreen(new GameOver(game));
            dispose();
        }
        if (nextLevel()) {
            game.setScreen(new PlayScreen(game, 2));
            delayTimer = 0;
        }
        hud.update(player.lives, player.numberOfBombs, Score.current, delta);
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
        enemyDraw();
        itemDraw();
        player.draw(batch);
        batch.end();

        batch.setProjectionMatrix(hud.camera.combined);
        hud.stage.draw();

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
        bgm.dispose();
        sounds.dispose();
    }
}
