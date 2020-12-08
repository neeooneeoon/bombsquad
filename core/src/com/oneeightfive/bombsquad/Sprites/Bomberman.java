package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class Bomberman extends Sprite {
    public enum STATE {STAY, UP, DOWN, RIGHT, LEFT, DEAD};
    public STATE currentState;
    public STATE previousState;
    private float stateTime;
    public final static float animationSpeed = 0.18f;



    private TextureRegion standingFront;
    private TextureRegion standingBack;
    private TextureRegion standingLeft;
    private TextureRegion standingRight;
    private Animation runningLeft;
    private Animation runningRight;
    private Animation runningUp;
    private Animation runningDown;

    private final PlayScreen screen;
    public World world;
    public Body b2Body;

    public Bomberman(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = STATE.STAY;
        previousState = STATE.STAY;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        /*
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(screen.getActorsAtlas().findRegion("player"), i * 32, 0, 32, 32));
        }
        runningUp = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(screen.getActorsAtlas().findRegion("player"), i * 32, 32, 32, 32));
        }
        runningRight = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(screen.getActorsAtlas().findRegion("player"), i * 32, 64, 32, 32));
        }
        runningRight = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(screen.getActorsAtlas().findRegion("player"), i * 32, 96, 32, 32));
        }
        runningDown = new Animation(0.2f, frames);
        frames.clear();

         */
        standingFront = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f02"), 0, 0, 64, 128);
        standingBack = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f02"), 0, 0, 64, 128);
        standingRight = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128);
        standingLeft = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 64, 128);

        defineBomberman();
        setBounds(0, 0, 64 / ResourceManager.PPM, 128 / ResourceManager.PPM);
        setRegion(standingFront);
    }

    public void defineBomberman() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(120 / ResourceManager.PPM, 120 / ResourceManager.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef1 = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(28 / ResourceManager.PPM);
        shape.setPosition(new Vector2(0 / ResourceManager.PPM,-24 / ResourceManager.PPM));
        fdef1.shape = shape;
        b2Body.createFixture(fdef1).setUserData(this);
        shape.dispose();
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

}
