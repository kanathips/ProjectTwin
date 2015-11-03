package com.projecttwin.handeller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.game.WorldController;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.Pair;
import com.badlogic.gdx.physics.box2d.Body;

/***
 * This Class is Class to handle a Contact event form world in box2d library
 * <p><b>Thing this class do :</b>
 * 		<p>Management the player state
 * 		<p>Something
 * 		<p>Something
 *  
 * @author NewWy
 * @since  20-10-2558
 */

public class ContactHandler extends WorldController implements ContactListener{

	private String TAG = "ContactHandler Class";

	
	/**
	 *  Initial
	 */
	public ContactHandler() {
		WorldPhysic.world.setContactListener(this);
	}
	
	/**
	 * This method use to manage when objects in box2d world are begin to contact to each other
	 * <p><b>Thing this method do :</b>
	 * 		<p>Check player is on ground or not ?, If it is change state to standing
	 * 		
	 * @param contact is contact event sent form contact handler
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
		Pair<Boolean, Body> focusTarget;
		Body focusBody;
		try{
			/*Check an player at the ground event and change state to STANDING*/
			if(checkAlotThing("playerSensor", Constants.floor, objectA, objectB)){
				getPlayer().setAtGround(true);
				Player.setState(State.STANDING);
			}
			
			if(checkAlotThing("player", Constants.floor, objectA, objectB)){
				Constants.hitWall  = true;
			}
			
			//check that player or object on spring or not
			focusTarget = checkAlotThing("spring", Constants.jumpable, objectA, objectB, true);
			if(focusTarget.getFirst()){
				focusBody = focusTarget.getSecond();
				if(focusBody.equals(WorldPhysic.playerBody)){
					getPlayer().setAtGround(true);
					getPlayer();
					Player.setState(State.STANDING);
					CharacterControll.jump(Player.getMovespeed()*2);
				}
				else
					focusBody.setLinearVelocity(new Vector2(focusBody.getLinearVelocity().x, Player.getMovespeed()*4));
			}
			
//			//check power area is contact on object or not
			focusTarget = checkAlotThing("power", Constants.forceable, objectA, objectB, true);
			if(focusTarget.getFirst()){
					focusBody = focusTarget.getSecond();
					focusBody.setLinearVelocity(new Vector2(0,0));
					focusBody.setGravityScale(0);
					focusBody.setAngularVelocity(0);
			}			
		}catch(NullPointerException e){
			Gdx.app.debug("#" + Constants.no + " " + TAG, objectA.getUserData() + " " + objectB.getUserData() + " Contact Error (Begin)");
			Constants.no++;
		}
	}

	

	/**
	 * This method use to manage when objects in box2d world are end contact to each other
	 * <p><b>Thing this method do :</b>
	 * 		<p>Check player is beginning to float or not ?, If it is change state to jumping
	 * 	
	 * @param contact is contact event sent form contact handler
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
		Pair<Boolean, Body> focusTarget;
		Body focusBody;
		try{
			
			if(checkAlotThing("player", Constants.floor, objectA, objectB)){
				Constants.hitWall  = false;
			}
			
			if(checkAlotThing("playerSensor", Constants.floor, objectA, objectB)){
				getPlayer().setAtGround(false);
				Player.setState(State.JUMPING);
			}
			
			focusTarget = checkAlotThing("spring", Constants.jumpable, objectA, objectB, true);
			focusBody = focusTarget.getSecond();
			if(focusTarget.getFirst()){
				if(focusBody.equals(WorldPhysic.playerBody)){
					getPlayer().setAtGround(false);
					Player.setState(State.JUMPING);
					CharacterControll.jump(Player.getMovespeed()*2);
					Player.resetTrigger();
				}
				else
					focusBody.setLinearVelocity(new Vector2(focusBody.getLinearVelocity().x, Player.getMovespeed()*4));
			}
			
			focusTarget = checkAlotThing("power", Constants.forceable, objectA, objectB, true);
			focusBody = focusTarget.getSecond();
			if(focusTarget.getFirst()){
					focusBody.setGravityScale(1);
					focusBody.setAngularVelocity(1);
			}
		}catch(NullPointerException e){
			Gdx.app.debug("#" + Constants.no + " " + TAG, objectA.getUserData() + " " + objectB.getUserData() + " Contact Error (end)");
			Constants.no++;
		}
	}

	
	@SuppressWarnings("unchecked")
	public Pair<Boolean, Body> checkAlotThing(String target, String[] other, Fixture objectA, Fixture objectB, boolean focusB){
		boolean found = false;
		Body body = null;
		if(objectA.getUserData().equals(target)){
			for(String s: other){
				if((objectB.getUserData() instanceof Pair && ((Pair<String, String>) objectB.getUserData()).getFirst().equals(s)) 
						|| objectB.getUserData().equals(s)){
					found = true;
					body = objectB.getBody();
				}
			}
		}else if(objectB.getUserData().equals(target)){
			for(String s: other){
				if((objectA.getUserData() instanceof Pair && ((Pair<String, String>) objectA.getUserData()).getFirst().equals(s)) 
						|| objectA.getUserData().equals(s)){
					found = true;
					body = objectA.getBody();
				}
			}
		}
		return new Pair<Boolean, Body>(found, body);		
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkAlotThing(String target, String[] other, Fixture objectA, Fixture objectB) {
		boolean found = false;
		if(objectA.getUserData().equals(target)){
			for(String s: other){
				if((objectB.getUserData() instanceof Pair && ((Pair<String, String>) objectB.getUserData()).getFirst().equals(s)) 
						|| objectB.getUserData().equals(s)){
					found = true;
				}
			}
		}
		else if(objectB.getUserData().equals(target)){
			for(String s: other){
				if((objectA.getUserData() instanceof Pair && ((Pair<String, String>) objectA.getUserData()).getFirst().equals(s)) 
						|| objectA.getUserData().equals(s)){
					found = true;
				}
			}
		}
		return found;
	}
	
	public boolean checkOneThing(String targetA, String targetB, Object objectA, Object objectB){
		if((objectA.equals(targetA) && objectB.equals(targetB))|| (objectA.equals(targetB) && objectB.equals(targetA))){
			return true;
		}
		return false;
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}
