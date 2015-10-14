package com.projecttwin.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.MapBodyBuilder;

public class WorldPhysic implements Disposable{
	public World world;
	private WorldController worldController;
	private BodyDef bodyDef;
	protected Body playerBody;
	protected Body[] boxBodys;
	private Sprite playerSprite;
	private Sprite[] boxSprites;
	private Array<Body> mapBody;
	public WorldPhysic(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	public void init(){
		world = new World(new Vector2(0, -100), true);
		initPlayerBody();
		initBox();
		mapBody = MapBodyBuilder.buildShapes(worldController.tiledMap, 1, world, "WalkWay"); // build a map and collect data in mapbody
	}
		
	public void initBox(){
		boxSprites = worldController.boxSprites;
		boxBodys = new Body[boxSprites.length];
		int i = 0;
		for(Sprite boxSprite: boxSprites){
			bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set((boxSprite.getX() + boxSprite.getWidth() / 2),
					boxSprite.getY() + boxSprite.getHeight() / 2);
			boxBodys[i] = world.createBody(bodyDef);
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 1f;
			fixtureDef.restitution = 0.5f;
			boxBodys[i].createFixture(fixtureDef);
			shape.dispose();
			i++;
		}
	}
	
	public void initPlayerBody(){
		playerSprite = worldController.playerSprite;
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set((playerSprite.getX() + playerSprite.getWidth() / 2),
				(playerSprite.getY() + playerSprite.getHeight() / 2));
		playerBody = world.createBody(bodyDef);
		bodyDef = null;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerSprite.getWidth() / 2,	playerSprite.getHeight() / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		playerBody.createFixture(fixtureDef);
		playerBody.setFixedRotation(true);
		shape.dispose();		
	}
	
	public void update(float deltaTime){
		world.step(deltaTime, 100, 100);	
	}
	
	public void dispose(){
		world.dispose();
	}
	
}