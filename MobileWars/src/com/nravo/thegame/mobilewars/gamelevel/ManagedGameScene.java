package com.nravo.thegame.mobilewars.gamelevel;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.runtime.ManagedScene;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;

public abstract class ManagedGameScene extends ManagedScene {
    public HUD gameHud = new HUD();

    private Text loadingText;
    private Scene loadingScene;

    public ManagedGameScene() {
        this(2f);
    }

    public ManagedGameScene(float loadingScreenMinimumSecondsShown) {
        super(loadingScreenMinimumSecondsShown);
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        gameHud.setScaleCenter(0f, 0f);
        gameHud.setScale(ResourceManager.getInstance().cameraScaleFactorX,
                ResourceManager.getInstance().cameraScaleFactorY);
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        // Setup and return the loading screen
        loadingScene = new Scene();
        loadingScene.setBackgroundEnabled(true);
        loadingText = new Text(0, 0, ResourceManager.sFontDefault32Bold, "Loading...",
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        loadingScene.setPosition(loadingText.getWidth()/2f, ResourceManager.getInstance().cameraHeight -
                loadingText.getHeight()/2f);
        loadingScene.attachChild(loadingText);
        return loadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        loadingText.detachSelf();
        loadingText = null;
        loadingScene = null;
    }

    @Override
    public void onHideScene() {
    };
}
