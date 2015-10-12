package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable{
	private TextureRegion standLeft;
	private TextureRegion standRight;
	
	private Animation walkingLeft;
	private Animation walkingRight;
	private Animation sleepingLeft;
	private Animation sleepingRight;
	private State state  = State.STANDING;
	public boolean facingLeft = false;
	private final float walkUpdateTrigger = 0.1f;
	private final float sleepUpdateTrigger = 0.3f;
	public static float trigger = 0;
	public TextureRegion playerFrame;
	private TextureAtlas atlas;
	public Player(TextureAtlas atlas){
		this.atlas = atlas;
		init();
	}
	
	
	// initial player character's animation
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
	
	// set status of player character to facing left side
	public void setFacingLeft(boolean facing){
		facingLeft = facing;
	}
	
	// get status of player character that facing left side or not
	public boolean getFacingLeft(){
		return facingLeft;
	}
	
	// set player state
	public void setState(State state){
		this.state = state;
	}
	
	// get player state
	public State getState(){
		return state;
	}
	
	//set trigger time
	public void setTrigger(float deltaTime){
		trigger = deltaTime;
	}
	
	//update player character animation method 
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
		}		
	}
	
	public static enum State{
		STANDING, WALKING, JUMPING, POWERUSNG; 
	}
	
	public void dispose(){
		atlas.dispose();
	}
}
