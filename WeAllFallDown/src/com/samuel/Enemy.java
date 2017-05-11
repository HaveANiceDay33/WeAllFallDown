package com.samuel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.painter.HvlCursor;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplate;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Enemy {
	
	public float x;
	public float y;
	public float speed;
	HvlFontPainter2D gameFont;
	
	//add a declaration to put in a newly added enemy graphic
	
	public Enemy(float x, float y, float speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public void display(float delta){
		float colorArg = HvlMath.map(speed, 20, 100, .05f , 1.0f);
		this.y += this.speed * delta;
		HvlPainter2D.hvlDrawQuad(x, y, speed, speed, Main.getTexture(3), new Color(colorArg,colorArg,colorArg));// put it before color argument
		
	}
	public void displayMenu(float delta){
		float colorArg = HvlMath.map(speed, 20, 100, .05f , 1.0f);
		this.y += this.speed * delta;
		HvlPainter2D.hvlDrawQuadc(x, y, speed, speed, Main.getTexture(3), new Color(colorArg,colorArg,colorArg));// put it before color argument
	}
}
