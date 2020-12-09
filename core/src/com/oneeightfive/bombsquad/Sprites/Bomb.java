package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.ResourceManager;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class Bomb extends Sprite {
    public World world;
    public PlayScreen screen;
    public Body b2body;

    public float x;
    public float y;

    public double timeLeft;
    public boolean available = false;

    public boolean flame = false;
    public float timeFlame = 0;

    public int radius = 1;

    public Bomb() {}

    public Bomb(World world, PlayScreen screen, float x, float y, int radius) {
        this.world = world;
        this.screen = screen;
        defineBomb();
        setBounds(0, 0, 48, 48);

        this.x = x;
        this.y = y;
        this.radius = radius;

        timeLeft = 4;
        available = true;
    }

    public void blow() {
        available = false;
        flame = true;
        timeFlame = 0;
    }

    public void clear() {
        flame = false;
    }

    public void defineBomb() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((x + 24) / ResourceManager.PPM, (y + 24) / ResourceManager.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / ResourceManager.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
