package com.projecttwin.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.game.ProjectTwin;
import com.projecttwin.utils.Constants;

public class Include_gameState extends GameState{
	//---------------------include gameState---------------
	private boolean start_to_menu = true;
	private boolean menu_to_selectStage = true;
	
	//----------------------core game-----------------------
	
	private SpriteBatch spriteBatch;
	private Vector2 location_cursor = new Vector2(0,0);
	private OrthographicCamera camera;
	
	//----------------------start game-----------------------
	
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
	private boolean signal_load;
	
	
	//-------------------------menu---------------------------
	
	private Sprite sprite_eff;
	private Animation effect;
	private TextureAtlas atlas_eff;
	private Array<Sprite> cage = new Array<Sprite>();
	private float alpha_st=0f, alpha_hw=0f, alpha_hi=0f, alpha_cr=0f;
	private float runy_eff = Constants.VIEWPORT_HEIGHT;
	private float deltaTime_eff=0f;
	private boolean change_eff=false;
	
	//------------------------SelectStage----------------------
	
	private Array<Sprite> arr_select = new Array<Sprite>();
	private float index_x=0f;
	
	protected Include_gameState(GameStateManager gsm,SpriteBatch spriteBatch,Vector2 location_cursor,boolean signal_load,OrthographicCamera camera){
		super(gsm);
		this.spriteBatch = spriteBatch;
		this.location_cursor = location_cursor;
		this.signal_load = signal_load;
		this.camera = camera;
	}
	
	
	public void createStartGame(){
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
	public void renderStartGame(float deltaTime,boolean check){
		if(start_to_menu || check == true){
			if(signal_load){
				spriteBatch.draw(effect_ligh.getKeyFrame(deltaTime,true), 0,Constants.VIEWPORT_HEIGHT/2.5f,1024,250);
				bg_load.draw(spriteBatch);
				current_load = load.getKeyFrame(deltaTime,true);	
				spriteBatch.draw(current_load,Constants.VIEWPORT_WIDTH-current_load.getRegionWidth(),0,
						current_load.getRegionWidth()*1.2f,current_load.getRegionHeight()*1.2f);
				timer_load += 0.005;
			}
			
			if(timer_load >= 1 || signal_load == false){
				bg_load.setAlpha(0);
				spriteBatch.draw(background, 0,0,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
				
				alpha_lo+= 0.005;
				if(alpha_lo >= 1)	alpha_lo = 1;
				sprite_logo.setAlpha(alpha_lo);
				sprite_logo.draw(spriteBatch);
				
				
				current_start = start.getKeyFrame(deltaTime,true);
				if(alpha_lo == 1)					
				spriteBatch.draw(current_start,Constants.VIEWPORT_WIDTH/1.5f,0,	//Constants.VIEWPORT_HEIGHT/8f
						current_start.getRegionWidth()/1.6f,current_start.getRegionHeight()/1.6f);
				
				sprite_circle.get(0).rotate(10);
				sprite_circle.get(0).draw(spriteBatch);
			
				sprite_circle.get(1).rotate(5);
				sprite_circle.get(1).draw(spriteBatch);
				
				timer_load=1;
			}
			
			
		}
		if((Gdx.input.justTouched() && alpha_lo == 1) || start_to_menu == false){ 
			start_to_menu = false;
			renderMenuState(deltaTime,false);
						
		}
		
	}
	
	public void createMenuState(){
		atlas_eff = new TextureAtlas(Gdx.files.internal("effect_menu/eff_menu.atlas"));
		TextureRegion[] effFrame = new TextureRegion[7];
		for(int i=0; i<=6 ; i++)	effFrame[i] = atlas_eff.findRegion("effs ("+(i+1)+")");
		effect = new Animation(0.2f, effFrame);
		effect.setPlayMode(PlayMode.NORMAL);
		
		sprite_eff = new Sprite(new Texture(Gdx.files.internal("effect_menu/effs (1).png")));
		
		for(int i=0;i<=7;i++){
			cage.add(new Sprite(new Texture(Gdx.files.internal("menu_state/m"+i+".png"))));
		}
			
		for(int i = 0;i<=3;i++){
			cage.get(i*2).setBounds(Constants.VIEWPORT_WIDTH-cage.get(0).getWidth()*1.62f, Constants.VIEWPORT_HEIGHT-cage.get(0).getHeight()*(i+1), 
					cage.get(0).getWidth(), cage.get(0).getHeight());
		}
		for(int i = 1;i<=4;i++){
			cage.get((i*2)-1).setBounds(Constants.VIEWPORT_WIDTH-cage.get(0).getWidth()*1.62f, Constants.VIEWPORT_HEIGHT-cage.get(0).getHeight()*i, 
					cage.get(0).getWidth(), cage.get(0).getHeight());
			cage.get((i*2)-1).setAlpha(0);
		}
	}
	public void setRenderMenu(int index_ar1,int index_ar2,float alpha){
		cage.get(index_ar1).setAlpha(alpha);
		cage.get(index_ar1).draw(spriteBatch);
	}
	public void renderMenuState(float deltaTime,boolean check){
		if(start_to_menu == false && menu_to_selectStage == true || check == true){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			deltaTime_eff += Gdx.graphics.getDeltaTime();
			
			
			if(change_eff == false )
				sprite_eff.setBounds(0, runy_eff, sprite_eff.getWidth(), sprite_eff.getHeight());
			else 
				sprite_eff.setBounds(Constants.VIEWPORT_WIDTH-sprite_eff.getWidth()/1.2f, runy_eff, sprite_eff.getWidth(), sprite_eff.getHeight());
			
			sprite_eff.draw(spriteBatch);
			runy_eff-=15;
			
			if(runy_eff <= 0){
				sprite_eff.setAlpha(0);
				if(deltaTime_eff >= 1.5) deltaTime_eff = 0.7f;
				if(change_eff == false)
					spriteBatch.draw(effect.getKeyFrame(deltaTime_eff,false), 0,0);
				else 
					spriteBatch.draw(effect.getKeyFrame(deltaTime_eff,false), Constants.VIEWPORT_WIDTH-sprite_eff.getWidth()/1.2f,0);
				
				if(effect.isAnimationFinished(deltaTime_eff)){
					runy_eff = Constants.VIEWPORT_HEIGHT;
					sprite_eff.setAlpha(1);
					deltaTime_eff = 0f;
					change_eff = !change_eff;
				}
			}
			
			
			for(int i = 0;i<=3;i++)
				cage.get(i*2).draw(spriteBatch);
			
			//-------------------------------------------------------------------
			
			if(location_cursor.x >= cage.get(0).getWidth()/1.5f && location_cursor.x <= Constants.VIEWPORT_WIDTH-cage.get(0).getWidth()/1.5f)
			{
				alpha_st = (location_cursor.y <= cage.get(1).getHeight())? 1 : 0;
				alpha_hw = (location_cursor.y > cage.get(1).getHeight() && location_cursor.y <= cage.get(1).getHeight()*2) ? 1 : 0;
				alpha_hi = (location_cursor.y > cage.get(1).getHeight()*2 && location_cursor.y <= cage.get(1).getHeight()*3) ? 1 : 0;
				alpha_cr = (location_cursor.y > cage.get(1).getHeight()*3 && location_cursor.y <= cage.get(1).getHeight()*4) ? 1 : 0;
			}
			else
			{
				alpha_st = 0;	alpha_hw = 0;	alpha_hi = 0;	alpha_cr = 0;
			}
			
			setRenderMenu(1,0,alpha_st);
			setRenderMenu(3,2,alpha_hw);
			setRenderMenu(5,4,alpha_hi);
			setRenderMenu(7,6,alpha_cr);
			
			
		}
		if(alpha_st == 1 && Gdx.input.isButtonPressed(Input.Buttons.LEFT) || menu_to_selectStage == false){
			menu_to_selectStage = false;
			renderSelectStage(false);
		}

		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			start_to_menu = true;
			renderStartGame(deltaTime,false);
		}
	}
	
	public void createSelectStage(){
		for(int i = 0;i <= 5;i++)	arr_select.add(new Sprite(new Texture(Gdx.files.internal("selectStage/stage"+(i+1)+".png"))));
		
		for(int i = 0;i <= 4 ;i++)	{
			arr_select.get(i).setBounds(index_x, 0, arr_select.get(0).getWidth(), arr_select.get(0).getHeight());
			index_x += arr_select.get(0).getWidth();
		}
	}
	public void renderSelectStage(boolean check){
		if(menu_to_selectStage == false || check == true){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			for(int i=0;i<=4;i++)	arr_select.get(i).draw(spriteBatch);
			
			for(int i=1;i<=5;i++){
				
				if(location_cursor.x > 205*(i-1) &&location_cursor.x <= 205*i){
					arr_select.get(5).setBounds(205*(i-1), 0, arr_select.get(0).getWidth(), arr_select.get(0).getHeight());
					arr_select.get(5).setAlpha(1);
					arr_select.get(5).draw(spriteBatch);
					
					if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
						if(i==1)	gsm.setState(new ProjectTwin(gsm));
					}
				}
				else
					arr_select.get(5).setAlpha(0);
			}
		}
	}

	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		createStartGame();
		createMenuState();
		createSelectStage();
		
	}

	@Override
	public void render() {}

	@Override
	public void update(float deltaTime) {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {}
	
}
