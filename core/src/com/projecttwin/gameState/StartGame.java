package com.projecttwin.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.utils.Constants;



public class StartGame extends GameState implements EventListener{
	
	private boolean signal_load;
	private float deltaTime;
	
	private Include_gameState include_gameState;
	
	private Texture background;
	private TextureRegion current_start;
	private TextureRegion current_load;
	private TextureAtlas atlas;
	private Animation start;
	private Animation load;

	private Animation effect_ligh;
	private TextureAtlas atlas_light;
	
	private float alpha_lo = 0f,timer_load=0f;;
	private Sprite sprite_logo;
	private Sprite bg_load;
	private Array<Sprite> sprite_circle;
	
	protected StartGame(GameStateManager gsm,boolean signal_load) {
		super(gsm);
		this.signal_load = signal_load;
		create();
	}

	@Override
	public void create() {
		
		include_gameState = new Include_gameState(gsm, spriteBatch, location_cursor, signal_load, camera);
		include_gameState.create();
		
		
		atlas = new TextureAtlas("Decoration/pack/start_menu.atlas");
		sprite_circle = new Array<Sprite>();
		
		background = new Texture(Gdx.files.internal("Decoration/bg4.png"));
		bg_load = new Sprite(new Texture("Decoration/pack/load.png"));
		bg_load.setBounds(80,Constants.VIEWPORT_HEIGHT/2.2f, bg_load.getWidth(), bg_load.getHeight());
		
		TextureRegion[] loading = new TextureRegion[17];
		for(int i = 0; i <= 16 ;i++)		loading[i] = atlas.findRegion("load ("+(i+1)+")");
		load = new Animation(0.05f, loading);
		
		atlas_light = new TextureAtlas(Gdx.files.internal("effect_menu/eff_menus.atlas"));
		TextureRegion[] lightFrame = new TextureRegion[8];
		for(int i=0; i<=7 ; i++)	lightFrame[i] = atlas_light.findRegion("light ("+(i+1)+")");
		effect_ligh = new Animation(0.2f, lightFrame);
		effect_ligh.setPlayMode(PlayMode.NORMAL);
		
		//----------------------------------------------------
		
		sprite_logo = new Sprite(atlas.findRegion("logo"));
		sprite_logo.setBounds(Constants.VIEWPORT_WIDTH/12f,Constants.VIEWPORT_HEIGHT/5f,sprite_logo.getWidth()+250,sprite_logo.getHeight()/2f+150);
		
		TextureRegion[] startFrame = new TextureRegion[2];
		for(int i = 0; i <= 1 ;i++)		startFrame[i] = atlas.findRegion("start"+i);
		start = new Animation(0.5f, startFrame);
		
		sprite_circle.add(new Sprite(atlas.findRegion("d1")));
		sprite_circle.add(new Sprite(atlas.findRegion("d3")));
		
		sprite_circle.get(0).setBounds(0-sprite_circle.get(0).getWidth()/2.3f,0-sprite_circle.get(0).getHeight()/2.3f,
				sprite_circle.get(0).getWidth()/1.2f,sprite_circle.get(0).getHeight()/1.2f);
		sprite_circle.get(0).setOriginCenter();
		
		sprite_circle.get(1).setBounds(Constants.VIEWPORT_WIDTH-sprite_circle.get(1).getWidth()/2.9f,Constants.VIEWPORT_HEIGHT-sprite_circle.get(1).getHeight()/2.9f,
				sprite_circle.get(1).getWidth()/1.2f,sprite_circle.get(1).getHeight()/1.2f);
		sprite_circle.get(1).setOriginCenter();
		
	}
	

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime += Gdx.graphics.getDeltaTime();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		

		
		
		spriteBatch.begin();
		
//		include_gameState.renderStartGame(deltaTime);
//		include_gameState.renderMenuState(deltaTime);
//		include_gameState.renderSelectStage();
		
//		if(signal_load){
//			spriteBatch.draw(effect_ligh.getKeyFrame(deltaTime,true), 0,Constants.VIEWPORT_HEIGHT/2.5f,1024,250);
//			bg_load.draw(spriteBatch);
//			current_load = load.getKeyFrame(deltaTime,true);	
//			spriteBatch.draw(current_load,Constants.VIEWPORT_WIDTH-current_load.getRegionWidth(),0,
//					current_load.getRegionWidth()*1.2f,current_load.getRegionHeight()*1.2f);
//			timer_load += 0.005;
//		}
//		
//		if(timer_load >= 1 || signal_load == false){
//			bg_load.setAlpha(0);
//			spriteBatch.draw(background, 0,0,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
//				
//			alpha_lo+= 0.005;
//			if(alpha_lo >= 1)	alpha_lo = 1;
//			sprite_logo.setAlpha(alpha_lo);
//			sprite_logo.draw(spriteBatch);
//				
//				
//			current_start = start.getKeyFrame(deltaTime,true);
//			if(alpha_lo == 1)					
//			spriteBatch.draw(current_start,Constants.VIEWPORT_WIDTH/1.5f,0,	//Constants.VIEWPORT_HEIGHT/8f
//					current_start.getRegionWidth()/1.6f,current_start.getRegionHeight()/1.6f);
//				
//			sprite_circle.get(0).rotate(10);
//			sprite_circle.get(0).draw(spriteBatch);
//			
//			sprite_circle.get(1).rotate(5);
//			sprite_circle.get(1).draw(spriteBatch);
//				
//			timer_load=1;
//		}
//		
		Gdx.input.setInputProcessor(new InputAdapter() {
		    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		        if (button == Buttons.LEFT) {//&& alpha_lo == 1
		        	gsm.setState(new MenuState(gsm));
		        }
				return false;
		    }
		});
		
		
		spriteBatch.end();
		
		
	}
	
	
	@Override
	public void update(float deltaTime) {}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {	}

	@Override
	public boolean handle(Event event) {
		return false;
	}

	

}
