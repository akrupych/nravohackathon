package com.nravo.thegame.mobilewars.entity;

import org.andengine.util.adt.pool.GenericPool;

public class AndroidHeroPool extends GenericPool<Hero> {

    public AndroidHeroPool() {

    }

	@Override
	protected Hero onAllocatePoolItem() {
		return new HeroAndroid();
	}


    public synchronized Hero obtainAndroid(int xFrom, int yFrom, int xTo, int yTo) {
        Hero hero = super.obtainPoolItem();

        return super.obtainPoolItem();
    }

    public synchronized Hero obtainApple(int xFrom, int yFrom, int xTo, int yTo) {
        return super.obtainPoolItem();
    }
}
