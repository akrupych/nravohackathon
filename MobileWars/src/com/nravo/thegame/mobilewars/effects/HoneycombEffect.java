package com.nravo.thegame.mobilewars.effects;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

import android.opengl.GLES20;
import android.util.Log;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SFXManager;

public class HoneycombEffect extends GodPowerEffect {
	
	private VertexBufferObjectManager mVboManager;
	private Scene mScene;
	private Sprite mCenterSprite;
	private LinkedList<Sprite> mAllHoneycombs = new LinkedList<Sprite>();
	private LinkedList<Sprite> mBees = new LinkedList<Sprite>();

	@Override
	public void launch(float x, float y, Scene scene,
			VertexBufferObjectManager vboManager) {
		super.launch(x, y, scene, vboManager);
		SFXManager.playBees(1, 1);
		mVboManager = vboManager;
		mScene = scene;
		mCenterSprite = new Sprite(x, y, ResourceManager.sHoneycombTR, vboManager);
		mCenterSprite.registerUpdateHandler(new TimerHandler(12, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mAllHoneycombs.addFirst(mCenterSprite);
				fade(mAllHoneycombs);
				fade(mBees);
			}
		}));
		scene.attachChild(mCenterSprite);
		spawnHoneycomb(mCenterSprite);
	}

	private void fade(final LinkedList<Sprite> list) {
		for (int i = 0; i < list.size(); i++) {
			final Sprite sprite = list.get(i);
			sprite.registerEntityModifier(new AlphaModifier(1, 1, 0, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) { }
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							remove(sprite);
							list.remove(sprite);
							Log.d("removed", list.size() + "");
						}
					});
				}
			}));
		}
	}

	private void remove(Sprite sprite) {
		sprite.clearEntityModifiers();
		sprite.clearUpdateHandlers();
		sprite.detachSelf();
	}

	private void spawnHoneycomb(Sprite center) {
		final LinkedList<Sprite> honeycombs = new LinkedList<Sprite>();
		honeycombs.add(new Sprite(center.getX() - 0.75f * center.getWidth(),
						center.getY() - 0.5f * center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		honeycombs.add(new Sprite(center.getX() + 0.75f * center.getWidth(),
						center.getY() + 0.5f * center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		honeycombs.add(new Sprite(center.getX() - 0.75f * center.getWidth(),
						center.getY() + 0.5f * center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		honeycombs.add(new Sprite(center.getX() + 0.75f * center.getWidth(),
						center.getY() - 0.5f * center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		honeycombs.add(new Sprite(center.getX(), center.getY() - center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		honeycombs.add(new Sprite(center.getX(), center.getY() + center.getHeight(),
						ResourceManager.sHoneycombTR, mVboManager));
		mAllHoneycombs.addAll(honeycombs);
		final Iterator<Sprite> iterator = honeycombs.iterator();
		mCenterSprite.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (iterator.hasNext()) {
					Sprite next = iterator.next();
					next.registerEntityModifier(new AlphaModifier(1, 0, 1));
					BeeSprite bee = new BeeSprite(next.getX(), next.getY(), ResourceManager.sBeeTR,
							ResourceManager.getEngine().getVertexBufferObjectManager());
					mBees.add(bee);
					bee.attachTo(mScene);
					mScene.attachChild(next);
				} else {
					for (Sprite sprite : honeycombs) {
						spawnHoneycomb(sprite);
					}
				}
			}
		}));
	}

	@Override
	public float getRespawnTime() {
		return 30;
	}
	
	public double getDamageTo(Building building) {
		Log.d("update", mAllHoneycombs.size() + " " + mBees.size());
		for (int i = 0; i < mAllHoneycombs.size(); i++) {
			Sprite honeycomb = mAllHoneycombs.get(i);
			if (honeycomb.collidesWith(building.buildingSprite)) {
				honeycomb.setBlendFunction(GLES20.GL_SRC_ALPHA,
						GLES20.GL_ONE_MINUS_SRC_ALPHA);
				honeycomb.setColor(1, 1, 0);
				honeycomb.registerEntityModifier(new AlphaModifier(1, 1, 0));
				return 2;
			}
		}
		for (int i = 0; i < mBees.size(); i++) {
			Sprite bee = mBees.get(i);
			if (bee.collidesWith(building.buildingSprite)) {
				bee.setBlendFunction(GLES20.GL_SRC_ALPHA,
						GLES20.GL_ONE_MINUS_SRC_ALPHA);
				bee.setColor(1, 0, 0);
				bee.registerEntityModifier(new AlphaModifier(1, 1, 0));
				return 1;
			}
		}
		return 0;
	}

}
