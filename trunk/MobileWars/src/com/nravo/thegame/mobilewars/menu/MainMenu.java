package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.effects.StarFlightEffect;
import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.gamelevel.Levels;
import com.nravo.thegame.mobilewars.input.GrowButton;
import com.nravo.thegame.mobilewars.input.GrowToggleButton;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SFXManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class MainMenu extends ManagedMenuScene {

    private static final MainMenu INSTANCE = new MainMenu();

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
        
        Engine engine = ResourceManager.getInstance().engine;
        float centerX = ResourceManager.getInstance().cameraWidth / 2f;
        float centerY = ResourceManager.getInstance().cameraHeight / 2f;

        // create the background
        mMenuBackgroundSprite = new Sprite(centerX, centerY,
        		ResourceManager.sMenuBackgroundTR, engine.getVertexBufferObjectManager());
        mMenuBackgroundSprite.setScale(ResourceManager.getInstance().cameraHeight /
        		ResourceManager.sMenuBackgroundTR.getHeight());
        mMenuBackgroundSprite.setZIndex(-5000);
        attachChild(mMenuBackgroundSprite);
        
        attachChild(new StarFlightEffect(engine, ResourceManager.mStarFlightTR, centerX, centerY));

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

        Rectangle rectangle = new Rectangle(0, 0, 200, 200, engine.getVertexBufferObjectManager());
        rectangle.setColor(0, 0, 1);
        this.attachChild(rectangle);

        final GrowButton playButton = new GrowButton(centerX, centerY, ResourceManager.menuMainButtonsTTR) {
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
