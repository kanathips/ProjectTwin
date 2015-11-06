package com.projecttwin.gameState;

import java.util.Stack;


public class GameStateManager {
	
	private GameCore game;
	
	private Stack<GameState> gameStates;
	
	public GameStateManager(GameCore game) {
		this.game = game;
		gameStates = new Stack<GameState>();	}
	
	public GameCore gameCore() { return game; }
	
	public void update(float deltaTime) {
		gameStates.peek().update(deltaTime);
	}
	
	public void render() {
		gameStates.peek().render();
	}

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















