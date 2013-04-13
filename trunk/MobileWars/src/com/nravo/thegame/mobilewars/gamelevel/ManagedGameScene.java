package com.nravo.thegame.mobilewars.gamelevel;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.runtime.ManagedScene;

public abstract class ManagedGameScene extends ManagedScene {
	
    public GameHUD mGameHud;

    private Text loadingText;
    private Scene loadingScene;

    private Sprite mGameBackgroundSprite;

    public ManagedGameScene() {
        this(2f);
    }

    public ManagedGameScene(float loadingScreenMinimumSecondsShown) {
        super(loadingScreenMinimumSecondsShown);
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
    }

    public abstract void onLoadLevel();

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
    public void onLoadScene() {
        ResourceManager.loadGameResources();

        mGameBackgroundSprite = new Sprite(ResourceManager.getInstance().cameraWidth/2,
                ResourceManager.getInstance().cameraHeight/2, ResourceManager.sMenuBackgroundTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        mGameBackgroundSprite.setScale(ResourceManager.getInstance().cameraHeight / ResourceManager.sGameBackgroundTR.getHeight());
        this.attachChild(mGameBackgroundSprite);
        this.onLoadLevel();
    }

    @Override
    public void onShowScene() {
    	mGameHud = new GameHUD(this);
        ResourceManager.getInstance().engine.getCamera().setHUD(mGameHud);
    }

    @Override
    public void onUnloadScene() {
        // detach and unload the scene
        ResourceManager.getInstance().engine.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                ManagedGameScene.this.detachChildren();
                ManagedGameScene.this.clearEntityModifiers();
                ManagedGameScene.this.clearTouchAreas();
                ManagedGameScene.this.clearUpdateHandlers();
            }
        });
    }

    @Override
    public void onHideScene() {
        ResourceManager.getInstance().engine.getCamera().setHUD(null);
    };
}
