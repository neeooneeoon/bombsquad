package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Boomer;

public class Player extends Sprite {
    //the world that our player is going to live in
    public World world;
    public Body b2body;

    public Player (World world) {
        this.world = world;
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Boomer.PPM, 32 / Boomer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Boomer.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
