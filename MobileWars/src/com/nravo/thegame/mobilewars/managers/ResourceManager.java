package com.nravo.thegame.mobilewars.managers;

import android.content.Context;
import com.nravo.thegame.mobilewars.runtime.MainGameActivity;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

public class ResourceManager {
    private static final ResourceManager INSTANCE = new ResourceManager();
    private static final TextureOptions NORMAL_TEXTURE_OPTION = TextureOptions.BILINEAR;

    private final String MENU_GRAPHICS_PATH = "graphics/menu/";

    public Engine engine;
    public Context context;
    public MainGameActivity activity;
    public float cameraWidth;
    public float cameraHeight;
    public float cameraScaleFactorX;
    public float cameraScaleFactorY;

    // ================== GAME RESOURCES =====================
    // TR = Texture Region
    public static ITextureRegion sGameBackgroundTR;

    // ================== MENU RESOURCES =====================
    // TR = Texture Region
    public static ITextureRegion sMenuBackgroundTR;

    public static Font sFontDefault32Bold;

    private String mPreviousAssetBasePath = "";

    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        return INSTANCE;
    }

    public static void setup(MainGameActivity activity, Engine engine, Context context, float cameraWidth, float cameraHeight,
                      float cameraScaleFactorX, float cameraScaleFactorY) {
        getInstance().activity = activity;
        getInstance().engine = engine;
        getInstance().context = context;
        getInstance().cameraWidth = cameraWidth;
        getInstance().cameraHeight = cameraHeight;
        getInstance().cameraScaleFactorX = cameraScaleFactorX;
        getInstance().cameraScaleFactorY = cameraScaleFactorY;
    }

    public static void loadGameResources() {

    }

    public static void loadMenuResources() {
        getInstance().loadMenuTextures();
    }

    public static void unloadGameResources() {

    }

    public static void unloadMenuResources() {

    }

    public static void unloadSharedResources() {
        // shared sounds, fonts, textures
    }

    // ===================================================
    // ================ PRIVATE METHODS ==================
    // ===================================================
    private void loadMenuTextures() {
        mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(MENU_GRAPHICS_PATH);

        if (sMenuBackgroundTR == null) {
            final IBitmapTextureAtlasSource bitmapTextureAtlasSource =
                    AssetBitmapTextureAtlasSource.create(activity.getAssets(),
                            BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + "bg.png");
            final BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                    activity.getTextureManager(), bitmapTextureAtlasSource.getTextureWidth(),
                    bitmapTextureAtlasSource.getTextureHeight(), NORMAL_TEXTURE_OPTION);
            final TextureRegion textureRegion = new TextureRegion(bitmapTextureAtlas, 0, 0,
                    bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), false);
            bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
            bitmapTextureAtlas.load();
            sMenuBackgroundTR = textureRegion;
        }
    }

}
