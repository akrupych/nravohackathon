package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.gamelevel.Levels.Race;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.AnimatedSprite;

public class Hero extends Entity {

	private AnimatedSprite android;

	public Hero(final float x, final float y, Race type) {
		if (type == Race.ANDROID) {
			android = new AnimatedSprite(x, y, ResourceManager.sAndroidTTR,
					ResourceManager.getActivity()
							.getVertexBufferObjectManager());
			android.animate(new long[] { 150, 150, 150, 150 });
		} else {
			android = new AnimatedSprite(x, y, ResourceManager.sAppleTTR,
					ResourceManager.getActivity()
							.getVertexBufferObjectManager());
			android.animate(new long[] { 250, 250, 250, 250, 250 });
			// android.animate(new long[] { 100, 100, 100, 100, 100, 100, 100,
			// 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 });
		}
	}

	public void moveHero(float fromX, float fromY, float toX, float toY) {

		
		//TODO calculate time (speed and distance)
		float time = 15;

		AlphaModifier alphaModifier = new AlphaModifier(1, 0, 1);
		android.registerEntityModifier(alphaModifier);

		ScaleModifier scaleModifier = new ScaleModifier(1, 0, 1);
		android.registerEntityModifier(scaleModifier);

		MoveModifier moveModifier = new MoveModifier(time, fromX, fromY, toX,
				toY);
		android.registerEntityModifier(moveModifier);

		RotationModifier rotate = new RotationModifier(2, 0, 360);		
		LoopEntityModifier loop = new LoopEntityModifier(rotate);
		android.registerEntityModifier(loop);
	}
}