import java.awt.Color;
import java.util.Random;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;

//representing the current world state
class NBulletsWorld extends World {
  ILoGamePiece bulletsLeft;
  ILoGamePiece ships;
  int ticks;
  ILoGamePiece bulletsShot;
  int destroyed;
  
  //takes in everything
  NBulletsWorld(ILoGamePiece bulletsLeft, ILoGamePiece ships, int ticks, ILoGamePiece bulletsShot) {
    this.bulletsLeft = bulletsLeft;
    this.ships = ships;
    this.ticks = ticks;
    this.bulletsShot = bulletsShot;
  }
  
  //takes in everything
  NBulletsWorld(ILoGamePiece bulletsLeft, ILoGamePiece ships, int ticks, ILoGamePiece bulletsShot, int destroyed) {
    this.bulletsLeft = bulletsLeft;
    this.ships = ships;
    this.ticks = ticks;
    this.bulletsShot = bulletsShot;
    this.destroyed = destroyed;
  }
  
  NBulletsWorld(ILoGamePiece bulletsLeft, ILoGamePiece ships) {
    this.bulletsLeft = bulletsLeft;
    this.ships = ships;
  }
  
  //only takes in initial bullet number and creates a list from there
  NBulletsWorld(int initialBullets) {
    this.bulletsLeft = new MtLoGamePiece().createBullets(initialBullets);
    this.ships = new MtLoGamePiece();
    this.ticks = 0;
    this.bulletsShot = new MtLoGamePiece();
    this.destroyed = 0;
  }
  
  NBulletsWorld(ILoGamePiece ships) {
    this.ships = ships;
  }
  
  /* fields:
   *  bulletsLeft -- ILoGamePiece
   *  ships -- ILoGamePiece
   *  ticks -- int
   *  bulletsShot -- ILoGamePiece
   * methods:
   *  onTick() -- World
   *  remove() -- NBulletsWorld
   *  addTick() -- NBulletsWorld
   *  spawn() -- NBulletsWorld
   *  moveAll() -- NBulletsWorld
   *  makeScene() -- WorldScene
   *  onKeyEvent(String) -- World
   *  shotBullets() -- ILoGamePiece
   *  worldEnds() -- WorldEnd
   */
  
  //does stuff on-tick
  public World onTick() {
    return this.checkDestroyed().remove().addTick().spawn().moveAll();
  }  
  
  //splits a bullet
  public NBulletsWorld split() {
    return new NBulletsWorld(this.bulletsLeft, this.ships, 
        this.ticks, this.bulletsShot.splitAll(this.ships), this.destroyed);
  }
  
  //checks if any ships were destroyed
  public NBulletsWorld checkDestroyed() {
    return new NBulletsWorld(this.bulletsLeft, this.ships, 
        this.ticks, this.bulletsShot.splitAll(this.ships), this.ships.destroyed(this.bulletsShot, 0) + this.destroyed);
  }
  
  //removes objects that are not on the screen 
  public NBulletsWorld remove() {
    return new NBulletsWorld(this.bulletsLeft, this.ships.remove(this.bulletsShot), 
        this.ticks, this.bulletsShot.remove(new MtLoGamePiece()));
  }
  
  //adds a tick to the world
  public NBulletsWorld addTick() {
    return new NBulletsWorld(this.bulletsLeft, this.ships, this.ticks + 1, this.bulletsShot);
  }
  
  //spawns ships every second
  public NBulletsWorld spawn() {
    return new NBulletsWorld(this.bulletsLeft, this.ships.spawn(this.ticks, (new Random().nextInt(3)) + 1), 
        this.ticks, this.bulletsShot);
  }
  
  //moves ships
  public NBulletsWorld moveAll() {
    return new NBulletsWorld(this.bulletsLeft, this.ships.move(), this.ticks, this.bulletsShot.move());
  }
  
  //draws each object
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(IConstants.SCREEN_WIDTH, IConstants.SCREEN_HEIGHT);
    return this.ships.draw(this.bulletsShot.getBullets(this.bulletsShot.draw((scene.placeImageXY(
        new TextImage("Bullets left: " + this.bulletsLeft.countBullets(0) 
        + "; Ships destroyed: " + this.destroyed, 
        IConstants.FONT_COLOR), IConstants.SCREEN_SEVENTH * 3, 
        IConstants.SCREEN_HEIGHT - (IConstants.SCREEN_SEVENTH / 2))))));
  } 
  
  //shoots a bullet on key
  public World onKeyEvent(String key) {
    switch (key) {
    case " ":
      return new NBulletsWorld(this.bulletsLeft.restBullet(), this.ships, this.ticks, this.shotBullets());
    }
    return this;
  }
  
  ILoGamePiece shotBullets() {
    if (this.bulletsLeft.countBullets(0) == 0) {
      return this.bulletsLeft;
    }
    else {
      return new ConsLoGamePiece(this.bulletsLeft.firstBullet(), this.bulletsShot);
    }
  }
  
  //determines if world is ended
  public WorldEnd worldEnds() {
    WorldScene scene = this.getEmptyScene();
    if (this.bulletsLeft.countBullets(0) == 0 && this.bulletsShot.checkRemoved()) {
      return new WorldEnd (true,
          scene.placeImageXY(new TextImage("Game Over", Color.RED), IConstants.SCREEN_WIDTH / 2, IConstants.SCREEN_HEIGHT / 2));
    } 
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
}