package com.nravo.thegame.mobilewars.modifier;

import com.nravo.thegame.mobilewars.entity.AndroidSpritePool;
import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.entity.Hero;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.IModifier;

import java.util.List;

public class ModifierForHero extends MoveModifier {

	public ModifierForHero(float duration, float fromX, float fromY, float toX,
			float toY, final List<Building> buildingsFrom,
			final Building buildingTo, final AndroidSpritePool pool,
			final Hero hero, final Building building) {
		super(duration, fromX, fromY, toX, toY);
		if (fromX < toX){
			hero.heroSprite.setFlippedHorizontal(false);
		}
		else {
			hero.heroSprite.setFlippedHorizontal(true);
		}
		this.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
				hero.countOfEnemy = building.decrementNumberOfUnits();
				if (buildingsFrom != null)
					buildingsFrom.clear();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				pool.recyclePoolItem(hero.heroSprite);
				buildingTo.decrementNumberOfUnits(hero);
			}
		});
	}

}
