package cis315GraphicsGame;

/**
 * @author Joseph Schwenker, Omar Alsayar, Arthur Manning
 *
 */

public class Rectangle extends Shape {
	
	Rectangle(int x, int y, int width, int height) { // start of Constructor
		this.pntUL.setLocation(x, y);
		this.pntLR.setLocation(x+width, y+height);
	} // end of constructor

}