import tester.Tester;
import java.awt.Color;
import java.util.Random;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;

interface ILoGamePiece {
  
}

class MtLoGamePiece implements ILoGamePiece {
  
}

class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;
  
  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }
}

interface IGamePiece extends IConstants{
  //draws the current world
  WorldScene draw(WorldScene scene);
  //moves an object in the world
  WorldScene move(WorldScene scene);
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
  
  //draws the current world
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(BULLET_RADIUS, OutlineMode.SOLID, BULLET_COLOR), 
        this.x, this.y);
  }
  
  //moves this bullet by moving it by its speed
  public Bullet move(int minX, int maxX, int minY, int maxY) {
    return new Bullet(this.speed, this.radius, this.color, this.x + this.speed, this.y + this.speed);
  }

  @Override
  public WorldScene move(WorldScene scene) {
    // TODO Auto-generated method stub
    return null;
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
  
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(SHIP_RADIUS, OutlineMode.SOLID, SHIP_COLOR), this.x, this.y);
  }
  
  //moves this ship by moving it by its speed
  public Ship move() {
    return new Ship(this.speed, this.radius, this.color, this.x + this.speed, this.y);
  }

  @Override
  public WorldScene move(WorldScene scene) {
    // TODO Auto-generated method stub
    return null;
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
  
  NBulletsWorld(Bullet bullet) {
    this(new Bullet(IConstants.BULLET_SPEED, 50, IConstants.BULLET_COLOR, 250, 300), new Ship(3, 100, IConstants.SHIP_COLOR, 40, 60), new Random());
  }
  
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    return this.bullets.draw(scene);
  }
  
  public World onTick(){
    return new NBulletsWorld(this.bullets.move(0, this.getEmptyScene().width, 0, this.getEmptyScene().height));
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
  int BULLET_RADIUS = 2;
  //add to current bullet radius
  int BULLET_AFTER_EXPLOSION = 2;
  int MAX_BULLET_RADIUS = 10;
  Color BULLET_COLOR = Color.pink;
  int BULLET_SPEED = 8;
  
  //ships
  int SPAWN = 1;
  Range SHIP_SPAWN_NUMBER = new Range(1, 3);
  int SHIP_RADIUS = (1 / 30) * SCREEN_HEIGHT;
  Color SHIP_COLOR = Color.cyan;
  double SHIP_SPEED = (1.0 / 2.0) * BULLET_SPEED;
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
    NBulletsWorld myWorld = new NBulletsWorld(new Bullet(IConstants.BULLET_SPEED, 50, IConstants.BULLET_COLOR, 250, 300));
    return myWorld.bigBang(SCREEN_WIDTH, SCREEN_HEIGHT, IConstants.TICK_RATE);
  }
}
