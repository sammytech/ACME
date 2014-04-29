/*
 * CDAccount.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: CDAccount.java,v $
 * Revision 1.2 2012-05-17 17:59:34 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.1 2012-05-14 05:44:23 sob8666
 * BatchMode finished, except for final Data
 */

/**
 * Makes a Certificate of Deposit Account
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public class CDAccount extends Account
{
	/**
	 * Makes a Certificate of Deposit Account
	 * 
	 * @param accountType
	 * @param accountNumber
	 * @param pin
	 * @param balance
	 */
	public CDAccount(String accountNumber, String pin,
			double balance)
	{
		super("s", accountNumber, pin, balance);
		
	}

	/*
	 * @Overides
	 * (non-Javadoc)
	 * 
	 * @see Account#withdrawFromAccount(double)
	 */

	void withdrawFromAccount(double amount)
	{
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Account#applyInterest()
	 */
	protected Double applyInterest()
	{
		return getInterest() * balance;

	}
}
