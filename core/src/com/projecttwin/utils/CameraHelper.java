package com.projecttwin.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	public Vector2 position;
	
	public CameraHelper(){
		position = new Vector2(0, 0);
	}
	public void setPosition(int x, int y){
		position.set(x, y);
	}
	
	public Vector2 getPostition(){
		return position;		
	}
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.update();
	}

}
