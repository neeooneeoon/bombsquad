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
    public TextureRegion bomberStand;


    public Player (World world, PlayScreens screen) {
        //super(screen.getAtlas().findRegion("boomer"));
        this.world = world;
        definePlayer();
        bomberStand = new TextureRegion();
        setBounds(0, 0, 32, 32 );
        TextureRegion[][] temp = TextureRegion.split(new Texture("output-onlinepngtools.png"), 32, 32);
        bomberStand = temp[0][4];
        setRegion(bomberStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 , 100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 16, new Vector2(0,0), 0);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
