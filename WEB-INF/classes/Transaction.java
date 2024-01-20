import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 
 // META TAGS ARE DOWN BELOW ****************


public class Transaction extends HttpServlet
{
    HashMap<String, BankUser> hash= new HashMap<>();   

public void writeInFile(HashMap h){
    
		try{
				FileOutputStream fos = new FileOutputStream("users.txt", false); // get the users.txt file and write to it the updated hash
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
   
     //read from hash file into memory so you can use it below to update the accounts after a transaction was done, then write hash back to file
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
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect usres account obj 
		
		String username = userAccount.getUsername();
		String history;
		String log;
		

		String withdraw = request.getParameter("Withdraw"); // this is the acc to withdraw from
		String deposit = request.getParameter("Deposit");// this is the acc to deposit too 
		String am = request.getParameter("Amount");
		double amount = Double.parseDouble(am); // returns double primitive


		int result = userAccount.doTransaction(withdraw, deposit, amount);
		if(result == 1){	// failed to do transaction so tell user below that you failed to do it

			response.setContentType("text/HTML");
			PrintWriter out = response.getWriter();

			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Transaction failed due to insufficient amounts</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("<BR><BR><BR>");
			out.println("<CENTER><H1> Transaction failed due to insufficient amounts </H1></CENTER>");
			out.println("</BODY>");
			out.println("</HTML>");
			out.flush();
			
			history = "Transaction failed for " + username + ". You wanted to transfer "+ amount+" from "+ withdraw + " to "+ deposit;
			userAccount.addToHistory(history);		// write the failed transaction to user history

			//do a log
			log = username + " did a Unsuccessful Transaction and did not write into user.txt file";
			userAccount.writeToLog(log);
			adderSession.setAttribute("user",userAccount);
			sendPage(response, adderSession); // go to menu
		}
		else{			// transaction was successful so tell user that
			response.setContentType("text/HTML");
			PrintWriter out = response.getWriter();

			out.println("<HTML>");

			out.println("<HEAD>");
			out.println("<TITLE>Transaction failed due to insufficient amounts</TITLE>");


			// META TAGS ARE HERE ****************

			// out.println("<META HTTP-EQUIV='Expires' CONTENT='Tue, 02 May 2023 01:00:00 GMT'>"); //the browser should load a new copy from the server, instead of using the copy in its cache.
			out.println("<META HTTP-EQUIV='Expires' CONTENT='0'>"); // If you set it to “0“, the browser will check for a fresh new document on each visit
			out.println("<META HTTP-EQUIV='Cache-Control' content='no-store>'"); //means browsers aren’t allowed to cache a response and must pull it from the server each time it’s requested. 
			// out.println("<META HTTP-EQUIV='Cache-Control' content='no-cache'>"); // meaning a browser can cache, but first submit a validation request to server
			out.println("<META HTTP-EQUIV='Pragma' content='no-cache'>");
			out.println("</HEAD>");


			out.println("<BODY>");
			out.println("<BR><BR><BR>");
			out.println("<CENTER><H1> Transaction was successful </H1></CENTER>");
			out.println("</BODY>");
			out.println("</HTML>");
			out.flush();


			hash = readFile();      // read file of all accounts
			hash.put(username, userAccount);      // change that persons account or add in a new account if their new
			writeInFile(hash);      // write into file of updated hash
			history = "Transaction successful for " + username + ". You transfered "+ amount+" from "+ withdraw + " to "+ deposit;
			userAccount.addToHistory(history);// write the successfull transaction to user history

			//do a log
			log = username + " did a Successful Transaction and change was made to the user in user.txt file";
			userAccount.writeToLog(log);


			adderSession.setAttribute("user",userAccount);

			sendPage(response, adderSession);// go to menu
		}
		

	}

	private void sendPage(HttpServletResponse reply, HttpSession session)  
	throws IOException  
		{

	    reply.setContentType("text/HTML");
		PrintWriter out = reply.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "TheMenu" + "</TITLE>");		// menu
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='Menu'");

		out.println("<TABLE>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='View' CHECKED>");

		out.println("	<FONT COLOR=blue>	View your balance</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Delete'>");
		out.println(
		"	<FONT COLOR=blue>	Delete an account.</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='History'>");
		out.println(
		"	<FONT COLOR=blue>	View history of transactions</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Transaction'>"); 
		out.println("	<FONT COLOR=blue>	Do a transaction</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='addAccount'>"); 
		out.println("	<FONT COLOR=blue>	Add another account</FONT></TD>");
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
