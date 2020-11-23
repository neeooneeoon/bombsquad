package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Boomer;
import com.mygdx.game.Screens.PlayScreen;

public class Player extends Sprite {
    //the world that our player is going to live in
    public World world;
    public Body b2body;
    private TextureRegion bomberStand;

    public Player (World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("boomer"));
        this.world = world;
        definePlayer();
        bomberStand = new TextureRegion(getTexture(), 64, 0, 16, 16);
        setBounds(0, 0, 16 / Boomer.PPM, 16 / Boomer.PPM);
        setRegion(bomberStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(16 / Boomer.PPM, 304 / Boomer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius((float) (6.5 / Boomer.PPM));
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
