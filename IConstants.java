import java.awt.Color;
import java.util.Random;

interface IConstants {
  //world
  int SCREEN_WIDTH = 500;
  int SCREEN_HEIGHT = 300;
  int SCREEN_SEVENTH = SCREEN_HEIGHT / 7;
  Color FONT_COLOR = Color.black;
  int FONT_SIZE = 13;
  double TICK_RATE  = 1.0 / 28.0;
  double CONVERSION = Math.PI / 180;
  
  //bullets
  //starting point of every bullet
  Posn BULLET_START = new Posn(250, 300);
  int MIN_BULLET_RADIUS = 2;
  //add to current bullet radius
  int BULLET_AFTER_EXPLOSION = 2;
  int MAX_BULLET_RADIUS = 10;
  Color BULLET_COLOR = Color.pink;
  int BULLET_SPEED = -8;
  
  //ships
  int LEFT_RIGHT = new Random().nextInt(1);
  int SPAWN_FREQUENCY = 1;
  int SHIP_SPAWN_NUMBER = new Random().nextInt(3);
  int SHIP_RADIUS = SCREEN_HEIGHT / 30;
  Color SHIP_COLOR = Color.cyan;
  int SHIP_SPEED = BULLET_SPEED / 2;
  int SHIP_SPAWN_LOC = new Random().nextInt((SCREEN_HEIGHT - SCREEN_SEVENTH) - SCREEN_SEVENTH + 1) + SCREEN_SEVENTH;
}