import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 		

public class chooseAccToAdd extends HttpServlet
{
public void doGet(HttpServletRequest request,
						HttpServletResponse response)// this page asks u what new account type does a user want to make: checking, saving, mortgage
			throws IOException,ServletException
	{
        HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect usres account obj and set it to session so other servlets can use it
        adderSession.setAttribute("user",userAccount);
        
// this page asks u what new account type does a user want to make: checking, saving, mortgage
		response.setContentType("text/HTML"); 
        PrintWriter out = response.getWriter();

        out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "addAccount" + "</TITLE>");
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<H1><FONT COLOR=Red>" + "Choose the type of account you want to create" + "</FONT></H1>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='addAccount'");	

		out.println("<TABLE>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Checking' CHECKED>");

		out.println("	<FONT COLOR=blue>	Checking</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Savings'>");
		out.println(
		"	<FONT COLOR=blue>	Savings</FONT></TD>");
		out.println("</TR>");

		out.println("<TR>");
		out.println(
		"	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Mortgage'>");
		out.println(
		"	<FONT COLOR=blue>	Mortgage</FONT></TD>");
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
