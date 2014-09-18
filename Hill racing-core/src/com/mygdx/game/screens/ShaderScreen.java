package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.TextBox;

public class ShaderScreen implements Screen 
{	
	MyGdxGame myGdxGame;
	public Mesh mesh;
	ShaderProgram shader;
	boolean ok = false;
	GL20 gl = Gdx.graphics.getGL20();
	public ShaderScreen(MyGdxGame myGdxGame) 
	{		
		this.myGdxGame = myGdxGame;
		ShaderProgram.pedantic = false;
		shader=new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),Gdx.files.internal("shaders/fragment.frag"));
		if (shader.isCompiled() == false) {
			Gdx.app.log("ShaderTest", shader.getLog());
			//Gdx.app.exit();
		}
	
		else
		{
			ok=true;
			mesh = new Mesh(true, 3, 3, VertexAttribute.Position(), VertexAttribute.ColorUnpacked());
			mesh.setVertices(new float[] {-0.5f, -0.5f, 0, 1, 0.4f, 1, 1, 0.5f, -0.5f, 0, 1, 0.2f, 0f, 1,  0.5f, 0.5f, 0, 1, 1, 1,
			1});
			mesh.setIndices(new short[] {0, 1, 2});
		}
	}

	@Override
	public void render(float delta) 
	{		
		gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);		
		if(ok)
		{
		shader.begin();
		mesh.render(shader, gl.GL_TRIANGLES);
		shader.end();
		
		}
		else
		{
			TextBox text= new TextBox(100, Gdx.graphics.getHeight()-100);
			text.setStr(shader.getLog()+"   .:(error):.");
			text.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			text.setScale(2);
			text.draw(0.02f);
		}
		if(Gdx.input.isTouched()) myGdxGame.setScreen(new ScreenTwo(myGdxGame));
	}

	@Override
	public void resize(int width, int height) {}
	@Override
	public void show() {}
	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void dispose() 
	{
		mesh.dispose();
	}
	
}

