import java.io.Serializable;
import java.util.*;
import java.io.*;

public class BankUser extends Account{
    String username;    
    ArrayList<Account> accountsList;
    String history;

	public BankUser(String u)  // each user will have a username, a list of all accounts they can have(checking, sav,etc) and a history of all transactions
	{
        username = u;
        accountsList = new ArrayList<Account>();
        history = "";
        }

    public String getUsername(){
		return username;
	}
    
    public ArrayList<Account> getArrayList(){ //return the list of accounts a person can have (c,savings,m)
        return accountsList;
    }
    
    public void addintoList(Account newA){ // add one account to a users list        
        accountsList.add(newA);
    }
	
	public int deleteAccount(String delete){ //example: delete == Checking0 
        int ret = 1;
        for (int i = 0; i < accountsList.size(); i++) {

            String type = accountsList.get(i).getType();        // gett one account from the list 
            int number = accountsList.get(i).getNumber();    
            String n = Integer.toString(number);
            String check = type+n;       // combine the type and Number to get the specific account the user wants

            /*   DONT CARE ABOUT THIS */ 

				// BufferedWriter ou = null;
				// try {
				// 	File file = new File("toto.txt");
				// 	ou = new BufferedWriter(new FileWriter(file));
				// 	ou.write(check);
				// } catch ( IOException e ) {
				// 	e.printStackTrace();
				// } finally {
				// if ( ou != null ) {
				// 	try {
				// 		ou.close();
				// 	}catch (IOException e){
				// 		e.printStackTrace();
				// 	}
				// }
				// }

            if(check.equals(delete)){ // compare that accounts type to delete, if its a match then delete that account and end the loop
                double balance = accountsList.get(i).getBalance();
                ret = 1;
                if(balance == 0){
                    accountsList.remove(accountsList.get(i));
                    ret = 0;
                }
                break;
            }
         } 
         return ret;  
    }
	public int doTransaction(String withAcc, String depAcc, double amount){     // would look like this, Checking1, Savings0, 100.00

        Account accWith = new Account();
        Account accDep= new Account();

        for (int i = 0; i < accountsList.size(); i++) {

            String type = accountsList.get(i).getType();  // get the accounts the user wants to withdraw and depost by comparing the type with string withAcc and String depAcc
            
            int number = accountsList.get(i).getNumber();    
            String n = Integer.toString(number);
            String check = type+n;      // combine the type and Number to get the specific account the user wants

            if(check.equals(withAcc)){
                accWith = accountsList.get(i);
            }
            if(check.equals(depAcc)){
                accDep = accountsList.get(i);   
            }
            
         }

         int result = accWith.withdraw(amount); 
         if(result == 0){        // only enter here if you withdrew successfully 
            accDep.deposit(amount);
         }
         else{
            return 1;}
         return 0; 
    }

    public int listSize(){
        return accountsList.size();
    }
    public void addToHistory(String string){
		history = history + "\n" + string;      // concatinate the previous history transaction to the new one
        history = history + "\n";

        writeInFile(string);        // write that new transaction into the users history file
	}
    public String showHistory(){
        return history;
    }

    public void writeInFile(String history){ // write that new transaction into the users history file
    
        try {
        String filename = username + "History.txt";
        FileWriter writer = new FileWriter(filename, true);

        writer.write(history);
        writer.write(System.getProperty("line.separator"));
        writer.close(); 
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void writeToLog(String log){  // writes to the log file
        try {
        String filename = "Log.txt";
        FileWriter writer = new FileWriter(filename, true);

        writer.write(log);
        writer.write(System.getProperty("line.separator"));
        writer.close(); 
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

}