package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Sprites.Bomb;

public class BrickLayerCreator extends LayerCreator{

    public BrickLayerCreator(World world, TiledMap map,
                             BodyDef bdef, FixtureDef fdef, PolygonShape shape) {
        fdef.filter.categoryBits = BombSquad.BRICK_BIT;
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
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

    public void test(TiledMap map, Bomb bomb) {
        float x = bomb.x, y = bomb.y;
        boolean up = true, down = true, left = true, right = true;
        TiledMapTileLayer layer;
        for (int i = 1; i <= bomb.radius; i++) {

            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if(layer.getCell((int) x + i, (int) y) != null && right){
                layer.getCell((int) x + i, (int) y).setTile(null);
                right = false;
            }
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if(layer.getCell((int) x - i, (int) y) != null && left){
                layer.getCell((int) x - i, (int) y).setTile(null);
                left = false;
            }
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if(layer.getCell((int) x, (int) y + i) != null && up){
                layer.getCell((int) x, (int) y + i).setTile(null);
                up = false;
            }
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if(layer.getCell((int) x, (int) y - i) != null && down){
                layer.getCell((int) x, (int) y - i).setTile(null);
                down = false;
            }

        }

    }

}
