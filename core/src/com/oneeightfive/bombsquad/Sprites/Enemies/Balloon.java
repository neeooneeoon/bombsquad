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

    public Balloon(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        currentState = STATE.STAY;
        previousState = Balloon.STATE.STAY;
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

        switch (screen.playerDirection){
            case LEFT:
                currentState = STATE.LEFT;
                break;
            case RIGHT:
                currentState = STATE.RIGHT;
                break;
            case UP:
                currentState = STATE.UP;
                break;
            case DOWN:
                currentState = STATE.DOWN;
                break;
            case STAY:
                break;
        }

        switch (currentState) {
            case LEFT:
                setRegion(runningLeft.getKeyFrame(stateTimer, true));
                break;
            case RIGHT:
                setRegion(runningRight.getKeyFrame(stateTimer, true));
                break;
            case UP:
                setRegion(runningUp.getKeyFrame(stateTimer, true));
                break;
            case DOWN:
                setRegion(runningDown.getKeyFrame(stateTimer, true));
                break;
            case STAY:
                switch (previousState) {
                    case LEFT:
                        setRegion(standingLeft);
                        break;
                    case RIGHT:
                        setRegion(standingRight);
                        break;
                    case UP:
                        setRegion(standingBack);
                        break;
                    case DOWN:
                        setRegion(standingFront);
                        break;
                }
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
