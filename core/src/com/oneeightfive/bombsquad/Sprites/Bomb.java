package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.Database.Score;
import com.oneeightfive.bombsquad.Screens.PlayScreen;
import com.oneeightfive.bombsquad.World.DestroyedBrick;

public class Bomb extends Sprite {
    public World world;
    public PlayScreen screen;
    public Body b2body;
    public FixtureDef fdef;

    public float x;
    public float y;

    public double timeLeft;
    public boolean available;

    public boolean flame = false;
    public float timeFlame = 0;

    public int radius;

    public boolean defined;

    public int up, down, left, right;
    int xCoordinate, yCoordinate;

    public Bomb(World world, PlayScreen screen, float x, float y, int radius) {
        this.x = (int)((x * 64 + 24) / BombSquad.PPM);
        this.y = (int)((y * 64 + 24) / BombSquad.PPM);
        this.radius = radius;
        this.world = world;
        this.screen = screen;
        setBounds(0, 0, 48, 48);
        timeLeft = 4;
        available = true;
        defined = false;
    }

    public void blow() {
        available = false;
        flame = true;
        timeFlame = 0;
    }

    public void defineBomb() {
        defined = true;
        BodyDef bdef = new BodyDef();
        bdef.position.set( (int)((x * 64 + 24) / BombSquad.PPM) + 0.5f, (int)((y * 64 + 24) / BombSquad.PPM) + 0.5f);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / BombSquad.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = BombSquad.BOMB_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public boolean check(int x, int y) {
        return x != xCoordinate || y != yCoordinate;
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

    public boolean brick(int x, int y, Array<DestroyedBrick> destroyedBricks) {
        for (DestroyedBrick t : destroyedBricks) {
            if (x == t.getX() && y == t.getY()) {
                return false;
            }
        }
        return true;
    }

    public void playerHitBomb(int xFlame, int yFlame, Bomberman bomberman) {
        if (((int) bomberman.getX()  == xFlame) && ((int) bomberman.getY() == yFlame)) {
            bomberman.currentState = Bomberman.STATE.DEAD;
        }
        if( (int) screen.balloon.b2body.getPosition().x == xFlame && (int) screen.balloon.b2body.getPosition().y == yFlame ){
            screen.balloon.available = false;
            Score.current += 500;
        }

        if( (int) screen.balloon2.b2body.getPosition().x == xFlame && (int) screen.balloon2.b2body.getPosition().y == yFlame ){
            screen.balloon2.available = false;
            Score.current += 500;
        }

        if( (int) screen.balloon3.b2body.getPosition().x == xFlame && (int) screen.balloon3.b2body.getPosition().y == yFlame ){
            screen.balloon3.available = false;
            Score.current += 500;
        }

        if( (int) screen.balloon4.b2body.getPosition().x == xFlame && (int) screen.balloon4.b2body.getPosition().y == yFlame ){
            screen.balloon4.available = false;
            Score.current += 500;
        }

        if( (int) screen.balloon5.b2body.getPosition().x == xFlame && (int) screen.balloon5.b2body.getPosition().y == yFlame ){
            screen.balloon5.available = false;
            Score.current += 500;
        }

    }

    public void FlameCollision(TiledMap map,
                               Array<DestroyedBrick> destroyedBricks, Array<Fixture> fixtures,
                               Bomberman bomberman) {

        float x = this.x, y = this.y;
        TiledMapTileLayer layer1, layer2;
        up = 0;
        down = 0;
        left = 0;
        right = 0;
        layer1 = (TiledMapTileLayer) map.getLayers().get(1);
        layer2 = (TiledMapTileLayer) map.getLayers().get(2);
        playerHitBomb((int) x , (int) y, bomberman);
        for (int i = 1; i <= this.radius; i++) {

            playerHitBomb((int) x + i, (int) y, bomberman);
            if (layer2.getCell((int) x + i, (int) y) != null) {
                xCoordinate = (int) x + i;
                yCoordinate = (int) y;

                if (brick(xCoordinate, yCoordinate, destroyedBricks)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    Score.current += 300;
                    screen.numberOfBricks--;
                    break;
                }
            }

            if (layer1.getCell((int) x + i, (int) y) != null) {
                right--;
                break;
            }
            right++;
        }
        for (int i = 1; i <= this.radius; i++) {

            playerHitBomb((int) x - i, (int) y, bomberman);
            if (layer2.getCell((int) x - i, (int) y) != null) {
                xCoordinate = (int) x - i;
                yCoordinate = (int) y;

                if (brick(xCoordinate, yCoordinate, destroyedBricks)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    Score.current += 300;
                    screen.numberOfBricks--;
                    break;
                }
            }

            if (layer1.getCell((int) x - i, (int) y) != null) {
                left--;
                break;
            }
            left++;
        }
        for (int i = 1; i <= this.radius; i++) {

            playerHitBomb((int) x, (int) y + i, bomberman);
            if (layer2.getCell((int) x, (int) y + i) != null) {
                xCoordinate = (int) x;
                yCoordinate = (int) y + i;

                if (brick(xCoordinate, yCoordinate, destroyedBricks)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    Score.current += 300;
                    screen.numberOfBricks--;
                    break;
                }
            }

            if (layer1.getCell((int) x, (int) y + i) != null) {
                up--;
                break;
            }
            up++;
        }
        for (int i = 1; i <= this.radius; i++) {

            playerHitBomb((int) x, (int) y - i, bomberman);
            if (layer2.getCell((int) x, (int) y - i) != null) {
                xCoordinate = (int) x;
                yCoordinate = (int) y - i;

                if (brick(xCoordinate, yCoordinate, destroyedBricks)) {
                    layer2.getCell(xCoordinate, yCoordinate).setTile(null);
                    destroyedBricks.add(new DestroyedBrick(xCoordinate, yCoordinate));
                    delete(fixtures);
                    Score.current += 300;
                    screen.numberOfBricks--;
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
