package com.oneeightfive.bombsquad;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable {

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
