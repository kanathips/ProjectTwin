package com.projecttwin.character;

import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MapAnimation {

	private TextureAtlas atlas;
	public TreeMap<String, Sprite> textureTree;
	public MapAnimation(TextureAtlas atlas){
		this.atlas = atlas;
		textureTree = new TreeMap<String, Sprite>();
		init();
	}
	
	private void init(){
		textureTree.put("switchOff", new Sprite(atlas.findRegion("switch0")));
		textureTree.put("switchOn", new Sprite(atlas.findRegion("switch1")));
	}
	
	public TreeMap<String, Sprite> getTexture(){
		return textureTree;
	}
}
