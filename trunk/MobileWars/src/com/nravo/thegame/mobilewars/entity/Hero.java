package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;

public abstract class Hero extends Entity {

	public float fromX;
	public float fromY;
	public float toX;
	public float toY;
	
	public int countOfEnemy;
	
	public AnimatedSprite heroSprite;
	
	public Hero(float fromX, float fromY, float toX, float toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
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