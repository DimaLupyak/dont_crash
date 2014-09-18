package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.mygdx.game.screens.GameScreen;

public class Car extends InputAdapter
{
	private Body chassis, leftWheel, rightWheel;
	private WheelJoint leftAxis, rightAxis;
	private float motorSpeed = 300;
	public Status status;
	boolean touch = false;
	GameScreen gameScreen;
	TextureRegion texture;
	SpriteBatch batch;
	PolygonSpriteBatch polyBatch;
	public Sprite chassisSprite;
	Sprite leftWheelSprite;
	Sprite rightWheelSprite;
	float radOfWheel = 3;
	private  float  health;
	public boolean leftWheelContact = false;
	public boolean rightWheelContact = false;
	
	public Car(float x, float y, float width, float height, GameScreen gameScreen)
	{
		this.gameScreen=gameScreen;
		status = Status.NONE;
		batch = new SpriteBatch();
		polyBatch = new PolygonSpriteBatch();		
		texture = new TextureRegion(new Texture("box.png"));
		
		chassisSprite = new Sprite(new Texture("car.png"));
		Texture wheelTexture = new Texture("wheel.png");
		leftWheelSprite = new Sprite(wheelTexture);
		rightWheelSprite = new Sprite(wheelTexture);
		
		FixtureDef chassisFixtureDef = new FixtureDef(), wheelFixtureDef = new FixtureDef();
		
		chassisFixtureDef.density = 45;
		chassisFixtureDef.friction = 1f;
		chassisFixtureDef.restitution = .2f;
		
		wheelFixtureDef.density = chassisFixtureDef.density * 2f;
		wheelFixtureDef.friction = 10;
		wheelFixtureDef.restitution = 0.3f;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		
		health=1;
		
		//chassis
		
		
		
		PolygonShape chassisShape = new PolygonShape();
		chassisShape.set(new Vector2[]{
				new Vector2(-8f, -2f),
				new Vector2(8f, -2f),
				//new Vector2(7f, 1.5f),
				//new Vector2(6f, 1.5f),
				new Vector2(8f, 1f),
				new Vector2(-7f, 1f),
				//new Vector2(-10f, -1),
				});
		
		chassisFixtureDef.shape = chassisShape;
		chassisFixtureDef.isSensor = false;
		chassis = gameScreen.world.createBody(bodyDef);
		chassis.createFixture(chassisFixtureDef);
		MassData mass = chassis.getMassData();
		mass.mass=1000;
		chassis.setMassData(mass);		
		
		//left wheel
		
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(radOfWheel);
		
		wheelFixtureDef.shape = wheelShape;		
		leftWheel = gameScreen.world.createBody(bodyDef);
		leftWheel.createFixture(wheelFixtureDef);
		//right wheel
		
		rightWheel = gameScreen.world.createBody(bodyDef);
		rightWheel.createFixture(wheelFixtureDef);
		
		//left axis
		WheelJointDef axisDef = new WheelJointDef();
		axisDef.bodyA = chassis;
		axisDef.bodyB = leftWheel;
		axisDef.frequencyHz = 1.6f;
		axisDef.localAnchorA.set(-8*.6f, -5f);
		axisDef.localAxisA.set(0,1f);
		axisDef.maxMotorTorque = 50/9*wheelShape.getRadius()*wheelShape.getRadius()*5500f;
		
		leftAxis = (WheelJoint) gameScreen.world.createJoint(axisDef);
		
		//right axis
		axisDef.bodyB = rightWheel;
		axisDef.localAnchorA.x *= -1;
		
		rightAxis = (WheelJoint) gameScreen.world.createJoint(axisDef);
		
		leftAxis.setSpringDampingRatio(0.7f);
		rightAxis.setSpringDampingRatio(0.7f);
		
		leftWheel.setBullet(true);
		rightWheel.setBullet(true);
	}
	
	
	public float getHealth()
	{
		return health;		
	}
	
	public void setHealth(float health)
	{
		this.health=health;		
	}
	
	public Body getChassis()
	{
		return chassis;	
	}
	
	public Body getLeftWheel()
	{
		return leftWheel;	
	}
	
	public Body getRightWheel()
	{
		return rightWheel;	
	}
	
	public float getX()
	{
		return chassis.getPosition().x;		
	}
	
	public float getY()
	{
		return chassis.getPosition().y;		
	}
	
