package com.projecttwin.character;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.utils.Constants;

public class Player implements Disposable {
	private TextureRegion standLeft;
	private TextureRegion standRight;

	private Animation walkingLeft;
	private Animation walkingRight;
	private Animation sleepingLeft;
	private Animation sleepingRight;
	public static State state = State.STANDING;
	private static boolean facingLeft = false;
	private final float walkUpdateTrigger = 0.1f;
	private final float sleepUpdateTrigger = 0.3f;
	private static float trigger = 0;
	public TextureRegion playerFrame;
	private TextureAtlas atlas;
	private BodyDef bodyDef;
	private int powerType = 1;
	private Animation pullingLeft;
	private float pullUpdateTrigger = 0.3f;
	private Animation pullingRight;
	private Animation pushingLeft;
	private float pushUpdateTrigger = 0.3f;
	private Animation pushingRight;
	private final static float moveSpeed = 2f;
	private static boolean atGround = true;

	public Player(TextureAtlas atlas) {
		this.atlas = atlas;
//		try {
			init();
			Gdx.app.debug(Constants.no + " Player", "Create Player Complete");
			Constants.no++;
//		} catch (Exception e) {
//			Gdx.app.debug(Constants.no + " Player", "Create Player Error");
//			Constants.no++;
//		}
	}

	/**
	 * initial player character's animation
	 */
	public void init() {
		standRight = atlas.findRegion("stand");
		standLeft = new TextureRegion(standRight);
		standLeft.flip(true, false);
		playerFrame = standRight;

		TextureRegion[] walkingRightFrame = new TextureRegion[11];
		for (int i = 0; i < walkingRightFrame.length; i++)
			walkingRightFrame[i] = atlas.findRegion("walk" + i);
		walkingRight = new Animation(walkUpdateTrigger, walkingRightFrame);
		TextureRegion[] walkingLeftFrame = new TextureRegion[11];
		for (int i = 0; i < walkingLeftFrame.length; i++) {
			walkingLeftFrame[i] = new TextureRegion(walkingRightFrame[i]);
			walkingLeftFrame[i].flip(true, false);
		}
		walkingLeft = new Animation(walkUpdateTrigger, walkingLeftFrame);

		TextureRegion[] sleepingRightFrame = new TextureRegion[6];
		for (int i = 0; i < sleepingRightFrame.length; i++)
			sleepingRightFrame[i] = atlas.findRegion("sleep" + i);
		sleepingRight = new Animation(sleepUpdateTrigger, sleepingRightFrame);

		TextureRegion[] sleepingLeftFrame = new TextureRegion[6];
		for (int i = 0; i < sleepingLeftFrame.length; i++) {
			sleepingLeftFrame[i] = new TextureRegion(sleepingRightFrame[i]);
			sleepingLeftFrame[i].flip(true, false);
		}
		sleepingLeft = new Animation(sleepUpdateTrigger, sleepingLeftFrame);

		TextureRegion[] pullingRightFrame = new TextureRegion[4];
		for (int i = 0; i < pullingRightFrame.length; i++)
			pullingRightFrame[i] = atlas.findRegion("pull" + i);
		pullingRight = new Animation(pullUpdateTrigger, pullingRightFrame);

		TextureRegion[] pullingLeftFrame = new TextureRegion[4];
		for (int i = 0; i < pullingLeftFrame.length; i++) {
			pullingLeftFrame[i] = new TextureRegion(pullingRightFrame[i]);
			pullingLeftFrame[i].flip(true, false);
		}
		pullingLeft = new Animation(pullUpdateTrigger, pullingLeftFrame);

		TextureRegion[] pushingRightFrame = new TextureRegion[4];
		for (int i = 0; i < pushingRightFrame.length; i++) {
			pushingRightFrame[i] = atlas.findRegion("push" + i);
		}
		pushingRight = new Animation(pushUpdateTrigger, pushingRightFrame);

		TextureRegion[] pushingLeftFrame = new TextureRegion[4];
		for (int i = 0; i < pushingLeftFrame.length; i++) {
			pushingLeftFrame[i] = new TextureRegion(pushingRightFrame[i]);
			pushingLeftFrame[i].flip(true, false);
		}
		pushingLeft = new Animation(pushUpdateTrigger, pushingLeftFrame);
		pullingLeftFrame = null;
		pullingRightFrame = null;
		walkingRightFrame = null;
		walkingLeftFrame = null;
	}

