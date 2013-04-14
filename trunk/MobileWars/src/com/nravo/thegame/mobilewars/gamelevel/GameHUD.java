package com.nravo.thegame.mobilewars.gamelevel;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.nravo.thegame.mobilewars.effects.GodPowerEffect.State;
import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class GameHUD extends HUD {
	
	private Sprite mHoneycomb;
	private Sprite mIceCreamSandwich;
	private Sprite mJellyBeans;
	
	public GameHUD(Scene scene) {
		
        setScaleCenter(0f, 0f);
        setPosition(ResourceManager.getCamera().getCenterX() -
        		ResourceManager.getInstance().cameraScaleFactorX *
        		ResourceManager.sHudBackgroundTR.getWidth() / 2, 0);
        setScale(ResourceManager.getInstance().cameraScaleFactorX,
                ResourceManager.getInstance().cameraScaleFactorY);
        
        Sprite backround = new Sprite(0, 0, ResourceManager.sHudBackgroundTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager());
        backround.setAnchorCenter(0, 0);
        attachChild(backround);
        
        mHoneycomb = new Sprite(23, 10, ResourceManager.sPowerHoneycombTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager()) {
        	@Override
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
        			float pTouchAreaLocalX, float pTouchAreaLocalY) {
        		if (GameLevel.mHoneycombEffect.mIsEnabled) {
        			GameLevel.mHoneycombEffect.mState = State.WAITING;
        		}
        		return false;
        	};
        };
        scene.registerTouchArea(mHoneycomb);
        mHoneycomb.setAnchorCenter(0, 0);
        attachChild(mHoneycomb);
        
        mIceCreamSandwich = new Sprite(153, 10,
        		ResourceManager.sPowerIceCreamSandwichTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager()) {
        	@Override
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
        			float pTouchAreaLocalX, float pTouchAreaLocalY) {
        		if (GameLevel.mIceCreamSandwichEffect.mIsEnabled) {
        			GameLevel.mIceCreamSandwichEffect.mState = State.WAITING;
        		}
        		return false;
        	}
        };
        scene.registerTouchArea(mIceCreamSandwich);
        mIceCreamSandwich.setAnchorCenter(0, 0);
        attachChild(mIceCreamSandwich);
        
        mJellyBeans = new Sprite(307, 10, ResourceManager.sPowerJellyBeansTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager()) {
        	@Override
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
        			float pTouchAreaLocalX, float pTouchAreaLocalY) {
        		if (GameLevel.mJellyBeansEffect.mIsEnabled) {
        			GameLevel.mJellyBeansEffect.mState = State.WAITING;
        		}
        		return false;
        	};
        };
        scene.registerTouchArea(mJellyBeans);
        mJellyBeans.setAnchorCenter(0, 0);
        attachChild(mJellyBeans);
	}
	
}
