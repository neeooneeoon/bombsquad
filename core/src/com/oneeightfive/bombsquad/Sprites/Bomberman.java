package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class Bomberman extends Sprite {
    public enum STATE {STAY, UP, DOWN, RIGHT, LEFT, DEAD}

    public STATE currentState;
    public STATE previousState;
    public final static float animationSpeed = 0.1f;

    private float stateTimer;

    private final TextureRegion standingFront;
    private final TextureRegion standingBack;
    private final TextureRegion standingLeft;
    private final TextureRegion standingRight;
    private final Animation<TextureRegion> runningLeft;
    private final Animation<TextureRegion> runningRight;
    private final Animation<TextureRegion> runningUp;
    private final Animation<TextureRegion> runningDown;

    private final PlayScreen screen;
    public World world;
    public Body b2Body;

    public Array<Bomb> bombs = new Array<>();

    public Bomberman(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = STATE.STAY;
        previousState = STATE.STAY;

        Array<TextureRegion> frames = new Array<>();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f00"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f01"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f02"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f03"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f04"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f05"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f06"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f07"), 0, 0, 64, 128));
        runningUp = new Animation<>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f00"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f01"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f02"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f03"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f04"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f05"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f06"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f07"), 0, 0, 64, 128));
        runningDown = new Animation<>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f00"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f01"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f03"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f04"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f05"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f06"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f07"), 0, 0, 64, 128));
        runningRight = new Animation<>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f00"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f01"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f03"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f04"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f05"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f06"), 0, 0, 64, 128));
        frames.add(new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f07"), 0, 0, 64, 128));
        for(TextureRegion frame : frames) {
            frame.flip(true, false);
        }
        runningLeft = new Animation<>(animationSpeed, frames);
        frames.clear();

        standingFront = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f02"), 0, 0, 64, 128);
        standingBack = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f02"), 0, 0, 64, 128);
        standingRight = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128);
        standingLeft = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128);
        standingLeft.flip(true, false);

        defineBomberman();
        setBounds(0, 0, 64 / BombSquad.PPM, 128 / BombSquad.PPM);
        setRegion(standingFront);

    }

    public void defineBomberman() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(120 / BombSquad.PPM, 120 / BombSquad.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(26 / BombSquad.PPM);
        fdef.filter.categoryBits = BombSquad.BOMBERMAN_BIT;
        fdef.filter.maskBits = BombSquad.BRICK_BIT | BombSquad.ITEM_BIT | BombSquad.WALL_BIT;

        shape.setPosition(new Vector2(0 / BombSquad.PPM, 0 / BombSquad.PPM));
        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public void update(float dt) {
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        currentState = screen.playerDirection;

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
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - (30)/64f);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

}
