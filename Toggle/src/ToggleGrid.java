import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates a boggle grid from a file, with all the words on the board and
 * maximum score for the board
 * 
 * @author Michelle Pokrass
 * 
 */
public class ToggleGrid
{
	private char[][] grid;

	private ArrayList<String> wordsOnGrid;

	private int maxScore = 0;

	private Cube[] shuffled;

	private ArrayList<String> foundWords;

	private String dictName;

	private ArrayList<Cube> cubes;

	private static int scoreThreshhold;

	/**
	 * Creates a new grid, solves it and updates the words in the list
	 * 
	 * @param gridName
	 *            the String name of the file containing the grid
	 * @param listName
	 *            the String name of the list of all of the legal words
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public ToggleGrid(String gridName, String listName)
			throws FileNotFoundException
	{
		// Open and read in the file
		try
		{
			Scanner fileIn = new Scanner(new File(gridName));
			String firstLine = fileIn.nextLine();
			int gridSize = firstLine.length();
			grid = new char[gridSize][gridSize];
			grid[0] = firstLine.toCharArray();
			for (int row = 1; row < gridSize; row++)
				grid[row] = fileIn.nextLine().toCharArray();
			fileIn.close();
		}
		catch (FileNotFoundException exp)
		{
			// Deal with File error
			grid = new char[][] { { 'B', 'A', 'D', '*' },
					{ 'F', 'I', 'L', 'E' } };
		}
		this.dictName = listName;
		// Set words on grid
		wordsOnGrid = solve(listName);
		foundWords = new ArrayList<String>();

	}

	/**
	 * @param listName
	 *            the String name of the file containing all legal words
	 * @param minBoardScore
	 *            the minimum score of the board
	 * 
	 */
	public ToggleGrid(String listName, int minBoardScore)
	{
		// Set the minumum score of the board
		scoreThreshhold = minBoardScore;
		// Create an array for the words that have been found by the user
		foundWords = new ArrayList<String>();
		// Create a new grid for the letters
		grid = new char[4][4];
		// Create a list of cubes
		cubes = new ArrayList<Cube>();

		this.dictName = listName;
	}

