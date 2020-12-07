package com.oneeightfive.bombsquad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.ResourceManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width =  960;
		config.height = 540;
		new LwjglApplication(new BombSquad(), config);
	}
}
