package com.mygdx.game.screens;

import Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Boomer;
import com.mygdx.game.Sprites.Boom;
import com.mygdx.game.Sprites.Player;

enum DIRECTION {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    STAY
}

public class PlayScreens implements Screen {
    Boomer game;
    private float x;
    private float y;
    Animation[] PlayerAnimation;
    Animation BoomAnimation;
    private float tileSize = 32;
    private Player player;

    private OrthographicCamera gameCam;
    private Viewport viewport;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;


    DIRECTION direction = DIRECTION.STAY;

    public final static float PlayerAnimationSpeed = 0.18f;
    private float stateTime;

    public PlayScreens(Boomer game) {

        this.game = game;

        loadAnimation();

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(Boomer.V_WIDTH, Boomer.V_HEIGHT, gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Tilemap/Map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();


        new B2WorldCreator(world, map);

        player = new Player(world);
        player.b2body.setGravityScale(0);


    }

    private void loadAnimation() {

        PlayerAnimation = new Animation[5];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("transparent.png"), 32, 32);

        TextureRegion[] left = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[0], 0, left, 0, 3);
        TextureRegion[] down = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[0], 3, down, 0, 3);
        TextureRegion[] right = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[1], 0, right, 0, 3);
        TextureRegion[] up = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[1], 3, up, 0, 3);
        TextureRegion[] stay = new TextureRegion[1];
        stay[0] = rollSpriteSheet[0][4];


        PlayerAnimation[0] = new Animation(PlayerAnimationSpeed, left);
        PlayerAnimation[1] = new Animation(PlayerAnimationSpeed, right);
        PlayerAnimation[2] = new Animation(PlayerAnimationSpeed, up);
        PlayerAnimation[3] = new Animation(PlayerAnimationSpeed, down);
        PlayerAnimation[4] = new Animation(PlayerAnimationSpeed, stay);

        //boom
        TextureRegion[] boom = new TextureRegion[3];
        System.arraycopy(rollSpriteSheet[3], 0, boom, 0, 3);
        BoomAnimation = new Animation(PlayerAnimationSpeed, boom);
    }

    @Override
    public void show() {
    }

    private void createBoom() {
        for (int i = 0; i < player.numberOfBoom; i++) {
            if (player.boom[i].available == false) {
                player.boom[i] = new Boom(world, player.getX(), player.getY());
                break;
            }
        }
    }

    public void updatePlayer(float dt) {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.b2body.setLinearVelocity(new Vector2(0, 5000 * dt));
            direction = DIRECTION.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.setLinearVelocity(new Vector2(5000 * dt, 0));
            direction = DIRECTION.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.b2body.setLinearVelocity(new Vector2(-5000 * dt, 0));
            direction = DIRECTION.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.b2body.setLinearVelocity(new Vector2(0, -5000 * dt));
            direction = DIRECTION.DOWN;
        } else {
            player.b2body.setLinearVelocity(new Vector2(0, 0));
            direction = DIRECTION.STAY;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            createBoom();
        }

        world.step(1 / 60f, 6, 2);
        player.update(dt);
        x = player.getX();
        y = player.getY();
    }

    public void drawPlayer() {
        game.batch.begin();

        switch (direction) {
            case LEFT:
                game.batch.draw((TextureRegion) PlayerAnimation[0].getKeyFrame(stateTime, true), x, y, tileSize, tileSize);
                break;
            case RIGHT:
                game.batch.draw((TextureRegion) PlayerAnimation[1].getKeyFrame(stateTime, true), x, y, tileSize, tileSize);
                break;
            case UP:
                game.batch.draw((TextureRegion) PlayerAnimation[2].getKeyFrame(stateTime, true), x, y, tileSize, tileSize);
                break;
            case DOWN:
                game.batch.draw((TextureRegion) PlayerAnimation[3].getKeyFrame(stateTime, true), x, y, tileSize, tileSize);
                break;
            case STAY:
                game.batch.draw((TextureRegion) PlayerAnimation[4].getKeyFrame(stateTime, true), x, y, tileSize, tileSize);
                break;
        }


        game.batch.end();
    }

    private void drawTileMap() {
        renderer.render();
        game.batch.begin();
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.end();
        renderer.setView(gameCam);

    }

    private void drawBoom() {
        game.batch.begin();

        for (int i = 0; i < player.numberOfBoom; i++) {
            if (player.boom[i].available == true) {
                System.out.println(i);
                if (player.boom[i].timeLeft < 0) {
                    player.boom[i].available = false;
                }
                player.boom[i].timeLeft -= Gdx.graphics.getDeltaTime();
                game.batch.draw((TextureRegion) BoomAnimation.getKeyFrame(stateTime, true), player.boom[i].x, player.boom[i].y, tileSize, tileSize);
            }
        }

        game.batch.end();
    }

    @Override
    public void render(float delta) {

        updatePlayer(delta);

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawTileMap();
        drawBoom();
        //b2dr.render(world, gameCam.combined);
        drawPlayer();
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
