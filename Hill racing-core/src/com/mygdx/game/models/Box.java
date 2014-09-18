package com.mygdx.game.models;

import com.mygdx.game.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Box 
{
	private Body body;
	SpriteBatch batch;
	Sprite boxSprite;
	float hx, hy, w, h;
	GameScreen gameScreen;
	
	public Box(float x, float y, float hx, float hy, GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;
		this.hx=hx;
		this.hy=hy;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
			
		batch=new SpriteBatch();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 10;
		fixDef.restitution = 0.1f;
		fixDef.friction = 10f;
		body = gameScreen.world.createBody(bodyDef);
		body.createFixture(fixDef);		
		body.setTransform(x, y, 90);
		
		boxSprite = new Sprite (new Texture("box.png"));
		
		
		
	}
	
	

	public boolean draw()
	{
		boolean drowed = false;		
		if(body.getPosition().x > gameScreen.camera.position.x-gameScreen.camera.viewportWidth*gameScreen.camera.zoom && body.getPosition().x<gameScreen.camera.position.x+gameScreen.camera.viewportWidth*gameScreen.camera.zoom)
		{			
			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			
			boxSprite.setSize(hx*2*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, hy*2*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom);
			boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
			boxSprite.setPosition((body.getPosition().x-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2-boxSprite.getWidth()/2, (body.getPosition().y-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom+h/2-boxSprite.getHeight()/2);		
			boxSprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees);
			
			
			batch.begin();
				boxSprite.draw(batch);
			batch.end();
			drowed = true;
		}		
		
		return drowed;
	}
}
