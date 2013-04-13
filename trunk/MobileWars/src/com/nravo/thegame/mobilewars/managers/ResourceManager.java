package com.nravo.thegame.mobilewars.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.color.Color;

import android.content.Context;
import android.graphics.Typeface;

import com.nravo.thegame.mobilewars.effects.JellyBeansEffect;
import com.nravo.thegame.mobilewars.layers.LevelWonLayer;
import com.nravo.thegame.mobilewars.runtime.MainGameActivity;

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	private static final TextureOptions NORMAL_TEXTURE_OPTION = TextureOptions.BILINEAR;

	private final String MENU_GRAPHICS_PATH = "graphics/menu/";
	private final String GAME_GRAPHICS_PATH = "graphics/game/";

	public Engine engine;
	public Context context;
	public MainGameActivity activity;
	public Camera camera;
	public float cameraWidth;
	public float cameraHeight;
	public float cameraScaleFactorX;
	public float cameraScaleFactorY;

	// ================== GAME RESOURCES =====================
	// TR = Texture Region
	public static ITextureRegion sGameBackgroundTR;
	public static ITextureRegion sLevelWonBackgroundTR;
	public static ITextureRegion sAppleSmallBuildingTR;
	public static ITextureRegion sAndroidSmallBuildingTR;
    public static ITextureRegion sBuildingCounterBgTR;
	public static TiledTextureRegion sAndroidTTR;
	public static TiledTextureRegion sAppleTTR;
	public static TextureRegion sApplePieTR;
	public static TextureRegion sMagnetTR;
	
	public static ITextureRegion sHudBackgroundTR;
	public static ITextureRegion sPowerHoneycombTR;
	public static ITextureRegion sPowerIceCreamSandwichTR;
	public static ITextureRegion sPowerJellyBeansTR;
	
	public static ITextureRegion[] sJellyBeansTRs;
	private static final String[] mJellyBeanImages = {
		"jelly_bean_blue.png",
		"jelly_bean_green.png",
		"jelly_bean_purple.png",
		"jelly_bean_red.png",
		"jelly_bean_yellow.png"
	};

	// ================== MENU RESOURCES =====================
	// TR = Texture Region; TTR = Tiled texture region
	public static ITextureRegion sMenuBackgroundTR;
	public static ITiledTextureRegion menuMainButtonTTR;

	public static TiledTextureRegion musicToggleTTR;
	public static TiledTextureRegion soundToggleTTR;

    public static TextureRegion mStarFlightTR;

    public static Font sFontDefault32Bold;

	private String mPreviousAssetBasePath = "";

	private ResourceManager() {
	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	public static void setup(MainGameActivity activity, Engine engine,
			Context context, Camera camera, float cameraWidth, float cameraHeight,
			float cameraScaleFactorX, float cameraScaleFactorY) {
		getInstance().activity = activity;
		getInstance().engine = engine;
		getInstance().context = context;
		getInstance().camera = camera;
		getInstance().cameraWidth = cameraWidth;
		getInstance().cameraHeight = cameraHeight;
		getInstance().cameraScaleFactorX = cameraScaleFactorX;
		getInstance().cameraScaleFactorY = cameraScaleFactorY;
	}

	public static void loadGameResources() {
		getInstance().loadGameTextures();
        getInstance().loadSharedResources();
        LevelWonLayer.getInstance().onLoadLayer();
	}

	public static void loadMenuResources() {
		getInstance().loadMenuTextures();
		getInstance().loadSharedResources();
	}

	public static void unloadGameResources() {
		getInstance().loadSharedResources();
	}

	public static void unloadMenuResources() {

	}

	public static void unloadSharedResources() {
		// shared sounds, fonts, textures
	}

	public static MainGameActivity getActivity() {
		return getInstance().activity;
	}

	public static Engine getEngine() {
		return getInstance().engine;
	}

    public static Context getContext() {
        return getInstance().context;
    }

    public static Camera getCamera() {
        return getInstance().camera;
    }

	// ===================================================
	// ================ PRIVATE METHODS ==================
	// ===================================================

	private void loadSharedResources() {
		loadFonts();
	}

	private void loadGameTextures() {
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory
				.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(GAME_GRAPHICS_PATH);
		//////////////////////////////// HUD ///////////////////////////////////
		if (sHudBackgroundTR == null) {
			sHudBackgroundTR = getTextureRegion("hud_background.png",
					NORMAL_TEXTURE_OPTION);
		}
		if (sPowerHoneycombTR == null) {
			sPowerHoneycombTR = getTextureRegion("power_honeycomb.png",
					NORMAL_TEXTURE_OPTION);
		}
		if (sPowerIceCreamSandwichTR == null) {
			sPowerIceCreamSandwichTR = getTextureRegion("power_ice_cream_sandwich.png",
					NORMAL_TEXTURE_OPTION);
		}
		if (sPowerJellyBeansTR == null) {
			sPowerJellyBeansTR = getTextureRegion("power_jelly_beans.png",
					NORMAL_TEXTURE_OPTION);
		}
		///////////////////////////// JELLY BEANS //////////////////////////////
		sJellyBeansTRs = new TextureRegion[mJellyBeanImages.length];
		for (int i = 0; i < mJellyBeanImages.length; i++) {
			sJellyBeansTRs[i] = getTextureRegion(mJellyBeanImages[i],
					NORMAL_TEXTURE_OPTION);
		}
		JellyBeansEffect.setTextures(sJellyBeansTRs);
		/////////////////////////////// ETC ////////////////////////////////////
		if (sGameBackgroundTR == null) {
			sGameBackgroundTR = getTextureRegion("andrew_background.png",
					NORMAL_TEXTURE_OPTION);
		}
        if (sLevelWonBackgroundTR == null) {
			sLevelWonBackgroundTR = getTextureRegion("level_won_bg.jpg",
					NORMAL_TEXTURE_OPTION);
		}
		if (sAppleSmallBuildingTR == null) {
			sAppleSmallBuildingTR = getTextureRegion("apple_mac.png",
					NORMAL_TEXTURE_OPTION);
		}
        if (sAndroidSmallBuildingTR == null) {
			sAndroidSmallBuildingTR = getTextureRegion("android.png",
					NORMAL_TEXTURE_OPTION);
		}
        if (sBuildingCounterBgTR == null) {
            sBuildingCounterBgTR = getTextureRegion("counter_bg.png",
            		NORMAL_TEXTURE_OPTION);
		}
		if (sAndroidTTR == null) {
			sAndroidTTR = getTiledTextureRegion("android_small.png", 4, 1,
					NORMAL_TEXTURE_OPTION);
		}
		if (sAppleTTR == null) {
			sAppleTTR = getTiledTextureRegion("apple_bite.png", 5, 1,
					NORMAL_TEXTURE_OPTION);
		}
		if (sApplePieTR == null) {
			sApplePieTR = getTextureRegion("apple_pie.png", NORMAL_TEXTURE_OPTION);
		}
		if (sMagnetTR == null) {
			sMagnetTR = getTextureRegion("magnet.png", NORMAL_TEXTURE_OPTION);
		}

	}

	private void loadMenuTextures() {
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory
				.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(MENU_GRAPHICS_PATH);

        if (mStarFlightTR == null) {
        	mStarFlightTR = getTextureRegion("star.png", NORMAL_TEXTURE_OPTION);
        }
        
		// MENU background
		if (sMenuBackgroundTR == null) {
			sMenuBackgroundTR = getTextureRegion("andrew_background.png",
					NORMAL_TEXTURE_OPTION);
		}

		// MENU button
		if (menuMainButtonTTR == null) {
			menuMainButtonTTR = getTiledTextureRegion("andrew_play_button.png", 2, 1,
					NORMAL_TEXTURE_OPTION);
		}

		if (musicToggleTTR == null) {
			musicToggleTTR = getTiledTextureRegion("andrew_sound_button.png", 2, 1,
					NORMAL_TEXTURE_OPTION);
		}
	}

	// =======================================
	// =============== FONTS =================
	// =======================================
	private void loadFonts() {
		if (sFontDefault32Bold == null) {
			sFontDefault32Bold = FontFactory.create(engine.getFontManager(),
					engine.getTextureManager(), 256, 256,
					Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32f,
					true, Color.BLACK_ABGR_PACKED_INT);
			sFontDefault32Bold.load();
		}
	}

	private TextureRegion getTextureRegion(String textureRegionPath,
			TextureOptions textureOptions) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource
				.create(activity.getAssets(),
						BitmapTextureAtlasTextureRegionFactory
								.getAssetBasePath() + textureRegionPath);
		final BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(),
				bitmapTextureAtlasSource.getTextureWidth(),
				bitmapTextureAtlasSource.getTextureHeight(), textureOptions);
		final TextureRegion textureRegion = new TextureRegion(
				bitmapTextureAtlas, 0, 0,
				bitmapTextureAtlasSource.getTextureWidth(),
				bitmapTextureAtlasSource.getTextureHeight(), false);
		bitmapTextureAtlas
				.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return textureRegion;
	}

	private TiledTextureRegion getTiledTextureRegion(
			String tiledTextureRegionPath, int columns, int rows,
			TextureOptions textureOptions) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource
				.create(activity.getAssets(),
						BitmapTextureAtlasTextureRegionFactory
								.getAssetBasePath() + tiledTextureRegionPath);
		final BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(),
				bitmapTextureAtlasSource.getTextureWidth(),
				bitmapTextureAtlasSource.getTextureHeight(), textureOptions);
		final ITextureRegion[] textureRegions = new ITextureRegion[columns
				* rows];

		final int tileWidth = bitmapTextureAtlas.getWidth() / columns;
		final int tileHeight = bitmapTextureAtlas.getHeight() / rows;

		for (int tileColumn = 0; tileColumn < columns; tileColumn++) {
			for (int tileRow = 0; tileRow < rows; tileRow++) {
				final int tileIndex = tileRow * columns + tileColumn;

				final int x = tileColumn * tileWidth;
				final int y = tileRow * tileHeight;
				textureRegions[tileIndex] = new TextureRegion(
						bitmapTextureAtlas, x, y, tileWidth, tileHeight, false);
			}
		}

		final TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(
				bitmapTextureAtlas, false, textureRegions);
		bitmapTextureAtlas
				.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return tiledTextureRegion;
	}
}
