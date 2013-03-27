package com.nravo.thegame.mobilewars.menu;

import com.nravo.thegame.mobilewars.runtime.ManagedScene;

public abstract class ManagedMenuScene extends ManagedScene {
    public ManagedMenuScene(float loadingMinimumSecondsShown) {
        super(loadingMinimumSecondsShown);
    }

    public ManagedMenuScene() {}

    @Override
    public void onUnloadManagedScene() {
        if (isLoaded) {
            // For menus, reloading of resources is disabled.
            // isLoaded = false;
            onUnloadScene();
        }
    }
}
