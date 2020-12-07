package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Boom extends Sprite {
    public World world;
    public Body b2body;
    public float x;
    public float y;
    public double timeLeft;
    public boolean available = false;

    public Boom() {
    }

    public Boom(World world, float x, float y) {
        this.world = world;
        defineBoom();
        setBounds(0, 0, 32, 32);
        this.x = x;
        this.y = y;
        timeLeft = 5;
        available = true;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void defineBoom() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x - 16, y - 16);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 16, new Vector2(0, 0), 0);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
