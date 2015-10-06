package com.projecttwin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.CameraHelper;

public class WorldController extends InputAdapter {
	public static final String TAG = WorldController.class.getName();
	public Sprite[] testSprite;
	public CameraHelper cameraHelper;
	public StageLevel level;
	public Sprite playerSprite;
	private Player player;
	
	public enum StageLevel{
		INTRO, ONE, TWO, THREE, FOUR, FIVE;
	}
	
	public WorldController(){
		init();
	}
	
	private void init(){
		Gdx.input.setInputProcessor(this);
		level = StageLevel.INTRO;
		player = Assets.instance.player;
		playerSprite = new Sprite(player.playerFrame);
		cameraHelper = new CameraHelper();
	}
	
	public void update(float deltaTime){
		HandleInput(deltaTime);
		cameraHelper.update(deltaTime);
		player.update(deltaTime);
	}

	private void HandleInput(float deltaTime) {
		float cameraSpeed = 100 * deltaTime;
		int acc = 5;
		float playerSpeed = 50 * deltaTime;
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
		
	}
	
	private void moveCamera(float x, float y) {
		x += cameraHelper.position.x;
		y += cameraHelper.position.y;
		cameraHelper.position.set(x, y);
	}
	
	private void movePlayer(float x, float y){
		x += playerSprite.getX();
		y += playerSprite.getY();
		playerSprite.setPosition(x, y);
	}
		
	@Override
	public boolean keyDown(int keycode) {
		if(Keys.ESCAPE == keycode)
			Gdx.app.exit();
		if(Keys.ENTER == keycode){
			//get Position
			System.out.println("camera " + cameraHelper.getPosition());
			System.out.println("player " + playerSprite.getX() + " " + playerSprite.getY() );
		}
		if(Keys.SPACE == keycode){
			System.out.println("facing Left : " + player.facingLeft + " State : " + player.getState());
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode){
		if(Keys.D == keycode){
			player.setState(State.STANDING);
			player.setFacingLeft(false);
			System.out.println("UP D");
		}
		if(Keys.A == keycode){
			player.setState(State.STANDING);
			player.setFacingLeft(true);
			System.out.println("UP A");
		}
		if(Keys.W == keycode){
			
		}
		if(Keys.S == keycode){
			
		}
		return false;
	}
	
}
