package com.projecttwin.handeller;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Player;
import com.projecttwin.utils.Constants;

public class InputHandler implements InputProcessor,Disposable{
	
	private String[] keyList = {"A", "S", "D", "W", "SHIFT_LEFT", "SHIFT_RIGHT", "UP", "DOWN", "LEFT", "RIGHT", "R"};
	private static TreeMap<String, Boolean> keyPressing = new TreeMap<String, Boolean>();

	public InputHandler() {
		Gdx.input.setInputProcessor(this);
		for(String s: keyList)
			keyPressing.put(s, false);
	}
	
//	public void update(){
//		updateKey();
//		
//	}
	
	public void updateKey(){
		if(keyPressing.get("UP")){
			System.out.println(CharacterControll.getPlayerState());
		}
		if(keyPressing.get("W")){
			CharacterControll.jump(Player.getMovespeed());
			
		}			
		if(keyPressing.get("A") && keyPressing.get("D")){
			CharacterControll.walk(0);
		}
		else if(keyPressing.get("A")){
			CharacterControll.setPlayerFacingLeft(true);
			CharacterControll.walk(-Player.getMovespeed());
		}
		else if(keyPressing.get("D")){
			CharacterControll.setPlayerFacingLeft(false);
			CharacterControll.walk(Player.getMovespeed());
		}
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		System.out.println(keycode);
		switch(keycode){
			case(Keys.ESCAPE):
				Gdx.app.exit(); 
				break;
			case(Keys.R):
				keyPressing.put("R", true);
				break;
			case(Keys.SHIFT_LEFT):
				keyPressing.put("SHIFT_LEFT", true);
				break;
			case(Keys.SHIFT_RIGHT):
				keyPressing.put("SHIFT_RIGHT", true);
				break;
			case(Keys.UP):
				keyPressing.put("UP", true);
				break;
			case(Keys.DOWN):
				keyPressing.put("DOWN", true);
				break;
			case(Keys.LEFT):
				keyPressing.put("LEFT", true);
				break;
			case(Keys.RIGHT): 
				keyPressing.put("RIGHT", true);
				break;
			case(Keys.A):
				keyPressing.put("A", true);
				System.out.println(keyPressing);
				break;
			case(Keys.S): 
				keyPressing.put("S", true);
				System.out.println(keyPressing);
				break;
			case(Keys.D): 
				keyPressing.put("D", true);
				System.out.println(keyPressing);
				break;
			case(Keys.W):
				keyPressing.put("W", true);
				System.out.println(keyPressing);
				break;
		}
		return false;		
	}
		
	@Override
	public boolean keyUp(int keycode){
		switch(keycode){
			case(Keys.SHIFT_LEFT):
				keyPressing.put("SHIFT_LEFT", false);
				break;
			case(Keys.SHIFT_RIGHT):
				keyPressing.put("SHIFT_RIGHT", false);
				break;
			case(Keys.UP):
				keyPressing.put("UP", false);
				break;
			case(Keys.DOWN):
				keyPressing.put("DOWN", false);
				break;
			case(Keys.LEFT):
				keyPressing.put("LEFT", false);
				break;
			case(Keys.RIGHT):
				keyPressing.put("RIGHT", false);
				break;
			case(Keys.A):
				CharacterControll.resetplayer(true);
				keyPressing.put("A", false);
				break;
			case(Keys.S):
				CharacterControll.resetplayer(false);
				keyPressing.put("S", false);
				break;
			case(Keys.D):
				CharacterControll.resetplayer(true);
				keyPressing.put("D", false);
				break;
			case(Keys.W):
				CharacterControll.resetplayer(false);
				keyPressing.put("W", false);
				break;
			case(Keys.R):
				keyPressing.put("R", false);
				break;
		}
		return false;
	}
		
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Constants.isClicking = true;
		Constants.clickX = screenX;
        Constants.clickY = screenY;
        return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Constants.isClicking = false;
		Constants.clickX = screenX;
        Constants.clickY = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Constants.isClicking = true;
		Constants.clickX = screenX;
        Constants.clickY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}