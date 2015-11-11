package com.projecttwin.builders;

import java.util.TreeMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.utils.Pair;

public class ObjectBuilder {

	private static float ppt = 0;
	private static String name = "";
	private TiledMap tiledMap;
	private World world;
	private String layer;
<<<<<<< HEAD
	private Vector2 position;

	public ObjectBuilder(TiledMap tiledMap, float ppt, World world, String layer) {
		ObjectBuilder.ppt = ppt;
		this.tiledMap = tiledMap;
=======
	private short category;
	private Vector2 position;

	public ObjectBuilder(TiledMap tiledMap, float ppt, World world, String layer, short category) {
		ObjectBuilder.ppt = ppt;
		this.tiledMap = tiledMap;
		this.category = category;
>>>>>>> origin/master
		this.world = world;
		this.layer = layer;
	}

	public Array<Body> buildShapes() {
		MapObjects objects = tiledMap.getLayers().get(layer).getObjects();
		Array<Body> bodies = new Array<Body>();

		for (MapObject object : objects) {
			TreeMap<String, String> data = new TreeMap<String, String>();
			Shape shape = checkShape(object);
			if (shape == null)
				continue;

			FixtureDef fdef = new FixtureDef();
			fdef.density = 1;
			fdef.shape = shape;
<<<<<<< HEAD
=======
			fdef.filter.categoryBits = category;
>>>>>>> origin/master
			BodyDef bd = new BodyDef();
			bd.type = BodyType.DynamicBody;
			bd.position.set(position);
			Body body = world.createBody(bd);
			data.put("name", name);
<<<<<<< HEAD
			if(name.equals("ball"))
				data.put("color", (String) object.getProperties().get("color"));
			body.createFixture(fdef).setUserData(data);
			body.setUserData(data);
=======
			body.createFixture(fdef).setUserData(data);
>>>>>>> origin/master

			// set name and category to physic body
			// use in contact handler and filter

			bodies.add(body);
			shape.dispose();
		}
		return bodies;
	}

	public ObjectBuilder(float ppt) {
		ObjectBuilder.ppt = ppt;
	}

	protected static Pair<PolygonShape, Vector2> getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		name = rectangleObject.getName();
		polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt);
		return new Pair<PolygonShape, Vector2>(polygon, new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
				(rectangle.y + rectangle.height * 0.5f) / ppt));
	}

	protected static Pair<CircleShape, Vector2> getCircle(EllipseMapObject ellipseMapObject) {
		Ellipse ellipse = ellipseMapObject.getEllipse();
		CircleShape circleShape = new CircleShape();
		name = ellipseMapObject.getName();
		circleShape.setRadius(ellipse.width / 2 / ppt);
		return new Pair<CircleShape, Vector2>(circleShape, new Vector2(ellipse.x / ppt, ellipse.y / ppt));
	}

	protected Shape checkShape(MapObject object) {

		if (object instanceof TextureMapObject) {
			return null;
		}
		Shape shape;

		if (object instanceof RectangleMapObject) {
			Pair<PolygonShape, Vector2> data = getRectangle((RectangleMapObject) object);
			shape = data.getFirst();
			position = data.getSecond();
		} else if (object instanceof EllipseMapObject) {
			Pair<CircleShape, Vector2> data = getCircle((EllipseMapObject) object);
			shape = data.getFirst();
			position = data.getSecond();
		} else {
			return null;
		}
		return shape;

	}

	public void dispose() {
		world.dispose();
	}
}
