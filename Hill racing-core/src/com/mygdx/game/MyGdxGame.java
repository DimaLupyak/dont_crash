package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.ScreenTwo;
import com.mygdx.game.screens.ShaderScreen;

public class MyGdxGame extends Game 
{
	@Override
	public void create () 
	{
		setScreen(new ScreenTwo(this));
	}
}

