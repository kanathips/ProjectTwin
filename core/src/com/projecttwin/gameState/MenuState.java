package com.projecttwin.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.utils.Constants;

public class MenuState extends GameState{
	
	private Sprite sprite_eff;
	private Animation effect;
	private TextureAtlas atlas_eff;
	private Array<Sprite> cage = new Array<Sprite>();
	private float alpha_st=0f, alpha_hw=0f, alpha_hi=0f, alpha_cr=0f;
	private float runy_eff = Constants.VIEWPORT_HEIGHT;
	private float deltaTime_eff=0f;
	private boolean change_eff=false;
	
	protected MenuState(GameStateManager gsm) {
		super(gsm);
		create();
	}

	@Override
	public void create() {
		
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
	
	private void setRender(int index_ar1,int index_ar2,float alpha){
		cage.get(index_ar1).setAlpha(alpha);
		cage.get(index_ar1).draw(spriteBatch);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime_eff += Gdx.graphics.getDeltaTime();
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		
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
		
		setRender(1,0,alpha_st);
		setRender(3,2,alpha_hw);
		setRender(5,4,alpha_hi);
		setRender(7,6,alpha_cr);
		
		Gdx.input.setInputProcessor(new InputAdapter() {
		    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		        if (button == Buttons.LEFT&& alpha_st == 1) {//
		        	gsm.setState(new SelectStage(gsm));
		        }
				return false;
		    }
		});
		
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			gsm.setState(new StartGame(gsm,false));
		
		spriteBatch.end();
		
	}
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
		
	}

	

}
