package com.nravo.thegame.mobilewars.managers;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import org.andengine.engine.handler.IUpdateHandler;

/**
 * Monitors the game and checks whether the player won or lost
 */
public class GameManager implements IUpdateHandler {

    public interface GameLevelGoal {
        public boolean isLevelCompleted();
        public void onLevelCompleted();
        public boolean isLevelFailed();
        public void onLevelFailed();
    }

    private static final GameManager INSTANCE = new GameManager();

    private GameLevelGoal mGameLevelGoal;
    private GameLevel mGameLevel;

    private GameManager() {
        ResourceManager.getEngine().registerUpdateHandler(this);
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if (mGameLevelGoal != null) {
            mGameLevel.mNumberOfEnemiesLeft--;
            if (mGameLevelGoal.isLevelCompleted()) {
                mGameLevelGoal.onLevelCompleted();
                mGameLevelGoal = null;
            } else if (mGameLevelGoal.isLevelFailed()) {
                mGameLevelGoal.onLevelFailed();
                mGameLevelGoal = null;
            }
        }
    }

    public static void setGameLevel(GameLevel gameLevel) {
        INSTANCE.mGameLevel = gameLevel;
    }

    public static GameLevel getGameLevel() {
        return INSTANCE.mGameLevel;
    }

    public static void setGameLevelGoal(GameLevelGoal gameLevelGoal) {
        INSTANCE.mGameLevelGoal = gameLevelGoal;
    }

    @Override
    public void reset() { }

}
