package com.projecttwin.game;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Box;
import com.projecttwin.character.PlayerForce;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.Pair;;

/**
 * This Class is use to render everything in this game
 * 
 * @author NewWy
 *
 */
public class WorldRender implements Disposable {
	public static OrthographicCamera camera;
	private WorldController worldController;
	private SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	private ShapeRenderer shapeRenderer;
	Vector3 position;
	private WorldPhysic worldPhysic;
	private static Array<Sprite> boxSprites;
	private Sprite img;
	private OrthographicCamera hudCam;
	private TreeMap<String, Sprite> hudTexture;
	private TreeMap<String, Sprite> mapTexture;
	private TreeMap<String, Animation> mapAnimation;
	private Array<TreeMap<String, Object>> targetAnimation;
	private float trigger;
	private static Array<Sprite> ballSprites;
	private boolean doNotBox;
	private boolean doNotButton;
	private Array<TreeMap<String, Object>> springAnimations;


	public WorldRender(WorldController worldController, WorldPhysic worldPhysic) {
		this.worldController = worldController;
		this.worldPhysic = worldPhysic;
		init();
	}

	/**
	 * initial renderer instant
	 */

	private void init() {
		doNotBox = false;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.update();
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		hudCam.update();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		boxSprites = getBoxSprite(WorldPhysic.boxBodys);
		ballSprites = getBallSprites(WorldPhysic.boxBodys);
		img = new Sprite(new Texture(Gdx.files.internal("images/force.png")));
		img.setSize(worldPhysic.playerForce.getRadius() * 2, worldPhysic.playerForce.getRadius() * 2);
		hudTexture = Assets.instance.getHud().getTexture();
		mapTexture = Assets.instance.getMapAnimation().getTexture();
		mapAnimation = Assets.instance.getMapAnimation().getAnimation();
		targetAnimation = getTarget(worldPhysic.sensorBody);
		springAnimations = getSpringAnimation(worldPhysic.sensorBody);
		int lineWidth = 5; // pixels
		Gdx.gl.glLineWidth(lineWidth / camera.zoom);
	}

	/**
	 * main render method
	 */
	public void render(float deltaTime) {
		renderMap();
		renderForce(deltaTime);
		renderObject();
		renderHud();
		try{
			renderSpring(deltaTime);
		}catch(NullPointerException e){
			
		}
		renderTarget(deltaTime);
		try{
			if(!doNotButton)
				renderGate(worldPhysic.renderShape, worldPhysic.gateButtonBody.getSecond());
		}catch(NullPointerException e){
			Gdx.app.debug(Constants.no + "World Render :", " do not render gate");
			Constants.no++;
			doNotButton = true;
		}
	}

	public void renderHud() {
		batch.setProjectionMatrix(hudCam.combined);
		Long timeLeft = worldController.getTimer().getTimeLeft();
		int size = timeLeft.toString().length();
		for (int i = 0; i < size; i++) {
			char s = timeLeft.toString().toCharArray()[i];
			Sprite sprite = hudTexture.get("" + s);
			sprite.setPosition(Constants.VIEWPORT_WIDTH - (sprite.getWidth() * (size - i) + 20),
					Constants.VIEWPORT_HEIGHT - sprite.getHeight() - 20);
			if (timeLeft > 20 || timeLeft % 2 == 0) {
				batch.begin();
				sprite.draw(batch);
				batch.end();
			}

		}
	}

