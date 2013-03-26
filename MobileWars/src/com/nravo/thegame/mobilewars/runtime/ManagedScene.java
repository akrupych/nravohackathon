package com.nravo.thegame.mobilewars.runtime;

import org.andengine.entity.scene.Scene;

public abstract class ManagedScene extends Scene {
    public final boolean hasLoadingScreen;
    public final float minLoadingScreenTime;
    public float elapsedLoadingScreenTime = 0f;
    public boolean isLoaded = false;

    // Convenience constructor that disables the loading screen
    public ManagedScene() {
        this(0f);
    }

    public ManagedScene(final float pLoadingScreenMinimumSecondsShown) {
        minLoadingScreenTime = pLoadingScreenMinimumSecondsShown;
        hasLoadingScreen = (minLoadingScreenTime > 0f);
    }

    // Methods to Override in the subclasses.
    public abstract Scene onLoadingScreenLoadAndShown();
    public abstract void onLoadingScreenUnloadAndHidden();
    public abstract void onLoadScene();
    public abstract void onShowScene();
    public abstract void onHideScene();
    public abstract void onUnloadScene();

    /**
     * Called by {@link com.nravo.thegame.mobilewars.managers.SceneManager}
     */
    public void onLoadManagedScene() {
        if (!isLoaded) {
            onLoadScene();
            isLoaded = true;
            this.setIgnoreUpdate(true);
        }
    }

    /**
     * Called by {@link com.nravo.thegame.mobilewars.managers.SceneManager}
     */
    public void onUnloadManagedScene() {
        if (isLoaded) {
            isLoaded = false;
            onUnloadScene();
        }
    }

    /**
     * Called by {@link com.nravo.thegame.mobilewars.managers.SceneManager}
     *
     * Unpauses the screen before showing it.
     */
    public void onShowManagedScene() {
        this.setIgnoreUpdate(false);
        onShowScene();
    }

    /**
     * Called by {@link com.nravo.thegame.mobilewars.managers.SceneManager}
     *
     * Pauses the screen before hiding it.
     */
    public void onHideManagedScene() {
        this.setIgnoreUpdate(true);
        onHideScene();
    }
}
