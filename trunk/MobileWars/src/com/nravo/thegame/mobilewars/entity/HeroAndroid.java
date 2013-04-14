package com.nravo.thegame.mobilewars.entity;

public class HeroAndroid extends Hero {

	public HeroAndroid(float fromX, float fromY, float toX, float toY,
			AndroidSpritePool pool) {
		super(fromX, fromY, toX, toY);
		heroSprite = pool.obtainAndroid(fromX, fromY);
		heroSprite.animate(new long[] { 150, 150, 150, 150 });
	}
}
