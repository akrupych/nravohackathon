package com.nravo.thegame.mobilewars.effects;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;


public class GodPowerEffect {
	
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
	}

}
