package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class Walls extends InteractiveTileObject  {
    public Walls(World wold, TiledMap map, Rectangle bounds) {
        super(wold, map, bounds);
    }
}
