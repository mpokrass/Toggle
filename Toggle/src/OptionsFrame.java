import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * A frame that has time, minimum board score, and hint options
 * 
 * @author Michelle Pokrass
 * 
 */
public class OptionsFrame extends JFrame
{
	private ButtonGroup timeLimit;

	private ButtonGroup hintLimit;

	private ButtonGroup gameScore;

	private JRadioButton oneMin;

	private JRadioButton twoMin;

	private JRadioButton threeMin;

	private JRadioButton twoHint;

	private JRadioButton threeHint;

	private JRadioButton fourHint;

	private JRadioButton score100;

	private JRadioButton score150;

	private JRadioButton score200;

	private static final Font WORD_FONT = new Font(
			"Franklin Gothic Medium Cond", Font.PLAIN, 28);

	private ToggleMain frame;

	/**
	 * Creates a new frame
	 * 
	 * @param parentFrame
	 *            the parent frame
	 */
	public OptionsFrame(ToggleMain parentFrame)
	{
		// Creates the frame
		super("Options");
		frame = parentFrame;
		// Create a new panel for all the buttons and set the layout
		JPanel allButtons = new JPanel();
		allButtons.setLayout(new BoxLayout(allButtons, BoxLayout.X_AXIS));
		setLayout(new BorderLayout());
		// Create a new button group for the time limit
		timeLimit = new ButtonGroup();
		oneMin = new JRadioButton("1 Minute");
		twoMin = new JRadioButton("2 Minutes");
		threeMin = new JRadioButton("3 Minutes");
		oneMin.setFont(WORD_FONT);
		twoMin.setFont(WORD_FONT);
		threeMin.setFont(WORD_FONT);
		threeMin.setSelected(true);
		timeLimit.add(oneMin);
		timeLimit.add(twoMin);
		timeLimit.add(threeMin);
		// Add all of the time options to their own panel
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(0, 1));
		JLabel timeLabel = new JLabel("Time Limit");
		timeLabel.setFont(WORD_FONT);
		timePanel.add(timeLabel);
		timePanel.add(oneMin);
		timePanel.add(twoMin);
		timePanel.add(threeMin);
		// Add the time panel to the panel of buttons
		allButtons.add(timePanel);
		// Add a spacer panel to the panel of buttons
		allButtons.add(new JPanel());
		// Create a new button group for the hint options
		hintLimit = new ButtonGroup();
		twoHint = new JRadioButton("2 Hints");
		threeHint = new JRadioButton("3 Hints");
		fourHint = new JRadioButton("4 Hints");
		twoHint.setFont(WORD_FONT);
		threeHint.setFont(WORD_FONT);
		fourHint.setFont(WORD_FONT);
		fourHint.setSelected(true);
		hintLimit.add(twoHint);
		hintLimit.add(threeHint);
		hintLimit.add(fourHint);
		// Add all of the hint options to their own panel
		JPanel hintPanel = new JPanel();
		hintPanel.setLayout(new GridLayout(0, 1));
		JLabel hintLabel = new JLabel("Number of hints");
		hintLabel.setFont(WORD_FONT);
		hintPanel.add(hintLabel);
		hintPanel.add(twoHint);
		hintPanel.add(threeHint);
		hintPanel.add(fourHint);
		// Add the time panel to the panel of buttons
		allButtons.add(hintPanel);
		// Add a spacer panel to the panel of buttons
		allButtons.add(new JPanel());
		// Create a new button group for the score options
		gameScore = new ButtonGroup();
		score100 = new JRadioButton("100 Points");
		score150 = new JRadioButton("150 Points");
		score200 = new JRadioButton("200 Points");
		score100.setFont(WORD_FONT);
		score150.setFont(WORD_FONT);
		score200.setFont(WORD_FONT);
		score100.setSelected(true);
		gameScore.add(score100);
		gameScore.add(score150);
		gameScore.add(score200);
		// Add all of the score options to their own panel
		JPanel score = new JPanel();
		score.setLayout(new GridLayout(0, 1));
		JLabel scoreLabel = new JLabel("Minimum score on board");
		scoreLabel.setFont(WORD_FONT);
		score.add(scoreLabel);
		score.add(score100);
		score.add(score150);
		score.add(score200);
		// Add the score panel to the panel of buttons
		allButtons.add(score);
		// Create a submit button for the options
		JButton submit = new JButton("Submit");
		submit.setFont(WORD_FONT);
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				// Upon mouseclick, submit all the options
				submitButtons();

			}
		});
		// Create a panel to contain all of the components and set the layout
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		// Add all of the components
		options.add(allButtons);
		options.add(submit);
		add(options);
		pack();

	}

	/**
	 * Submit all of the options that have been selectes
	 */
	public void submitButtons()
	{
		// Set the time limit, based on the radio button selected
		if (oneMin.isSelected())
		{
			frame.setTime(60);
		}
		if (twoMin.isSelected())
		{
			frame.setTime(120);
		}
		if (threeMin.isSelected())
		{
			frame.setTime(180);
		}
		// Set the hint limit, based on the radio button selected
		if (twoHint.isSelected())
		{
			frame.setMaxHint(2);
		}
		if (threeHint.isSelected())
		{
			frame.setMaxHint(3);
		}
		if (fourHint.isSelected())
		{
			frame.setMaxHint(4);
		}
		// Set the minimum board score, based on the radio button selected
		if (score100.isSelected())
		{
			frame.setMinScore(100);
			System.out.println("Sent min score");
		}
		if (score150.isSelected())
		{
			frame.setMinScore(150);
			System.out.println("Sent min score");
		}
		if (score200.isSelected())
		{
			frame.setMinScore(200);
			System.out.println("Sent min score");
		}
		// Make this frame invisible
		this.setVisible(false);
	}

}
