<%@page import="user.TradeUtility" %>
<%

String tradeId = request.getParameter("tradeId");
String action = request.getParameter("action");
TradeUtility util = new TradeUtility();
util.approveBarterRequest(tradeId,action);

%>