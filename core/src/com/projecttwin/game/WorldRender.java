package com.projecttwin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Player;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.Constants;;

public class WorldRender implements Disposable{
	private OrthographicCamera camera;
	private WorldController worldController;
	private SpriteBatch batch;
	
	public WorldRender(WorldController worldController){
		this.worldController = worldController;
		init();
	}

	private void init(){
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.update();
	}
	public void render(){
		renderPlayer();
		renderMap();
		worldController.cameraHelper.applyTo(camera);
		
	}
	public void renderPlayer(){;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.playerSprite.draw(batch);
		batch.end();
	}

	public void renderMap(){
		switch(worldController.level){
			case INTRO:
				break;
			case ONE:
				break;
			case TWO:
				break;
			case THREE:
				break;
			case FOUR:
				break;
			case FIVE:
				break;
		}
	}
	
	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	public void dispose(){
		batch.dispose();
	}
}
