package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.adt.pool.GenericPool;

public class AndroidSpritePool extends GenericPool<AnimatedSprite> {

	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		return new AnimatedSprite(0, 0, ResourceManager.sAndroidTTR,
                ResourceManager.getActivity()
                        .getVertexBufferObjectManager());
	}

    public synchronized AnimatedSprite obtainAndroid(float x, float y) {
        AnimatedSprite heroSprite = super.obtainPoolItem();
        heroSprite.setX(x);
        heroSprite.setY(y);
        return heroSprite;
    }

    @Override
    protected void onHandleRecycleItem(AnimatedSprite pItem) {
        pItem.setVisible(false);
        pItem.setIgnoreUpdate(true);
        pItem.clearEntityModifiers();
        pItem.clearUpdateHandlers();
    }
}
