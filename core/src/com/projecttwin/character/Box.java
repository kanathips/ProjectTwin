package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Box {
	public TextureRegion boxTextute;
	public static float torque = 0.0f;
	
	public Box(TextureAtlas atlas){
		boxTextute = atlas.findRegion("Box");
	}
}
