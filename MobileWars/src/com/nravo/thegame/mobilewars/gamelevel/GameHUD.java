package com.nravo.thegame.mobilewars.gamelevel;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.nravo.thegame.mobilewars.effects.JellyBeansEffect.State;
import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class GameHUD extends HUD {
	
	public GameHUD(Scene scene) {
		
        setScaleCenter(0f, 0f);
        setScale(ResourceManager.getInstance().cameraScaleFactorX,
                ResourceManager.getInstance().cameraScaleFactorY);
        
        Sprite backround = new Sprite(0, 0, ResourceManager.sHudBackgroundTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager());
        backround.setAnchorCenter(0, 0);
        attachChild(backround);
        
        Sprite honeycomb = new Sprite(0, 0, ResourceManager.sPowerHoneycombTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager());
        honeycomb.setAnchorCenter(0, 0);
        attachChild(honeycomb);
        
        Sprite iceCreamSandwich = new Sprite(128, 0,
        		ResourceManager.sPowerIceCreamSandwichTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager());
        iceCreamSandwich.setAnchorCenter(0, 0);
        attachChild(iceCreamSandwich);
        
        Sprite jellyBeans = new Sprite(256, 0,
        		ResourceManager.sPowerJellyBeansTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager()) {
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
        			float pTouchAreaLocalX, float pTouchAreaLocalY) {
        		setScale(2);
        		GameLevel.mJellyBeansEffect.mState = State.WAITING;
        		return true;
        	};
        };
        jellyBeans.setScaleCenter(jellyBeans.getX() + jellyBeans.getWidth() / 2,
        		jellyBeans.getY() + jellyBeans.getHeight() / 2);
        jellyBeans.setAnchorCenter(0, 0);
        scene.registerTouchArea(jellyBeans);
        attachChild(jellyBeans);
	}
	
}
