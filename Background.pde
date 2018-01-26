
class Wallpaper{
  float x;
  float y;
  Wallpaper(float xPos, float yPos ){
   x = xPos;
   y = yPos;
  }

  void display(){
 
   fill(9, 113, 178, 20); 
   rect(0, 0, x, y);
  }
}


