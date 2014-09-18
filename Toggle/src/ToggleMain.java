import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.net.*;

/**
 * A frame that contains a game area, word list, score, current word, and
 * buttons
 * 
 * @author Michelle Pokrass
 * 
 */
public class ToggleMain extends JFrame
{
	private GamePanel grid;

	private GamePanel tableArea;

	private static ToggleGrid toggle;

	private ImageIcon frameIcon = new ImageIcon("images\\Icon.png");

	private static final Font LIST_FONT = new Font("Arial", Font.BOLD, 16);

	private static final Font TITLE_FONT = new Font("Franklin Gothic Medium",
			Font.PLAIN, 32);

	private static final Font WORD_FONT = new Font(
			"Franklin Gothic Medium Cond", Font.PLAIN, 30);

	private static final Font SPACER_FONT = new Font("Arial", Font.PLAIN, 2);

	private List wordList;

	private JLabel currentScore;

	private JLabel currentWord;

	private JPanel middlePanel;

	private JPanel buttonPanel;

	private HintPanel hint;

	private JLabel timerLabel;

	private JLabel spacerLabel;

	private int time;

	private static int minBoardScore;

	private OptionsFrame optionsFrame;// = new OptionsFrame();

	/**
	 * Creates a new frame
	 * 
	 * @throws IOException
	 *             if images are not found
	 */
	public ToggleMain() throws IOException
	{
		// Creates the jframe
		super("Toggle");
		// Set initial variables
		time = 180;
		minBoardScore = 100;
		setResizable(false);
		setBackground(new Color(0, 0, 125));
		// Position in the middle of the window
		setLocation(100, 100);
		tableArea = new GamePanel(this);
		this.setIconImage(frameIcon.getImage());
		setLayout(new BorderLayout());
		// Add a panel to contain all of the found words
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		JLabel sideTitle = new JLabel("Words", JLabel.CENTER);
		sideTitle.setFont(TITLE_FONT);
		sidePanel.add(sideTitle, BorderLayout.NORTH);
		wordList = new List(10);
		wordList.setFont(LIST_FONT);
		sidePanel.add(wordList, BorderLayout.CENTER);
		// Create a label to display the current word
		currentWord = new JLabel("Current word: ");
		currentWord.setMaximumSize(new Dimension(405, 50));
		currentWord.setBackground(new Color(0, 0, 125));
		// Create a label to display the current score
		currentScore = new JLabel("  Score: " + tableArea.getScore());
		currentScore.setFont(TITLE_FONT);
		currentWord.setFont(WORD_FONT);
		sidePanel.add(currentScore, BorderLayout.SOUTH);
		middlePanel = new JPanel();
		// Add all of the components in the middle to their own panel
		middlePanel.setPreferredSize(new Dimension(420, 450));
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		middlePanel.add(tableArea, BorderLayout.NORTH);
		middlePanel.add(currentWord, BorderLayout.SOUTH);
		// Add the middle and side panel to this frame
		add(middlePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.WEST);
		// Create a new panel for the buttons and set its layout
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		// Add the logo image to the button panel
		ImageIcon image = new ImageIcon("images\\logo.png");
		JLabel logo = new JLabel(image);
		buttonPanel.add(logo);
		// Create panels for all of the buttons
		NewGamePanel newGame = new NewGamePanel(this);
		RestartPanel restart = new RestartPanel(this);
		hint = new HintPanel(this);
		OptionsPanel options = new OptionsPanel(this);
		// Add all of the buttons to the button panel
		buttonPanel.add(newGame);
		// Create a label to display the time
		timerLabel = new JLabel("Time: " + tableArea.getTime());
		timerLabel.setFont(TITLE_FONT);
		buttonPanel.add(newGame);
		buttonPanel.add(hint);
		buttonPanel.add(restart);
		buttonPanel.add(options);
		buttonPanel.add(timerLabel);
		// Set the preferred size of the button panel and add it to this frame
		buttonPanel.setPreferredSize(new Dimension(300, 460));
		add(buttonPanel, BorderLayout.EAST);
		// Create a new frame for options that is not visible
		optionsFrame = new OptionsFrame(this);
		optionsFrame.setVisible(false);
		// Redraw this frame
		setPreferredSize(new Dimension(860, 460));
		repaint();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		// Create a new grid of words
		toggle = new ToggleGrid("boggleWords.txt", minBoardScore);
		// create this frame and start a new game
		ToggleMain frame = new ToggleMain();
		frame.newGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Returns the grid of letters
	 * 
	 * @return the grid of letters
	 */
	public char[][] returnGrid()
	{
		return toggle.returnGrid();
	}

	/**
	 * Display a dialog box when time is up
	 */
	public void gameOver()
	{
		// If the game is over, display a dialog box
		if (tableArea.getGameOver())
		{

			String[] buttons = { "New Game", "Exit" };
			JOptionPane pane = new JOptionPane(
					"Great Job! Your final Score is " + tableArea.getScore()
							+ "!" + "\n The highest point word on the grid is "
							+ toggle.returnLongest());
			pane.setOptions(buttons);
			JDialog dialog = pane.createDialog(this, "Game Over ...");
			this.setFocusable(false);
			dialog.setVisible(true);
			Object obj = pane.getValue();
			int result = -1;
			for (int k = 0; k < buttons.length; k++)
				if (buttons[k].equals(obj))
					result = k;
			// Either start a new game or close the frame
			if (result == 0)
			{
				this.newGame();
				this.setFocusable(true);
			}
			else if (result == 1)
			{
				this.dispose();
			}
		}
	}

	/**
	 * Returns the list of cubes
	 * 
	 * @return the cube array
	 */
	public Cube[] returnCubes()
	{
		return toggle.returnCubes();
	}

	/**
	 * Returns the togglegrid
	 * 
	 * @return the togglegrid
	 */
	public ToggleGrid returnToggleGrid()
	{
		return toggle;
	}

	/**
	 * Adds a word to the word list and sets the current score
	 * 
	 * @param word
	 *            the word to be added
	 */
	public void addWord(String word)
	{
		wordList.add(word);
		currentScore.setText("Score: " + tableArea.getScore());

	}

	/**
	 * Updates the current word
	 */
	public void updateWord()
	{
		currentWord.setText("Current word: " + tableArea.getWord());
	}

	/**
	 * Updates the current time
	 */
	public void updateTime()
	{
		timerLabel.setText("                 Time: " + tableArea.getTime());
	}

	/**
	 * Starts a new game
	 */
	public void newGame()
	{
		// Removes all the words from the word list
		wordList.removeAll();
		// Creates a new grid, starting a new game on it
		try
		{
			toggle = new ToggleGrid("boggleWords.txt", minBoardScore);
			toggle.newGame();
		}
		catch (IOException e)
		{
			// If the files are not found, an exception will be thrown
		}

		try
		{
			// Remove the game panel and create a new one
			middlePanel.remove(tableArea);
			tableArea = new GamePanel(this);
			// Start the timer
			tableArea.getTimer().start();
			// Re-add this panel
			middlePanel.add(tableArea, BorderLayout.NORTH);
			middlePanel.add(currentWord);
			this.add(middlePanel);
			// Redraw the screen
			repaint();
		}
		catch (IOException e)
		{
			// If the images are not found, an exception will be thrown
		}
		// Set the current score, time, and word
		currentScore.setText("Score: " + tableArea.getScore());
		timerLabel.setText("                 Time: " + tableArea.getTime());
		currentWord.setText("Current word: " + tableArea.getWord());
		// Reset the hints and redraw
		hint.resetHints();
		repaint();
	}

	/**
	 * Display a hint
	 */
	public void getHint()
	{
		currentWord.setText("Hint: " + toggle.returnHint());
	}

	/**
	 * Display that there are no hints remaining
	 */
	public void noHint()
	{
		currentWord.setText("No more hints!");
	}

	/**
	 * Restart the current game
	 */
	public void restartGame()
	{
		// Remove all the words from the word list
		wordList.removeAll();
		// Set the score to zero and reset the time
		tableArea.setScore(0);
		tableArea.resetTime();
		// Update the labels
		currentScore.setText("Score: " + tableArea.getScore());
		currentWord.setText("Current word: " + tableArea.getWord());
		timerLabel.setText("Time: " + tableArea.getTime());
		// Reset the hints
		hint.resetHints();
		// Reset the list of found words
		toggle.resetFound();
		// Redraw the screen
		repaint();
	}

	/**
	 * Display the options frame
	 */
	public void displayOptions()
	{
		// Set the frame to visible, set this frame to unfocusable
		optionsFrame.setVisible(true);
		this.setFocusable(false);
		this.setFocusableWindowState(false);
	}

	/**
	 * Set the lowest score that the game board can have
	 * 
	 * @param minScore
	 *            the integer new lowest game board score
	 */
	public void setMinScore(int minScore)
	{

		minBoardScore = minScore;

	}

	/**
	 * Returns the time remaining
	 * 
	 * @return the time remaining
	 */
	public int getTime()
	{
		return time;
	}

	/**
	 * Sets the time
	 * 
	 * @param num
	 *            the new time
	 */
	public void setTime(int num)
	{
		time = num;
	}

	/**
	 * Sets the amound of hints there can be
	 * 
	 * @param num
	 *            the number of hints
	 */
	public void setMaxHint(int num)
	{
		hint.setHintNum(num);
	}
}
