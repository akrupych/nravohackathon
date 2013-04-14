package com.nravo.thegame.mobilewars.entity;

public class HeroApple extends Hero {

	public HeroApple(float fromX, float fromY, float toX, float toY,
			AppleSpritePool pool) {
		super(fromX, fromY, toX, toY);
		heroSprite = pool.obtainApple(fromX, fromY);
		heroSprite.animate(new long[] { 250, 250, 250, 250, 250 });
	}

}