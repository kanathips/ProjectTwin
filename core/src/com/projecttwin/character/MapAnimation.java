package com.projecttwin.character;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapAnimation {

	private TextureAtlas atlas;
	public TreeMap<String, Sprite> textureTree;

	public MapAnimation(TextureAtlas atlas) {
		this.atlas = atlas;
		textureTree = new TreeMap<String, Sprite>();
		init();
	}

	private void init() {
		textureTree.put("switchOff", new Sprite(atlas.findRegion("switch0")));
		textureTree.put("switchOn", new Sprite(atlas.findRegion("switch1")));
	}

	public TreeMap<String, Sprite> getTexture() {
		return textureTree;
	}

	public TreeMap<String, Animation> getAnimation() {
		TreeMap<String, Animation> animations = new TreeMap<String, Animation>();

		// purple target
		TextureRegion loader = new TextureRegion(new Texture(Gdx.files.internal("images/targetPurple.png")));
		TextureRegion[][] splitedTexture = loader.split(64, 64);
		TextureRegion[] targetPurpleFrame = new TextureRegion[splitedTexture.length * splitedTexture[0].length];
		int k = 0;
		for (int i = 0; i < splitedTexture.length; i++) {
			for (int j = 0; j < splitedTexture[i].length; j++) {
				targetPurpleFrame[k] = splitedTexture[i][j];
				k++;
			}
		}
		animations.put("targetPurple", new Animation(0.1f, targetPurpleFrame));

		loader = new TextureRegion(new Texture(Gdx.files.internal("images/targetBlue.png")));
		splitedTexture = loader.split(64, 64);
		TextureRegion[] targetBlueFrame = new TextureRegion[splitedTexture.length * splitedTexture[0].length];
		k = 0;
		for (int i = 0; i < splitedTexture.length; i++) {
			for (int j = 0; j < splitedTexture[i].length; j++) {
				targetBlueFrame[k] = splitedTexture[i][j];
				k++;
			}
		}
		TextureRegion[] springFrame = new TextureRegion[4];
		springFrame[0] = new TextureRegion(atlas.findRegion("spring2"));
		springFrame[1] = new TextureRegion(atlas.findRegion("spring1"));
		springFrame[2] = new TextureRegion(atlas.findRegion("spring0"));
		springFrame[3] = new TextureRegion(atlas.findRegion("spring1"));
		animations.put("spring", new Animation(0.05f, springFrame));
		animations.put("targetBlue", new Animation(0.1f, targetBlueFrame));

		return animations;
	}
}
