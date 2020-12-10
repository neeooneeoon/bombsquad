package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.World.BrickLayerCreator;
import com.oneeightfive.bombsquad.World.WallLayerCreator;

public class WorldCreator {

    public WallLayerCreator wallLayerCreator;
    public BrickLayerCreator brickLayerCreator;
    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape shape;
    public WorldCreator(World world, TiledMap map, Array<Fixture> worldBody) {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        shape = new PolygonShape();

        wallLayerCreator = new WallLayerCreator(world, map, bdef, fdef, shape);
        brickLayerCreator = new BrickLayerCreator(world, map, bdef, fdef, shape, worldBody);
    }

    public void deleteBrick(World world, TiledMap map, Array<Fixture> worldBody){
        world = new World(new Vector2(0, 0), true);
        wallLayerCreator = new WallLayerCreator(world, map, bdef, fdef, shape);
        brickLayerCreator.delete(worldBody);
    }

}
