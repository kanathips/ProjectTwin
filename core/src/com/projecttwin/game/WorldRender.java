package com.projecttwin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.utils.Constants;;

public class WorldRender implements Disposable{
	public OrthographicCamera camera;
	private WorldController worldController;
	private SpriteBatch batch;
	private WorldPhysic worldPhysic;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	

	public WorldRender(WorldController worldController, WorldPhysic worldPhysic) {
		this.worldController = worldController;
		this.worldPhysic = worldPhysic;
		init();
	}

	// initial renderer instant
	private void init(){
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		
		camera.update();
		//Uncomment to see a debug mode of box2d
		//debugRenderer = new Box2DDebugRenderer();
	}
	
	public void render(){
		renderMap();
		renderObject();
		worldController.cameraHelper.applyTo(camera); // apply update position to camera
	}
	
	//render object
	public void renderObject(){;
		batch.setProjectionMatrix(camera.combined);
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.rect(worldController.playerSprite.getX(), worldController.playerSprite.getY(), worldController.playerSprite.getWidth(), worldController.playerSprite.getHeight());
		
		batch.begin();		
		worldController.playerSprite.draw(batch);
		for(int i = 0; i < worldController.boxSprites.length; i++){
			worldController.boxSprites[i].draw(batch);
			shapeRenderer.rect(worldController.boxSprites[i].getX(), worldController.boxSprites[i].getY(), worldController.boxSprites[i].getWidth(), worldController.boxSprites[i].getHeight());
		}
		//uncomment to see a debug mode of box2d 
//			debugMatrix = batch.getProjectionMatrix();
//			debugRenderer.render(worldPhysic.world, debugMatrix);
		batch.end();
		shapeRenderer.end();
	}

	//render tiled map
	public void renderMap(){
		worldController.tiledMapRenderer.setView(camera);
		worldController.tiledMapRenderer.render();
	}
	
	//update view port when window has resize
	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	public void dispose(){
		batch.dispose();
		camera = null;
	}
}
