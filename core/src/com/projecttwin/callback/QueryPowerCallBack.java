package com.projecttwin.callback;

import java.util.ArrayList;

import com.projecttwin.utils.Constants;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class QueryPowerCallBack implements QueryCallback {
	public ArrayList<Fixture> fixtureList;
	private boolean haveObject;
	private boolean havePowerfield;
	private Body object;
	
	public QueryPowerCallBack() {
		fixtureList = new ArrayList<Fixture>();
	}
	
	/**
	 * when QueryAABB found an object call this method
	 * @param fixture  
	 */
	@Override
	public boolean reportFixture(Fixture fixture) {
		fixtureList.add(fixture);
		return true;
	}
	
	/**
	 * 
	 * @return true when the object that you click is in a power field
	 */
	public boolean inPowerfield(){
		for(Fixture f: fixtureList){
			for(String s: Constants.forceable)
				if(f.getUserData().equals(s)){
					haveObject = true;
					object = f.getBody();
				}
			if(f.getUserData().equals("power"))
				havePowerfield = true;
		}
		return haveObject && havePowerfield;
	}
	
	public Body getBody(){
		return object;
	}
	
	public void destroy(){
		fixtureList.clear();
		haveObject = false;
		havePowerfield = false;
	}
}