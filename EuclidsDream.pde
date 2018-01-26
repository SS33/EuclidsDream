

//import apwidgets.*;
//APMediaPlayer sound1;
//APMediaPlayer sound2;
//APMediaPlayer sound3;
//APMediaPlayer sound4;

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



void setup() {
  orientation(PORTRAIT);
  time = millis();

  size (768, 1024);
  gameFill = color( 20, 133, 204);

  font = loadFont("SegoeUI-Light-80.vlw");

  textFont(font);
  textSize(80);
  textAlign(CENTER);
  msgTime = 100;
  gameWin = false;
  pastWin = false;
  w = new Wallpaper(width, height);




  //  sound1 = new APMediaPlayer(this);
  //  sound2 = new APMediaPlayer(this);
  //  sound3 = new APMediaPlayer(this);
  //  sound4 = new APMediaPlayer(this);
  //  sound1.setMediaFile("sfx2.mp3");
  //  sound2.setMediaFile("sfx3.mp3");
  //  sound3.setMediaFile("powerup.mp3");
  //  sound4.setMediaFile("chordalSynthPattern02.mp3");

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

void draw() {
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


void showMessage(String msgText, int msgTime) {

  if (msgTime > 0) {
    alpha = alpha + 3;
    fill(200, 200, 200, alpha);
    text(msgText, width/2, height/2);
  }
}

void initStar() {
  if (starCounter > 0) {
    star.sY = 4;
    starCounter = 0;
  }
}

boolean winCheck() {
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



void collisionCheck() {

  for (Circle c : circles) {
    if (tri.collision(c.x, c.y)) {
      tri.collided = false;
      c.collided = true;

      //sound1.start();
    }
  }

  for (Rectangle r : rectangles) {
    if (tri.collision(r.x, r.y)) {
      tri.collided = false;
      tri.deathCollide = false;
      level = LOSE;
      //  sound2.start();
    }
  }
  if (tri.collision(star.x, star.y)) {
    tri.collided= false;
    star.collided = true;
    sizeChange();
    //  sound3.start();
  }
}

void timeDelay() {
  if (millis() - time >= delay) {
    time = millis();
  }
}


void reset() {
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

void sizeChange() {

  for (Circle c : circles) {
    c.r = 100;
  }
  for (Rectangle r : rectangles) {
    r.w = 20;
    r.h = 20;
  }
}

void circDraw() {
  for (Circle c : circles) {
    c.move();
    c.wrap();
    c.display();
  }
}
void rectDraw() {
  for (Rectangle r : rectangles) {
    r.move();
    r.wrap();
    r.display();
  }
}



void mousePressed() { 

  canDisplay = true;

  if (pastWin) {
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

void rectanglesRemove() {
  for (int i = rectangles.size () - 1; i  >= rectTotal; i--) {
    Rectangle rect = rectangles.get(i);
    rectangles.remove(i);
  }
  rectTotal = 6;
}


void triDraw(boolean canDisplay) {
  if (canDisplay) {
    tri.display();
    tri.click(mouseX, mouseY);
    tri.orientationCheck(mouseY);
  } else {
    tri.x = width/2;
    tri.y = 2000;
  }
}

void starDraw() {
  star.display();
  star.simpleMove();
  star.r = random(-80, 80);
}

void keyPressed() {
  if (key == ' ') save("Euclid.png");
}