package servlets;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import beans.AccountPojo;
import beans.UserPojo;

import user.UserUtility;
import utils.Crypto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	private static Logger log=Logger.getLogger(LoginServlet.class.getName());
	
	UserUtility util = new UserUtility();
       
    public UserUtility getUtil() {
		return util;
	}

	public void setUtil(UserUtility util) {
		this.util = util;
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		log.info("Entering LoginServlet : Method doPost");
		String formType=request.getParameter("formtype");
		if(formType!=null && formType.length()>0)
			if("login".equalsIgnoreCase(formType))
				processLoginForm(request, response);
			else
				processSignUpForm(request, response);
		log.info("Exiting LoginServlet : Method doPost");
	}
	
	public void processLoginForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		log.info("Entering LoginServlet : Method processLoginForm");
		String returnUrl =null;
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String encryptedPassowrd = Crypto.encode(password);
		
		boolean userAuthenticated = getUtil().validateUser(email, encryptedPassowrd);
		
		if(userAuthenticated){
			HttpSession session = request.getSession();
            session.setAttribute("email", email);
            String name = getUtil().fetchUsername(email);
            session.setAttribute("name", name);
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
			returnUrl="/barter/index.jsp";
			response.sendRedirect(returnUrl);
		}else{
			returnUrl="/pages/login/login.jsp";
			RequestDispatcher view = request.getRequestDispatcher(returnUrl);
			request.setAttribute("message","Authentication Failed");
	        view.forward(request, response);
		}
		

		
	}
	
	public void processSignUpForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String returnUrl =null;
		
		try{
			boolean accountUpdate = false; 
			String firstname=request.getParameter("firstname");
			String lastname=request.getParameter("lastname");
			String email=request.getParameter("email");
			String phone=request.getParameter("phone");
			String dob=request.getParameter("dob");
			String passwd=request.getParameter("passwd");
			String confirmpassword=request.getParameter("confirmpassword");
			String streetName = request.getParameter("address1");
			String aptDetails = request.getParameter("address2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zipcode");
			
			log.info("F Name :: "+firstname+" L Name ::"+lastname+" Email :: "+email+" Phone ::"+phone+" dob :: "+dob+" Password :: "+passwd);
			log.info("Confirm Password :: "+confirmpassword+" Street Name :: "+streetName+" Apt Details :: "+aptDetails+" City ::"+city);
			log.info("State :: "+state+" Zip Code :: "+zipcode);
			
			UserPojo user = new UserPojo();
			user.setFirstName(firstname);
			user.setLastName(lastname);
			SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date d = formatter.parse(dob);
			user.setDob(d);
			user.setPhone(phone);
			user.setEmailId(email);
			user.setAddress1(streetName);
			user.setAddress2(aptDetails);
			user.setCity(city);
			user.setState(state);
			int zip = Integer.parseInt(zipcode);
			user.setZipcode(zip);
			int userId = getUtil().addNewUser(user);
			if(userId>0){
				user.setUserId(userId);
				AccountPojo accountObj = new AccountPojo();
				accountObj.setUserId(user.getUserId());
				accountObj.setEmailId(user.getEmailId());
				String encryptedPassowrd = Crypto.encode(passwd);
				accountObj.setPassword(encryptedPassowrd);
				accountObj.setAccountBlocked("false");
				accountObj.setLoginAttempt(0);
				accountObj.setUnblockTime(null);
				accountUpdate=getUtil().addNewUserDetailsToAccount(accountObj);
			}
			if(accountUpdate){
				HttpSession session = request.getSession();
	            session.setAttribute("email", email);
	            session.setAttribute("name", firstname);
	            //setting session to expiry in 30 mins
	            session.setMaxInactiveInterval(30*60);
				returnUrl="/barter/index.jsp";
				response.sendRedirect(returnUrl);
			}else{
				returnUrl="/pages/login/login.jsp";
				request.setAttribute("message","Account Creation Failed. Kindly try again.");
				RequestDispatcher view = request.getRequestDispatcher(returnUrl);
		        view.forward(request, response);
			}
			

	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
}
