package com.projecttwin.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.projecttwin.handeller.ContactHandler;
import com.projecttwin.handeller.InputHandler;
import com.projecttwin.utils.Constants;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class MenuScreen extends Game implements ApplicationListener{
	
	private WorldRender worldRender;
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private RayHandler rayHandler;
	private World world;
	private OrthographicCamera camera;
	private PointLight point;
	
	public MenuScreen(WorldRender worldRender){
		this.worldRender=worldRender;
		
		create();
		
	}

	public MenuScreen(){
		create();
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera(25f, 25f);
		camera.update();
		world = new World(new Vector2(0, -10),  true);
		rayHandler = new RayHandler(world);
		point = new PointLight(rayHandler, 16);
		point.setColor(Color.MAROON);
		
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton button = new TextButton("Start Game", skin, "default");
        
        button.setColor(Color.CYAN);
        button.setBounds(Constants.MAP_WIDTH/2, Constants.MAP_HEIGHT/2, 200, 50);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);
        
        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                //button.setText("You clicked the button");

        		System.out.println("You clicked the button");
        		setScreen(worldRender);
                dispose();
            } 
        });
        
        stage.addActor(button);
  
        Gdx.input.setInputProcessor(stage);
  
        
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	    // And here is stage acts...
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		
		rayHandler.updateAndRender();
		rayHandler.setCombinedMatrix(camera);
		
	    batch.begin();
        stage.draw();
        batch.end();

        
        super.render();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}


}
