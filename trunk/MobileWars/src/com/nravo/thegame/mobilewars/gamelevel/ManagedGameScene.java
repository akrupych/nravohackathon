package com.nravo.thegame.mobilewars.gamelevel;

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
