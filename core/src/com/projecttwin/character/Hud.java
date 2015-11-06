package com.projecttwin.character;

import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Hud {
	private TextureAtlas atlas;
	public TreeMap<String, Sprite> textureTree;
	public Hud(TextureAtlas atlas){
		this.atlas = atlas;
		init();
	}

	private void init() {
		textureTree = new TreeMap<String, Sprite>();
		for(Integer i = 0; i <= 9; i++){
			textureTree.put(i.toString(), new Sprite(atlas.findRegion("number" + i)));
		}
		for(Integer i = 0; i <=3; i++){
			textureTree.put("star" + i, new Sprite(atlas.findRegion("star" + i)));
		}
	}
	
	public TreeMap<String, Sprite> getTexture(){
		return textureTree;
	}
}
