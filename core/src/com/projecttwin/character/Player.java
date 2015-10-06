package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
	private TextureRegion standLeft;
	private TextureRegion standRight;
	private Animation walkingLeft;
	private Animation walkingRight;
	private State state  = State.STANDING;
	public boolean facingLeft = false;
	private final float updateTrigger = 0.042f;
	private static int keyFrameIndex = 0;
	private static float trigger = 0;
	public TextureRegion playerFrame;
	private TextureAtlas atlas;
	
	public Player(TextureAtlas atlas){
		this.atlas = atlas;
		init();
	}
	
	public void init(){
		standRight = atlas.findRegion("player01");
		standLeft = new TextureRegion(standRight);
		standLeft.flip(true, false);
		playerFrame = standRight;
		
		TextureRegion[] walkingLeftFrame = new TextureRegion[9];
		for(int i = 1; i < 9 ; i++){
			walkingLeftFrame[i] = atlas.findRegion("player0" + i);
			walkingLeftFrame[i].flip(true, false);
		}
		
		walkingLeft = new Animation(updateTrigger, walkingLeftFrame);
		walkingLeftFrame = null;
		
		TextureRegion[] walkingRightFrame = new TextureRegion[9];
		for(int i = 1; i < 9; i++)
			walkingRightFrame[i] = atlas.findRegion("player0" + i);
		
		walkingRight = new Animation(updateTrigger, walkingRightFrame);
		walkingRightFrame = null;
	}
	public void setFacingLeft(boolean facing){
		facingLeft = facing;
	}
	
	public boolean getFacingLeft(){
		return facingLeft;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	public void update(float deltaTime){
		trigger += deltaTime;
		switch(state){
			case WALKING:
				playerFrame = facingLeft ? walkingLeft.getKeyFrame(trigger, true) : walkingRight.getKeyFrame(trigger, true); 
				break;
			case JUMPING:
				if(facingLeft){
					
				}
				else{
					
				}
				break;
			case STANDING:
				playerFrame = facingLeft ? standLeft : standRight;
				break;
			default:
				break;
		}		
	}
	
	public static enum State{
		STANDING, WALKING, JUMPING; 
	}
}
