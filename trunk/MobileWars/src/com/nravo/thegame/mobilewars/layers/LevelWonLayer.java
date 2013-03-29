package com.nravo.thegame.mobilewars.layers;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;

/**
 * This layer is shown to the player if a level is completed successfully
 */
public class LevelWonLayer extends ManagedLayer {

    private static final LevelWonLayer INSTANCE = new LevelWonLayer();

    public static LevelWonLayer getInstance() {
        return INSTANCE;
    }

    public static LevelWonLayer getInstance(final GameLevel currentLevel) {
        return INSTANCE;
    }

    @Override
    public void onLoadLayer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onShowLayer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onHideLayer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUnloadLayer() { }
}
