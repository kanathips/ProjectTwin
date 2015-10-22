package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.utils.Constants;

public class Player implements Disposable{
	private TextureRegion standLeft;
	private TextureRegion standRight;
	
	private Animation walkingLeft;
	private Animation walkingRight;
	private Animation sleepingLeft;
	private Animation sleepingRight;
	public static State state  = State.STANDING;
	private static boolean facingLeft = false;
	private final float walkUpdateTrigger = 0.1f;
	private final float sleepUpdateTrigger = 0.3f;
	private static float trigger = 0;
	public TextureRegion playerFrame;
	private TextureAtlas atlas;
	private BodyDef bodyDef;
	private final static float moveSpeed = 2f;
	private boolean atGround = true;
	private static PlayerForce playerForce;
	private Body forceBody;
	
	public Player(TextureAtlas atlas){
		this.atlas = atlas;
		init();
	}
	
	/**
	 *  initial player character's animation
	 */
	public void init(){
		standRight = atlas.findRegion("stand");
		standLeft = new TextureRegion(standRight);
		standLeft.flip(true, false);
		playerFrame = standRight;
		
		TextureRegion[] walkingRightFrame = new TextureRegion[11];
		for(int i = 0; i < 11; i++)
			walkingRightFrame[i] = atlas.findRegion("walk" + i); 
		walkingRight = new Animation(walkUpdateTrigger, walkingRightFrame);
		
		TextureRegion[] walkingLeftFrame = new TextureRegion[11];
		for(int i = 0; i < 11 ; i++){
			walkingLeftFrame[i] = new TextureRegion(walkingRightFrame[i]);
			walkingLeftFrame[i].flip(true, false);
		}
		walkingLeft = new Animation(walkUpdateTrigger, walkingLeftFrame);
		

		TextureRegion[] sleepingRightFrame = new TextureRegion[6];
		for(int i = 0; i < 6; i++)
			sleepingRightFrame[i] = atlas.findRegion("sleep" + i);
		sleepingRight = new Animation(sleepUpdateTrigger, sleepingRightFrame);
		
		TextureRegion[] sleepingLeftFrame = new TextureRegion[6];
		for(int i = 0; i < 6; i++){
			sleepingLeftFrame[i] = new TextureRegion(sleepingRightFrame[i]);
			sleepingLeftFrame[i].flip(true, false);
		}
		sleepingLeft = new Animation(sleepUpdateTrigger, sleepingLeftFrame);
		
		
		walkingRightFrame = null;
		walkingLeftFrame = null;
	}
	
	/**
	 * Get player Physic body
	 * @param wolrd  (Add player physic body to this world)
	 * @param sprite (Use to reference size of player physic body)
	 * @return
	 */
	public Body getBody(World wolrd, Sprite sprite){
		
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(Constants.pixelsToMeters(sprite.getX() + sprite.getWidth() / 2),
				Constants.pixelsToMeters(sprite.getY() + sprite.getHeight() / 2));
		Body body = wolrd.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Constants.pixelsToMeters(sprite.getWidth() / 2), Constants.pixelsToMeters(sprite.getHeight() / 2));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.filter.categoryBits = Constants.CHARACTER_CATEGORY;
		fixtureDef.filter.maskBits = ~Constants.CHARACTER_CATEGORY;
		body.createFixture(fixtureDef).setUserData("player");
		

		shape.setAsBox(Constants.pixelsToMeters(sprite.getWidth()/2 - 5), 
				Constants.pixelsToMeters(5), 
				new Vector2(0, Constants.pixelsToMeters(-(sprite.getHeight() / 2))), 0);
		FixtureDef sensorDef = new FixtureDef();
		sensorDef.isSensor = true;
		sensorDef.shape = shape;		
		body.createFixture(sensorDef).setUserData("playerSensor");
		
		body.setFixedRotation(true);
		//set name to player physic body
		shape.dispose();
		return body;
	}
	
	// set status of player character to facing left side
	public void setFacingLeft(boolean facing){
		facingLeft = facing;
	}
	
	// get status of player character that facing left side or not
	public static boolean getFacingLeft(){
		return facingLeft;
	}
	
	// set player state
	public void setState(State state){
		if(atGround)
			Player.state = state;
	}
	
	// get player state
	public State getState(){
		return state;
	}
	
	//set trigger time
	public void setTrigger(float deltaTime){
		trigger = deltaTime;
	}
	
	public boolean getAtGround(){
		return atGround;
	}
	
	public void setAtGround(boolean atGround){
		this.atGround = atGround;
	}
	
	/**
	 * update player animation
	 * @param deltaTime
	 */
	public void update(float deltaTime){
		trigger += deltaTime;
		switch(state){
			case WALKING:
				playerFrame = facingLeft ? walkingLeft.getKeyFrame(trigger, true) :  walkingRight.getKeyFrame(trigger, true);
				break;
			case JUMPING:
				if(facingLeft){
					
				}
				else{
					
				}
				break;
			case STANDING:
				if(trigger > 10.0f) // wait until trigger time 10 sec then go to sleeping animation  
					playerFrame = facingLeft ? sleepingLeft.getKeyFrame(trigger, true) : sleepingRight.getKeyFrame(trigger, true);
				else
					playerFrame = facingLeft ? standLeft : standRight;					
				break;
			case POWERUSNG:
				if(facingLeft){
					
				}
				else{
					
				}
				break;
			default:
				break;
		}		
	}
	
	public static enum State{
		STANDING, WALKING, JUMPING, POWERUSNG; 
	}
	
	public void dispose(){
		atlas.dispose();
	}


	public float getTrigger() {
		return trigger;
	}


	public static float getMovespeed() {
		return moveSpeed;
	}
}
