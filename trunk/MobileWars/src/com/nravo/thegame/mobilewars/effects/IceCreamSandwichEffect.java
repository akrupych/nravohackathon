package com.nravo.thegame.mobilewars.effects;

import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.opengl.GLES20;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class IceCreamSandwichEffect extends GodPowerEffect {

	private static final float RATE_MINIMUM = 50;
	private static final float RATE_MAXIMUM = 100;
	private static final int PARTICLES_MAXIMUM = 1000;
	private static final float EFFECT_TIME = 5;
	private static final float RESPAWN_TIME = 30;
	private static final int FREEZE_CIRCLES_COUNT = 3;
	private static final float SPAWN_TIME = 3;
	
	public void launch(float x, float y, final Scene scene,
			VertexBufferObjectManager vboManager) {
		super.launch(x, y, scene, vboManager);
		
		for (int i = 0; i < FREEZE_CIRCLES_COUNT; i++) {
			SpriteParticleSystem particleSystem = new SpriteParticleSystem(
					new CircleOutlineParticleEmitter(x, y, 100 * (i + 1)),
					RATE_MINIMUM, RATE_MAXIMUM, PARTICLES_MAXIMUM,
					ResourceManager.sFreezeTR,
					ResourceManager.getEngine().getVertexBufferObjectManager());
			particleSystem.addParticleInitializer(
					new ExpireParticleInitializer<Sprite>(SPAWN_TIME));
			particleSystem.addParticleInitializer(
					new BlendFunctionParticleInitializer<Sprite>(
							GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			particleSystem.addParticleInitializer(
					new ColorParticleInitializer<Sprite>(0.2f, 0.2f, 1));
			particleSystem.addParticleInitializer(
					new AlphaParticleInitializer<Sprite>(0.9f));
			particleSystem.addParticleInitializer(
					new AccelerationParticleInitializer<Sprite>(
							-10, 10, -10, 10));
			scene.attachChild(particleSystem);
		}
		
		Sprite sandwich = new Sprite(x, y, ResourceManager.sIceCreamSandwichTR,
				ResourceManager.getEngine().getVertexBufferObjectManager());
		scene.attachChild(sandwich);
	}

}
