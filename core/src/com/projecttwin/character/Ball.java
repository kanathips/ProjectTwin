package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Ball {
	
	public Sprite ballTexture;

	public Ball(TextureAtlas atlas){
			ballTexture = new Sprite(atlas.findRegion("Ball"));
	}
	
	public Sprite getTexture(){
		return ballTexture;
	}
}
