package com.projecttwin.character;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.projecttwin.utils.Constants;

public class PlayerForce {
	
	private World world;
	private Body body;
	private float radius;
	private final float min_radius  = 5;
	private final float max_radius = 300;
	private final float increate_rate = 2;
	
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
	
	public void updatePosition(float x, float y) {
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

	
	public Body getPlayerForce(Vector2 position) {
		return getPlayerForce(position.x, position.y);
	}
	
	
	/**
	 * 
	 * @param object that you want to apply force to
	 * @param destination 
	 * @param powerType  if mode true = pull mode otherwise push 
	 */
	public static void applyPowerToObject(Body object, Vector2 destination, int powerType ){
		
		double degree = Constants.getAngle(new Vector2(Constants.metersToPixels(object.getPosition().x), Constants.metersToPixels(object.getPosition().y)), destination);
		double xPow = Constants.metersToPixels((float) Math.cos(Math.toRadians(degree))) * 2;
		double yPow = Constants.metersToPixels((float) Math.sin(Math.toRadians(degree))) * 2;
		if((powerType == 1 && degree > 90 && degree < 270) || (powerType == 0 && (degree > 270 || degree < 90)))
			Constants.bodyInPower.applyForce((float)xPow, (float)yPow, Constants.bodyInPower.getPosition().x, Constants.bodyInPower.getPosition().y, true);
	}
	
}
