package com.projecttwin.builders;

import java.util.TreeMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class MapBodyBuilder extends AbstractMapBuilder {

	// The pixels per tile. If your tiles are 16x16, this is set to 16f

	private TiledMap tiledMap;
	private World world;
	private String layer;

	public MapBodyBuilder(TiledMap tiledMap, float ppt, World world, String layer) {
		super(ppt);
		this.tiledMap = tiledMap;
		this.world = world;
		this.layer = layer;
	}

	public void setTiledMap(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}

	public void setLayer(String layer) {
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
			fdef.restitution = 0.3f;
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			Body body = world.createBody(bd);
			data.put("name", name);
			body.createFixture(fdef).setUserData(data);
			body.setUserData(data);

			// set name and category to physic body
			// use in contact handler and filter

			bodies.add(body);
			shape.dispose();
		}
		return bodies;
	}

	@Override
	public void dispose() {
		world.dispose();
	}
}