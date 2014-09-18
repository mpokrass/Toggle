import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A panel that, when clicked, starts a new game
 * 
 * @author Michelle Pokrass
 * 
 */
public class NewGamePanel extends JPanel
{
	private ToggleMain frame;

	private BufferedImage picture;

	/**
	 * Creates a panel, given the parent frame
	 * 
	 * @param frame
	 *            the parent frame
	 * @throws IOException
	 *             throws an exception when the image is not found
	 */
	public NewGamePanel(ToggleMain frame) throws IOException
	{
		// Get the image
		picture = ImageIO.read(new File("images\\newGame.png"));
		this.frame = frame;
		// Add a mouse listener to handle clicks
		addMouseListener(new MouseHandler());

	}

	// Draw the panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);

	}

	/**
	 * Handles mouse clicks
	 * 
	 * @author Michelle Pokrass
	 * 
	 */
	private class MouseHandler extends MouseAdapter
	{

		/**
		 * When this panel has been clicked, start a new game
		 */
		public void mousePressed(MouseEvent event)
		{

			frame.newGame();

		}

	}
}
