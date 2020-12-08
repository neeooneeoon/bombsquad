package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class Bomberman extends Sprite {
    public enum State {STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;

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
        currentState = State.STANDING;
        previousState = State.STANDING;

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
        standingFront = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_F_f02"), 0, 0, 32, 64);
        standingBack = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_B_f02"), 0, 0, 32, 64);
        standingRight = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 32, 64);
        standingLeft = new TextureRegion(screen.getCharactersAtlas().findRegion("Bman_S_f02"), 0, 0, 32, 64);

        defineBomberman();
        setBounds(0, 0, 32 / ResourceManager.PPM, 64 / ResourceManager.PPM);
        setRegion(standingFront);
    }

    public void defineBomberman() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / ResourceManager.PPM, 32 / ResourceManager.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5F / ResourceManager.PPM);
        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

}
