package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.gamelevel.Levels;
import com.nravo.thegame.mobilewars.input.GrowButton;
import com.nravo.thegame.mobilewars.input.GrowToggleButton;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SFXManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class MainMenu extends ManagedMenuScene {

    private static final MainMenu INSTANCE = new MainMenu();

    private static final float CAMERA_WIDTH = ResourceManager.getInstance().cameraWidth;
    private static final float CAMERA_HEIGHT = ResourceManager.getInstance().cameraHeight;

    private Sprite mMenuBackgroundSprite;

    public static MainMenu getInstance() {
        return INSTANCE;
    }

    public MainMenu() {
        this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        throw new UnsupportedOperationException();  // No loading screen for menu - no need to implement this
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        throw new UnsupportedOperationException(); // No loading screen for menu - no need to implement this
    }


    @Override
    public void onLoadScene() {
        ResourceManager.loadMenuResources();

        // create the background
        mMenuBackgroundSprite = new Sprite(ResourceManager.getInstance().cameraWidth / 2f,
                ResourceManager.getInstance().cameraHeight / 2f,
                ResourceManager.sMenuBackgroundTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        mMenuBackgroundSprite.setScale(ResourceManager.getInstance().cameraHeight / ResourceManager.sMenuBackgroundTR.getHeight());
        mMenuBackgroundSprite.setZIndex(-5000);
        this.attachChild(mMenuBackgroundSprite);

        GrowToggleButton musicToggleButton = new GrowToggleButton(ResourceManager.musicToggleTTR.getWidth() / 2,
                ResourceManager.musicToggleTTR.getHeight() / 2, ResourceManager.musicToggleTTR, !SFXManager.isMusicMuted()) {
            @Override
            public void onClick() {
                SFXManager.toggleMusicMuted();
            }

            @Override
            public boolean checkState() {
                return !SFXManager.isMusicMuted();
            }
        };

        Rectangle rectangle = new Rectangle(0, 0, 200, 200, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rectangle.setColor(0, 0, 1);
        this.attachChild(rectangle);

        final GrowButton playButton = new GrowButton(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, ResourceManager.menuMainButtonsTTR) {
            @Override
            public void onClick() {
                // Stub for now, later levels have to be chosen dynamically
                Levels.LevelDefinition firstLevelDefinition = Levels.AVAILABLE_LEVELS[0]; // load first level
                SceneManager.getInstance().showScene(new GameLevel(firstLevelDefinition));
            }
        };

        this.attachChild(musicToggleButton);
        this.attachChild(playButton);
        this.registerTouchArea(playButton);
        this.registerTouchArea(musicToggleButton);

    }

    @Override
    public void onShowScene() {
        Rectangle rectangle = new Rectangle(200, 200, 200, 200, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rectangle.setColor(0, 0, 1);
        this.attachChild(rectangle);
    }

    @Override
    public void onHideScene() {
    }

    @Override
    public void onUnloadScene() {
    }


}
