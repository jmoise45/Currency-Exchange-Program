import java.util.Scanner; //Imports Scanner
import java.lang.Math; //Imports Math library's functionality
public class CurrencyExchange {
	
	private static double balance = 0;

	/**
	 * Get the current balance of the account
	 */
	public static double getBalance() {
		return balance;
	}

	/**
	 * Update the balance of current account, will do a roundup to 2 significant
	 * digits
	 *
	 * @return if update succeed, will return true. If failed, return false
	 */
	private static boolean updateBalance(double newBalance) {

		balance = Math.round(newBalance * 100) / 100.0;
		if (balance >= 0) {
			return true;
		} else
			return false;
	}
	
	public static void main(String[] args) {
		
		//Use Scanner class to create an object to read input from System.in
        Scanner input = new Scanner(System.in);
        
        //Print welcome message
        System.out.println("Welcome to Currency Exchange 2.0");
        System.out.println();
        
        //Print the conversion table
        printConversionTable();
        
        //Create a control variable for a do-while loop
        int count = 0;
        
        //Make a do-while loop for the main menu and its options
        do {
	        	
	        //Create an integer variable that holds the value chosen by the user in the main menu, and print the main menu
	        int choice = mainMenuOptionSelector(input);
	        
	        //If choice is equal to 1, the current balance in the account will be printed out
			if (choice == 1) {
			   System.out.println("Your current balance is: " + getBalance());
			   System.out.println();
				}
	        //If the user chooses option 2 in the main menu
			else if (choice == 2) {
	        	
	            /* Create an integer variable that holds the value chosen by the
			    user in the currency menu, and print the currency menu*/
	        	int choice1 = currencyMenuOptionSelector(input);
	        	
	        	/*Make a String variable that will take in the name of a given currency type value using
	        	the nameOfCurrency method*/
	        	String currencyName = nameOfCurrency(choice1);
	        	
	        	//Ask user to enter the amount of currency being deposited
	        	System.out.println("Please enter the deposit amount:");
	        	
	        	//Make a double variable that takes in the input for the deposit amount
	        	double depositAmount = input.nextDouble();
	        	
	        	//If the deposit method returns true, then the "You successfully deposited..." string will print
	        	if (deposit(depositAmount, choice1)) {
	        		
	        		/*Make a string variable that takes the return value of the logTransaction method when 
	        		 isDeposit is true and print it*/
	        		String result = logTransaction(depositAmount, choice1, true) + currencyName;
	        		System.out.println(result);
	        		System.out.println();
	        	}
	        	
	        	//If the deposit method returns false, the "Logging Error" string will print
	        	else {
	        		
	        		/*Make a string variable that takes the return value of the logTransaction method when 
	        		 isDeposit is false and print it*/
	        		String result1 = logTransaction(depositAmount, choice1, false);
	        		System.out.println(result1);
	        		System.out.println();
	        	}
	        }
			
			//If the user chooses option 3 in the main menu
			else if (choice == 3) {
	        	
	        	/* Create an integer variable that holds the value chosen by the
			    user in the currency menu, and print the currency menu*/
	        	int choice2 = currencyMenuOptionSelector(input);
	        	
	        	/*Make a String variable that will take in the name of a given currency type value using
	        	the nameOfCurrency method*/
	        	String currencyName1 = nameOfCurrency(choice2);
	        	
	        	//Ask user to enter the amount of currency being withdrawn
	        	System.out.println("Please enter the withdraw amount:");
	        	
	        	//Make a double variable that takes in the input for the withdraw amount
	        	double withdrawAmount = input.nextDouble();
	        	
	        	//If the withdraw method returns true, then the "You successfully withdrew..." string will print
	        	if (withdraw(withdrawAmount, choice2)) {
	        		
	        		balance = balance + convertCurrency(withdrawAmount,choice2, true);
	        		/*Make a string variable that takes the return value of the logTransaction method when 
	       		    isDeposit is false and print it*/
	        		String result = logTransaction(withdrawAmount, choice2, false) + currencyName1;
	        		System.out.println(result);
	        		System.out.println();
	        	}
	        	
	        	/*If the withdraw method returns false and the withdrawAmount value is greater than balance, 
	        	then "Error: Insufficient funds." and "Logging error" will print. If the withdraw method just
	        	returns false, then the "Logging error" will only be printed.*/
	        	else {
	        		if (withdrawAmount > balance) {
	        			System.out.println("Error: Insufficient funds.");
	        			String result1 = logTransaction(withdrawAmount, choice2, false);
	        			System.out.println(result1);
	        			System.out.println();
	        		}
	        		else {
	        			String result2 = logTransaction(withdrawAmount, choice2, false);
	        			System.out.println(result2);
	        			System.out.println();
	        		}
	        		
	        	}
	        }
			//If the user chooses option 4 in the main menu
	        else if (choice == 4) {
	        	
	        	//If the balance is greater than 0
	        	if (balance > 0) {
	        		
	        		/*The two parameter values (0 and -1) help avoid the other possible if/else statements
	        		in the logTransaction method*/
	        		String result = logTransaction(0,-1,false);
	        		System.out.println(result);
	        		System.out.println("Goodbye");
	        		count++; //causes count to equal 1 and end the loop
	        	}
	        	else {
	        		
	        		/*The two parameter values (0 and -1) help avoid the other possible if/else statements
	        		in the logTransaction method*/
	        		String result1 = logTransaction(0,-1,false);
	        		System.out.println(result1);
	        		System.out.println("Goodbye");
	        		count++; //causes count to equal 1 and end the loop
	        	}
	        }
        }while(count == 0);
    }


