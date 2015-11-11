package com.projecttwin.handeller;

import java.util.HashMap;
import java.util.TreeMap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.game.WorldPhysic;

public class DoorControll {
	private HashMap<Body, Integer> command;
	private Array<Body> gateBodies;
	private Array<Body> buttonBodies;
	private boolean doNotDo;
	@SuppressWarnings("unchecked")
	public DoorControll(WorldPhysic worldPhysic) {
		command = new HashMap<Body, Integer>();
		doNotDo = false;
		try{
		gateBodies = worldPhysic.gateButtonBody.getFirst();
		buttonBodies = worldPhysic.gateButtonBody.getSecond();
		for (Body b : buttonBodies) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
			command.put(b, Integer.parseInt(data.get("status")) * Integer.parseInt(data.get("condition")));
		}
		}catch(NullPointerException e){
			doNotDo = true;
		}
	}

	@SuppressWarnings("unchecked")
	public void checkDoor() {
		if(doNotDo)
			return;
		Body button = null;
		// Trigger = -1 mean button is not press
		// Trigger = 1 mean button is press
		for (Contact c : WorldPhysic.world.getContactList()) {
			Fixture fixtureA = c.getFixtureA();
			Fixture fixtureB = c.getFixtureB();
			TreeMap<String, String> userDataA = (TreeMap<String, String>) fixtureA.getUserData();
			TreeMap<String, String> userDataB = (TreeMap<String, String>) fixtureB.getUserData();
			Integer trigger = -1;
			if (!c.isTouching())
				continue;
			if (userDataB.get("name").equals("button")) {
				button = fixtureB.getBody();
				if (!userDataA.get("name").equals("floor") && !userDataA.get("name").equals("power")) {
					trigger = -1 * Integer.parseInt(userDataB.get("condition"));
				} else {
					trigger = 1 * Integer.parseInt(userDataB.get("condition"));
				}
				command.put(button, trigger);

			}
		}
		// == -1 mean button is press and button action is do the thing when
		// press / status = -1
		for (Body i : command.keySet()) {
			String linkData = ((TreeMap<String, String>) i.getUserData()).get("link");
			((TreeMap<String, String>) i.getUserData()).replace("status", command.get(i).toString());
			if (command.get(i) == -1) {
				open(linkData);
			} else {
				close(linkData);
			}
		}
		for (Body b : buttonBodies) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
			int condition = Integer.parseInt(data.get("condition"));
			command.put(b, 1 * condition);
		}
	}

	@SuppressWarnings("unchecked")
	public void open(String link) {
		TreeMap<String, String> data;
		for (Body b : gateBodies) {
			data = (TreeMap<String, String>) b.getUserData();
			if (data.get("link").equals(link)) {
				b.setActive(true);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void close(String link) {
		TreeMap<String, String> data;
		for (Body b : gateBodies) {
			data = (TreeMap<String, String>) b.getUserData();
			if (data.get("link").equals(link)) {
				b.setActive(false);
			}
		}
	}
}