	public void renderObject() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		WorldController.getPlayerSprite().draw(batch);
		int boxNo = 0, ballNo = 0;
		for (Body b : WorldPhysic.boxBodys) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
			if (data.get("name").equals("box")) {
				updateBoxPosition(boxSprites.get(boxNo), b);
				boxSprites.get(boxNo).draw(batch);
				boxNo++;
			} else if (data.get("name").equals("ball") && b.isActive()) {
				updateBallPosition(ballSprites.get(ballNo), b);
				ballSprites.get(ballNo).draw(batch);
				ballNo++;
			}
		}
		if (worldPhysic.gateButtonBody == null)
			doNotButton = true;
		else {
			for (Body b : worldPhysic.gateButtonBody.getSecond()) {
				updateButton(b).draw(batch);
			}
		}
		batch.end();

	}

	private void updateBallPosition(Sprite sprite, Body body) {
		sprite.setPosition(Constants.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2,
				Constants.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));

	}

	/**
	 * get tiledmap form worldController and render
	 */
	public void renderMap() {
		worldController.getTiledMapRenderer().setView(camera);
		worldController.getTiledMapRenderer().render();
	}

	public Sprite updateButton(Body b) {
		if (doNotButton) {
			return null;
		}
		@SuppressWarnings("unchecked")
		TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
		Sprite sprite = null;
		if (data.get("status").equals("1")) {
			float x = b.getPosition().x;
			float y = b.getPosition().y;
			sprite = new Sprite(mapTexture.get("switchOff"));
			sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2, Constants.metersToPixels(y));
		} else if (data.get("status").equals("-1")) {
			float x = b.getPosition().x;
			float y = b.getPosition().y;
			sprite = new Sprite(mapTexture.get("switchOn"));
			sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2, Constants.metersToPixels(y));
		}
		return sprite;
	}

	/**
	 * When window is resized, call this method
	 * 
	 * @param width
	 *            of new window
	 * @param height
	 *            of new window
	 */
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}

	/**
	 * render player power
	 * 
	 * @param deltaTime
	 */
	public void renderForce(float deltaTime) {
		batch.setProjectionMatrix(camera.combined);
		float i = 360 * deltaTime;
		i = Constants.powerType == 1 ? i * -1 : i;
		img.rotate(i);
		if (Constants.power) {
			img.setSize(Constants.metersToPixels(worldPhysic.playerForce.getRadius() * 2),
					Constants.metersToPixels(worldPhysic.playerForce.getRadius() * 2));
			img.setOriginCenter();
			img.setPosition(Constants.metersToPixels(worldPhysic.forceBody.getPosition().x) - img.getWidth() / 2,
					Constants.metersToPixels(worldPhysic.forceBody.getPosition().y) - img.getHeight() / 2);
			batch.begin();
			img.draw(batch);
			batch.end();
			if (Constants.haveObjectinPower) {
				if (PlayerForce.checkPosition(Constants.bodyInPower, new Vector2(Constants.clickX, Constants.clickY),
						Constants.powerType))
					shapeRenderer.setColor(Color.BLUE);
				else
					shapeRenderer.setColor(Color.RED);
				shapeRenderer.begin(ShapeType.Line);
				shapeRenderer.line(new Vector2(Constants.clickX, Constants.clickY),
						Constants.metersToPixels(Constants.bodyInPower.getPosition()));
				shapeRenderer.end();
			}
		}
	}

	public Array<Sprite> getBoxSprite(Array<Body> bodys) {
		if (bodys == null) {
			doNotBox = true;
			return null;
		}
		Box box = Assets.instance.getBox();
		Array<Sprite> boxSprites = new Array<Sprite>();
		for (Body b : bodys) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
			if (data.get("name").equals("box")) {
				Sprite sprite = new Sprite(box.boxTextute);
				float x = b.getPosition().x;
				float y = b.getPosition().y;
				sprite.setOriginCenter();
				sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2,
						Constants.metersToPixels(y) - sprite.getHeight() / 2);
				boxSprites.add(sprite);
			}
		}
		return boxSprites;
	}

	public Array<Sprite> getBallSprites(Array<Body> bodys) {
		TreeMap<String, Sprite> ballTextures = Assets.instance.getBall().getTexture();
		Array<Sprite> ballSprites = new Array<Sprite>();

		for (Body b : bodys) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getUserData();
			if (data.get("name").equals("ball")) {
				Sprite sprite = null;
				if (data.get("color").equals("blue")) {
					sprite = new Sprite(ballTextures.get("blueBall"));
				} else if (data.get("color").equals("purple")) {
					sprite = new Sprite(ballTextures.get("purpleBall"));
				}

				float x = b.getPosition().x;
				float y = b.getPosition().y;
				// sprite.setOriginCenter();
				sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2, Constants.metersToPixels(y));
				ballSprites.add(sprite);
			}
		}
		return ballSprites;
	}

	public void renderGate(ArrayList<TreeMap<String, Object>> shape, Array<Body> bodys) {
		if (bodys == null) {
			doNotButton = true;
			return;
		}
		shapeRenderer.begin(ShapeType.Line);
		TreeMap<String, String> linkStatus = new TreeMap<String, String>();
		for (Body b : bodys) {
			@SuppressWarnings("unchecked")
			TreeMap<String, String> userData = (TreeMap<String, String>) b.getUserData();
			linkStatus.put(userData.get("link"), userData.get("status"));
		}
		for (TreeMap<String, Object> s : shape) {
			if (linkStatus.get(s.get("link")).equals("-1")) {
				shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLUE);
			} else {
				shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GRAY);
			}
			if (s.get("type").equals("rect")) {
				float[] data = (float[]) s.get("shape");
				float width = Constants.metersToPixels(data[0]) * 2;
				float height = Constants.metersToPixels(data[1]) * 2;
				shapeRenderer.rect(Constants.metersToPixels(data[2]) - width / 2,
						Constants.metersToPixels(data[3]) - height / 2, width, height);
			} else if (s.get("type").equals("cir")) {
				float[] data = (float[]) s.get("shape");
				shapeRenderer.circle(Constants.metersToPixels(data[1]), Constants.metersToPixels(data[2]),
						Constants.metersToPixels(data[0]));
			} else if (s.get("type").equals("poly")) {
				float[] data = (float[]) s.get("shape");
				for (int i = 0; i < data.length; i++) {
					data[i] = Constants.metersToPixels(data[i]);
				}
				shapeRenderer.polygon(data);
			} else if (s.get("type").equals("line")) {
				Vector2[] data = (Vector2[]) s.get("shape");
				for (int i = 0; i < data.length - 1; i++) {
					shapeRenderer.rectLine(Constants.metersToPixels(data[i].x), Constants.metersToPixels(data[i].y),
							Constants.metersToPixels(data[i + 1].x), Constants.metersToPixels(data[i + 1].y), 3);
				}
			}
		}
		shapeRenderer.end();
	}

	private void updateBoxPosition(Sprite sprite, Body body) {
		if (doNotBox)
			return;
		sprite.setPosition(Constants.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2,
				Constants.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
	}

	public Array<TreeMap<String, Object>> getTarget(Array<Pair<Body, Vector2>> sensorBody) {
		Array<TreeMap<String, Object>> returnObject = new Array<TreeMap<String, Object>>();
		for (Pair<Body, Vector2> b : sensorBody) {
			TreeMap<String, String> data = (TreeMap<String, String>) b.getFirst().getUserData();
			if (data.get("name").equals("target")) {
				TreeMap<String, Object> object = new TreeMap<String, Object>();
				object.put("position", Constants.metersToPixels(b.getSecond()));
				object.put("body", b.getFirst());
				if (data.get("color").equals("purple")) {
					object.put("animation", mapAnimation.get("targetPurple"));
				} else if (data.get("color").equals("blue")) {
					object.put("animation", mapAnimation.get("targetBlue"));
				}
				returnObject.add(object);
			}
		}
		return returnObject;
	}

	private void renderTarget(float deltaTime) {
		trigger += deltaTime;
		Vector2 position;
		for (TreeMap<String, Object> a : targetAnimation) {
			if (((Body) a.get("body")).isActive()) {
				Sprite frame = new Sprite(((Animation) a.get("animation")).getKeyFrame(trigger, true));
				position = (Vector2) a.get("position");
				frame.setPosition(position.x - frame.getWidth() / 7, position.y - frame.getHeight() / 7);
				batch.begin();
				frame.draw(batch);
				batch.end();
			}
		}
	}
	
	private Array<TreeMap<String, Object>> getSpringAnimation(Array<Pair<Body, Vector2>> sensorBody){
		Array<TreeMap<String, Object>> animation = new Array<TreeMap<String, Object>>();
		for(Pair<Body, Vector2> b : sensorBody){
			TreeMap<String, String> bodyData = (TreeMap<String, String>) b.getFirst().getUserData();
			if(bodyData.get("name").equals("spring")){
				TreeMap<String, Object> data = new TreeMap<String, Object>();
				Animation a = mapAnimation.get("spring");
				data.put("body", b.getFirst());
				data.put("position", b.getSecond());
				data.put("animation", a);
				data.put("time", 0.1f);
				Sprite sprite = new Sprite(a.getKeyFrame(0.0f));
				batch.begin();
				sprite.draw(batch);
				batch.end();
				animation.add(data);
				bodyData.put("playAnimation", "false");
				b.getFirst().setUserData(bodyData);
			}
		}
		return animation;
	}
	
	public void renderSpring(float deltaTime){
		for(TreeMap<String, Object> t : springAnimations){
			Sprite sprite;
			TreeMap<String, String> data = (TreeMap<String, String>)((Body)t.get("body")).getUserData();
			if(data.get("playAnimation").equals("true")){
				Animation a = ((Animation)t.get("animation"));
				t.replace("time", (Float)t.get("time") + deltaTime);
				TextureRegion frame = a.getKeyFrame((Float)t.get("time"), false);
				sprite = new Sprite(frame);
				Vector2 position = Constants.metersToPixels((Vector2)t.get("position"));
				sprite.setPosition(position.x, position.y);
				if(a.isAnimationFinished((Float)t.get("time"))){
					data.replace("playAnimation", "false");
					t.replace("time", 0.0f);
				}
			}else{
				Animation a = ((Animation)t.get("animation"));
				TextureRegion frame = a.getKeyFrame(0.0f, false);
				sprite = new Sprite(frame);
				Vector2 position = Constants.metersToPixels((Vector2)t.get("position"));
				sprite.setPosition(position.x, position.y);
			}
			batch.begin();
			sprite.draw(batch);
			batch.end();
		}
	}
}
