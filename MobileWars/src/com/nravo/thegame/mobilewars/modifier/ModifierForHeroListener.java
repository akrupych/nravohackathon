package com.nravo.thegame.mobilewars.modifier;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import org.andengine.util.modifier.IModifier;

/**
 * Created with IntelliJ IDEA.
 * User: Taras Osiris
 * Date: 4/13/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModifierForHeroListener implements IModifier.IModifierListener {

    private GameLevel mGameLevel;

    public ModifierForHeroListener(GameLevel gameLevel) {
        mGameLevel = gameLevel;
    }

    @Override
    public void onModifierStarted(IModifier pModifier, Object pItem) {
        // reduce number of units in buidingsFrom[]
    }

    @Override
    public void onModifierFinished(IModifier pModifier, Object pItem) {
        // increase number of units in buildingTo
    }
}
