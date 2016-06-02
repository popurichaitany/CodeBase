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
                <p class="lead">Browse Categories</p>
                <div class="list-group">
                    <a onclick="fetchBarteringPosts(1);" href="javascript:void(0);" class="list-group-item">Food Products</a>
                    <a onclick="fetchBarteringPosts(2);" href="javascript:void(0);" class="list-group-item">Automotive</a>
                    <a onclick="fetchBarteringPosts(3);" href="javascript:void(0);" class="list-group-item">Clothes</a>
                    <a onclick="fetchBarteringPosts(4);" href="javascript:void(0);" class="list-group-item">Furniture</a>
                    <a onclick="fetchBarteringPosts(5);" href="javascript:void(0);" class="list-group-item">Services</a>
                    <a onclick="fetchBarteringPosts(6);" href="javascript:void(0);" class="list-group-item">Electronics</a>
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
    <div class="modal fade" id="barterRequestModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="padding:15px 20px;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4><span class="col-md-4" style="float: right;"></span>Start Barter</h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form role="form" method="post">
            <div class="form-group">
              <label for="barterOfferPostId">Enter Your Bartering Post Number</label>
              <input type="text" class="form-control" id="barterOfferPostId" placeholder="Enter post number">
            </div>
            <div id="secReqId" style="display: none;"></div>
            <button type="submit" class="btn btn-success btn-block" id="sendBarterRequest"><span class="glyphicon glyphicon-off"></span> Send Request </button>
          </form>
        </div>
        
      </div>
      <!-- End of modal content -->
    </div>
  </div> 
  
  
  
  <div class="modal fade" id="barterConfirmationModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Lets Barter</h4>
        </div>
        <div class="modal-body">
          <p>Barter Request Sent !!</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
<script type="text/javascript" src="/barter/js/barterDetailData.js"></script>
</body>

</html>
