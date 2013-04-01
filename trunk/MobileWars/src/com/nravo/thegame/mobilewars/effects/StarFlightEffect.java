package com.nravo.thegame.mobilewars.effects;

import org.andengine.engine.Engine;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Simple star-flight particle system.
 * @author Andrew
 */
public class StarFlightEffect extends BatchedSpriteParticleSystem {

	private final static float MIN_SPAWN_RATE = 25;
	private final static float MAX_SPAWN_RATE = 50;
	private final static int MAX_PARTICLE_COUNT = 1000;

	/**
	 * Creates fullscreen star-flight effect. Usage example:
	 * scene.attachChild(new StarFlightEffect(mEngine, mStarTexture, centerX, centerY));
	 * @param engine - game engine
	 * @param texture - star texture (or whatever)
	 * @param x - effect center on X axis
	 * @param y - effect center on Y axis
	 */
	public StarFlightEffect(Engine engine, ITextureRegion texture, float x,
			float y) {
		super(new PointParticleEmitter(x, y), MIN_SPAWN_RATE, MAX_SPAWN_RATE,
				MAX_PARTICLE_COUNT, texture, engine
						.getVertexBufferObjectManager());
		addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(
				-100, 100, -100, 100));
		addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(
				10));
		addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(0, 10,
				0, 2));
	}

}
