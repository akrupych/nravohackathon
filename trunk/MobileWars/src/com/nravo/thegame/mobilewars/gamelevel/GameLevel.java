package com.nravo.thegame.mobilewars.gamelevel;

import android.util.Log;
import com.nravo.thegame.mobilewars.Utils.Utils;
import com.nravo.thegame.mobilewars.effects.GodPowerEffect.State;
import com.nravo.thegame.mobilewars.effects.HoneycombEffect;
import com.nravo.thegame.mobilewars.effects.IceCreamSandwichEffect;
import com.nravo.thegame.mobilewars.effects.JellyBeansEffect;
import com.nravo.thegame.mobilewars.entity.*;
import com.nravo.thegame.mobilewars.gamelevel.Levels.Race;
import com.nravo.thegame.mobilewars.gamelevel.handlers.DrawPointerUpdateHandler;
import com.nravo.thegame.mobilewars.layers.LevelLostLayer;
import com.nravo.thegame.mobilewars.layers.LevelWonLayer;
import com.nravo.thegame.mobilewars.managers.GameManager;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import com.nravo.thegame.mobilewars.modifier.ModifierForHero;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import java.util.ArrayList;
import java.util.List;

public class GameLevel extends ManagedGameScene implements
		GameManager.GameLevelGoal, IOnSceneTouchListener {

	public static final int HEROES_POOL_SIZE = 100;
	public final Levels.LevelDefinition mLevelDefinition;
	public final int mNumberOfBuildingsInCurrentLevel;

	public List<Building> mAllBuilding = new ArrayList<Building>();

	public static JellyBeansEffect mJellyBeansEffect = new JellyBeansEffect();
	public static IceCreamSandwichEffect mIceCreamSandwichEffect = new IceCreamSandwichEffect();
	public static HoneycombEffect mHoneycombEffect = new HoneycombEffect();

	public float mX = 0;
	public float mY = 0;

	// Moving units from building to building
	// These hold information about the touch event
	// to collect units from multiple buildings (buildingsFrom)
	public List<Building> buildingsFrom = null;
	public Building buildingTo;

	private boolean mHasCompletionTimerRun = false;

	private Sprite mJellyBeansSprite;
	private Sprite mIceCreamSandwichSprite;
	private Sprite mHoneycombSprite;

	private GameStatus gameStatus = GameStatus.IN_PROGRESS;

	public enum GameStatus {
		IN_PROGRESS, WON, LOST
	}

	// ============================================================
	// ===================== UPDATE HANDLERS=======================
	// ============================================================
	private IUpdateHandler onCompletionTimer = new IUpdateHandler() {
		final float COMPLETION_DELAY_SECONDS = 1f;
		private float mTotalElapsedTime = 0f;

		@Override
		public void onUpdate(float pSecondsElapsed) {
			this.mTotalElapsedTime += pSecondsElapsed;
			if (this.mTotalElapsedTime >= this.COMPLETION_DELAY_SECONDS) {
				GameLevel.this.mHasCompletionTimerRun = true;
				if (GameLevel.this.isLevelCompleted()) {
					gameStatus = GameStatus.WON;
					GameLevel.this.onLevelCompleted();
				} else {
					gameStatus = GameStatus.LOST;
					GameLevel.this.onLevelFailed();
				}
				GameLevel.this.unregisterUpdateHandler(this);
			}
		}

		@Override
		public void reset() {
		}
	};

	// Draws pointers when dragging your finger
	public DrawPointerUpdateHandler lineDrawingHandler;
	public AndroidSpritePool mAndroidHeroPool;
	public AppleSpritePool mAppleHeroPool;

	public GameLevel(final Levels.LevelDefinition levelDefinition) {
		this.mLevelDefinition = levelDefinition;
		mNumberOfBuildingsInCurrentLevel = levelDefinition.buildingsInLevel.length;
	}

	// ======================================
	// ============== METHODS ===============
	// ======================================
	@Override
	public boolean isLevelCompleted() {
		int numberOfEnemyBuildingsLeft = 0;
		for (Building building : mAllBuilding) {
			if (building.type.equals(Levels.Race.APPLE_IOS)) {
				numberOfEnemyBuildingsLeft++;
			}
		}
		return numberOfEnemyBuildingsLeft == 0;
	}

	@Override
	public boolean isLevelFailed() {
		int numberOfMyBuildingsLeft = 0;
		for (Building building : mAllBuilding) {
			if (building.type.equals(Levels.Race.ANDROID)) {
				numberOfMyBuildingsLeft++;
			}
		}
		return numberOfMyBuildingsLeft == 0;
	}

	@Override
	public void onLevelCompleted() {
		if (this.mHasCompletionTimerRun) {
			SceneManager.getInstance().showLayer(
					LevelWonLayer.getInstance(this), true, true, true);
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}

	@Override
	public void onLevelFailed() {
		if (this.mHasCompletionTimerRun) {
			SceneManager.getInstance().showLayer(
					LevelLostLayer.getInstance(this), false, false, false);
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}

	@Override
	public void onLoadLevel() {
		GameManager.setGameLevel(this);
		GameManager.setGameLevelGoal(this);

		for (Levels.BuildingDefinition buildingDefinition : mLevelDefinition.buildingsInLevel) {

		}

		final int numberOfBuildingsInLevel = mLevelDefinition.buildingsInLevel.length;
		buildingsFrom = new ArrayList<Building>(numberOfBuildingsInLevel);
		lineDrawingHandler = new DrawPointerUpdateHandler(GameLevel.this);

		// ============ HERO POOLS =============
		mAndroidHeroPool = new AndroidSpritePool();
		mAndroidHeroPool.batchAllocatePoolItems(HEROES_POOL_SIZE);

		mAppleHeroPool = new AppleSpritePool();
		mAppleHeroPool.batchAllocatePoolItems(HEROES_POOL_SIZE);

		// Buildings
		for (Levels.BuildingDefinition currentBuilding : GameLevel.this.mLevelDefinition.buildingsInLevel) {
			Building building = new Building(GameLevel.this,
					currentBuilding.race, currentBuilding.x, currentBuilding.y,
					currentBuilding.initialNumberOfUnits);
			mAllBuilding.add(building);
		}

		mJellyBeansSprite = new Sprite(0, 0,
				ResourceManager.sPowerJellyBeansTR, ResourceManager.getEngine()
						.getVertexBufferObjectManager());
		mIceCreamSandwichSprite = new Sprite(0, 0,
				ResourceManager.sPowerIceCreamSandwichTR, ResourceManager
						.getEngine().getVertexBufferObjectManager());
		mHoneycombSprite = new Sprite(0, 0, ResourceManager.sPowerHoneycombTR,
				ResourceManager.getEngine().getVertexBufferObjectManager());

		GameLevel.this.setOnSceneTouchListener(this);
		registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (mJellyBeansEffect.mState == State.RUNNING) {
					for (Building building : mAllBuilding) {
						if (building.type != Race.ANDROID) {
							double damage = mJellyBeansEffect
									.getDamageTo(building);
							building.mNumberOfUnits -= Math.min(damage,
									building.mNumberOfUnits);
						}
					}
				}
				if (mHoneycombEffect.mState == State.RUNNING) {
					for (Building building : mAllBuilding) {
						if (building.type != Race.ANDROID) {
							double damage = mHoneycombEffect
									.getDamageTo(building);
							building.mNumberOfUnits -= Math.min(damage,
									building.mNumberOfUnits);
						}
					}
				}
			}
		}));

        SceneManager.getInstance().showLayer(LevelWonLayer.getInstance(), true, true, true);
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene,
			final TouchEvent pSceneTouchEvent) {

		float x = pSceneTouchEvent.getX();
		float y = pSceneTouchEvent.getY();

		if (pSceneTouchEvent.isActionDown()) {
			if (mJellyBeansEffect.mState == State.WAITING) {
				mJellyBeansSprite.setPosition(x, y);
				mJellyBeansSprite.setScale(2);
				if (!mJellyBeansSprite.hasParent()) {
					pScene.attachChild(mJellyBeansSprite);
				}
			} else if (mIceCreamSandwichEffect.mState == State.WAITING) {
				mIceCreamSandwichSprite.setPosition(x, y);
				mIceCreamSandwichSprite.setScale(2);
				if (!mIceCreamSandwichSprite.hasParent()) {
					pScene.attachChild(mIceCreamSandwichSprite);
				}
			} else if (mHoneycombEffect.mState == State.WAITING) {
				mHoneycombSprite.setPosition(x, y);
				mHoneycombSprite.setScale(2);
				if (!mHoneycombSprite.hasParent()) {
					pScene.attachChild(mHoneycombSprite);
				}
			}
		}

		if (pSceneTouchEvent.isActionMove()) {
			mX = pSceneTouchEvent.getX();
			mY = pSceneTouchEvent.getY();
			if (mJellyBeansEffect.mState == State.WAITING) {
				mJellyBeansSprite.setPosition(x, y);
			} else if (mIceCreamSandwichEffect.mState == State.WAITING) {
				mIceCreamSandwichSprite.setPosition(x, y);
			} else if (mHoneycombEffect.mState == State.WAITING) {
				mHoneycombSprite.setPosition(x, y);
			} else if (!lineDrawingHandler.isRegistered()) {
				GameLevel.this.registerUpdateHandler(lineDrawingHandler);
				lineDrawingHandler.setPointersVisible(true);
				lineDrawingHandler.setRegistered(true);
			}
		}

		if (pSceneTouchEvent.isActionUp()) {
			lineDrawingHandler.reset();
			GameLevel.this.unregisterUpdateHandler(lineDrawingHandler);
			// if our touch event of collecting houses is correct
			// perform moving units
			if (!buildingsFrom.isEmpty() && buildingTo != null) {
				performUnitMovement();
			} else {
				buildingsFrom.clear();
			}
			buildingTo = null;
			if (mJellyBeansEffect.mState == State.WAITING) {
				mJellyBeansSprite.detachSelf();
				mJellyBeansEffect.launch(x, y, pScene, ResourceManager
						.getEngine().getVertexBufferObjectManager());
			} else if (mHoneycombEffect.mState == State.WAITING) {
				mHoneycombSprite.detachSelf();
				mHoneycombEffect.launch(x, y, pScene, ResourceManager
						.getEngine().getVertexBufferObjectManager());
			} else if (mIceCreamSandwichEffect.mState == State.WAITING) {
				mIceCreamSandwichSprite.detachSelf();
				mIceCreamSandwichEffect.launch(x, y, pScene, ResourceManager
						.getEngine().getVertexBufferObjectManager());
				for (Building building : mAllBuilding) {
					if (building.type != Levels.Race.ANDROID) {
						final Building finalBuilding = building;
						building.mAllowBotSpawn = false;
						float freezeTime = mIceCreamSandwichEffect
								.getFreezeTimeFor(building);
						registerUpdateHandler(new TimerHandler(freezeTime,
								new ITimerCallback() {
									@Override
									public void onTimePassed(
											TimerHandler pTimerHandler) {
										Log.e("qwerty", "allow updates");
										finalBuilding.mAllowBotSpawn = true;
									}
								}));
					}
				}
			}
		}
		return false;
	}

	private void performUnitMovement() {
		Hero heroAndroid;
		ModifierForHero move;

		for (Building building : buildingsFrom) {
			heroAndroid = new HeroAndroid(building.buildingSprite.getX(),
					building.buildingSprite.getY(),
					buildingTo.buildingSprite.getX(),
					buildingTo.buildingSprite.getY(), mAndroidHeroPool);

			float timeToMove = Utils.calculateTime(heroAndroid.fromX,
					heroAndroid.fromY, heroAndroid.toX, heroAndroid.toY);
			move = new ModifierForHero(timeToMove, heroAndroid.fromX,
					heroAndroid.fromY, heroAndroid.toX, heroAndroid.toY,
					buildingsFrom, buildingTo, mAndroidHeroPool, heroAndroid,
					building);
			if (!heroAndroid.heroSprite.hasParent()) {
				GameLevel.this.attachChild(heroAndroid.heroSprite);
				heroAndroid.heroSprite.registerEntityModifier(move);
			}
		}
		// buildingTo.incrementNumberOfUnits(1);
	}

	public void disposeLevel() {
		// TODO dispose HUD here
	}
}
