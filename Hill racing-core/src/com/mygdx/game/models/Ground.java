package com.mygdx.game.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.mygdx.game.screens.GameScreen;

public class Ground 
{
	private List<Brick> 		bricks;
	private List<Box> 			boxes;
	private List<Tree> 			trees;
	private	GameScreen 			gameScreen;
	float distance;
	float minY;
	
	public Ground(int distance, GameScreen gameScreen)
	{
		this.distance=distance;
		this.gameScreen=gameScreen;
		bricks = new ArrayList<Brick>();
		boxes = new ArrayList<Box>();
		trees = new ArrayList<Tree>();
		createRoad(distance);
		minY = Math.min(Collections.min(bricks).y1, Collections.min(bricks).y2);
	}
	
	public List<Brick> getBricks()
	{
		return bricks;
	}
	
	private void createRoad(int distance)
	{
		Random rand = new Random();
		
		for(int x=-100, y=0; x<distance;)
		{
			int dx = rand.nextInt(80)+30;
			int dy = rand.nextInt(200)-100;
			float h = rand.nextInt(20)+5;
			float w;
			if(dy>-20 && dy< 20)trees.add(new Tree(x+dx/2, y+dy/2+h, (float)(rand.nextInt(20)+5), h, gameScreen));
			if(rand.nextInt(4) == 0)
			{
				h = rand.nextInt(30)+1;
				w = (float)(rand.nextInt(15)+1)/10f;
				boxes.add(new Box(x, y+h, w, h, gameScreen));		
			}
			
			
			
			createHill(x, y, x+dx, y+dy, rand.nextInt(8)+10);
			x+=dx;
			y+=dy;
		}
	}
	
	private void createHill(float x1, float y1, float x2, float y2, int n)
	{
		if(n<15 && y1 > y2) n = 1;
		float dx = (x2-x1)/n;
		float dsiny = 90f/n;
		float x = x1;
		float siny = 0;
		for(int i = 0; i < n; i++)
		{
			bricks.add(new Brick(x, y1+(float)Math.pow(Math.sin(Math.toRadians(siny)), 3)*(y2-y1), x+dx, y1+(float)Math.pow(Math.sin(Math.toRadians(siny+dsiny)), 3)*(y2-y1), gameScreen));
			x+=dx;
			siny+=dsiny;
	    	
		}		
	}

	public void draw() 
	{	
		
		for(Box box: boxes)
    	{			
			box.draw();
    	}
		for(Tree tree: trees)
    	{			
			tree.draw();
    	}		
		for(Brick brick: bricks)
    	{
    		brick.draw(distance, minY);
    	}		
	}
	
	public float getDistance()
	{
		return distance;
	}
}
