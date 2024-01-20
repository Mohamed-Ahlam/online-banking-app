import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 
public class Delete extends HttpServlet
{
    HashMap<String, BankUser> hash= new HashMap<>();   

public void writeInFile(HashMap h){
         //write hash back to file after you have updated the hash below

		try{
				FileOutputStream fos = new FileOutputStream("users.txt",false);
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
   
     //read from file into memory 
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
		// String choice = request.getParameter("Option");
		String Delete = request.getParameter("Delete"); // this is the acc to withdraw from
		
        // String type = "";

        // if (choice.equals("Checking"))		
		// 	type = "Checking";

		// if (choice.equals("Savings"))
		// 	type = "Savings";

		// if (choice.equals("Mortgage"))
		// 	type = "Mortgage";


        String username = userAccount.getUsername();
		String history;
		String log;
		int result = userAccount.deleteAccount(Delete);
		if(result == 1){				// failed to do deletion so tell user below that you failed to do it

			response.setContentType("text/HTML");
			PrintWriter out = response.getWriter();

			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Deletion failed</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("<BR><BR><BR>");
			out.println("<CENTER><H1> Deletion failed </H1></CENTER>");
			out.println("</BODY>");
			out.println("</HTML>");
			out.flush();
			
			history = "Deletion failed for " + username; // write to the users history that it failed
			userAccount.addToHistory(history);

			//do a log
			log = username + " did a Unuccessful Deletion and nothing was changed in user.txt file";
			userAccount.writeToLog(log);

			adderSession.setAttribute("user",userAccount);
			sendPage(response, adderSession); // go to menue
		}
		else{							// deletion was successful so tell user that
			response.setContentType("text/HTML");
			PrintWriter out = response.getWriter();

			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Deletion successful</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("<BR><BR><BR>");
			out.println("<CENTER><H1> Deletion successful </H1></CENTER>");
			out.println("</BODY>");
			out.println("</HTML>");
			out.flush();
			
			hash = readFile();      // read hash file of all obj accounts

			hash.put(username, userAccount);      // update the persons account in the hash bc you deleted one of thier accounts
			writeInFile(hash);      // write into file of updated hash

			history = "Account for " + username + " has been deleted. The account was: "+Delete; // write to the users history that it passed
			userAccount.addToHistory(history);

			//do a log
			log = username + " did a Successful Deletion of an account and account was deleted from user.txt file";
			userAccount.writeToLog(log);

			adderSession.setAttribute("user",userAccount);
			sendPage(response, adderSession); // go to menue
		}

	}

	private void sendPage(HttpServletResponse reply, HttpSession session)  
	throws IOException  
		{

	    reply.setContentType("text/HTML");
		PrintWriter out = reply.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "TheMenu" + "</TITLE>");	// the menue
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
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
