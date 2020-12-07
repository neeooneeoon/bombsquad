package com.oneeightfive.bombsquad.Tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.Map.WallLayerCreator;

public class WorldCreator {
    public WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        new WallLayerCreator(world, map, bdef, fdef, shape);
    }
}
