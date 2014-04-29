/*
 * CheckingAccount.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: CheckingAccount.java,v $
 * Revision 1.2 2012-05-17 17:59:33 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.1 2012-05-14 05:44:18 sob8666
 * BatchMode finished, except for final Data
 */

/**
 * Creates a checking account
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public class CheckingAccount extends Account
{
	/**
	 * Creates a checking account
	 * 
	 * @param accountType
	 * @param accountNumber
	 * @param pin
	 * @param balance
	 */
	public CheckingAccount(String accountNumber,
			String pin, double balance)
	{
		super("x", accountNumber, pin, balance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Account#applyInterest()
	 */
	protected Double applyInterest()
	{
		if (getBalance() < 50)
		{
			if (getBalance() > 5)
			{
				return (double) 5;
			}
			else
			{
				return getBalance() * .1;
			}
		}
		return (double) 0;

	}

}
