package com.projecttwin.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecttwin.utils.Constants;

public class Box {
	public TextureRegion boxTextute;
	
	public Box(TextureAtlas atlas){
		try{
			boxTextute = atlas.findRegion("Box");
			Gdx.app.debug(Constants.no + " Box", "Create Box Complete");
			Constants.no++;
		}catch(Exception e){
			Gdx.app.debug(Constants.no + " Box", "Create Box Error");
			Constants.no++;		
		}
	}
}
