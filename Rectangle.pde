class Rectangle {

  float x;
  float y;
  float w;
  float h;
  float sX; //speed
  float sY;
  boolean collided;
  color c;

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
  void reset(float xPos, float yPos, float size) {
    x = xPos;
    y = yPos;
    h = size;
    w = size;
    collided = false;
  }

  void setColor( float rd, float gr, float bl) {
    c = color(rd, gr, bl);
  }

  void move() {
    x = x + sX;
    y = y + sY;
  }
  
   void wrap() {
    if (x > width + w) x = 0 - w;
    if (x < 0 - w) x = width + w;
    if (y < 0 - w) y = height + w;
    if (y > height + w) y = 0 - w;
  }

  void stop() {
    sX = 0;
    sY = 0;
  }
  void resume() {
    sX = random(2, 9);
    sY = random(2, 9);
  }

  void display() {
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

