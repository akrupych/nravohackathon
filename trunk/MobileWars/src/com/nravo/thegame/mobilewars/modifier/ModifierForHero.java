package com.nravo.thegame.mobilewars.modifier;

import java.util.List;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.IModifier;

import com.nravo.thegame.mobilewars.entity.Building;

public class ModifierForHero extends MoveModifier {
	
	public ModifierForHero(float duration, float fromX, float fromY, float toX, float toY, final List<Building> from, final Building to) {
		super (duration, fromX,fromY,toX,toY);	
		this.addModifierListener(new IModifierListener<IEntity>() {			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				for (Building building: from){
					building.decrementNumberOfUnits(1);
				}	
				from.clear();
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				to.incrementNumberOfUnits(1);					
			}
		});
	}
	
}
