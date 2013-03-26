package com.nravo.thegame.mobilewars.managers;

import com.nravo.thegame.mobilewars.layers.ManagedLayer;
import com.nravo.thegame.mobilewars.runtime.ManagedScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

public class SceneManager {
    private static final SceneManager INSTANCE = new SceneManager();

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public ManagedScene currentScene;
    private ManagedScene mNextScene;
    private Engine mEngine = ResourceManager.getInstance().engine;

    private int mNumberOfFramesPassed = -1;
    private boolean mLoadingScreenHandlerRegistered = false;

    private IUpdateHandler mLoadingScreenHandler = new IUpdateHandler() {
        @Override
        public void onUpdate(float pSecondsElapsed) {
            mNumberOfFramesPassed++;
            mNextScene.elapsedLoadingScreenTime += pSecondsElapsed;
            if (mNumberOfFramesPassed == 1) {
                if (currentScene != null) {
                    currentScene.onHideManagedScene();
                    currentScene.onUnloadManagedScene();
                }
                mNextScene.onLoadManagedScene();
            }

            if (mNumberOfFramesPassed > 1 && mNextScene.elapsedLoadingScreenTime >= mNextScene.minLoadingScreenTime) {
                mNextScene.clearChildScene();;
                mNextScene.onLoadingScreenUnloadAndHidden();
                mNextScene.onShowManagedScene();
                currentScene = mNextScene;

                // reset the handler and loading screen variables for another use
                mNextScene.elapsedLoadingScreenTime = 0;
                mNumberOfFramesPassed = -1;
                mEngine.unregisterUpdateHandler(this);
                mLoadingScreenHandlerRegistered = false;
            }
        }

        @Override public void reset() {}
    };

    private boolean mCamerahadHud = false;
    public boolean mIsLayerShown = false;
    private Scene mPlaceholderModalScene;
    public ManagedLayer currentLayer;
}
