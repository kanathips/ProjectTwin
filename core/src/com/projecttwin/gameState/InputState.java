package com.projecttwin.gameState;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class InputState {
	protected GameStateManager gsm;
	protected GameCore game;

	protected SpriteBatch spriteBatch;
	protected OrthographicCamera camera;
	protected OrthographicCamera hudCam;

	protected InputState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.gameCore();
		spriteBatch = game.getSpriteBatch();
		camera = game.getCamera();
		hudCam = game.getHUDCamera();
	}
}
