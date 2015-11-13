package com.projecttwin.gameState;

import java.math.BigDecimal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecttwin.character.Player;
import com.projecttwin.handeller.Timer;
import com.projecttwin.utils.Assets;
import com.projecttwin.utils.Constants;
public class HowToStage extends GameState {

	private Player player;
	private Sprite playerwalkSprite;
	private SpriteBatch batch;
	private float trigger;
	private int facing = -1;
	private Animation walkAnimation;
	private Sprite standSprite;
	private int jumping = 1;
	private Animation pullAnimation;
	private Animation pushAnimation;
	private Sprite poweringSprite;
	private Animation poweringAnimation;
	private int powerSwitch = 1;
	private Texture backGround;
	private Timer poweringTimer;
	private Texture backButton;
	protected HowToStage(GameStateManager gsm) {
		super(gsm);
		create();
	}

	@Override
	public void create() {
		Assets.instance.init(new AssetManager());
		player = Assets.instance.getPlayer();
		player.setPowerType(Constants.powerType);
		player.init();
		walkAnimation = player.walkingLeft;
		standSprite = new Sprite(player.standLeft);
		standSprite.setPosition(700, 350);
		pullAnimation = player.pullingLeft;
		pushAnimation = player.pushingLeft;
		poweringAnimation = pullAnimation;
		poweringSprite = new Sprite(pullAnimation.getKeyFrame(0, true));
		poweringSprite.setPosition(830, 270);
		batch = new SpriteBatch();
		playerwalkSprite = new Sprite(player.walkingLeft.getKeyFrame(0, true));
		playerwalkSprite.setPosition(900, 470);
		backGround = new Texture("howto/backGround.png");
		poweringTimer = new Timer(5);
		poweringTimer.start();

		backButton = new Texture(Gdx.files.internal("howto/back.png"));
	}
	
	public void updatePlayerWalk(float deltaTime){
		trigger += deltaTime;
		float distance = 100 * deltaTime;
		player.update(deltaTime);
		float currentPosition = playerwalkSprite.getX();
		if(currentPosition < 670){
			facing  = 1;
			walkAnimation = player.walkingRight;
		}else if(currentPosition > 900){
			facing  = -1;
			walkAnimation = player.walkingLeft;
		}
		playerwalkSprite = new Sprite(walkAnimation.getKeyFrame(trigger, true));
		currentPosition += distance * facing;
		playerwalkSprite.setPosition(currentPosition, 470);
	}
	
	public void updatePlayerJump(float deltaTime){
		float distance = 200 * deltaTime;
		float currentPosition = standSprite.getY();
		if(currentPosition < 370)
			jumping = 1;
		else if(currentPosition > 420)
			jumping = -1;
		currentPosition += (distance * jumping);
		standSprite.setPosition(700, currentPosition);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backGround, 0,0);
		playerwalkSprite.draw(batch);
		standSprite.draw(batch);
		poweringSprite.draw(batch);
		batch.draw(backButton, 930, 5);
		batch.end();
	}
	
	public void updatePlayerPower(float deltaTime){
		if(poweringTimer.hasCompleted()){
			powerSwitch  *= -1;
			poweringTimer.start();
		}
		if(powerSwitch == 1)
			poweringAnimation = pullAnimation;
		else if(powerSwitch == -1)
			poweringAnimation = pushAnimation;
		
		poweringSprite = new Sprite(poweringAnimation.getKeyFrame(trigger, true));
		poweringSprite.setPosition(900, 270);
	}

	@Override
	public void update(float deltaTime) {
		updatePlayerWalk(deltaTime);
		updatePlayerJump(deltaTime);
		updatePlayerPower(deltaTime);
		updateAction();
	}
	
	public void updateAction(){
		Gdx.input.setInputProcessor(new InputAdapter() {
		    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		    	
		        if (button == Buttons.LEFT  &&  screenX > 930 && screenX <  backButton.getWidth() + 930
		        		&& screenY > 5 && screenY < screenY + backButton.getHeight()) {//
		        	gsm.setState(new MenuState(gsm));
		        }
				return false;
		    }
		});
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
	    	gsm.setState(new MenuState(gsm));
		}
	}
	
	
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
