package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * The SplashScreen class uses entity moidfiers and resolution-independent
 * positioning to show the splash screen of the game.
 *
 * This class is unique because it is seen only at the beginning of the game.
 * After it's hidden, it's never used again. It also has to load as quickly
 * as possible
 */
public class SplashScreens extends ManagedSplashScreen {
    // ===========================================
    // =============== CONSTANTS =================
    // ===========================================
    private static final float EACH_ANIMATION_DURATION = 0.25f;
    private static final float EACH_ANIMATION_PAUSE_DURATION = 2.2f;
    private static final float EACH_SCALE_TO_SIZE = 0.7f * ResourceManager.getInstance().cameraScaleFactorY;

    private static final BitmapTextureAtlas AVATAR_TEXTURE_1 = new BitmapTextureAtlas(
            ResourceManager.getEngine().getTextureManager(), 399, 388, TextureOptions.BILINEAR);
    private static final ITextureRegion AVATAR_TEXTURE_1_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
            AVATAR_TEXTURE_1, ResourceManager.getContext(), "graphics/splash/avatar1.png", 0, 0);
    private static final Sprite AVATAR_1_SPRITE = new Sprite((ResourceManager.getInstance().cameraWidth)/2f,
            0f, AVATAR_TEXTURE_1_TR, ResourceManager.getEngine().getVertexBufferObjectManager());


    @Override
    protected void unloadSplashTextures() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLoadScene() {
        AVATAR_TEXTURE_1.load();
        ResourceManager.getCamera().setCenter(ResourceManager.getInstance().cameraWidth/2f,
                ResourceManager.getInstance().cameraHeight/2f);

        this.setBackgroundEnabled(true);
        this.setBackground(new Background(0.1f, 0.1f, 0.1f));
        this.attachChild(AVATAR_1_SPRITE);
    }
}
