package com.oneeightfive.bombsquad.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oneeightfive.bombsquad.Screens.PlayScreen;

public class Bomb extends Sprite {
    public World world;
    public PlayScreen screen;
    public Body b2body;

    public float x;
    public float y;

    public double timeLeft;
    public boolean available = false;

    public boolean flame = false;
    public float timeFlame = 0;

    public int radius = 1;

    private final Animation<TextureRegion> bombAnimation;
    private final Animation<TextureRegion> flameAnimation;
    public final static float animationSpeed = 0.1f;

    public Bomb(World world, PlayScreen screen, float x, float y, int radius) {
        this.world = world;
        this.screen = screen;
        defineBomb();
        setBounds(0, 0, 32, 32);

        this.x = x;
        this.y = y;
        this.radius = radius;

        timeLeft = 4;
        available = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Bomb_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Bomb_f02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Bomb_f03"), 0, 0, 48, 48));
        bombAnimation = new Animation<TextureRegion>(animationSpeed, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Flame_f00"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Flame_f01"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Flame_F02"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Flame_F03"), 0, 0, 48, 48));
        frames.add(new TextureRegion(screen.getWeaponAtlas().findRegion("Flame_F04"), 0, 0, 48, 48));
        flameAnimation = new Animation<TextureRegion>(animationSpeed, frames);
        frames.clear();
    }

    public void blow(){
        available = false;
        flame = true;
        timeFlame = 0;
    }

    public void clear() {
        flame = false;
    }

    public void defineBomb() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x - 16, y - 16);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 16, new Vector2(0, 0), 0);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    private void drawBoom() {
        screen.getBatch().begin();

        for (int i = 0; i < screen.getPlayer().numberOfBombs; i++) {
            if (screen.getPlayer().bombs[i].available == true) {
                if (screen.getPlayer().bombs[i].timeLeft < 0) {
                    screen.getPlayer().bombs[i].blow();
                }
                screen.drawWeaponAnimation(bombAnimation, true, screen.getPlayer().bombs[i].x, screen.getPlayer().bombs[i].y);
                screen.getPlayer().bombs[i].timeLeft -= Gdx.graphics.getDeltaTime();
            }
            if (screen.getPlayer().bombs[i].flame == true) {
                if (screen.getPlayer().bombs[i].timeFlame <= 2 * animationSpeed) {
                    //drawFlame(i);
                } else {
                    screen.getPlayer().bombs[i].clear();
                }
            }
        }

        screen.getBatch().end();
    }
}
