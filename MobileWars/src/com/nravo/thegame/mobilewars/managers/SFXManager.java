package com.nravo.thegame.mobilewars.managers;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
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
    }

    public static void playMusic() {
        mMusic.play();
    }
}
