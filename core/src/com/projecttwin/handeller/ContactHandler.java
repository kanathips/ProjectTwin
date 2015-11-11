package com.projecttwin.handeller;

import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.game.WorldController;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.Pair;

/***
 * This Class is Class to handle a Contact event form world in box2d library
 * <p>
 * <b>Thing this class do :</b>
 * <p>
 * Management the player state
 * <p>
 * Something
 * <p>
 * Something
 * 
 * @author NewWy
 * @since 20-10-2558
 */

public class ContactHandler extends WorldController implements ContactListener {

	private String TAG = "ContactHandler Class";
<<<<<<< HEAD
	public static Array<Contact> doorContact;
	int currentTarget = 0;
=======
	 public static Array<Contact> doorContact;
	// public static Array<String> doorEContact;

>>>>>>> origin/master
	/**
	 * Initial
	 */
	public ContactHandler() {
		// doorBContact = new Array<String>();
		// doorEContact = new Array<String>();
		WorldPhysic.world.setContactListener(this);
	}

	/**
	 * This method use to manage when objects in box2d world are begin to
	 * contact to each other
	 * <p>
	 * <b>Thing this method do :</b>
	 * <p>
	 * Check player is on ground or not ?, If it is change state to standing
	 * 
	 * @param contact
	 *            is contact event sent form contact handler
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
<<<<<<< HEAD
		TreeMap<String, String> dataA = (TreeMap<String, String>) objectA.getUserData();
		TreeMap<String, String> dataB = (TreeMap<String, String>) objectB.getUserData();
		Pair<Boolean, Body> focusTarget;
		Body focusBody;
		try {
			focusTarget = checkAlotThing("player", new String[] { "star" }, objectA, objectB, true);
			if (focusTarget.getFirst()) {
				Constants.collectedStarBody.add(focusTarget.getSecond());
				Constants.collectedStarNumber++;
			}
=======
		
		Pair<Boolean, Body> focusTarget;
		Body focusBody;
		try {
			/*
			 * Check an player at the ground event and change state to STANDING
			 */

>>>>>>> origin/master
			if (checkAlotThing("playerSensor", Constants.floor, objectA, objectB)) {
				getPlayer().setAtGround(true);
				Player.setState(State.STANDING);
				return;
			}
			if (checkAlotThing("playerSensor", Constants.dead, objectA, objectB)) {
				Constants.gameOver = true;
				return;
			}
<<<<<<< HEAD
			try {
				if (dataA.get("color").equals(dataB.get("color"))) {
					if (dataA.get("name").equals("ball") && dataB.get("name").equals("target")) {
						Constants.finishTarget.add(objectA.getBody());
						Constants.finishTarget.add(objectB.getBody());
						currentTarget++;
					} else if (dataA.get("name").equals("target") && dataB.get("name").equals("ball")) {
						Constants.finishTarget.add(objectA.getBody());
						Constants.finishTarget.add(objectB.getBody());
						currentTarget++;
					}
					if(currentTarget == Constants.target){
						Constants.gameFinished = true;
					}
				}
			} catch (NullPointerException e) {

=======

			if (checkAlotThing("playerSensor", Constants.dead, objectA, objectB)) {
				Constants.gameOver = true;
>>>>>>> origin/master
			}

			// check that player or object on spring or not
			focusTarget = checkAlotThing("spring", Constants.jumpable, objectA, objectB, true);
			if (focusTarget.getFirst()) {
				focusBody = focusTarget.getSecond();
				if (focusBody.equals(WorldPhysic.playerBody)) {
					getPlayer().setAtGround(true);
					getPlayer();
					Player.setState(State.STANDING);
					CharacterControll.jump(Player.getMovespeed() * 2);
				} else
					focusBody
							.setLinearVelocity(new Vector2(focusBody.getLinearVelocity().x, Player.getMovespeed() * 4));
<<<<<<< HEAD
				return;
=======
>>>>>>> origin/master
			}

			// //check power area is contact on object or not
			focusTarget = checkAlotThing("power", Constants.forceable, objectA, objectB, true);
			if (focusTarget.getFirst()) {
				focusBody = focusTarget.getSecond();
				focusBody.setLinearVelocity(new Vector2(0, 0));
				focusBody.setGravityScale(0);
				focusBody.setAngularVelocity(0);
<<<<<<< HEAD
				return;
=======
>>>>>>> origin/master
			}
		} catch (NullPointerException e) {
			Gdx.app.debug("#" + Constants.no + " " + TAG,
					objectA.getUserData() + " " + objectB.getUserData() + " Contact Error (Begin)");
			Constants.no++;
		}
	}

	/**
	 * This method use to manage when objects in box2d world are end contact to
	 * each other
	 * <p>
	 * <b>Thing this method do :</b>
	 * <p>
	 * Check player is beginning to float or not ?, If it is change state to
	 * jumping
	 * 
	 * @param contact
	 *            is contact event sent form contact handler
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
		// TreeMap<String, String> userDataA = (TreeMap<String, String>)
		// objectA.getUserData();
		// TreeMap<String, String> userDataB = (TreeMap<String, String>)
		// objectB.getUserData();
		Pair<Boolean, Body> focusTarget;
		Body focusBody;
		try {
<<<<<<< HEAD
=======
			// if (userDataA.get("name").equals("button")) {
			// for (String s : doorBContact) {
			// if (s.equals(userDataA.get("link")))
			// doorBContact.removeValue(s, false);
			// }
			// doorEContact.add(userDataA.get("link"));
			// } else if (userDataB.get("name").equals("button")) {
			// for (String s : doorEContact) {
			// if (s.equals(userDataB.get("link")))
			// doorBContact.removeValue(s, false);
			// }
			// doorEContact.add(userDataA.get("link"));
			// }
>>>>>>> origin/master
			focusTarget = checkAlotThing("spring", Constants.jumpable, objectA, objectB, true);
			focusBody = focusTarget.getSecond();
			if (focusTarget.getFirst()) {
				if (focusBody.equals(WorldPhysic.playerBody)) {
					getPlayer().setAtGround(false);
					Player.setState(State.JUMPING);
					CharacterControll.jump(Player.getMovespeed() * 2);
					Player.resetTrigger();
				} else
					focusBody
							.setLinearVelocity(new Vector2(focusBody.getLinearVelocity().x, Player.getMovespeed() * 4));
<<<<<<< HEAD
				return;
=======
>>>>>>> origin/master
			}

			focusTarget = checkAlotThing("power", Constants.forceable, objectA, objectB, true);
			focusBody = focusTarget.getSecond();
			if (focusTarget.getFirst()) {
				focusBody.setGravityScale(1);
				focusBody.setAngularVelocity(1);
<<<<<<< HEAD
				return;
=======
>>>>>>> origin/master
			}
		} catch (NullPointerException e) {
			Gdx.app.debug("#" + Constants.no + " " + TAG,
					objectA.getUserData() + " " + objectB.getUserData() + " Contact Error (end)");
			Constants.no++;
		}
	}

	@SuppressWarnings("unchecked")
	public Pair<Boolean, Body> checkAlotThing(String target, String[] other, Fixture objectA, Fixture objectB,
			boolean focusB) {
		boolean found = false;
		TreeMap<String, String> dataA = (TreeMap<String, String>) objectA.getUserData();
		TreeMap<String, String> dataB = (TreeMap<String, String>) objectB.getUserData();
		Body body = null;
		if (dataA.get("name").equals(target)) {
			for (String s : other) {
				if (dataB.get("name").equals(s)) {
					found = true;
					body = objectB.getBody();
				}
			}
		} else if (dataB.get("name").equals(target)) {
			for (String s : other) {
				if (dataA.get("name").equals(s)) {
					found = true;
					body = objectA.getBody();
				}
			}
		}
		return new Pair<Boolean, Body>(found, body);
	}

	@SuppressWarnings("unchecked")
	private boolean checkAlotThing(String target, String[] other, Fixture objectA, Fixture objectB) {
		TreeMap<String, String> dataA = (TreeMap<String, String>) objectA.getUserData();
		TreeMap<String, String> dataB = (TreeMap<String, String>) objectB.getUserData();
		boolean found = false;

		if (dataA.get("name").equals(target)) {
			for (String s : other) {
				if (dataB.get("name").equals(s)) {
					found = true;
				}
			}
		} else if (dataB.get("name").equals(target)) {
			for (String s : other) {
				if (dataA.get("name").equals(s)) {
					found = true;
				}
			}
		}
		return found;
	}

	public boolean checkOneThing(String targetA, String targetB, Object objectA, Object objectB) {
		if ((objectA.equals(targetA) && objectB.equals(targetB))
				|| (objectA.equals(targetB) && objectB.equals(targetA))) {
			return true;
		}
		return false;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
<<<<<<< HEAD
		
=======
		WorldPhysic.world.getContactList().removeValue(contact, true);
>>>>>>> origin/master
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
<<<<<<< HEAD

=======
		WorldPhysic.world.getContactList().removeValue(contact, true);
>>>>>>> origin/master
	}

}
