package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.screens.GameScreen;

public class Brick implements Comparable<Brick> 
{
	private Body body;
	
	float x1, y1, x2, y2, w, h;
	GameScreen gameScreen;
	Texture snowTexture;
	Texture groundTexture;
	PolygonSprite polygonSprite;
	PolygonSpriteBatch batch;
	float[] snowVertices;
	float[] groundVertices;
	short[] triangles;
	public Brick(float x1, float y1, float x2, float y2, GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		snowTexture = new Texture("snow2.png");
		groundTexture = new Texture("progressBg.png");
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		
		EdgeShape shape = new EdgeShape();
		shape.set(x1, y1, x2, y2);
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 10;
		fixDef.restitution = 0.1f;
		fixDef.friction = 10f;
		body = gameScreen.world.createBody(bodyDef);
		body.createFixture(fixDef);	
		
		snowVertices = new float[]{100,0,100,100,0,100,0,0};
		triangles = new short[]
				{
					0,1,3,0,1,2
				};
		polygonSprite = new PolygonSprite(new PolygonRegion(new TextureRegion(snowTexture,128,128), snowVertices, triangles));
		batch = new PolygonSpriteBatch();
		
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public float getX1()
	{
		return x1;
	}
	public float getX2()
	{
		return x2;
	}
	public float getY1()
	{
		return y1;
	}
	public float getY2()
	{
		return y2;
	}
	public void draw(float distance, float minY)
	{
		boolean inPole = true;
		
			if((x2-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2<0
					|| (x1-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2 > w)
				inPole = false;
			else
			{
			
				float[] snowVertices = new float[] 
					{ 
					(x1-0.01f-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y1-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom-3*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+h/2,
					(x2-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y2-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom+h/2,
					(x2-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y2-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom-3*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+h/2,
					(x1-0.01f-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y1-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom+h/2
	                };
				triangles = new short[]
						{
							0,1,3,0,1,2
						};
				PolygonRegion region = new PolygonRegion
						(
							new TextureRegion(snowTexture),
							snowVertices, 
							triangles
						);
				polygonSprite.setRegion(region);
	            
	         }
			
			if(inPole)
			{	
				batch.begin();			
					polygonSprite.draw(batch);		
				batch.end();
				float[] groundVertices = new float[] 
						{ 
						(x1-0.01f-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, 0,
						(x2-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, -Math.abs(y1-y2)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom,
						(x2-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y2-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom-3*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+h/2,
						(x1-0.01f-gameScreen.camera.position.x)*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+w/2, (y1-gameScreen.camera.position.y)*h/gameScreen.camera.viewportHeight/gameScreen.camera.zoom-3*w/gameScreen.camera.viewportWidth/gameScreen.camera.zoom+h/2
		                };
				triangles = new short[]
						{
							0,1,3,3,1,2
						};
				PolygonRegion region = new PolygonRegion
						(
							new TextureRegion(groundTexture,128,128),
							groundVertices, 
							triangles
						);
				polygonSprite.setRegion(region);
				batch.begin();			
				polygonSprite.draw(batch);		
			batch.end();
			}		
		
		
	}

	@Override
	public int compareTo(Brick tmp) 
	{
	    if(Math.min(this.y1, this.y2) < Math.min(tmp.y1, tmp.y2))
	    {
	      return -1;
	    }   
	    else if(Math.min(this.y1, this.y2) > Math.min(tmp.y1, tmp.y2))
	    {
	      return 1;
	    }
		return 0;
	}

	
}
