package com.oneeightfive.bombsquad.Sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public final static float animationSpeed = 0.1f;

    private final TextureRegion standingFront;
    private final TextureRegion standingBack;
    private final TextureRegion standingLeft;
    private final TextureRegion standingRight;
    private final Animation<TextureRegion> runningLeft;
    private final Animation<TextureRegion> runningRight;
    private final Animation<TextureRegion> runningUp;
    private final Animation<TextureRegion> runningDown;

    private float previousX=300;
    private float previousY=300;

    public Balloon(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        currentState = STATE.RIGHT;
        previousState = STATE.RIGHT;
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
        for(TextureRegion frame : frames) {
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
        setBounds(0, 0, 64 / BombSquad.PPM, 64 / BombSquad.PPM);
        setRegion(standingFront);
        stateTimer = 0;
    }

    public void update(float dt) {
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        if( ((int)(b2body.getPosition().x *1000))/1000f == ((int)(previousX *1000))/1000f
                && ((int)(b2body.getPosition().y *1000))/1000f == ((int)(previousY *1000))/1000f){
            while( currentState == previousState){
                Random random = new Random();
                switch (Math.abs(random.nextInt())%4){
                    case 0:
                        System.out.println("Left");
                        currentState = STATE.LEFT;
                        break;
                    case 1:
                        System.out.println("Right");
                        currentState = STATE.RIGHT;
                        break;
                    case 2:
                        System.out.println("Up");
                        currentState = STATE.UP;
                        break;
                    case 3:
                        System.out.println("Down");
                        currentState = STATE.DOWN;
                        break;
                }
            }
        } else {
            System.out.println("b2body position:   " + ((int)(b2body.getPosition().x *1000))/1000f + " " + ((int)(b2body.getPosition().y *1000))/1000f);
            System.out.println("previous position:   " + ((int)(previousX *1000))/1000f + " " + ((int)(previousY *1000))/1000f);
        }

        previousX = b2body.getPosition().x;
        previousY = b2body.getPosition().y;

        switch (currentState) {
            case LEFT:
                b2body.setLinearVelocity(new Vector2(-150 * dt, 0));
                setRegion(runningLeft.getKeyFrame(stateTimer, true));
                break;
            case RIGHT:
                b2body.setLinearVelocity(new Vector2(150 * dt, 0));
                setRegion(runningRight.getKeyFrame(stateTimer, true));
                break;
            case UP:
                b2body.setLinearVelocity(new Vector2(0, 150 * dt));
                setRegion(runningUp.getKeyFrame(stateTimer, true));
                break;
            case DOWN:
                b2body.setLinearVelocity(new Vector2(0, -150 * dt));
                setRegion(runningDown.getKeyFrame(stateTimer, true));
                break;
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - (30)/64f);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(120/ BombSquad.PPM, 120 / BombSquad.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(26 / BombSquad.PPM);
        fdef.filter.categoryBits = BombSquad.ENEMY_BIT;
        fdef.filter.maskBits = BombSquad.BRICK_BIT | BombSquad.ITEM_BIT | BombSquad.WALL_BIT | BombSquad.ENEMY_BIT | BombSquad.OBJECT_BIT;

        shape.setPosition(new Vector2(0 / BombSquad.PPM, 0 / BombSquad.PPM));
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }
}