	/**
	 * Creates a String representation of the grid
	 */
	public String toString()
	{
		// Goes through every row and every column, adding it to the string
		String str = "";
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[row].length; col++)
			{
				str += grid[row][col];
			}
			// Add a new line after every row
			str += "\n";
		}
		return str;
	}

	/**
	 * Checks if the given word can be found on this board starting at the
	 * position defined by the row and the column
	 * 
	 * @param row
	 *            the row being checked
	 * @param col
	 *            the column being checked
	 * @param word
	 *            the word being searched for
	 * @return true if the word is on the board, false otherwise
	 */
	private boolean canFindWord(int row, int col, String word)
	{
		// Check that row and col are on the grid
		if (row < 0 || row >= grid.length || col < 0 || col >= grid[row].length)
			return false;
		// If the word is empty, return true
		if (word.length() == 0)
			return true;
		// Check that the first letter of word matches the grid for this row and
		// column
		if (word.charAt(0) != grid[row][col])
			return false;
		// If the word has only one letter, it is on the board

		if (word.length() == 1 && word.charAt(0) == 'Q')
			return false;
		else if (word.length() == 1)
			return true;
		String wordLeft;
		// If the first and second characters are "Qu", continue searching the
		// word starting from the next character after "u"
		if (word.charAt(0) == 'Q' && word.charAt(1) == 'U')
		{
			wordLeft = word.substring(2);
		}
		else if (word.charAt(0) == 'Q' && word.charAt(1) != 'U')
		{
			return false;
		}
		// Otherwise, search for the word starting from the second character
		else
		{
			wordLeft = word.substring(1);
		}
		// Mark this spot as used
		grid[row][col] += 32;
		// Search all around the spot in eight directions
		boolean found = canFindWord(row, col - 1, wordLeft)
				|| canFindWord(row, col + 1, wordLeft)
				|| canFindWord(row - 1, col, wordLeft)
				|| canFindWord(row - 1, col + 1, wordLeft)
				|| canFindWord(row - 1, col - 1, wordLeft)
				|| canFindWord(row + 1, col, wordLeft)
				|| canFindWord(row + 1, col - 1, wordLeft)
				|| canFindWord(row + 1, col + 1, wordLeft);
		// Unmark this spot
		grid[row][col] -= 32;
		return found;
	}

	/**
	 * Searches for a given word on the entire board
	 * 
	 * @param word
	 *            the word being searched for
	 * @return true if the word is on the board, false otherwise
	 */
	boolean search(String word)
	{
		// Go through every position, using it as the starting position to
		// search for the word
		for (int row = 0; row < grid.length; row++)
			for (int col = 0; col < grid[row].length; col++)
				if (canFindWord(row, col, word))
					return true;
		return false;
	}

	/**
	 * Finds all of the words in the board and calculate the maximum score
	 * 
	 * @param fileName
	 *            the name of the file containing all of the words
	 * @return all of the words on the board
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	private ArrayList<String> solve(String fileName)
			throws FileNotFoundException
	{
		// Get all the words in the file
		Scanner fileIn = new Scanner(new File(fileName));
		ArrayList<String> allWords = new ArrayList<String>();
		while (fileIn.hasNext())
		{
			allWords.add(fileIn.next());
		}
		ArrayList<String> foundWords = new ArrayList<String>();
		// Create a list of the scores for words of length less than 8
		int[] scores = { 0, 0, 0, 1, 1, 2, 3, 5 };
		for (String word : allWords)
		{
			// If the word is found, and it can be counted, add the score
			if (search(word) && (word.length() > 3 || grid[0].length == 4))
			{
				foundWords.add(word);
				if (word.length() < 8)
				{
					maxScore += scores[word.length()];
				}
				// If the word length is greater than 7, add 11 to the score
				else
				{
					maxScore += 11;
				}
			}

		}

		return foundWords;
	}

	/**
	 * Returns the number of words on the board
	 * 
	 * @return the number of words on the board
	 */
	public int getNoOfWords()
	{
		if (wordsOnGrid != null)
			return wordsOnGrid.size();
		return -1;
	}

	/**
	 * Returns the maximum score of the board
	 * 
	 * @return the maximum score of the board
	 */
	public int getMaxScore()
	{
		return maxScore;
	}

	/**
	 * Return the longest word on the grid
	 * 
	 * @return returns the longest word on the grid
	 */
	public String returnLongest()
	{
		// Make a string for the longest word
		String longestWord = "";
		// Go through every word on the board, if it is longer than the current
		// longest word, set the longest word as this word
		for (String word : wordsOnGrid)
		{
			if (word.length() > longestWord.length())
			{
				longestWord = word;
			}
		}
		return longestWord;
	}

	/**
	 * Returns the grid
	 * 
	 * @return the character array of letters
	 */
	public char[][] returnGrid()
	{
		return grid;
	}

	/**
	 * Returns the shuffled array of cubes
	 * 
	 * @return the shuffled array of cubes
	 */
	public Cube[] returnCubes()
	{
		return shuffled;
	}

	/**
	 * Checks if word is on the board and hasn't been found yet
	 * 
	 * @param word
	 *            the word being checked
	 * @return true if the word is on the board and hasn't been found yet, false
	 *         otherwise
	 */
	public boolean checkWord(String word)
	{
		if ((Collections.binarySearch(wordsOnGrid, word) > 0)
				&& (!(foundWords.contains(word))))
		{
			// Add the found word to the list of words found by the user
			foundWords.add(word);
			return true;
		}
		else
			return false;

	}

	/**
	 * Gives the point value of the given word
	 * 
	 * @param word
	 *            the word being checked
	 * @return the point value of the given word
	 */
	public int returnPoints(String word)
	{
		int[] scores = { 0, 0, 0, 1, 1, 2, 3, 5 };
		if (word.length() > 7)
			return 11;
		else
			return scores[word.length()];
	}

	/**
	 * Randomly returns a word that has not been found yet
	 * 
	 * @return a String word that has not been found
	 */
	public String returnHint()
	{
		Random rndm = new Random();
		int size = wordsOnGrid.size();
		// Generate a random position
		int randomSpot = rndm.nextInt(size);
		// If the word at that spot has already been found, generate a new
		// random position
		while (foundWords.contains(wordsOnGrid.get(randomSpot)))
		{
			randomSpot = rndm.nextInt(size);
		}
		// Otherwise return the word at that position
		return wordsOnGrid.get(randomSpot);
	}

	/**
	 * Resets the list of found words
	 */
	public void resetFound()
	{
		foundWords.clear();
	}

	/**
	 * Starts a new game
	 * 
	 * @throws IOException
	 *             if the cube image is not found
	 */
	public void newGame() throws IOException
	{
		// Keep making grids while they are below the score threshhold
		while (maxScore < scoreThreshhold)
		{
			// Clear the list of cubes
			cubes.clear();
			// Make 16 cubes using the given letter lists
			Cube cube1 = new Cube("YTLRTM");
			Cube cube2 = new Cube("VTHREW");
			Cube cube3 = new Cube("ZNRNHL");
			Cube cube4 = new Cube("XEDLIR");
			Cube cube5 = new Cube("BAOBOJ");
			Cube cube6 = new Cube("GEWNEH");
			Cube cube7 = new Cube("UQHMNI");
			Cube cube8 = new Cube("YIDSTT");
			Cube cube9 = new Cube("KAFPSF");
			Cube cube10 = new Cube("USEEIN");
			Cube cube11 = new Cube("TIESOS");
			Cube cube12 = new Cube("MTOIVC");
			Cube cube13 = new Cube("VLREYD");
			Cube cube14 = new Cube("TOATWO");
			Cube cube15 = new Cube("GAEAEN");
			Cube cube16 = new Cube("SCAOPH");
			// Add the cubes to an array list
			cubes.add(cube1);
			cubes.add(cube2);
			cubes.add(cube3);
			cubes.add(cube4);
			cubes.add(cube5);
			cubes.add(cube6);
			cubes.add(cube7);
			cubes.add(cube8);
			cubes.add(cube9);
			cubes.add(cube10);
			cubes.add(cube11);
			cubes.add(cube12);
			cubes.add(cube13);
			cubes.add(cube14);
			cubes.add(cube15);
			cubes.add(cube16);
			// Create an array for the shuffles cubes
			shuffled = new Cube[16];
			// Go through every cube, finding a random position for it on the
			// shuffled array
			for (Cube cube : cubes)
			{
				Random rndm = new Random();
				int position = rndm.nextInt(16);
				while (shuffled[position] != null)
				{
					position = rndm.nextInt(16);
				}
				shuffled[position] = cube;
			}
			// Set the grid using the shuffled array
			for (int i = 0; i < 16; i++)
			{
				grid[i / 4][i % 4] = shuffled[i].returnChar();
			}
			// Find all of the words on the grid, and update the maximum points
			wordsOnGrid = solve(dictName);
		}
		// Clear the list of found words
		foundWords.clear();
	}

}
