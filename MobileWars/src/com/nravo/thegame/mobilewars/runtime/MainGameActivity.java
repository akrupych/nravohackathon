package com.nravo.thegame.mobilewars.runtime;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SFXManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import com.nravo.thegame.mobilewars.menu.SplashScreens;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.BaseGameActivity;

public class MainGameActivity extends BaseGameActivity {

    // Window resolution constants
    private static final float DESIGN_SCREEN_WIDTH_INCHES = 4.472441f;
    private static final float DESIGN_SCREEN_HEIGHT_INCHES = 2.805118f;
    private static final float DESIGN_SCREEN_WIDTH_PIXELS = 1280;
    private static final float DESIGN_SCREEN_HEIGHT_PIXELS = 800;

    private static final float MIN_WIDTH_PIXELS = 800;
    private static final float MIN_HEIGHT_PIXELS = 480f;
    private static final float MAX_WIDTH_PIXELS = 1600f;
    private static final float MAX_HEIGHT_PIXELS = 960f;

    public float cameraWidth;
    public float cameraHeight;
    public float actualScreenWidthInches;
    public float actualScreenHeightInches;
    
	private Camera mCamera;

    @Override
    public void onBackPressed() {
        if (ResourceManager.getInstance().engine != null) {
            if (SceneManager.getInstance().mIsLayerShown) {
                SceneManager.getInstance().currentLayer.onHideLayer();
            } else if (SceneManager.getInstance().currentScene.getClass().equals(GameLevel.class)) {
                ((GameLevel) SceneManager.getInstance().currentScene).disposeLevel();
                SceneManager.getInstance().showMainMenu();
            } else {
                super.onBackPressed();
                System.exit(RESULT_OK);
            }
        } else {
            super.onBackPressed();
            System.exit(RESULT_OK);
        }
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

        mCamera = new Camera(0, 0, cameraWidth, cameraHeight);
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(),
                mCamera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public void onCreateResources(
            OnCreateResourcesCallback pOnCreateResourcesCallback) {
        ResourceManager.setup(this, this.getEngine(), this.getApplicationContext(), mCamera, cameraWidth, cameraHeight,
                cameraWidth/DESIGN_SCREEN_WIDTH_PIXELS, cameraHeight/DESIGN_SCREEN_HEIGHT_PIXELS);
        ResourceManager.getEngine().registerUpdateHandler(new FPSLogger());
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
    	mEngine.enableVibrator(this);
        //SceneManager.getInstance().showScene(new SplashScreens());
        SceneManager.getInstance().showMainMenu();
    	SFXManager.playMusic();
        pOnCreateSceneCallback.onCreateSceneFinished(mEngine.getScene());
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    // ACTIVITY LICFECYCLE
    @Override
    protected void onPause() {
        super.onPause();
        if (this.isGameLoaded()) {
            SFXManager.pauseMusic();
        }
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
        if (this.isGameLoaded()) {
            SFXManager.resumeMusic();
        }
    }

   // Ensure activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
  //      System.exit(0);
   }
}