/*
 * Account.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: Account.java,v $
 * Revision 1.2 2012-05-17 17:59:34 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.1 2012-05-14 05:44:24 sob8666
 * BatchMode finished, except for final Data
 */

/**
 * An abstract class that has all necessities of the account types
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public abstract class Account
{
	protected String	accountNumber;
	protected String	accountType;
	protected String	pin;
	protected Double	balance;
	protected Double	interest;

	/**
	 * Sets up an Account with its basic informations.
	 * 
	 * @param accountType
	 *            The type of account - "x": Checking "s": Saving "c": CD
	 * @param accountNumber
	 *            The account number
	 * @param pin
	 *            The pin required to login to the account
	 * @param balance
	 *            The current balance of the account
	 */
	public Account(String accountType, String accountNumber, String pin,
			double balance)
	{
		// TODO Auto-generated constructor stub
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.pin = pin;
		this.balance = balance;

		if (accountType.equals("s"))
		{
			this.interest = .005 / 12;
		}
		else if (accountType.equals("c"))
		{
			this.interest = .05 / 12;
		}

	}

	/**
	 * Deposits money into account
	 * 
	 * @param amount
	 */
	void depositIntoAccount(double amount)
	{
		balance += amount;
	}

	/**
	 * Withdraws money from account
	 * 
	 * @param amount
	 */

	void withdrawFromAccount(double amount)
	{
		balance -= amount;
	}

	/**
	 * Applies interest to the accounts, because different accounts have
	 * different
	 * interest, so this method is written in the classes.
	 * 
	 * @return The amount after the interest is applied
	 */
	abstract protected Double applyInterest();

	/**
	 * Gets the Account number
	 * 
	 * @return Account Number
	 */
	String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * Gets the account type
	 * 
	 * @return Account type
	 */
	public String getAccountType()
	{
		return accountType;
	}

	/**
	 * Gets the pin number
	 * 
	 * @return Pin number
	 */
	protected String getPin()
	{
		return pin;
	}

	/**
	 * Gets the Balance
	 * 
	 * @return Balance
	 */
	protected Double getBalance()
	{
		return balance;
	}

	/**
	 * Gets the interest being charged
	 * 
	 * @return Interest
	 */
	protected Double getInterest()
	{
		return interest;
	}
}
