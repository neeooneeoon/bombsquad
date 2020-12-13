package com.oneeightfive.bombsquad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oneeightfive.bombsquad.BombSquad;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width =  1280;
		config.height = 600;
		config.forceExit = true;
		new LwjglApplication(new BombSquad(), config);
	}
}
