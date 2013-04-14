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
	
	public PointF mEffectCenter;
	
	public void launch(float x, float y, final Scene scene,
			VertexBufferObjectManager vboManager) {
		mState = State.RUNNING;
		mIsEnabled = false;
		mEffectCenter = new PointF(x, y);
		scene.registerUpdateHandler(
				new TimerHandler(getRespawnTime(), new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mIsEnabled = true;
			}
		}));
	}

	public abstract float getRespawnTime();

}