    /**
     * deposit the amount of a specific currency to the account
     *
     * @param amount       the amount to be deposited.
     * @param currencyType the currency type will be the same as the type number used
     *                     in the convertCurrency(double, int, boolean) method. An Invalid type will result in a
     *                     deposit failure.
     * @return if deposit succeed, will return true. If failed, return false
     */
    public static boolean deposit(double amount, int currencyType) {
        
    	//If the amount is greater than 0, and the currencyType value is a number from 1 to 11
    	if (amount > 0 && currencyType >= 1 && currencyType <= 11) {
    		
    		//If the currency is foreign, it will be converted to USD, and then added to balance
    		if (currencyType > 1) {
    		
    			balance = balance + convertCurrency(amount, currencyType, true);
    			updateBalance(balance);
    		}
    		//If the currency is USD, then it will just be added to the balance
    		else if (currencyType == 1) {
    			balance = balance + amount;
    			updateBalance(balance);
    		}
    		
    		//Deposit succeeded
    		return true;
    	}
    	
    	//Deposit failed
    	else
    		return false;
    }

    /**
     * withdraw the value amount with a specific currency from the account. The withdraw amount should never exceed the current account balance, or the withdrawal will fail. If the currency is other than USD, a 0.5% convenience fee will be charged
     *
     * @param amount       the amount to be withdrawn.
     * @param currencyType the currency type will be the same as the type number used
     *                     in the convertCurrency(double, int, boolean) method. An invalid
     * 		         type will result a withdraw failure.
     * @return if withdraw succeed, will return true. If failed, return false
     */
    public static boolean withdraw(double amount, int currencyType) {
        
    	/*If the amount is greater than 0, the currencyType value is a number from 1 to 11, and the 
    	amount value is less than or equal to the balance value*/
    	if (amount > 0 && currencyType >= 1 && currencyType <= 11 && amount <= balance) {
    		
    		//If the currency is USD, then it will just be subtracted from the balance
    		if (currencyType == 1) {
    			balance = balance - amount;
    			updateBalance(balance);
    		}
    		
    		/*If the currency is foreign, it will be converted to USD, and then subtracted from 
    		the balance with a convenience fee*/
    		else if (currencyType > 1) {
    			balance = balance - (convertCurrency(amount, currencyType, true) * (1.005));
    			updateBalance(balance);
    		}
    		
    		//Withdraw succeeded
    		return true;
    	}
    	
    	else
    		//Withdraw failed
    		return false;
    }
    /**
     * Convert the value amount between U.S. dollars and a specific currency.
     *
     * @param amount         The amount of the currency to be converted.
     * @param currencyType   The integer currencyType works as follows:
     *                       1 for usd (U.S. Dollars)
     *                       2 for eur (Euros)
     *                       3 for bri (British Pounds)
     *                       4 for inr (Indian Rupees)
     *                       5 for aus (Australian Dollars)
     *                       6 for cnd (Canadian Dollars)
     *                       7 for sid (Singapore Dollars)
     *                       8 for swf (Swiss Francs)
     *                       9 for mar (Malaysian Ringgits)
     *                       10 for jpy (Japanese Yen)
     *                       11 for cyr (Chinese Yuan Renminbi)
     * @param isConvertToUSD Tells the direction of the conversion. If the value is true, then the conversion is from a
     *                       foreign currency to USD. If the value is false, then the conversion is from USD to the
     *                       foreign currency
     * @return the converted amount
     */
    public static double convertCurrency(double amount, int currencyType, boolean isConvertToUSD) {
        
    	//If isConvertToUSD is true, then the conversion is from a foreign currency to USD
		if (isConvertToUSD == true) {
			switch (currencyType) {

			case 2:
				amount = (amount / 0.89);
				break;
			case 3:
				amount = (amount / 0.78);
				break;
			case 4:
				amount = (amount / 66.53);
				break;
			case 5:
				amount = (amount / 1.31);
				break;
			case 6:
				amount = (amount / 1.31);
				break;
			case 7:
				amount = (amount / 1.37);
				break;
			case 8:
				amount = (amount / 0.97);
				break;
			case 9:
				amount = (amount / 4.12);
				break;
			case 10:
				amount = (amount / 101.64);
				break;
			case 11:
				amount = (amount / 6.67);
			}
		}
        
		//If isConvertToUSD is false, then the conversion is from USD to the foreign currency
		else {
            
			/*Since there is no case 1 in the switch statement, the amount will remain the 
			same when the currencyType value is 1*/
			switch (currencyType) {

			case 2:
				amount = (amount * 0.89);
				break;
			case 3:
				amount = (amount * 0.78);
				break;
			case 4:
				amount = (amount * 66.53);
				break;
			case 5:
				amount = (amount * 1.31);
				break;
			case 6:
				amount = (amount * 1.31);
				break;
			case 7:
				amount = (amount * 1.37);
				break;
			case 8:
				amount = (amount * 0.97);
				break;
			case 9:
				amount = (amount * 4.12);
				break;
			case 10:
				amount = (amount * 101.64);
				break;
			case 11:
				amount = (amount * 6.67);

			}
		}
		
		return amount;
    }
    	

