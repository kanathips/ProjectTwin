package com.projecttwin.gameState;

public class ConcludeScore extends GameState{

	private int score;
	
	protected ConcludeScore(GameStateManager gsm, int score) {
		super(gsm);
		this.score = score;
		
		
	}

	@Override
	public void create() {
		
		
	}

	@Override
	public void render() {
		
		
	}

	@Override
	public void update(float deltaTime) {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {}

}
