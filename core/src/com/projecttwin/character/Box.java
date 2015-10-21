package com.projecttwin.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.projecttwin.utils.Constants;

public class Box {
	public TextureRegion boxTextute;
	public static float torque = 0.0f;
	
	public Box(TextureAtlas atlas){
		boxTextute = atlas.findRegion("Box");
	}
	
	public Body[] initBox(World world, Sprite[] sprites){
		Body[] bodys = new Body[sprites.length];
		for(int i = 0; i < sprites.length; i++){
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(Constants.pixelsToMeters(sprites[i].getX() + sprites[i].getWidth() / 2),
					Constants.pixelsToMeters(sprites[i].getY() + sprites[i].getHeight() / 2));
			bodys[i] = world.createBody(bodyDef);
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(Constants.pixelsToMeters(sprites[i].getWidth() / 2), Constants.pixelsToMeters(sprites[i].getHeight() / 2));
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0;
			fixtureDef.restitution = 0.5f;
			fixtureDef.filter.categoryBits = Constants.OBJECT_CATEGORY;
			fixtureDef.filter.maskBits = -1;
			bodys[i].createFixture(fixtureDef).setUserData("box");
			
			//set name and category to physic body
    		//use in contact handler and filter
			
			shape.dispose();
		}
		return bodys;
	}
}
