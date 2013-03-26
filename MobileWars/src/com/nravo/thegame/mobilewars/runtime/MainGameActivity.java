package com.nravo.thegame.mobilewars.runtime;

import android.view.View;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public class MainGameActivity extends BaseGameActivity {

    // Window resolution constants
    private static final float MIN_WIDTH_PIXELS = 800;
    private static final float MIN_HEIGHT_PIXELS = 480f;
    private static final float MAX_WIDTH_PIXELS = 1600f;
    private static final float MAX_HEIGHT_PIXELS = 9600f;

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
        // Quite complicated, but works great - taken from MagneTank source code
        FillResolutionPolicy engineFillResolutionPolicy = new FillResolutionPolicy() {
            @Override
            public void onMeasure(final IResolutionPolicy.Callback pResolutionPolicyCallback, final int pWidthMeasureSpec, final int pHeightMeasureSpec) {
                super.onMeasure(pResolutionPolicyCallback, pWidthMeasureSpec, pHeightMeasureSpec);

                final int measuredWidth = View.MeasureSpec.getSize(pWidthMeasureSpec);
                final int measuredHeight = View.MeasureSpec.getSize(pHeightMeasureSpec);

                // Uncomment the following lines to log the pixel values needed for setting up the design-window's values
//				Log.v("Andengine","Design window width & height (in pixels): " + measuredWidth + ", " + measuredHeight);
//				Log.v("Andengine","Design window width & height (in inches): " + measuredWidth / getResources().getDisplayMetrics().xdpi + ", " + measuredHeight / getResources().getDisplayMetrics().ydpi);

                // Determine the device's physical window size.
                actualScreenWidthInches = measuredWidth / getResources().getDisplayMetrics().xdpi;
                actualScreenHeightInches = measuredHeight / getResources().getDisplayMetrics().ydpi;

                // Get an initial width for the camera, and bound it to the minimum or maximum values.
                float actualScaledWidthInPixels = DESIGN_SCREEN_WIDTH_PIXELS *
                        (actualScreenWidthInches / DESIGN_SCREEN_WIDTH_INCHES);
                float boundScaledWidthInPixels = Math.round(Math.max(Math.min(actualScaledWidthInPixels,MAX_WIDTH_PIXELS),MIN_WIDTH_PIXELS));

                // Get the height for the camera based on the width and the height/width ratio of the device
                float boundScaledHeightInPixels = boundScaledWidthInPixels *
                        (actualScreenHeightInches / actualScreenWidthInches);
                // If the height is outside of the set bounds, scale the width to match it.
                if(boundScaledHeightInPixels > MAX_HEIGHT_PIXELS) {
                    float boundAdjustmentRatio = MAX_HEIGHT_PIXELS / boundScaledHeightInPixels;
                    boundScaledWidthInPixels *= boundAdjustmentRatio;
                    boundScaledHeightInPixels *= boundAdjustmentRatio;
                } else if(boundScaledHeightInPixels < MIN_HEIGHT_PIXELS) {
                    float boundAdjustmentRatio = MIN_HEIGHT_PIXELS / boundScaledHeightInPixels;
                    boundScaledWidthInPixels *= boundAdjustmentRatio;
                    boundScaledHeightInPixels *= boundAdjustmentRatio;
                }
                // set the height and width variables
                cameraHeight = boundScaledHeightInPixels;
                cameraWidth = boundScaledWidthInPixels;
                // apply the height and width variables
                mCamera.set(0f, 0f, cameraWidth, cameraHeight);
            }
        };
        mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_FIXED, engineFillResolutionPolicy,
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
        Rectangle rectangle = new Rectangle(cameraWidth/2, cameraHeight/2, 200, 200, mEngine.getVertexBufferObjectManager());
        rectangle.setColor(1, 0, 1);
        mScene.attachChild(rectangle);
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}