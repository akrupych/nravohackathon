package com.nravo.thegame.mobilewars.modifier;

import com.nravo.thegame.mobilewars.managers.SFXManager;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.util.modifier.IModifier;

import com.nravo.thegame.mobilewars.entity.AppleSpritePool;
import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.entity.Hero;

public class ModifierForEnemy extends MoveModifier {

	public ModifierForEnemy(float duration, float fromX, float fromY,
			float toX, float toY, final Building buildingTo, final Hero hero,
			final Building building, final AppleSpritePool pool) {
		super(duration, fromX, fromY, toX, toY);
		this.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier,
					IEntity pItem) {
				hero.countOfEnemy = building.decrementNumberOfUnits();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier,
					IEntity pItem) {
				pool.recyclePoolItem(hero.heroSprite);
				buildingTo.decrementNumberOfUnits(hero.countOfEnemy);
                SFXManager.playExplosion(1.0f, 1.0f);
			}
		});
	}

}
