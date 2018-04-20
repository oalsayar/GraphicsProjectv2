/**
 *   file: myPanel.java
 */
package cis315GraphicsGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JColorChooser;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class drawingPanel extends Panel {
	public drawingPanel() {
	}

	/**
	 * 
	 */

	// variables needed for drawing
	boolean isDrawing = false; // set true when drawing
	boolean isDrawingMouseDown = false; // when drawing object
	Point pntStart = new Point(); // starting point on click-drag
	Point pntEndPrev = new Point();
	Cursor prevCursor; // for storage of previous cursor
	Color colorShapeFill = Color.WHITE;
	boolean isSolidFill = false;
	Color colorShapeOutline = Color.black;
	boolean isSolidOutline = true;
	String currentShape;

	private static final long serialVersionUID = 1L;
	ArrayList<Shape> myShapes = new ArrayList<>();

	@Override
	public void paint(Graphics g) {

		super.paint(g); // behave just like any panel

		// add additional painting

		// iterate through all Shapes in myDrawing
		for (Shape sh : myShapes) {
			// g.drawOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y -
			// sh.pntUL.y);
			
			if (sh instanceof Oval) {
				// only draw inner shape if solid fill
				if (sh.getSolidFill()) {
					// get fill color
					g.setColor(sh.colorFill);
					// add 1 to x,y due to outline width
					g.fillOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x + 1, sh.pntLR.y - sh.pntUL.y + 1);
				}
				// now the outline
				g.setColor(sh.colorOutline);
				g.drawOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y - sh.pntUL.y);
			}
			else if (sh instanceof Rectangle) {
				if (sh.getSolidFill()) {
					// get fill color
					g.setColor(sh.colorFill);
					// add 1 to x,y due to outline width
					g.fillRect(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x + 1, sh.pntLR.y - sh.pntUL.y + 1);
				}
				// now the outline
				g.setColor(sh.colorOutline);
				// draw inner shape
				g.drawRect(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y - sh.pntUL.y);
			}
			else if (sh instanceof Triangle) {
				if (sh.getSolidFill()) {
					// get fill color
					g.setColor(sh.colorFill);
					// add 1 to x,y due to outline width
					int[] xPoints = { // triangle x coordinates
						(int)  sh.pntUL.getX() - 1, // upper left
						(int) sh.pntLR.getX() - (int)(sh.pntLR.getX() - sh.pntUL.getX())/2 + 1, // middle of upper left and upper right
						(int) sh.pntLR.getX() + 1 // lower right
					};
					int[] yPoints = {
						(int)  sh.pntLR.getY() + 1, // upper left
						(int) sh.pntUL.getY() - 1, // middle of upper left and upper right
						(int) sh.pntLR.getY() + 1 // lower right
					};
					g.fillPolygon(xPoints, yPoints, 3);
				}
				// now the outline
				g.setColor(sh.colorOutline);
				int[] xPoints = { // triangle x coordinates
					(int)  sh.pntUL.getX(), // upper left
					(int) sh.pntLR.getX() - (int)(sh.pntLR.getX() - sh.pntUL.getX())/2, // middle of upper left and upper right
					(int) sh.pntLR.getX() // lower right
				};
				int[] yPoints = {
					(int)  sh.pntLR.getY(), // upper left
					(int) sh.pntUL.getY(), // middle of upper left and upper right
					(int) sh.pntLR.getY() // lower right
				};
				g.drawPolygon(xPoints, yPoints, 3);

			}
			else if (sh instanceof RoundedRectangle) {
				if (sh.getSolidFill()) {
					// get fill color
					g.setColor(sh.colorFill);
					// add 1 to x,y due to outline width
					g.fillRoundRect(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x + 1, sh.pntLR.y - sh.pntUL.y + 1, RoundedRectangle.arcWidth, RoundedRectangle.arcHeight);
				}
				// now the outline
				g.setColor(sh.colorOutline);
				// draw inner shape
				g.drawRoundRect(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y - sh.pntUL.y, RoundedRectangle.arcWidth, RoundedRectangle.arcHeight);
			}
		}
	}

	void addShape(Shape sh) {
		myShapes.add(sh);
	}

	// set global outline color
	void setColorShapeOutline(Color c) {
		this.colorShapeOutline = c;
	}

	// set global fill color
	void setColorShapeFill(Color c) {
		this.colorShapeFill = c;
	}

	void setSolidFill(boolean isSolid) {
		this.isSolidFill = isSolid;
	}

	
	// show a color selection dialog for outline color selection
	void chooseColorOutline() {
		Color c = JColorChooser.showDialog(null, "Choose Outline Color", this.getBackground());
		this.colorShapeOutline = c;
	}

	// display a color selection dialog for fill color selection
	void chooseColorFill() {
		Color c = JColorChooser.showDialog(null, "Choose Outline Color", this.getBackground());
		this.colorShapeFill = c;
	}

	public void drawStart(String shapeName) {

		// change the cursor to indicate drawing
		isDrawing = true;

		// store cursor for later restore
		prevCursor = this.getCursor();
		
		// store current shape for when the drawing starts
		currentShape = shapeName;

		// drawing begins with mouse click-drag
		// in the panel
		// drawing ends with mouse-release
		// add circle Object to drawing list
		// (panel paint() method draws this)

	}

	public void dragStart(MouseEvent e) {

		pntStart.setLocation(e.getPoint());
		isDrawingMouseDown = true;
		pntEndPrev.setLocation(pntStart);

	} // end dragStart

	public void mouseReleased(MouseEvent e) {
		// end the shape if being drawn
		if (isDrawing) {
			isDrawing = false; // done drawing

			// shape width and height cannot be negative - correct if needed
			int xStart = pntStart.x;
			int width = e.getX() - pntStart.x;

			if (width < 0) {
				xStart += width;
				width = -width;
			}

			int yStart = pntStart.y;
			int height = e.getY() - pntStart.y;
			if (height < 0) {
				yStart += height;
				height = -height;
			}

			Shape thisShape = null; // initialize variable
			
			// choose type of shape
			if (currentShape.equals("Oval")) {
				thisShape = new Oval(xStart, yStart, width, height);
			}
			else if (currentShape.equals("Rectangle")) {
				thisShape = new Rectangle(xStart, yStart, width, height);
			}
			else if (currentShape.equals("Triangle")) {
				thisShape = new Triangle(xStart, yStart, width, height);
			}
			else if (currentShape.equals("RoundedRectangle")) {
				thisShape = new RoundedRectangle(xStart, yStart, width, height);
			}
			
			// set this shape's colors
			thisShape.setColors(this.colorShapeOutline, this.colorShapeFill);
			thisShape.setSolidFill(this.isSolidFill);
			this.myShapes.add(thisShape);

			// restore the panel Cursor
			this.setCursor(prevCursor);

			// force a repaint of this panel
			this.repaint();
		}

	} // end mouseReleased

	public void mouseMoved(MouseEvent e) {
		if (isDrawing) {
			// got some help from StackOverflow for this
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		}
	} // end mouseMoved

	public void mouseDragged(MouseEvent e) {
		// clear screen before redrawing
		this.repaint();
		
		// draw shape if mouse dragging
		// start point is mouse down
		if (isDrawing) {
			// only if a shape in progress

			// draw shape from startpoint to here
			Graphics g = this.getGraphics();

			// shape width and height cannot be negative - correct if needed
			int xStart = pntStart.x;
			int width = e.getX() - pntStart.x;

			if (width < 0) {
				xStart += width;
				width = -width;
			}

			int yStart = pntStart.y;
			int height = e.getY() - pntStart.y;
			if (height < 0) {
				yStart += height;
				height = -height;
			}

			// g.setXORMode(this.getBackground()); // trying to clean up drawing on drag
			
			g.setColor(this.colorShapeFill);
			
			// preview fill color
			if (this.isSolidFill) {
				if (currentShape.equals("Oval")) {
					g.fillOval(xStart, yStart, width + 1, height + 1); // add 1 for outline width
				}
				else if (currentShape.equals("Rectangle")) {
					g.fillRect(xStart, yStart, width+1, height+1);
				}
				else if (currentShape.equals("Triangle")) {
					int[] xPoints = { // triangle x coordinates
							xStart, // lower left
							(int) (xStart+width/2)+1, // middle of upper left and upper right
							 xStart+width+1// lower right
					};
					int[] yPoints = {
							yStart+height+1, // lower left
							yStart, // middle of upper left and upper right
							yStart+height+1// lower right
					};
					g.fillPolygon(xPoints, yPoints, 3); // draw new triangle
				}
				else if (currentShape.equals("RoundedRectangle")) {
					g.fillRoundRect(xStart, yStart, width+1, height+1, RoundedRectangle.arcWidth, RoundedRectangle.arcHeight);
				}
				
			}
			
			// xor mode allows erasing previous lines
			//g.setXORMode(this.getBackground());
			
			// erase previous shape first
			// oval width and height cannot be negative - correct if needed
			int xStartOld = pntStart.x;
			int widthOld = pntEndPrev.x - pntStart.x;

			if (widthOld < 0) {
				xStartOld += widthOld;
				widthOld = -widthOld;
			}

			int yStartOld = pntStart.y;
			int heightOld = pntEndPrev.y - pntStart.y;
			if (heightOld < 0) {
				yStartOld += heightOld;
				heightOld = -heightOld;
			}
			
			g.setColor(this.colorShapeOutline);
			
			if (currentShape.equals("Oval")) {
				//g.drawOval(xStartOld, yStartOld, widthOld, heightOld);
				g.drawOval(xStart, yStart, width, height);
			}
			else if (currentShape.equals("Rectangle")) {
				//g.drawRect(xStartOld, yStartOld, widthOld, heightOld);
				g.drawRect(xStart, yStart, width, height);
				
			}
			else if (currentShape.equals("Triangle")) {
				/*
				int[] xPointsOld = { // triangle x coordinates
						xStartOld, // lower left
						(int) (xStartOld+widthOld/2), // middle of upper left and upper right
						 xStartOld+widthOld// lower right
				};
				int[] yPointsOld = {
						yStartOld+heightOld, // lower left
						yStartOld, // middle of upper left and upper right
						yStartOld+heightOld// lower right
				};
				
				g.drawPolygon(xPointsOld, yPointsOld, 3); // erase old triangle
				*/
				int[] xPoints = { // triangle x coordinates
						xStart, // lower left
						(int) (xStart+width/2), // middle of upper left and upper right
						 xStart+width// lower right
				};
				int[] yPoints = {
						yStart+height, // lower left
						yStart, // middle of upper left and upper right
						yStart+height// lower right
				};
				g.drawPolygon(xPoints, yPoints, 3); // draw new triangle
			}
			else if (currentShape.equals("RoundedRectangle")) {
				//g.drawRoundRect(xStartOld, yStartOld, widthOld, heightOld, RoundedRectangle.arcWidth, RoundedRectangle.arcHeight);
				g.drawRoundRect(xStart, yStart, width, height, RoundedRectangle.arcWidth, RoundedRectangle.arcHeight);
			}
			
			// the shape doesn't become permanent until
			// the mouse is released
			
			pntEndPrev.setLocation(e.getPoint());
		}

	}
}
