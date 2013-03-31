package com.nravo.thegame.mobilewars.managers;

import android.content.Context;
import android.graphics.Typeface;
import com.nravo.thegame.mobilewars.runtime.MainGameActivity;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.color.Color;

public class ResourceManager {
	private static final ResourceManager INSTANCE = new ResourceManager();
	private static final TextureOptions NORMAL_TEXTURE_OPTION = TextureOptions.BILINEAR;

	private final String MENU_GRAPHICS_PATH = "graphics/menu/";
	private final String GAME_GRAPHICS_PATH = "graphics/game/";

	public Engine engine;
	public Context context;
	public MainGameActivity activity;
	public float cameraWidth;
	public float cameraHeight;
	public float cameraScaleFactorX;
	public float cameraScaleFactorY;

	// ================== GAME RESOURCES =====================
	// TR = Texture Region
	public static ITextureRegion sGameBackgroundTR;
	public static ITextureRegion sBuildingTR;
    public static ITextureRegion sBuildingCounterBgTR;
	public static TiledTextureRegion sAndroidTTR;
	public static TiledTextureRegion sAppleTTR;

	// ================== MENU RESOURCES =====================
	// TR = Texture Region; TTR = Tiled texture region
	public static ITextureRegion sMenuBackgroundTR;
	public static TiledTextureRegion menuMainButtonsTTR;

	public static TiledTextureRegion musicToggleTTR;
	public static TiledTextureRegion soundToggleTTR;

	public static Font sFontDefault32Bold;

	private String mPreviousAssetBasePath = "";

	private ResourceManager() {
	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	public static void setup(MainGameActivity activity, Engine engine,
			Context context, float cameraWidth, float cameraHeight,
			float cameraScaleFactorX, float cameraScaleFactorY) {
		getInstance().activity = activity;
		getInstance().engine = engine;
		getInstance().context = context;
		getInstance().cameraWidth = cameraWidth;
		getInstance().cameraHeight = cameraHeight;
		getInstance().cameraScaleFactorX = cameraScaleFactorX;
		getInstance().cameraScaleFactorY = cameraScaleFactorY;
	}

	public static void loadGameResources() {
		getInstance().loadGameTextures();
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

		if (sGameBackgroundTR == null) {
			sGameBackgroundTR = getTextureRegion("bg.png",
					NORMAL_TEXTURE_OPTION);
		}
		if (sBuildingTR == null) {
			sBuildingTR = getTextureRegion("house.png", NORMAL_TEXTURE_OPTION);
		}
        if (sBuildingCounterBgTR == null) {
            sBuildingCounterBgTR = getTextureRegion("counter_bg.png", NORMAL_TEXTURE_OPTION);
		}
		if (sAndroidTTR == null) {
			sAndroidTTR = getTiledTextureRegion("android_small.png", 4, 1,
					NORMAL_TEXTURE_OPTION);
		}
		if (sAppleTTR == null) {
			sAppleTTR = getTiledTextureRegion("apple_bite.png", 5, 1,
					NORMAL_TEXTURE_OPTION);
		}

	}

	private void loadMenuTextures() {
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory
				.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(MENU_GRAPHICS_PATH);

		// MENU background
		if (sMenuBackgroundTR == null) {
			sMenuBackgroundTR = getTextureRegion("bg.png",
					NORMAL_TEXTURE_OPTION);
		}

		// MENU button
		if (menuMainButtonsTTR == null) {
			menuMainButtonsTTR = getTiledTextureRegion("button.png", 2, 1,
					NORMAL_TEXTURE_OPTION);
		}

		if (musicToggleTTR == null) {
			musicToggleTTR = getTiledTextureRegion("sound_on_off.png", 2, 1,
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
