/*
 * BankModel.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: BankModel.java,v $
 * Revision 1.3 2012-05-17 17:59:34 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.2 2012-05-17 03:37:55 sob8666
 * Remains A little fix of batch and ATM GUI
 * 
 * Revision 1.1 2012-05-14 05:44:23 sob8666
 * BatchMode finished, except for final Data
 */
/**
 * Makes a Bank Model with multiple accounts capability
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class BankModel
{

	private static ArrayList<Account>	openAccount			= new ArrayList<Account>();
	private static ArrayList<Account>	closeAccount		= new ArrayList<Account>();
	private String						close				= "";
	private String						initial				= "";
	private String						depositNwithdraw	= "";
	private String						interest			= "";
	private String						bankFile;
	private String						batchFile;
	private DecimalFormat				f					= new DecimalFormat(
																	"#0.00");

	/**
	 * Makes a bank model if batch file is not provided
	 * 
	 * @param bankFile
	 * @param batchMode
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public BankModel(String bankFile) throws NumberFormatException, IOException
	{
		this.bankFile = bankFile;
		BufferedReader inputStream = null;
		try
		{
			inputStream = new BufferedReader(new FileReader(bankFile));
		}
		catch (FileNotFoundException e)
		{
			new FileWriter(bankFile);
			inputStream = new BufferedReader(new FileReader(bankFile));
		}

		String l;

		while ((l = inputStream.readLine()) != null)
		{

			String[] line = l.split(" ");

			if (line[0].equals("o"))
			{
				boolean result = addAccount(line[2], line[1], line[3],
						Double.parseDouble(line[4]));
				if (result)
				{

					if (line[1].equals("x"))
					{
						initial += "Checking   " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";
					}
					else if (line[1].equals("c"))
					{
						initial += "CD         " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";
					}
					else if (line[1].equals("s"))
					{
						initial += "Saving     " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";

					}

				}
			}

		}
		printInitialData();

	}

	/**
	 * Makes Bank Model if batch file is provided.
	 * 
	 * @param bankFile2
	 * @param batchFile
	 * @throws IOException
	 */
	public BankModel(String bankFile, String batchFile) throws IOException
	{
		this.bankFile = bankFile;
		BufferedReader inputStream = null;
		try
		{
			inputStream = new BufferedReader(new FileReader(bankFile));
		}
		catch (FileNotFoundException e)
		{
			new FileWriter(bankFile);
			inputStream = new BufferedReader(new FileReader(bankFile));
		}

		BufferedReader inputStreamBatch = null;
		try
		{
			inputStreamBatch = new BufferedReader(new FileReader(batchFile));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Usage: java Bank bankFile [batchFile]");
			return;
		}

		String l;

		while ((l = inputStream.readLine()) != null)
		{

			String[] line = l.split(" ");

			if (line[0].equals("o"))
			{
				boolean result = addAccount(line[2], line[1], line[3],
						Double.parseDouble(line[4]));
				if (result)
				{
					if (line[1].equals("x"))
					{
						initial += "Checking   " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";
					}
					else if (line[1].equals("c"))
					{
						initial += "CD         " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";
					}
					else if (line[1].equals("s"))
					{
						initial += "Saving     " + line[2] + "   $"
								+ f.format(Double.parseDouble(line[4])) + "\n";

					}

				}
			}

		}

		printInitialData();

		this.batchFile = batchFile;

		String bopen = "";

		String bl;

		while ((bl = inputStreamBatch.readLine()) != null)
		{

			String[] line = bl.split(" ");

			if (line[0].equals("o"))
			{
				boolean result = addAccount(line[2], line[1], line[3],
						Double.parseDouble(line[4]));
				if (!result)
				{

					bopen += line[2] + "  " + line[0] + "  " + line[1]
							+ "  Open: Failed " + "\n";
				}
				else
				{
					bopen += line[2] + "  " + line[0] + "  " + line[1]
							+ "  Open: Success $" + line[4] + "\n";
				}
			}
			else if (line[0].equals("c"))
			{
				closeAccount(line[1], true);

			}
			else if (line[0].equals("d"))
			{
				depositIntoAccount(line[1], Double.parseDouble(line[2]));

			}
			else if (line[0].equals("w"))
			{
				withdrawFromAccount(line[1], Double.parseDouble(line[2]));

			}
			else if (line[0].equals("a"))
			{
				applyInterest();
			}

		}

		printInfo(bopen);

		System.out.println(printFinalData());
	}

	/**
	 * Prints the initial Banking Statements provided in the Bank File
	 * 
	 * @return
	 */
	private void printInitialData()
	{
		// TODO Auto-generated method stub
		System.out.println("========== Initial Bank Data ==================");
		System.out.println("Account Type    Account Balance");
		System.out.println("------------    ------- -----------");
		System.out.println(initial);

	}

	/**
	 * Checks if accountNumber is Valid
	 * 
	 * @param accountNumber
	 * @return
	 */
	public boolean validAccount(String accountNumber)
	{

		Account accountInfo = getAccountInfo(accountNumber);
		if (accountInfo != null)
		{
			return true;
		}

		return false;

	}

	/**
	 * Adds Account to the Bank Model
	 * 
	 * @param accountNumber
	 * @param accountType
	 * @param pin
	 * @param balance
	 * @return
	 */
	public boolean addAccount(String accountNumber, String accountType,
			String pin, double balance)
	{
		Account acc = null;
		if ((accountNumber.matches("\\d+")) && pin.matches("\\d+")
				&& accountNumber.length() >= 4 && pin.length() == 4
				&& !validAccount(accountNumber))
		{
			if (accountType.equals("x"))
			{
				if (balance < 50)
				{
					return false;
				}
				acc = new CheckingAccount(accountNumber, pin,
						balance);

			}
			else if (accountType.equals("s"))
			{
				if (balance < 200)
				{
					return false;
				}
				acc = new SavingAccount(accountNumber, pin,
						balance);
			}
			else if (accountType.equals("c"))
			{
				if (balance < 500)
				{
					return false;
				}
				acc = new CDAccount(accountNumber, pin, balance);
			}
			else
			{
				System.err.println("Wrong Account Type");
			}
			openAccount.add(acc);
			return true;
		}
		return false;

	}

	/**
	 * Closes the Accounts in the GUI, and saves the updated Account information
	 * into the BankFile
	 */
	public void closeGUIAccounts()
	{
		Writer outputStream = null;

		try
		{
			outputStream = new FileWriter(bankFile);

			for (Account i : openAccount)
			{
				DecimalFormat f = new DecimalFormat("#0.00");
				String out = "o " + i.getAccountType() + " "
						+ i.getAccountNumber() + " " + i.getPin() + " "
						+ f.format(i.getBalance());
				outputStream.write(out);
				outputStream.write('\n');
			}
			outputStream.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAccount = openAccount;

		System.out.println(printFinalData());

	}

	/**
	 * Closes the account, for batch Mode
	 * 
	 * @param accountNumber
	 * @return
	 */
	public boolean closeAccount(String accountNumber, boolean batchMode)
	{

		Account accountInfo = getAccountInfo(accountNumber);
		DecimalFormat f = new DecimalFormat("#0.00");
		if (accountInfo != null)
		{
			if (batchMode)
			{
				close += accountInfo.accountNumber + "  c  Closed:Success $"
						+ f.format(accountInfo.getBalance()) + "\n";
				closeAccount.add(accountInfo);
				openAccount.remove(accountInfo);
				return true;
			}
			else
			{

			}

		}
		close += accountNumber + "  c  Closed:Failed" + "\n";
		return false;

	}

	/**
	 * Deposit money into account account Number
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	public boolean depositIntoAccount(String accountNumber, Double amount)
	{
		Account accountInfo = getAccountInfo(accountNumber);
		DecimalFormat f = new DecimalFormat("#0.00");
		if (accountInfo != null)
		{
			depositNwithdraw += accountInfo.getAccountNumber() + "  d  $"
					+ f.format(amount);
			accountInfo.depositIntoAccount(amount);
			depositNwithdraw += "  $" + f.format(accountInfo.getBalance())
					+ "\n";
			return true;
		}
		depositNwithdraw += accountNumber + "  d  $" + f.format(amount)
				+ "  Failed \n";
		return false;
	}

	/**
	 * Withdraws money from the account Number
	 * 
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	public boolean withdrawFromAccount(String accountNumber, double amount)
	{
		Account accountInfo = getAccountInfo(accountNumber);
		DecimalFormat f = new DecimalFormat("#0.00");
		if (accountInfo != null)
		{
			depositNwithdraw += accountInfo.getAccountNumber() + "  w  $"
					+ f.format(amount);
			accountInfo.withdrawFromAccount(amount);
			depositNwithdraw += "  $" + f.format(accountInfo.getBalance())
					+ "\n";
			return true;
		}
		depositNwithdraw += accountNumber + "  w  $" + f.format(amount)
				+ "  Failed \n";
		return false;
	}

	/**
	 * Applies interest to all the account Number
	 */
	public void applyInterest()
	{
		DecimalFormat f = new DecimalFormat("#0.00");
		for (Account i : openAccount)
		{
			Double intInterest = i.applyInterest();
			i.depositIntoAccount(intInterest);
			interest += i.accountNumber + "  $" + f.format(intInterest) + "  $"
					+ f.format(i.getBalance()) + "\n";
		}

	}

	/**
	 * Gets the Account Infomation for the given account Number
	 * 
	 * @param accountNumber
	 * @return
	 */
	public Account getAccountInfo(String accountNumber)
	{
		for (Account i : openAccount)
		{
			if (i.accountNumber.equals(accountNumber))
			{
				return i;
			}
		}
		return null;

	}

	/**
	 * Prints out the the transaction for batch Mode
	 * 
	 * @param open
	 */
	public void printInfo(String open)
	{

		System.out.println("===============================================");
		System.out.println(open.trim());
		System.out.println(depositNwithdraw);

		System.out.println("============== Interest Report ==============");

		System.out.println("Account Adjustment      New Balance");
		System.out.println("------- -----------     -----------");
		System.out.println(interest.trim());

		System.out.println("===============================================");
		System.out.println();
		System.out.println(close);

	}

	/**
	 * Shows all the available accounts
	 * 
	 * @return
	 */
	public ArrayList<Account> getAvailableAccounts()
	{
		return openAccount;
	}

	/**
	 * Prints out the Final bank Data
	 * 
	 * @return
	 */

	public String printFinalData()
	{
		System.out.println("==========   Final Bank Data ==================");
		System.out.println("Account Type      Account Balance");
		System.out.println("------------      ------- -----------");
		DecimalFormat f = new DecimalFormat("#0.00");
		String printfbd = "";
		for (Account i : closeAccount)
		{
			if (i.accountType.equals("x"))
			{
				printfbd += "Checking   " + i.accountNumber + "   $"
						+ f.format(i.getBalance()) + "\n";
			}
			else if (i.accountType.equals("c"))
			{
				printfbd += "CD         " + i.accountNumber + "   $"
						+ f.format(i.getBalance()) + "\n";
			}
			else if (i.accountType.equals("s"))
			{
				printfbd += "Saving     " + i.accountNumber + "   $"
						+ f.format(i.getBalance()) + "\n";

			}
		}
		return printfbd.trim();

	}

}
