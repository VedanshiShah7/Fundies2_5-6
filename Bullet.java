import java.awt.Color;

//representing a bullet
class Bullet extends AGamePiece {
  ILoGamePiece bullets;
  int n;
  int max;
  
  Bullet(double xSpeed, double ySpeed, int radius, Color color, Posn loc) {
    super(xSpeed, ySpeed, radius, color, loc);
    this.bullets = new MtLoGamePiece();
    this.n = 1;
    this.max = 1;
  }
  
  Bullet(double xSpeed, double ySpeed, int radius, Color color, Posn loc, int n, int max) {
    super(0, ySpeed, radius, color, loc);
    this.bullets = new MtLoGamePiece();
    this.n = n;
    this.max = max;
  }
  
  Bullet() {
    super(0, BULLET_SPEED, MIN_BULLET_RADIUS, BULLET_COLOR, BULLET_START);
    this.bullets = new MtLoGamePiece();
    this.n = 1;
    this.max = 1;
  }
  
  Bullet(Posn loc) {
    super(0, BULLET_SPEED, MIN_BULLET_RADIUS, BULLET_COLOR, loc);
    this.bullets = new MtLoGamePiece();
    this.n = 1;
    this.max = 1;
  }
  
  Bullet(int max) {
    super(0, BULLET_SPEED, MIN_BULLET_RADIUS, BULLET_COLOR, BULLET_START);
    this.bullets = new MtLoGamePiece();
    this.n = 1;
    this.max = max;
  }
  
  /* methods:
   *  move -- IGamePiece
   */
  
  //moves this IGamePiece
  public IGamePiece move() {
    return new Bullet(this.xSpeed, this.ySpeed, this.radius, this.color, loc.changeBullet(this.xSpeed, this.ySpeed), this.n, this.max);
  }
  
  //changes a bullet when it explodes
  public IGamePiece explode() {
    return new Bullet(this.xSpeed(this.max + 1, this.n + 1), this.ySpeed(this.max + 1, this.n + 1), this.radius + BULLET_AFTER_EXPLOSION, Color.RED, this.loc);
  }
  
  //finds the angle of the bullet
  public double theta(int max, int n) {
    return (n * (360 / (max + 1)) * (Math.PI / 180.0));
  }
  
  //finds the x speed
  public double xSpeed(int max, int n) {
    return Math.cos(this.theta(max, n)) * BULLET_SPEED;
  }
  
  //finds the y speed
  public double ySpeed(int max, int n) {
    return Math.sin(this.theta(max, n)) * BULLET_SPEED;
  }
  
  
  public ILoGamePiece getBullets() {
    return this.bullets;
  }
}