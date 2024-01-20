
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.*;
 

public class Login extends HttpServlet
{
	
	public HashMap readFile(HashMap hash){	// read from file the users objects saved and put that into a hashMap so u can use it to see if user who logged in is in file
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

	 public void init() throws ServletException
	{ 	
			
			super.init();
			try
			{
				File file = new File("users.txt"); 	// get file that will hold all user objects
				if(!file.exists()){		// if the usrs file doesnt exist then make one 
					file.createNewFile();	
			
					FileOutputStream fos = new FileOutputStream("users.txt", false);	
					ObjectOutputStream oos = new ObjectOutputStream(fos);

					HashMap<String, BankUser> h2= new HashMap<>();
					BankUser dummy = new BankUser("dummy");
					h2.put("dummy", dummy);
					oos.writeObject(h2);
					oos.close();
					fos.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();}
	}       

	
	public void doGet(HttpServletRequest request,
						HttpServletResponse response)
			throws IOException,ServletException
	{

		HttpSession adderSession = request.getSession(); // get the session 
		String username = request.getParameter("username"); // gets username from user themseleves when they type it in on the HTML page
		

		//read the already made hash from file into memory, this hash should contain user objects so 
		//you can use that below to see if a user who wants to log in already has an account
		HashMap<String, BankUser> h1= new HashMap<>();
		HashMap<String, BankUser> hash= new HashMap<>();
		hash = readFile(h1);


	/*   DONT CARE ABOUT THIS */ //this gets the username and hashs it to see if its in the hash, then it writes the boolen into a file
		// boolean k = hash.containsKey(username);
		// String s1=String.valueOf(k);  
        // BufferedWriter output = null;
        // try {
        //     File file = new File("example.txt");
        //     output = new BufferedWriter(new FileWriter(file));
        //     output.write(s1);
        // } catch ( IOException e ) {
        //     e.printStackTrace();
        // } finally {
        //   if ( output != null ) {
        //     try {
        //         output.close();
        //     }catch (IOException e){
        //         e.printStackTrace();
        //     }
        //   }
        // }


	if(hash.containsKey(username) == true){  // look at the accounts saved in hash and see if the username gotten from the user is in hash, if so put it in the session
		BankUser userAccount = hash.get(username);
		adderSession.setAttribute("user", userAccount);//  put OBJ of the users account into session so ull send to pther servlets and they can call getAttribute to take it OBJ out and do stuff with it
		sendPage(response, adderSession);	// go to menu

		//write to log that they logged in
		String log = "User.txt file was read from." +username+ " was found and successfully logged in";
		userAccount.writeToLog(log);
	}
		
	else{//	create and add new user to user file 
	
		BankUser newBanker = new BankUser(username);
		adderSession.setAttribute("user", newBanker);//  OBJ of the users account that ull send to pther servlets so they can do stuff with it (llike view balance)
		response.sendRedirect("chooseAccToAdd");// this will take u to add new user to file
	}
}

	private void sendPage(HttpServletResponse reply, HttpSession session)  		// menu page thatll ask you if you want to do certain things like create another account
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
		out.println("<H1><FONT COLOR=Red>" + "The Menu" + "</FONT></H1>");
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

