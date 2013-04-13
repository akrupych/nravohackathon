package com.nravo.thegame.mobilewars.entity;

import org.andengine.util.adt.pool.GenericPool;

public class AndroidHeroPool<H> extends GenericPool<HeroAndroid> {

    public AndroidHeroPool() {

    }

	@Override
	protected HeroAndroid onAllocatePoolItem() {
		return new HeroAndroid();
	}

    public synchronized Hero obtainAndroid(int xFrom, int yFrom, int xTo, int yTo) {
        HeroAndroid hero = super.obtainPoolItem();

        return super.obtainPoolItem();
    }

    public synchronized Hero obtainApple(int xFrom, int yFrom, int xTo, int yTo) {
        return super.obtainPoolItem();
    }
}