	@Override
	public boolean keyDown(int keykode)
	{
		switch(keykode)
		{
		case Keys.W:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(-motorSpeed);
			break;		
		case Keys.S:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(motorSpeed);
			break;
		case Keys.UP:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(-motorSpeed);
			break;			
		case Keys.DOWN:
			leftAxis.enableMotor(true);
			leftAxis.setMotorSpeed(motorSpeed);
			break;	
					
		case Keys.SPACE:
			leftAxis.enableMotor(false);
			leftWheel.setAngularVelocity(0);
			rightWheel.setAngularVelocity(0);
			break;	
		case Keys.R:
			status = Status.RESTART;
			try{
				gameScreen.world.destroyBody(this.chassis);
				gameScreen.world.destroyBody(this.leftWheel);
				gameScreen.world.destroyBody(this.rightWheel);
			}			
			catch (NullPointerException e){}
			break;
		case Keys.Q:
			if(leftWheelContact||rightWheelContact)
			{
				chassis.applyLinearImpulse(0, 50000, chassis.getPosition().x, chassis.getPosition().y+10, true);
				leftWheel.applyLinearImpulse(0, 20000, chassis.getPosition().x, leftWheel.getPosition().y+10, true);
				rightWheel.applyLinearImpulse(0, 20000, chassis.getPosition().x, rightWheel.getPosition().y+10, true);			
			}
			break;	
		
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keykode)
	{
		switch(keykode)
		{
		case Keys.W:
			leftAxis.enableMotor(false);			
			break;		
		case Keys.S:
			leftAxis.enableMotor(false);
			break;	
		case Keys.UP:
			leftAxis.enableMotor(false);
			break;		
		case Keys.DOWN:
			leftAxis.enableMotor(false);
			break;	
		
		
		}
		return true;
	}
	
	public void update()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
				
		chassisSprite.setSize(16*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, 4*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom);
		chassisSprite.setOrigin(chassisSprite.getWidth()/2, chassisSprite.getHeight()/2);
		chassisSprite.setPosition(w/2-chassisSprite.getWidth()/2, h/2-chassisSprite.getHeight()/2);		
		chassisSprite.setRotation(chassis.getAngle()*MathUtils.radiansToDegrees);
		
		leftWheelSprite.setSize(radOfWheel*2*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, radOfWheel*2*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom);
		leftWheelSprite.setOrigin(leftWheelSprite.getWidth()/2, leftWheelSprite.getHeight()/2);
		leftWheelSprite.setPosition(w/2-leftWheelSprite.getWidth()/2+(gameScreen.camera.position.x-leftWheel.getPosition().x)*-w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, h/2-leftWheelSprite.getHeight()/2+(gameScreen.camera.position.y-leftWheel.getPosition().y)*-h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom);		
		leftWheelSprite.setRotation(leftWheel.getAngle()*MathUtils.radiansToDegrees);
		
		rightWheelSprite.setSize(radOfWheel*2*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, radOfWheel*2*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom);
		rightWheelSprite.setOrigin(rightWheelSprite.getWidth()/2, rightWheelSprite.getHeight()/2);
		rightWheelSprite.setPosition(w/2-rightWheelSprite.getWidth()/2+(gameScreen.camera.position.x-rightWheel.getPosition().x)*-w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom, h/2-rightWheelSprite.getHeight()/2+((gameScreen.camera.position.y-rightWheel.getPosition().y)*-h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom));		
		rightWheelSprite.setRotation(rightWheel.getAngle()*MathUtils.radiansToDegrees);
		if(chassis.getLinearVelocity().y<-59){status=Status.GAMEOVER;}
		if(Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)||Gdx.input.getAccelerometerY()<-3)
		{
			chassis.applyAngularImpulse(6000, true);
		}
		if(Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)||Gdx.input.getAccelerometerY()>2)
		{
			chassis.applyAngularImpulse(-6000, true);
		}
		
		if(chassis.getAngularVelocity()>2)chassis.setAngularVelocity(2);
		if(chassis.getAngularVelocity()<-2)chassis.setAngularVelocity(-2);
		
		if(touch)
		{
			if(w-Gdx.input.getY()>Gdx.graphics.getHeight()/8*7)
			{
				
			}
			if(Gdx.input.getX()>Gdx.graphics.getWidth()/3*2)
			{
				leftAxis.enableMotor(true);
				leftAxis.setMotorSpeed(-motorSpeed);
			}
			else if(Gdx.input.getX()<Gdx.graphics.getWidth()/3)
			{
				leftAxis.enableMotor(true);
				leftAxis.setMotorSpeed(motorSpeed);
			}
			else
			{
				if(leftWheelContact||rightWheelContact)
				{
					chassis.applyLinearImpulse(0, 50000, chassis.getPosition().x, chassis.getPosition().y+10, true);
					leftWheel.applyLinearImpulse(0, 20000, chassis.getPosition().x, leftWheel.getPosition().y+10, true);
					rightWheel.applyLinearImpulse(0, 20000, chassis.getPosition().x, rightWheel.getPosition().y+10, true);			
				}
			}
		}
		
		
		
		
		float[] leftDamper1Vertices = new float[] 
				{ 
				leftWheelSprite.getX()+leftWheelSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), leftWheelSprite.getY()+leftWheelSprite.getWidth()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				leftWheelSprite.getX()+leftWheelSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), leftWheelSprite.getY()+leftWheelSprite.getWidth()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2-chassisSprite.getWidth()/8*3*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation()))+chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2 - chassisSprite.getWidth()/8*3*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2-chassisSprite.getWidth()/8*3*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation()))-chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2 - chassisSprite.getWidth()/8*3*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))
	            };
	    float[] rightDamper2Vertices = new float[] 
				{ 
				rightWheelSprite.getX()+rightWheelSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), rightWheelSprite.getY()+rightWheelSprite.getWidth()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				rightWheelSprite.getX()+rightWheelSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), rightWheelSprite.getY()+rightWheelSprite.getWidth()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2+chassisSprite.getWidth()/8*3*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation()))+chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2 + chassisSprite.getWidth()/8*3*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2+chassisSprite.getWidth()/8*3*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation()))-chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2 + chassisSprite.getWidth()/8*3*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))
	            };		
		
		float[] leftDamper2Vertices = new float[] 
				{ 
				leftWheelSprite.getX()+leftWheelSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), leftWheelSprite.getY()+leftWheelSprite.getWidth()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				leftWheelSprite.getX()+leftWheelSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), leftWheelSprite.getY()+leftWheelSprite.getWidth()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))
                };
       float[] rightDamper1Vertices = new float[] 
				{ 
				rightWheelSprite.getX()+rightWheelSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), rightWheelSprite.getY()+rightWheelSprite.getWidth()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				rightWheelSprite.getX()+rightWheelSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), rightWheelSprite.getY()+rightWheelSprite.getWidth()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2 + chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2+chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation())),
				chassisSprite.getX()+chassisSprite.getWidth()/2 - chassisSprite.getWidth()/32*(float)Math.sin(Math.toRadians(90-chassisSprite.getRotation())), chassisSprite.getY()+chassisSprite.getHeight()/2-chassisSprite.getWidth()/32*(float)Math.cos(Math.toRadians(90-chassisSprite.getRotation()))
                };
		
		PolygonSprite leftDamper1 = new PolygonSprite(new PolygonRegion(texture , leftDamper1Vertices, new EarClippingTriangulator().computeTriangles(leftDamper1Vertices).toArray()));
		PolygonSprite rightDamper1= new PolygonSprite(new PolygonRegion(texture , rightDamper2Vertices, new EarClippingTriangulator().computeTriangles(rightDamper2Vertices).toArray()));
		PolygonSprite leftDamper2 = new PolygonSprite(new PolygonRegion(texture , leftDamper2Vertices, new EarClippingTriangulator().computeTriangles(leftDamper2Vertices).toArray()));
		PolygonSprite rightDamper2= new PolygonSprite(new PolygonRegion(texture , rightDamper1Vertices, new EarClippingTriangulator().computeTriangles(rightDamper1Vertices).toArray()));
		
		
		polyBatch.begin();
			leftDamper1.draw(polyBatch);
			rightDamper1.draw(polyBatch);
			leftDamper2.draw(polyBatch);
			rightDamper2.draw(polyBatch);
		polyBatch.end();
		batch.begin();
			chassisSprite.draw(batch);
			leftWheelSprite.draw(batch);
			rightWheelSprite.draw(batch);
		batch.end();
		
		if(health<=0) status = Status.GAMEOVER;
		
	}
	
	@Override
	public boolean touchDown(int x, int y, int arg2, int button) 
	{
		if(button == Buttons.LEFT)
		{
			touch = true;			
		}
		if(button == Buttons.MIDDLE)
		{
			status = Status.RESTART;
			try{
				gameScreen.world.destroyBody(this.chassis);
				gameScreen.world.destroyBody(this.leftWheel);
				gameScreen.world.destroyBody(this.rightWheel);
			}
			catch (NullPointerException e){}
			
		}
		
		return false;
	}
	
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int button)
	{
		touch = false;
		if(button == Buttons.LEFT)
		{
			leftAxis.enableMotor(false);
		}
		return false;
		}

	public float getSpeed() 
	{
		return chassis.getLinearVelocity().x;
	}
	
}
