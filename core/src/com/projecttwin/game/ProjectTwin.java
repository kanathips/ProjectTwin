package com.projecttwin.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.projecttwin.gameState.GameState;
import com.projecttwin.gameState.GameStateManager;
import com.projecttwin.handeller.ContactHandler;
import com.projecttwin.handeller.InputHandler;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.Constants;

public class ProjectTwin extends GameState {
<<<<<<< HEAD

	public static final String TAG = ProjectTwin.class.getName();
=======
	
>>>>>>> origin/master
	private WorldController worldController;
	private WorldRender worldRender;
	private float deltaTime;
	private int stage;
	private WorldPhysic worldPhysic;
	private InputHandler inputHandler;
<<<<<<< HEAD

=======
	protected static final String TAG = ProjectTwin.class.getName();
	
>>>>>>> origin/master
	public ProjectTwin(GameStateManager gsm,int stage) {
		super(gsm);
		this.stage = stage;
		System.out.println("Welcome to stage : "+stage);
		Assets.instance.init(new AssetManager());
		this.stage = stage;
		System.out.println(stage);
		create();
	}

	@Override
	public void create() {
		Constants.setting();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		worldController = new WorldController();
		worldPhysic = new WorldPhysic(worldController);
		new ContactHandler();
		inputHandler = new InputHandler();
		worldRender = new WorldRender(worldController, worldPhysic);
		
	}
	@Override
	public void render() {
		deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRender.render(deltaTime);
<<<<<<< HEAD
	}

=======
		
	}
	@Override
	public void update(float deltaTime) {
		if (Constants.gameOver) {
			create();
		}
		worldController.update(deltaTime);
		worldPhysic.update(deltaTime);
		inputHandler.update();
		Gdx.graphics.setTitle("Twin" + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
		
	}
>>>>>>> origin/master
	@Override
	public void resize(int width, int height) {
		worldRender.resize(width, height);
		
	}
<<<<<<< HEAD

=======
>>>>>>> origin/master
	@Override
	public void dispose() {
		worldRender.dispose();
		Assets.instance.dispose();
		worldController.dispose();
		worldPhysic.dispose();
<<<<<<< HEAD
	}

	@Override
	public void update(float deltaTime) {
		if (Constants.gameOver) {
			create();
		}
		worldController.update(deltaTime);
		worldPhysic.update(deltaTime);
		inputHandler.update();
		Gdx.graphics.setTitle("Twin" + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
=======
		
>>>>>>> origin/master
	}

	
}