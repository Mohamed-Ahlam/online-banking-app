import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;


public class Menu extends HttpServlet
{
	public void doPost(HttpServletRequest request,
						HttpServletResponse response)
			throws IOException,ServletException
	{
		HttpSession adderSession = request.getSession();
        BankUser userAccount =(BankUser) adderSession.getAttribute("user");  // gets the currect usres account obj 
        adderSession.setAttribute("user",userAccount);

		String choice = request.getParameter("Option"); // gets the action the user wants to do with thier account: to view their balance, etc...
		
        if (choice.equals("View"))
			response.sendRedirect("viewBalance"); 

		if (choice.equals("History"))
			response.sendRedirect("showHistory");

		if (choice.equals("Transaction"))
			response.sendRedirect("chooseAccToTransfer");

		if (choice.equals("addAccount"))
			response.sendRedirect("chooseAccToAdd");

		if (choice.equals("Delete"))		
		{
			response.sendRedirect("chooseAccToDelete");
		}
	}
}
