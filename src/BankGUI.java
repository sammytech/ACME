/*
 * BankGUI.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: BankGUI.java,v $
 * Revision 1.4 2013/04/20 07:25:32 sob8666
 * *** empty log message ***
 * 
 * Revision 1.3 2012-05-17 17:59:33 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.2 2012-05-17 03:37:56 sob8666
 * Remains A little fix of batch and ATM GUI
 * 
 * Revision 1.1 2012-05-14 05:44:20 sob8666
 * BatchMode finished, except for final Data
 */
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Creates a Bank User Interface
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */

public class BankGUI
{
	private BankModel					bm;
	private String						textInFocus				= "";
	private final JTextField			accountNumberText		= new JTextField();
	private JLabel						accountNumberCheckLabel	= new JLabel(
																		"↞");
	private final JTextField			balanceText				= new JTextField();
	private JLabel						balanceCheckLabel		= new JLabel(
																		"↞");
	private final JPasswordField		pinText					= new JPasswordField(
																		4);
	private JLabel						pinCheckLabel			= new JLabel(
																		"↞");
	private final JTextField			withdrawText			= new JTextField(
																		"0.00");
	private final JTextField			depositText				= new JTextField(
																		"0.00");
	private final JComboBox<String>		accountTypeText;
	private final JLabel				info					= new JLabel(
																		"Shows all necessary information");
	private DefaultListModel<String>	availableModel			= new DefaultListModel<String>();			;
	private JList<String>				availableAccountList;
//	private ArrayList<String>			AccountNumber;
	private final JButton				updateButton			= new JButton(
																		"Update");

	/**
	 * Creates a Bank GUI with the given bank Model
	 * 
	 * @param bm
	 */

