package com.projecttwin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

/**
 * This class use to declare Constants data of the project
 * 
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
	public static Vector3 clickPosition;
	public static boolean isClicking;
	public static boolean clickedLeft;
	public static boolean clickedRight;
	public static boolean hitWall;
	public static boolean power;

	public static String[] floor;
	public static String[] jumpable;
	public static String[] forceable;
	public static int no;
	public static int button;
	public static Body bodyInPower;
	public static boolean haveObjectinPower;
	public static int powerType;
	public static boolean gameOver;
	public static String[] dead;
<<<<<<< HEAD
	public static Array<Body> collectedStarBody;
	public static int collectedStarNumber;
	public static Array<Body> finishTarget;
	public static int target;
	public static boolean gameFinished;
=======
>>>>>>> origin/master

	/**
	 * Pixel to Meter converter
	 * 
	 * @param pixel
	 *            that you want to convert to meter by pixel per meter scale
	 * @return <b>(pixel / ppm)</b> how much meter in box2d by ppm scale
	 */
	public static float pixelsToMeters(float pixel) {
		return pixel / ppm;
	}

	public static Vector2 pixelsToMeters(Vector2 position) {
		float x = pixelsToMeters(position.x);
		float y = pixelsToMeters(position.y);
		return new Vector2(x, y);
	}

	/**
	 * Meter to Pixel Converter
	 * 
	 * @param meter
	 *            that you want to convert to pixel by meter per pixel scale
	 * @return <b>(meter * ppm)</b> how much pixel in box2d by ppm scale
	 */
	public static float metersToPixels(float meter) {
		return meter * ppm;
	}

	public static Vector2 metersToPixels(Vector2 position) {
		float x = metersToPixels(position.x);
		float y = metersToPixels(position.y);
		return new Vector2(x, y);
	}

	public static double getAngle(Vector2 centerPt, Vector2 targetPt) {
		double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
		double angle = Math.toDegrees(theta);
		if (angle < 0) {
			angle += 360;
		}
<<<<<<< HEAD
=======

>>>>>>> origin/master
		return angle;
	}

	public static void setting() {
		clickX = 0.0f;
		clickY = 0.0f;
		clickPosition = new Vector3(0, 0, 0);
		isClicking = false;
		clickedLeft = false;
		clickedRight = false;
		power = false;
		gameOver = false;
<<<<<<< HEAD
		floor = new String[] { "floor", "box", "button", "gate", "ball" };
		jumpable = new String[] { "playerSensor", "box", "ball" };
		forceable = new String[] { "box", "ball" };
		dead = new String[] { "acid", "sprike" };
		no = 0;
		gameFinished = false;
		target = 0;
		button = 0;
		collectedStarBody = new Array<Body>();
		collectedStarNumber = 0;
		finishTarget = new Array<Body>();
=======
		floor = new String[] { "floor", "box", "button", "gate" };
		jumpable = new String[] { "playerSensor", "box" };
		forceable = new String[] { "box" };
		dead = new String[] { "acid", "sprike" };
		no = 0;
		button = 0;
>>>>>>> origin/master
		haveObjectinPower = false;
		powerType = 1;
	}
}
