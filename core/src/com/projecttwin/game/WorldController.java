package com.projecttwin.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.projecttwin.character.Box;
import com.projecttwin.character.Player;
import com.projecttwin.handeller.CharacterControll;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.CameraHelper;
import com.projecttwin.utils.Constants;
/**
 * This class is initial starter instants and create object in this game
 * <p><b>Thing this class do : </b>
 * <p>create player texture
 * <p>create game state
 * <p>something
 * <p>something
 * @author NewWy
 *
 */
public class WorldController implements Disposable{
	public static final String TAG = WorldController.class.getName();
	private static CameraHelper cameraHelper;
	protected StageLevel level;
	private static Sprite playerSprite;
	private static Sprite[] boxSprites;
	private static Player player;
	private static Box box;
	public static float startPlayerWidth;
	public static float startPlayerHeigth;
	private static WorldPhysic worldPhysic;
	private static TiledMap tiledMap;
	private static OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public enum StageLevel{
		INTRO, ONE, TWO, THREE, FOUR, FIVE;
	}
	
	public static CameraHelper getCameraHelper() {
		return cameraHelper;
	}

	public static void setCameraHelper(CameraHelper cameraHelper) {
		WorldController.cameraHelper = cameraHelper;
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

	public static Box getBox() {
		return box;
	}

	public static void setBox(Box box) {
		WorldController.box = box;
	}

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


	
	public WorldController(){
		init();
	}
	
	public void init(){
		level = StageLevel.INTRO;
		player = Assets.instance.getPlayer();
		
		playerSprite = new Sprite(player.playerFrame);
		playerSprite.setPosition(50, 160);
		startPlayerHeigth = playerSprite.getHeight();
		startPlayerWidth = playerSprite.getWidth();
		
		box = Assets.instance.getBox();
		boxSprites = new Sprite[5];
		for(int i = 0; i < boxSprites.length; i++){
			boxSprites[i] = new Sprite(box.boxTextute);
			float randomX = MathUtils.random(100, Constants.VIEWPORT_WIDTH - 100);
			float randomY = MathUtils.random(100, Constants.VIEWPORT_HEIGHT - 100);
			boxSprites[i].setOriginCenter();
			boxSprites[i].setPosition(randomX, randomY);
		}		
		
		cameraHelper = new CameraHelper();
		cameraHelper.setTarget(playerSprite);
		tiledMap = new TmxMapLoader().load("untitiled.tmx");
//		tiledMap = new TmxMapLoader().load("Testing_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);       
	}
	
	public void update(float deltaTime){
			
		cameraHelper.update();
		CharacterControll.updateBox();
		CharacterControll.updatePlayer(deltaTime);
	}

	public void dispose(){
		player.dispose();
	}
	
}
