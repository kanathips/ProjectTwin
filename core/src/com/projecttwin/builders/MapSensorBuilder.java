package com.projecttwin.builders;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class MapSensorBuilder extends AbstractMapBuilder {
	
	
	private TiledMap tiledMap;
	private World world;
	private String layer;

	public MapSensorBuilder(TiledMap tiledMap, float ppt, World world, String layer) {
		super(ppt);
		this.tiledMap = tiledMap;
   		this.world = world;
   		this.layer = layer;
	}

	public Array<Body> buildShapes() {
		MapObjects objects = tiledMap.getLayers().get(layer).getObjects();

        Array<Body> bodies = new Array<Body>();
        
        for(MapObject object : objects) {
        	Shape shape = checkShape(object);
        	if(shape == null)
        		continue;
        	
        	FixtureDef fdef = new FixtureDef();
            fdef.density = 1;
            fdef.shape = shape;
            fdef.isSensor = true;
            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(fdef).setUserData(name);
            
            bodies.add(body);
            shape.dispose();
        }
		return null;
	}

	@Override
	public void dispose() {
		world.dispose();
	}

}
