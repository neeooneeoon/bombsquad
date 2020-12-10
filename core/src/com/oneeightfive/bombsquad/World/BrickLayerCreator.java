package com.oneeightfive.bombsquad.World;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Sprites.Bomb;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrickLayerCreator extends LayerCreator {
    public int up, down, left, right;
    int xCoordinate, yCoordinate;


    public BrickLayerCreator(World world, TiledMap map,
                             BodyDef bdef, FixtureDef fdef, PolygonShape shape, Array<Fixture> worldBody) {
        fdef.filter.categoryBits = BombSquad.BRICK_BIT;
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
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

    public boolean check(int x, int y) {
        if ((int) x == xCoordinate && (int) y == yCoordinate) {
            System.out.println("b   " + xCoordinate + " " + (int) x);
            return false;
        }

        return true;
    }

    public void delete(Array<Fixture> fixtures) {
        for (Fixture fixture : fixtures) {
            if (!check((int) fixture.getBody().getPosition().x, (int) fixture.getBody().getPosition().y)) {
                System.out.println("check");
                Filter filter = new Filter();
                filter.categoryBits = BombSquad.DEFAULT_BIT;
                fixture.setFilterData(filter);
            }
        }

    }

    public void test(TiledMap map, Bomb bomb, World world, Array<Fixture> fixtures) {
        float x = bomb.x, y = bomb.y;
        TiledMapTileLayer layer;
        up = 0;
        down = 0;
        left = 0;
        right = 0;
        for (int i = 1; i <= bomb.radius; i++) {
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if (layer.getCell((int) x + i, (int) y) != null) {
                layer.getCell((int) x + i, (int) y).setTile(null);
                xCoordinate = (int) x + i;
                yCoordinate = (int) y;
                delete(fixtures);
                break;
            }
            right++;
        }
        for (int i = 1; i <= bomb.radius; i++) {
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if (layer.getCell((int) x - i, (int) y) != null) {
                layer.getCell((int) x - i, (int) y).setTile(null);
                xCoordinate = (int) x - i;
                yCoordinate = (int) y;
                delete(fixtures);
                break;
            }
            left++;
        }
        for (int i = 1; i <= bomb.radius; i++) {
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if (layer.getCell((int) x, (int) y + i) != null) {
                layer.getCell((int) x, (int) y + i).setTile(null);
                xCoordinate = (int) x ;
                yCoordinate = (int) y+i;
                delete(fixtures);
                break;
            }
            up++;
        }

        for (int i = 1; i <= bomb.radius; i++) {
            layer = (TiledMapTileLayer) map.getLayers().get(1);
            if (layer.getCell((int) x, (int) y - i) != null) {
                layer.getCell((int) x, (int) y - i).setTile(null);
                xCoordinate = (int) x ;
                yCoordinate = (int) y-i;
                delete(fixtures);
                break;
            }
            down++;
        }

    }

}
