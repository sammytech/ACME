/*
 * SavingAccount.java
 * File:
 * $ID$
 * 
 * Revisions:
 * $Log: SavingAccount.java,v $
 * Revision 1.2 2012-05-17 17:59:33 sob8666
 * All Project Done including comments and readme and feedback
 * 
 * Revision 1.1 2012-05-14 05:44:19 sob8666
 * BatchMode finished, except for final Data
 */

/**
 * Creates a Saving Account
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 * 
 */
public class SavingAccount extends Account
{
	/**
	 * Creates a Saving Account
	 * 
	 * @param accountNumber
	 * @param pin
	 * @param balance
	 */
	public SavingAccount(String accountNumber, String pin,
			double balance)
	{
		
		super("s", accountNumber, pin, balance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Account#applyInterest()
	 */
	protected Double applyInterest()
	{

		if (getBalance() < 200)
		{
			if (getBalance() > 10)
			{
				return (double) 10;
			}
			else
			{
				return getBalance() * .1;
			}
		}
		else
		{
			return (getInterest() * getBalance());
		}

	}

}
