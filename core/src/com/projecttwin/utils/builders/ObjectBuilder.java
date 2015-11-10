package com.projecttwin.utils.builders;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
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
	private short category;
	private Vector2 position;
    
	public ObjectBuilder(TiledMap tiledMap, float ppt, World world, String layer, short category) {
		ObjectBuilder.ppt = ppt;
   		this.tiledMap = tiledMap;
   		this.category = category;
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
            fdef.filter.categoryBits = category;
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DynamicBody;
            bd.position.set(position);
            Body body = world.createBody(bd);
            	body.createFixture(fdef).setUserData(name);
            
            //set name and category to physic body
    		//use in contact handler and filter
            
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
	    polygon.setAsBox(rectangle.width * 0.5f / ppt,
	    			rectangle.height * 0.5f / ppt);
	    return new Pair<PolygonShape, Vector2>(polygon, new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
    			(rectangle.y + rectangle.height * 0.5f ) / ppt));
	}

	protected static Pair<CircleShape, Vector2> getCircle(CircleMapObject circleObject) {
		Circle circle = circleObject.getCircle();
	        CircleShape circleShape = new CircleShape();
	        name = circleObject.getName();
	        circleShape.setRadius(circle.radius / ppt);
	        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
	        return new Pair<CircleShape, Vector2>(circleShape, new Vector2(circle.x / ppt, circle.y / ppt));
	    }
	    
	  protected Shape checkShape(MapObject object){
		  
		  if (object instanceof TextureMapObject) {
              return null;
          }
          Shape shape;

          if (object instanceof RectangleMapObject) {
        	  Pair<PolygonShape, Vector2> data = getRectangle((RectangleMapObject)object);
              shape = data.getFirst();
              position = data.getSecond();
          }
          else if (object instanceof CircleMapObject) {
        	  Pair<CircleShape, Vector2> data = getCircle((CircleMapObject)object);
              shape = data.getFirst();
              position = data.getSecond();
          }
          else {
              return null;
          }
		return shape;
          
	  }
}
