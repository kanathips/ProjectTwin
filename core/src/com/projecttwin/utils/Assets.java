package com.projecttwin.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Ball;
import com.projecttwin.character.Box;
import com.projecttwin.character.Hud;
import com.projecttwin.character.MapAnimation;
import com.projecttwin.character.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public TextureAtlas atlas;
	private AssetManager assetManager;

	private Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this); // Set asset error handle to this
												// class
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);
		atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public Box getBox() {
		return new Box(atlas);
	}

	public Player getPlayer() {
		return new Player(atlas);
	}

	public Hud getHud() {
		return new Hud(atlas);
	}

	public Ball getBall() {
		return new Ball(atlas);
	}
	
	public MapAnimation getMapAnimation(){
		return new MapAnimation(atlas);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception) throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}
}