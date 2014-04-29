/*
 * AtmModel.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: AtmModel.java,v $
 * Revision 1.3 2012-05-17 17:59:34 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.2 2012-05-17 03:37:55 sob8666
 * Remains A little fix of batch and ATM GUI
 * 
 * Revision 1.1 2012-05-14 05:44:22 sob8666
 * BatchMode finished, except for final Data
 */

import java.util.*;

/**
 * This Class creates a model for the ATM to work on. All the functions that
 * has to do with the performance of the ATM is all in here.
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public class AtmModel
{
	protected static ArrayList<Integer>	uniq			= new ArrayList<Integer>(); // unique
																					// windows
	protected static ArrayList<String>	accountslogged	= new ArrayList<String>();	// accounts
																					// logged
																					// in
	private BankModel					bm;										// the
																					// bank
																					// model
	private String						accountNumber;								// the
																					// account
																					// number
																					// using
																					// this
																					// model

	/**
	 * Stores the bank model when ATM model is created.
	 * 
	 * @param bm
	 *            Bank model with all the accounts
	 */
	public AtmModel(BankModel bm)
	{
		this.bm = bm;
	}

	/**
	 * Deposits money into the Account that is logged into this model
	 * 
	 * @param amount
	 *            amount to deposit
	 */
	public void depositIntoAccount(double amount)
	{
		bm.getAccountInfo(accountNumber).depositIntoAccount(amount);
	}

	/**
	 * Withdraws money into the Account that is logged into this model
	 * 
	 * @param amount
	 *            amount to withdraw
	 */
	public void withdrawFromAccount(double amount)
	{
		bm.getAccountInfo(accountNumber).withdrawFromAccount(amount);
	}

	/**
	 * Checks if the accountNumber provided is valid
	 * 
	 * @param accountNumber
	 *            Number to check
	 * @return true if account Number is valid or vise versa
	 */
	public boolean isaccounNumberValid(String accountNumber)
	{
		this.accountNumber = accountNumber;
		return bm.validAccount(accountNumber);
	}

	/**
	 * checks if the pin provided is valid
	 * 
	 * @param pin
	 *            Number to check
	 * @return true if pin is valid and vise versa
	 */

	public boolean isPinValid(String pin)
	{
		return bm.getAccountInfo(accountNumber).getPin().equals(pin);
	}

	/**
	 * Checks if the number provided is truely unique.
	 * 
	 * @param num
	 *            unique number
	 * @return true if unique and vise versa
	 */
	public boolean isUnique(Integer num)
	{
		return uniq.contains(num);

	}

	/**
	 * Gets the Balance of the account number
	 * 
	 * @return a double value
	 */
	public Double getBalance()
	{
		return bm.getAccountInfo(accountNumber).getBalance();
	}

}
