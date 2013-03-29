package com.nravo.thegame.mobilewars.gamelevel;

import com.nravo.thegame.mobilewars.managers.GameManager;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.primitive.Rectangle;

public class GameLevel extends ManagedGameScene implements GameManager.GameLevelGoal {

    private int mNumberOfEnemiesLeft;

    @Override
    public boolean isLevelCompleted() {
        return this.mNumberOfEnemiesLeft <= 0;
    }

    @Override
    public void onLevelCompleted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLevelFailed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLevelFailed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLoadLevel() {
        GameManager.setGameLevel(this);
        Rectangle rectangle = new Rectangle(0f,0f,120f,120f, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rectangle.setColor(1, 0, 1);
        GameLevel.this.attachChild(rectangle);
    }
}
