package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

/**
 * A building where units are generated
 */
public class Building extends Entity {
    private final GameLevel gameLevel;

    public Building(final float x, final float y, GameLevel gameLevel) {
        this.gameLevel = gameLevel;

        final Sprite buildingSpprite = new Sprite(x, y, ResourceManager.sBuildingTR,
                ResourceManager.getActivity().getVertexBufferObjectManager());
        gameLevel.attachChild(buildingSpprite);
    }
}
