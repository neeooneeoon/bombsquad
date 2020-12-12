package com.oneeightfive.bombsquad.Sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

import java.util.Random;

public class Balloon extends Enemy {
    private float stateTimer;
    private Array<TextureRegion> frames;

    public enum STATE {STAY, UP, DOWN, RIGHT, LEFT, DEAD}

    public STATE currentState;
    public STATE previousState;
    public STATE randomState;
    public final static float animationSpeed = 0.1f;

    boolean left, right, down, up, turn = false, check;

    private final TextureRegion standingFront;
    private final TextureRegion standingBack;
    private final TextureRegion standingLeft;
    private final TextureRegion standingRight;
    private final Animation<TextureRegion> runningLeft;
    private final Animation<TextureRegion> runningRight;
    private final Animation<TextureRegion> runningUp;
    private final Animation<TextureRegion> runningDown;

    private float previousX;
    private float previousY;

    TiledMap gameMap;

    public Balloon(PlayScreen screen, float x, float y, TiledMap gameMap) {
        super(screen, x, y);
        this.gameMap = gameMap;
        currentState = STATE.RIGHT;
        previousState = STATE.RIGHT;
        randomState = STATE.UP;
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f00"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f01"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f02"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f03"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f04"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f05"), 0, 0, 64, 64));
        runningUp = new Animation(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f00"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f01"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f02"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f03"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f04"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f05"), 0, 0, 64, 64));
        runningDown = new Animation(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f00"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f01"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f02"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f03"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f04"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f05"), 0, 0, 64, 64));
        runningRight = new Animation(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f00"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f01"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f02"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f03"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f04"), 0, 0, 64, 64));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f05"), 0, 0, 64, 64));
        for (TextureRegion frame : frames) {
            frame.flip(true, false);
        }
        runningLeft = new Animation(animationSpeed, frames);
        frames.clear();

        standingFront = new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_F_f02"), 0, 0, 64, 64);
        standingBack = new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_B_f02"), 0, 0, 64, 64);
        standingRight = new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f02"), 0, 0, 64, 64);
        standingLeft = new TextureRegion(screen.getCharactersAtlas().findRegion("Creep_S_f02"), 0, 0, 64, 64);
        standingLeft.flip(true, false);

        defineEnemy();
        defineJunction2();
        setBounds(0, 0, 64 / BombSquad.PPM, 64 / BombSquad.PPM);
        setRegion(standingFront);
        stateTimer = 0;

    }

    public void update(float dt) {
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;


        if (!turn && outOfJunction2()) {
            if (defineJunction2()) {
                turn = true;
            }
        }

        if (turn && (outOfJunction() || !GoStraight())) {
            if(!GoStraight()){
                defineJunction2();

            }
            if(defineJunction2()){
                changeDirection(dt);
                currentState = randomState;
                turn = false;
            }

        }

        move(dt);

        setPosition(b2body.getPosition().x -

                getWidth() / 2, b2body.getPosition().y - (30) / 64f);

    }

    private void move(float dt) {
        switch (currentState) {
            case LEFT:
                b2body.setLinearVelocity(new Vector2(-180 * dt, 0));
                setRegion(runningLeft.getKeyFrame(stateTimer, true));
                break;
            case RIGHT:
                b2body.setLinearVelocity(new Vector2(180 * dt, 0));
                setRegion(runningRight.getKeyFrame(stateTimer, true));
                break;
            case UP:
                b2body.setLinearVelocity(new Vector2(0, 180 * dt));
                setRegion(runningUp.getKeyFrame(stateTimer, true));
                break;
            case DOWN:
                b2body.setLinearVelocity(new Vector2(0, -180 * dt));
                setRegion(runningDown.getKeyFrame(stateTimer, true));
                break;
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(120 / BombSquad.PPM, 120 / BombSquad.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / BombSquad.PPM);
        fdef.filter.categoryBits = BombSquad.BOMBERMAN_BIT;
        fdef.filter.maskBits = BombSquad.BRICK_BIT | BombSquad.ITEM_BIT | BombSquad.WALL_BIT | BombSquad.BOMBERMAN_BIT ;

        shape.setPosition(new Vector2(0 / BombSquad.PPM, 0 / BombSquad.PPM));
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
        previousY = b2body.getPosition().y;
        previousX = b2body.getPosition().x;
    }


    private boolean defineJunction2() {
        left = false;
        right = false;
        down = false;
        up = false;
        check = false;
        int enemyX = (int) b2body.getPosition().x;
        int enemyY = (int) b2body.getPosition().y;
        TiledMapTileLayer layer1 = (TiledMapTileLayer) gameMap.getLayers().get(1);
        TiledMapTileLayer layer2 = (TiledMapTileLayer) gameMap.getLayers().get(2);
        if (layer1.getCell(enemyX + 1, enemyY) == null &&
                layer2.getCell(enemyX + 1, enemyY) == null) {
            right = true;
            if (currentState != STATE.LEFT && currentState != STATE.RIGHT) {
                check = true;
            }
        }
        if (layer1.getCell(enemyX - 1, enemyY) == null &&
                layer2.getCell(enemyX - 1, enemyY) == null) {
            left = true;
            if (currentState != STATE.RIGHT && currentState != STATE.LEFT) {
                check = true;
            }
        }
        if (layer1.getCell(enemyX, enemyY + 1) == null &&
                layer2.getCell(enemyX, enemyY + 1) == null) {
            up = true;
            if (currentState != STATE.DOWN && currentState != STATE.UP) {
                check = true;
            }
        }
        if (layer1.getCell(enemyX, enemyY - 1) == null &&
                layer2.getCell(enemyX, enemyY - 1) == null) {
            down = true;
            if (currentState != STATE.UP && currentState != STATE.DOWN) {
                check = true;
            }
        }

        if (check == true) {
            previousY = b2body.getPosition().y;
            previousX = b2body.getPosition().x;
            return true;
        } else if (!GoStraight()) {
            previousY = b2body.getPosition().y;
            previousX = b2body.getPosition().x;
            return true;
        }
        return false;
    }

    private void changeDirection(float dt) {
        previousY = b2body.getPosition().y;
        previousX = b2body.getPosition().x;

        if (check == true) {
            randomState = RandomDirection();
        } else if (!GoStraight()) {
            randomState = OppositeDirection(currentState);
        }

    }

    private STATE OppositeDirection(STATE state) {
        if (state == STATE.RIGHT) {
            return STATE.LEFT;
        }
        if (state == STATE.LEFT) {
            return STATE.RIGHT;
        }
        if (state == STATE.DOWN) {
            return STATE.UP;
        }
        if (state == STATE.UP) {
            return STATE.DOWN;
        }
        return state;
    }

    private boolean GoStraight() {
        TiledMapTileLayer layer1 = (TiledMapTileLayer) gameMap.getLayers().get(1);
        TiledMapTileLayer layer2 = (TiledMapTileLayer) gameMap.getLayers().get(2);
        int enemyX = (int) b2body.getPosition().x;
        int enemyY = (int) b2body.getPosition().y;
        if( currentState == STATE.LEFT){
            enemyX = (int) (b2body.getPosition().x + 0.3f);
        }
        if( currentState == STATE.RIGHT){
            enemyX = (int) (b2body.getPosition().x - 0.3f);
        }
        if( currentState == STATE.UP){
            enemyY = (int) (b2body.getPosition().y - 0.3f);
        }
        if( currentState == STATE.DOWN){
            enemyY = (int) (b2body.getPosition().y + 0.3f);
        }
        if (currentState == STATE.LEFT
                && layer1.getCell(enemyX - 1, enemyY) == null &&
                layer2.getCell(enemyX - 1, enemyY) == null) {
            return true;
        }
        if (currentState == STATE.RIGHT
                && layer1.getCell(enemyX + 1, enemyY) == null &&
                layer2.getCell(enemyX + 1, enemyY) == null) {
            return true;
        }
        if (currentState == STATE.UP
                && layer1.getCell(enemyX, enemyY + 1) == null &&
                layer2.getCell(enemyX, enemyY + 1) == null) {
            return true;
        }
        if (currentState == STATE.DOWN
                && layer1.getCell(enemyX, enemyY - 1) == null &&
                layer2.getCell(enemyX, enemyY - 1) == null) {
            return true;
        }

        return false;
    }

    private STATE RandomDirection() {
        boolean t = false;
        STATE chose = STATE.RIGHT;
        while (t == false) {
            Random random = new Random();
            switch (Math.abs(random.nextInt()) % 4) {
                case 0:
                    t = left;
                    chose = STATE.LEFT;
                    break;
                case 1:
                    t = right;
                    chose = STATE.RIGHT;
                    break;
                case 2:
                    t = up;
                    chose = STATE.UP;
                    break;
                case 3:
                    t = down;
                    chose = STATE.DOWN;
                    break;
            }
        }
        return chose;
    }

    private boolean outOfJunction() {
        if (currentState == STATE.RIGHT) {
            if ((int) b2body.getPosition().y == (int) previousY && b2body.getPosition().x <= (int) previousX + 0.5f) {
                return false;
            }
        }

        if (currentState == STATE.LEFT) {
            if ((int) b2body.getPosition().y == (int) previousY && b2body.getPosition().x >= (int) previousX + 0.5f) {
                return false;
            }
        }

        if (currentState == STATE.DOWN) {
            System.out.println(b2body.getPosition().y + " " +((int) previousY - 0.5f) );
            if (b2body.getPosition().y >= (int) previousY - 1.5f && (int) b2body.getPosition().x == (int) previousX) {
                return false;
            }
        }

        if (currentState == STATE.UP) {
            if (b2body.getPosition().y <= (int) previousY + 0.5f && (int) b2body.getPosition().x == (int) previousX) {
                return false;
            }
        }

        return true;
    }

    private boolean outOfJunction2() {
        if ((int) b2body.getPosition().y == (int) previousY && (int) b2body.getPosition().x == (int) previousX) {
            return false;
        }
        return true;
    }
}
