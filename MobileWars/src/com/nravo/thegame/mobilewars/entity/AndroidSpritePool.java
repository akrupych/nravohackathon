package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.adt.pool.GenericPool;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class AndroidSpritePool extends GenericPool<AnimatedSprite> {

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		return new AnimatedSprite(0, 0, ResourceManager.sAndroidTTR,
                ResourceManager.getActivity()
                        .getVertexBufferObjectManager());
	}

    public synchronized AnimatedSprite obtainAndroid(float x, float y) {
        AnimatedSprite heroSprite = super.obtainPoolItem();
        heroSprite.setVisible(true);
        heroSprite.setIgnoreUpdate(false);
        heroSprite.setX(x);
        heroSprite.setY(y);
        return heroSprite;
    }

    @Override
    protected void onHandleRecycleItem(final AnimatedSprite pItem) {
        ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                pItem.detachSelf();
            }
        });
        pItem.setVisible(false);
        pItem.setIgnoreUpdate(true);
        pItem.clearUpdateHandlers();
    }
}
