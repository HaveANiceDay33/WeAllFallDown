import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.osreboot.ridhvl.HvlFontUtil;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.display.HvlDisplayMode;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.HvlCursor;
import com.osreboot.ridhvl.painter.HvlGradient;
import com.osreboot.ridhvl.painter.HvlGradient.Style;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D {
	public static void main(String [] args){
		new Main();
	}
	public Main(){
		super(60, 1920, 1080, "We All Fall Down ","blueBallIcon", new HvlDisplayModeDefault());
	}
	
	float width;
	float height;
	float score;
	boolean hasPlayed;
	
	float intensifier;
	
	float graceCount;
	
	float waitTimeMenu;
	float waitTimeGame;
	float yPos;
	
	HvlMenu splash;
	HvlMenu menu;
	HvlMenu game;
	HvlMenu death;
	Color splashColor;
	Color shade;
	Color healthBar;
	HvlFontPainter2D splashFont;
	float splashTime;
	
	float waitTime;
	ArrayList<Enemy> enemies;
	ArrayList<Enemy> enemiesMenu;
	float counter;
	
	HvlFontPainter2D gameFont;
	
	@Override
	public void initialize(){
		hasPlayed = false;
		
		Player player = new Player(Display.getWidth()/2);
		
		waitTimeMenu = .2f;
		waitTimeGame = 1f;
		graceCount = 30f;
		counter = 0;
		enemies = new ArrayList<Enemy>();
		enemiesMenu = new ArrayList<Enemy>();
		yPos = -50;
		width = Display.getWidth();
		height = Display.getHeight();
		
		splashColor = new Color(100 + (int)(Math.random() * ((255 - 100) + 1)) ,100 + (int)(Math.random() * ((255 - 100) + 1)),100 + (int)(Math.random() * ((255 - 100) + 1)));
		shade = new Color(0,0,0,.75f);
		splashTime = 3;
		
		intensifier = 1;
		
		score = 0;
		
		healthBar = new Color(255,255,255,0.5f);
		
		getTextureLoader().loadResource("osFont");//0
		getTextureLoader().loadResource("Font");//1
		getTextureLoader().loadResource("runningman");//2
		gameFont =  new HvlFontPainter2D(getTexture(0), HvlFontPainter2D.Preset.FP_INOFFICIAL,.5f,8f,0);
		splashFont = new HvlFontPainter2D(getTexture(1), HvlFontUtil.DEFAULT,192, 256,0.25f,10);
		
		splash = new HvlMenu(){
			@Override
			public void draw(float delta){

				splashTime-=delta;

				HvlPainter2D.hvlDrawQuad(0, 0, 1920, 1080, splashColor);

				splashFont.drawWord("running man", width/2+100,height/2-200,Color.black);
				splashFont.drawWord("productions", width/2+100,height/2-100,Color.black);
				HvlPainter2D.hvlDrawQuad(360, 240, 600, 600, getTexture(2));
				if(splashTime < 0){
					HvlMenu.setCurrent(menu);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
					exit();
				}
				super.draw(delta);
			}
		};
		menu = new HvlMenu(){
			@Override
			public void draw(float delta){
				counter+=delta;
				if(counter > waitTimeMenu){
					Enemy enemyMenu = new Enemy(HvlMath.randomFloatBetween(20, 1900),yPos -30, HvlMath.randomFloatBetween(20, 100));
					enemiesMenu.add(enemyMenu);
					counter = 0;
				}
				
				for(Enemy wave : enemiesMenu){
					wave.displayMenu(delta);
				}
				HvlPainter2D.hvlDrawQuad(0,0,width,height+ 400,shade);
				gameFont.drawWordc("We All Fall Down", width/2, height/2 - 400, Color.darkGray, 2.05f);
				gameFont.drawWordc("We All Fall Down", width/2, height/2 - 400, Color.white, 2f);
				gameFont.drawWordc("Press Space to begin", width/2, height/2 +400, Color.darkGray, 1.02f);
				gameFont.drawWordc("Press Space to begin", width/2, height/2 + 400, Color.white, 1f);
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
					HvlMenu.setCurrent(game);
				}
				super.draw(delta);
			}
		};
		game = new HvlMenu(){
			@Override
			public void draw(float delta){
				counter+=delta;
				score += delta; 
				if(counter > waitTimeGame){
					Enemy enemy = new Enemy(HvlMath.randomFloatBetween(20, 1900),yPos - 50, HvlMath.randomFloatBetween(20, 100));
					enemies.add(enemy);
					counter = 0;
				}
				waitTimeGame -= .005 * delta;
				for(Enemy wave : enemies){
					wave.display(delta);
					player.display(delta);
					graceCount -= delta;
					if(wave.x >= player.xPos && wave.x <= (player.xPos + 50) 
							|| (wave.x + wave.speed) >= player.xPos && (wave.x+wave.speed) <= (player.xPos + 50) 
							|| wave.x <= player.xPos && (wave.x + wave.speed) > (player.xPos + 50)){
						if((wave.y + wave.speed) >= player.yPos && (wave.y+wave.speed) <= (player.yPos + 50)
								|| wave.y >= player.yPos && wave.y <= (player.yPos + 50)
								|| wave.y <= player.yPos && (wave.y + wave.speed) >= (player.yPos + 50)){
							if(graceCount <= 0){
								player.health -= 10;
								graceCount = 30f;
							}
	
						}
						
					}
					if(graceCount <= 0){
						graceCount = 0;
					}

				}
				if(Keyboard.isKeyDown(Keyboard.KEY_D)){
					player.xPos += player.keySpeed * delta;	
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
					player.xPos -= player.keySpeed * delta;	
				}
				if(player.xPos <= 0){
					player.xPos = 0;
				}
				if(player.xPos + 50 >= width){
					player.xPos = width - 50;
				}
				if(player.health <= 0){
					player.health = 0;
					HvlMenu.setCurrent(death);
				}
				HvlPainter2D.hvlDrawQuad(100, 100, player.health * 3, 25, healthBar);
				gameFont.drawWordc("0", 100, 150, healthBar, 0.3f);
				gameFont.drawWordc("100", 400, 150, healthBar, 0.3f);
				gameFont.drawWordc("Invincibility Time: "+ Math.round(graceCount), 250 , 180, healthBar, .3f);
				gameFont.drawWordc("Score: "+Math.round(score), 250, 300, healthBar, 0.5f);
				super.draw(delta);
			}
		};
		death = new HvlMenu(){
			@Override
			public void draw(float delta){
				counter+=delta;
				intensifier += .005 * delta;
				if(counter > waitTimeMenu){
					Enemy enemyMenu = new Enemy(HvlMath.randomFloatBetween(20, 1900),yPos -30, HvlMath.randomFloatBetween(20, 100)*intensifier);
					enemiesMenu.add(enemyMenu);
					counter = 0;
				}
				for(Enemy wave : enemiesMenu){
					wave.displayMenu(delta);
				}
				HvlPainter2D.hvlDrawQuad(0,0,width,height+ 400,shade);
				gameFont.drawWordc("You have died", width/2, height/2 - 400, Color.darkGray, 2.05f);
				gameFont.drawWordc("You have died", width/2, height/2 - 400, Color.white, 2f);
				gameFont.drawWordc("Score: "+Math.round(score), width/2, height/2, Color.darkGray , 1.535f);
				gameFont.drawWordc("Score: "+Math.round(score), width/2, height/2, Color.white, 1.5f);
				gameFont.drawWordc("Press M to return to the menu", width/2, height/2 +400, Color.darkGray, 1.02f);
				gameFont.drawWordc("Press M to return to the menu", width/2, height/2 + 400, Color.white, 1f);
				
				if(Keyboard.isKeyDown(Keyboard.KEY_M)){
					HvlMenu.setCurrent(menu);
				}
				super.draw(delta);
			}
		};
		HvlMenu.setCurrent(splash);
	}
	@Override
	public void update(float delta){
		HvlMenu.updateMenus(delta);
	}
}
