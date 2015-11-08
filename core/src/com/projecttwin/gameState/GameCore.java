package com.projecttwin.gameState;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCore implements ApplicationListener {

	public static final String TITLE = "Twin";
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 768;

	public static final float STEP = 1 / 60f;
	private float accum;

	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;

	private GameStateManager gsm;

	public void create() {

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);

		gsm = new GameStateManager(this);
		gsm.pushState(new StartGame(gsm));

	}

	public void render() {

		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}

	}

	public void dispose() {

	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getHUDCamera() {
		return hudCam;
	}

	public void resize(int w, int h) {
	}

	public void pause() {
	}

	public void resume() {
	}

}
