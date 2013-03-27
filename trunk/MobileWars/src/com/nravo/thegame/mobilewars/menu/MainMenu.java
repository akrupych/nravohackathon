package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class MainMenu extends ManagedMenuScene {

    private static final MainMenu INSTANCE = new MainMenu();

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

    private Sprite mBackgroundSprite;

    @Override
    public void onLoadScene() {
        ResourceManager.loadMenuResources();

        mBackgroundSprite = new Sprite(ResourceManager.getInstance().cameraWidth/2f,
                ResourceManager.getInstance().cameraHeight/2f,
                ResourceManager.sMenuBackgroundTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        mBackgroundSprite.setScale(ResourceManager.getInstance().cameraHeight / ResourceManager.sMenuBackgroundTR.getHeight());
        mBackgroundSprite.setZIndex(-5000);
        this.attachChild(mBackgroundSprite);

        Rectangle rectangle = new Rectangle(0, 0, 200, 200, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rectangle.setColor(0, 0, 1);
        this.attachChild(rectangle);

        // create the background
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