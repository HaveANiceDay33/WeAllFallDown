package com.samuel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Player{
	public float sizeX = 50;
	public float sizeY = 50;
	public float keySpeed = 500;
	public Color colorPlay = Color.lightGray;
	public float health = 100;
	
	public float xPos;
	public float yPos;
	
	public Player(float xPos, float yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public void display(float delta){
		HvlPainter2D.hvlDrawQuad(xPos, yPos, sizeX, sizeY, Main.getTexture(4), colorPlay);
	}

}
