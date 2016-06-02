<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Lets Barter</title>


    <!-- Custom CSS -->
    <link href="/barter/css/barter.css" rel="stylesheet">
	

</head>

<body>

    <!-- Navigation -->
    <jsp:include page="/header/header.jsp"></jsp:include>

    <!-- Page Content -->
    <div class="container">

        <div class="row" style="padding-top: 20px;">

            <div class="col-md-3">
                <p class="lead">My Account</p>
                <div class="list-group">
                    <a onclick="showMyBarterPosts();" href="javascript:void(0);" class="list-group-item">My Barter Posts</a>
                    <a onclick="showMyTradeHistory();" href="javascript:void(0);" class="list-group-item">My Trade History</a>
                    <a onclick="showBarterRequest();" href="javascript:void(0);" class="list-group-item">Barter Trade Requests</a>
                    <a onclick="editMyPofile();" href="javascript:void(0);" class="list-group-item">Edit Profile</a>
                   
                </div>
            </div>

            <div class="col-md-9">
                <div class="row">
                <div id="barter_post_results"></div>
				</div>
			</div>

        </div>

    </div>
    <!-- /.container -->

    <div class="container">
	<hr>
	<jsp:include page="/footer/footer.jsp"></jsp:include>
    </div>

 <script type="text/javascript" src="/barter/js/myAccount.js"></script> 
</body>

</html>
