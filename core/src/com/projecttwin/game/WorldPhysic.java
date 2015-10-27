package com.projecttwin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.PlayerForce;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.builders.MapBodyBuilder;
import com.projecttwin.utils.builders.MapSensorBuilder;
/**
 * This class is major class you must always use this class to create box2d physic world
 * <p><b>Thing this class do :</b>
 * 		<p>declare physic world
 * 		<p>create player physic body
 * 		<p>create other object physic body
 * 		<p> create map body and map sensor
 * @author NewWy
 *
 */
public class WorldPhysic implements Disposable{
	private final String TAG = this.getClass().getName(); 
	public static World world;
	protected static WorldController worldController;
	public  static Body playerBody;
	public  static Body[] boxBodys;
	protected Array<Body> mapBody;
	protected Array<Body> stairBody;
	private MapBodyBuilder mapBodyBuilder;
	private MapSensorBuilder stairBuilder;
	private PlayerForce playerForce;
	private Body forceBody;
	
	public WorldPhysic(WorldController worldController) {
		WorldPhysic.worldController = worldController;
		init();
	}
	/**
	 * This method use to initial physic world and create other physic object
	 */
	public void init(){
		world = new World(new Vector2(0, -9.8f), true);
		playerBody = WorldController.getPlayer().getBody(world, WorldController.getPlayerSprite());
		playerForce = new PlayerForce(world);
		forceBody = playerForce.getPlayerForce(playerBody.getPosition());
		boxBodys = WorldController.getBox().initBox(world, WorldController.getBoxSprites());
		try{
			mapBodyBuilder = new MapBodyBuilder(worldController.getTiledMap(), Constants.ppm, world, "unwalkable", Constants.MAP_CATEGORY);
			mapBody = mapBodyBuilder.buildShapes();
			// TODO find how to print list of map body that have been create
			stairBuilder = new MapSensorBuilder(worldController.getTiledMap(), Constants.ppm, world, "walkable");
			stairBody = stairBuilder.buildShapes();
			// TODO find how to print list of map sensor that have been create
		}catch(NullPointerException e){
			Gdx.app.debug(TAG, "World Physic have an error on create map");
		}
		
		worldController.setWorldPhysic(this);
		
	}
	
	/**
	 * This method is use to update physic world and update physic object motivation
	 * @param deltaTime
	 */
	public void update(float deltaTime){
		world.step(deltaTime, 6, 2);
		playerForce.updatePosition(playerBody.getPosition());
		if(Constants.clickedLeft){
			playerForce.update();
//			playerForce.selectBox(Constants.clickX, Constants.clickY, world);
		}
		else if(Constants.clickedRight)
			playerForce.destroy();
	}
	
	public void dispose(){
		world.dispose();
	}
	
}