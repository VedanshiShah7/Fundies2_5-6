import tester.Tester;

import java.awt.Color;
import java.util.Random;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.TextImage;

class ExamplesWorld implements IConstants{
  
  WorldScene scene = new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT);
  
  //examples of "random" numbers
  int spawnArea = new Random(50).nextInt((SCREEN_HEIGHT - SCREEN_SEVENTH) - SCREEN_SEVENTH + 1) + SCREEN_SEVENTH;
  int shipSpawnNumber = new Random(2).nextInt(3);
  
  Posn posnOne = new Posn(3, 3);
  Posn posnTwo = new Posn(8, 30);
  
  IGamePiece bulletLoc = new Bullet(posnTwo);
  IGamePiece shipLoc = new ShipLeft(posnOne);
  
  
  IGamePiece bullet = new Bullet();
  IGamePiece ship = new ShipLeft();
  
  ILoGamePiece mt = new MtLoGamePiece();
  ILoGamePiece ships = new ConsLoGamePiece(new ShipLeft(posnOne), this.mt);
  ILoGamePiece shipsOne = new ConsLoGamePiece(new ShipLeft(posnTwo), this.mt);
  ILoGamePiece shipsTwo = new ConsLoGamePiece(new ShipLeft(posnTwo), this.ships);
  ILoGamePiece bullets = new ConsLoGamePiece(new Bullet(posnTwo), 
      new ConsLoGamePiece(new Bullet(posnOne), this.mt));
  ILoGamePiece bulletsOne = new ConsLoGamePiece(new Bullet(posnTwo), this.mt);
  ILoGamePiece listOne = new ConsLoGamePiece(this.shipLoc, new ConsLoGamePiece(this.bulletLoc, this.mt));
  ILoGamePiece listTwo = new ConsLoGamePiece(this.shipLoc.move(), new ConsLoGamePiece(this.bulletLoc.move(), this.mt));
  
  
  IGamePiece bulletOne = new Bullet(8, 0, 4, Color.GREEN, new Posn(100, 100), this.mt, 1, 2);
  ILoGamePiece list = new ConsLoGamePiece(this.bulletTwo, this.mt);
  IGamePiece bulletTwo = new Bullet(-8, 0, 4, Color.RED, new Posn(108, 100), new ConsLoGamePiece(this.bulletOne,this.mt), 2, 2);
  
  //tests for methods in Posn class-----------------------------------
  
  //tests for changeBullet
  boolean testChangeBullet(Tester t) {
    return t.checkExpect(this.posnOne.changeBullet(0.0, 1.0), new Posn(3, 4))
        && t.checkExpect(this.posnOne.changeBullet(0.0, -1.0), new Posn(3, 2));
  }
  
  //tests for changeShip
  boolean testChangeShip(Tester t) {
    return t.checkExpect(this.posnOne.changeShip(1), new Posn(4, 3))
        && t.checkExpect(this.posnOne.changeShip(-1), new Posn(2, 3));
  }
  
  //tests for sameLoc
  boolean testPosn(Tester t) {
    return t.checkExpect(new Posn(3, 3).sameLoc(new Posn(3, 3)), true)
        && t.checkExpect(new Posn(3, 3).sameLoc(new Posn(5, 3)), true)
        && t.checkExpect(new Posn(3, 3).sameLoc(new Posn(3, 40)), false);
  }
  
  //tests to see if something is off-screen
  boolean testOffScreen(Tester t) {
    return t.checkExpect(new Posn(700, 3).offScreen(), true)
        && t.checkExpect(new Posn(3, 700).offScreen(), true)
        && t.checkExpect(new Posn(0, 300).offScreen(), false)
        && t.checkExpect(new Posn(100, 0).offScreen(), false)
        && t.checkExpect(new Posn(500, 0).offScreen(), false)
        && t.checkExpect(new Posn(0, 300).offScreen(), false)
        && t.checkExpect(this.bullet.offScreen(), false)
        && t.checkExpect(new Bullet(new Posn(600, 3)).offScreen(), true);
  }
  
  //test to get x or y
  boolean testGet(Tester t) {
    return t.checkExpect(this.posnOne.getX(), 3.0)
        && t.checkExpect(this.posnOne.getY(), 3.0);
  }
  
  //tests for methods in IGamePiece(Bullets and ships)-------------------------------
  
  //tests for draw
  boolean testDraw(Tester t) {
    return t.checkExpect(this.shipLoc.draw(scene), 
        scene.placeImageXY(new CircleImage(SHIP_RADIUS, OutlineMode.SOLID, SHIP_COLOR), 3, 3))
        && t.checkExpect(this.bulletLoc.draw(scene), 
            scene.placeImageXY(new CircleImage(MIN_BULLET_RADIUS, OutlineMode.SOLID, BULLET_COLOR), 8, 30))
        && t.checkExpect(this.mt.draw(scene), this.scene)
        && t.checkExpect(this.listOne.draw(scene), 
                scene.placeImageXY(new CircleImage(SHIP_RADIUS, OutlineMode.SOLID, SHIP_COLOR), 3, 3)
                .placeImageXY(new CircleImage(MIN_BULLET_RADIUS, OutlineMode.SOLID, BULLET_COLOR), 8, 30))
        && t.checkExpect(this.list.getBullets(scene), bulletOne.draw(bulletTwo.draw(scene)));
  }
  
  //tests to get a location
  boolean testGetLoc(Tester t) {
    return t.checkExpect(this.shipLoc.getLoc(), this.posnOne)
        && t.checkExpect(this.bulletLoc.getLoc(), this.posnTwo);
  }
  
  //tests for sameLocation
  boolean testSameLocation(Tester t) {
    return t.checkExpect(new Bullet(new Posn(3,3)).sameLocation(new Bullet(new Posn(3,3))), true)
        && t.checkExpect(new Bullet(new Posn(3,3)).sameLocation(new Bullet(new Posn(4,3))), true)
        && t.checkExpect(new Bullet(new Posn(3,3)).sameLocation(new Bullet(new Posn(3,40))), false);
  }
  
 /* //tests for theta
  boolean testTheta(Tester t) {
    return t.checkExpect(new Bullet(1).theta(), 2 * Math.PI)
        && t.checkExpect(new Bullet(2).theta(), Math.PI);
  }*/
  
  //tests for methods in ILoGamePiece(ConsLoGamePiece + MtLoGamePiece)-------------------------------
  
  //tests for creating a list of bullets
  boolean testCreateBullets(Tester t) {
    return t.checkExpect(this.mt.createBullets(0), this.mt)
        && t.checkExpect(this.mt.createBullets(1), new ConsLoGamePiece(this.bullet, this.mt))
        && t.checkExpect(this.mt.createBullets(2), new ConsLoGamePiece(this.bullet,
            new ConsLoGamePiece(this.bullet, this.mt)))
        && t.checkExpect(new ConsLoGamePiece(this.bullet, this.mt).createBullets(1), new ConsLoGamePiece(this.bullet,
            new ConsLoGamePiece(this.bullet, this.mt)))
        && t.checkExpect(new ConsLoGamePiece(this.bullet, this.mt).createBullets(0), new ConsLoGamePiece(this.bullet, this.mt));
  }
  
  //tests for moving a list
  boolean testMove(Tester t) {
    return t.checkExpect(this.listOne.move(), this.listTwo)
        && t.checkExpect(this.bulletLoc.move(), new Bullet(posnTwo.changeBullet(0.0, BULLET_SPEED)))
        && t.checkExpect(this.shipLoc.move(), new ShipLeft(posnOne.changeShip(-1 * SHIP_SPEED)));
  }
  
  //tests ship spawn
  boolean testSpawn(Tester t) {
    return t.checkExpect(this.listOne.spawnTest(28, 1, 3), new ConsLoGamePiece(new ShipRight(new Posn(500, 115)),
        new ConsLoGamePiece(new ShipLeft(new Posn(3, 3)), new ConsLoGamePiece(new Bullet(new Posn(8, 30)), this.mt))))
        && t.checkExpect(this.mt.spawnTest(27, 1, 3), this.mt)
        && t.checkExpect(this.mt.spawnTest(28, 1, 3), new ConsLoGamePiece(new ShipRight(new Posn(500, 115)), this.mt));
  }
  
  //tests for side
  boolean testSide(Tester t) {
    return t.checkExpect(this.listOne.sideTest(3), new ShipRight(new Posn(500, 115)))
        && t.checkExpect(this.mt.sideTest(3), new ShipRight(new Posn(500, 115)));
  }
  
  //tests for remove
  boolean testRemove(Tester t) {
    return t.checkExpect(new ConsLoGamePiece(new Bullet(new Posn(700, 3)), this.mt).remove(this.mt), this.mt)
        && t.checkExpect(new ConsLoGamePiece(new Bullet(new Posn(100, 3)), this.mt).remove(this.mt), 
            new ConsLoGamePiece(new Bullet(new Posn(100, 3)), this.mt))
        && t.checkExpect(this.listOne.remove(new ConsLoGamePiece(this.bulletLoc, this.mt)), 
            new ConsLoGamePiece(this.shipLoc, this.mt))
        && t.checkExpect(this.listOne.remove(new ConsLoGamePiece(this.shipLoc, this.mt)), 
            new ConsLoGamePiece(this.bulletLoc, this.mt));
  }
  
  //tests for if a bullet was removed
  boolean testCheckRemoved(Tester t) {
    return t.checkExpect(new ConsLoGamePiece
        (new Bullet(new Posn(700, 3)), this.mt).checkRemoved(), true)
        && t.checkExpect(new ConsLoGamePiece
            (new Bullet(new Posn(100, 3)), this.mt).checkRemoved(), false)
        && t.checkExpect(this.mt.checkRemoved(), true);
  }
  
  //tests to see if a ship will be destroyed
  boolean testDestroyed(Tester t) {
    return t.checkExpect(this.listOne.destroyed
        (new ConsLoGamePiece(this.bulletLoc, this.mt), 0), 1)
        && t.checkExpect(this.listOne.destroyed
            (new ConsLoGamePiece(this.bulletLoc, this.mt), 7), 8)
        && t.checkExpect(this.listOne.destroyed
            (new ConsLoGamePiece(this.bulletLoc, new ConsLoGamePiece(this.shipLoc, this.mt)), 0), 2)
        && t.checkExpect(this.listOne.destroyed
            (new ConsLoGamePiece(new Bullet(new Posn(300, 300)), this.mt), 0), 0);
  }
  
  //tests to check Destroyed
  boolean testCheckDestroyed(Tester t) {
    return t.checkExpect(new NBulletsWorld(this.listOne,
        new ConsLoGamePiece(this.bulletLoc, this.mt), 3, new ConsLoGamePiece(this.shipLoc, this.mt)
        , 3).checkDestroyed(), new NBulletsWorld(this.listOne,
            new ConsLoGamePiece(this.bulletLoc, this.mt), 3, new ConsLoGamePiece(this.shipLoc, this.mt)
            , 3));
  }
  
  boolean testMakeScene(Tester t) {
    WorldScene scene = new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT);
    return t.checkExpect(new NBulletsWorld(this.listOne,
        new ConsLoGamePiece(this.bulletLoc, this.mt), 3, new ConsLoGamePiece(this.shipLoc, this.mt)
        , 3).makeScene(), this.ships.draw(new ConsLoGamePiece(this.bulletLoc, this.mt).draw(scene.placeImageXY(
        new TextImage("Bullets left: 2; Ships destroyed: 3",
        FONT_COLOR), SCREEN_SEVENTH * 3, SCREEN_HEIGHT - (SCREEN_SEVENTH / 2)))));
  }
  
  //tests for counting bullets
  boolean testCountBullets(Tester t) {
    return t.checkExpect(listOne.countBullets(0), 2)
        && t.checkExpect(listOne.countBullets(5), 7)
        && t.checkExpect(this.mt.countBullets(0), 0);
  }
  
  //tests for returning first bullet
  boolean testFirstBullet(Tester t) {
     return t.checkExpect(listOne.firstBullet(), this.shipLoc)
         && t.checkExpect(this.mt.firstBullet(), new Bullet());
  }
  
  //tests for returning the rest of a list
  boolean testRestBullet(Tester t) {
    return t.checkExpect(listOne.restBullet(), new ConsLoGamePiece(this.bulletLoc, this.mt))
        && t.checkExpect(this.mt.restBullet(), this.mt);
  }
  
  //tests for checkSame
  boolean testCheckSame(Tester t) {
    return t.checkExpect(ships.checkSame(new Bullet(posnOne)), true)
        && t.checkExpect(ships.checkSame(new Bullet(posnTwo)), false);
  }
  
  //testing that the random output is within the correct range
  boolean testRandom(Tester t) {
    return t.checkExpect(this.spawnArea, 236)
        && t.checkExpect(this.shipSpawnNumber, 1);
  }
  
//tests for methods in NBulletsWorld-------------------------------
  
  
  
  /*boolean testNewWorld(Tester t) {
    return t.checkExpect(new NBulletsWorld(1), new NBulletsWorld(new ConsLoGamePiece(this.bullet, this.mt), this.mt, 0, false));
  }
  
  boolean testPlay(Tester t) {
    NBulletsWorld myWorld = new NBulletsWorld(50);
    return myWorld.bigBang(SCREEN_WIDTH, SCREEN_HEIGHT, TICK_RATE);
  }*/
  
}

