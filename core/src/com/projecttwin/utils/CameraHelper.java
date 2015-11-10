package com.projecttwin.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	public Vector2 position;
	private float zoom;
	private Sprite target;
	public final float moveSpeed = 100;
	
	public CameraHelper(){
		position = new Vector2(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2);
		zoom = 1.0f;
	}
	
	//update cameraHelper to target position
	public void update (){
		if(!hasTarget()) return;
		
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}
	
	// set update position to cameraHelper
	public void setPosition (float x, float y){
		position.set(setPositionInMapRange(x, y));
	}
	
	public Vector2 getPosition() { return position;	}
	
	//update zoom
	public void addZoom(float amount){
		setZoom(zoom + amount);
	}
	
	//set zoom in range
	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	public float getZoom() { return zoom; }
	
	//set target to follow
	public void setTarget(Sprite target){
		this.target = target;
	}
	
	// check target that camera following or not
	// get target that camera is following
	public Sprite getTarget() { return target; }
	public boolean hasTarget() { return target != null; }
	public boolean hasTarget(Sprite target) {
		return hasTarget() && this.target.equals(target);
	}
	
	// apply  position and zoom to update camera; 
	public void applyTo (OrthographicCamera camera){
		camera.position.set(setPositionInMapRange(position), 0);
		camera.zoom = zoom;
		camera.update();
	}
	
	// set input x, y in range of map width and height
	private Vector2 setPositionInMapRange(Vector2 inputVector){
		float x = MathUtils.clamp(inputVector.x, Constants.VIEWPORT_WIDTH / 2, Constants.MAP_WIDTH - Constants.VIEWPORT_WIDTH / 2);
		float y = MathUtils.clamp(inputVector.y, Constants.VIEWPORT_HEIGHT / 2, Constants.MAP_HEIGHT - Constants.VIEWPORT_HEIGHT / 2);
		return new Vector2(x, y);
	}
	
	// set input x, y in range of map width and height 
	private Vector2 setPositionInMapRange(float inputX, float inputY){
		float x = MathUtils.clamp(inputX, Constants.VIEWPORT_WIDTH / 2, Constants.MAP_WIDTH - Constants.VIEWPORT_WIDTH / 2);
		float y = MathUtils.clamp(inputY, Constants.VIEWPORT_HEIGHT / 2, Constants.MAP_HEIGHT - Constants.VIEWPORT_HEIGHT / 2);
		return new Vector2(x, y);
	}
}
