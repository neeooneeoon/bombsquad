package com.oneeightfive.bombsquad.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.BombSquad;

public class WallLayerCreator {

    public WallLayerCreator(World world, TiledMap map,
                            BodyDef bdef, FixtureDef fdef, PolygonShape shape) {
        fdef.filter.categoryBits = BombSquad.WALL_BIT;
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BombSquad.PPM,
                    (rect.getY() + rect.getHeight() / 2) / BombSquad.PPM);

            Body body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / BombSquad.PPM, rect.getHeight() / 2 / BombSquad.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }


}
