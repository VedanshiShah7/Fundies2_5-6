import java.awt.Color;
import java.util.Random;

//representing a left ship
class ShipLeft extends AGamePiece {
  
  ShipLeft(double xSpeed, double ySpeed, int radius, Color color, Posn loc) {
    super(xSpeed, ySpeed, radius, color, loc);
  }

  ShipLeft() {
    super(SHIP_SPEED, 0, SHIP_RADIUS, SHIP_COLOR, new Posn( 0,
        new Random().nextInt((SCREEN_HEIGHT - SCREEN_SEVENTH) - SCREEN_SEVENTH + 1) + SCREEN_SEVENTH));
  }
  
  ShipLeft(Posn loc) {
    super(SHIP_SPEED, 0, SHIP_RADIUS, SHIP_COLOR, loc);
  }
  
  /* methods:
   *  move -- IGamePiece
   */
  
  //moves this IGamePiece
  public IGamePiece move() {
    return new ShipLeft(this.xSpeed, this.ySpeed, this.radius, this.color, loc.changeShip(this.xSpeed * -1));
  }
  
  //explodes a bullet
  public IGamePiece explode() {
    return this;
  }
}

//representing a ship
class ShipRight extends AGamePiece {

  ShipRight(double xSpeed, double ySpeed, int radius, Color color, Posn loc) {
    super(xSpeed, ySpeed, radius, color, loc);
  }

  //FIX LATER: DIRECTION
  ShipRight() {
    super(SHIP_SPEED, 0, SHIP_RADIUS, SHIP_COLOR, new Posn(SCREEN_WIDTH,
        new Random().nextInt((SCREEN_HEIGHT - SCREEN_SEVENTH) - SCREEN_SEVENTH + 1) + SCREEN_SEVENTH));
  }
  
  ShipRight(Posn loc) {
    super(SHIP_SPEED, 0, SHIP_RADIUS, SHIP_COLOR, loc);
  }
  
  /* methods:
   *  move -- IGamePiece
   */

  //moves this IGamePiece
  public IGamePiece move() {
    return new ShipRight(this.xSpeed, this.ySpeed, this.radius, this.color, loc.changeShip(this.xSpeed));
  }
  
  
  //explodes a bullet
  public IGamePiece explode() {
    return this;
  }
}