    /**
     * Displays message at the end of the withdraw, deposit, and endTransaction stating
     * how much the user just withdrew/deposited and what type (this will be used in both features B, C and D of the
     * main menu).
     *
     * @param amount       the amount of currency withdrew/deposited
     * @param currencyType the currency type will be the same as the type number used
     *                     in {@link #convertCurrency(double, int, boolean)}
     * @param isDeposit    if true log the deposit transaction, false log the withdraw transaction
     * @return Return the formatted log message as following examples:
     * You successfully withdrew 10.0 U.S. Dollars
     * You successfully deposited 30.0 Japanese Yen
     * <p>
     * The invalid input like invalid currencyType or negative amount,
     * will return a “Logging Error”
     */
    private static String logTransaction(double amount, int currencyType, boolean isDeposit) {
        
    	//Make a string variable that holds the "You successfully deposited..." string for the deposit method
    	String depositStatement = "You successfully deposited " + amount + " ";
    	
    	//Make a string variable that holds the "You successfully withdrew..." string for the withdraw method
    	String withdrawStatement = "You successfully withdrew " + amount + " ";
    	
    	//Make a string variable that holds the "Logging Error" string for invalid amount input
    	String error = "Logging Error";
    	
    	
    	/*Make a string variable that holds the string for the "End your session" main menu option when the
    	value of balance is greater than 0*/
    	String endSession = "You sucessfully withdrew " + balance + " U.S. Dollars";
    	
    	/*Make a string variable that holds the string for the "End your session" main menu option when the 
    	balance value is equal to 0*/
    	String endSession1 = "Your remaining balance is 0.0 U.S. Dollars";
    	
		// If isDeposit has value true, then the depositStatement variable will
		// be returned
		if (isDeposit == true) {
			return depositStatement;
		}
		/*
		 * if isDeposit is equal to false and the withdraw method returns true, then the string variable
		 * withdrawStatement will be returned
		 */
		else if ((isDeposit == false) && (withdraw(amount, currencyType))) {
			return withdrawStatement;
		} 
		/*
		 *  If isDeposit is equal to false, currencyType does not equal -1, and the withdraw method returns
		 *  false, then the string variable error will be returned
		 */
		else if ((isDeposit == false) && (currencyType != -1) && (withdraw(amount, currencyType) == false)) {
			return error;
		}
		/*
		 * If isDeposit has value of false and currencyType is equal to -1, then the variable endSession
		 * will be returned
		 */
		else if ((isDeposit == false) && (endTransaction(balance) == true) && currencyType == -1) {
			if (balance > 0) {
				return endSession;
			}
		}
		/*
		 * This return statement assumes that balance is equal to 0
		 */
		return endSession1;
    }
	
    	

