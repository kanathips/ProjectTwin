package com.projecttwin.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.utils.Constants;

import sun.applet.Main;


public class StartGame extends GameState implements EventListener{
	private boolean signal_load;
	private float deltaTime;
	
	private Include_gameState include_state;
	
	protected StartGame(GameStateManager gsm,boolean signal_load) {
		super(gsm);
		this.signal_load = signal_load;
		create();
	}

	@Override
	public void create() {
		include_state = new Include_gameState(gsm,spriteBatch, location_cursor, signal_load,camera);
		include_state.create();
		
	}
	

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime += Gdx.graphics.getDeltaTime();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		
		include_state.renderStartGame(deltaTime);
		
		spriteBatch.end();
		
		
	}
	
	
	@Override
	public void update(float deltaTime) {}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {	}

	@Override
	public boolean handle(Event event) {
		return false;
	}

	

}
