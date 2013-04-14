package com.nravo.thegame.mobilewars.gamelevel.handlers;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to draw arrows from buildings when the finger is on the screen
 */
public class DrawPointerUpdateHandler implements IUpdateHandler {

    GameLevel mGameLevel;
    private List<Line> lines;

    private boolean isRegistered = false;

    public DrawPointerUpdateHandler(GameLevel gameLevel) {
        mGameLevel = gameLevel;
        int capacity = gameLevel.mNumberOfBuildingsInCurrentLevel;
        lines = new ArrayList<Line>(capacity);
        for (int i = 0; i < capacity; i++) {
            lines.add(new Line(0, 0, gameLevel.mX, gameLevel.mY, 6f, ResourceManager.getEngine().getVertexBufferObjectManager()));
            lines.get(i).setColor(0f,0f,0f,0.5f);
//            lines.get(i).setZIndex(-1);
        }
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        for (Line line : lines) {
            if (!line.hasParent()) {
                mGameLevel.attachChild(line);
            }
        }
        if (!mGameLevel.buildingsFrom.isEmpty()) {
            for (int i = 0; i < mGameLevel.buildingsFrom.size(); i++) {
                for (int j = 0; j < mGameLevel.buildingsFrom.size(); j++) {
                    if (i == j) {
                        Building currentBuilding = mGameLevel.buildingsFrom.get(i);
                        lines.get(i).setPosition(currentBuilding.buildingSprite.getX(),
                                currentBuilding.buildingSprite.getY(), mGameLevel.mX, mGameLevel.mY);
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        isRegistered = false;
        for (Line line : lines) {
            line.setPosition(0, 0, 0, 0);
            line.setVisible(false);
        }
    }

    /**
     * Resets lines positions and sets their visibility
     *
     * @param visible  whether lines are visible
     */
    public void setPointersVisible(boolean visible) {
        reset();
        for (Line line : lines) {
            line.setVisible(visible);
        }
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}
