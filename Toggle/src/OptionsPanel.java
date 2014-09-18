import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Create a panel for the options button
 * 
 * @author Michelle Pokrass
 * 
 */
public class OptionsPanel extends JPanel
{
	private ToggleMain frame;

	private BufferedImage picture;

	/**
	 * Creates a panel
	 * 
	 * @param frame
	 *            the parent frame
	 * @throws IOException
	 *             throws an exception if the image is not found
	 */
	public OptionsPanel(ToggleMain frame) throws IOException
	{
		// Gets the image
		picture = ImageIO.read(new File("images\\options.png"));
		this.frame = frame;
		// Add a mouse handler
		addMouseListener(new MouseHandler());
	}

	// Draw the image
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);
	}

	// Create a mouse handler
	private class MouseHandler extends MouseAdapter
	{
		// Upon mouseclick, display the options
		public void mousePressed(MouseEvent event)
		{

			frame.displayOptions();

		}

	}

}
