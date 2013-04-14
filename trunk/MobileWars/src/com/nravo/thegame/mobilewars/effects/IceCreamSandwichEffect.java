package com.nravo.thegame.mobilewars.effects;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.opengl.GLES20;
import android.util.Log;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.gamelevel.Levels.Race;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SFXManager;

public class IceCreamSandwichEffect extends GodPowerEffect {

	private static final float RATE_MINIMUM = 50;
	private static final float RATE_MAXIMUM = 100;
	private static final int PARTICLES_MAXIMUM = 1000;
	private static final float SPAWN_TIME = 3;
	
	public void launch(final float x, final float y, final Scene scene,
			VertexBufferObjectManager vboManager) {
		super.launch(x, y, scene, vboManager);
		SFXManager.playIce(1, 1);
		final Sprite sandwich = new Sprite(x, y, ResourceManager.sIceCreamSandwichTR,
				ResourceManager.getEngine().getVertexBufferObjectManager());
		sandwich.registerUpdateHandler(new TimerHandler(2, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				float randomColor = 0.8f + new Random().nextInt(20) / 100f;
				final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
						new Random().nextBoolean() ?
								new CircleParticleEmitter(x, y, new Random().nextInt(200) + 100) :
								new CircleOutlineParticleEmitter(x, y, new Random().nextInt(200) + 100),
						RATE_MINIMUM, RATE_MAXIMUM, PARTICLES_MAXIMUM,
						ResourceManager.sFreezeTR,
						ResourceManager.getEngine().getVertexBufferObjectManager());
				particleSystem.addParticleInitializer(
						new ExpireParticleInitializer<Sprite>(SPAWN_TIME));
				particleSystem.addParticleInitializer(
						new BlendFunctionParticleInitializer<Sprite>(
								GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA));
				particleSystem.addParticleInitializer(
						new ColorParticleInitializer<Sprite>(
								randomColor, randomColor, 1));
				particleSystem.addParticleInitializer(
						new AccelerationParticleInitializer<Sprite>(
								-20, 20, -20, 20));
				particleSystem.addParticleModifier(
						new AlphaParticleModifier<Sprite>(0, 3, 1, 0));
				particleSystem.registerUpdateHandler(new TimerHandler(6,
						new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								particleSystem.clearEntityModifiers();
								particleSystem.clearUpdateHandlers();
								particleSystem.detachSelf();
							}
						});
					}
				}));
				scene.attachChild(particleSystem);
			}
		}));
		scene.attachChild(sandwich);
		sandwich.registerUpdateHandler(new TimerHandler(10, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				sandwich.registerEntityModifier(new AlphaModifier(2, 1, 0));
				mState = State.NONE;
				ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						sandwich.clearUpdateHandlers();
					}
				});
			}
		}));
	}

	@Override
	public float getRespawnTime() {
		return 45;
	}

	public float getFreezeTimeFor(Building building) {
		float x = building.buildingSprite.getX();
		float y = building.buildingSprite.getY();
		double distance = Math.sqrt(Math.pow(mEffectCenter.x - x, 2.0) +
				Math.pow(mEffectCenter.y - y, 2.0));
		float time = (float) (2000 / (1 + distance));
		if (time > 2) {
			final Sprite frozen = new Sprite(x, y, building.type == Race.APPLE_IOS ?
					ResourceManager.sFrostIPadTR : ResourceManager.sFrostNokiaTR,
					ResourceManager.getEngine().getVertexBufferObjectManager());
			frozen.registerEntityModifier(new SequenceEntityModifier(
					new AlphaModifier(1, 0, 1), new AlphaModifier(time - 1, 1, 0,
							new IEntityModifierListener() {
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) { }
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									frozen.clearEntityModifiers();
									frozen.clearUpdateHandlers();
									mScene.detachChild(frozen);
								}
							});
						}
					})));
			mScene.attachChild(frozen);
		}
		Log.e("qwerty", "time:" + time);
		return time;
	}

}
