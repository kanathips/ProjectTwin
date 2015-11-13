package com.projecttwin.handeller;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.projecttwin.character.Player;
import com.projecttwin.character.PlayerForce;
import com.projecttwin.character.Player.State;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.game.WorldRender;
import com.projecttwin.utils.Constants;

/**
 * This class is Handle Input form mouse and keyboard such as key down, up,
 * press and mouse click
 * 
 * @author NewWy
 */
public class InputHandler implements InputProcessor {

	private String[] keyList = { "A", "S", "D", "W", "SHIFT_LEFT", "SHIFT_RIGHT", "UP", "DOWN", "LEFT", "RIGHT", "R",
			"F" };
	public static TreeMap<String, Boolean> keyPressing = new TreeMap<String, Boolean>();

	/**
	 * Initial InputHandler class, set InputProcessor to this class
	 */
	public InputHandler() {
		Gdx.input.setInputProcessor(this);
		for (String s : keyList)
			keyPressing.put(s, false);
	}

	/**
	 * update InputHandler and key press method
	 */
	public void update() {
		updateKey();
	}

	/**
	 * when press or stroke keyboard to this class
	 */
	// TODO find the way how to stop player movement when collide with a wall
	public void updateKey() {
		if (keyPressing.get("UP")) {
			for (Contact c : WorldPhysic.world.getContactList())
//				System.out.print("[ " + c.getFixtureA().getUserData() + " " + c.getFixtureB().getUserData() + "] ");
				System.out.println(Constants.collectedStarNumber);
			System.out.println();
		}
		if (keyPressing.get("W") && !Constants.power) {
			Player.resetTrigger();
			CharacterControll.jump(Player.getMovespeed());
		}
		if (keyPressing.get("A") && keyPressing.get("D") && !Constants.power) {
			CharacterControll.walk(0);
		} else if (keyPressing.get("A") && !Constants.power) {
			CharacterControll.setPlayerFacingLeft(true);
			CharacterControll.walk(-Player.getMovespeed());
		} else if (keyPressing.get("D") && !Constants.power) {
			CharacterControll.setPlayerFacingLeft(false);
			CharacterControll.walk(Player.getMovespeed());
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case (Keys.ESCAPE):
			Gdx.app.exit();
			break;
		case (Keys.R):
			keyPressing.put("R", true);
			Constants.gameOver = true;
			break;
		case (Keys.SHIFT_LEFT):
			keyPressing.put("SHIFT_LEFT", true);
			break;
		case (Keys.SHIFT_RIGHT):
			keyPressing.put("SHIFT_RIGHT", true);
			break;
		case (Keys.UP):
			keyPressing.put("UP", true);
			break;
		case (Keys.DOWN):
			keyPressing.put("DOWN", true);
			break;
		case (Keys.LEFT):
			keyPressing.put("LEFT", true);
			break;
		case (Keys.RIGHT):
			keyPressing.put("RIGHT", true);
			break;
		case (Keys.A):
			keyPressing.put("A", true);
			break;
		case (Keys.S):
			keyPressing.put("S", true);
			break;
		case (Keys.D):
			keyPressing.put("D", true);
			break;
		case (Keys.W):
			keyPressing.put("W", true);
			break;
		case (Keys.F):
			if (!Constants.power)
				Constants.powerType = Constants.powerType == 1 ? 0 : 1;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case (Keys.SHIFT_LEFT):
			keyPressing.put("SHIFT_LEFT", false);
			break;
		case (Keys.SHIFT_RIGHT):
			keyPressing.put("SHIFT_RIGHT", false);
			break;
		case (Keys.UP):
			keyPressing.put("UP", false);
			break;
		case (Keys.DOWN):
			keyPressing.put("DOWN", false);
			break;
		case (Keys.LEFT):
			keyPressing.put("LEFT", false);
			break;
		case (Keys.RIGHT):
			keyPressing.put("RIGHT", false);
			break;
		case (Keys.A):
			if (CharacterControll.getPlayerState() == State.WALKING)
				CharacterControll.resetplayer(true);
			keyPressing.put("A", false);
			break;
		case (Keys.S):
			if (CharacterControll.getPlayerState() == State.WALKING)
				CharacterControll.resetplayer(false);
			keyPressing.put("S", false);
			break;
		case (Keys.D):
			if (CharacterControll.getPlayerState() == State.WALKING)
				CharacterControll.resetplayer(true);
			keyPressing.put("D", false);
			break;
		case (Keys.W):
			if (CharacterControll.getPlayerState() == State.WALKING)
				CharacterControll.resetplayer(false);
			keyPressing.put("W", false);
			break;
		case (Keys.R):
			keyPressing.put("R", false);
			break;
		case (Keys.F):
			keyPressing.put("F", false);
			break;
		}
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Constants.isClicking = true;
		Constants.button = button;
		if (button == 0 && !Constants.power) {
			Constants.clickedLeft = true;
			Constants.power = true;
		}
		if (button == 1)
			Constants.clickedRight = true;

		if (Constants.button == 0 && Constants.power && Constants.isClicking && Constants.haveObjectinPower) {
			PlayerForce.applyPowerToObject(Constants.bodyInPower, new Vector2(Constants.clickX, Constants.clickY),
					Constants.powerType);
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Constants.isClicking = false;
		Constants.button = button;
		if (button == 0)
			Constants.clickedLeft = false;
		if (button == 1) {
			Constants.clickedRight = false;
			Constants.power = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Constants.clickPosition.set(screenX, screenY, 0);
		WorldRender.camera.unproject(Constants.clickPosition);
		Constants.clickX = Constants.clickPosition.x;
		Constants.clickY = Constants.clickPosition.y;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}