	public BankGUI(final BankModel bm)
	{
		this.bm = bm;
		JFrame frame = new JFrame("Samuel Babalola");
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(0, 10));
		JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 0));

		// frame panels
		JPanel leftCenterPanel = new JPanel(new BorderLayout(0, 10));
		JPanel centerCenterPanel = new JPanel(new GridLayout(12, 1));
		JPanel rightCenterPanel = new JPanel();

		// Available Accounts

		final ArrayList<Account> accounts = bm.getAvailableAccounts();

		for (Account i : accounts)
		{
			availableModel.addElement(i.getAccountNumber());
		}
		availableModel.addElement("Create New Account");

		availableAccountList = new JList<String>(availableModel);
		availableAccountList
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollList = new JScrollPane(availableAccountList);

		JLabel availableLabel = new JLabel("All open Accounts: ");
		leftCenterPanel.add(availableLabel, BorderLayout.NORTH);
		leftCenterPanel.add(scrollList, BorderLayout.CENTER);

		accountNumberText.setForeground(Color.black);

		// centerCenter Panel

		JLabel accountNumberLabel = new JLabel("Account Number ");

		centerCenterPanel.add(accountNumberLabel);
		JPanel accountNumberPanel = new JPanel(new BorderLayout());
		accountNumberPanel.add(accountNumberText, BorderLayout.CENTER);
		// check field

		accountNumberPanel.add(accountNumberCheckLabel, BorderLayout.EAST);
		accountNumberCheckLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		accountNumberCheckLabel.setForeground(Color.red);
		centerCenterPanel.add(accountNumberPanel);

		JLabel accountTypeLabel = new JLabel("Account Type ");
		String[] types = { "", "CD", "Checking", "Saving" };
		accountTypeText = new JComboBox<String>(types);
		UIManager.put("ComboBox.disabledForeground", Color.black);
		centerCenterPanel.add(accountTypeLabel);
		centerCenterPanel.add(accountTypeText);

		JLabel balanceLabel = new JLabel("Balance ");

		centerCenterPanel.add(balanceLabel);

		JPanel balancePanel = new JPanel(new BorderLayout());
		balancePanel.add(balanceText, BorderLayout.CENTER);
		// check field

		balancePanel.add(balanceCheckLabel, BorderLayout.EAST);
		balanceCheckLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		balanceCheckLabel.setForeground(Color.red);
		centerCenterPanel.add(balancePanel);

		// centerCenterPanel.add(balanceText);

		JLabel pinLabel = new JLabel("Pin ");

		pinText.setEchoChar('*');
		centerCenterPanel.add(pinLabel);
		JPanel pinPanel = new JPanel(new BorderLayout());
		pinPanel.add(pinText, BorderLayout.CENTER);
		// check field

		pinPanel.add(pinCheckLabel, BorderLayout.EAST);
		pinCheckLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		pinCheckLabel.setForeground(Color.red);
		centerCenterPanel.add(pinPanel);

		accountNumberCheckLabel.setVisible(false);
		pinCheckLabel.setVisible(false);
		balanceCheckLabel.setVisible(false);

		accountNumberText.setEditable(false);
		balanceText.setEditable(false);
		pinText.setEditable(false);

		accountNumberText.setDisabledTextColor(Color.black);
		accountTypeText.setForeground(Color.black);
		balanceText.setDisabledTextColor(Color.black);
		pinText.setDisabledTextColor(Color.black);

		JLabel withdrawLabel = new JLabel("Withdraw ");

		 centerCenterPanel.add(withdrawLabel);
		 centerCenterPanel.add(withdrawText);
		JLabel depositLabel = new JLabel("Deposit ");

		 centerCenterPanel.add(depositLabel);
		 centerCenterPanel.add(depositText);

		textListeners(accountNumberText, "accountNumberText");
		textListeners(balanceText, "balance");
		textListeners(pinText, "pin");
		textListeners(withdrawText, "withdraw");
		textListeners(depositText, "deposit");

		// rightCenterPanel
		buttonMethod(rightCenterPanel);

		centerPanel.add(leftCenterPanel);
		centerPanel.add(centerCenterPanel);
		centerPanel.add(rightCenterPanel);

		// JList Listener
		availableAccountList
				.addListSelectionListener(new ListSelectionListener()
				{

					@Override
					public void valueChanged(ListSelectionEvent arg0)
					{
						// TODO Auto-generated method stub
						DecimalFormat f = new DecimalFormat("#0.00");
						if (!availableAccountList.getSelectedValue().equals(
								"" + "Create New Account"))
						{
							updateButton.setText("Update");

							accountNumberCheckLabel.setVisible(false);
							pinCheckLabel.setVisible(false);
							balanceCheckLabel.setVisible(false);
							pinText.setText("");

							accountNumberText.setText(accounts.get(
									availableAccountList.getSelectedIndex())
									.getAccountNumber());
							if (accounts
									.get(availableAccountList
											.getSelectedIndex())
									.getAccountType().equals("x"))
								accountTypeText.setSelectedIndex(2);
							else if (accounts
									.get(availableAccountList
											.getSelectedIndex())
									.getAccountType().equals("s"))
								accountTypeText.setSelectedIndex(3);
							else if (accounts
									.get(availableAccountList
											.getSelectedIndex())
									.getAccountType().equals("c"))
								accountTypeText.setSelectedIndex(1);
							accountTypeText.setEnabled(false);
							double balance = accounts.get(
									availableAccountList.getSelectedIndex())
									.getBalance();
							balanceText.setText(f.format(balance).toString());
							if (accounts
									.get(availableAccountList
											.getSelectedIndex())
									.getAccountType().equals("c"))
								withdrawText.setEnabled(false);
							else
								withdrawText.setEnabled(true);
							depositText.setEnabled(true);
							balanceText.setEnabled(false);
							accountNumberText.setEnabled(false);
							pinText.setEnabled(false);

						}
						else
						{
							updateButton.setText("OK");
							balanceText.setEnabled(true);
							accountNumberText.setEnabled(true);
							pinText.setEnabled(true);
							accountTypeText.setEnabled(true);
							accountNumberText.setText("");
							accountTypeText.setSelectedIndex(0);
							balanceText.setText("");
							withdrawText.setText("0.00");
							depositText.setText("0.00");
							withdrawText.setEnabled(false);
							depositText.setEnabled(false);

						}

					}

				});

		accountTypeText.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				if (!balanceText.getText().isEmpty())
				{
					if (accountTypeText.getSelectedItem().equals("Checking")
							&& Double.parseDouble(balanceText.getText()) >= 50)
					{
						balanceCheckLabel.setForeground(Color.green);
					}
					else if (accountTypeText.getSelectedItem().equals("Saving")
							&& Double.parseDouble(balanceText.getText()) >= 200)
					{
						balanceCheckLabel.setForeground(Color.green);
					}
					else if (accountTypeText.getSelectedItem().equals("CD")
							&& Double.parseDouble(balanceText.getText()) >= 500)
					{
						balanceCheckLabel.setForeground(Color.green);
					}
					else
					{
						balanceCheckLabel.setForeground(Color.red);
					}
				}
				else
				{
					balanceCheckLabel.setForeground(Color.red);
				}

			}
		});

		// South
		JPanel southPanel = new JPanel(new FlowLayout());

		southPanel.add(info);

		// Set default JList selection
		availableAccountList.setSelectedIndex(0);

		JLabel titleBar = new JLabel("Samuel Babalola");
		JPanel titlePanel = new JPanel(new FlowLayout());
		titleBar.setFont(new Font("Verdana", Font.BOLD, 30));
		titlePanel.add(titleBar);

		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frameListeners(frame);
		frame.setVisible(true);

	}

	/**
	 * Makes button into the given panel.
	 * 
	 * @param panel
	 */
	void buttonMethod(JPanel panel)
	{
		JButton zeroButton = new JButton("0");
		JButton oneButton = new JButton("1");
		JButton twoButton = new JButton("2");
		JButton threeButton = new JButton("3");
		JButton fourButton = new JButton("4");
		JButton fiveButton = new JButton("5");
		JButton sixButton = new JButton("6");
		JButton sevenButton = new JButton("7");
		JButton eightButton = new JButton("8");
		JButton nineButton = new JButton("9");
		JButton dotButton = new JButton(".");
		JButton backButton = new JButton("<-");
		JButton clearButton = new JButton("Clear");

		JButton atmButton = new JButton("ATM");
		panel.setLayout(new GridLayout(5, 3));

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
		buttonListeners(dotButton);

		removeFromTextbuttonListener(backButton);
		removeFromTextbuttonListener(clearButton);

		panel.add(oneButton);
		panel.add(twoButton);
		panel.add(threeButton);
		panel.add(fourButton);
		panel.add(fiveButton);
		panel.add(sixButton);
		panel.add(sevenButton);
		panel.add(eightButton);
		panel.add(nineButton);
		panel.add(dotButton);
		panel.add(zeroButton);
		panel.add(backButton);
		panel.add(updateButton);
		panel.add(atmButton);
		panel.add(clearButton);

		atmButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				@SuppressWarnings("unused")
				AtmGUI atm = new AtmGUI(new AtmModel(bm));

			}
		});

		updateButton.addActionListener(new ActionListener()
		{

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (updateButton.getText().equals("Update")
						&& !availableAccountList.getSelectedValue().equals(
								"Create New Account"))
				{
					DecimalFormat f = new DecimalFormat("#0.00");
					double withdraw = 0.0;
					double deposit = 0.0;
					String accountNumber = (String) availableAccountList
							.getSelectedValue();
					if (!withdrawText.getText().isEmpty())
					{
						withdraw = Double.parseDouble(withdrawText.getText());
					}
					if (!depositText.getText().isEmpty())
					{
						deposit = Double.parseDouble(depositText.getText());
					}
					bm.withdrawFromAccount(accountNumber, withdraw);
					bm.depositIntoAccount(accountNumber, deposit);
					withdrawText.setText("0.00");
					depositText.setText("0.00");
					balanceText.setText(f.format(
							bm.getAccountInfo(accountNumber).getBalance())
							.toString());
				}
				else
				{
					if (!(accountNumberText.getText().isEmpty()
							|| accountTypeText.getSelectedIndex() == 0 || pinText
							.getText().isEmpty()))
					{

						boolean result = false;
						if (accountTypeText.getSelectedIndex() == 1)
							result = bm.addAccount(accountNumberText.getText(),
									"c", pinText.getText(),
									Double.parseDouble(balanceText.getText()));
						else if (accountTypeText.getSelectedIndex() == 2)
							result = bm.addAccount(accountNumberText.getText(),
									"x", pinText.getText(),
									Double.parseDouble(balanceText.getText()));
						else if (accountTypeText.getSelectedIndex() == 3)
							result = bm.addAccount(accountNumberText.getText(),
									"s", pinText.getText(),
									Double.parseDouble(balanceText.getText()));

						if (result)
						{
							availableModel.add(
									availableModel.getSize() - 1,
									bm.getAccountInfo(
											accountNumberText.getText())
											.getAccountNumber());
							availableAccountList.setSelectedIndex(0);
							pinText.setText("");
							info.setText("Account added Successfully");
						}
						else
						{
							info.setText("Account could not be added, "
									+ "check the information provided");
						}

					}
					else
					{
						String error = "";
						if (accountNumberText.getText().length() < 4)
						{
							error += "Account Number has to be atleast four digits. ";
						}
						if (bm.getAccountInfo(accountNumberText.getText()) != null)
						{
							error += "The Account Number Already Exists. ";
						}

						if (balanceText.getText().isEmpty())
						{
							error += "The Balance is Empty. ";
						}
						else if (accountTypeText.getSelectedIndex() == 2
								&& Double.parseDouble(balanceText.getText()) < 50)
						{

							error += "Check your Balance, minimum is 50. ";
						}
						else if (accountTypeText.getSelectedIndex() == 1
								&& Double.parseDouble(balanceText.getText()) < 500)
						{

							error += "Check your Balance, minimum is 500. ";
						}
						else if (accountTypeText.getSelectedIndex() == 3
								&& Double.parseDouble(balanceText.getText()) < 200)
						{

							error += "Check your Balance, minimum is 200. ";
						}

						if (pinText.getPassword().length != 4)
						{
							error += "Pin Number has to be four digits ";
						}
						info.setText(error);
					}
				}

			}

		});

	}

	/**
	 * Gets the location of the cursor inorder to input text
	 * 
	 * @param text
	 * @param name
	 */

	public void textListeners(final JTextField text, final String name)
	{

		text.addCaretListener(new CaretListener()
		{

			@Override
			public void caretUpdate(CaretEvent arg0)
			{
				if (textInFocus != "")
				{
					if (name.equals("accountNumberText"))
					{

						if (bm.getAccountInfo(accountNumberText.getText()) != null)
						{
							info.setText("The Account Number Already Exists. ");
							accountNumberCheckLabel.setForeground(Color.red);
						}

						else if (accountNumberText.getText().length() >= 4)
						{
							accountNumberCheckLabel.setForeground(Color.green);
							info.setText("Account Number is Acceptable. ");
						}
						else
						{
							accountNumberCheckLabel.setForeground(Color.red);
							info.setText("Account Number has to be atleast four digits. ");
						}

					}

					else if (name.equals("balance"))
					{

						if (balanceText.getText().isEmpty())
						{
							info.setText("The Balance is Empty. ");
						}
						else if (accountTypeText.getSelectedIndex() == 2
								&& Double.parseDouble(balanceText.getText()) < 50)
						{

							info.setText("Check your Balance, minimum is 50. ");
						}
						else if (accountTypeText.getSelectedIndex() == 1
								&& Double.parseDouble(balanceText.getText()) < 500)
						{

							info.setText("Check your Balance, minimum is 500. ");
						}
						else if (accountTypeText.getSelectedIndex() == 3
								&& Double.parseDouble(balanceText.getText()) < 200)
						{

							info.setText("Check your Balance, minimum is 200. ");
						}
						else
						{
							info.setText("Balance is correct");
						}

						if (!balanceText.getText().isEmpty())
						{
							if (accountTypeText.getSelectedItem().equals(
									"Checking")
									&& Double.parseDouble(balanceText.getText()) >= 50)
							{
								balanceCheckLabel.setForeground(Color.green);
							}
							else if (accountTypeText.getSelectedItem().equals(
									"Saving")
									&& Double.parseDouble(balanceText.getText()) >= 200)
							{
								balanceCheckLabel.setForeground(Color.green);
							}
							else if (accountTypeText.getSelectedItem().equals(
									"CD")
									&& Double.parseDouble(balanceText.getText()) >= 500)
							{
								balanceCheckLabel.setForeground(Color.green);
							}
							else
							{
								balanceCheckLabel.setForeground(Color.red);
							}
						}
						else
						{
							balanceCheckLabel.setForeground(Color.red);
						}
					}
					else if (name.equals("pin"))
					{
						if (pinText.getPassword().length == 4)
						{
							pinCheckLabel.setForeground(Color.green);
						}
						else
						{
							pinCheckLabel.setForeground(Color.red);
						}
					}
				}
			}
		});

		text.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent arg0)
			{
				if (text.isEnabled())
				{
					textInFocus = (name);
					if (name.equals("accountNumberText"))
					{
						accountNumberCheckLabel.setVisible(true);
						balanceCheckLabel.setVisible(false);
						pinCheckLabel.setVisible(false);
					}
					else if (name.equals("balance"))
					{
						accountNumberCheckLabel.setVisible(false);
						balanceCheckLabel.setVisible(true);
						pinCheckLabel.setVisible(false);
					}
					else if (name.equals("pin"))
					{
						accountNumberCheckLabel.setVisible(false);
						balanceCheckLabel.setVisible(false);
						pinCheckLabel.setVisible(true);
					}
				}

			}

			@Override
			public void focusLost(FocusEvent arg0)
			{

				// textInFocus.remove(name);
			}

		});
	}

	/**
	 * Back space and Clear Button
	 * 
	 * @param button
	 */
	public void removeFromTextbuttonListener(final JButton button)
	{
		button.addActionListener(new ActionListener()
		{

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				if (textInFocus.equals("accountNumberText")
						&& accountNumberText.isEnabled())
				{
					if (button.getText().equals("<-"))
					{
						try
						{
							accountNumberText.setText(accountNumberText
									.getText(0, accountNumberText.getText()
											.length() - 1));
						}
						catch (BadLocationException e)
						{
						}
					}
					else if (button.getText().equals("Clear"))
					{
						accountNumberText.setText("");
					}

				}

				if (textInFocus.equals("balance") && balanceText.isEnabled())
				{

					if (button.getText().equals("<-"))
					{
						try
						{
							balanceText.setText(balanceText.getText(0,
									balanceText.getText().length() - 1));
						}
						catch (BadLocationException e)
						{
						}
					}
					else if (button.getText().equals("Clear"))
					{
						balanceText.setText("");
					}
				}

				else if (textInFocus.equals("pin") && pinText.isEnabled())
				{
					if (button.getText().equals("<-"))
					{
						try
						{

							pinText.setText(pinText.getText(0, pinText
									.getText().length() - 1));
						}
						catch (BadLocationException e)
						{
						}
					}
					else if (button.getText().equals("Clear"))
					{
						pinText.setText("");
					}
				}

				else if (textInFocus.equals("withdraw")
						&& withdrawText.isEnabled())
				{
					if (button.getText().equals("<-"))
					{
						try
						{
							withdrawText.setText(withdrawText.getText(0,
									withdrawText.getText().length() - 1));
						}
						catch (BadLocationException e)
						{
						}
					}
					else if (button.getText().equals("Clear"))
					{
						withdrawText.setText("");
					}
				}

				else if (textInFocus.equals("deposit")
						&& depositText.isEnabled())
				{
					if (button.getText().equals("<-"))
					{
						try
						{
							depositText.setText(depositText.getText(0,
									depositText.getText().length() - 1));
						}
						catch (BadLocationException e)
						{
						}
					}
					else if (button.getText().equals("Clear"))
					{
						depositText.setText("");
					}
				}
			}

		});
	}

	/**
	 * Button Listener for input numbers
	 * 
	 * @param button
	 */
	public void buttonListeners(final JButton button)
	{

		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				if (textInFocus.equals("accountNumberText")
						&& accountNumberText.isEnabled()
						&& !button.getText().equals("."))
				{
					accountNumberText.setText(accountNumberText.getText()
							+ button.getText());

				}
				else if (textInFocus.contains("balance")
						&& balanceText.isEnabled())
				{
					if (!button.getText().equals("."))
					{
						balanceText.setText(balanceText.getText()
								+ button.getText());
					}
					else if (!balanceText.getText().contains("."))
					{
						if (balanceText.getText().isEmpty())
						{
							balanceText.setText("0" + balanceText.getText()
									+ button.getText());
						}
						else
						{
							balanceText.setText(balanceText.getText()
									+ button.getText());
						}
					}

				}
				else if (textInFocus.contains("pin") && pinText.isEnabled()
						&& !button.getText().equals("."))
				{
					pinText.setText(new String(pinText.getPassword()) + button.getText());

				}
				else if (textInFocus.contains("withdraw")
						&& withdrawText.isEnabled())
				{
					if (!button.getText().equals("."))
					{
						withdrawText.setText(withdrawText.getText()
								+ button.getText());
					}
					else if (!withdrawText.getText().contains("."))
					{
						if (withdrawText.getText().isEmpty())
						{
							withdrawText.setText("0" + withdrawText.getText()
									+ button.getText());
						}
						else
						{
							withdrawText.setText(withdrawText.getText()
									+ button.getText());
						}
					}

				}
				else if (textInFocus.contains("deposit")
						&& depositText.isEnabled())
				{
					if (!button.getText().equals("."))
					{
						depositText.setText(depositText.getText()
								+ button.getText());
					}
					else if (!depositText.getText().contains("."))
					{
						if (depositText.getText().isEmpty())
						{
							depositText.setText("0" + depositText.getText()
									+ button.getText());
						}
						else
						{
							depositText.setText(depositText.getText()
									+ button.getText());
						}
					}

				}

			}

		});
	}

	/**
	 * Notifies the Bank Model that the Bank GUI is being closed.
	 * 
	 * @param frame
	 */
	public void frameListeners(JFrame frame)
	{
		frame.addWindowListener(new WindowListener()
		{

			public void windowClosing(WindowEvent arg0)
			{
				bm.closeGUIAccounts();

			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO Auto-generated method stub

			}

		});

	}
}
