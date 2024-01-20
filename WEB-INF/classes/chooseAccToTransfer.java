import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 		

public class chooseAccToTransfer extends HttpServlet
{
public void doGet(HttpServletRequest request,
						HttpServletResponse response)// this page asks u what account to writhdraw from and what acc to deposit too
			throws IOException,ServletException
	{
        HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect users account obj 
		adderSession.setAttribute("user",userAccount);


        ArrayList<Account>  alist = userAccount.getArrayList();
		
		String name = userAccount.getUsername();

		response.setContentType("text/HTML");

		PrintWriter out = response.getWriter();

		// this page asks u what account to writhdraw from and what account to deposit too

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "Transaction" + "</TITLE>");
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='Transaction'");	
		out.println("<BR><BR><BR><H3>" + " To make a transfer: " + "</H3>");

		out.println("<TABLE>");
		out.println(" <label for='Withdraw'>Choose an account to withdraw from:</label>");
		out.println("<select name='Withdraw' id='Withdraw'>"); 
		
		
		for(int i = 0; i < alist.size(); i++)// get each account a user has and let them choose from a drop down box which acc to WITHDRAW FROM 
		{
		Account U = alist.get(i);
		String type = U.getType();
		int number = U.getNumber();

		out.println("<option value= "+type+number+">"+type+number+"</option>");
        out.println("<BR><BR><BR>");
		}
		out.println("<TABLE>");
		
		out.println("<BR><BR><BR>");

		out.println("<TABLE>");

		out.println(" <label for='Deposit'>Choose an account to deposit too:</label>");
		out.println("<select name='Deposit' id='Deposit'>"); 
		
		for(int i = 0; i < alist.size(); i++) // get each account a user has and let them choose from a drop down box which acc to DEPOSIT TO 
		{
		Account U = alist.get(i);
		String type = U.getType();
		int number = U.getNumber();
		
		out.println("<option value= "+type+number+">"+type+number+"</option>");
        out.println("<BR><BR><BR>");
		}

		out.println("</TABLE>");

		out.println("<BR><BR><BR>");

		out.println("<TABLE>");

		out.println("<TR>");
		out.println("	<TD>The amount (INCLUDE CENTS OR IT'LL FAIL)");
		out.println(
			"	<INPUT TYPE='Text' NAME='Amount'  VALUE=''  SIZE=5></TD>");
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

