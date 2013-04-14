package com.nravo.thegame.mobilewars.effects;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import android.util.Log;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class JellyBeansEffect extends GodPowerEffect {

	private static final float RATE_MINIMUM = 5;
	private static final float RATE_MAXIMUM = 10;
	private static final int PARTICLES_MAXIMUM = 100;
	private static final float EFFECT_TIME = 5;
	private static final int IMAGE_SIZE = 32;
	
	private static final String[] mImageFileNames = {
		"jelly_bean_blue.png",
		"jelly_bean_green.png",
		"jelly_bean_purple.png",
		"jelly_bean_red.png",
		"jelly_bean_yellow.png"
	};

	private static ITextureRegion[] mTextureRegions =
			new ITextureRegion[mImageFileNames.length];
	private static SpriteParticleSystem[] mParticleSystems =
			new SpriteParticleSystem[mImageFileNames.length];
	
	public static void setTextures(ITextureRegion[] textures) {
		mTextureRegions = textures;
	}

	public void onCreateResources(BitmapTextureAtlas atlas, Context context,
			int textureX, int textureY) {
		for (int i = 0; i < mImageFileNames.length; i++) {
			mTextureRegions[i] = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, context, mImageFileNames[i],
							textureX + i * IMAGE_SIZE, textureY);
		}
	}

	public void launch(float x, float y, final Scene scene,
			VertexBufferObjectManager vboManager) {
		super.launch(x, y, scene, vboManager);
		for (int i = 0; i < mParticleSystems.length; i++) {
			ITextureRegion texture = mTextureRegions[i];
			mParticleSystems[i] = new SpriteParticleSystem(
					new PointParticleEmitter(x, y), RATE_MINIMUM, RATE_MAXIMUM,
					PARTICLES_MAXIMUM, texture, vboManager);
			final SpriteParticleSystem particleSystem = mParticleSystems[i];
			particleSystem.addParticleInitializer(
					new ExpireParticleInitializer<Sprite>(EFFECT_TIME));
			particleSystem.addParticleInitializer(
					new AccelerationParticleInitializer<Sprite>(
							-200, 200, -200, 200));
			particleSystem.addParticleModifier(
					new AlphaParticleModifier<Sprite>(0, 4, 1, 0));
			for (int j = 0; j < EFFECT_TIME; j++) {
				particleSystem.addParticleModifier(
						new RotationParticleModifier<Sprite>(j, j + 1, 0, 360));
			}
			particleSystem.registerUpdateHandler(
					new TimerHandler(EFFECT_TIME, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					mState = State.NONE;
					particleSystem.setParticlesSpawnEnabled(false);
					ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							particleSystem.clearUpdateHandlers();
							particleSystem.clearEntityModifiers();
							particleSystem.detachSelf();
						}
					});
				}
			}));
			scene.attachChild(particleSystem);
		}
	}

	@Override
	public float getRespawnTime() {
		return 60;
	}

	public double getDamageTo(Building building) {
		float x = building.getX();
		float y = building.getY();
		double distance = Math.sqrt(Math.pow(mEffectCenter.x - x, 2.0) +
				Math.pow(mEffectCenter.y - y, 2.0));
		double damage = 1000 / (1 + distance);
		Log.e("qwerty", distance + " " + damage);
		return damage;
	}

}
