import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Creates a panel for the restart option
 * 
 * @author Michelle Pokrass
 * 
 */
public class RestartPanel extends JPanel
{
	private ToggleMain frame;

	private BufferedImage picture;

	/**
	 * Creates the panel
	 * 
	 * @param frame
	 *            the parent frame
	 * @throws IOException
	 *             throws and exception when the image is not found
	 */
	public RestartPanel(ToggleMain frame) throws IOException
	{
		// Gets the image
		picture = ImageIO.read(new File("images\\restart.png"));
		this.frame = frame;
		// Adds a mouse handler
		addMouseListener(new MouseHandler());
	}

	// Draws the image
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);
	}

	// Handles mouse click
	private class MouseHandler extends MouseAdapter
	{
		// Restart the game upon mouseclick
		public void mousePressed(MouseEvent event)
		{

			frame.restartGame();

		}

	}

}
