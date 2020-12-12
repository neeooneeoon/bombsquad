package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class WorldCreator {

    public WallLayer wallLayer;
    public BrickLayer brickLayer;
    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape shape;
    public WorldCreator(World world, TiledMap map, Array<Fixture> worldBody) {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();

        wallLayer = new WallLayer(world, map, bdef, fdef, shape);
        brickLayer = new BrickLayer(world, map, bdef, fdef, shape, worldBody);
    }

}
