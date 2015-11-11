package com.projecttwin.character;

<<<<<<< HEAD
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import jdk.nashorn.internal.ir.BlockLexicalContext;

public class Ball {

	public TreeMap<String, Sprite> ballTexture;

	public Ball(TextureAtlas atlas) {
		ballTexture = new TreeMap<String, Sprite>();
		ballTexture.put("purpleBall", new Sprite(atlas.findRegion("ball0")));
		ballTexture.put("blueBall", new Sprite(atlas.findRegion("ball1")));
	}

	public TreeMap<String, Sprite> getTexture() {
=======
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Ball {

	public Sprite ballTexture;

	public Ball(TextureAtlas atlas) {
		ballTexture = new Sprite(atlas.findRegion("Ball"));
	}

	public Sprite getTexture() {
>>>>>>> origin/master
		return ballTexture;
	}
}
