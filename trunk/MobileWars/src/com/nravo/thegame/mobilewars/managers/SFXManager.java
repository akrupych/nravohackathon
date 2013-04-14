package com.nravo.thegame.mobilewars.managers;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

import java.io.IOException;

/**
 * Handles the playback of music and sounds as well as their muted state
 */
public class SFXManager {
	private static final SFXManager INSTANCE = new SFXManager();

	private static Music mMusic;

	private static Sound mExplosion;
	private static Sound mIce;
	private static Sound mBees;
	private static Sound mBeans;

	public static SFXManager getInstance() {
		return INSTANCE;
	}

	public boolean mSoundsMuted;
	public boolean mMusicMuted;

	private SFXManager() {
		MusicFactory.setAssetBasePath("sounds/");
		try {
			mMusic = MusicFactory.createMusicFromAsset(ResourceManager
					.getActivity().getMusicManager(), ResourceManager
					.getActivity(), "music.mp3");
		} catch (IOException e) {
			Debug.e(e);
		}

		SoundFactory.setAssetBasePath("sounds/");
		try {
			mExplosion = SoundFactory.createSoundFromAsset(ResourceManager
					.getActivity().getSoundManager(), ResourceManager
					.getActivity(), "boom_sound.mp3");
			mIce = SoundFactory.createSoundFromAsset(ResourceManager
					.getActivity().getSoundManager(), ResourceManager
					.getActivity(), "ice-cracking-01.wav");
			mBees = SoundFactory.createSoundFromAsset(ResourceManager
					.getActivity().getSoundManager(), ResourceManager
					.getActivity(), "KILLR_B2.WAV");
			mBeans = SoundFactory.createSoundFromAsset(ResourceManager
					.getActivity().getSoundManager(), ResourceManager
					.getActivity(), "221990_SOUNDDOGS__be.mp3");

		} catch (IOException e) {
			Debug.e(e);
		}
	}

	// ======================================
	// ============= METHODS ================
	// ======================================

	public static boolean isSoundMuted() {
		return getInstance().mSoundsMuted;
	}

	public static boolean isMusicMuted() {
		return getInstance().mMusicMuted;
	}

	public static void setSoundMuted(final boolean muted) {
		getInstance().mSoundsMuted = muted;
		setVolumeForAllSounds(getInstance().mSoundsMuted ? 0f : 1f);
	}

	private static void setVolumeForAllSounds(final float volume) {
		mExplosion.setVolume(volume);
	}

	public static void setMusicMuted(final boolean muted) {
		getInstance().mMusicMuted = muted;
		if (getInstance().mMusicMuted) {
			mMusic.pause();
		} else {
			mMusic.play();
		}
	}

	public static boolean toggleMusicMuted() {
		getInstance().mMusicMuted = !getInstance().mMusicMuted;
		if (getInstance().mMusicMuted) {
			mMusic.pause();
		} else {
			mMusic.play();
		}
		return getInstance().mMusicMuted;
	}

	public static void playMusic() {
        mMusic.setVolume(0.2f);
        mMusic.setLooping(true);
		mMusic.play();
	}

	private static void playSound(final Sound sound, final float pRate,
			final float pVolume) {
		if (isSoundMuted()) {
			return;
		}

		sound.setRate(pRate);
		sound.setVolume(pVolume);
		sound.play();
	}

	public static void pauseMusic() {
		mMusic.pause();
	}

	public static void resumeMusic() {
		if (!isMusicMuted()) {
			mMusic.resume();
		}
	}

	// ========================= SOUNDS ============================
	public static void playExplosion(final float rate, final float volume) {
		playSound(mExplosion, rate, volume);
	}

	public static void playIce(final float rate, final float volume) {
		playSound(mIce, rate, volume);
	}

	public static void playBean(final float rate, final float volume) {
		playSound(mBeans, rate, volume);
	}

	public static void playBees(final float rate, final float volume) {
		playSound(mBees, rate, volume);
	}
}
