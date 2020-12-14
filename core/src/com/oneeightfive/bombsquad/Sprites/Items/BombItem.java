package com.oneeightfive.bombsquad.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class BombItem extends Sprite {

    private final PlayScreen screen;
    public World world;
    public Body b2Body;
    public BodyDef bdef;

    private final float x;
    private final float y;

    TextureRegion texture;

    public BombItem(PlayScreen screen, float x, float y) {
        this.x = x;
        this.y = y;

        this.screen = screen;
        this.world = screen.getWorld();

        texture = new TextureRegion(screen.getItemAtlas().findRegion("BombPowerup"), 0, 0, 32, 32);

        defineItem();
        setBounds(0, 0, 32 / BombSquad.PPM, 32 / BombSquad.PPM);
        setRegion(texture);
    }

    public void defineItem() {
        bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18 / BombSquad.PPM);
        fdef.filter.categoryBits = BombSquad.POWER_UP;
        fdef.filter.maskBits = BombSquad.BOMBERMAN_BIT;

        shape.setPosition(new Vector2(8.5F / BombSquad.PPM, 8.5F / BombSquad.PPM));
        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public void update() {
        setPosition(b2Body.getPosition().x - .1F, b2Body.getPosition().y - .1F);
    }
}
