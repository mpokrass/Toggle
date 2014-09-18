import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Creates a panel to give hints
 * 
 * @author Michelle Pokrass
 * 
 */
public class HintPanel extends JPanel
{
	private ToggleMain frame;

	private BufferedImage picture;

	private int hintsUsed;

	private int maxHint;

	/**
	 * Creates this panel, given the parent frame
	 * 
	 * @param frame
	 *            the parent frame
	 * @throws IOException
	 *             throws an exception if the image is not found
	 */
	public HintPanel(ToggleMain frame) throws IOException
	{
		// Sets the hints used and maximum amount
		hintsUsed = 0;
		maxHint = 4;
		// Get the picture to display for the button
		picture = ImageIO.read(new File("images\\hint.png"));
		this.frame = frame;
		// Add a mouse listener
		addMouseListener(new MouseHandler());
	}

	/**
	 * Draws thae panel
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);
	}

	/**
	 * Creates a mouse handler that handles with the panel hasbeen clicked
	 * 
	 * @author Michelle Pokrass
	 * 
	 */
	private class MouseHandler extends MouseAdapter
	{
		// Handles mouse clicks
		public void mousePressed(MouseEvent event)
		{
			// If the has more hints, get one from the frame and add one to the
			// amount used
			if (hintsUsed < maxHint)
			{
				frame.getHint();
				hintsUsed++;
			}
			// Otherwise display that there are no more hints
			else
			{
				frame.noHint();
			}
		}

	}

	/**
	 * Set the amount of hints used to zero
	 */
	public void resetHints()
	{
		hintsUsed = 0;
	}

	/**
	 * Set the maximum amount of hints allowed
	 * 
	 * @param num
	 *            the number of hints
	 */
	public void setHintNum(int num)
	{
		maxHint = num;
	}
}
