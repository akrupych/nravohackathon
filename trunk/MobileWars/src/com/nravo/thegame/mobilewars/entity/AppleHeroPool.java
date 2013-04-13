package com.nravo.thegame.mobilewars.entity;

import org.andengine.util.adt.pool.GenericPool;

/**
 * Created with IntelliJ IDEA. User: Taras Osiris Date: 4/13/13 Time: 1:23 PM To
 * change this template use File | Settings | File Templates.
 */
public class AppleHeroPool<H> extends GenericPool<HeroApple> {
	@Override
	protected HeroApple onAllocatePoolItem() {
		return new HeroApple();
	}

	public synchronized Hero obtainApple(int xFrom, int yFrom, int xTo,
			int yTo) {
		HeroApple hero = super.obtainPoolItem();
		hero.fromX = xFrom;
		hero.fromY = yFrom;
		hero.toX = xTo;
		hero.toY = yTo;
		return hero;
	}
}
