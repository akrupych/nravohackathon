package com.nravo.thegame.mobilewars.entity;

import org.andengine.util.adt.pool.GenericPool;

/**
 * Created with IntelliJ IDEA.
 * User: Taras Osiris
 * Date: 4/13/13
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppleHeroPool<H> extends GenericPool<Hero> {
    @Override
    protected Hero onAllocatePoolItem() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
