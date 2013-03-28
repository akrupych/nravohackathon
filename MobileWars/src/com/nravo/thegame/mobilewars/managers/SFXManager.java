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

    public static SFXManager getInstance() {
        return INSTANCE;
    }

    public boolean mSoundsMuted;
    public boolean mMusicMuted;

    private SFXManager() {
        MusicFactory.setAssetBasePath("sounds/");
        try {
            mMusic = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(),
                    ResourceManager.getActivity(), "music.ogg");
        } catch (IOException e) {
            Debug.e(e);
        }

        SoundFactory.setAssetBasePath("sounds/");
        try {
            mExplosion = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(),
                    ResourceManager.getActivity(), "boom_sound.mp3");
        } catch (IOException e) {
            Debug.e(e);
        }
    }

    public static boolean isSoundMuted() {
        return getInstance().mSoundsMuted;
    }

    public static void playMusic() {
        mMusic.play();
    }

    public static void playExplosion(final float rate, final float volume) {
        playSound(mExplosion, rate, volume);
    }

    private static void playSound(final Sound sound, final float pRate, final float pVolume) {
        if (isSoundMuted()) {
            return;
        }

        sound.setRate(pRate);
        sound.setVolume(pVolume);
        sound.play();
    }
}
