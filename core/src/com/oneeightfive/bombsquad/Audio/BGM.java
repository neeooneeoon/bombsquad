package com.oneeightfive.bombsquad.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class BGM implements Disposable {

    Music bgm;

    public BGM() {
        bgm = Gdx.audio.newMusic(Gdx.files.internal("audio/bgm.ogg"));

    }

    public void play() {
        bgm.setVolume(0.05f);
        bgm.play();
    }

    @Override
    public void dispose() {
        bgm.dispose();
    }
}
