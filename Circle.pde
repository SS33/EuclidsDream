class Circle {

  float x;
  float y;
  float r; 
  float sX; 
  float sY;
  boolean collided;
  color c;

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

  void reset(float xPos, float yPos, float speedX, float speedY) {
    x = xPos;
    y = yPos;
    r = 45;
    sX = speedX;
    sY = speedY;
    collided = false;
  }

  void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }


  void move() {
    x = x + sX;
    y = y + sY;
  }
  void simpleMove() {
    y = y + sY;
  }

  void wrap() {
    if (x > width + r) x = 0 - r;
    if (x < 0 - r) x = width + r;
    if (y < 0 - r) y = height + r;
    if (y > height + r) y = 0 - r;
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
      fill(255, 64, 129);
      // fill(0, 178, 51);
      ellipse(x, y, r, r);
    } else if (collided) {
      ellipse(x - 2000, y - 2000, r, r);
    }
  }
}

