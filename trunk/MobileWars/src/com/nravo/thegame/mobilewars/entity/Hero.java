package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;

public abstract class Hero extends Entity {


	public int fromX;
	public int fromY;
	public int toX;
	public int toY;
	
	public AnimatedSprite hero;
	
	public Hero (){		
	}
	
	
/**
	public void moveHero(float fromX, float fromY, float toX, float toY) {

		
		//TODO calculate time (speed and distance)
		float time = 15;

		AlphaModifier alphaModifier = new AlphaModifier(1, 0, 1);
		android.registerEntityModifier(alphaModifier);

		ScaleModifier scaleModifier = new ScaleModifier(1, 0, 1);
		android.registerEntityModifier(scaleModifier);

		MoveModifier moveModifier = new MoveModifier(time, fromX, fromY, toX,
				toY);
		android.registerEntityModifier(moveModifier);

		RotationModifier rotate = new RotationModifier(2, 0, 360);		
		LoopEntityModifier loop = new LoopEntityModifier(rotate);
		android.registerEntityModifier(loop);
	}*/
}