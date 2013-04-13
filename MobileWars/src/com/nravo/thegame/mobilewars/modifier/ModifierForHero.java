package com.nravo.thegame.mobilewars.modifier;

import com.nravo.thegame.mobilewars.entity.AndroidSpritePool;
import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.entity.Hero;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.IModifier;

import java.util.List;

public class ModifierForHero extends MoveModifier {
	
	public ModifierForHero(float duration, float fromX, float fromY, float toX, float toY,
                           final List<Building> buildingsFrom, final Building buildingTo,
                           final AndroidSpritePool pool, final Hero hero) {
		super(duration,fromX, fromY, toX, toY);
		this.addModifierListener(new IModifierListener<IEntity>() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                for (Building building : buildingsFrom) {
                    building.decrementNumberOfUnits();
                }
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                pool.recyclePoolItem(hero.heroSprite);
                buildingTo.incrementNumberOfUnits(1);
            }
        });
	}
	
}
