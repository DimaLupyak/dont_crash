package com.mygdx.game.controllers;


import com.mygdx.game.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameController implements InputProcessor
{
	private GameScreen screen;
	float normalHeight, normalWidth, zoomHeight, zoomWidth;
	boolean zoom = false;
	int zoomN=8;
	
	public GameController(GameScreen screen) 
	{
		this.screen = screen;
		
	}
	@Override
	public boolean keyDown(int keyCode) 
	{
		try {
			switch (keyCode)
			{			
			case Keys.PLUS:
				if(screen.camera.viewportHeight>Gdx.graphics.getHeight()/100 && !zoom)
				{
					normalHeight=screen.camera.viewportHeight*=0.9f;
					normalWidth=screen.camera.viewportWidth*=0.9f;
					zoomHeight = normalHeight*zoomN;
					zoomWidth = normalWidth*zoomN;
				}
				break;
			case Keys.MINUS:
				if(screen.camera.viewportHeight<Gdx.graphics.getHeight() && !zoom)
				{
					normalHeight=screen.camera.viewportHeight*=1.1f;
					normalWidth=screen.camera.viewportWidth*=1.1f;
					zoomHeight = normalHeight*zoomN;
					zoomWidth = normalWidth*zoomN;					
				}
				break;
			case Keys.CONTROL_LEFT:
				screen.debugRend++;
				if(screen.debugRend>2)screen.debugRend=0;
					
				break;
			case Keys.DEL:
			{
				screen.myGame.setScreen(new GameScreen(screen.myGame));
			}
				break;
			
			case Keys.SHIFT_LEFT:
			{
				if(!zoom)
				{
				normalHeight=screen.camera.viewportHeight;
				normalWidth=screen.camera.viewportWidth;
				zoomHeight = normalHeight*zoomN;
				zoomWidth = normalWidth*zoomN;
				screen.camera.viewportHeight = zoomHeight;
				screen.camera.viewportWidth = zoomWidth;
				}
				zoom = true;
			}
				break;
			
			}
		} 
		catch (NullPointerException e){}		
		return false;
	}
	@Override
	public boolean keyTyped(char arg0){return false;}
	@Override
	public boolean keyUp(int keyCode)
	{
		if (keyCode == Keys.SHIFT_LEFT)
		{
			zoom = false;

			screen.camera.viewportHeight = normalHeight;
			screen.camera.viewportWidth = normalWidth;
		}
		return false;
	}
	@Override
	public boolean scrolled(int arg0)
	{
		if(screen.camera.viewportHeight>Gdx.graphics.getHeight()/100 && screen.camera.viewportHeight<Gdx.graphics.getHeight() && !zoom)
		{
			normalHeight=screen.camera.viewportHeight*=1+arg0/10f;
			zoomHeight = normalHeight*zoomN;		
			normalWidth=screen.camera.viewportWidth*=1+arg0/10f;
			zoomWidth = normalWidth*zoomN;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2){return false;}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int button)
	{
		if(button == Buttons.RIGHT)
		{
			zoom = false;

			screen.camera.viewportHeight = normalHeight;
			screen.camera.viewportWidth = normalWidth;
		}
		
		
		return false;
		}
	
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int button) 
	{
		if(button == Buttons.RIGHT)
		{
			zoom = true;
			normalHeight=screen.camera.viewportHeight;
			normalWidth=screen.camera.viewportWidth;
			zoomHeight = normalHeight*zoomN;
			zoomWidth = normalWidth*zoomN;
			screen.camera.viewportHeight = zoomHeight;
			screen.camera.viewportWidth = zoomWidth;
		}
		return false;
	}
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Автоматически созданная заглушка метода
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
