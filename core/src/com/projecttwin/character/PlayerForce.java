package com.projecttwin.character;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.projecttwin.utils.Constants;

public class PlayerForce {
	
	private World world;
	private Body body;
	private float radius;
	private final float min_radius  = 5;
	private final float max_radius = 300;
	private final float increate_rate = 1;
	public PlayerForce(World world){
		this.world = world;
	}
	
	public Body getPlayerForce(float x, float y){
		radius = min_radius;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		body = world.createBody(bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(Constants.pixelsToMeters(radius));
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("power");
		shape.dispose();
		return body;
	}
	
	public Body getPlayerForce(Vector2 position){
		return getPlayerForce(position.x, position.y);
	}
	
	public void update(float deltaTime, float x, float y){
		body.setActive(true);
		CircleShape shape = new CircleShape();
		radius += Constants.pixelsToMeters(increate_rate);
		radius = MathUtils.clamp(radius , Constants.pixelsToMeters(min_radius), Constants.pixelsToMeters(max_radius));
		shape.setRadius(radius);
		
		body.destroyFixture(body.getFixtureList().first());
		body.setTransform(x,  y, 0);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("power");
		shape.dispose();
	}
	
	public void update(float deltaTime, Vector2 position){
		update(deltaTime, position.x, position.y);
	}
	
	public void destroy(){
		CircleShape shape = new CircleShape();
		radius = Constants.pixelsToMeters(min_radius);
		shape.setRadius(radius);
		body.setActive(false);
		body.destroyFixture(body.getFixtureList().first());
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("power");
		shape.dispose();
	}

	public float getRadius() {
		return radius;
	}
	
}
