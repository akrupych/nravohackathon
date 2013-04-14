package com.nravo.thegame.mobilewars.effects;

import java.util.Random;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BeeSprite extends Sprite {
	
	public BeeSprite(float x, float y, ITextureRegion sBeeTR,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(x, y, sBeeTR, vertexBufferObjectManager);
	}

	public void attachTo(Scene scene) {
		Random random = new Random();
		int x = random.nextInt(20) + 10;
		int y = random.nextInt(20) + 10;
		x = random.nextBoolean() ? x : -x;
		y = random.nextBoolean() ? y : -y;
		registerEntityModifier(new LoopEntityModifier(new MoveByModifier(1, x, y)));
		scene.attachChild(this);
	}

}
