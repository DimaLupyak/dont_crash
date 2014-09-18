package com.mygdx.game.screens;


import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.Brick;
import com.mygdx.game.models.Car;
import com.mygdx.game.models.Ground;
import com.mygdx.game.models.Status;
import com.mygdx.game.models.TextBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.mygdx.game.controllers.GameController;
import com.mygdx.game.controllers.MyContactListener;

public class GameScreen implements Screen 
{
	private float				w, h;
	public  World				world;
	private Box2DDebugRenderer  debugRenderer;
	private	SpriteBatch			batch;
	public	OrthographicCamera 	camera;
	public 	int 				debugRend = 2;
	private float 				timeStep = 1/30f;
	private Car					car;
	public 	MyGdxGame 			myGame;
	private Ground				ground;
	private TextBox				wayBox;
	private TextBox				speedBox;
	MyContactListener 			contactListener;
	private Sprite				healthBg;
	private Texture				healthProgress;
	private Sprite				progressBg;
	private Texture				progress;
	
	public GameScreen(MyGdxGame myGame)
	{
		
		
		this.myGame=myGame;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		wayBox = new TextBox(0, + h/60);		
		speedBox = new TextBox(0, Gdx.graphics.getHeight()-10);
		speedBox.setColor(1, 1, 1, 0.3f);
		wayBox.setScale(1.2f);
		wayBox.setColor(1, 1, 1, 0.5f);
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -10f), true);		
		camera = new OrthographicCamera();	
		
		batch = new SpriteBatch();		
		
		healthBg = new Sprite(new Texture("health.png"));
		healthBg.setBounds(w/2-w/8, h-h/30, w/4, h/60);
		healthProgress = new Texture("health.png");
		
		progressBg = new Sprite(new Texture("progressBg.png"));
		progressBg.setBounds(0, 0, w, h/50);
		progress = new Texture("progressBg.png");
		
		ground = new Ground(10000, this);
		//car		
		float tmpY = 0;
		float tmpX = 0;
		for(Brick brick: ground.getBricks())
		{
			if(tmpX>brick.getX1()&&tmpX<brick.getX2())
			{
				float d = (tmpX - brick.getX1()/(brick.getX2() - brick.getX1()));
				tmpY = brick.getY1()+(brick.getY2()-brick.getY1())*d+8;
				break;
			}
		}
		
		car = new Car(tmpX, tmpY, 3, 1.5f, this);
		
		contactListener = new MyContactListener(this);
		world.setContactListener(contactListener);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(new GameController(this), car));
	}	
	
	@Override
	public void hide() 
	{
	}

	public Car getCar() 
	{
		return car;
	}
	
	public Ground getGround() 
	{
		return ground;
	}
	
	@Override
	public void render(float stateTime) 
	{
		float framesPerSecond = Gdx.graphics.getFramesPerSecond();
		System.out.println(framesPerSecond + " fps");
		Gdx.graphics.getGL20().glClearColor(0, 0.4f, 0.7f, 0);
		
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	camera.position.x = car.getX();
    	camera.position.y = car.getY();
    	if(camera.zoom<Math.abs(car.getSpeed())/15+0.5)camera.zoom+=0.01;
    	if(camera.zoom>Math.abs(car.getSpeed())/15+0.5)camera.zoom-=0.003;
    	camera.update();
    	wayBox.setPosition(w*camera.position.x/ground.getDistance(), h/40);
    	wayBox.setStr(String.format("%.1f",camera.position.x/ground.getDistance()*100) + "%");
    	//speedBox.setStr("Speed: "+String.format("%.1f", (car.getSpeed()/framesPerSecond/60*1000)) + " km/h");
    	speedBox.setStr(""+Gdx.graphics.getFramesPerSecond() + " fps");
    	
    	if(car.status == Status.RESTART) 
    		{
    		float tmpY = car.getY()+5;
    		float tmpX = car.getX();
    		contactListener.contactWithChassis=false;
    		car = new Car(tmpX, tmpY, 3, 1.5f, this);
    		Gdx.input.setInputProcessor(new InputMultiplexer(new GameController(this), car));
    		}
    	
    	if(car.status == Status.GAMEOVER) 
		{
    		myGame.setScreen(new GameScreen(myGame));
		}
    	
    	world.step(timeStep, 8, 3);     	
    	
    	if(debugRend == 0 || debugRend == 1) {debugRenderer.render(world, camera.combined);} 
    	if(debugRend == 2 || debugRend == 1)
    	{
	    	ground.draw();    	 
	    	car.update();
    	}
    	
    	contactListener.update();
    	
    	batch.begin();
    		healthBg.draw(batch, 0.4f);
    		progressBg.draw(batch, 0.4f);
    		batch.draw(healthProgress, w/2-w/8, h-h/30, w/4*car.getHealth(), h/60, 0, 0, (int)(256*car.getHealth()), 128, false, false);
    		batch.draw(progress, 0, 0, w*camera.position.x/ground.getDistance(), h/50, 0, 0, (int)(512*camera.position.x/ground.getDistance()), 32, false, false);
    	batch.end();
    	wayBox.draw(stateTime);    	
    	speedBox.draw(stateTime);
    	
	}
	
	@Override
	public void resize(int arg0, int arg1) 
	{ 
		camera.viewportWidth = 60/h*w;
		camera.viewportHeight = 60;
	}	  
	
	@Override
    public void dispose()
    {
    	world.dispose();
    	batch.dispose();
    	debugRenderer.dispose();
    }

	@Override
	public void pause() 
	{
	}

	@Override
	public void resume() 
	{
	}

	@Override
	public void show() 
	{
	}
}

