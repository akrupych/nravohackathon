package com.nravo.thegame.mobilewars.menu;

import org.andengine.entity.scene.Scene;

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
        return null;  // No loading screen
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        // No loading screen
    }

    @Override
    public void onLoadScene() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onShowScene() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onHideScene() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUnloadScene() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
