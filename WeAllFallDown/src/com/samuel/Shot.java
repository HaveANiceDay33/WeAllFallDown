package com.samuel;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class Shot {
	
	public float x;
	public float y;
	public float speed;
	
	public Shot(float x, float y, float speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public void display(float delta) {
		this.y -= this.speed * delta;
		HvlPainter2D.hvlDrawQuad(x, this.y, 5,25, Color.magenta);
	}
}
