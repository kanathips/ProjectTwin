package com.projecttwin.handeller;

import com.badlogic.gdx.math.Vector2;
import com.projecttwin.character.Player;
import com.projecttwin.character.Player.State;
import com.projecttwin.game.WorldController;
import com.projecttwin.game.WorldPhysic;
import com.projecttwin.utils.Constants;

public class CharacterControll extends WorldController{
	
	//move player character
	public static void walk(float walkSpeed){
		if(getPlayerState() == State.STANDING || getPlayerState() == State.WALKING )
			getPlayer().setState(State.WALKING);
		walkSpeed -= WorldPhysic.playerBody.getLinearVelocity().x;
		WorldPhysic.playerBody.applyLinearImpulse(new Vector2(walkSpeed, 0), WorldPhysic.playerBody.getWorldCenter(), true);		
	}
	
	public static void setPlayerFacingLeft(boolean isFacingLeft){
		getPlayer().setFacingLeft(isFacingLeft);
	}
	
	public static boolean getPlayerFacingLeft(){
		return Player.getFacingLeft();
	}

	public static void updateBox(){
		for(int i = 0; i < getBoxSprites().length; i++){
			getBoxSprites()[i].setPosition(Constants.metersToPixels(WorldPhysic.boxBodys[i].getPosition().x) - getBoxSprites()[i].getWidth() / 2 
					, Constants.metersToPixels(WorldPhysic.boxBodys[i].getPosition().y) - getBoxSprites()[i].getHeight() / 2);
			getBoxSprites()[i].setRotation((float)Math.toDegrees(WorldPhysic.boxBodys[i].getAngle()));
		}
	}
	
	/**
	 * Use to update player animation position reference by player physic body
	 * @param deltaTime
	 */
	public static void updatePlayer(float deltaTime){
		getPlayer().update(deltaTime);
		getPlayerSprite().setSize(getPlayer().playerFrame.getRegionWidth(), getPlayer().playerFrame.getRegionHeight());
		getPlayerSprite().setRegion(getPlayer().playerFrame);
		getPlayerSprite().setPosition((Constants.metersToPixels(WorldPhysic.playerBody.getPosition().x) - startPlayerWidth / 2),
					(Constants.metersToPixels(WorldPhysic.playerBody.getPosition().y) - startPlayerHeigth / 2));
		if(Player.getFacingLeft())
			getPlayerSprite().translate(startPlayerWidth - getPlayerSprite().getWidth(), 0);
	}
	
	
	public static void resetplayer(boolean isX){
		getPlayer().setTrigger(0);
		if(isX){
			WorldPhysic.playerBody.setLinearVelocity(0, WorldPhysic.playerBody.getLinearVelocity().y);
			getPlayer().setState(State.STANDING);
		}
		else{
			WorldPhysic.playerBody.setLinearVelocity(WorldPhysic.playerBody.getLinearVelocity().x, 0);
		}
	}
	
	/**
	 * This method is use to control player physic body to jump
	 * @param jumpSpeed
	 */
	public static void jump(float jumpSpeed){
		if(getPlayer().getAtGround()){
			getPlayer().setState(State.JUMPING);
			WorldPhysic.playerBody.setLinearVelocity(new Vector2(WorldPhysic.playerBody.getLinearVelocity().x, jumpSpeed * 2));
		}
	}
	
	public static State getPlayerState(){
		return getPlayer().getState();
	}
}
