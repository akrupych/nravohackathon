package com.nravo.thegame.mobilewars.effects;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;

import android.graphics.PointF;

public class ApplePiePower {

	private Engine mEngine;
	private Scene mScene;
	
	private Sprite mApplePieSprite;
	private ArrayList<Sprite> mMagnetFieldSprites = new ArrayList<Sprite>();
	
	private Quake mQuake;

	public ApplePiePower(float x, float y, ITextureRegion applePieTR,
			ITextureRegion magnetFieldTR, Engine engine, Scene scene, Camera camera) {
		
		mEngine = engine;
		mScene = scene;
		mQuake = new Quake(camera);
		
		mApplePieSprite = new Sprite(x, y, applePieTR,
				engine.getVertexBufferObjectManager());
		mApplePieSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mApplePieSprite.setAlpha(0);
		
		mMagnetFieldSprites.add(new Sprite(x, y, magnetFieldTR,
				engine.getVertexBufferObjectManager()));
		mMagnetFieldSprites.add(new Sprite(x, y, magnetFieldTR,
				engine.getVertexBufferObjectManager()));
		mMagnetFieldSprites.add(new Sprite(x, y, magnetFieldTR,
				engine.getVertexBufferObjectManager()));
		for (int i = 0; i < mMagnetFieldSprites.size(); i++) {
			Sprite current = mMagnetFieldSprites.get(i);
			current.setWidth((i + 2) * mApplePieSprite.getWidth());
			current.setHeight((i + 2) * mApplePieSprite.getHeight());
			current.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			current.setAlpha(0);
			mScene.attachChild(current);
		}
		
		mApplePieSprite.registerEntityModifier(createApplePieModifiers());
		scene.attachChild(mApplePieSprite);
	}

	private IEntityModifier createApplePieModifiers() {
		// falling
		float fallDuration = 2;
		IEntityModifier fallScale = new ScaleModifier(fallDuration, 10, 1);
		IEntityModifier fallAlpha = new AlphaModifier(fallDuration, 0, 1);
		IEntityModifier fall = new ParallelEntityModifier(fallScale, fallAlpha);
		// earthquake
		IEntityModifier push = new DelayModifier(mQuake.getPushDuration(),
				new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				mQuake.push();
			}
		});
		IEntityModifier quake = new LoopEntityModifier(push, mQuake.getPushCount(),
				new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				mEngine.vibrate((long) (mQuake.getPushCount() *
						mQuake.getPushDuration() * 1000));
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				for (int i = 0; i < mMagnetFieldSprites.size(); i++) {
					mMagnetFieldSprites.get(i).registerEntityModifier(
							createMagnetEnitityModifiers(i));
				}
			}
		});
		// combined
		IEntityModifier result = new SequenceEntityModifier(fall, quake);
		return result;
	}
	
	protected IEntityModifier createMagnetEnitityModifiers(int magnet) {
		float duration = magnet + 2;
		IEntityModifier scale = new ScaleModifier(duration, 1, 0);
		IEntityModifier alpha = new AlphaModifier(duration, 0.1f, 0);
		IEntityModifier conbined = new ParallelEntityModifier(scale, alpha);
		IEntityModifier result = new LoopEntityModifier(conbined);
		return result;
	}

	private class Quake {
		
		private Camera mCamera;
		private PointF[] mPushes;
		private int mCurrent = 0;
		private float mPushDuration = 0.01f;

		public Quake(Camera camera) {
			mCamera = camera;
			float x = camera.getCenterX();
			float y = camera.getCenterY();
			mPushes = new PointF[] {
					new PointF(x + 10, y + 10),
					new PointF(x, y - 10),
					new PointF(x - 10, y + 10),
					new PointF(x + 10, y),
					new PointF(x - 10, y - 10),
					new PointF(x, y + 10),
					new PointF(x + 10, y - 10),
					new PointF(x - 10, y),
					new PointF(x, y)
			};
		}

		public float getPushDuration() {
			return mPushDuration;
		}

		public int getPushCount() {
			return mPushes.length;
		}

		public void push() {
			PointF push = mPushes[mCurrent++];
			mCamera.setCenter(push.x, push.y);
		}
		
	}

}
