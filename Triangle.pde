class Triangle {
  color c;
  float x, y;
  boolean collided;
  boolean deathCollide;
  boolean upOrientation;
  float easing = 0.1;


  Triangle(float tempX, float tempY) {
    x= tempX;
    y= tempY;
    collided = false;
    deathCollide = false;
    upOrientation = true;
  }

  void orientationCheck(float mouseY) {
    float msY = mouseY;
    if (msY > y) {
      upOrientation = false;
    } else if (msY < y) {
      upOrientation = true;
    }
  }  

  void reset(float xPos, float yPos) {
    x = xPos;
    y = yPos;
    collided = false;
    deathCollide = false;
  }

  void setColor( float rd, float gr, float bl ) {
    c = color(rd, gr, bl);
  }



  void click(int mx, int my) {
    float targetX = mx;
    float dx = targetX - x;
    x += dx * easing;

    float targetY = my;
    float dy = targetY - y;
    y += dy * easing;
  }



  boolean collision(float otherX, float otherY) {
    if (otherX > x - 35 && otherX < x + 35 && otherY > y - 32.5 && otherY < y + 32.5) {
      collided = true;
    } 
    return collided;
  }


  void display() {
    float offX = x + 5;
    float offY = y + 5;

    if (!deathCollide && upOrientation ) {
      fill(45, 45, 45);
      triangle(offX - 35, offY + 32.5, offX, offY - 32.5, offX + 35, offY + 32.5 );
      fill(255, 252, 25);
      triangle(x - 35, y + 32.5, x, y - 32.5, x + 35, y + 32.5 );
    } else if (!deathCollide && !upOrientation) {
      fill(45, 45, 45);
      triangle(offX - 35, offY - 32.5, offX, offY + 32.5, offX + 35, offY - 32.5 );
      fill(255, 252, 25);
      triangle(x - 35, y - 32.5, x, y + 32.5, x + 35, y - 32.5);
    } else if (deathCollide) {
      triangle(x-5000, y - 5000, x - 5000, y - 5000, x - 5000, y - 5000);
    }
  }
}

