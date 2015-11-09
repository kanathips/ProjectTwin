package com.projecttwin.gameState;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class GameCore implements ApplicationListener {
	
	public static final String TITLE = "Twin";
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 768;
	public static final float STEP = 1 / 60f;
	private float deltaTime_state;
	private float deltaTime_mouse;
	private boolean run_def=false;
	private Vector2 location_cursor = new Vector2(0, 0);
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;
	private TextureAtlas atlas_mouse;
	private TextureAtlas atlas_def;
	private Animation mouse;
	private Animation mouse_def;
	
	private GameStateManager gsm;
	static org.lwjgl.input.Cursor emptyCursor;
	
	public void create() {
		
		atlas_def = new TextureAtlas("cursor/mouse_def.atlas");
		atlas_mouse = new TextureAtlas("cursor/cursor_sprite.atlas");
		TextureRegion[] mouseFrame = new TextureRegion[10];
		for(int i=1;i<=10;i++)	mouseFrame[i-1] = atlas_mouse.findRegion("cursor ("+i+")");
		mouse = new Animation(1.5f, mouseFrame);
		
		TextureRegion[] defFrame = new TextureRegion[20];
		for(int i=0;i<=19;i++)	defFrame[i] = atlas_def.findRegion("def_"+String.format("%02d", (i+1)));
		mouse_def = new Animation(0.03f, defFrame);
		
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		gsm.pushState(new StartGame(gsm,true));
		
	}
	
	public void render() {
		try {
			setHWCursorVisible(false);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		location_cursor.x = Gdx.input.getX();
		location_cursor.y = Gdx.input.getY();
		deltaTime_state += Gdx.graphics.getDeltaTime();
		deltaTime_mouse += Gdx.graphics.getDeltaTime();
		
		while(deltaTime_state >= STEP) {
			deltaTime_state -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
		
		spriteBatch.begin();
		spriteBatch.draw(mouse.getKeyFrame(deltaTime_mouse,true), location_cursor.x-15, Gdx.graphics.getHeight()-location_cursor.y-55, 60, 60);
		
		if(Gdx.input.justTouched()){	run_def = true;		deltaTime_mouse = 0f; 	}
		
		if(run_def)
			spriteBatch.draw(mouse_def.getKeyFrame(deltaTime_mouse,true), location_cursor.x-45, Gdx.graphics.getHeight()-location_cursor.y-95, 120, 120);
		
			
		if(mouse_def.isAnimationFinished(deltaTime_mouse))	run_def = false;
			
		spriteBatch.end();
		
	}
	
	public void dispose() {
		spriteBatch.dispose();
	}
	public Vector2 getLocationCursor(){	return location_cursor;	}
	public SpriteBatch getSpriteBatch() { return spriteBatch; }
	public OrthographicCamera getCamera() { return camera; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	
	public static void setHWCursorVisible(boolean visible) throws LWJGLException {
		if (Gdx.app.getType() != ApplicationType.Desktop && Gdx.app instanceof LwjglApplication)
			return;
		if (emptyCursor == null) {
			if (Mouse.isCreated()) {
				int min = org.lwjgl.input.Cursor.getMinCursorSize();
				IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
				emptyCursor = new org.lwjgl.input.Cursor(min, min, min / 2, min / 2, 1, tmp, null);
			} else {
				throw new LWJGLException(
						"Could not create empty cursor before Mouse object is created");
			}
		}
		if (Mouse.isInsideWindow())
			Mouse.setNativeCursor(visible ? null : emptyCursor);
	}
	
}
