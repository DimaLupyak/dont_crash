package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.screens.GameScreen;

public class Player extends Sprite implements InputProcessor
{
	public final static float width = 20;
	public final static float height = 30;
	private Body body;
	SpriteBatch spriteBatch;
	GameScreen gameScreen;
	float h, w;
	//private ParticleEffect tailFireEffect;
	
	public Player(float x, float y, GameScreen gameScreen)
	{		
		super(new Texture("badlogic.jpg"));
		this.gameScreen=gameScreen;
		h=Gdx.graphics.getHeight();
		w=Gdx.graphics.getWidth();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		bodyDef.position.set(x, y);
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 10;
		fixDef.restitution = 0.1f;
		fixDef.friction = 10f;
		body = gameScreen.world.createBody(bodyDef);
		body.createFixture(fixDef);		
		//body.setTransform(x, y, 90);
		
		spriteBatch = new SpriteBatch();
		
		//tailFireEffect = new ParticleEffect();
		//tailFireEffect.load(Gdx.files.internal("p.p"), Gdx.files.internal("."));
		//tailFireEffect.setPosition(x+getWidth()/2, y+getHeight());				
	}
	
	public void update(float delta) 
	{
		//tailFireEffect.start();
		//tailFireEffect.update(delta);
		setSize(width*2/w*gameScreen.camera.viewportWidth, height*2/h*gameScreen.camera.viewportHeight);
		setPosition(body.getPosition().x+w/2-getWidth()/2, body.getPosition().y+h/2-getHeight()/2);
		spriteBatch.begin();
			//tailFireEffect.draw(spriteBatch);			
			this.draw(spriteBatch);
		spriteBatch.end();
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) 
	{		
		return false;
	}

	@Override
	public boolean keyTyped(char character) 
	{		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		screenY-=h/2;
		screenY*=-1;
		screenX-=w/2;
		body.applyForceToCenter(1000000*screenX, 1000000*screenY, true);
		System.out.println(""+screenX+" - "+screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) 
	{	
		return false;
	}

	@Override
	public boolean scrolled(int amount) 
	{		
		return false;
	}

}
