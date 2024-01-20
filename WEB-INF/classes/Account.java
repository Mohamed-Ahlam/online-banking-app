import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account implements Serializable{
	
	String username;
	double balance;
	String type;
	int number;
	public Account(){}
	public Account(String u, String type, int number){
		this.username = u;
		this.balance = 100.00;
		this.type = type;
		this.number = number;  //  each same type of account will be #erd
		}

	public String getUsername(){
		return username;
	}
	public double getBalance(){
		return balance;
	}

	public String getType(){
		return type;
	}
	public int getNumber(){ // checking 1, checking 2, ..., diffrentiates each account 
		return number;
	}
	public void deposit(double amount){	
	    if (amount >= 0){
			BigDecimal am = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP); // rounds double to 2 decimal places
		// convert BigDecimal back to double
      	    double add = am.doubleValue();

			balance += add;
		}
	}

	public int withdraw(double amount){
		BigDecimal am = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP); // rounds double to 2 decimal places
		// convert BigDecimal back to double
      	double delete = am.doubleValue();

		double check = balance - delete;
		if(check >= 0){
			balance = check;
			return 0;
		}	
		 return 1;
	}


}