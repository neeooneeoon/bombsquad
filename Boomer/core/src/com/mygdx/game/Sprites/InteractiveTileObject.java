package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Boomer;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMap tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Boomer.PPM, (bounds.getY() + bounds.getHeight() / 2) / Boomer.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2 / Boomer.PPM, bounds.getHeight() / 2 / Boomer.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
