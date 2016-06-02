<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Lets Barter</title>
	<link rel="icon" href="/barter/favicon/favicon.ico" type="image/x-icon">

	<!-- Google Fonts -->
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
	 <!-- <link href="/barter/css/bootstrap.min.css" rel="stylesheet"> -->
	<link rel="stylesheet" href="/barter/css/animate.css">
	<!-- Custom Stylesheet -->
	<link rel="stylesheet" href="/barter/css/style.css">
	<link href="/barter/css/socialmedia.css" rel="stylesheet">
	<!-- <link href="/barter/css/modern-business.css" rel="stylesheet"> -->
	<link href="/barter/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script> 
</head>

<body>
	 <%-- <jsp:include page="/header/header.jsp"></jsp:include> --%> 
	<div class="container">
	
		<div class="top">
			<h1 id="title" class="hidden"><span id="logo">Thank you for visiting <span>Lets Barter</span><br/><span style="font-size:20px;"><a href="/barter/index.jsp" style="color:white;">Visit Homepage</a></span></span></span></h1>
		</div>
<!-- 		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Log In</h2>
			</div>
			<label for="username">Username</label>
			<br/>
			<input type="text" id="username">
			<br/>
			<label for="password">Password</label>
			<br/>
			<input type="password" id="password">
			<br/>
			<button type="submit">Sign In</button>
			<br/>
			<a href="#"><p class="small">Forgot your password?</p></a>
		</div>
 -->	
 <div class="login-box animated fadeInUp">
 <div class="style_social" style="width:350px;float:left;text-align: left;margin-left: 50px;">
<h2 style="font-size: 14px;color: #666;font-weight: bolder;">Connect with LetsBarter</h2>
<ul class="list-unstyled list-social-icons" style="list-style: none;">
                    <li style="padding: 5px 5px 5px;margin-bottom: 20px;margin-left: -40px;">
                        <a href="#" class="btn btn-facebook" style="padding: 10px 10px 10px 10px;"><i class="fa fa-facebook"></i> | Connect with Facebook</a>
                    </li>
                    <li style="padding: 5px 5px 5px;margin-bottom: 20px;margin-left: -40px;">
                        <a href="#" class="btn btn-linkedin" style="padding: 10px 10px 10px 10px"><i class="fa fa-linkedin"></i> | Connect with LinkedIn</a>
                    </li>
                    
                    <li style="padding: 5px 5px 5px;margin-bottom: 20px;margin-left: -40px;">
                        <a href="#" class="btn btn-google-plus" style="padding: 10px 10px 10px 10px"><i class="fa fa-google-plus"></i> | Connect with Google Plus</a>
                    </li>
                    
                </ul>
                
</div>

            <div class="row">
                <div class="col-lg-12" >
                    <p style="font-size: 14px;color: #666;font-weight: bolder;">Copyright &copy; Letsbarter.com 2015</p>
                </div>
            </div>
 </div>
 
 
 
 </div>
  <!-- <link href="/barter/css/bootstrap.min.css" rel="stylesheet"> -->
</body>

<script>
	$(document).ready(function () {
    	$('#logo').addClass('animated fadeInDown');
    	$("input:text:visible:first").focus();
	});
/* 	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	}); */
</script>

</html>