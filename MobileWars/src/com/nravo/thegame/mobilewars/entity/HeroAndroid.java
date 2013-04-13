package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class HeroAndroid extends Hero{


	public HeroAndroid() {
			heroSprite = new AnimatedSprite(fromX, fromY, ResourceManager.sAndroidTTR,
					ResourceManager.getActivity()
							.getVertexBufferObjectManager());
		
			heroSprite.animate(new long[] { 150, 150, 150, 150 });
		
	}
}
