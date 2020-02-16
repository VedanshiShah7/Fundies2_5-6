import java.util.Random;
import javalib.funworld.WorldScene;

//representing a non-empty list of game pieces
class ConsLoGamePiece implements ILoGamePiece {
  IGamePiece first;
  ILoGamePiece rest;
  
  ConsLoGamePiece(IGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* fields:
   *  this.first -- IGamePiece
   *  this.rest -- ILoGamePiece
   * methods:
   *  createBullets(int) -- ILoGamePiece
   *  draw(WorldScene) -- WorldScene
   *  move() -- ILoGamePiece
   *  spawn(int, int) -- ILoGamePiece
   *  side() -- IGamePiece
   *  remove(ILoGamePiece) -- ILoGamePiece
   *  checkRemoved() -- boolean
   *  countBullets(int) -- int
   *  firstBullet() -- IGamePiece
   *  restBullets() -- IGamePiece
   *  checkSame(IGamePiece) -- boolean
   *  //methods for creating consistent randoms
   *  spawnTest(int, int, int) -- ILoGamePiece
   *  sideTest(int) -- IGamePiece
   * methods for fields: 
   *  this.first.draw(WorldScene) -- WorldScene
   *  this.first.offScreen() -- boolean
   *  this.first.getLoc() -- Posn
   *  this.first.sameLocation(IGamePiece) -- boolean
   *  this.rest.createBullets(int) -- ILoGamePiece
   *  this.rest.draw(WorldScene) -- WorldScene
   *  this.rest.move() -- ILoGamePiece
   *  this.rest.spawn(int, int) -- ILoGamePiece
   *  this.rest.side() -- IGamePiece
   *  this.rest.remove(ILoGamePiece) -- ILoGamePiece
   *  this.rest.checkRemoved() -- boolean
   *  this.rest.countBullets(int) -- int
   *  this.rest.firstBullet() -- IGamePiece
   *  this.rest.restBullets() -- IGamePiece
   *  this.rest.checkSame(IGamePiece) -- boolean
   *  //methods for creating consistent randoms
   *  this.rest.spawnTest(int, int, int) -- ILoGamePiece
   *  this.rest.sideTest(int) -- IGamePiece
   */
  
  //creates a list with the given number of bullets
  public ILoGamePiece createBullets(int bulletsLeft) {
    if (bulletsLeft > 0) {
      return new ConsLoGamePiece(new Bullet(), this).createBullets(bulletsLeft -1);
    }
    else {
      return this;
    }
  }
  
  //draws a world with all of the game pieces
  public WorldScene draw(WorldScene scene) {
    return this.first.draw(this.rest.draw(scene));
  }
  
  //moves these game pieces
  public ILoGamePiece move() {
    return new ConsLoGamePiece(this.first.move(), this.rest.move());
  }
  
  //spawns new ships every 28 ticks
  public ILoGamePiece spawn(int ticks, int ships) {
    if (ticks % (1 / IConstants.TICK_RATE) == 0) {
      if (ships > 0) {
        return new ConsLoGamePiece(this.side(), this).spawn(ticks, ships - 1);
      }
      else {
        return this;
      }
    }
    else {
      return this;
    }
  }
  
  public IGamePiece side() {
    if (new Random().nextInt(2) == 0) {
      return new ShipLeft();
    }
    else {
      return new ShipRight();
    }
  }
  
  //removes any game pieces that are off-screen
  public ILoGamePiece remove(ILoGamePiece bullets) {
    if (this.first.offScreen() || bullets.checkSame(this.first)) {
      return this.rest.remove(bullets);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.remove(bullets));
    }
  }
  
  //rcounts the number of destroyed ships
  public int destroyed(ILoGamePiece bullets, int acc) {
    if (bullets.checkSame(this.first)) {
      return this.rest.destroyed(bullets, 1 + acc);
    }
    else {
      return this.rest.destroyed(bullets, acc);
    }
  }
  
  //checks if a list of bullet is removed
  public boolean checkRemoved() {
    return (this.first.offScreen() && this.rest.checkRemoved());
  }
  
  //counts number of bullets left
  public int countBullets(int acc) {
    return this.rest.countBullets(acc + 1);
  }
  
  //first bullet
  public IGamePiece firstBullet() {
    return this.first;
  }
  
  //rest of the bullets
  public ILoGamePiece restBullet() {
    return this.rest;
  }
  
  //checks if a ship collided with any of this list of bullets
  public boolean checkSame(IGamePiece ship) {
    return ship.sameLocation(this.first) || this.rest.checkSame(ship);
  }
  
  //splits all bullets that hit ships
  public ILoGamePiece splitAll(ILoGamePiece ships) {
    return new ConsLoGamePiece(ships.split(this.first), this.rest.splitAll(ships));
  }
  
  //checks if a bullet collided with any of this list of ships
  public IGamePiece split(IGamePiece bullet) {
    if (bullet.sameLocation(this.first)) {
      return bullet.explode();
    }
    else {
      return this.rest.split(bullet);
    }
  }
  
  public WorldScene getBullets(WorldScene scene) {
    return this.first.getBullets().draw(scene);
  }
  
  //---------methods for testing, uses random seed------
  
  
  //spawns new ships every 28 ticks
  public ILoGamePiece spawnTest(int ticks, int ships, int seed) {
    if (ticks % (1 / IConstants.TICK_RATE) == 0) {
      if (ships > 0) {
        return new ConsLoGamePiece(this.sideTest(seed), this).spawn(ticks, ships - 1);
      }
      else {
        return this;
      }
    }
    else {
      return this;
    }
  }
  
  //spawns a ship
  public IGamePiece sideTest(int seed) {
    if (new Random(seed).nextInt(2) == 0) {
      return new ShipLeft();
    }
    else {
      return new ShipRight(new Posn(500, 115));
    }
  }
  
}