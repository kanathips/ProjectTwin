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
import com.projecttwin.utils.Pair;

public class GateButtonBuilder extends AbstractMapBuilder {

	private TiledMap tiledMap;
	private short mapCategory;
	private World world;
	private String layer;

	public GateButtonBuilder(TiledMap tiledMap, float ppt, World world, String layer, short mapCategory) {
   		super(ppt);
   		this.tiledMap = tiledMap;
   		this.mapCategory = mapCategory;
   		this.world = world;
   		this.layer = layer;
	}

	public Pair<Array<Body>, Array<Body>> buildShapes() {
        MapObjects objects = tiledMap.getLayers().get(layer).getObjects();

        Array<Body> buttonBodies = new Array<Body>();
        Array<Body> gateBodies = new Array<Body>();
        
        for(MapObject object : objects) {
            
        	Shape shape = checkShape(object);
            if(shape == null)
            	continue;
            
            FixtureDef fdef = new FixtureDef();
            fdef.density = 1;
            fdef.shape = shape;
            fdef.filter.categoryBits = mapCategory;
            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(fdef).setUserData(new Pair<String, String>(name, (String) object.getProperties().get("link")));
            body.setUserData(new Pair<String, String>(name, (String) object.getProperties().get("link")));
            
            //set name and category to physic body
    		//use in contact handler and filter
            if(name.equals("button"))
            	buttonBodies.add(body);
            else if(name.equals("gate"))
            	gateBodies.add(body);
            shape.dispose();
        }
        return new Pair<Array<Body>, Array<Body>>(gateBodies, buttonBodies);
    }

	@Override
	public void dispose() {
		world.dispose();
	}

}
