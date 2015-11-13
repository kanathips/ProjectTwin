package com.projecttwin.gameState;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected GameCore game;
	
	protected  Vector2 location_cursor = new Vector2(0, 0);
	
	protected Array<Sprite> cursors = new Array<Sprite>();
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera camera;
	protected OrthographicCamera hudCam;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.gameCore();
		location_cursor = game.getLocationCursor();
		
		spriteBatch = game.getSpriteBatch();
		camera = game.getCamera();
		hudCam = game.getHUDCamera();
	}
	
	public abstract void create();
	public abstract void render();
	public abstract void update(float deltaTime);
	public abstract void resize(int width, int height);
	public abstract void dispose();
	
}









