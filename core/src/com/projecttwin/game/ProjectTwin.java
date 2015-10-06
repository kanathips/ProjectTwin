package com.projecttwin.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.projecttwin.character.Player;
import com.projecttwin.utils.Assets;

public class ProjectTwin extends ApplicationAdapter {
	
	public static final String TAG = ProjectTwin.class.getName();
	private WorldController worldController;
	private WorldRender worldRender;
	private float deltaTime;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		worldController = new WorldController();
		worldRender = new WorldRender(worldController);
	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		worldController.update(deltaTime);
		Gdx.gl.glClearColor(102/255f, 221/255f, 170/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		worldRender.render();
	}
	
	@Override
	public void resize(int width, int height){
		worldRender.resize(width, height);
	}
	
	@Override
	public void dispose(){
		worldRender.dispose();
		Assets.instance.dispose();
	}
}
