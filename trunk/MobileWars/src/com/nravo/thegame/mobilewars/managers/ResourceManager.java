package com.nravo.thegame.mobilewars.managers;

import android.content.Context;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;

public class ResourceManager {
    private static final ResourceManager INSTANCE = new ResourceManager();

    public Engine engine;
    public Context context;
    public float cameraWidth;
    public float cameraHeight;
    public float cameraScaleFactorX;
    public float cameraScaleFactorY;

    public static ITextureRegion gameBackgroundTextureRegion;
    public static ITextureRegion gameMenuTextureRegion;

    public static Font fontDefault32Bold;

    private String mPreviousAssetBasePath = "";

    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        return INSTANCE;
    }

    public void setup(Engine engine, Context context, float cameraWidth, float cameraHeight,
                      float cameraScaleFactorX, float cameraScaleFactorY) {
        this.engine = engine;
        this.context = context;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.cameraScaleFactorX = cameraScaleFactorX;
        this.cameraScaleFactorY = cameraScaleFactorY;
    }

    public static void loadGameResources() {

    }

    public static void loadMenuResources() {

    }

    public static void unloadGameResources() {

    }

    public static void unloadMenuResources() {

    }

    public static void unloadSharedResources() {
        // shared sounds, fonts, textures
    }
}
