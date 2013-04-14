package com.nravo.thegame.mobilewars.effects;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public abstract class GodPowerEffect {
	
	public enum State {
		NONE,
		WAITING,
		RUNNING
	}
	public State mState = State.NONE;
	public boolean mIsEnabled = true;
	public int mSecondsElapsed = 0;
	
	public PointF mEffectCenter;
	public Scene mScene;
	
	public void launch(float x, float y, final Scene scene,
			VertexBufferObjectManager vboManager) {
		mState = State.RUNNING;
		mIsEnabled = false;
		mEffectCenter = new PointF(x, y);
		mScene = scene;
		scene.registerUpdateHandler(
				new TimerHandler(1, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (mState == State.RUNNING && mIsEnabled) {
					mIsEnabled = false;
					mSecondsElapsed = 0;
				} else if (mSecondsElapsed > getRespawnTime()) {
					mIsEnabled = true;
				}
				mSecondsElapsed++;
			}
		}));
	}

	public abstract float getRespawnTime();

}
