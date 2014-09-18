import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A panel that contains 16 cubes and keeps track of the current game score,
 * time, and selected word
 * 
 * @author Michelle Pokrass
 * 
 */
public class GamePanel extends JPanel
{
	private ToggleMain parentFrame;

	private Cube[] shuffled;

	private String word = "";

	private Cube[] cubeList;

	private boolean[] checked;

	private ToggleGrid toggleGrid;

	private int score;

	private int lastRow = 0;

	private int lastCol = 0;

	private Timer timer;

	private boolean gameOver;

	private int time;

	/**
	 * Creates a panel and draws the grid
	 * 
	 * @param parentFrame
	 *            the togglemain parent
	 * @throws IOException
	 *             when images are not found
	 */
	public GamePanel(ToggleMain parentFrame) throws IOException
	{

		// Set up the size and background color
		setPreferredSize(new Dimension(400, 400));
		this.parentFrame = parentFrame;
		// Set the layout of this to a grid
		this.setLayout(new GridLayout(4, 4));
		time = parentFrame.getTime();
		gameOver = false;
		score = 0;
		// Get the letter cubes from the parent frame
		cubeList = parentFrame.returnCubes();
		checked = new boolean[16];
		// Add all of the cubes to this panel
		if (cubeList == null)
		{
			for (int i = 0; i < 16; i++)
				this.add(new Cube());
		}
		else
		{
			for (Cube cube : cubeList)
			{
				this.add(cube);
			}
		}
		this.repaint();
		// Create a timer object. This object generates an event every
		// 1 second (1000 milliseconds)
		// The TimerEventHandler object that will handle this timer
		// event is defined below as a inner class
		timer = (new Timer(1000, new TimerEventHandler()));
		// Add mouse listeners to the table panel
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());
		toggleGrid = parentFrame.returnToggleGrid();

	}

	/**
	 * Draw the panel
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

	}

	/**
	 * Handles mouse events for the panel
	 * 
	 * @author Michelle Pokrass
	 * 
	 */
	private class MouseHandler extends MouseAdapter
	{
		/**
		 * Handles mouse presses
		 */
		public void mousePressed(MouseEvent event)
		{
			Point selectedPoint = event.getPoint();
			// Checks every cube, checking which cube the mouse clicked on
			for (int i = 0; i < 16; i++)
			{
				if (cubeList[i].getBounds().contains(selectedPoint))
				{
					// Add the top face of the cube to the current word
					char letter = cubeList[i].returnChar();
					if (letter == 'Q')
					{
						word += "QU";
					}
					else
					{
						word += cubeList[i].returnChar();
					}
					// Set that this space has been checked
					checked[i] = true;
					// Set that this cube is the last row and column
					lastRow = i / 4;
					lastCol = i % 4;
					// Highlight the current cube
					try
					{
						cubeList[i].highlight();
					}
					catch (IOException e)
					{
						// If the images are not found, an exception will occur
					}
					// Update the word displayed on the parent frame
					parentFrame.updateWord();
					break;
				}
			}
			// Redraw the board
			repaint();
		}

		/**
		 * Handles mouse releases for the board
		 */
		public void mouseReleased(MouseEvent event)
		{
			// If the current word is valid and has not been found yet, add its
			// points to the score
			if (toggleGrid.checkWord(word))
			{
				int wordPoints = toggleGrid.returnPoints(word);
				score += wordPoints;
				// Add the word that has been just checked to the list of found
				// words
				parentFrame.addWord("" + word + "(" + wordPoints + ")");
				parentFrame.repaint();
			}
			// Un highlight all of the cubes
			for (Cube cube : cubeList)
			{
				try
				{
					cube.unHightLight();
				}
				catch (IOException e)
				{
					// If the images are not found, and exception will be thrown
				}
			}
			// Clears the current word
			word = "";
			parentFrame.updateWord();
			// Clears the array to show that no letters have been used
			checked = new boolean[16];
			// Redraws the board
			repaint();
		}
	}

	/**
	 * Handles mouse motions for the panel
	 * 
	 * @author Michelle Pokrass
	 * 
	 */
	private class MouseMotionHandler implements MouseMotionListener
	{
		/**
		 * Do nothing when the mouse is moved
		 */
		public void mouseMoved(MouseEvent event)
		{

		}

		/**
		 * When the mouse is dragged, check which cube it is in and highlight it
		 */
		public void mouseDragged(MouseEvent event)
		{
			Point currentPoint = event.getPoint();
			// Check which cube the mouse is in
			for (int i = 0; i < 16; i++)
			{
				if (cubeList[i].returnBounds().contains(currentPoint))
				{
					// If this cube has not been checked yet and it is adjacent
					// to the previous cube, add its letter to the current word
					if (!(checked[i]) && adjacent(i / 4, i % 4))
					{

						char letter = cubeList[i].returnChar();
						if (letter == 'Q')
						{
							word += "QU";
						}
						else
						{
							word += cubeList[i].returnChar();
						}
						// Highlight the cube
						try
						{
							cubeList[i].highlight();
						}
						catch (IOException e)
						{
							// If the image is not found, an exception will be
							// thrown
						}
						// Set that this cube has been checked
						checked[i] = true;
						// Set the last row and column as this cube
						lastRow = i / 4;
						lastCol = i % 4;
						// Update the current word and redraw the panel
						parentFrame.updateWord();
						repaint();
					}
				}
			}

		}

	}

	// An inner class to deal with the timer events
	private class TimerEventHandler implements ActionListener
	{
		// The following method is called each time a timer event is
		// generated
		public void actionPerformed(ActionEvent event)
		{
			// Increment the time by one every second
			if (time != 0)
				time--;
			// When time is up, stop the timer and end the game
			else
			{
				timer.stop();
				gameOver = true;
				parentFrame.gameOver();
			}
			// Update the time
			parentFrame.updateTime();
			// Repaint the screen
			repaint();
		}
	}

	/**
	 * Returns the time, formatted in minutes and seconds
	 * 
	 * @return the time formatted in minutes and seconds
	 */
	public String getTime()
	{

		return String.format("%d:%02d", time / 60, time % 60);
	}

	/**
	 * Resets the time to the defined interval
	 */
	public void resetTime()
	{
		time = parentFrame.getTime();
	}

	/**
	 * Returns the timer
	 * 
	 * @return the timer in this panel
	 */
	public Timer getTimer()
	{
		return timer;
	}

	/**
	 * Returns the current score
	 * 
	 * @return the integer score
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Returns the current word
	 * 
	 * @return the string word
	 */
	public String getWord()
	{
		return word;
	}

	/**
	 * Sets the score to zero
	 */
	public void resetScore()
	{
		score = 0;
	}

	/**
	 * Sets the score to a given number
	 * 
	 * @param newScore
	 *            integer new score
	 */
	public void setScore(int newScore)
	{
		score = newScore;
	}

	/**
	 * Checks if the given row and column are adjacent to the last row and
	 * column
	 * 
	 * @param row
	 *            the given row
	 * @param col
	 *            the given column
	 * @return true if adjacent, false otherwise
	 */
	public boolean adjacent(int row, int col)
	{
		// Checks the left
		boolean left = (lastRow - 1 == row) && (col == lastCol);
		// Checks the right
		boolean right = (lastRow + 1 == row) && (col == lastCol);
		// Checks upwards
		boolean up = (lastRow == row) && (col - 1 == lastCol);
		// Checks downwards
		boolean down = (lastRow == row) && (col + 1 == lastCol);
		// Checks diagonally
		boolean upRight = (lastRow + 1 == row) && (col - 1 == lastCol);
		// Checks diagonally
		boolean upLeft = (lastRow - 1 == row) && (col - 1 == lastCol);
		// Checks diagonally
		boolean downRight = (lastRow + 1 == row) && (col + 1 == lastCol);
		// Checks diagonally
		boolean downLeft = (lastRow - 1 == row) && (col + 1 == lastCol);
		// Only one direction needs to be true for the spot to be adjacent
		return left || right || up || down || upRight || upLeft || downRight
				|| downLeft;
	}

	/**
	 * Returns if the game is over
	 * 
	 * @return true if the time is up, false otherwise
	 */
	public boolean getGameOver()
	{
		return gameOver;
	}

}
