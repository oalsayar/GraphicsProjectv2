/**
 *   file: Oval.java
 */
package cis315GraphicsGame;

/**
 * @author Joseph Schwenker, Omar Alsayar, Arthur Manning
 *
 */
public class Oval extends Shape {
	boolean isCircle;  // is this a circle?
	
	Oval(int x, int y, int width, int height) { // start of Constructor
		this.pntUL.setLocation(x, y);
		this.pntLR.setLocation(x+width, y+height);
	} // end of constructor
}
