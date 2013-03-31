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

    private static final float UNIT_REGENERATION_DELAY_IN_SEC = 2f;

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

            float timePassed = 0;

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                timePassed += pSecondsElapsed;

                if (timePassed >= UNIT_REGENERATION_DELAY_IN_SEC) {
                    mNumberOfUnits++;
                    this.setText(String.valueOf(mNumberOfUnits));
                    timePassed = 0;
                }

                super.onManagedUpdate(pSecondsElapsed);
            }
        };
        gameLevel.attachChild(unitNumber);
    }

}
