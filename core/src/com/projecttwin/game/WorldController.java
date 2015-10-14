package com.projecttwin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Box;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.CameraHelper;
import com.projecttwin.utils.Constants;

public class WorldController extends InputAdapter implements Disposable{
	public static final String TAG = WorldController.class.getName();
	public Sprite[] testSprite;
	public CameraHelper cameraHelper;
	public StageLevel level;
	public Sprite playerSprite;
	public Sprite[] boxSprites;
	protected Player player;
	protected Box box;
	private WorldPhysic worldPhysic;
	private float torque = 0.0f;
	TiledMap tiledMap;
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	
	
	public enum StageLevel{
		INTRO, ONE, TWO, THREE, FOUR, FIVE;
	}
	
	public WorldController(){
		init();
	}
	
	public void setWorldPhysic(WorldPhysic worldPhysic){
		this.worldPhysic = worldPhysic;		
	}
	
	private void init(){
		Gdx.input.setInputProcessor(this);
		level = StageLevel.INTRO;
		player = Assets.instance.getPlayer();
		box = Assets.instance.getBox();
		boxSprites = new Sprite[1];
		for(int i = 0; i < 1; i++){
			boxSprites[i] = new Sprite(box.boxTextute);
			float randomX = MathUtils.random(100, Constants.VIEWPORT_WIDTH - 100);
			float randomY = MathUtils.random(100, Constants.VIEWPORT_HEIGHT - 100);
			boxSprites[i].setOriginCenter();
			boxSprites[i].setPosition(randomX, randomY);
		}
		playerSprite = new Sprite(player.playerFrame);
		playerSprite.setPosition(50, 190);
		cameraHelper = new CameraHelper();
		cameraHelper.setTarget(playerSprite);
		tiledMap = new TmxMapLoader().load("Testing_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void update(float deltaTime){
		HandleInput(deltaTime);
		cameraHelper.update(deltaTime);
		updateBox();
		updatePlayer(deltaTime);
	}

	//Handle keyboard input method
	private void HandleInput(float deltaTime) {
		float cameraSpeed = 100 * deltaTime;
		int acc = 5;
		float playerSpeed = 960 * 1000;
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)){
			cameraSpeed *= acc;
			playerSpeed *= acc;
		}
		//Camera Control
		if(Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, cameraSpeed);
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -cameraSpeed);
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(cameraSpeed, 0);
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-cameraSpeed, 0);
		
		//Player Control
		if(Gdx.input.isKeyPressed(Keys.W)){
			//jump
			player.setState(State.JUMPING);
			movePlayer(0, playerSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.S))
			movePlayer(0, -playerSpeed);
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			//walk left
			player.setState(State.WALKING);
			player.setFacingLeft(true);
			movePlayer(-playerSpeed, 0);
		}
			
		if(Gdx.input.isKeyPressed(Keys.D)){
			//walk right			
			player.setState(State.WALKING);
			player.setFacingLeft(false);
			movePlayer(playerSpeed, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.ALT_LEFT)){
			torque += 0.01;
		}
		
		if(Gdx.input.isKeyPressed(Keys.ALT_RIGHT)){
			torque -= 0.01;
		}
	}
	
	// Have a box movement bug here 
	private void updateBox(){
		for(int i = 0; i < boxSprites.length; i++){
			boxSprites[i].setPosition(worldPhysic.boxBodys[i].getPosition().x  - boxSprites[i].getWidth() / 2 
					, worldPhysic.boxBodys[i].getPosition().y - boxSprites[i].getHeight() / 2);
			boxSprites[i].setRotation((float)Math.toDegrees(worldPhysic.boxBodys[i].getAngle()));
		}
	}
	
	private void updatePlayer(float deltaTime){
		player.update(deltaTime);
		playerSprite.setRegion(player.playerFrame);
		playerSprite.setPosition(worldPhysic.playerBody.getPosition().x - playerSprite.getWidth() / 2
				, worldPhysic.playerBody.getPosition().y - playerSprite.getHeight() / 2);
	}
	//move camera
	private void moveCamera(float x, float y) {
		x += cameraHelper.position.x;
		y += cameraHelper.position.y;
		cameraHelper.setTarget(null);
		cameraHelper.setPosition(x, y);
	}
	
	//move player character
	private void movePlayer(float x, float y){
		x -= worldPhysic.playerBody.getLinearVelocity().x;
		y -= worldPhysic.playerBody.getLinearVelocity().y;
		worldPhysic.playerBody.applyForceToCenter(new Vector2(x, y), true);;
	}
		
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
			case(Keys.ESCAPE):
				Gdx.app.exit();
				break;
			case(Keys.R):
				init();
				worldPhysic.init();
				break;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode){
		switch(keycode){
			case(Keys.D):
				player.setState(State.STANDING);
				player.setFacingLeft(false);
				player.setTrigger(0);
				break;
			case(Keys.A):
				player.setState(State.STANDING);
				player.setFacingLeft(true);
				player.setTrigger(0);
				break;
			case(Keys.W):
				break;
			case(Keys.S):
				break;
			case(Keys.UP):
			case(Keys.LEFT):
			case(Keys.RIGHT):
			case(Keys.DOWN):
				cameraHelper.setTarget(playerSprite);
				break;
		}
		return false;
	}
	
	//apply force to every box
	public void forceToBox(float forceX, float forceY, int screenX, int screenY){
		for(Body b: worldPhysic.boxBodys){
			b.applyForce(forceX, forceY, screenX, screenY, true);
		}
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        forceToBox(-10, 0, screenX, screenY);
        return true;
	}
	
	public void dispose(){
		player.dispose();
	}
	
}
