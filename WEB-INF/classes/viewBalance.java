import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;


public class viewBalance extends HttpServlet
{
	public void doGet(HttpServletRequest request,
						HttpServletResponse response)
			throws IOException,ServletException
	{
        
		HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect users account obj 

        adderSession.setAttribute("user",userAccount);

		response.setContentType("text/HTML");
		
        ArrayList<Account>  alist = userAccount.getArrayList(); // get the list of accounts one user would have and PRINT out their balance
		
		String name = userAccount.getUsername();

		//write to log that they logged in
		String log = name + " users.txt file was read from and user had viewed their balance";
		userAccount.writeToLog(log);

		response.setContentType("text/HTML");

		PrintWriter out = response.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "TheBalance" + "</TITLE>");
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='Menu'");	
		out.println("<BR><BR><BR><H3>" + "Hello " + name +" your current balance is:" + "</H3>");

		for(int i = 0; i < alist.size(); i++) 	// iterate throgh list and print out each of the users acc type and the balance for that specific account
		{
		Account U = alist.get(i);
		double currentBalance = U.getBalance();
		String type = U.getType();
		int number = U.getNumber();
		out.println("<BR><BR><BR><H3>" +"Your balance for " + type + number +" is: "+ currentBalance + "</H3>");
        out.println("<BR><BR><BR>");
		}

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