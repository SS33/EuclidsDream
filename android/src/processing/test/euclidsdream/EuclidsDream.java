package processing.test.euclidsdream;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import apwidgets.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class EuclidsDream extends PApplet {




APMediaPlayer sound1;
APMediaPlayer sound2;
APMediaPlayer sound3;
APMediaPlayer sound4;

public static final int START = 0;
public static final int LVL1 = 1;
public static final int WIN = 2;
public static final int LOSE = 3;
public static final int RESET = 4;

int level;
int winFill;
int gameFill;
int time;
int delay = 2000;
int starCounter = 0;
int rectTotal = 6;
int winCounter = 0;
ArrayList<Circle> circles; 
ArrayList<Rectangle> rectangles;

Triangle tri;
Star star;
Wallpaper w;

boolean pastWin;
boolean gameWin;
boolean canDisplay = false;
float gameBackground = 50;
float titleBackground = 20;
int rad;

PFont font;
String msg;
int msgTime;
int alpha = 0;



public void setup() {
  orientation(PORTRAIT);
  time = millis();

  
  gameFill = color( 20, 133, 204);

  font = loadFont("SegoeUI-Light-80.vlw");

  textFont(font);
  textSize(80);
  textAlign(CENTER);
  msgTime = 100;
  gameWin = false;
  pastWin = false;
  w = new Wallpaper(width, height);




  sound1 = new APMediaPlayer(this);
  sound2 = new APMediaPlayer(this);
  sound3 = new APMediaPlayer(this);
  sound4 = new APMediaPlayer(this);
  sound1.setMediaFile("sfx2.mp3");
  sound2.setMediaFile("sfx3.mp3");
  sound3.setMediaFile("powerup.mp3");
  sound4.setMediaFile("chordalSynthPattern02.mp3");

  circles = new ArrayList<Circle>();
  for (int i = 0; i < 6; i++) {
    circles.add(new Circle(random(width), random(height)));
  }

  rectangles = new ArrayList<Rectangle>();
  for (int i = 0; i < rectTotal; i++) {
    rectangles.add(new Rectangle(random(width), random(height)));
  }

  tri= new Triangle(width/2, 2000);
  tri.setColor(120, 120, 120);

  star = new Star(200, -60, 40, 0, 0);
  star.setColor(0, 178, 51);

  level = START;
}

public void draw() {
  w.display();
  switch(level) {
  case START:
    println("start");
    if (msgTime>0) {
      msgTime--;
      showMessage("EUCLID'S DREAM", msgTime);
    }
    if (msgTime == 0) {
      level = LVL1;
      msgTime = 100;
    }

    break;
  case LVL1:
    println("level1");  

    rectDraw();
    circDraw();
    triDraw(canDisplay);
    starDraw();    
    collisionCheck();
    initStar();
    if (winCheck()) {
      level = WIN; 
      gameWin = false;
    }
    break;
  case WIN:
    println("win");   
    showMessage("YOU WON!", msgTime);
    pastWin = true;

    if (msgTime > 0) {
      msgTime--;
    }
    if (msgTime == 0) {
      level = RESET;
      msgTime = 100;
    }
    break;
  case LOSE:
    println("lose");
    showMessage("TRY AGAIN!", msgTime);
    starCounter++;
    rectanglesRemove();
    pastWin = false;

    if (msgTime > 0) {
      msgTime--;
    }
    if (msgTime == 0) {
      level = RESET;
      msgTime = 100;
    }
    break; 
  case RESET:
    println("reset");
    canDisplay = false;
    reset();
    level = LVL1;
    break;
  }

  print("totalRect: " +  rectangles.size() + " ");
  print(pastWin);
}


public void showMessage(String msgText, int msgTime) {

  if (msgTime > 0) {
    alpha = alpha + 3;
    fill(200, 200, 200, alpha);
    text(msgText, width/2, height/2);
  }
}




public void initStar() {
  if (starCounter > 0) {
    star.sY = 4;
    starCounter = 0;
  }
}



public boolean winCheck() {
  int count = 0;
  for (Circle c : circles) {
    if (c.collided) {
      count++;
  
    }
  }

  if (count == circles.size()) {

    for (Rectangle r : rectangles) {
      r.collided = true;
      count = 0;
    }
    gameWin = true;
  }
  winCounter++;
  return gameWin;
}



public void collisionCheck() {

  for (Circle c : circles) {
    if (tri.collision(c.x, c.y)) {
      tri.collided = false;
      c.collided = true;

      sound1.start();
    }
  }

  for (Rectangle r : rectangles) {
    if (tri.collision(r.x, r.y)) {
      tri.collided = false;
      tri.deathCollide = false;
      level = LOSE;
      sound2.start();
    }
  }

  if (tri.collision(star.x, star.y)) {
    tri.collided= false;
    star.collided = true;
    sizeChange();
    sound3.start();
  }
}

public void timeDelay() {
  if (millis() - time >= delay) {
    time = millis();
  }
}


public void reset() {
  timeDelay();
  msgTime = 100;
  alpha = 0;
  tri.reset(width/2, height - 40);
  for (Circle c : circles) {
    c.reset(random(width), random(height), c.sX, c.sY);
  }
  for (Rectangle r : rectangles) {
    r.reset(random(width), random(height), 45);
  }
  tri.reset(width/2, 875);
  star.reset(random(40, width-40), -60, 40, 0);
}

public void sizeChange() {

  for (Circle c : circles) {
    c.r = 100;
  }
  for (Rectangle r : rectangles) {
    r.w = 20;
    r.h = 20;
  }
}

public void circDraw() {
  for (Circle c : circles) {
    c.move();
    c.wrap();
    c.display();
  }
}
public void rectDraw() {
  for (Rectangle r : rectangles) {
    r.move();
    r.wrap();
    r.display();
  }
}



public void mousePressed() { 

  canDisplay = true;

    if(pastWin){
      rectTotal++;
    }
    
    if (level == 1) {
		if (pastWin) { 
			for (int i = 0; i < 1; i++) {
			rectangles.add(new Rectangle(random(width), random(height)));
			}
		}	 
	}
    if (rectangles.size() > rectTotal + 1) {
      for (int i = rectangles.size() - 1; i  >= rectTotal + 1; i--) {
       Rectangle rect = rectangles.get(i);
        rectangles.remove(i);
      }
    }
  
}

public void rectanglesRemove() {
  for (int i = rectangles.size () - 1; i  >= rectTotal; i--) {
    Rectangle rect = rectangles.get(i);
    rectangles.remove(i);
  }
  rectTotal = 6;
}


public void triDraw(boolean canDisplay) {
  if (canDisplay) {
    tri.display();
    tri.click(mouseX, mouseY);
    tri.orientationCheck(mouseY);
  } else {
    tri.x = width/2;
    tri.y = 2000;
  }
}

public void starDraw() {
  star.display();
  star.simpleMove();
  star.r = random(-80, 80);
}

public void keyPressed() {
  if (key == ' ') save("Euclid.png");
}

class Wallpaper{
  float x;
  float y;
  Wallpaper(float xPos, float yPos ){
   x = xPos;
   y = yPos;
  }

  public void display(){
 
   fill(9, 113, 178, 20); 
   rect(0, 0, x, y);
  }
}
class Circle {

  float x;
  float y;
  float r; 
  float sX; 
  float sY;
  boolean collided;
  int c;

  Circle(float xPos, float yPos) {
    x = xPos;
    y = yPos;
    r = 45;
    sX = random(-2, 6);
    sY = random(-6, 8);
    collided  = false;
  }

  Circle(float xPos, float yPos, float radius, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = radius;
    sX = speedX;
    sY = speedY;
    collided  = false;
  }

  public void reset(float xPos, float yPos, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = 45;
    sX = speedX;
    sY = speedY;
    collided = false;
  }

  public void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }


  public void move() {
    x = x + sX;
    y = y + sY;
  }
  public void simpleMove() {
    y = y + sY;
  }

  public void wrap() {
    if (x > width + r) x = 0 - r;
    if (x < 0 - r) x = width + r;
    if (y < 0 - r) y = height + r;
    if (y > height + r) y = 0 - r;
  }

  public void stop() {
    sX = 0;
    sY = 0;
  }

  public void resume() {
    sX = random(4, 8);
    sY = random(4, 8);
  }

  public void display() {
    //   fill(c);
    if (!collided) {
      fill(45, 45, 45);
      ellipse(x + 4, y + 4, r, r);
      fill(255, 64, 129);
      // fill(0, 178, 51);
      ellipse(x, y, r, r);
    } else if (collided) {
      ellipse(x - 2000, y - 2000, r, r);
    }
  }
}
class Rectangle {

