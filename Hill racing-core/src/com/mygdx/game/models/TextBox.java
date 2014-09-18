package com.mygdx.game.models;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TextBox extends BitmapFont 
{	
	private SpriteBatch spriteBatch;   
	private String 		text;
	private float 		x,y;
    
    public TextBox(float x,float y) 
    {
    	text = "";
    	setColor(Color.WHITE);
    	this.x=x;
    	this.y=y;
        spriteBatch = new SpriteBatch();
	}
    public void setPosition(float x, float y)
    {
    	this.x=x;
    	this.y=y;
        
    }
    public void draw(float stateTime)
    {
    	spriteBatch.begin();    		
    		drawMultiLine(spriteBatch, text, x, y);
    	spriteBatch.end();
        
    }
    public void clear()
    {	    	
    	text = "";
    }
    public void add(char ch)
    {	    	
    	if(text.length()<4)text += ch;
    }
    public void back()
    {	    	
    	if(text.length()>0)text = text.substring(0, text.length()-1);
    }
    public String getStr()
    {
		return text;
    }
    public void setStr(String text)
    {
    	this.text=text;
    } 
}
