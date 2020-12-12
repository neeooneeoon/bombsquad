package com.oneeightfive.bombsquad.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class BGM implements Disposable {

    Music menuBgm;
    Music inGameBGM;

    public BGM() {
        menuBgm = Gdx.audio.newMusic(Gdx.files.internal("audio/menu_bgm.ogg"));
        inGameBGM = Gdx.audio.newMusic(Gdx.files.internal("audio/ingame_bgm.ogg"));
    }

    public void playMenu() {
        menuBgm.setVolume(0.05f);
        menuBgm.play();
    }

    public void playInGame() {
        inGameBGM.setVolume(0.05f);
        inGameBGM.play();
    }

    @Override
    public void dispose() {
        menuBgm.dispose();
        inGameBGM.dispose();
    }
}
