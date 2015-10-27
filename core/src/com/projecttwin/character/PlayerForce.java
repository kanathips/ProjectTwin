package com.projecttwin.character;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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
		radius = Constants.pixelsToMeters(min_radius);
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		body.setActive(false);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("power");
		shape.dispose();
		return body;
	}
	
	public void update(){
		body.setActive(true);
		CircleShape shape = new CircleShape();
		radius += Constants.pixelsToMeters(increate_rate);
		radius = MathUtils.clamp(radius , Constants.pixelsToMeters(min_radius), Constants.pixelsToMeters(max_radius));
		shape.setRadius(radius);
		
		body.destroyFixture(body.getFixtureList().first());
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("power");
		shape.dispose();
	}
	
	public void updatePosition(Vector2 position){
		updatePosition(position.x, position.y);
	}
	
	private void updatePosition(float x, float y) {
		body.setTransform(x, y, 0);
		
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

	public Body selectBox(float clickX, float clickY, World world){
		Body force;
		BodyDef bdef = new BodyDef();
		bdef.position.set(Constants.pixelsToMeters(clickX), Constants.pixelsToMeters(clickY));
		force = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Constants.pixelsToMeters(10), Constants.pixelsToMeters(10));
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		force.createFixture(fdef).setUserData("force");
		return force;
	}
	
	public Body getPlayerForce(Vector2 position) {
		return getPlayerForce(position.x, position.y);
	}
	
}
