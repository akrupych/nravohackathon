package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * A building where units are generated
 */
public class Building extends Entity {
    private final GameLevel mGameLevel;
    private int mNumberOfUnits;

    public Building(GameLevel gameLevel, final float x, final float y, int initialNumberOfUnits) {
        this.mGameLevel = gameLevel;
        this.mNumberOfUnits = initialNumberOfUnits;

        final Sprite buildingSprite = new Sprite(x, y, ResourceManager.sBuildingTR,
                ResourceManager.getActivity().getVertexBufferObjectManager());
        gameLevel.attachChild(buildingSprite);

        // Text displaying number of units
        Text unitNumber = new Text(x, y, ResourceManager.sFontDefault32Bold,
                String.valueOf(initialNumberOfUnits), 100,
                ResourceManager.getEngine().getVertexBufferObjectManager()) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                mNumberOfUnits--;
                this.setText(String.valueOf(mNumberOfUnits));
            }
        };
        gameLevel.attachChild(unitNumber);
    }

}
