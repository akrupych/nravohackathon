package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.gamelevel.Levels;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * A building where units are generated
 */
public class Building extends Entity {

    private static final float UNIT_REGENERATION_DELAY_IN_SEC = 1f;
    private static final int MAX_NUMBER_OF_UNITS_IN_BUILDING = 20;

    private final GameLevel mGameLevel;
    private int mNumberOfUnits;

    public Building(final GameLevel gameLevel, final Levels.Race buildingRace,
                    final float x, final float y, final int initialNumberOfUnits) {
        this.mGameLevel = gameLevel;
        this.mNumberOfUnits = initialNumberOfUnits;

        // BUILDING SPRITE
        ITextureRegion buildingTextureRegion = buildingRace.equals(Levels.Race.APPLE_IOS)
                ? ResourceManager.sAppleSmallBuildingTR : ResourceManager.sAndroidSmallBuildingTR;
        final Sprite buildingSprite = new Sprite(x, y, buildingTextureRegion,
                ResourceManager.getActivity().getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean isAndroid = (buildingRace == Levels.Race.ANDROID);

                if (pSceneTouchEvent.isActionOutside()) {
                    return true;
                }
                if (pSceneTouchEvent.isActionDown() && isAndroid && gameLevel.buildingsFrom.isEmpty()) {
                    gameLevel.buildingsFrom.add(Building.this);
                    return true;
                } else if (pSceneTouchEvent.isActionMove() && isAndroid
                        && !gameLevel.buildingsFrom.contains(Building.this)) {
                    gameLevel.buildingsFrom.add(Building.this);
                    return true;
                } else if (pSceneTouchEvent.isActionUp() && !isAndroid
                        && !gameLevel.buildingsFrom.contains(Building.this) && gameLevel.buildingTo == null) {
                    gameLevel.buildingTo = (Building.this);
                    return false;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        gameLevel.attachChild(buildingSprite);
        gameLevel.registerTouchArea(buildingSprite);

        // Background for counter text
        final Sprite counterBgSprite = new Sprite(0, 0, ResourceManager.sBuildingCounterBgTR,
                ResourceManager.getActivity().getVertexBufferObjectManager());
        buildingSprite.attachChild(counterBgSprite);

        // Text displaying number of units
        Text unitNumber = new Text(0, 0, ResourceManager.sFontDefault32Bold,
                String.valueOf(initialNumberOfUnits), 100,
                ResourceManager.getEngine().getVertexBufferObjectManager()) {

            float timePassed = 0;

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                timePassed += pSecondsElapsed;

                if (timePassed >= UNIT_REGENERATION_DELAY_IN_SEC) {
                    if (mNumberOfUnits < MAX_NUMBER_OF_UNITS_IN_BUILDING) {
//                        mNumberOfUnits++;
                        timePassed = 0;

                        // TODO
                        // increment number of total units depending on
                        // race of the building.
                    }
                }
                this.setText(String.valueOf(mNumberOfUnits));
                super.onManagedUpdate(pSecondsElapsed);
            }
        };
        buildingSprite.attachChild(unitNumber);
    }

    public void decrementNumberOfUnits(int numberOfUnits) {
        // TODO perform checks here
        mNumberOfUnits -= numberOfUnits;
    }

    public void incrementNumberOfUnits(int numberOfUnits) {
        // TODO perform checks here
        mNumberOfUnits += numberOfUnits;
    }

}
