package com.projecttwin.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.projecttwin.handeller.ContactHandler;
import com.projecttwin.handeller.InputHandler;
import com.projecttwin.utils.Assets;

public class ProjectTwin extends ApplicationAdapter implements ApplicationListener{
	
	public static final String TAG = ProjectTwin.class.getName();
	private WorldController worldController;
	private WorldRender worldRender;
	private WorldPhysic worldPhysic;
	private MenuScreen menuscreen;
	
	private float deltaTime;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		
		
		
		worldController = new WorldController();
		worldPhysic = new WorldPhysic(worldController);
		worldController.setWorldPhysic(worldPhysic);
		worldRender = new WorldRender(worldController, worldPhysic);
		new ContactHandler();
		
		menuscreen = new MenuScreen(worldRender);
		
		
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(102/255f, 221/255f, 170/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
//		worldRender.render();
		
		menuscreen.render();
		
	}
	
	@Override
	public void resize(int width, int height){
//		worldRender.resize(width, height);
		menuscreen.resize(width, height);
	}
	
	@Override
	public void dispose(){

		Assets.instance.dispose();
		worldRender.dispose();
		worldController.dispose();
		worldPhysic.dispose();
//		menuscreen.dispose();
	}
}
