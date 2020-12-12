package com.oneeightfive.bombsquad.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class Sounds implements Disposable {

    Sound footstep;
    Sound fire;
    Sound powerup;
    Sound explode;
    Sound planted;
    Sound die;

    Sound select;
    Sound selected;

    Sound enemy_die;

    Boolean isFootstep = false;

    public Sounds() {
        footstep = Gdx.audio.newSound(Gdx.files.internal("audio/footstep.wav"));
        fire = Gdx.audio.newSound(Gdx.files.internal("audio/fire.wav"));
        powerup = Gdx.audio.newSound(Gdx.files.internal("audio/powerup.wav"));
        explode = Gdx.audio.newSound(Gdx.files.internal("audio/explode.wav"));
        die = Gdx.audio.newSound(Gdx.files.internal("audio/die.wav"));
        select = Gdx.audio.newSound(Gdx.files.internal("audio/select.wav"));
        selected = Gdx.audio.newSound(Gdx.files.internal("audio/selected.wav"));
        enemy_die = Gdx.audio.newSound(Gdx.files.internal("audio/enemy_die.wav"));
        planted = Gdx.audio.newSound(Gdx.files.internal("audio/planted.wav"));
    }

    public void playFootstep() {
        if(!isFootstep) {
            footstep.play(0.5f);
            isFootstep = true;
        }
    }

    public void stopFootstep() {
        footstep.stop();
        isFootstep = false;
    }

    public void playPlanted() {
        planted.play(0.5f);
    }

    public void playFire() {
        fire.play(0.5f);
    }

    public void playPowerUp() {
        powerup.play(0.5f);
    }

    public void playExplode() {
        explode.play(0.5f);
    }

    public void playDie() {
        die.play(0.5f);
    }

    public void playSelect() {
        select.play(0.5f);
    }

    public void playSelected() {
        selected.play(0.5f);
    }

    public void playEnemyDie() {
        enemy_die.play(0.5f);
    }

    @Override
    public void dispose() {
        footstep.dispose();
        fire.dispose();
        powerup.dispose();
        explode.dispose();
        die.dispose();
        select.dispose();
        selected.dispose();
        enemy_die.dispose();
        planted.dispose();
    }
}
