package com.projecttwin.gameState;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projecttwin.game.ProjectTwin;
import com.projecttwin.utils.Constants;

public class StartGame extends GameState {

	public float CheckStateEnter;
	private Texture bg;
	private ArrayList<Sprite> arr_sp;
	private float logo_alpha = 0, bt_alpha = 0, light_alpha = 0;

	protected StartGame(GameStateManager gsm) {
		super(gsm);
		CheckStateEnter = 0;
		create();
	}

	@Override
	public void create() {

		bg = new Texture(Gdx.files.internal("Decoration/bg2.png"));
		arr_sp = new ArrayList<Sprite>();
		for (int i = 1; i <= 6; i++)
			arr_sp.add(new Sprite(new Texture(Gdx.files.internal("Decoration/d" + i + ".png"))));

	}

	private void renderSprite(Sprite sprite, SpriteBatch spriteBatch, float rotate, float x, float y, float scalX,
			float scalY) {
		sprite.setBounds(x, y, scalX, scalY);
		sprite.rotate(rotate);
		sprite.draw(spriteBatch);
	}

	@Override
	public void render() {

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		spriteBatch.draw(bg, 0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

		light_alpha += 0.5; // get(2) - light
		renderSprite(arr_sp.get(2), spriteBatch, light_alpha, Constants.MAP_WIDTH / 8f, Constants.MAP_HEIGHT / 3f,
				Constants.MAP_WIDTH / 2f + 200, Constants.MAP_HEIGHT);

		if (light_alpha >= 130f) {
			arr_sp.get(2).setAlpha(0); // get(3) - round
			for (int i = 3; i <= 5; i++)
				arr_sp.get(i).setOrigin(arr_sp.get(i).getWidth() / 2f, arr_sp.get(i).getHeight() / 2f);
			renderSprite(arr_sp.get(5), spriteBatch, 20f, Constants.MAP_WIDTH / 8f + 5, Constants.MAP_HEIGHT / 11f,
					arr_sp.get(3).getWidth(), arr_sp.get(3).getHeight());
			renderSprite(arr_sp.get(4), spriteBatch, 2f, 0 - arr_sp.get(3).getWidth() / 2f,
					0 - arr_sp.get(3).getHeight() / 2f, arr_sp.get(3).getWidth(), arr_sp.get(3).getHeight());
			renderSprite(arr_sp.get(3), spriteBatch, 5f, Constants.VIEWPORT_WIDTH - arr_sp.get(3).getWidth() / 2f,
					Constants.VIEWPORT_HEIGHT - arr_sp.get(3).getHeight() / 2f, arr_sp.get(3).getWidth(),
					arr_sp.get(3).getHeight());
		}

		if (logo_alpha < 1)
			logo_alpha += 0.005;
		else if (logo_alpha >= 1)
			logo_alpha = 1;
		arr_sp.get(0).setAlpha(logo_alpha); // get(0) - logo
		renderSprite(arr_sp.get(0), spriteBatch, 0, Constants.VIEWPORT_WIDTH / 9f, Constants.VIEWPORT_HEIGHT / 2.5f,
				Constants.MAP_WIDTH / 2.5f, Constants.MAP_HEIGHT / 2.5f);

		if (bt_alpha < 1 && logo_alpha == 1)
			bt_alpha += 0.02;
		else if (bt_alpha >= 1 && logo_alpha == 1) {
			bt_alpha = 0;
		}
		arr_sp.get(1).setAlpha(bt_alpha); // get(1) - Start game
		renderSprite(arr_sp.get(1), spriteBatch, 0, Constants.VIEWPORT_WIDTH / 2.5f, Constants.VIEWPORT_HEIGHT / 4.5f,
				arr_sp.get(1).getWidth(), arr_sp.get(1).getHeight());

		spriteBatch.end();

		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			gsm.setState(new ProjectTwin(gsm));
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
		spriteBatch.dispose();
	}

}
