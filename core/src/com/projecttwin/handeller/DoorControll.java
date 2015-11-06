package com.projecttwin.handeller;

import java.util.TreeMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.Pair;

public class DoorControll {
	private TreeMap<String, Boolean> command;
	private Array<Body> gateBodies;
	private Array<Body> buttonBodies;
	public DoorControll(WorldPhysic worldPhysic){
		command = new TreeMap<String, Boolean>();
		gateBodies = worldPhysic.gateButtonBody.getFirst();
		buttonBodies = worldPhysic.gateButtonBody.getSecond();
	}
		
	
	@SuppressWarnings("unchecked")
	public void checkDoor(){
		String link = null;
		
		for(Contact c : WorldPhysic.world.getContactList()){
			Fixture fixtureA = c.getFixtureA();
			Fixture fixtureB = c.getFixtureB();
			boolean trigger = false;
			if ((fixtureA.getUserData() instanceof Pair) && (((Pair<String, String>) fixtureA.getUserData()).getFirst().equals("button"))){
				link  = ((Pair<String, String>) fixtureA.getUserData()).getSecond();
				for(String s: Constants.jumpable){
					if(fixtureB.getUserData().equals(s) && !s.equals("floor")){
						trigger = true;	
						break;
					}
					else{
						trigger = false;
					}		
				}
				command.put(link, trigger);				
			}
			if ((fixtureB.getUserData() instanceof Pair) && (((Pair<String, String>) fixtureB.getUserData()).getFirst().equals("button"))){
				link  = ((Pair<String, String>) fixtureB.getUserData()).getSecond();
				for(String s: Constants.jumpable){
					if(fixtureA.getUserData().equals(s) && !s.equals("floor")){
						trigger = true;
						break;
					}
					else{
						trigger = false;
					}
				}
				command.put(link, trigger);
			}
		}
		for(String i: command.keySet()){
			if(command.get(i)){
				open(i);
			}
			else{
				close(i);
			}
		}
		for(Body b : buttonBodies){
			command.put(((Pair<String, String>) b.getUserData()).getSecond(), false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void open(String link){
		for(Body b: gateBodies){
			if(((Pair<String, String>)b.getUserData()).getSecond().equals(link)){
				b.setActive(true);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void close(String link){
		for(Body b: gateBodies){
			if(((Pair<String, String>)b.getUserData()).getSecond().equals(link)){
				b.setActive(false);
			}
		}
	}
}
