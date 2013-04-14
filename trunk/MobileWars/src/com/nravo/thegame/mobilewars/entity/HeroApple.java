package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;

public class HeroApple extends Hero {

	public HeroApple(float fromX, float fromY, float toX, float toY,
			AppleSpritePool pool) {
		super(fromX, fromY, toX, toY);
		heroSprite = pool.obtainApple(fromX, fromY);
		heroSprite.animate(new long[] { 250, 250, 250, 250, 250 });
	}

	/*public void moveHero(float fromX, float fromY, float toX, float toY) {
		if (fromX< toX){
		
		}
		RotationModifier rotate = new RotationModifier(2, 0, 360);		
		LoopEntityModifier loop = new LoopEntityModifier(rotate);
		heroSprite.setRotationCenter(0,0);
		heroSprite.registerEntityModifier(loop);
	}*/
	
}