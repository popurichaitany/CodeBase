<%@page import="user.BarterPostUtility" %>
<%@page import="user.UserUtility" %>
<%

String primaryReqId = request.getParameter("primReqId");
String secondaryReqId = request.getParameter("secReqId");
BarterPostUtility utilObj = new BarterPostUtility();
int tradeId = utilObj.addBarterTradeRequest(primaryReqId, secondaryReqId);
if(tradeId>0){
	out.print("Success");
}else{
	out.print("Failed");
}
%>