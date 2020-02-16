import javalib.funworld.WorldScene;

//representing a game piece
interface IGamePiece extends IConstants{
  
  //draws this IGamePiece
  WorldScene draw(WorldScene scene);
  
  //moves this IGamePiece
  IGamePiece move();
  
  //determines if this IGamePiece is off-screen
  boolean offScreen();
  
  //determines if this IGamePiece is in the same location as
  //the given 
  boolean sameLocation(IGamePiece others);
  
  //gets the location of this IGamePiece
  Posn getLoc();
  
  //explodes a bullet
  IGamePiece explode();
  
  ILoGamePiece getBullets();
}