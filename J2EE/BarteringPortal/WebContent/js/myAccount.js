/**
 * 
 */

$(document).ready(function(){
	
	showMyBarterPosts();
	$("#updateprofile").bind('click',updateProfile);

})	

function approveTradeRequest(tradeId,tradeType){
	
	
	 $.ajax({
	   		type 	 : "POST",
	   		url 	 : "/barter/ajax/approveTradeRequest.jsp",
	   		data     : "tradeId="+tradeId+"&action="+tradeType,
	   		cache 	 : false,
	   		async    : false,
	   		complete : function(html){
	   			var response = html.responseText;
	   			response = response.replace(/^\s+|\s+$/g,'');
	   			if(response!=null && response != ''){
	   				document.getElementById("barter_post_results").innerHTML = response;
	   				
	   			}
	   			
	   		}
	 });
	 showBarterRequest();
	 if("approveRequest"==tradeType){
		 $('<div class="alert alert-success"><b>Request Approved !! Check Approved Request Section.</b></div>').insertBefore('#requestReceived').delay(2500).fadeOut();
	 }
		 
		 
	 if("completeBarter"==tradeType)
	 $('<div class="alert alert-success"><b>Barter Finalised !! Check Trade History Section.</b></div>').insertBefore('#barterRequestApproved').delay(2500).fadeOut();
}


function rateUser(tradeId){
	
	var rating = $("#rating").val();
	
	 $.ajax({
	   		type 	 : "POST",
	   		url 	 : "/barter/ajax/rateUser.jsp",
	   		data     : "tradeId="+tradeId+"&rating="+rating,
	   		cache 	 : false,
	   		async    : false,
	   		complete : function(html){
	   			var response = html.responseText;
	   			response = response.replace(/^\s+|\s+$/g,'');
	   			if(response!=null && response != ''){
	   				document.getElementById("barter_post_results").innerHTML = response;
	   			}
	   			
	   		}
	 });
	 showMyTradeHistory();
	 $('<div class="alert alert-success"><b>Rating updated. Hope you had great barter trade !!</b></div>').insertBefore('#barterComplete').delay(2500).fadeOut();
	 
	 
}




function showMyBarterPosts(){
	
	
	 $.ajax({
	   		type 	 : "POST",
	   		url 	 : "/barter/ajax/myBarterPostsAjax.jsp",
	   		cache 	 : false,
	   		async    : false,
	   		complete : function(html){
	   			var response = html.responseText;
	   			response = response.replace(/^\s+|\s+$/g,'');
	   			if(response!=null && response != ''){
	   				document.getElementById("barter_post_results").innerHTML = response;
	   				
	   			}
	   			
	   		}
	 
	 });
	 
}

function showMyTradeHistory(){
	
	
	$.ajax({
   		type 	 : "POST",
   		url 	 : "/barter/ajax/showTradeHistory.jsp",
   		cache 	 : false,
   		async    : false,
   		complete : function(html){
   			var response = html.responseText;
   			response = response.replace(/^\s+|\s+$/g,'');
   			if(response!=null && response != ''){
   				document.getElementById("barter_post_results").innerHTML = response;
   				
   			}
   			
   		}
 });
}

function showBarterRequest(){
	
	$.ajax({
   		type 	 : "POST",
   		url 	 : "/barter/ajax/showCurrentBarterRequest.jsp",
   		cache 	 : false,
   		async    : false,
   		complete : function(html){
   			var response = html.responseText;
   			response = response.replace(/^\s+|\s+$/g,'');
   			if(response!=null && response != ''){
   				document.getElementById("barter_post_results").innerHTML = response;
   				
   			}
   			
   		}
 });
}

function editMyPofile(){
	
	$.ajax({
   		type 	 : "POST",
   		url 	 : "/barter/ajax/editProfileAjax.jsp",
   		cache 	 : false,
   		async    : false,
   		complete : function(html){
   			var response = html.responseText;
   			response = response.replace(/^\s+|\s+$/g,'');
   			if(response!=null && response != ''){
   				
   				document.getElementById("barter_post_results").innerHTML = response;
   				
   			}
   			
   		}
 });
}

function updateProfile(){
	
	alert("Profile Updated Successfully");
	return false;
	
}
