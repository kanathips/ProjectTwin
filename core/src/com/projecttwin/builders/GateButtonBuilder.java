package com.projecttwin.builders;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.utils.Pair;

public class GateButtonBuilder {

	private TiledMap tiledMap;
<<<<<<< HEAD
=======
	private short mapCategory;
>>>>>>> origin/master
	private World world;
	private String layer;
	protected static float ppt = 0;
	protected static String name = "";
	protected static Vector2 position;
	protected static ArrayList<TreeMap<String, Object>> renderShape;

<<<<<<< HEAD
	public GateButtonBuilder(TiledMap tiledMap, float ppt, World world, String layer) {
		GateButtonBuilder.ppt = ppt;
		this.tiledMap = tiledMap;
=======
	public GateButtonBuilder(TiledMap tiledMap, float ppt, World world, String layer, short mapCategory) {
		GateButtonBuilder.ppt = ppt;
		this.tiledMap = tiledMap;
		this.mapCategory = mapCategory;
>>>>>>> origin/master
		this.world = world;
		this.layer = layer;
		renderShape = new ArrayList<TreeMap<String, Object>>();
	}

	protected static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		name = rectangleObject.getName();
		if (!name.equals("button")) {
			TreeMap<String, Object> data = new TreeMap<String, Object>();
			Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
					(rectangle.y + rectangle.height * 0.5f) / ppt);
			polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size, 0.0f);

			data.put("type", "rect");
			data.put("shape",
					new float[] { rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size.x, size.y });
			data.put("link", rectangleObject.getProperties().get("link"));
			renderShape.add(data);
		} else {
			position = new Vector2(rectangle.x / ppt, rectangle.y / ppt);
			polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt);
		}
		return polygon;
	}

	protected static CircleShape getCircle(EllipseMapObject ellipseMapObject) {
		Ellipse ellipse = ellipseMapObject.getEllipse();
		CircleShape circleShape = new CircleShape();
		name = ellipseMapObject.getName();
		circleShape.setRadius(ellipse.width / 2 / ppt);
		if (!name.equals("button")) {
			TreeMap<String, Object> data = new TreeMap<String, Object>();
			data.put("type", "cir");
			data.put("shape", new float[] { ellipse.width / 2 / ppt, ellipse.x, ellipse.y });
			data.put("link", ellipseMapObject.getProperties().get("link"));
			circleShape.setPosition(new Vector2(ellipse.x / ppt, ellipse.y / ppt));
			renderShape.add(data);
		} else {
			position = new Vector2(ellipse.x / ppt, ellipse.y / ppt);
		}

		return circleShape;
	}

	protected static PolygonShape getPolygon(PolygonMapObject polygonObject) {
		PolygonShape polygon = new PolygonShape();
		name = polygonObject.getName();
		float[] vertices = polygonObject.getPolygon().getTransformedVertices();
		float[] worldVertices = new float[vertices.length];

		for (int i = 0; i < vertices.length; ++i) {
			worldVertices[i] = vertices[i] / ppt;
		}
		TreeMap<String, Object> data = new TreeMap<String, Object>();
		data.put("type", "poly");
		data.put("shape", worldVertices);
		data.put("link", polygonObject.getProperties().get("link"));
		renderShape.add(data);
		polygon.set(worldVertices);
		return polygon;
	}

	protected static ChainShape getPolyline(PolylineMapObject polylineObject) {
		float[] vertices = polylineObject.getPolyline().getTransformedVertices();
		name = polylineObject.getName();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] / ppt;
			worldVertices[i].y = vertices[i * 2 + 1] / ppt;
		}
		ChainShape chain = new ChainShape();
		TreeMap<String, Object> data = new TreeMap<String, Object>();
		data.put("type", "line");
		data.put("shape", worldVertices);
		data.put("link", polylineObject.getProperties().get("link"));
		renderShape.add(data);
		chain.createChain(worldVertices);
		return chain;
	}

	protected Shape checkShape(MapObject object) {

		if (object instanceof TextureMapObject) {
			return null;
		}
		Shape shape;

		if (object instanceof RectangleMapObject) {
			shape = getRectangle((RectangleMapObject) object);
		} else if (object instanceof PolygonMapObject) {
			shape = getPolygon((PolygonMapObject) object);
		} else if (object instanceof PolylineMapObject) {
			shape = getPolyline((PolylineMapObject) object);
		} else if (object instanceof EllipseMapObject) {
			shape = getCircle((EllipseMapObject) object);
		} else {
			return null;
		}
		return shape;

	}

	/**
	 * 
	 * @return Pair of body array first is gate body, second is button body
	 */
	public Pair<Array<Body>, Array<Body>> buildShapes() {
<<<<<<< HEAD
		MapObjects objects;
		try {
			objects = tiledMap.getLayers().get(layer).getObjects();
		} catch (NullPointerException e) {
			return null;
		}
=======
		MapObjects objects = tiledMap.getLayers().get(layer).getObjects();
>>>>>>> origin/master
		Array<Body> buttonBodies = new Array<Body>();
		Array<Body> gateBodies = new Array<Body>();

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
			fdef.filter.categoryBits = mapCategory;
>>>>>>> origin/master
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			if (name.equals("button")) {
				bd.position.set(position);
			}
			Body body = world.createBody(bd);
			data.put("name", name);
			data.put("link", (String) object.getProperties().get("link"));
			data.put("status", (String) object.getProperties().get("status"));
			body.createFixture(fdef).setUserData(data);
			body.setUserData(data);
			// set name and category to physic body
			// use in contact handler and filter
			if (name.equals("button")) {
				buttonBodies.add(body);

			} else if (name.equals("gate"))
				gateBodies.add(body);
			shape.dispose();
		}
		return new Pair<Array<Body>, Array<Body>>(gateBodies, buttonBodies);
	}

	public ArrayList<TreeMap<String, Object>> getRenderShape() {
		return renderShape;
	}

	public void dispose() {
		world.dispose();
	}
}