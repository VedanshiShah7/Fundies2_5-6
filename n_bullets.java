import tester.Tester;
import java.awt.Color;
import java.util.Random;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;

interface ILoGamePiece {
  ILoGamePiece newList(int bullets, ILoGamePiece soFar);
}

class MtLoGamePiece implements ILoGamePiece {
  public ILoGamePiece newList(int bullets, ILoGamePiece soFar) {
    return soFar;
  }
}

class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;
  
  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoGamePiece newList(int bullets, ILoGamePiece soFar) {
    if (bullets > 0) {
      return this.newList(bullets - 1, new ConsLoGamePiece(new Bullet(150, 301), soFar));
    }
    else {
      return soFar;
    }
  }
}

interface IGamePiece extends IConstants{
  //draws the current world
  WorldScene draw(WorldScene scene);
}

class Bullet implements IGamePiece{
  int speed;
  int radius;
  Color color;
  int x;
  int y;
  
  Bullet(int speed, int radius, Color color, int x, int y) {
    this.speed = speed;
    this.radius = radius;
    this.color = color;
    this.x = x;
    this.y =y;
  }
  
  Bullet(int x, int y) {
    this.speed = SHIP_SPEED;
    this.radius = SHIP_RADIUS;
    this.color = SHIP_COLOR;
    this.x = x;
    this.y = y;
  }
  
  //draws the current world
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), 
        this.x, this.y);
  }
  
  //moves this bullet by moving it by its speed
  public Bullet move() {
    return new Bullet(this.speed, this.radius, this.color, this.x + this.speed, this.y + this.speed);
  }

}

class Ship implements IGamePiece {
  int speed;
  int radius;
  Color color;
  int x;
  int y;
  
  Ship(int speed, int radius, Color color, int x, int y) {
    this.speed = speed;
    this.radius = radius;
    this.color = color;
    this.x = x;
    this.y =y;
  }
  
  Ship(int x, int y) {
    this.speed = SHIP_SPEED;
    this.radius = SHIP_RADIUS;
    this.color = SHIP_COLOR;
    this.x = x;
    this.y = y;
  }
  
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), this.x, this.y);
  }
  
  //moves this ship by moving it by its speed
  public Ship move() {
    return new Ship(this.speed, this.radius, this.color, this.x + this.speed, this.y);
  }
}


class NBulletsWorld extends World {
  
  Bullet bullets;
  Ship ships;
  Random rand;
  
  NBulletsWorld(Bullet bullets, Ship ships, Random rand) {
    this.bullets = bullets;
    this.ships = ships;
    this.rand = rand;
  }
  
  NBulletsWorld(Bullet bullets, Ship ships) {
    this.bullets = bullets;
    this.ships = ships;
  }
  
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    return this.ships.draw(this.bullets.draw(scene));
  }
  
  public World onTick(){
    return new NBulletsWorld(this.bullets.move(), this.ships.move());
  }
  
  public World onKeyReleased(String key) {
    //Random r = new Random();
    switch (key) {
    case " ":
      return new NBulletsWorld(this.bullets, this.ships, this.rand);
    }
    return this;
  }
}

interface IConstants {
  //world
  int SCREEN_WIDTH = 500;
  int SCREEN_HEIGHT = 300;
  double SCREEN_SEVENTH = SCREEN_HEIGHT / 7.0;
  Color FONT_COLOR = Color.black;
  int FONT_SIZE = 13;
  double TICK_RATE  = 1.0 / 28.0;
  
  //bullets
  int BULLET_RADIUS = 10;
  //add to current bullet radius
  int BULLET_AFTER_EXPLOSION = 2;
  int MAX_BULLET_RADIUS = 10;
  Color BULLET_COLOR = Color.pink;
  int BULLET_SPEED = -8;
  
  //ships
  int SPAWN = 1;
  Range SHIP_SPAWN_NUMBER = new Range(1, 3);
  int SHIP_RADIUS = 40;
  Color SHIP_COLOR = Color.cyan;
  int SHIP_SPEED = BULLET_SPEED /2;
  Range SHIP_SPAWN_LOC = new Range(SCREEN_SEVENTH, SCREEN_HEIGHT - SCREEN_SEVENTH);

}

class Range {
  double low;
  double high;
  
  Range(double low, double high) {
    this.low = low;
    this.high = high;
  }
  
  public boolean inRange(double number) {
    return (number >= low && number <= high);
  }
}

class RunNBulletsWorld implements IConstants{
  boolean testPlay(Tester t) {
    NBulletsWorld myWorld = new NBulletsWorld(new Bullet(100, 100), new Ship(100, 100), new Random());
    return myWorld.bigBang(SCREEN_WIDTH, SCREEN_HEIGHT, TICK_RATE);
  }

}
