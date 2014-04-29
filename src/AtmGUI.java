/*
 * AtmGUI.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: AtmGUI.java,v $
 * Revision 1.4 2013/04/20 07:25:32 sob8666
 * *** empty log message ***
 * 
 * Revision 1.3 2012-05-17 17:59:34 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.2 2012-05-17 03:37:56 sob8666
 * Remains A little fix of batch and ATM GUI
 * 
 * Revision 1.1 2012-05-14 05:44:25 sob8666
 * BatchMode finished, except for final Data
 */

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Creates a ATM GUI based ATM Model provided
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public class AtmGUI
{

	private TextField		accountNPinNumberInput;
	private JButton			zeroButton		= new JButton("0");
	private JButton			oneButton		= new JButton("1");
	private JButton			twoButton		= new JButton("2");
	private JButton			threeButton		= new JButton("3");
	private JButton			fourButton		= new JButton("4");
	private JButton			fiveButton		= new JButton("5");
	private JButton			sixButton		= new JButton("6");
	private JButton			sevenButton		= new JButton("7");
	private JButton			eightButton		= new JButton("8");
	private JButton			nineButton		= new JButton("9");
	private JButton			okButton		= new JButton("OK");
	private JButton			cancelButton	= new JButton("Cancel");
	private JButton			closeButton		= new JButton("Close");
	private JButton			clearButton		= new JButton("Clear");
	private String			currentScreen	= "account";
	private JList<String>	alist;
	private JLabel			infoCenter		= new JLabel(
													"Check for necessary information");
	private JPanel			northPanel;
	private JPanel			buttonPanel;
	private AtmModel		am;
	private JFrame			frame;
	private JLabel			resultLabel;
	private JTextField		resultText;
	private String			AccountNumber	= "";

	/**
	 * Makes the ATM GUI with the ATM model provided
	 * 
	 * @param am
	 *            The ATMModel to model that provides information for the GUI
	 */
	public AtmGUI(AtmModel am)
	{
		this.am = am;
		Random random = new Random();
		Integer randInt = Math.abs(random.nextInt());

		if (!am.isUnique(randInt))
			randInt = Math.abs(random.nextInt());
		AtmModel.uniq.add(randInt);
		frame = new JFrame("Samuel Babalola: " + randInt);
		frame.setSize(500, 500);
		accountScreen();
		frame.setVisible(true);
	}

	/**
	 * Attach a functionality to each items in the list.
	 * 
	 * @param alist
	 *            The list of options in the ATM GUI
	 */
	void listListener(final JList<String> alist)
	{
		alist.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				if (alist.getSelectedIndex() == 0)
				{
					DecimalFormat f = new DecimalFormat("#0.00");
					resultLabel.setVisible(true);
					resultText.setVisible(true);
					resultLabel.setText("Balance");
					resultText.setEnabled(false);
					resultText.setText(f.format(am.getBalance()).toString());

				}
				else if (alist.getSelectedIndex() == 1)
				{
					resultLabel.setVisible(true);
					resultText.setVisible(true);
					resultLabel.setText("Deposit ");
					resultText.setEnabled(true);
					resultText.setText("");

				}
				else if (alist.getSelectedIndex() == 2)
				{
					resultLabel.setVisible(true);
					resultText.setVisible(true);
					resultLabel.setText("Withdraw ");
					resultText.setEnabled(true);
					resultText.setText("");
				}
				else if (alist.getSelectedIndex() == 3)
				{
					resultLabel.setVisible(false);
					resultText.setVisible(false);

				}
			}
		});
	}

	/**
	 * Shows the Account Screen and the Pin Screen
	 * 
	 */

	void accountScreen()
	{
		frame.setLayout(new BorderLayout());
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		northPanel = new JPanel(new GridLayout(3, 1));

		buttonPanel = new JPanel(new BorderLayout());

		JLabel titleLabel = new JLabel("Welcome to ACME");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		titlePanel.add(titleLabel);

		accountNPinNumberInput = new TextField();
		final JLabel accountNPinNumberLabel = new JLabel(""
				+ "Enter your Account Number: ");

		makeButton(buttonPanel);

		accountNPinNumberInput.setEditable(false);
		northPanel.add(titlePanel);
		northPanel.add(accountNPinNumberLabel);
		northPanel.add(accountNPinNumberInput);
		infoCenter.setHorizontalAlignment(SwingConstants.CENTER);

		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(infoCenter, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				if (currentScreen.equals("account"))
				{
					if (am.isaccounNumberValid(accountNPinNumberInput.getText())
							&& !AtmModel.accountslogged
									.contains(accountNPinNumberInput.getText()))
					{
						infoCenter.setText("Successful");
						AccountNumber = accountNPinNumberInput.getText();
						accountNPinNumberLabel.setText("Enter your Pin Number");
						accountNPinNumberInput.setText("");
						currentScreen = "pin";
						AtmModel.accountslogged.add(AccountNumber);
						accountNPinNumberInput.setEchoChar('*');
					}
					else
					{
						infoCenter.setText("Incorrect Account Number or "
								+ "this Account Number is already logged in");
					}
				}
				else if (currentScreen.equals("pin"))
				{
					if (am.isPinValid(accountNPinNumberInput.getText()))
					{
						infoCenter.setText("Successful");

						currentScreen = "transaction";
						transactionMenu();

					}
					else
					{
						AtmModel.accountslogged.remove(AccountNumber);
						infoCenter.setText("Incorrect Pin");
						currentScreen = "account";
						accountNPinNumberLabel.setText("Enter your Account "
								+ "Number: ");
						accountNPinNumberInput.setText("");
						accountNPinNumberInput.setEchoChar((char) 0);

					}
				}
				else if (currentScreen.equals("transaction"))
				{

					if (alist.getSelectedIndex() == 1)
					{
						am.depositIntoAccount(Double.parseDouble(resultText
								.getText()));
						resultText.setText("");
						infoCenter.setText("Successful Deposit");
						alist.setSelectedIndex(0);
					}
					else if (alist.getSelectedIndex() == 2)
					{
						am.withdrawFromAccount(Double.parseDouble(resultText
								.getText()));
						resultText.setText("");
						infoCenter.setText("Successful Withdraw");
						alist.setSelectedIndex(0);
					}
					else if (alist.getSelectedIndex() == 3)
					{
						AtmModel.accountslogged.remove(AccountNumber);
						frame.dispose();
					}
				}

			}

		});

	}

	/**
	 * Shows the Transaction Menu
	 */
	void transactionMenu()
	{
		northPanel.removeAll();
		buttonPanel.removeAll();
		// North
		String[] alistarray = { "Balance", "Deposit Money", "Withdraw Money",
				"Log Off" };
		alist = new JList<String>(alistarray);
		alist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel titleLabel = new JLabel("Account Number: " + AccountNumber);
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 25));
		northPanel.add(titleLabel);

		// center

		buttonPanel.setLayout(new GridLayout(2, 1));
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel resultPanel = new JPanel(new BorderLayout(10, 0));

		resultLabel = new JLabel("Balance");
		resultText = new JTextField();
		resultText.setEditable(false);
		resultText.setDisabledTextColor(Color.black);
		listPanel.add(alist, BorderLayout.CENTER);

		resultPanel.add(resultLabel, BorderLayout.WEST);
		resultPanel.add(resultText, BorderLayout.CENTER);

		listPanel.add(resultPanel, BorderLayout.SOUTH);
		buttonPanel.add(listPanel);
		makeButton(buttonPanel);
		listListener(alist);
		alist.setSelectedIndex(0);
		buttonPanel.validate();
		buttonPanel.repaint();
		northPanel.validate();
		northPanel.repaint();

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}

	/**
	 * Makes Buttons in the panel given.
	 * 
	 * @param panel
	 *            The area that needs the Button to show up on
	 */
	void makeButton(JPanel panel)
	{
		JPanel tempPanel = new JPanel(new GridLayout(5, 3));

		tempPanel.add(oneButton);
		tempPanel.add(twoButton);
		tempPanel.add(threeButton);
		tempPanel.add(fourButton);
		tempPanel.add(fiveButton);
		tempPanel.add(sixButton);
		tempPanel.add(sevenButton);
		tempPanel.add(eightButton);
		tempPanel.add(nineButton);
		tempPanel.add(okButton);
		tempPanel.add(zeroButton);
		tempPanel.add(cancelButton);
		tempPanel.add(new JLabel());
		tempPanel.add(closeButton);
		tempPanel.add(clearButton);

		removeListeners(oneButton);
		removeListeners(twoButton);
		removeListeners(threeButton);
		removeListeners(fourButton);
		removeListeners(fiveButton);
		removeListeners(sixButton);
		removeListeners(sevenButton);
		removeListeners(eightButton);
		removeListeners(nineButton);
		removeListeners(zeroButton);

		buttonListeners(oneButton);
		buttonListeners(twoButton);
		buttonListeners(threeButton);
		buttonListeners(fourButton);
		buttonListeners(fiveButton);
		buttonListeners(sixButton);
		buttonListeners(sevenButton);
		buttonListeners(eightButton);
		buttonListeners(nineButton);
		buttonListeners(zeroButton);
		buttonListeners(clearButton);
		buttonListeners(closeButton);
		buttonListeners(cancelButton);

		panel.add(tempPanel);

	}

	/**
	 * Removes the listeners from the provided button
	 * 
	 * @param button
	 *            The Button that wants its listeners removed
	 */
	public void removeListeners(final JButton button)
	{
		for (ActionListener al : button.getActionListeners())
		{
			button.removeActionListener(al);
		}
	}

	/**
	 * Adds Button listeners into the button provided.
	 * 
	 * @param button
	 *            The button that needs listeners added
	 */
	public void buttonListeners(final JButton button)
	{

		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (button.getText().equals("Clear"))
				{
					if (currentScreen.equals("account")
							|| currentScreen.equals("pin"))
					{
						accountNPinNumberInput.setText("");

					}
					else if (currentScreen.equals("transaction"))
					{
						resultText.setText("");
					}
				}
				else if (button.getText().equals("Close"))
				{
					if (AtmModel.accountslogged.contains(AccountNumber))
					{
						AtmModel.accountslogged.remove(AccountNumber);
					}
					frame.dispose();
				}
				else if (button.getText().equals("Cancel"))
				{
					if (currentScreen.equals("account")
							|| currentScreen.equals("pin"))
					{
						accountNPinNumberInput.setText("");

					}
					else if (currentScreen.equals("transaction"))
					{
						resultText.setText("");
						alist.setSelectedIndex(0);
					}
				}
				else
				{
					if (currentScreen.equals("account")
							|| currentScreen.equals("pin"))
					{
						accountNPinNumberInput.setText(accountNPinNumberInput
								.getText() + button.getText());

					}
					else if (currentScreen.equals("transaction")
							&& resultText.isEnabled())
					{

						String text = resultText.getText() + button.getText();

						resultText.setText(text);
					}
				}

			}
		});

	}
}
