package com.projecttwin.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.projecttwin.game.ProjectTwin;
<<<<<<< HEAD
import com.projecttwin.utils.Constants;
=======
>>>>>>> origin/master


public class SelectStage extends GameState{
	
	private Array<Sprite> arr_select = new Array<Sprite>();
	private float index_x=0f;
	private float alpha_star=0f;
	private int clear_stage=1;
<<<<<<< HEAD
	public static int stage;
	
=======
	private int count_star=0;
	public static int stage;
	
	
>>>>>>> origin/master
	protected SelectStage(GameStateManager gsm) {
		super(gsm);
		create();
		
	}

	@Override
	public void create() {
<<<<<<< HEAD
		for(int i = 0;i <= 11;i++)	arr_select.add(new Sprite(new Texture(Gdx.files.internal("selectStage/stage"+(i+1)+".png"))));
=======
		for(int i = 0;i <= 9;i++)	arr_select.add(new Sprite(new Texture(Gdx.files.internal("selectStage/stage"+(i+1)+".png"))));
>>>>>>> origin/master
		
		for(int i = 0;i <= 4;i++){
			arr_select.get(i).setBounds(index_x, 0, arr_select.get(1).getWidth(), arr_select.get(1).getHeight());
			index_x += arr_select.get(1).getWidth();
		}
		
<<<<<<< HEAD
		arr_select.get(5).setBounds(0, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
		arr_select.get(7).setBounds(arr_select.get(1).getWidth()*3, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
		arr_select.get(8).setBounds(arr_select.get(1).getWidth()*4, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
=======
>>>>>>> origin/master
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();

		for(int i=0;i<=4;i++)	arr_select.get(i).draw(spriteBatch);
		
		for(int i=1;i<=clear_stage;i++){
			
			if(location_cursor.x > 205*(i-1) &&location_cursor.x <= 205*i){
				alpha_star = 1;
				arr_select.get(9).setBounds(205*(i-1), 0, arr_select.get(0).getWidth(), arr_select.get(0).getHeight());
				arr_select.get(9).setAlpha(alpha_star);
				arr_select.get(9).draw(spriteBatch);
				stage = i;
				
				Gdx.input.setInputProcessor(new InputAdapter() {
				    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				        if (button == Buttons.LEFT && alpha_star == 1) {
				        	gsm.setState(new ProjectTwin(gsm,SelectStage.stage));
				        }
						return false;
				    }
				});
				
			}
			else{
				alpha_star = 0f;
				arr_select.get(9).setAlpha(alpha_star);
			}
				
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))		
			gsm.setState(new MenuState(gsm));
		
<<<<<<< HEAD
		
		spriteBatch.end();
		
		index_x = arr_select.get(0).getWidth()*(clear_stage);
		for(int i = 5;i>clear_stage;i--){
			arr_select.get(11).setPosition(index_x, 0);
			arr_select.get(10).setBounds(index_x+30, Constants.VIEWPORT_HEIGHT/2.5f, 150, 150);
			spriteBatch.begin();
			arr_select.get(10).draw(spriteBatch);
			arr_select.get(11).draw(spriteBatch);
			spriteBatch.end();
			index_x+=arr_select.get(0).getWidth();
		}
		
		index_x = arr_select.get(0).getWidth();
		for(int i = 1;i<=2;i++){
			arr_select.get(6).setPosition(index_x, 0);
			spriteBatch.begin();
			for(int j = 5;j<=8;j++)
				arr_select.get(j).draw(spriteBatch);
			spriteBatch.end();
			index_x+=arr_select.get(0).getWidth();
		}
			
=======
			
		spriteBatch.end();
		
		for(int i=5;i<=8;i++){
			index_x = 0;
			for(int j = 1;j<=5;j++){
				arr_select.get(i).setPosition(index_x, 0);
				index_x+=arr_select.get(0).getWidth();
				spriteBatch.begin();
				arr_select.get(i).draw(spriteBatch);
				spriteBatch.end();
			}
			if(i >= 6 )	arr_select.get(i).setAlpha(0);
//			if((i >= 7 || i == 5) && count_star == 1)	arr_select.get(i).setAlpha(0);
//			if((i == 8 || i <= 6)&& count_star == 2)	arr_select.get(i).setAlpha(0);
//			if(i <= 7 && count_star == 3)	arr_select.get(i).setAlpha(0);
			
		}
>>>>>>> origin/master
	}

	@Override
	public void update(float deltaTime) {
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void dispose() {

		
	}

}
