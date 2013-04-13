package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.Levels;
import org.andengine.util.adt.pool.GenericPool;

public class HeroPool extends GenericPool<Hero> {

    public HeroPool() {

    }

	@Override
	protected Hero onAllocatePoolItem() {
		return new HeroAndroid();
	}


    public synchronized Hero obtainPoolItem(int xFrom, int yFrom, int xTo, int yTo) {
        return super.obtainPoolItem();
    }
}