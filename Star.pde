class Star {

  float x;
  float y;
  float r; 
  float sX; 
  float sY;
  boolean collided;
  color c;

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

  void reset(float xPos, float yPos, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = 40;
    sX = speedX;
    sY = speedY;
    collided = false;
  }

  void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }

  void bounce() {
    if (x > width || x < 0) {
      sX = sX * -1;
    }
    if (y > height || y < 0) {  
      sY = sY * -1;
    }
  }


  void move() {
    x = x + sX;
    y = y + sY;
  }
  void simpleMove() {
    y = y + sY;
  }


  void stop() {
    sX = 0;
    sY = 0;
  }

  void resume() {
    sX = random(4, 8);
    sY = random(4, 8);
  }

  void display() {
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

