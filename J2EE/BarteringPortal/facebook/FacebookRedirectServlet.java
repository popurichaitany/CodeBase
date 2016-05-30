package facebook;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;



/**
 * Servlet implementation class FacebookRedirectServlet
 */
@WebServlet("/FacebookRedirectServlet")
public class FacebookRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static Logger log=Logger.getLogger(FacebookRedirectServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacebookRedirectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("Entering method doGet FacebookRedirectServlet ");;
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String code = request.getParameter("code");
		
		if(code!=null && code.length()>0){
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		
		if(fbProfileData!=null && fbProfileData.size()>0){
			
			HttpSession session = request.getSession();
			String email = fbProfileData.get("email");
			if(email != null && email.length()>0){
				log.info("Email Obtained ::"+email);
				session.setAttribute("email", email);
			}
            session.setAttribute("name", fbProfileData.get("first_name"));
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
			response.sendRedirect("/barter/index.jsp");
		}else{
			RequestDispatcher view = request.getRequestDispatcher("/barter/pages/login/login.jsp");
			request.setAttribute("message","Account Creation Failed. Kindly try again.");
			view.forward(request, response);
		}
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