	/**
	 * Get player Physic body
	 * 
	 * @param wolrd
	 *            (Add player physic body to this world)
	 * @param sprite
	 *            (Use to reference size of player physic body)
	 * @return
	 */
	public Body getBody(World wolrd, Sprite sprite) {
		TreeMap<String, String> data = new TreeMap<String, String>();
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(Constants.pixelsToMeters(sprite.getX() + sprite.getWidth() / 2),
				Constants.pixelsToMeters(sprite.getY() + sprite.getHeight() / 2));
		Body body = wolrd.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Constants.pixelsToMeters(15), Constants.pixelsToMeters(35));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
<<<<<<< HEAD
=======
		fixtureDef.filter.categoryBits = Constants.CHARACTER_CATEGORY;
		fixtureDef.filter.maskBits = ~Constants.CHARACTER_CATEGORY;
>>>>>>> origin/master
		data.put("name", "player");
		body.createFixture(fixtureDef).setUserData(data);
		body.setBullet(true);

		shape.setAsBox(Constants.pixelsToMeters(10), Constants.pixelsToMeters(5),
				new Vector2(0, Constants.pixelsToMeters(-35.5f)), 0);
		FixtureDef sensorDef = new FixtureDef();
		sensorDef.isSensor = true;
		sensorDef.shape = shape;
		TreeMap<String, String> powerData = new TreeMap<String, String>();
		powerData.put("name", "playerSensor");
		body.createFixture(sensorDef).setUserData(powerData);

		body.setFixedRotation(true);
		// set name to player physic body
		shape.dispose();
		return body;
	}

	// set status of player character to facing left side
	public void setFacingLeft(boolean facing) {
		facingLeft = facing;
	}

	// get status of player character that facing left side or not
	public static boolean getFacingLeft() {
		return facingLeft;
	}

	// set player state
	public static void setState(State state) {
		if (atGround)
			Player.state = state;
	}

	// get player state
	public State getState() {
		return state;
	}

	// set trigger time
	public void setTrigger(float deltaTime) {
		trigger = deltaTime;
	}

	public boolean getAtGround() {
		return atGround;
	}

	public void setAtGround(boolean atGround) {
		Player.atGround = atGround;
	}

	/**
	 * update player animation
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		trigger += deltaTime;
		switch (state) {
		case WALKING:
			playerFrame = facingLeft ? walkingLeft.getKeyFrame(trigger, true) : walkingRight.getKeyFrame(trigger, true);
			break;
		case JUMPING:
			if (facingLeft) {

			} else {

			}
			break;
		case STANDING:
			if (trigger > 10.0f) // wait until trigger time 10 sec then go to
									// sleeping animation
				playerFrame = facingLeft ? sleepingLeft.getKeyFrame(trigger, true)
						: sleepingRight.getKeyFrame(trigger, true);
			else
				playerFrame = facingLeft ? standLeft : standRight;
			break;
		case POWERUSNG:
			if (powerType == 1)
				playerFrame = facingLeft ? pullingLeft.getKeyFrame(trigger, true)
						: pullingRight.getKeyFrame(trigger, true);
			else
				playerFrame = facingLeft ? pushingLeft.getKeyFrame(trigger, true)
						: pushingRight.getKeyFrame(trigger, true);

			break;
		default:
			break;
		}
	}

	public static enum State {
		STANDING, WALKING, JUMPING, POWERUSNG;
	}

	public void dispose() {
		atlas.dispose();
	}

	public float getTrigger() {
		return trigger;
	}

	public static void resetTrigger() {
		trigger = 0;
	}

	public static float getMovespeed() {
		return moveSpeed;
	}

	public void setPowerType(int i) {
		this.powerType = i;
	}
}