  float x;
  float y;
  float w;
  float h;
  float sX; //speed
  float sY;
  boolean collided;
  int c;

  Rectangle(float xPos, float yPos) {
    x = xPos;
    y = yPos;
    w = 45;
    h = 45;
    sX = random(-3, 8);
    sY = random(-5, 7);
    collided = false;
  }


  Rectangle (float xPos, float yPos, float wd, float ht, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    w = wd;
    h = ht;
    sX = speedX;
    sY = speedY;
    collided = false;
  }
  public void reset(float xPos, float yPos, float size) {
    x = xPos;
    y = yPos;
    h = size;
    w = size;
    collided = false;
  }

  public void setColor( float rd, float gr, float bl) {
    c = color(rd, gr, bl);
  }

  public void move() {
    x = x + sX;
    y = y + sY;
  }
  
   public void wrap() {
    if (x > width + w) x = 0 - w;
    if (x < 0 - w) x = width + w;
    if (y < 0 - w) y = height + w;
    if (y > height + w) y = 0 - w;
  }

  public void stop() {
    sX = 0;
    sY = 0;
  }
  public void resume() {
    sX = random(2, 9);
    sY = random(2, 9);
  }

  public void display() {
    //fill(c);
    if (!collided) {
      fill(45, 45, 45);
      rect(x + 5, y + 5, w, h);
      fill(178, 0, 0);
      rect(x, y, w, h);
    } else if (collided) {
      rect(x - 2000, y - 2000, w, h);
    }
  }
}
class Star {