    /**
     * Prints the currency menu (see output examples), allows the user to make a selection from available currencies
     *
     * @param input the Scanner object you created at the beginning of the main method. Any value other than the
     *              11 valid valid currency types should generate an invalid value prompt. Print the list again
     *              and prompt user to select a valid value from the list. the currency type will be the same as
     *              the type number used in {@link #convertCurrency(double, int, boolean)}
     * @return an integer from 1-11 inclusive representing the user’s selection.
     */
    private static int currencyMenuOptionSelector(Scanner input) {
    	
    	//Make a variable that takes in user input
    	int choice = 0;
    	
    	//Make control variable for do-while loop
    	int count = 0;
    	
    	//Make a do-while loop to repeat the menu when user gives invalid input
    	do {
	    	
	    	//Print the currency menu
	    	System.out.println("Please select the currency type:");
	    	System.out.println("1. U.S. Dollars");
	    	System.out.println("2. Euros");
	    	System.out.println("3. British Pounds");
	    	System.out.println("4. Indian Rupees");
	    	System.out.println("5. Australian Dollars");
	    	System.out.println("6. Canadian Dollars");
	    	System.out.println("7. Singapore Dollars");
	    	System.out.println("8. Swiss Francs");
	    	System.out.println("9. Malaysian Ringgits");
	    	System.out.println("10. Japanese Yen");
	    	System.out.println("11. Chinese Yuan Renminbi");
	    	
	        //Takes in user input for the option choice
	    	choice = input.nextInt();
	    	
	    	//if choice is a number from 1-11, the do-while loop is terminated
	    	if (choice >= 1 && choice <= 11)
	    		count++; //ends the loop
	    	
	    	// if choice is not a number from 1-4, the menu is repeated until the user gives a valid input
	    	else {
	    		System.out.println("Input failed validation. Please try again.");
	    		System.out.println();
	    		continue;
	    	}
	    	
        }while(count == 0); //when count is equal to 1, the do-while loop will end
    	
    	return choice;
    	
    	
    }

    /**
     * Prints the main menu (see output examples), allows the user to make a selection from available operations
     *
     * @param input the Scanner object you created at the beginning of the main method. Any value other than the 4
     *              valid selections should generate an invalid value prompt. Print the list again and prompt user to
     *              select a valid value from the list.
     * @return an integer from 1-4 inclusive representing the user’s selection.
     */
    private static int mainMenuOptionSelector(Scanner input) {
    	
    	//Make a variable that takes in user input
    	int choice = 0;
    	
    	//Make control variable for do-while loop
    	int count = 0;
    	
    	//Make a do-while loop to repeat the menu when user gives invalid input
    	do {
    	//Print the main menu
    	System.out.println("Please select an option from the list below:");
    	System.out.println("1. Check the balance of your account");
    	System.out.println("2. Make a deposit");
    	System.out.println("3. Withdraw an amount in a specific currency");
    	System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
    	
    	//Takes in user input for option choice
    	 choice = input.nextInt();
    	
    	//if choice is a number from 1-4, the do-while loop is terminated
    	if (choice >= 1 && choice <= 4)
    		count++; //ends the loop
    	
    	// if choice is not a number from 1-4, the menu is repeated until the user gives a valid input
    	else {
    		System.out.println("Input failed validation. Please try again.");
    		System.out.println();
    		continue;
    	}
        }while(count == 0); //when count is equal to 1, the do-while loop will end
    	
    	return choice;
    }

    /**
     * Prints the conversion table at the start of the program (see the output examples).
     */
    private static void printConversionTable() {
    	System.out.println("Current rates are as follows:");
    	System.out.println();
    	System.out.println("1 - U.S. Dollar - 1.00");
    	System.out.println("2 - Euro - 0.89");
    	System.out.println("3 - British Pound - 0.78");
    	System.out.println("4 - Indian Rupee - 66.53");
    	System.out.println("5 - Australian Dollar - 1.31");
    	System.out.println("6 - Canadian Dollar - 1.31");
    	System.out.println("7 - Singapore Dollar - 1.37");
    	System.out.println("8 - Swiss Franc - 0.97");
    	System.out.println("9 - Malaysian Ringgit - 4.12");
    	System.out.println("10 - Japanese Yen - 101.64");
    	System.out.println("11 - Chinese Yuan Renminbi - 6.67");
    	System.out.println();
    }
    
    //Make a method that can determine the name of a currency type value
    private static String nameOfCurrency(int choice) {
    	
    	//Make a String variable that will take in the name of the type of currency being deposited
    	String name = "";
    	
    	/*Make a switch statement to determine the name of the currency based on 
    	the value of the integer variable choice*/
    	switch (choice) {
    	
    	case 1: name = "U.S. Dollars";
    	        break;
    	case 2: name = "Euros";
    	        break;
    	case 3: name = "British Pounds";
                break;
    	case 4: name = "Indian Rupees";
    	        break;
    	case 5: name = "Australian Dollars";
    	        break;
    	case 6: name = "Canadian Dollars";
    	        break;
    	case 7: name = "Singapore Dollars";
    	        break;
    	case 8: name = "Swiss Francs";
    	        break;
    	case 9: name = "Malaysian Ringgits";
    	        break;
    	case 10: name = "Japanese Yen";
    	        break;
    	case 11: name = "Chinese Yuan Renminbi";
    	
    	}
    	return name;
    }
    //Create a method for determining the print statement for the "End your session" choice on main menu
    private static boolean endTransaction(double newBalance) {
    	
    	//If the balance is greater than 0, return true
    	if (balance > 0){
    		return true;
    	}
    	
    	//This assumes that the balance is equal to 0 
    	else
    		return false;
    	}


}
