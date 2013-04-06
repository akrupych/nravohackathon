package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;

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
    private static final float mEachAnimationDuration = 0.25f;
    private static final float mEachAnimationPauseDuration = 2.2f;
    private static final float mEachScaleToSize = 0.7f * ResourceManager.getInstance().cameraScaleFactorY;

    private static final BitmapTextureAtlas mAvatarTextureAtlas = new BitmapTextureAtlas(
            ResourceManager.getEngine().getTextureManager(), 399, 388, TextureOptions.BILINEAR);
    private static final ITextureRegion mAvatarTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
            mAvatarTextureAtlas, ResourceManager.getContext(), "graphics/splash/avatar1.png", 0, 0);
    private static final Sprite mAvatarSprite = new Sprite((ResourceManager.getInstance().cameraWidth)/2f,
            0f, mAvatarTR, ResourceManager.getEngine().getVertexBufferObjectManager());

    private static final MoveModifier mAvatarEntityModifier = new
            MoveModifier(5, ResourceManager.getInstance().cameraWidth, ResourceManager.getInstance().cameraHeight/2f,
            0, ResourceManager.getInstance().cameraHeight/2f);


    @Override
    public void onLoadScene() {
        mAvatarTextureAtlas.load();
        ResourceManager.getCamera().setCenter(ResourceManager.getInstance().cameraWidth/2f,
                ResourceManager.getInstance().cameraHeight/2f);

        this.setBackgroundEnabled(true);
        this.setBackground(new Background(0.1f, 0.1f, 0.1f));

        this.attachChild(mAvatarSprite);

        mAvatarEntityModifier.addModifierListener(new IModifier.IModifierListener<IEntity>() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                SceneManager.getInstance().showMainMenu();
            }
        });

        this.registerUpdateHandler(new IUpdateHandler() {
            int counter = 0;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                this.counter++;
                if (this.counter > 2) {
                    SplashScreens.mAvatarSprite.registerEntityModifier(
                            SplashScreens.mAvatarEntityModifier
                    );
                }
            }

            @Override
            public void reset() {
            }
        });
    }

    @Override
    protected void unloadSplashTextures() {
        mAvatarTextureAtlas.unload();
    }
}
