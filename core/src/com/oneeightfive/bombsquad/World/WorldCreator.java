package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.World.BrickLayerCreator;
import com.oneeightfive.bombsquad.World.WallLayerCreator;

public class WorldCreator {

    public WallLayerCreator wallLayerCreator;
    public BrickLayerCreator brickLayerCreator;
    public WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        wallLayerCreator = new WallLayerCreator(world, map, bdef, fdef, shape);
        brickLayerCreator = new BrickLayerCreator(world, map, bdef, fdef, shape);
    }
}
