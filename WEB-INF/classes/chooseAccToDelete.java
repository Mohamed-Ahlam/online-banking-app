import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 		

public class chooseAccToDelete extends HttpServlet
{
public void doGet(HttpServletRequest request,
						HttpServletResponse response)// this page asks u what account type does a user want to delete: checking, saving, mortgage
			throws IOException,ServletException
	{
        HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect usres account obj 
        adderSession.setAttribute("user",userAccount);


		ArrayList<Account>  alist = userAccount.getArrayList(); // get the list of accounts one user would have and PRINT out their balance
		

        response.setContentType("text/HTML"); 
        PrintWriter out = response.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + "Delete" + "</TITLE>");
		out.println("</HEAD>");

		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<BR><BR><BR>");

		out.println("<FORM METHOD=POST ACTION='Delete'");	
		out.println("<BR><BR><BR><H3>" + "Choose an account to Delete" + "</H3>");

		out.println("<TABLE>");
		out.println(" <label for='Delete'>Choose an account to Delete:</label>");
		out.println("<select name='Delete' id='Delete'>"); 
		
		
		for(int i = 0; i < alist.size(); i++)// get each account a user has and let them choose from a drop down box which acc to Delete  
		{
		Account U = alist.get(i);
		String type = U.getType();
		int number = U.getNumber();
	
		out.println("<option value= "+type+number+">"+type+number+"</option>");
        out.println("<BR><BR><BR>");
		}
		out.println("<TABLE>");

		out.println("<BR><BR><BR>");

		out.println("<INPUT TYPE='Submit' VALUE='Submit'>");

		out.println("</FORM>");
		out.println("</CENTER>");

		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();

			/*   DONT CARE ABOUT THIS */ 
				//     BufferedWriter output = null;
				//     try{
				// 			FileOutputStream fos = new FileOutputStream("example.txt",true);
				// 			ObjectOutputStream oos = new ObjectOutputStream(fos);
				// 			oos.writeObject(userAccount);
				// 			oos.close();
				// 			fos.close();
				// //		}
						
				// 	}catch(IOException ioe)
				// 	{
				// 			ioe.printStackTrace();
				// 	}

        // response.setContentType("text/HTML"); 
        // PrintWriter out = response.getWriter();

        // out.println("<HTML>");
		// out.println("<HEAD>");
		// out.println("<TITLE>" + "Delete" + "</TITLE>"); // what account does a user want to delete: checking, saving, mortgage
		// out.println("</HEAD>");

		// out.println("<BODY>");

		// out.println("<CENTER>");
		// out.println("<H1><FONT COLOR=Red>" + "Choose the account you want to delete" + "</FONT></H1>");
		// out.println("<BR><BR><BR>");

		// out.println("<FORM METHOD=POST ACTION='Delete'");	

		// out.println("<TABLE>");

		// out.println("<TR>");
		// out.println(
		// "	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Checking' CHECKED>");

		// out.println("	<FONT COLOR=blue>	Checking</FONT></TD>");
		// out.println("</TR>");

		// out.println("<TR>");
		// out.println(
		// "	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Savings'>");
		// out.println(
		// "	<FONT COLOR=blue>	Savings</FONT></TD>");
		// out.println("</TR>");

		// out.println("<TR>");
		// out.println(
		// "	<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Mortgage'>");
		// out.println(
		// "	<FONT COLOR=blue>	Mortgage</FONT></TD>");
		// out.println("</TR>");

		// out.println("</TABLE>");

		// out.println("<BR><BR><BR>");

		// out.println("<INPUT TYPE='Submit' VALUE='Submit'>");

		// out.println("</FORM>");
		// out.println("</CENTER>");

		// out.println("</BODY>");
		// out.println("</HTML>");
		// out.flush();
		}
	}

