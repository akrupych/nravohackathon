package com.nravo.thegame.mobilewars.entity;

import com.nravo.thegame.mobilewars.Utils.Utils;
import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.gamelevel.Levels;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.modifier.ModifierForEnemy;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * A building where units are generated
 */
public class Building extends Entity {

	private static final float UNIT_REGENERATION_DELAY_IN_SEC = 1f;
	private static final int MAX_NUMBER_OF_UNITS_IN_BUILDING = 40;

	private final GameLevel mGameLevel;
	public AnimatedSprite buildingSprite;
	public double mNumberOfUnits;
	private boolean isMy;
	public Levels.Race type;
	public float x;
	public float y;

	public Building(final GameLevel gameLevel, final Levels.Race buildingRace,
			final float x, final float y, final int initialNumberOfUnits) {
		isMy = true;
		type = buildingRace;
		this.mGameLevel = gameLevel;
		this.mNumberOfUnits = initialNumberOfUnits;
		this.x = x;
		this.y = y;
		buildSprite();
	}

	public void buildSprite() {

		// BUILDING SPRITE
		ITiledTextureRegion buildingTextureRegion = type
				.equals(Levels.Race.APPLE_IOS) ? ResourceManager.sAppleBigBuildingTTR
				: ResourceManager.sAndroidBigBuildingTR;

		if (type.equals(Levels.Race.NEUTRAL))
			buildingTextureRegion = ResourceManager.sNeutralButildingTTR;

		buildingSprite = new AnimatedSprite(x, y, buildingTextureRegion,
				ResourceManager.getActivity().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				boolean isAndroid = (type == Levels.Race.ANDROID);

				if (pSceneTouchEvent.isActionOutside()) {
					return true;
				}
				if (pSceneTouchEvent.isActionDown() && isAndroid
						&& mGameLevel.buildingsFrom.isEmpty()) {
					mGameLevel.buildingsFrom.add(Building.this);
					return false;
				} else if (pSceneTouchEvent.isActionMove() && isAndroid
						&& !mGameLevel.buildingsFrom.contains(Building.this)) {
					mGameLevel.buildingsFrom.add(Building.this);
					return true;
				} else if (pSceneTouchEvent.isActionUp() && !isAndroid
						&& !mGameLevel.buildingsFrom.contains(Building.this)
						&& mGameLevel.buildingTo == null) {
					mGameLevel.buildingTo = (Building.this);
					return false;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		buildingSprite.animate(new long[] { 150, 150, 150, 150, 150, 150, 150,
				150, 150, 150 });
		mGameLevel.attachChild(buildingSprite);
		mGameLevel.registerTouchArea(buildingSprite);

		// coordinates for building unit counter
		final float counterWidth = buildingSprite.getWidth() / 2;
		final float counterHeight = buildingSprite.getHeight() + 0.25f
				* buildingSprite.getHeight();

		// Background for counter text
		final Sprite counterBgSprite = new Sprite(counterWidth, counterHeight,
				ResourceManager.sBuildingCounterBgTR, ResourceManager
						.getActivity().getVertexBufferObjectManager());
		buildingSprite.attachChild(counterBgSprite);

		// Text displaying number of units
		Text unitNumber = new Text(counterWidth, counterHeight,
				ResourceManager.sFontDefault32Bold,
				String.valueOf((int) mNumberOfUnits), 100, ResourceManager
						.getEngine().getVertexBufferObjectManager()) {

			float timePassed = 0;
			float timePassedForBot = 0;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				timePassed += pSecondsElapsed;
				timePassedForBot += pSecondsElapsed;

				if (timePassedForBot >= 5f) {
					Building from = mGameLevel.mAllBuilding
							.get(((int) (Math.random() * 1000) % mGameLevel.mAllBuilding
									.size()));
					Building to;
					if (from.type == Levels.Race.APPLE_IOS) {
						to = mGameLevel.mAllBuilding
								.get(((int) (Math.random() * 1000) % mGameLevel.mAllBuilding
										.size()));
						if (to.type != Levels.Race.APPLE_IOS) {
							Hero heroApple;
							ModifierForEnemy move;
							heroApple = new HeroApple(
									from.buildingSprite.getX(),
									from.buildingSprite.getY(),
									to.buildingSprite.getX(),
									to.buildingSprite.getY(),
									mGameLevel.mAppleHeroPool);
							float timeToMove = Utils.calculateTime(
									heroApple.fromX, heroApple.fromY,
									heroApple.toX, heroApple.toY);
							move = new ModifierForEnemy(timeToMove,
									heroApple.fromX, heroApple.fromY,
									heroApple.toX, heroApple.toY, to,
									heroApple, from, mGameLevel.mAppleHeroPool);
                            if (!heroApple.heroSprite.hasParent()) {
							    mGameLevel.attachChild(heroApple.heroSprite);
                            }
							heroApple.heroSprite.registerEntityModifier(move);
						}
					}
					timePassedForBot = 0;
				}

				if (timePassed >= UNIT_REGENERATION_DELAY_IN_SEC) {
					if (mNumberOfUnits < MAX_NUMBER_OF_UNITS_IN_BUILDING) {
						if (!type.equals(Levels.Race.NEUTRAL)) {
							mNumberOfUnits++;
						}
						timePassed = 0;

						// TODO
						// increment number of total units depending on
						// race of the building.

					}
				}
				this.setText(String.valueOf((int) mNumberOfUnits));
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		buildingSprite.attachChild(unitNumber);
	}

	public int decrementNumberOfUnits() {
		int count = (int) (mNumberOfUnits / 2);
		mNumberOfUnits -= count;
		return count;
	}

	// Androids run
	public void decrementNumberOfUnits(Hero hero) {

		if (type == Levels.Race.ANDROID) {
			mNumberOfUnits += hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy > 0
				&& type == Levels.Race.APPLE_IOS) {
			mNumberOfUnits -= hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy < 0
				&& type == Levels.Race.APPLE_IOS) {
			mNumberOfUnits = (mNumberOfUnits - hero.countOfEnemy) * (-1);
			type = type.equals(Levels.Race.APPLE_IOS) ? Levels.Race.ANDROID
					: Levels.Race.APPLE_IOS;
			mGameLevel.mAllBuilding.get(mGameLevel.mAllBuilding.indexOf(this)).type = type;
			isMy = false;
			buildingSprite.detachSelf();
			buildingSprite.clearEntityModifiers();
			buildingSprite.clearUpdateHandlers();
			buildingSprite = null;
			buildSprite();
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy > 0
				&& type == Levels.Race.NEUTRAL) {
			mNumberOfUnits -= hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy < 0
				&& type == Levels.Race.NEUTRAL) {
			mNumberOfUnits = (mNumberOfUnits - hero.countOfEnemy) * (-1);
			type = Levels.Race.ANDROID;
			mGameLevel.mAllBuilding.get(mGameLevel.mAllBuilding.indexOf(this)).type = type;
			isMy = false;
			buildingSprite.detachSelf();
			buildingSprite.clearEntityModifiers();
			buildingSprite.clearUpdateHandlers();
			buildingSprite = null;
			buildSprite();
			return;
		}
	}

	public void decrementNumberOfUnitsEnemy(Hero hero) {
		if (type == Levels.Race.APPLE_IOS) {
			mNumberOfUnits += hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy > 0
				&& type == Levels.Race.ANDROID) {
			mNumberOfUnits -= hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy < 0
				&& type == Levels.Race.ANDROID) {
			mNumberOfUnits = (mNumberOfUnits - hero.countOfEnemy) * (-1);
			type = type.equals(Levels.Race.APPLE_IOS) ? Levels.Race.ANDROID
					: Levels.Race.APPLE_IOS;
			mGameLevel.mAllBuilding.get(mGameLevel.mAllBuilding.indexOf(this)).type = type;
			isMy = false;
			buildingSprite.detachSelf();
			buildingSprite.clearEntityModifiers();
			buildingSprite.clearUpdateHandlers();
			buildingSprite = null;
			buildSprite();
		}
		if (mNumberOfUnits - hero.countOfEnemy > 0
				&& type == Levels.Race.NEUTRAL) {
			mNumberOfUnits -= hero.countOfEnemy;
			return;
		}
		if (mNumberOfUnits - hero.countOfEnemy < 0
				&& type == Levels.Race.NEUTRAL) {
			mNumberOfUnits = (mNumberOfUnits - hero.countOfEnemy) * (-1);
			type = Levels.Race.APPLE_IOS;
			mGameLevel.mAllBuilding.get(mGameLevel.mAllBuilding.indexOf(this)).type = type;
			isMy = false;
			buildingSprite.detachSelf();
			buildingSprite.clearEntityModifiers();
			buildingSprite.clearUpdateHandlers();
			buildingSprite = null;
			buildSprite();
			return;
		}
	}

}
