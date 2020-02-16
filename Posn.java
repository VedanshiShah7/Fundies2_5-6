//representing an objects x and y coordinates
class Posn implements IConstants{
  double x;
  double y;
  
  Posn(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  /* fields:
   *  this.x -- int
   *  this.y -- int
   * methods:
   *  changeBullet(int) -- Posn
   *  changeShip(int) -- Posn
   *  sameLoc(Posn) -- boolean
   *  offScreen() -- boolean
   *  getX() -- int
   *  getY() -- int
   */
  
  //changes the location of a bullet{
  Posn changeBullet(double xSpeed, double ySpeed) {
    return new Posn(x + xSpeed, y + ySpeed);
  }
  
  //changes the location of a ship{
  Posn changeShip(double speed) {
    return new Posn(x + speed, y);
  }
  
  //determines if a bullet is in the same location as a ship
  boolean sameLoc(Posn bullet) {
    return Math.abs(this.x - bullet.x) <= IConstants.SHIP_RADIUS
        && Math.abs(this.y - bullet.y) <= IConstants.SHIP_RADIUS;
  }
  
  //determines if this location is off-screen
  boolean offScreen() {
  return this.x > SCREEN_WIDTH || this.x < 0 
      || this.y > SCREEN_HEIGHT || this.y < 0;
  }
  
  //retrieves this.x 
  double getX() {
    return this.x;
  }
  
  //retrieves this.y
  double getY() {
    return this.y;
  }
}