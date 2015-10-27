package com.projecttwin.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projecttwin.gameState.GameCore;

public class Desktop {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = false;
	
		config.title = GameCore.TITLE;
		config.width = GameCore.V_WIDTH ;
		config.height = GameCore.V_HEIGHT ;
		
		new LwjglApplication(new GameCore(),config);
	
	}
}
