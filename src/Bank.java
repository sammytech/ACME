import java.io.*;
import java.util.*;

/*
 * Bank.java
 * File:
 *   $ID$
 * 
 * Revisions:
 *   $Log: Bank.java,v $
 *   Revision 1.4  2012-05-17 17:59:34  sob8666
 *   All Project Done including comments and readme and feedback
 *
 *   Revision 1.3  2012-05-17 03:37:56  sob8666
 *   Remains A little fix of batch and ATM GUI
 *
 *   Revision 1.2  2012-05-14 05:46:26  sob8666
 *   BatchMode finished, except for final Data
 *
 *   Revision 1.1  2012-05-14 05:44:23  sob8666
 *   BatchMode finished, except for final Data
 *
 *
 */
/**
 * 
 * @author Samuel Babalola (sob8666@rit.edu)
 *
 */

public class Bank {

	/**
	 * The beginning of the program
	 * 
	 * @param args 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(args.length == 0 || args.length > 2)
		{
			System.err.println("Usage: java Bank bankFile [batchFile]");
			return;
		}
		else{
			String bankFile = args[0];
			String batchFile = null;
			
			if (args.length == 2)
			{
				batchFile = args[1];	
			}
			
			if ( batchFile == null )
			{
				BankModel bm = new BankModel(bankFile);
				BankGUI bank = new BankGUI(bm);
			}
			else
			{
				BankModel bm = new BankModel( bankFile, batchFile);
			}
		}
	}
		
		



	
	

}
