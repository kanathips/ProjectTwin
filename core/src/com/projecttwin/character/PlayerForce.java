package com.projecttwin.character;

import java.util.TreeMap;

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
	private static Body body;
	private float radius;
	private final float min_radius = 5;
	private final float max_radius = 200;
	private final float increate_rate = 2;
	private TreeMap<String, String> data;
	public static boolean fire = false;
	public PlayerForce(World world) {
		this.world = world;
		data = new TreeMap<String, String>();
		data.put("name", "power");
	}

	public Body getPlayerForce(float x, float y) {
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
		
		body.createFixture(fdef).setUserData(data);
		shape.dispose();
		return body;
	}

	public void update() {
		body.setActive(true);
		CircleShape shape = new CircleShape();
		radius += Constants.pixelsToMeters(increate_rate);
		radius = MathUtils.clamp(radius, Constants.pixelsToMeters(min_radius), Constants.pixelsToMeters(max_radius));
		shape.setRadius(radius);

		body.destroyFixture(body.getFixtureList().first());
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(data);
		shape.dispose();
	}

	public void updatePosition(Vector2 position) {
		updatePosition(position.x, position.y);
	}

	public void updatePosition(float x, float y) {
		body.setTransform(x, y, 0);
	}

	public void destroy() {
		CircleShape shape = new CircleShape();
		radius = Constants.pixelsToMeters(min_radius);
		shape.setRadius(radius);
		body.setActive(false);
		body.destroyFixture(body.getFixtureList().first());
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(data);

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
	 * @param object
	 *            that you want to apply force to
	 * @param destination
	 * @param powerType
	 *            if mode true = pull mode otherwise push
	 */
	public static void applyPowerToObject(Body object, Vector2 destination, int powerType) {
		fire = false;
		double degree = Constants.getAngle(new Vector2(Constants.metersToPixels(object.getPosition().x),
				Constants.metersToPixels(object.getPosition().y)), destination);
		double xPow = Constants.metersToPixels((float) Math.cos(Math.toRadians(degree)) * 0.5f);
		double yPow = Constants.metersToPixels((float) Math.sin(Math.toRadians(degree)) * 0.5f);

		// push = 0 pull = 1
		if (checkPosition(object, destination, powerType))
			object.applyForce((float) xPow, (float) yPow, object.getPosition().x, Constants.bodyInPower.getPosition().y,
					true);
	}
	
	public static boolean checkPosition(Body object, Vector2 destination, int powerType){
		fire = false;
		double degree = Constants.getAngle(new Vector2(Constants.metersToPixels(object.getPosition().x),
				Constants.metersToPixels(object.getPosition().y)), destination);

		// push = 0 pull = 1
		if (powerType == 0) {
			if (!(body.getPosition().x < object.getPosition().x) && degree < 270 && degree > 90) {
				fire = true;
			} else if (!(body.getPosition().x > object.getPosition().x) && (degree > 270 || degree < 90))
				fire = true;
		} else if (powerType == 1) {
			if (!(body.getPosition().x > object.getPosition().x) && degree < 270 && degree > 90)
				fire = true;
			else if (!(body.getPosition().x < object.getPosition().x) && (degree > 270 || degree < 90))
				fire = true;
		}
		return fire;
	}
}
