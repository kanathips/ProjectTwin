package com.projecttwin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.utils.Constants;;

/**
 * This Class is use to render everything in this game
 * @author NewWy
 *
 */
public class WorldRender implements Disposable{
	public static OrthographicCamera camera;
	private WorldController worldController;
	private SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	private ShapeRenderer shapeRenderer;	
	Vector3 position;
	public WorldRender(WorldController worldController, WorldPhysic worldPhysic) {
		this.worldController = worldController;
		init();
	}

	// initial renderer instant
	private void init(){
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.update();
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		position = new Vector3(100, 100, 0);
		camera.unproject(position);
	}
	
	public void render(){
		renderMap();
		renderObject();
		WorldController.getCameraHelper().applyTo(camera); // apply update position to camera
	}
	
	//render object
	public void renderObject(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();		
		WorldController.getPlayerSprite().draw(batch);
		for(int i = 0; i < WorldController.getBoxSprites().length; i++){
			WorldController.getBoxSprites()[i].draw(batch);
		}
		batch.end();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.circle(Constants.clickPosition.x, Constants.clickPosition.y, 20);
		shapeRenderer.end();
		debugMatrix = batch.getProjectionMatrix().cpy().scl(Constants.ppm);
		debugRenderer.render(WorldPhysic.world, debugMatrix);
	}

	//render tiled map
	public void renderMap(){
		worldController.getTiledMapRenderer().setView(camera);
		worldController.getTiledMapRenderer().render();
	}
	
	//update view port when window has resize
	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	public void dispose(){
		batch.dispose();
		shapeRenderer.dispose();
	}
}
