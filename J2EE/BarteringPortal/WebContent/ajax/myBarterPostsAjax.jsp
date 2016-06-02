<%@page import="user.BarterPostUtility" %>
<%@page import="user.UserUtility" %>
<%@page import="beans.BarterPostPojo" %>
<%@page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	BarterPostUtility utilObj = new BarterPostUtility();
	UserUtility userObj = new  UserUtility();
	String sessionEmail = null;
	boolean allowBarter = false;
	List<BarterPostPojo> barterPosts = null;
	if(request.getSession().getAttribute("email")!=null){
		sessionEmail=(String)session.getAttribute("email");
		barterPosts = utilObj.fetchMyBarterPosts(sessionEmail);
		allowBarter = false;
	}else{
		System.out.print("User not logged in");
	}
	if(barterPosts.size() > 0){
	for (BarterPostPojo obj : barterPosts){
		String imgName=null;
		if(obj.getItemImage()!=null){
			imgName = "/barter/uploadeddata/"+obj.getItemImage();
			//imgName = "file:///D:/projimages/"+obj.getItemImage();
		}else
			imgName = "/barter/uploadeddata/noimage.jpg";
	
	
	%>
	<div>

	  <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <div style="width:120px;height: 100px;">
                            <img src="<%=imgName%>">
                            </div>
                            <div class="caption">
                            	<h4 style="color:#337ab7">#<%=obj.getReqId() %>&nbsp;<%=obj.getTitle()%></h4>
                                <p><%=obj.getItemOfferedDesc()%></p>
                            </div>
                            <div class="caption">
                            <strong>Looking For</strong> 
                            <p><%=obj.getExpectedItemDesc() %>
                            </div>
                            <%-- <%
                            	int userRating = userObj.fetchUserRatingFromUserId(obj.getUserId());
                            	
                            %> --%>
                           <%--  <div class="ratings">
                                <p class="pull-right">User Rating</p>
                                <p>
                                <%
                                	for (int i=1;i<=userRating;i++){
                                %>
                                    <span class="glyphicon glyphicon-star"></span>
                                    
                                <%
                                	}
                                %>
                                </p>
                                
                            </div> --%>
                            <% if (allowBarter){ %>
                            <div class="col-sm-4 col-md-4 col-xs-4"><a class="btn btn-primary" onclick="setSelectedBarterPost('<%=obj.getReqId()%>');" href="javascript:void(0);">Barter Now!</a></div>
                            <% } %>
                        </div>
       </div>
               
               
       <%
	} }else{
       %>
		<div class="alert alert-info"><b>This is your bartering space. <a href="/barter/pages/newBarterForm.jsp" >Click here</a> to upload your first post ...</b></div>
	<%
	}
	%>
</div>