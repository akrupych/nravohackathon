package com.nravo.thegame.mobilewars.runtime;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import com.nravo.thegame.mobilewars.menu.MainMenu;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public class MainGameActivity extends BaseGameActivity {

    // Window resolution constants
    private static final float DESIGN_SCREEN_WIDTH_INCHES = 4.472441f;
    private static final float DESIGN_SCREEN_HEIGHT_INCHES = 2.805118f;
    private static final float DESIGN_SCREEN_WIDTH_PIXELS = 800;
    private static final float DESIGN_SCREEN_HEIGHT_PIXELS = 480;

    private static final float MIN_WIDTH_PIXELS = 800;
    private static final float MIN_HEIGHT_PIXELS = 480f;
    private static final float MAX_WIDTH_PIXELS = 1600f;
    private static final float MAX_HEIGHT_PIXELS = 960f;

    public float cameraWidth;
    public float cameraHeight;
    public float actualScreenWidthInches;
    public float actualScreenHeightInches;

    @Override
    public void onBackPressed() {
        if (ResourceManager.getInstance().engine != null) {

        }
        super.onBackPressed();
    }

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

        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
                new Camera(0, 0, cameraWidth, cameraHeight));
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public void onCreateResources(
            OnCreateResourcesCallback pOnCreateResourcesCallback) {
        ResourceManager.setup(this, this.getEngine(), this.getApplicationContext(), cameraWidth, cameraHeight,
                cameraWidth/DESIGN_SCREEN_WIDTH_PIXELS, cameraHeight/DESIGN_SCREEN_HEIGHT_PIXELS);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        SceneManager.getInstance().showMainMenu();
        pOnCreateSceneCallback.onCreateSceneFinished(MainMenu.getInstance());
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {
//        Rectangle rectangle1 = new Rectangle(cameraWidth/2, cameraHeight/2, 200, 200, mEngine.getVertexBufferObjectManager());
//        rectangle1.setColor(1, 0, 1);
//        Rectangle rectangle2 = new Rectangle(0, 0, 200, 200, mEngine.getVertexBufferObjectManager());
//        rectangle2.setColor(0, 0, 1);
//        mScene.attachChild(rectangle1);
//        mScene.attachChild(rectangle2);
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


    // Ensure activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        System.exit(0);
    }
}