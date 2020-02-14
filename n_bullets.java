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

interface IGamePiece extends IConstants {
}

class Bullet implements IGamePiece {
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
    this.y = y;
  }

  // draws the current world
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(BULLET_RADIUS, OutlineMode.SOLID, BULLET_COLOR),
        this.x, this.y);
  }

  // moves this bullet by moving it by its speed
  public Bullet move(int minX, int maxX, int minY, int maxY) {
    return new Bullet(this.speed, this.radius, this.color, this.x + this.speed,
        this.y + this.speed);
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
    this.y = y;
  }

  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(SHIP_RADIUS, OutlineMode.SOLID, SHIP_COLOR), this.x,
        this.y);
  }

  // moves this ship by moving it by its speed
  public Ship move() {
    return new Ship(this.speed, this.radius, this.color, this.x + this.speed, this.y);
  }
}

class NBulletsWorld extends World {

  Bullet bullets;
  Ship ships;
  Random rand;

  NBulletsWorld() {
    this.bullets = new Bullet(IConstants.BULLET_SPEED, 50, IConstants.BULLET_COLOR, 250, 300);
    this.ships = new Ship(3, 100, IConstants.SHIP_COLOR, 40, 60);
    this.rand = new Random();
  }

  NBulletsWorld(Bullet bullets, Ship ships) {
    this.bullets = bullets;
    this.ships = ships;
  }

  NBulletsWorld(Bullet bullets) {
    this.bullets = bullets;
  }

  NBulletsWorld(Ship ships) {
    this.ships = ships;
  }

  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    return this.ships.draw(this.bullets.draw(scene));
  }

  public World onTick() {
    return new NBulletsWorld(this.bullets.move(0, 10, 0, 10), this.ships.move());
  }

  public World onKeyReleased(String key) {
    Random r = new Random();
    switch (key) {
    case " ":
      return new NBulletsWorld(
          new Bullet(r.nextInt(IConstants.BULLET_SPEED), r.nextInt(IConstants.BULLET_RADIUS),
              IConstants.BULLET_COLOR, r.nextInt(250), r.nextInt(300)),
          new Ship(r.nextInt(3), r.nextInt(100), IConstants.SHIP_COLOR, r.nextInt(40),
              r.nextInt(60)));
    }
    return this;
  }
}

interface IConstants {
  // world
  int SCREEN_WIDTH = 500;
  int SCREEN_HEIGHT = 300;
  double SCREEN_SEVENTH = SCREEN_HEIGHT / 7.0;
  Color FONT_COLOR = Color.black;
  int FONT_SIZE = 13;
  double TICK_RATE = 1.0 / 28.0;

  // bullets
  int BULLET_RADIUS = 2;
  // add to current bullet radius
  int BULLET_AFTER_EXPLOSION = 2;
  int MAX_BULLET_RADIUS = 10;
  Color BULLET_COLOR = Color.pink;
  int BULLET_SPEED = 8;

  // ships
  int SPAWN = 1;
  Range SHIP_SPAWN_NUMBER = new Range(1, 3);
  int SHIP_RADIUS = SCREEN_HEIGHT / 30;
  Color SHIP_COLOR = Color.cyan;
  double SHIP_SPEED = BULLET_SPEED / 2.0;
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

class RunNBulletsWorld {
  boolean testPlay(Tester t) {
    NBulletsWorld myWorld = new NBulletsWorld(
        new Bullet(IConstants.BULLET_SPEED, 50, IConstants.BULLET_COLOR, 250, 300),
        new Ship(3, 100, IConstants.SHIP_COLOR, 40, 60));
    return myWorld.bigBang(500, 500, IConstants.TICK_RATE);
  }
}

// new Bullet(r.nextInt(200), r.nextInt(2), Color.BLACK, r.nextInt(20), r.nextInt(20))