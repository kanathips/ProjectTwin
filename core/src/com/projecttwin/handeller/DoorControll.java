package com.projecttwin.handeller;

import java.util.TreeMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.Pair;

public class DoorControll {
	private WorldPhysic worldPhysic;
	private TreeMap<String, Boolean> command;
	public DoorControll(WorldPhysic worldPhysic){
		this.worldPhysic = worldPhysic;
		command = new TreeMap<String, Boolean>();
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
					if(fixtureB.getUserData().equals(s)){
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
					if(fixtureA.getUserData().equals(s)){
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
		for(Body b : worldPhysic.buttonBody){
			command.put(((Pair<String, String>) b.getUserData()).getSecond(), false);
		}
	}
	
	public void open(String link){

	}
	
	public void close(String link){

	}
}
