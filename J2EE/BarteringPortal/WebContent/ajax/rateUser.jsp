<%@page import="user.TradeUtility" %>
<%

String tradeId = request.getParameter("tradeId");
String rating = request.getParameter("rating");

TradeUtility  tradeObj = new TradeUtility();
String sessionEmail = null;

if(request.getSession().getAttribute("email")!=null){
	sessionEmail=(String)session.getAttribute("email");
	tradeObj.updateUserRating(sessionEmail,tradeId,rating);

}else{
	System.out.print("User not logged in");
}

%>
