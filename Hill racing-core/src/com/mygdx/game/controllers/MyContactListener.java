package com.mygdx.game.controllers;

import com.mygdx.game.screens.GameScreen;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener
{
	GameScreen gameScreen;
	public boolean contactWithChassis;
	public MyContactListener(GameScreen gameScreen)
	{
		this.gameScreen=gameScreen;
		contactWithChassis = false;
	}
	@Override
	public void beginContact(Contact contact) {
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if(fA.getBody() != null && fB.getBody() != null)
		{
			/*for(Brick brick: gameScreen.getGround().getBricks())
			{
				if(fB.equals(gameScreen.getCar().getChassis().getFixtureList().get(0))  && fA.equals(brick.getBody().getFixtureList().get(0))){   
					gameScreen.life-=0.002;
		         }
			}*/
			if(fB.equals(gameScreen.getCar().getChassis().getFixtureList().get(0)))
			{   
				contactWithChassis=true;
	        }
			if(fB.equals(gameScreen.getCar().getLeftWheel().getFixtureList().get(0)))
			{   
				gameScreen.getCar().leftWheelContact = true;
	        }
			if(fB.equals(gameScreen.getCar().getRightWheel().getFixtureList().get(0)))
			{   
				gameScreen.getCar().rightWheelContact = true;
	        }
	      }
	}

	@Override
	public void endContact(Contact contact) 
	{
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if(fA.getBody() != null && fB.getBody() != null)
		{
			/*for(Brick brick: gameScreen.getGround().getBricks())
			{
				if(fB.equals(gameScreen.getCar().getChassis().getFixtureList().get(0))  && fA.equals(brick.getBody().getFixtureList().get(0))){   
					gameScreen.life-=0.002;
		         }
			}*/
			if(fB.equals(gameScreen.getCar().getChassis().getFixtureList().get(0)))
			{   
				contactWithChassis=false;
	        }
			if(fB.equals(gameScreen.getCar().getLeftWheel().getFixtureList().get(0)))
			{   
				gameScreen.getCar().leftWheelContact = false;
	        }
			if(fB.equals(gameScreen.getCar().getRightWheel().getFixtureList().get(0)))
			{   
				gameScreen.getCar().rightWheelContact = false;
	        }
	      }
		
	}

	public void update()
	{
		if(contactWithChassis) gameScreen.getCar().setHealth(gameScreen.getCar().getHealth() - 0.01f);
	}
	
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {}

}