  float x;
  float y;
  float r; 
  float sX; 
  float sY;
  boolean collided;
  int c;

  Star(float xPos, float yPos) {
    x = xPos;
    y = yPos;
    r = 70;
    sX = random(4, 8);
    sY = random(4, 8);
    collided  = false;
  }

  Star(float xPos, float yPos, float radius, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = radius;
    sX = speedX;
    sY = speedY;
    collided  = false;
  }

  public void reset(float xPos, float yPos, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = 40;
    sX = speedX;
    sY = speedY;
    collided = false;
  }

  public void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }

  public void bounce() {
    if (x > width || x < 0) {
      sX = sX * -1;
    }
    if (y > height || y < 0) {  
      sY = sY * -1;
    }
  }


  public void move() {
    x = x + sX;
    y = y + sY;
  }
  public void simpleMove() {
    y = y + sY;
  }


  public void stop() {
    sX = 0;
    sY = 0;
  }

  public void resume() {
    sX = random(4, 8);
    sY = random(4, 8);
  }

  public void display() {
    //   fill(c);
    if (!collided) {
      fill(45, 45, 45);
      ellipse(x + 4, y + 4, r, r);
      fill(c);
      ellipse(x, y, r, r);
    } else if (collided) {
      ellipse(x - 2000, y - 2000, r, r);
    }
  }
}
class Triangle {
  int c;
  float x, y;
  boolean collided;
  boolean deathCollide;
  boolean upOrientation;
  float easing = 0.1f;


  Triangle(float tempX, float tempY) {
    x= tempX;
    y= tempY;
    collided = false;
    deathCollide = false;
    upOrientation = true;
  }

  public void orientationCheck(float mouseY) {
    float msY = mouseY;
    if (msY > y) {
      upOrientation = false;
    } else if (msY < y) {
      upOrientation = true;
    }
  }  

  public void reset(float xPos, float yPos) {
    x = xPos;
    y = yPos;
    collided = false;
    deathCollide = false;
  }

  public void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }



  public void click(int mx, int my) {
    float targetX = mx;
    float dx = targetX - x;
    x += dx * easing;

    float targetY = my;
    float dy = targetY - y;
    y += dy * easing;
  }



  public boolean collision(float otherX, float otherY) {
    if (otherX > x - 35 && otherX < x + 35 && otherY > y - 32.5f && otherY < y + 32.5f) {
      collided = true;
    } 
    return collided;
  }


  public void display() {
    float offX = x + 5;
    float offY = y + 5;

    if (!deathCollide && upOrientation ) {
      fill(45, 45, 45);
      triangle(offX - 35, offY + 32.5f, offX, offY - 32.5f, offX + 35, offY + 32.5f );
      fill(255, 252, 25);
      triangle(x - 35, y + 32.5f, x, y - 32.5f, x + 35, y + 32.5f );
    } else if (!deathCollide && !upOrientation) {
      fill(45, 45, 45);
      triangle(offX - 35, offY - 32.5f, offX, offY + 32.5f, offX + 35, offY - 32.5f );
      fill(255, 252, 25);
      triangle(x - 35, y - 32.5f, x, y + 32.5f, x + 35, y - 32.5f);
    } else if (deathCollide) {
      triangle(x-5000, y - 5000, x - 5000, y - 5000, x - 5000, y - 5000);
    }
  }
}
  public void settings() {  size (768, 1024); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "EuclidsDream" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
