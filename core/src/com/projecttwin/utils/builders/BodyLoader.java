package com.projecttwin.utils.builders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.projecttwin.utils.BodyEditorLoader;

public class BodyLoader {
	private BodyEditorLoader loader;
	private String bodyName;
	private float scale;
	public BodyLoader (String filePath){
		loader = new BodyEditorLoader(Gdx.files.internal(filePath));
		
	}
	
	public void setBody(Body body, FixtureDef fixture, String bodyName, float scale){
		this.bodyName = bodyName;
		this.scale = scale;
		loader.attachFixture(body, bodyName, fixture, scale);
	}
	
	public Vector2 getOrigin(){
		return loader.getOrigin(bodyName, scale).cpy();
	}

}
