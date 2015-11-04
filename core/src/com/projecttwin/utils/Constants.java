package com.projecttwin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * This class use to declare Constants data of the project
 * @author NewWy
 * @category utility
 */

public class Constants {
	public static final float ppm = 100f;
	public static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
	public static final String TEXTURE_ATLAS_OBJECTS = "images/imagesPack.pack";
	public static float MAP_WIDTH = 2500;
	public static float MAP_HEIGHT = 800;
	
	public static final short CHARACTER_CATEGORY = 1;
	public static final short MAP_CATEGORY = 4;
	public static final short STAIR_CATEGORY = 0;
	public static final short OBJECT_CATEGORY = 2;
	public static float clickX;
	public static float clickY;
	public static Vector3 clickPosition = new Vector3(0,0, 0);
	public static boolean isClicking;
	public static boolean clickedLeft = false;
	public static boolean clickedRight = false;
	public static boolean hitWall;
	public static boolean power = false;
	
	public static String[] floor = {"floor", "box", "button", "gate"};
	public static String[] jumpable = {"playerSensor", "box"};
	public static String[] forceable = {"box"};
	public static int no = 0;
	public static int button = 0;
	public static Body bodyInPower;
	public static boolean haveObjectinPower = false;
	public static int powerType = 1;
	
	/**
	 * Pixel to Meter converter
	 * @param pixel that you want to convert to meter by pixel per meter scale
	 * @return <b>(pixel / ppm)</b> how much meter in box2d by ppm scale    
	 */
	public static float pixelsToMeters(float pixel){
		return pixel / ppm;
	}
	
	public static Vector2 pixelsToMeters(Vector2 position){
		float x = pixelsToMeters(position.x);
		float y = pixelsToMeters(position.y);
		return new Vector2(x, y);
	}
	
	/**
	 * Meter to Pixel Converter
	 * @param meter that you want to convert to pixel by meter per pixel scale
	 * @return <b>(meter * ppm)</b> how much pixel in box2d by ppm scale 
	 */
	public static float metersToPixels(float meter){
		return meter * ppm;
	}

	public static Vector2 metersToPixels(Vector2 position) {
		float x = metersToPixels(position.x);
		float y = metersToPixels(position.y);
		return new Vector2(x, y);
	}
	
	public static double getAngle(Vector2 centerPt, Vector2 targetPt)
	{
	    double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
	    double angle = Math.toDegrees(theta);
	    if (angle < 0) {
	        angle += 360;
	    }

	    return angle;
	}
}