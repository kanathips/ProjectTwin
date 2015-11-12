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
import com.projecttwin.utils.Constants;
import com.projecttwin.utils.XmlLoader;


public class SelectStage extends GameState{
	
	private Array<Sprite> arr_select = new Array<Sprite>();
	private float index_x=0f;
	private float alpha_stage=0f;
	private int clear_stage=1;
	private float alpha_stageOut;
	public static int stage;
	
	protected SelectStage(GameStateManager gsm) {
		super(gsm);
		create();
		
	}

	@Override
	public void create() {
		XmlLoader xmlLoader = new XmlLoader("database.xml", "level");
		for(int i = 5; i > 0; i--){
			if(xmlLoader.getData(i, "unlock").equals("true")){
				clear_stage = i;
				break;
			}
		}
		for(int i = 0;i <= 11;i++)	arr_select.add(new Sprite(new Texture(Gdx.files.internal("selectStage/stage"+(i+1)+".png"))));
		
		for(int i = 0;i <= 4;i++){
			arr_select.get(i).setBounds(index_x, 0, arr_select.get(1).getWidth(), arr_select.get(1).getHeight());
			index_x += arr_select.get(1).getWidth();
		}
		
		arr_select.get(5).setBounds(0, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
		arr_select.get(7).setBounds(arr_select.get(1).getWidth()*3, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
		arr_select.get(8).setBounds(arr_select.get(1).getWidth()*4, 0, arr_select.get(5).getWidth(), arr_select.get(5).getHeight());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();

		for(int i=0;i<=4;i++)	arr_select.get(i).draw(spriteBatch);
		
		for(int i=1;i<=clear_stage;i++){
			
			if(location_cursor.x > 205*(i-1) &&location_cursor.x <= 205*i){
				alpha_stage = 1;
				arr_select.get(9).setBounds(205*(i-1), 0, arr_select.get(0).getWidth(), arr_select.get(0).getHeight());
				arr_select.get(9).setAlpha(alpha_stage);
				arr_select.get(9).draw(spriteBatch);
				stage = i;
				
				Gdx.input.setInputProcessor(new InputAdapter() {
				    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				        if (button == Buttons.LEFT && alpha_stage == 1) {
				        	gsm.setState(new ProjectTwin(gsm,SelectStage.stage));
				        }
						return false;
				    }
				});
				
			}
				alpha_stageOut = 0f;
				arr_select.get(9).setAlpha(alpha_stageOut);
				
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))		
			gsm.setState(new MenuState(gsm));
		
		
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
