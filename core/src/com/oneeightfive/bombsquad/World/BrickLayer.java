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
    public int up, down, left, right;
    int xCoordinate, yCoordinate;
    Array<DestroyedBrick> destroyedBricks = new Array<>();

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

    public boolean check(int x, int y) {
        if (x == xCoordinate && y == yCoordinate) {
            System.out.println("Destroyed Brick's Coordinate:   " + xCoordinate + " " + x);
            return false;
        }
        return true;
    }

    public void delete(Array<Fixture> fixtures) {
        for (Fixture fixture : fixtures) {
            if (!check((int) fixture.getBody().getPosition().x, (int) fixture.getBody().getPosition().y)) {
                Filter filter = new Filter();
                filter.categoryBits = BombSquad.DEFAULT_BIT;
                fixture.setFilterData(filter);
            }
        }

    }

    public boolean brick(int x, int y) {
        for (DestroyedBrick t : destroyedBricks) {
            if (x == t.getX() && y == t.getY()) {
                return false;
            }
        }
        return true;
    }

    public void playerHitBomb(int xCoordinate, int yCoordinate, Bomberman bomberman) {
        if (((int) bomberman.getX()  == xCoordinate) && ((int) bomberman.getY() == yCoordinate)) {
            System.out.println("Player dead");
            bomberman.currentState = Bomberman.STATE.DEAD;
        }
    }

    public void test(TiledMap map, Bomb bomb, Array<Fixture> fixtures, Bomberman bomberman) {

        float x = bomb.x, y = bomb.y;
        TiledMapTileLayer layer1, layer2;
        up = 0;
        down = 0;
        left = 0;
        right = 0;
        layer1 = (TiledMapTileLayer) map.getLayers().get(1);
        layer2 = (TiledMapTileLayer) map.getLayers().get(2);

        for (int i = 0; i <= bomb.radius; i++) {

            playerHitBomb((int) x + i, (int) y, bomberman);
            if (layer2.getCell((int) x + i, (int) y) != null) {
                xCoordinate = (int) x + i;
                yCoordinate = (int) y;

                if (brick(xCoordinate, yCoordinate)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    break;
                }
            }

            if (layer1.getCell((int) x + i, (int) y) != null) {
                right--;
                break;
            }
            right++;
        }
        for (int i = 1; i <= bomb.radius; i++) {

            playerHitBomb((int) x - i, (int) y, bomberman);
            if (layer2.getCell((int) x - i, (int) y) != null) {
                xCoordinate = (int) x - i;
                yCoordinate = (int) y;

                if (brick(xCoordinate, yCoordinate)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    break;
                }
            }

            if (layer1.getCell((int) x - i, (int) y) != null) {
                left--;
                break;
            }
            left++;
        }
        for (int i = 1; i <= bomb.radius; i++) {

            playerHitBomb((int) x, (int) y + i, bomberman);
            if (layer2.getCell((int) x, (int) y + i) != null) {
                xCoordinate = (int) x;
                yCoordinate = (int) y + i;

                if (brick(xCoordinate, yCoordinate)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    break;
                }
            }

            if (layer1.getCell((int) x, (int) y + i) != null) {
                up--;
                break;
            }
            up++;
        }
        for (int i = 1; i <= bomb.radius; i++) {

            playerHitBomb((int) x, (int) y - i, bomberman);
            if (layer2.getCell((int) x, (int) y - i) != null) {
                xCoordinate = (int) x;
                yCoordinate = (int) y - i;

                if (brick(xCoordinate, yCoordinate)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    break;
                }
            }

            if (layer1.getCell((int) x, (int) y - i) != null) {
                down--;
                break;
            }
            down++;
        }

    }

}
