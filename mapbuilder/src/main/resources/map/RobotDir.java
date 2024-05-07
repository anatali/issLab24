package main.resources.map;

public class RobotDir {
	
	public static Direction curDiretion;
	
	public static enum Direction {DOWN, LEFT, UP, RIGHT}
	
	public static void setDir( Direction dir ) {
		curDiretion = dir;
	}
	public static Direction getDir(   ) {
		return curDiretion ;
	}
	
	public static boolean goingUp() {
		return curDiretion == Direction.UP;
	}
	public static boolean goingDown() {
		return curDiretion == Direction.DOWN;
	}
	public static boolean goingLeft() {
		return curDiretion == Direction.LEFT;
	}
	public static boolean goingRight() {
		return curDiretion == Direction.RIGHT;
	}
	
	
}
