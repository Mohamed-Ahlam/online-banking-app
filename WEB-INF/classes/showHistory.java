import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 
public class showHistory extends HttpServlet
{
    HashMap<String, BankUser> hash= new HashMap<>();   


    public void doGet(HttpServletRequest request,
						HttpServletResponse response)
			throws IOException,ServletException
	{
	    HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect usres account obj 
		String choice = request.getParameter("Option");

		String name = userAccount.getUsername();
		String log = name + " user history file was read from and user had viewed their history";
		userAccount.writeToLog(log);

        String history = userAccount.showHistory(); // get the string of all the stuff a user has done
        
        response.setContentType("text/HTML");

		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Simple Servlet</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<BR><BR><BR>");
		out.println("<CENTER><H1> This is a view of your Account history </H1></CENTER>");

        Scanner scanner = new Scanner(history);  // print out the history string line by line so itll show on page
        while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        // process the line
        out.println("<CENTER><H3 "+ line+ ">"+line+"</H3></CENTER>");
        }
        scanner.close();
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();

	   adderSession.setAttribute("user",userAccount);
       sendPage(response, adderSession);  // go to menu
	}

	private void sendPage(HttpServletResponse reply, HttpSession session)  
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

