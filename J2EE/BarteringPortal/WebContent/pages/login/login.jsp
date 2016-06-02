<!DOCTYPE html>
<%@page import="facebook.FBConnection"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Lets Barter</title>
	<link rel="icon" href="/barter/favicon/favicon.ico" type="image/x-icon">
    <!-- Bootstrap Core CSS -->
    <!-- <link href="/barter/css/bootstrap.min.css" rel="stylesheet">

    Custom CSS
    <link href="/barter/css/modern-business.css" rel="stylesheet">

    Custom Fonts
    <link href="/barter/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
 -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  
</head>

<body>
	<jsp:include page="/header/header.jsp"></jsp:include>
	<%
	FBConnection fbConnection = new FBConnection();
	if(session.getAttribute("name")!=null){
		String email = (String)session.getAttribute("email");
		String name = (String)session.getAttribute("name");
	%>
	
	<%
		}
	%>
	<div class="container">
	<!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Login
                    
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/barter/index.jsp">Home</a>
                    </li>
                    <li class="active">Login</li>
                </ol>
            </div>
        </div>

    <div class="container">    
        <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2" style="margin-top: 10px;">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Sign In</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
                    </div>     

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                            
                        <form id="loginform" class="form-horizontal" role="form" method="post" action="userLogin">
                           <%
								String message=(String)request.getAttribute("message");
								if(message!=null && message.length()>0){
							%>
							<div class="alert alert-danger">
  							<strong>Authentication Failed</strong>
							</div>
							   <%
								}
							   %>
   		                   <div style="margin-bottom: 25px" class="form-group">
   		                   <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                               <input id="login-username" type="text" class="form-control" name="email"  placeholder="email" />  
        					</div>
        					<div class="messageContainer"></div>
                            </div>
        					                        
                            <div style="margin-bottom: 25px" class="form-group">
                            <div class="input-group">
                                 <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                               	 <input id="login-password" type="password" class="form-control" name="password" placeholder="password"/>
                            </div>
                            
                            </div>
                            
                            <div class="input-group">
                               <div class="checkbox">
                               <label><input id="login-remember" type="checkbox" name="remember" value="1"> Remember me </label>
                               </div>
                            </div>
 							
 							
                            <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->
                                    <div class="col-sm-12 controls">
                                      <button id="btn-login" type="submit" class="btn btn-success">Login </button>
                                      <a id="btn-fblogin" href="<%=fbConnection.getFBAuthUrl()%>" class="btn btn-primary">Login with Facebook</a>
                                    </div>
                            </div>
							<input type="hidden" name="formtype" value="login">
                            <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Don't have an account! 
                                        <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                            Sign Up Here
                                        </a>
                                        </div>
                                    </div>
                                </div>    
                            </form>     
					 </div>                     
                    </div>  
        </div>
        <div id="signupbox" style="display:none;margin-top: 10px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">Sign Up</div>
                            <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a></div>
                        </div>  
                        <div class="panel-body" >
                            <form id="signupform" class="form-horizontal" role="form" method="post" action="userLogin">
                                
                                <div id="signupalert" style="display:none" class="alert alert-danger">
                                    <p>Error:</p>
                                    <span></span>
                                </div>
                                    
                                <div class="form-group">
                                    <label for="firstname" class="col-md-3 control-label">First Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="firstname" placeholder="Enter First Name">
                                    </div>
                                        <div class="signupMessageContainer"></div>
                                </div>
                                  
                                <div class="form-group">
                                    <label for="lastname" class="col-md-3 control-label">Last Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="lastname" placeholder="Enter Last Name">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="email" class="col-md-3 control-label">Email</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="email" placeholder="Enter Email Address">
                                    </div>
                                </div>
                                    
                                <div class="form-group">
                                    <label for="phone" class="col-md-3 control-label">Phone</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="phone" placeholder="Phone Number">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="dob" class="col-md-3 control-label">Date of Birth</label>
                                    <div class="col-md-9">
                                      <input type="date" class="form-control" name="dob" placeholder="DateOfBirth" maxlength="10">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-md-3 control-label">Password</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="passwd" placeholder="Password">
                                    </div>
                                    
                                </div>
                                
                                <div class="form-group">
                                    <label for="confirmpassword" class="col-md-3 control-label">Confirm Password</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="confirmpassword" placeholder="Confirm Password">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="address1" class="col-md-3 control-label">Street Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="address1" placeholder="Enter Street Name">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="address2" class="col-md-3 control-label">Apartment Details</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="address2" placeholder="Enter Apartment Details">
                                    </div>
                                </div>    
                                
                                <div class="form-group">
                                    <label for="city" class="col-md-3 control-label">City</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="city" placeholder="Enter City">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="state" class="col-md-3 control-label">State</label>
                                    <div class="col-md-9">
									<select class="form-control" name="state">
										<option value="AL">Alabama</option>
										<option value="AK">Alaska</option>
										<option value="AZ">Arizona</option>
										<option value="AR">Arkansas</option>
										<option value="CA">California</option>
										<option value="CO">Colorado</option>
										<option value="CT">Connecticut</option>
										<option value="DE">Delaware</option>
										<option value="DC">District Of Columbia</option>
										<option value="FL">Florida</option>
										<option value="GA">Georgia</option>
										<option value="HI">Hawaii</option>
										<option value="ID">Idaho</option>
										<option value="IL">Illinois</option>
										<option value="IN">Indiana</option>
										<option value="IA">Iowa</option>
										<option value="KS">Kansas</option>
										<option value="KY">Kentucky</option>
										<option value="LA">Louisiana</option>
										<option value="ME">Maine</option>
										<option value="MD">Maryland</option>
										<option value="MA">Massachusetts</option>
										<option value="MI">Michigan</option>
										<option value="MN">Minnesota</option>
										<option value="MS">Mississippi</option>
										<option value="MO">Missouri</option>
										<option value="MT">Montana</option>
										<option value="NE">Nebraska</option>
										<option value="NV">Nevada</option>
										<option value="NH">New Hampshire</option>
										<option value="NJ">New Jersey</option>
										<option value="NM">New Mexico</option>
										<option value="NY">New York</option>
										<option value="NC">North Carolina</option>
										<option value="ND">North Dakota</option>
										<option value="OH">Ohio</option>
										<option value="OK">Oklahoma</option>
										<option value="OR">Oregon</option>
										<option value="PA">Pennsylvania</option>
										<option value="RI">Rhode Island</option>
										<option value="SC">South Carolina</option>
										<option value="SD">South Dakota</option>
										<option value="TN">Tennessee</option>
										<option value="TX">Texas</option>
										<option value="UT">Utah</option>
										<option value="VT">Vermont</option>
										<option value="VA">Virginia</option>
										<option value="WA">Washington</option>
										<option value="WV">West Virginia</option>
										<option value="WI">Wisconsin</option>
										<option value="WY">Wyoming</option>
									</select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="zipcode" class="col-md-3 control-label">Zip Code</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="zipcode" placeholder="Enter Zip Code">
                                    </div>
                                </div>
                                
                                <!-- <div class="form-group">
                                    <label for="icode" class="col-md-3 control-label">Invitation Code</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="icode" placeholder="">
                                    </div>
                                </div> -->
								<input type="hidden" name="formtype" value="signup">
                                <div class="form-group">
                                    <!-- Button -->                                        
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-signup" type="submit" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp Sign Up</button>
                                         
                                    </div>
                                </div>
                                
                                <div style="border-top: 1px solid #999; padding-top:20px"  class="form-group">
                                    
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-facebook"></i>Sign Up with Facebook</button>
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
  <!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script> -->
  <!-- <script src="/barter/js/jquery.js"></script>  
  <script src="/barter/js/bootstrap.min.js"></script>
   -->
   <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/> 
  <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"> </script>
  <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/3.51/jquery.form.min.js"></script> -->
  <script src="/barter/js/loginvalidation.js"></script>  	
    </body>
    </html>
