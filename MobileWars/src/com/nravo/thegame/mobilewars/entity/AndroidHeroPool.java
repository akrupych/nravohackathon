package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import org.andengine.util.adt.pool.GenericPool;

public class AndroidHeroPool<Her> extends GenericPool<HeroAndroid> {

    private GameLevel mGameLevel;

    public AndroidHeroPool(GameLevel gameLevel) {
        mGameLevel = gameLevel;
    }

	@Override
	protected HeroAndroid onAllocatePoolItem() {
		return new HeroAndroid();
	}

    public synchronized Hero obtainAndroid(float xFrom, float yFrom, float xTo, float yTo) {
        HeroAndroid hero = super.obtainPoolItem();
        hero.heroSprite.setX(xFrom);
        hero.heroSprite.setY(yFrom);
        mGameLevel.attachChild(hero.heroSprite);
        hero.fromX=xFrom;
        hero.fromY=yFrom;
        return hero;
    }
}
