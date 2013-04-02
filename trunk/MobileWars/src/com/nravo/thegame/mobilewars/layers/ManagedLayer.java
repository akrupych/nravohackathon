package com.nravo.thegame.mobilewars.layers;

import org.andengine.entity.scene.CameraScene;

public abstract class ManagedLayer extends CameraScene {
    public static final float SLIDE_PIXELS_PER_SECOND = 3000f;

    public boolean hasLoaded = false;
    public boolean unloadOnHidden;

    public ManagedLayer() {
        this(false);
    }

    public ManagedLayer(boolean  unloadOnHidden) {
        this.unloadOnHidden = unloadOnHidden;
        this.setBackgroundEnabled(false);
    }

    public void onShowManagedLayer() {
        if (!hasLoaded) {
            hasLoaded = true;
            onLoadLayer();
        }
        this.setIgnoreUpdate(false);
        onShowLayer();
    }

    public void onHideManagedLayer() {
        this.setIgnoreUpdate(true);
        onHideLayer();
        if (unloadOnHidden) {
            onUnloadLayer();
        }
    }

    public abstract void onLoadLayer();
    public abstract void onShowLayer();
    public abstract void onHideLayer();
    public abstract void onUnloadLayer();
}
