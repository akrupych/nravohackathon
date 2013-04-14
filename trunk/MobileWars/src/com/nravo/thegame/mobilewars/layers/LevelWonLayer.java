package com.nravo.thegame.mobilewars.layers;

import com.nravo.thegame.mobilewars.effects.StarFlightEffect;
import com.nravo.thegame.mobilewars.gamelevel.GameLevel;
import com.nravo.thegame.mobilewars.gamelevel.Levels;
import com.nravo.thegame.mobilewars.input.GrowButton;
import com.nravo.thegame.mobilewars.managers.ResourceManager;
import com.nravo.thegame.mobilewars.managers.SceneManager;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

/**
 * This layer is shown to the player if a level is completed successfully
 */
public class LevelWonLayer extends ManagedLayer {

	private static final LevelWonLayer INSTANCE = new LevelWonLayer();

	public static LevelWonLayer getInstance() {
		return INSTANCE;
	}

	public static LevelWonLayer getInstance(final GameLevel currentLevel) {
		INSTANCE.setCurrentLevel(currentLevel);
		return INSTANCE;
	}

	// ===============================
	// ========= VARIABLES ===========
	// ===============================
	private GameLevel mCurrentLevel;
	private Sprite mLayerBackground;

	// ===============================
	// ======= UPDATE HANDLERS =======
	// ===============================
	// Animates the layer to slide in from the top
	IUpdateHandler mSlideInUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(float pSecondsElapsed) {
			if (LevelWonLayer.this.mLayerBackground.getY() > 0f) {
				LevelWonLayer.this.mLayerBackground
						.setY(Math.max(
								LevelWonLayer.this.mLayerBackground.getY()
										- (pSecondsElapsed * ManagedLayer.SLIDE_PIXELS_PER_SECOND),
								0f));
			} else {
				ResourceManager.getInstance().engine
						.unregisterUpdateHandler(this);
			}
		}

		@Override
		public void reset() {
		}
	};
	// Animates the layer to slide out through the top and tell the SceneManager
	// to hide it when it is off-screen
	IUpdateHandler mSlideOutUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(float pSecondsElapsed) {
			if (LevelWonLayer.this.mLayerBackground.getY() < (ResourceManager
					.getInstance().cameraHeight / 2f)
					+ (LevelWonLayer.this.mLayerBackground.getHeight() / 2f)) {
				LevelWonLayer.this.mLayerBackground
						.setY(Math.min(
								LevelWonLayer.this.mLayerBackground.getY()
										+ (pSecondsElapsed * ManagedLayer.SLIDE_PIXELS_PER_SECOND),
								(ResourceManager.getInstance().cameraHeight / 2f)
										+ (LevelWonLayer.this.mLayerBackground
												.getHeight() / 2f)));
			} else {
				ResourceManager.getInstance().engine
						.unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
		}

		@Override
		public void reset() {
		}
	};

	@Override
	public void onLoadLayer() {
		if (this.hasLoaded) {
			return;
		}
		this.hasLoaded = true;

		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionDownEnabled(true);

		final Rectangle fadingBackgroundRectangle = new Rectangle(0f, 0f,
				ResourceManager.getInstance().cameraWidth,
				ResourceManager.getInstance().cameraHeight, ResourceManager
						.getActivity().getVertexBufferObjectManager());
		fadingBackgroundRectangle.setColor(0f, 0f, 0f, 0.5f);
		this.attachChild(fadingBackgroundRectangle);

		mLayerBackground = new Sprite(
				0f,
				(ResourceManager.getInstance().cameraHeight / 2f)
						+ (ResourceManager.sLevelWonBackgroundTR.getHeight() / 2f),
				ResourceManager.sLevelWonBackgroundTR, ResourceManager
						.getActivity().getVertexBufferObjectManager());
		mLayerBackground.setScale(ResourceManager.getInstance().cameraHeight
				/ ResourceManager.sMenuBackgroundTR.getHeight());
		mLayerBackground.attachChild(new StarFlightEffect(ResourceManager
				.getEngine().getVertexBufferObjectManager(),
				ResourceManager.mStarFlightTR, 0, 0));
		this.attachChild(mLayerBackground);

		GrowButton restartButton = new GrowButton(
				mLayerBackground.getWidth() / 2f, 0,
				ResourceManager.nextLevelButtonTTR) {
			@Override
			public void onClick() {
				if (Levels.currentRoom != 2) {
					Levels.currentRoom += 1;
				} else
					Levels.currentRoom = 0;
				SceneManager.getInstance().showScene(
						new GameLevel(Levels.AVAILABLE_LEVELS[Levels.currentRoom]));
			}
		};
		restartButton.setScale(0.5f);
		mLayerBackground.attachChild(restartButton);
		this.registerTouchArea(restartButton);

		this.setPosition(ResourceManager.getInstance().cameraWidth / 2f,
				ResourceManager.getInstance().cameraHeight / 2f);
	}

	@Override
	public void onShowLayer() {
		ResourceManager.getInstance().engine
				.registerUpdateHandler(mSlideInUpdateHandler);
	}

	@Override
	public void onHideLayer() {
		// Register the slide-out animation with the engine
		ResourceManager.getInstance().engine
				.registerUpdateHandler(mSlideOutUpdateHandler);
	}

	@Override
	public void onUnloadLayer() {
	}

	public void setCurrentLevel(final GameLevel currentLevel) {
		this.mCurrentLevel = currentLevel;
	}
}
