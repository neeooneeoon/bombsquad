package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Boomer;
import com.mygdx.game.screens.PlayScreens;

public class Player extends Sprite {
    //the world that our player is going to live in
    public World world;
    public Body b2body;
    public Boom boom[] = new Boom[5];
    public int numberOfBoom = 5;


    public Player(World world) {
        this.world = world;
        definePlayer();
        setBounds(0, 0, 32, 32);
        for (int i = 0; i < numberOfBoom; i++) {
            boom[i] = new Boom();
        }
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 - 16 + 32, 200 - 16 + 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 16, new Vector2(0, 0), 0);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
