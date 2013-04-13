package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class HeroAndroid extends Hero {

	public HeroAndroid() {
		hero = new AnimatedSprite(fromX, fromY, ResourceManager.sAndroidTTR,
				ResourceManager.getActivity().getVertexBufferObjectManager());

		hero.animate(new long[] { 150, 150, 150, 150 });
	}

}
