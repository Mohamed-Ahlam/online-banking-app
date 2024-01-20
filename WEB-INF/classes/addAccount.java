import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 
public class addAccount extends HttpServlet
{
    HashMap<String, BankUser> hash= new HashMap<>();   


//read from hash file into memory so you can use it below to update the accounts after a transaction was done
// then write hash back to file

public void writeInFile(HashMap h){
    
		try{
				FileOutputStream fos = new FileOutputStream("users.txt", false);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(h);
				oos.close();
				fos.close();
	
		}catch(IOException ioe)
		{
				ioe.printStackTrace();
		}
    }

public HashMap readFile(){
   
     //read from file into memory and use the hash below to add an account in it
    try
    {
        FileInputStream fis = new FileInputStream("users.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        hash = (HashMap) ois.readObject();
        ois.close();
        fis.close();
    }catch(IOException ioe)
    {
        ioe.printStackTrace();
        return null;
    }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return null;
        }
	return hash;			 

}

    public void doPost(HttpServletRequest request,
						HttpServletResponse response)
			throws IOException,ServletException
	{
	    HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");
		String choice = request.getParameter("Option");

        String type = ""; 

        if (choice.equals("Checking"))		// look at what user choose to use that and make a new account with it
			type = "Checking";

		if (choice.equals("Savings"))
			type = "Savings";

		if (choice.equals("Mortgage"))
			type = "Mortgage";

        String us = userAccount.getUsername();

		// check if they have a number with the accocunt - if no then add it in
		ArrayList<Account>  alist = userAccount.getArrayList(); // get the list of accounts one user would have and PRINT out their balance
		int count = 0;
		for(int i = 0; i < alist.size(); i++) 	// iterate throgh list and print out each of the users acc type and the balance for that specific account
		{
			Account U = alist.get(i);
			String t = U.getType();
			if(type.compareTo(t) == 0){
				count++;
			}
		}

		Account newAcc = new Account(us, type, count);     // makes an account based on that type
        userAccount.addintoList(newAcc);    // adds that account to the user list of accounts

        
        hash = readFile();      // read hash file that holds all accounts
        hash.put(us, userAccount);      // change that persons account or add in a new account if their new
        writeInFile(hash);      // write into file of updated hash  bc you added an account to their account list
		

		//add this to users history
		String history = "New Account was made for " + us + " and the type was: "+type;
		userAccount.addToHistory(history);

		//do a log
		String log = us + " created a new account and account was written into user.txt file";
		userAccount.writeToLog(log);
		adderSession.setAttribute("user",userAccount);// set the session with the users new account obj so others can use it
        sendPage(response, adderSession);

	}

	private void sendPage(HttpServletResponse reply, HttpSession session)  	// tells user account has been made and also gives them menu options
	throws IOException  
		{
		

	    reply.setContentType("text/HTML");
		PrintWriter out = reply.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "TheMenu" + "</TITLE>");
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<H1><FONT COLOR=Red>" + "Your new Account has been added" + "</FONT></H1>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='Menu'");

		out.println("<TABLE>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='View' CHECKED>");

		out.println("	<FONT COLOR=blue>	View your balance.</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Delete'>");
		out.println(
		"	<FONT COLOR=blue>	Delete your account.</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='History'>");
		out.println(
		"	<FONT COLOR=blue>	View history of transactions.</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Transaction'>"); 
		out.println("	<FONT COLOR=blue>	Do a transaction.</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='addAccount'>"); 
		out.println("	<FONT COLOR=blue>	Add another account.</FONT></TD>");
		out.println("</TR>");


		out.println("</TABLE>");

		out.println("<BR><BR><BR>");

		out.println("<INPUT TYPE='Submit' VALUE='Submit'>");

		out.println("</FORM>");
		out.println("</CENTER>");

		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		}
}
