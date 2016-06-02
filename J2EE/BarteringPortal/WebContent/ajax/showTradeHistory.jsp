<%@page import="user.BarterPostUtility" %>
<%@page import="user.UserUtility" %>
<%@page import="user.TradeUtility" %>
<%@page import="beans.TradePojo" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.ArrayList" %>
<style>
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
    background-color: #4CAF50;
    color: white;
}


</style>
<%
	BarterPostUtility utilObj = new BarterPostUtility();
	UserUtility userObj = new  UserUtility();
	TradeUtility  tradeObj = new TradeUtility();
	String sessionEmail = null;
	List<TradePojo> barterCompletedTrades = new ArrayList<TradePojo>();
	List<TradePojo> completedTrades = new ArrayList<TradePojo>();
	
	List<TradePojo> myTradeHistory = null;
	if(request.getSession().getAttribute("email")!=null){
		sessionEmail=(String)session.getAttribute("email");
		myTradeHistory = tradeObj.fetchTradeHistory(sessionEmail);
	
	}else{
		System.out.print("User not logged in");
	}
	
	for(TradePojo trade : myTradeHistory){
		if("Barter-Finalised".equalsIgnoreCase(trade.getRequestStatus()))
			barterCompletedTrades.add(trade);
		else
			completedTrades.add(trade);
	}
	%>
<div class="table-responsive"> 
  <h4>Barter Finalised Trades</h4>         
  <table class="table" style="overflow: hidden;" id="barterComplete">
    <thead>
      <tr>
        <th>Trade #</th>
    <!--     <th>Request Status</th> -->
        <th>Status Time</th>
        <th>Co Trader Name</th>
        <th>Co Trader Rating</th>
      </tr>
    </thead>
    <tbody>
      <%
      if(barterCompletedTrades.size()>0){
      for (TradePojo trade : barterCompletedTrades){
      
      %>
      
      <tr>
        <td><%=trade.getTradeId() %></td>
        <%-- <td><%=trade.getRequestStatus()%></td> --%>
        <%
        
       DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 	   //get current date time with Date()
 	   Date date = trade.getTradeStatusTime();
 	   String time = dateFormat.format(date);
 	 
        %>
        <td><%=time %></td>
        
        <%
         String name = null;
         int tradeId = 0;
         int loggedInUserId = userObj.fetchUserIdFromEmail(sessionEmail);
         if(loggedInUserId==trade.getPrimaryTraderUserId())
        	 tradeId = trade.getSecReqId();
         else
        	 tradeId = trade.getPrimaryReqId();
         int userId = utilObj.fetchUserIdFromReqId(tradeId);
		 name =  userObj.fetchNamefromUserId(userId);
        %>
        <td><%=name %></td>
        <%
        
        
        %>
        <td>
        <% 
        boolean showRating = tradeObj.showRatingOption(trade.getTradeId(), loggedInUserId);
        if("Barter-Finalised".equalsIgnoreCase(trade.getRequestStatus()) && showRating){ 
        %>	
        	<div class="row">
    
    
    		<div class="col-md-4">
  			
       		<select class="form-control input-small" name="rating" id="rating">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			</div>
			<div class="col-md-8">
            <a id="rateUser" class="btn btn-info" onclick="rateUser(<%=trade.getTradeId()%>);" href="javascript:void(0);"><i class="icon-hand-right"></i>Rate User</a>
            </div>
            </div>
       <%
       }else{
    	   %>
    	   <div class="ratings">
    	    <%
    	    	String type = tradeObj.loggedInType(trade.getTradeId(), loggedInUserId);
    	    	int rating=0;
    	    	int id =0;
    	    	if("PRIMARY".equalsIgnoreCase(type))
    	    		id = trade.getSecTraderUserId();
    	    	else
    	    		id = trade.getPrimaryTraderUserId();
    	    	rating = userObj.fetchUserRatingFromUserId(userId);
        		for (int i=1;i<=rating;i++){ 
        	%>
              <span class="glyphicon glyphicon-star"></span>
         	<% } %>
         	<span style="color: black;padding-left: 10px;">Rating Updated !!</span>
         </div>
         
        </td>
        
        <%
        
        }
        
        %>
      </tr>
       <%
        
        }
      }else{
        
        %>
        <tr>
        <td colspan="8">
        <div class="alert alert-info">
  		No records found
		</div>
        
        </td>
        <tr>
      
      <% } %>
    
    </tbody>
  </table>
  </div>
  
  
  <div class="table-responsive"> 
  <h4>Completed Trades</h4>         
  <table class="table">
    <thead>
      <tr>
        <th>Trade #</th>
        <!-- <th>Request Status</th> -->
        <th>Status Time</th>
        <th>Co Trader Name</th>
        <th>Co Trader Rating</th>
      </tr>
    </thead>
    <tbody>
      <%
      if(completedTrades.size()>0){
      for (TradePojo trade : completedTrades){
      
      %>
      
      <tr>
        <td><%=trade.getTradeId() %></td>
        <%-- <td><%=trade.getRequestStatus()%></td> --%>
        <%
        
       DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 	   //get current date time with Date()
 	   Date date = trade.getTradeStatusTime();
 	   String time = dateFormat.format(date);
 	 
        %>
        <td><%=time %></td>
        
        <%
         String name = null;
         int tradeId = 0;
         int loggedInUserId = userObj.fetchUserIdFromEmail(sessionEmail);
         if(loggedInUserId==trade.getPrimaryTraderUserId())
        	 tradeId = trade.getSecReqId();
         else
        	 tradeId = trade.getPrimaryReqId();
         int userId = utilObj.fetchUserIdFromReqId(tradeId);
		 name =  userObj.fetchNamefromUserId(userId);
        %>
        <td><%=name %></td>
        <%
        
        
        %>
        <td>
      	<div class="ratings">
    	    <%
    	    	String type = tradeObj.loggedInType(trade.getTradeId(), loggedInUserId);
    	    	int rating=0;
    	    	int id =0;
    	    	if("PRIMARY".equalsIgnoreCase(type))
    	    		id = trade.getSecTraderUserId();
    	    	else
    	    		id = trade.getPrimaryTraderUserId();
    	    	rating = userObj.fetchUserRatingFromUserId(userId);
        		for (int i=1;i<=rating;i++){ 
        	%>
              <span class="glyphicon glyphicon-star"></span>
         	<% } %>
         </div>
        </td>
        
       
      </tr>
       <%
        
        }
      }else{
        
        %>
        <tr>
        <td colspan="8">
        <div class="alert alert-info">
  		No records found
		</div>
        
        </td>
        <tr>
      
      <% } %>
    
    </tbody>
  </table>
    </div>
    