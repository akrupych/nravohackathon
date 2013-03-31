package com.nravo.thegame.mobilewars.gamelevel;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;

import com.nravo.thegame.mobilewars.entity.Building;
import com.nravo.thegame.mobilewars.entity.Hero;
import com.nravo.thegame.mobilewars.gamelevel.Levels.Race;
import com.nravo.thegame.mobilewars.layers.LevelWonLayer;
import com.nravo.thegame.mobilewars.managers.GameManager;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;

public class GameLevel extends ManagedGameScene implements
		GameManager.GameLevelGoal {

	public final Levels.LevelDefinition mLevelDefinition;

	public int mNumberOfEnemiesLeft = 10;
	private int mNumberOfAlliesLeft = 10;

	private boolean mHasCompletionTimerRun = false;

	public IUpdateHandler onCompletionTimer = new IUpdateHandler() {
		final float COMPLETION_DELAY_SECONDS = 3f;
		private float mTotalElapsedTime = 0f;

		@Override
		public void onUpdate(float pSecondsElapsed) {
			this.mTotalElapsedTime += pSecondsElapsed;
			if (this.mTotalElapsedTime >= this.COMPLETION_DELAY_SECONDS) {
				GameLevel.this.mHasCompletionTimerRun = true;
				if (GameLevel.this.isLevelCompleted()) {
					GameLevel.this.onLevelCompleted();
				} else {
					GameLevel.this.onLevelFailed();
				}
				GameLevel.this.unregisterUpdateHandler(this);
			}
		}

		@Override
		public void reset() {
		}
	};

	public GameLevel(final Levels.LevelDefinition levelDefinition) {
		this.mLevelDefinition = levelDefinition;
	}

	// ======================================
	// ============== METHODS ===============
	// ======================================
	@Override
	public boolean isLevelCompleted() {
		return this.mNumberOfEnemiesLeft <= 0;
	}

	@Override
	public boolean isLevelFailed() {
		return this.mNumberOfAlliesLeft <= 0;
	}

	@Override
	public void onLevelCompleted() {
		if (this.mHasCompletionTimerRun) {
			SceneManager.getInstance().showLayer(
					LevelWonLayer.getInstance(this), false, false, false);
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}

	@Override
	public void onLevelFailed() {
		if (this.mHasCompletionTimerRun) {
			// TODO restart level
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}

	@Override
	public void onLoadLevel() {
		GameManager.setGameLevel(this);
		GameManager.setGameLevelGoal(this);
		Rectangle rectangle = new Rectangle(0f, 0f, 120f, 120f,
				ResourceManager.getInstance().engine
						.getVertexBufferObjectManager());
		rectangle.setColor(1, 0, 1);
		GameLevel.this.attachChild(rectangle);

		// Buildings
		for (Levels.BuildingDefinition currentBuilding : GameLevel.this.mLevelDefinition.buildingsInLevel) {
			new Building(GameLevel.this, currentBuilding.race, currentBuilding.x, currentBuilding.y, 0);
		}

		new Hero(500f, 400f, GameLevel.this, Race.ANDROID).moveHero(100, 100, 900, 500);
		
		new Hero(500f, 400f, GameLevel.this, Race.APPLE_IOS).moveHero(300, 300, 700, 400);
	}
}
