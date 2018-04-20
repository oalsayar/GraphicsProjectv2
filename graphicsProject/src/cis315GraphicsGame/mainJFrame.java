/**
 *   file: mainJFrame.java
 */
package cis315GraphicsGame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Label;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JCheckBoxMenuItem;

/**
 * @author Joseph Schwenker, Omar Alsayar, Arthur Manning
 * 
 * 
 *         This is an extension of an existing drawing program. Three additional
 *         shapes were added: Rectangle, Triangle, and Rounded Rectangle. Java
 *         already possessed libraries for two of these, but Triangle needed to
 *         be implemented using the Polygon function.
 * 
 * 
 * 
 *
 */
public class mainJFrame extends JFrame {

	private JPanel contentPane;
	Color colorOutline;

	// these items declared here for global access
	// from all ActionHandlers
	JTextArea textAreaHistory;
	Label labelDebug; // for displaying status
	drawingPanel panelDraw; // need access to this from paint()
	ArrayList<Shape> myDrawing = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainJFrame frame = new mainJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainJFrame() {
		setTitle("graphicsDrawing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 401);
		// we are intiating the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// here is the first menu tap called file
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		// here is the second menu tap called Open
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		// here is the third menu tap called Save
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		// drag menu items to the menu panel
		JMenuItem mntmQuit = new JMenuItem("Quit");

		// right-click on menu items to add event Listeners
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// end the program
				// todo: check for save
				System.exit(NORMAL);
			} // end of my action event
		}); // end of my actionListener
		mnFile.add(mntmQuit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmSelectAll = new JMenuItem("Select All");
		mnEdit.add(mntmSelectAll);

		JMenu mnDraw = new JMenu("Draw");
		mnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		// starting the circle section
		menuBar.add(mnDraw);

		JMenuItem mntmCircle = new JMenuItem("Oval");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// start drawing an Oval
				textAreaHistory.append("drawing Oval\n");
				labelDebug.setText("oval...");

				panelDraw.drawStart("Oval");
			}
		});
		mnDraw.add(mntmCircle);

		JMenuItem mntmNewMenuItem = new JMenuItem("Rectangle");

		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// start drawing a rectangle
				textAreaHistory.append("drawing Rectangle\n");
				labelDebug.setText("rectangle...");

				panelDraw.drawStart("Rectangle");
			}
		}); // end of my circle section

		// starting the Triangle section

		mnDraw.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Triangle");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// start drawing a Tritangle
				textAreaHistory.append("drawing Triangle\n");
				labelDebug.setText("triangle...");

				panelDraw.drawStart("Triangle");
			}
		}); // end of my Triangle section

		// starting my Rounded Rectangle section

		mnDraw.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Rounded Rectangle");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// start drawing a rectangle
				textAreaHistory.append("drawing Rounded Rectangle\n");
				labelDebug.setText("rounded rectangle...");

				panelDraw.drawStart("RoundedRectangle");
			}
		}); // end of my Rounded Rectangle section

		// adding some enhancement such as choosing outline colors, on or off
		// solid fill
		mnDraw.add(mntmNewMenuItem_2);

		JMenu mnColors = new JMenu("Colors");
		menuBar.add(mnColors);

		JMenuItem mntmColorchooser = new JMenuItem("Outline...");
		mntmColorchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// new colorJDialog().setVisible(true);

				panelDraw.chooseColorOutline();

			}
		});
		mnColors.add(mntmColorchooser);

		JMenuItem mntmFill = new JMenuItem("Fill...");
		mntmFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(contentPane, "Choose Outline Color", panelDraw.getBackground());
				panelDraw.setColorShapeFill(c);
			}
		});
		mnColors.add(mntmFill);

		JCheckBoxMenuItem chckbxmntmNoFill = new JCheckBoxMenuItem("Solid Fill");
		chckbxmntmNoFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// turn on/off fill based on checkbox status
				JCheckBoxMenuItem me = (JCheckBoxMenuItem) e.getSource();
				panelDraw.setSolidFill(me.isSelected());
			}
		});
		mnColors.add(chckbxmntmNoFill);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelDraw = new drawingPanel();
		panelDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// store this location and start drawing

				panelDraw.dragStart(e);

			}

			@Override
			public void mouseReleased(MouseEvent e) {

				panelDraw.mouseReleased(e);
			}
		});

		panelDraw.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				labelDebug.setText("m:" + e.getX() + "," + e.getY());

				panelDraw.mouseMoved(e);

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				labelDebug.setText("d:" + e.getX() + "," + e.getY());

				panelDraw.mouseDragged(e);

			}
		});

		// setting the panelDraw
		panelDraw.setBackground(SystemColor.window);
		panelDraw.setBounds(12, 10, 488, 308);
		contentPane.add(panelDraw);

		// setting the scrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(506, 36, 96, 153);
		contentPane.add(scrollPane);

		// setting the textAreaHistory
		textAreaHistory = new JTextArea();
		scrollPane.setViewportView(textAreaHistory);
		textAreaHistory.setBackground(new Color(240, 255, 255));

		// setting the label called "History"
		Label label = new Label("History");
		label.setBounds(526, 10, 58, 20);
		contentPane.add(label);

		// setting the labelDebug
		labelDebug = new Label("Debug...");
		labelDebug.setBounds(506, 195, 58, 20);
		contentPane.add(labelDebug);
	}//end of my mainJFrame
}//end of my extended mainJFrame class
