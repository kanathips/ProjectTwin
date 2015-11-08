package com.projecttwin.game;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private Array<Sprite> boxSprite;
	private Sprite img;
	private OrthographicCamera hudCam;
	private TreeMap<String, Sprite> hudTexture;
	private TreeMap<String, Sprite> mapTexture;
	private Array<Sprite> starSprites;
	private static float degreePosition = 0;

	public WorldRender(WorldController worldController, WorldPhysic worldPhysic) {
		this.worldController = worldController;
		this.worldPhysic = worldPhysic;
		init();
	}

	/**
	 * initial renderer instant
	 */

	private void init() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.update();
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		hudCam.update();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		boxSprite = getBoxSprite(WorldPhysic.boxBodys);
		img = new Sprite(new Texture(Gdx.files.internal("images/force.png")));
		img.setSize(worldPhysic.playerForce.getRadius() * 2, worldPhysic.playerForce.getRadius() * 2);
		hudTexture = Assets.instance.getHud().getTexture();
		mapTexture = Assets.instance.getMapAnimation().getTexture();
		starSprites = getStarSprite(worldPhysic.sensorBody);
		int lineWidth = 5; // pixels
		Gdx.gl.glLineWidth(lineWidth / camera.zoom);
	}

	/**
	 * main render method
	 */
	public void render(float deltaTime) {
		renderMap();
		renderForce(deltaTime);
		renderGate(worldPhysic.renderShape, worldPhysic.gateButtonBody.getSecond());
		renderObject();
		// worldPhysic.render(batch);
		renderHud();
		renderStar(deltaTime);
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
		for (int i = 0; i < boxSprite.size; i++) {
			updateBoxposition(boxSprite.get(i), WorldPhysic.boxBodys.get(i));
			boxSprite.get(i).draw(batch);
		}
		for (Body b : worldPhysic.gateButtonBody.getSecond()) {
			updateButton(b).draw(batch);
			;
		}
		batch.end();

	}

	/**
	 * get tiledmap form worldController and render
	 */
	public void renderMap() {
		worldController.getTiledMapRenderer().setView(camera);
		worldController.getTiledMapRenderer().render();
	}

	public Sprite updateButton(Body b) {
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
				shapeRenderer.setColor(0, 0, 255, 1);
				shapeRenderer.begin(ShapeType.Line);
				shapeRenderer.line(new Vector2(Constants.clickX, Constants.clickY),
						Constants.metersToPixels(Constants.bodyInPower.getPosition()));
				shapeRenderer.end();
			}
		}
	}

	public Array<Sprite> getBoxSprite(Array<Body> bodyArray) {
		Box box = Assets.instance.getBox();
		Array<Sprite> boxSprites = new Array<Sprite>();
		for (Body b : bodyArray) {
			Sprite sprite = new Sprite(box.boxTextute);
			float x = b.getPosition().x;
			float y = b.getPosition().y;
			sprite.setOriginCenter();
			sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2,
					Constants.metersToPixels(y) - sprite.getHeight() / 2);
			boxSprites.add(sprite);
		}
		return boxSprites;
	}

	public Sprite renderGate(ArrayList<TreeMap<String, Object>> shape, Array<Body> bodys) {
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
		return null;
	}

	private void updateBoxposition(Sprite sprite, Body body) {
		sprite.setPosition(Constants.metersToPixels(body.getPosition().x) - sprite.getWidth() / 2,
				Constants.metersToPixels(body.getPosition().y) - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
	}

	public Array<Sprite> getStarSprite(Array<Pair<Body, Vector2>> sensorBody) {
		Array<Sprite> starSprites = new Array<Sprite>();
		Sprite starTexture = mapTexture.get("star");
		for (Pair<Body, Vector2> b : sensorBody) {
			Body body = b.getFirst();
			Vector2 position = b.getSecond();
			TreeMap<String, String> data = (TreeMap<String, String>) body.getUserData();
			if (data.get("name").equals("star")) {
				Sprite sprite = new Sprite(starTexture);
				float x = position.x;
				float y = position.y;
				sprite.setOriginCenter();
				sprite.setPosition(Constants.metersToPixels(x) - sprite.getWidth() / 2,
						Constants.metersToPixels(y) - sprite.getHeight() / 2);
				starSprites.add(sprite);
			}
		}
		return starSprites;
	}

	public void updateStarSprite(Sprite sprite, float degreePosition) {
		final float shakeAmplitudeInDegrees = 5.0f;
		float shake = MathUtils.sin(degreePosition) * shakeAmplitudeInDegrees;
		sprite.setRotation(shake);
	}

	public void renderStar(float deltaTime) {
		float degressPerSecond = 10.0f;
		degreePosition = (degreePosition + deltaTime * degressPerSecond) % 360;
		batch.begin();
		for (Sprite s : starSprites) {
			updateStarSprite(s, degreePosition);
			s.draw(batch);
		}
		batch.end();
	}
}
