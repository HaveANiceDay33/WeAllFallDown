package com.samuel;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Stars {
	
	public float x;
	public float y;
	public float speed;
	
	public Stars(float x, float y, float speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public void display(float delta){
		this.y += this.speed * delta * 10;
		HvlPainter2D.hvlDrawQuad(x, y, speed, speed, new Color(255, 255, 255, .7f));
	}
	
}
