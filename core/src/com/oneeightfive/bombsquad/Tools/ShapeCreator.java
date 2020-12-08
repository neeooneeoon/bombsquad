package com.oneeightfive.bombsquad.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;

public class ShapeCreator {
    public static ChainShape createEllipse(float width, float height)
    {
        return createEllipse(width, height, 128);
    }

    public static ChainShape createEllipse(float width, float height, int STEPS)
    {
        ChainShape ellipse = new ChainShape();
        Vector2[] verts = new Vector2[STEPS];

        for(int i = 0; i < STEPS; i++)
        {
            float t = (float)(i*2*Math.PI)/STEPS;
            verts[i] = new Vector2(width * (float)Math.cos(t), height * (float)Math.sin(t));
        }

        ellipse.createLoop(verts);
        return ellipse;
    }
}
