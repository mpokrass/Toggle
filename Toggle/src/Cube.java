import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Cube class that has the image and top letter of a cube
 * 
 * @author Michelle Pokrass
 * 
 */
public class Cube extends JPanel
{
	private char topFace;

	private static final Font LETTER_FONT = new Font("Franklin Gothic Medium",
			Font.BOLD, 36);

	private BufferedImage top;

	/**
	 * Creates a cube from a character array
	 * 
	 * @param array
	 *            character array of size g
	 * @throws IOException
	 *             when the cube image is not found
	 */
	public Cube(char[] array) throws IOException
	{
		// Get the cube image and randomly pick a letter for the top face
		Random rndm = new Random();
		top = ImageIO.read(new File("images\\cube.png"));
		this.topFace = array[rndm.nextInt(6)];

	}

	/**
	 * Creates a cube from a string
	 * 
	 * @param charArray
	 *            String of 6 letters
	 * @throws IOException
	 *             when the cube image is not found
	 */
	public Cube(String charArray) throws IOException
	{
		// Get the cube image and randomly pick a letter for the top face
		Random rndm = new Random();
		top = ImageIO.read(new File("images\\cube.png"));
		this.topFace = charArray.toCharArray()[rndm.nextInt(6)];

	}

	/**
	 * Creates a cube without any letters
	 * 
	 * @throws IOException
	 *             when the cube image is not found
	 */
	public Cube() throws IOException
	{
		// Get the cube image and set the top face to empty
		top = ImageIO.read(new File("images\\cube.png"));
		this.topFace = ' ';
	}

	/**
	 * Draws the images for the panel
	 */
	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		// Draw the image of the cube
		g.drawImage(top, 0, 0, null);
		g.setFont(LETTER_FONT);
		// If the cube is a Q, draw QU
		if (topFace == 'Q')
		{
			g.drawString("Qu", 29, 57);
		}
		// Otherwise draw the top letter
		else
		{

			g.drawString("" + topFace, 39, 57);
		}
	}

	/**
	 * Returns the top letter of the cube
	 * 
	 * @return the top letter of the cube
	 */
	public char returnChar()
	{
		return topFace;
	}

	/**
	 * Sets the top image to the blue face
	 * 
	 * @throws IOException
	 *             when the file is not found
	 */
	public void highlight() throws IOException
	{
		top = ImageIO.read(new File("images\\cubeMouseover.png"));
	}

	/**
	 * Sets the top image to the grey face
	 * 
	 * @throws IOException
	 *             when the file is not found
	 */
	public void unHightLight() throws IOException
	{
		top = ImageIO.read(new File("images\\cube.png"));
	}

	/**
	 * Returns the bounds of the cube
	 * 
	 * @return a smaller rectangle
	 */
	public Rectangle returnBounds()
	{
		// Get the rectangle for this panel
		Rectangle large = this.getBounds();
		// Create a new smaller rectangle that is 30 units smaller on both
		// sides, in the centre of the large rectangle
		Rectangle small = new Rectangle();
		small.x = large.x + 15;
		small.y = large.y + 15;
		small.height = large.height - 30;
		small.width = large.width - 30;
		// Return the smaller rectangle
		return small;
	}

	/**
	 * Returns the top face of the cube
	 */
	public String toString()
	{
		return "" + topFace;
	}

}
