package com.nravo.thegame.mobilewars.gamelevel;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;

import com.nravo.thegame.mobilewars.managers.ResourceManager;

public class GameHUD extends HUD {
	
	public GameHUD() {
        setScaleCenter(0f, 0f);
        setScale(ResourceManager.getInstance().cameraScaleFactorX,
                ResourceManager.getInstance().cameraScaleFactorY);
        attachChild(new Sprite(0, 0, ResourceManager.sHudBackgroundTR,
        		ResourceManager.getEngine().getVertexBufferObjectManager()));
	}
	
}
