package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.TextBox;

public class ScreenTwo implements Screen 
{
	SpriteBatch batchSprite;
	MyGdxGame myGdxGame;
	Sprite logoSprite;
	Texture texture;
	OrthographicCamera camera;
	
	GameScreen gameScreen;
	public ScreenTwo(final MyGdxGame myGdxGame) 
	{
		
		texture = new Texture("logo.png");
		logoSprite = new Sprite(texture);
		logoSprite.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		logoSprite.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/4);
		this.myGdxGame=myGdxGame;
		batchSprite= new SpriteBatch();
		
		
		gameScreen = new GameScreen(myGdxGame);
	}

	@Override
	public void render(float delta) 
	{
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batchSprite.begin();
			logoSprite.draw(batchSprite);
		batchSprite.end();
		
		
 		if(Gdx.input.isTouched()) myGdxGame.setScreen(gameScreen); 
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}

