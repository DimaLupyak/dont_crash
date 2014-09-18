package com.mygdx.game.models;

import java.util.Random;

import com.mygdx.game.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class Tree 
{
	private Body body, bole;
	private WheelJoint joint;
	SpriteBatch batch;
	Sprite boxSprite;
	Sprite boleSprite;
	float hx, hy, w, h;
	GameScreen gameScreen;
	
	public Tree(float x, float y, float hx, float hy, GameScreen gameScreen)
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
		shape.set(new Vector2[]{
				new Vector2(-hx/2, -hy/2f),
				new Vector2(+hx/2, -hy/2f),
				new Vector2(0.1f, hy/2f),
				new Vector2(0, hy/2f),
				});
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 10;
		fixDef.restitution = 0.1f;
		fixDef.friction = 10f;
		body = gameScreen.world.createBody(bodyDef);
		body.createFixture(fixDef);		
		body.setTransform(x, y, 0);
		Random rand = new Random();
		boxSprite = new Sprite (new Texture("tree"+(rand.nextInt(3)+1)+".png"));	
		boleSprite = new Sprite(new Texture("box.png"));
		bodyDef.type = BodyType.StaticBody;	
		
		CircleShape boleShape = new CircleShape();
		boleShape.setRadius(hx/8);
				
		fixDef.shape = boleShape;
		bole = gameScreen.world.createBody(bodyDef);
		bole.createFixture(fixDef);		
		bole.setTransform(x, y-hy+hy/16, 0);
		
		//joint
				WheelJointDef axisDef = new WheelJointDef();
				axisDef.bodyA = body;
				axisDef.bodyB = bole;
				axisDef.frequencyHz = 1.1f;
				axisDef.localAnchorA.set(0, 0);
				axisDef.localAxisA.set(0,1f);
				
				setJoint((WheelJoint) gameScreen.world.createJoint(axisDef));
	}
	
	

	public boolean draw()
	{
		boolean drowed = false;		
		if(body.getPosition().x + hx*2 > gameScreen.camera.position.x-gameScreen.camera.viewportWidth*gameScreen.camera.zoom/2 && body.getPosition().x-hx*2<gameScreen.camera.position.x+gameScreen.camera.viewportWidth*gameScreen.camera.zoom/2)
		{			
			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			
			boleSprite.setSize(hx/4*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, hx/4*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom);
			boleSprite.setOrigin(boleSprite.getWidth()/2, boleSprite.getHeight()/2);
			boleSprite.setPosition((bole.getPosition().x-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2-boleSprite.getWidth()/2, (bole.getPosition().y-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom+h/2-boleSprite.getHeight()/2);		
			boleSprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees);
			
			boxSprite.setSize(hx*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, hy*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom);
			boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
			boxSprite.setPosition((body.getPosition().x-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2-boxSprite.getWidth()/2, (body.getPosition().y-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom+h/2-boxSprite.getHeight()/2);		
			boxSprite.setRotation(body.getAngle()*MathUtils.radiansToDegrees);
			
			
			
			batch.begin();
				boleSprite.draw(batch);
				boxSprite.draw(batch);				
			batch.end();
			drowed = true;
		}		
		
		return drowed;
	}



	public WheelJoint getJoint() {
		return joint;
	}



	public void setJoint(WheelJoint joint) {
		this.joint = joint;
	}
}
