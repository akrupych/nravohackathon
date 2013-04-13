package com.nravo.thegame.mobilewars.gamelevel.handlers;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import org.andengine.engine.handler.IUpdateHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Taras Osiris
 * Date: 4/13/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitMovementHandler implements IUpdateHandler {
    GameLevel mGameLevel;

    public UnitMovementHandler(GameLevel gameLevel) {
        mGameLevel = gameLevel;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {

    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
