import java.awt.Color;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;

//an abstract game piece
abstract class AGamePiece implements IGamePiece {
  double xSpeed;
  double ySpeed;
  int radius;
  Color color;
  Posn loc;
  
  AGamePiece(double xSpeed, double ySpeed, int radius, Color color, Posn loc) {
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    this.radius = radius;
    this.color = color;
    this.loc = loc;
  }
  
  /* fields:
   *  this.speed -- int
   *  this.radius -- radius
   *  this.color -- Color
   *  this.loc -- Posn
   * methods:
   *  draw(WorldScene) -- WorldScene
   *  offScreen() -- boolean
   *  getLoc() -- Posn
   *  sameLocation(IGamePiece) -- boolean
   * methods for fields:
   *  this.loc.getX() -- int
   *  this.loc.getY() -- int
   *  this.loc.offScreen() -- boolean
   *  this.loc.sameLoc(IGamePiece) -- boolean
   */
  
  //draws this IGamePiece
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, this.color), 
        (int)Math.round(this.loc.getX()), (int)Math.round(this.loc.getY()));
  }
  
  //determines if this IGamePiece is off-screen
  public boolean offScreen() {
    return this.loc.offScreen();
  }
  
  public Posn getLoc() {
    return this.loc;
  }
  
  //determines if these are in the same Location
  public boolean sameLocation(IGamePiece bullet) {
    return this.loc.sameLoc(bullet.getLoc());
  }
  
  public ILoGamePiece getBullets() {
    return new MtLoGamePiece();
  }
  
}