package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MainCharacter {
	public final AtlasRegion character;
	public MainCharacter(TextureAtlas atlas){
		character = atlas.findRegion("Triangel");
	}
}
