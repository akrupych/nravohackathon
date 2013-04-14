package com.nravo.thegame.mobilewars.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.IModifier;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.entity.Hero;

public class ModifierForEnemy extends MoveModifier {
	
	public ModifierForEnemy(float duration, float fromX, float fromY, float toX, float toY, final Building buildingTo,
                           final Hero hero, final Building building) {
		super(duration,fromX, fromY, toX, toY);
		this.addModifierListener(new IModifierListener<IEntity>() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
               hero.countOfEnemy = building.decrementNumberOfUnits();                  
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {            
                buildingTo.decrementNumberOfUnits(hero.countOfEnemy);
            }
        });
	}
	
}
