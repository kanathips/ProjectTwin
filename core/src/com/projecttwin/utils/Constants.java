package com.projecttwin.utils;

import com.badlogic.gdx.Gdx;

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
	
	/**
	 * Pixel to Meter converter
	 * @param pixel that you want to convert to meter by pixel per meter scale
	 * @return <b>(pixel / ppm)</b> how much meter in box2d by ppm scale    
	 */
	public static float pixelsToMeters(float pixel){
		return pixel / ppm;
	}
	
	/**
	 * Meter to Pixel Converter
	 * @param meter that you want to convert to pixel by meter per pixel scale
	 * @return <b>(meter * ppm)</b> how much pixel in box2d by ppm scale 
	 */
	public static float metersToPixels(float meter){
		return meter * ppm;
	}
}