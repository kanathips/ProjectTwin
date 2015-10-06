package com.projecttwin.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projecttwin.game.ProjectTwin;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Twin";
		config.useGL30 = false;
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new ProjectTwin(), config);
	}
}
