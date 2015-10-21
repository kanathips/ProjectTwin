package com.projecttwin.handeller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.projecttwin.character.Player.State;
import com.projecttwin.game.WorldController;
import com.projecttwin.game.WorldPhysic;


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

	private String TAG = this.getClass().getName();
	
	
	/**
	 *  
	 */
	public ContactHandler() {
		WorldPhysic.world.setContactListener(this);
	}

	private boolean playerOnobject(Contact contact, String[] name){
		//TODO complete this method (check player contact with object that is on ground)  
		for(int i = 0; i < Math.ceil(name.length / 2); i++){
			
		}
		
		return true;
	}
	
	/**
	 * This method use to manage when objects in box2d world are begin to contact to each other
	 * <p><b>Thing this method do :</b>
	 * 		<p>Check player is on ground or not ?, If it is change state to standing
	 * 		<p>Check player is on ladder or not ?, If it is change state to climbing
	 * @param contact is contact event sent form contact handler
	 */
	@Override
	public void beginContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
		try{
			/*Check an player at the ground event and change state to STANDING*/
			if((objectA.getUserData().equals("playerSensor") || objectB.getUserData().equals("playerSensor")) 
					&& objectA.getUserData().equals("floor") || objectB.getUserData().equals("floor")){
				getPlayer().setAtGround(true);
				getPlayer().setState(State.STANDING);
				Gdx.app.debug(TAG, "Player Change State to " + getPlayer().getState());
			}
			/*Check when player at on ladder or stair the change state to CLAMBING*/
			else if((objectA.getUserData().equals("playerSensor") || objectB.getUserData().equals("playerSensor")) 
					&& (objectA.getUserData().equals("stair") || objectB.getUserData().equals("stair"))){ 
				getPlayer().setState(State.CLIMBING);
				getPlayer().setAtLadder(true);
				Gdx.app.debug(TAG, "Player at Stair");
			}
		}catch(NullPointerException e){
			Gdx.app.debug(TAG, objectA.getClass().toString() + " " + objectB.getClass().toString() + " Contact Error (Begin)");
		}
	}

	/**
	 * This method use to manage when objects in box2d world are end contact to each other
	 * <p><b>Thing this method do :</b>
	 * 		<p>Check player is beginning to float or not ?, If it is change state to jumping
	 * 		<p>Check player is touching the ground when finish climbing a ladder or not ?, If it is change state to standing
	 * @param contact is contact event sent form contact handler
	 */
	@Override
	public void endContact(Contact contact) {
		Fixture objectA = contact.getFixtureA();
		Fixture objectB = contact.getFixtureB();
		try{
			if((objectA.getUserData().equals("playerSensor") || objectB.getUserData().equals("playerSensor")) 
					&& (objectA.getUserData().equals("floor") || objectB.getUserData().equals("floor"))){
				getPlayer().setAtGround(false);
				getPlayer().setState(State.JUMPING);
			}
			else if((objectA.getUserData().equals("playerSensor") || objectB.getUserData().equals("playerSensor")) 
					&& (objectA.getUserData().equals("stair") || objectB.getUserData().equals("stair"))) 
				getPlayer().setAtLadder(false);
				getPlayer().setState(State.STANDING);
			
		}catch(NullPointerException e){
			System.out.println(objectA.getClass() + " " + objectB.getClass() + " Contact Error (END)");
		}		
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
