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
         hero.fromX=xFrom;
        hero.fromY=yFrom;
        hero.toX=xTo;
        hero.toY=yTo;
        return hero;
    }
}
