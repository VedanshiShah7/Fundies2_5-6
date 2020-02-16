import java.util.Random;

import javalib.funworld.WorldScene;

//representing an empty list of game pieces
class MtLoGamePiece implements ILoGamePiece {
  
  //creates a list with the given number of bullets
  public ILoGamePiece createBullets(int bulletsLeft) {
    if (bulletsLeft > 0) {
      return new ConsLoGamePiece(new Bullet(), this).createBullets(bulletsLeft -1);
    }
    else {
      return this;

    } 
  }
  
  /* fields:
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
   */
  
  //draws a world with all of the game pieces
  public WorldScene draw(WorldScene scene) {
    return scene;
  }
  
  //moves these game pieces
  public ILoGamePiece move() {
    return this;
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
  
  //determines which side a ship will spawn on
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
    return this;
  }
  
  //removes any game pieces that are off-screen
  public int destroyed(ILoGamePiece bullets, int acc) {
    return acc;
  }
  
  //checks if a bullet is removed
  public boolean checkRemoved() {
    return true;
  }
  
  //counts number of bullets left
  public int countBullets(int acc) {
    return acc;
  }
  
  //first bullet
  public IGamePiece firstBullet() {
    return new Bullet();
  }
  
  //rest of the bullets
  public ILoGamePiece restBullet() {
    return this;
  }
  
  //checks if the two IGamePiees are the same
  public boolean checkSame(IGamePiece bullet) {
    return false;
  }
  
  //splits a bullet
  public IGamePiece split(IGamePiece bullet) {
    return bullet;
  }

  //splits all bullets
  public ILoGamePiece splitAll(ILoGamePiece ships) {
    return this;
  }
  
  public WorldScene getBullets(WorldScene scene) {
    return scene;
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