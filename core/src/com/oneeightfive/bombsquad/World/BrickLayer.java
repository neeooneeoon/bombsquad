package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Sprites.Bomb;
import com.oneeightfive.bombsquad.Sprites.Bomberman;

import java.util.ArrayList;
import java.util.List;

public class BrickLayer extends Layer {

    public BrickLayer(World world, TiledMap map,
                      BodyDef bdef, FixtureDef fdef, PolygonShape shape, Array<Fixture> worldBody) {
        fdef.filter.categoryBits = BombSquad.BRICK_BIT;
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BombSquad.PPM,
                    (rect.getY() + rect.getHeight() / 2) / BombSquad.PPM);
            Body body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / BombSquad.PPM, rect.getHeight() / 2 / BombSquad.PPM);
            fdef.shape = shape;
            worldBody.add(body.createFixture(fdef));
        }

    }


}
