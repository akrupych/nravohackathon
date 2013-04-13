package com.nravo.thegame.mobilewars.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class HeroApple extends Hero {

	public HeroApple() {
		hero = new AnimatedSprite(fromX, fromY, ResourceManager.sAppleTTR,
				ResourceManager.getActivity().getVertexBufferObjectManager());

		hero.animate(new long[] { 250, 250, 250, 250, 250 });
	}

}
