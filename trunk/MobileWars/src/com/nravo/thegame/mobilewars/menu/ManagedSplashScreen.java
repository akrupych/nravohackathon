package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.runtime.ManagedScene;
import org.andengine.entity.scene.Scene;

/**
 * Scene for a splash screen
 */
public abstract class ManagedSplashScreen extends ManagedScene {


    public ManagedSplashScreen thisManagedSplashScene = this;

    public ManagedSplashScreen() {
        this(0f);
    }

    public ManagedSplashScreen(float pLoadingScreenMinimumSecondsShown) {
        super(pLoadingScreenMinimumSecondsShown);
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
        this.setPosition(0, ResourceManager.getInstance().cameraHeight/2f);
        this.dispose();
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
    }

    @Override
    public void onShowScene() {
    }

    @Override
    public void onHideScene() {
    }

    @Override
    public void onUnloadScene() {
        ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                thisManagedSplashScene.detachChildren();
                for (int i = 0; i < thisManagedSplashScene.getChildCount(); i++) {
                    thisManagedSplashScene.getChildByIndex(i).dispose();
                }
                thisManagedSplashScene.clearEntityModifiers();
                thisManagedSplashScene.clearTouchAreas();
                thisManagedSplashScene.clearUpdateHandlers();
                thisManagedSplashScene.unloadSplashTextures();
            }
        });
    }

    protected abstract void unloadSplashTextures();
}
