package com.projecttwin.gameState;

import java.util.TreeMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandlerState extends InputState implements InputProcessor{
	
	private String[] arr_press = {"UP","DOWN","ENTER"};
	public static TreeMap<String, Boolean> keyPressing = new TreeMap<String, Boolean>();
	
	protected InputHandlerState(GameStateManager gsm) {
		super(gsm);
		for(String string:arr_press){
			keyPressing.put(string, false);
		}
		updateKey();
	}
	
	public void updateKey(){
		
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
			case(Keys.UP):
				break;
			case(Keys.DOWN):
				break;
			case(Keys.ENTER):
				break;
				
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
	
	
}
