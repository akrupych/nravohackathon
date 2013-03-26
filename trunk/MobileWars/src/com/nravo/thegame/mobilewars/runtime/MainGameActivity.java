package com.nravo.thegame.mobilewars.runtime;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public class MainGameActivity extends BaseGameActivity {

    // Window resolution constants
    private static final float MIN_WIDTH_PIXELS = 800;
    private static final float MIN_HEIGHT_PIXELS = 480f;
    private static final float MAX_WIDTH_PIXELS = 1600f;
    private static final float MAX_HEIGHT_PIXELS = 960f;

    private static final float DESIGN_SCREEN_WIDTH_INCHES = 4.472441f;
    private static final float DESIGN_SCREEN_HEIGHT_INCHES = 2.805118f;
    private static final float DESIGN_SCREEN_WIDTH_PIXELS = 1280f;
    private static final float DESIGN_SCREEN_HEIGHT_PIXELS = 800f;

    public float cameraWidth;
    public float cameraHeight;
    public float actualScreenWidthInches;
    public float actualScreenHeightInches;
    private Camera mCamera;
    private Scene mScene;

    @Override
    public EngineOptions onCreateEngineOptions() {
        // Determine the device's physical screen size.
        actualScreenWidthInches = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().xdpi;
        actualScreenHeightInches = getResources().getDisplayMetrics().heightPixels / getResources().getDisplayMetrics().ydpi;
        // Set the Camera's Width & Height according to the device with which you design the game.
        cameraWidth = Math.round(Math.max(Math.min(DESIGN_SCREEN_WIDTH_PIXELS
                * (actualScreenWidthInches / DESIGN_SCREEN_WIDTH_INCHES), MAX_WIDTH_PIXELS), MIN_WIDTH_PIXELS));
        cameraHeight = Math.round(Math.max(Math.min(DESIGN_SCREEN_HEIGHT_PIXELS
                * (actualScreenHeightInches / DESIGN_SCREEN_HEIGHT_INCHES), MAX_HEIGHT_PIXELS), MIN_HEIGHT_PIXELS));
        mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
                mCamera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public void onCreateResources(
            OnCreateResourcesCallback pOnCreateResourcesCallback) {
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        mScene = new Scene();
        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {
        Rectangle rectangle1 = new Rectangle(cameraWidth/2, cameraHeight/2, 200, 200, mEngine.getVertexBufferObjectManager());
        rectangle1.setColor(1, 0, 1);
        Rectangle rectangle2 = new Rectangle(0, 0, 200, 200, mEngine.getVertexBufferObjectManager());
        rectangle2.setColor(0, 0, 1);
        mScene.attachChild(rectangle1);
        mScene.attachChild(rectangle2);
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}