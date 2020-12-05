package com.oneeightfive.bombsquad.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.ResourceManager;

public class WorldCreator {
    public WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        //Wall Layer
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / ResourceManager.PPM, (rect.getY() + rect.getHeight() / 2) / ResourceManager.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / ResourceManager.PPM, rect.getHeight() / 2 / ResourceManager.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
