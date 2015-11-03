package com.projecttwin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.projecttwin.callback.QueryPowerCallBack;
import com.projecttwin.character.PlayerForce;
import com.projecttwin.handeller.DoorControll;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.builders.MapBodyBuilder;
import com.projecttwin.utils.builders.ObjectBuilder;
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
	protected Array<Body> mapBody;
	protected Array<Body> stairBody;
	private MapBodyBuilder mapBodyBuilder;
	PlayerForce playerForce;
	Body forceBody;
	private QueryPowerCallBack powercallBack;
	private Box2DDebugRenderer debugRenderer;

	public static Array<Body> boxBodys;
	private ObjectBuilder boxBuilder;
	public Array<Body> buttonBody;
	private DoorControll doorControll;
	
	public WorldPhysic(WorldController worldController) {
		WorldPhysic.worldController = worldController;
		init();
	}
	/**
	 * This method use to initial physic world and create other physic object
	 */
	public void init(){
		world = new World(new Vector2(0, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		playerBody = WorldController.getPlayer().getBody(world, WorldController.getPlayerSprite());
		playerForce = new PlayerForce(world);
		forceBody = playerForce.getPlayerForce(playerBody.getPosition());
		try{
			//create map
			mapBodyBuilder = new MapBodyBuilder(worldController.getTiledMap(), Constants.ppm, world, "unwalkable", Constants.MAP_CATEGORY);
			mapBody = mapBodyBuilder.buildShapes();
			//create box
			boxBuilder = new ObjectBuilder(worldController.getTiledMap(), 100, world, "box", Constants.OBJECT_CATEGORY);
			boxBodys = boxBuilder.buildShapes();
			mapBodyBuilder.setLayer("button");
			buttonBody = mapBodyBuilder.buildShapes();
		}catch(NullPointerException e){
			Gdx.app.debug(TAG, "World Physic have an error on create map");
		}
		worldController.setWorldPhysic(this);
		powercallBack = new QueryPowerCallBack();		
		doorControll = new DoorControll(this);
	}
	
	/**
	 * This method is use to update physic world and update physic object motivation
	 * @param deltaTime
	 */
	public void update(float deltaTime){
		world.step(deltaTime, 6, 2);
		updateForce();
		doorControll.checkDoor();
	}
	
	/**
	 * set player power's position  and update it
	 */
	private void updateForce(){
		playerForce.updatePosition(playerBody.getPosition());
		if(Constants.clickedLeft){
			playerForce.update();
			Player.setState(State.POWERUSNG);
		}
		else if(Constants.clickedRight){
			playerForce.destroy();
			powercallBack.destroy();
			Constants.haveObjectinPower = false;
			Constants.bodyInPower = null;
			Player.setState(State.STANDING);
			Player.resetTrigger();
		}
		else if(Constants.button == 0 && Constants.power && Constants.isClicking){
	    	powercallBack.destroy();
			world.QueryAABB(powercallBack, Constants.pixelsToMeters(Constants.clickX) - Constants.pixelsToMeters(5), Constants.pixelsToMeters(Constants.clickY) - Constants.pixelsToMeters(5),
					Constants.pixelsToMeters(Constants.clickX) + Constants.pixelsToMeters(5), Constants.pixelsToMeters(Constants.clickY) + Constants.pixelsToMeters(5));
			Constants.haveObjectinPower = powercallBack.inPowerfield();
			if(Constants.haveObjectinPower){
				Constants.bodyInPower = powercallBack.getBody();
			}
		}
	}
	
	/**
	 * render box2d Debug mode
	 * @param batch
	 */
	public void render(SpriteBatch batch){
		Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scl(Constants.ppm);
		debugRenderer.render(world, debugMatrix);		
	}
	
	public void dispose(){
		world.dispose();
	}
	
}