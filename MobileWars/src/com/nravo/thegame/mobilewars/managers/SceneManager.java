package com.nravo.thegame.mobilewars.managers;

import com.nravo.thegame.mobilewars.layers.ManagedLayer;
import com.nravo.thegame.mobilewars.menu.MainMenu;
import com.nravo.thegame.mobilewars.runtime.ManagedScene;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
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

    public void showMainMenu() {
        showScene(MainMenu.getInstance());
    }

    public void showScene(final ManagedScene managedScene) {
        mEngine.getCamera().set(0f, 0f,
                ResourceManager.getInstance().cameraWidth, ResourceManager.getInstance().cameraHeight);
        if (managedScene.hasLoadingScreen) {
            managedScene.setChildScene(managedScene.onLoadingScreenLoadAndShown(), true, true, true);
            if (mLoadingScreenHandlerRegistered) {
                mNumberOfFramesPassed = 1;
                mNextScene.clearChildScene();
                mNextScene.onLoadingScreenUnloadAndHidden();
            } else {
                mEngine.registerUpdateHandler(mLoadingScreenHandler);
                mLoadingScreenHandlerRegistered = true;
            }

            mNextScene = managedScene;
            mEngine.setScene(managedScene);
            return;
        }
        mNextScene = managedScene;
        mEngine.setScene(mNextScene);

        if (currentScene != null) {
            currentScene.onHideManagedScene();
            currentScene.onUnloadManagedScene();
        }
        mNextScene.onLoadManagedScene();
        mNextScene.onShowManagedScene();
        currentScene = mNextScene;
    }

    public void showLayer(final ManagedLayer layer,
                          final boolean suspendSceneDrawing,
                          final boolean suspendSceneUpdates,
                          final boolean suspendSceneTouchEvents) {
        if (mEngine.getCamera().hasHUD()) {
            mCamerahadHud = true;
        } else {
            mCamerahadHud = false;
            HUD placeholderHud = new HUD();
            mEngine.getCamera().setHUD(placeholderHud);
        }
        if (suspendSceneDrawing || suspendSceneUpdates || suspendSceneTouchEvents) {
            mEngine.getCamera().getHUD().setChildScene(layer, suspendSceneDrawing,
                    suspendSceneUpdates, suspendSceneTouchEvents);
            if (mPlaceholderModalScene == null) {
                mPlaceholderModalScene = new Scene();
                mPlaceholderModalScene.setBackgroundEnabled(false);
            }
            currentScene.setChildScene(mPlaceholderModalScene, suspendSceneDrawing,
                    suspendSceneUpdates, suspendSceneTouchEvents);
        } else {
            mEngine.getCamera().getHUD().setChildScene(layer);
        }
        layer.setCamera(mEngine.getCamera());
        layer.setScale(ResourceManager.getInstance().cameraScaleFactorX,
                ResourceManager.getInstance().cameraScaleFactorY);
        layer.onShowManagedLayer();
        mIsLayerShown = true;
    }

    public void hideLayer() {
        if (mIsLayerShown) {
            mEngine.getCamera().getHUD().clearChildScene();
            if (currentScene.hasChildScene()) {
                if (currentScene.getChildScene() == mPlaceholderModalScene) {
                    currentScene.clearChildScene();
                }
            }
            if (!mCamerahadHud) {
                mEngine.getCamera().setHUD(null);
            }
            mIsLayerShown = false;
            currentLayer = null;
        }
    }
}
