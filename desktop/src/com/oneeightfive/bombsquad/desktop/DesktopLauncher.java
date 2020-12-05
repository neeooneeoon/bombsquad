package com.oneeightfive.bombsquad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oneeightfive.bombsquad.BombSquad;
import com.oneeightfive.bombsquad.ResourceManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width =  ResourceManager.V_WIDTH;
		config.height = ResourceManager.V_HEIGHT;
		new LwjglApplication(new BombSquad(), config);
	}
}
