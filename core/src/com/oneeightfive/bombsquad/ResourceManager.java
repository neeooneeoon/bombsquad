package com.oneeightfive.bombsquad;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable {
    public static final int V_WIDTH = 32;
    public static final int V_HEIGHT = 16;
    public static final float PPM = 64;



    private static final ResourceManager instance = new ResourceManager();

    private final AssetManager assetManager;

    private ResourceManager() {
        assetManager = new AssetManager();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
