<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Lets Barter</title>
	<link rel="icon" href="/barter/favicon/favicon.ico" type="image/x-icon">
</head>

<body>
	<jsp:include page="/header/header.jsp"></jsp:include>
	<div class="container">
	<!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">New Barter Request
                    
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/barter/index.jsp">Home</a>
                    </li>
                    <li class="active">New Barter Request</li>
                </ol>
            </div>
        </div>

    <div class="container">    

        <div id="newBarterRequest" style="margin-top: 10px;" class="mainbox col-md-9 col-sm-9 col-sm-offset-1">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">Bartering Now</div>
                        </div>  
                        <div class="panel-body">
                            <form id="newBarterForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" action="newBarter">
                                
                                <div id="newBarteralert" style="display: none;" class="alert alert-danger">
                                    <p>Error:</p>
                                    <span></span>
                                </div>
                                
                                <%
								String message=(String)request.getAttribute("message");
								if(message!=null && message.length()>0){
								%>
								<div class="alert alert-success">
  									<b><%=message%> !!</b><a href="/barter/pages/myAccount.jsp"> Click here</a> to View your Barter Post.
								</div>
							   <%
								}
							   %>
                                    
                                <div class="form-group">
                                    <label for="title" class="col-md-4 control-label">Title Message</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="title" placeholder="Enter Barter Title">
                                    </div>
                                    <div class="newBarterMessageContainer"></div>
                                </div>
                                
                                 <div class="form-group">
                                    <label for="offeringProductCategory" class="col-md-4 control-label">Category of Offering Product</label>
                                    <div class="col-md-8">
									<select class="form-control" name="offeringProductCategory">
										<option value="1">Food Products</option>
										<option value="2">Automotive</option>
										<option value="3">Clothes</option>
										<option value="4">Furniture</option>
										<option value="5">Services</option>
										<option value="6">Electronics</option>
										</select>
									</div>
								</div>
								  
                                <div class="form-group">
                                    <label for="myOffer" class="col-md-4 control-label">What am I offering</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="myOffer" placeholder="Offering Product or Service">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="expectedProductCategory" class="col-md-4 control-label">Category of Expected Product</label>
                                    <div class="col-md-8">
									<select class="form-control" name="expectedProductCategory">
										<option value="1">Food Products</option>
										<option value="2">Automotive</option>
										<option value="3">Clothes</option>
										<option value="4">Furniture</option>
										<option value="5">Services</option>
										<option value="6">Electronics</option>
										</select>
									</div>
								</div>
                                    
                                <div class="form-group">
                                    <label for="askOffer" class="col-md-4 control-label">What I am looking for</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="askOffer" placeholder="Expected Product or Service">
                                    </div>
                                </div>
                                
                                                                
                                <div class="form-group">
                                    <label for="imageUpload" class="col-md-4 control-label">Upload Image of Product</label>
                                    <div class="col-md-8">
                                        <input type="file" name="file" id="js-upload-files">
                                    </div>
                                </div>
                                
                               
                                <div class="form-group">
                                    <label for="contactDetail" class="col-md-4 control-label">Contact Details</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" name="contactDetail" placeholder="Enter Contact Details">
                                    </div>
                                </div>
                                
                                
                                <div class="alert alert-info col-md-offset-4">
  								<strong>Note :</strong> Contact Number will be displayed to other users on Approval for barter trade.
								</div>
								
                                <input type="hidden" name="formtype" value="newbarterrequest">
                                <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-4 col-md-12" style="padding-top:5px;">
                                        <button id="btn-signup" type="submit" class="btn btn-info"><i class="icon-hand-right"></i>Upload Request</button>
                                    </div>
                                </div>
                                
                                
                            </form>
                         </div>
                    </div>
			 </div>
          
    </div>
    <hr>
          <!-- Footer -->
        <jsp:include page="/footer/footer.jsp"></jsp:include>
   </div>

  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/> 
  <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"> </script>
  <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/3.51/jquery.form.min.js"></script> -->
  <script src="/barter/js/loginvalidation.js"></script>  	
    </body>
    </html>