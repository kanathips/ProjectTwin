package com.projecttwin.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Player;
import com.projecttwin.handeller.CharacterControll;
import com.projecttwin.handeller.Timer;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.XmlLoader;

/**
 * This class is initial starter instants and create object in this game
 * <p>
 * <b>Thing this class do : </b>
 * <p>
 * create player texture
 * <p>
 * create game state
 * <p>
 * something
 * <p>
 * something
 * 
 * @author NewWy
 *
 */
public class WorldController implements Disposable {
	public static final String TAG = WorldController.class.getName();
	private static Sprite playerSprite;
	private static Sprite[] boxSprites;
	private static Player player;
	public static float startPlayerWidth;
	public static float startPlayerHeigth;
	private static WorldPhysic worldPhysic;
	private static TiledMap tiledMap;
	public boolean saved;
	private static OrthogonalTiledMapRenderer tiledMapRenderer;

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public static Sprite getPlayerSprite() {
		return playerSprite;
	}

	public static void setPlayerSprite(Sprite playerSprite) {
		WorldController.playerSprite = playerSprite;
	}

	public static Sprite[] getBoxSprites() {
		return boxSprites;
	}

	public static void setBoxSprites(Sprite[] boxSprites) {
		WorldController.boxSprites = boxSprites;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		WorldController.player = player;
	}

	private Timer timer;
	private int stage;

	public WorldPhysic getWorldPhysic() {
		return worldPhysic;
	}

	public void setWorldPhysic(WorldPhysic worldPhysic) {
		WorldController.worldPhysic = worldPhysic;
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public void setTiledMap(TiledMap tiledMap) {
		WorldController.tiledMap = tiledMap;
	}

	public OrthogonalTiledMapRenderer getTiledMapRenderer() {
		return tiledMapRenderer;
	}

	public void setTiledMapRenderer(OrthogonalTiledMapRenderer tiledMapRenderer) {
		WorldController.tiledMapRenderer = tiledMapRenderer;
	}

	public WorldController(int stage) {
		this.stage = stage;
		init();
	}

	public void init() {
		player = Assets.instance.getPlayer();
		player.setPowerType(Constants.powerType);
		playerSprite = new Sprite(player.playerFrame);
		playerSprite.setPosition(50, 60);
		startPlayerHeigth = playerSprite.getHeight();
		startPlayerWidth = playerSprite.getWidth();
		setTimer(new Timer(180));
		String mapLocation = null;
		saved = false;
		switch(stage){
			case 1:
				mapLocation = "maps/map1.tmx";
				break;
			case 2:
				mapLocation = "maps/map2.tmx";
				break;
			case 3:
				mapLocation = "maps/map3.tmx";
				break;
			case 4:
				mapLocation = "maps/map4.tmx";
				break;
			case 5:
				mapLocation = "maps/map5.tmx";
				break;
		}
		tiledMap = new TmxMapLoader().load(mapLocation);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		getTimer().start();
	}

	public void update(float deltaTime) {
		if (!Constants.power)
			player.setPowerType(Constants.powerType);
		CharacterControll.updatePlayer(deltaTime);
		if (getTimer().hasCompleted()) {
			XmlLoader xmlLoader = new XmlLoader("database.xml", "level");
			if (getTimer().getTimeLeft() > Integer.parseInt(xmlLoader.getData(stage, "highscore"))) {
				xmlLoader.setData(stage, "highscore", String.format("%d", getTimer().getTimeLeft()));
				xmlLoader.saveData();
			}
			Constants.gameOver = true;
		}
		if (Constants.gameFinished) {
			System.out.println("fin stage" + stage);
			XmlLoader xmlLoader = new XmlLoader("database.xml", "level");
			if (getTimer().getTimeLeft() > Integer.parseInt(xmlLoader.getData(stage, "highscore"))) {
				xmlLoader.setData(stage, "highscore", String.format("%d", getTimer().getTimeLeft()));
				xmlLoader.saveData();
			}
			try {
				xmlLoader.setData(stage + 1, "unlock", "true");
			} catch (Exception e) {
				
			}
			xmlLoader.saveData();
			saved = true;
		}
	}

	public void dispose() {
		player.dispose();
	}

}
