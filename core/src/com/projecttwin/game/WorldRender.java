package com.projecttwin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Box;
import com.projecttwin.utils.Assets;
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
	private WorldPhysic worldPhysic;
	private Array<Sprite> boxSprite;
	private Sprite img;
	
	public WorldRender(WorldController worldController, WorldPhysic worldPhysic) {
		this.worldController = worldController;
		this.worldPhysic = worldPhysic;
		init();
	}

	/**
	 *  initial renderer instant
	 */
	
	private void init(){
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.update();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		boxSprite = getBoxSprite(WorldPhysic.boxBodys);
		img = new Sprite(new Texture(Gdx.files.internal("images/force.png")));
		img.setSize(worldPhysic.playerForce.getRadius() * 2, worldPhysic.playerForce.getRadius() * 2);
	}
	
	/**
	 * main render method
	 */
	public void render(float deltaTime){
		renderMap();
		renderForce(deltaTime);
		renderObject();
		worldPhysic.render(batch);
	}
	
	/**
	 * render player and box form worldController
	 */
	
	public void renderObject(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();		
		WorldController.getPlayerSprite().draw(batch);
		for(int i = 0; i < boxSprite.size; i++){
			updateBoxposition(boxSprite.get(i), WorldPhysic.boxBodys.get(i));
			boxSprite.get(i).draw(batch);
		}
		batch.end();
		
	}

	/**
	 * get tiledmap form worldController and render
	 */
	public void renderMap(){
		worldController.getTiledMapRenderer().setView(camera);
		worldController.getTiledMapRenderer().render();
	}
	
	/**
	 * When window is resized, call this method
	 * @param width of new window
	 * @param height of new window
	*/
	public void resize(int width, int height){
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	public void dispose(){
		batch.dispose();
		shapeRenderer.dispose();
	}
	
	/**
	 * render player power
	 * @param deltaTime 
	 */
	public void renderForce(float deltaTime){
		batch.setProjectionMatrix(camera.combined);
		float i = 360 * deltaTime;
		i = Constants.powerType == 1 ? i * -1 : i;
		img.rotate(i);
		if(Constants.power){
			img.setSize(Constants.metersToPixels(worldPhysic.playerForce.getRadius() * 2), Constants.metersToPixels(worldPhysic.playerForce.getRadius() * 2));
			img.setOriginCenter();
			img.setPosition(Constants.metersToPixels(worldPhysic.forceBody.getPosition().x) - img.getWidth() / 2, Constants.metersToPixels(worldPhysic.forceBody.getPosition().y) - img.getHeight() / 2);
			batch.begin();
			img.draw(batch);
			batch.end();
			if(Constants.haveObjectinPower){
				shapeRenderer.setColor(0, 0, 255, 1);
				shapeRenderer.begin(ShapeType.Line);			
				shapeRenderer.line(new Vector2(Constants.clickX, Constants.clickY), Constants.metersToPixels(Constants.bodyInPower.getPosition()));
				shapeRenderer.end();
			}
		}
	}
	

	public Array<Sprite> getBoxSprite(Array<Body> bodyArray){
		Box box = Assets.instance.getBox();
		Array<Sprite> boxSprites = new Array<Sprite>();
		for(int i = 0; i < bodyArray.size; i++){
			boxSprites.add(new Sprite(box.boxTextute));
			float x = bodyArray.get(i).getPosition().x;
			float y = bodyArray.get(i).getPosition().y;
			boxSprites.get(i).setOriginCenter();
			boxSprites.get(i).setPosition(Constants.metersToPixels(x) - boxSprites.get(i).getWidth() / 2, Constants.metersToPixels(y) - boxSprites.get(i).getHeight() / 2);
		}
		return boxSprites;
	}
	
	private void updateBoxposition(Sprite sprite, Body body){
		sprite.setPosition(Constants.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2, Constants.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
	}
}
