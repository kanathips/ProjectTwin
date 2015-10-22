package com.projecttwin.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projecttwin.game.*;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Twin";
		config.useGL30 = false;
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new ProjectTwin(),config);
		
	}
}
