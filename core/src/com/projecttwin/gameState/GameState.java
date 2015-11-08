package com.projecttwin.gameState;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

	protected GameStateManager gsm;
	protected GameCore game;

	protected SpriteBatch spriteBatch;
	protected OrthographicCamera camera;
	protected OrthographicCamera hudCam;

	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.gameCore();
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
