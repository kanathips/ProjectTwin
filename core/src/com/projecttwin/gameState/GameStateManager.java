package com.projecttwin.gameState;

import java.util.Stack;


public class GameStateManager{
	
	private GameCore game;
	
	private Stack<GameState> gameStates;
	
//	public static final int PLAY = 200000;
//	public static final int STARTGAME = 100000;
	
	public GameStateManager(GameCore game) {
		this.game = game;
		gameStates = new Stack<GameState>();
//		pushState(PLAY);
	}
	
	public GameCore gameCore() { return game; }
	
	public void update(float deltaTime) {
		gameStates.peek().update(deltaTime);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
//	private GameState getState(int state) {
//		if(state == STARTGAME) return new StartGame(this);
//		return null;
//	}
	
	public void setState(GameState state) {
		popState();
		pushState(state);
	}
	
	public void pushState(GameState state) {
		gameStates.push(state);
	}
	
	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
	
}















