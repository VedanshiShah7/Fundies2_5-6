import javalib.funworld.WorldScene;

//representing a list of game pieces
interface ILoGamePiece {
  
  //creates a list with the given number of bullets
  ILoGamePiece createBullets(int bulletsLeft);
  
  //draws a world with all of the game pieces
  WorldScene draw(WorldScene scene);
  
  //moves the list of game pieces
  ILoGamePiece move();
  
  //spawns new ships every 28 ticks
  ILoGamePiece spawn(int ticks, int ships);
  
  //determines which side a ship will spawn on
  IGamePiece side();
  
  //removes any pieces that are off the screen
  ILoGamePiece remove(ILoGamePiece bullets);
  
  //removes any pieces that are off the screen
  int destroyed(ILoGamePiece bullets, int acc);
  
  //checks if a bullet is removed
  boolean checkRemoved();
  
  //counts number of bullets left
  int countBullets(int acc);
  
  //first bullet
  IGamePiece firstBullet();
  
  //rest of the bullets
  ILoGamePiece restBullet();
  
  //sees if this ship has collided with any given bullets
  boolean checkSame(IGamePiece bullet);
  
  //splits a bullet on impact
  IGamePiece split(IGamePiece bullet);
  
  //splits all bullets on impact
  ILoGamePiece splitAll(ILoGamePiece ships, ILoGamePiece soFar);
  
  public WorldScene getBullets(WorldScene scene);
  
  
  //-------methods for testing, has random seed input---
  //spawns new ships every 28 ticks
  ILoGamePiece spawnTest(int ticks, int ships, int seed);
  
  IGamePiece sideTest(int seed);
}