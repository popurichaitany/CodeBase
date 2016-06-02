<%@page import="beans.UserPojo" %>
<%@page import="user.UserUtility" %>
<%
UserUtility userObj = new  UserUtility();
String sessionEmail = null;
UserPojo userProfile = null;
if(request.getSession().getAttribute("email")!=null){
	sessionEmail=(String)session.getAttribute("email");
	UserUtility util = new UserUtility();
	userProfile = util.fetchUserProfile(sessionEmail);
	
}else{
	System.out.print("User not logged in");
}

%>
<div class="container"> 
<div id="signupbox" style="margin-top: 10px;" class="mainbox col-md-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">Edit Profile</div>
                            
                        </div>  
                        <div class="panel-body" >
                            <form id="signupform" class="form-horizontal" role="form" method="post">
                                
                                <div id="signupalert" style="display:none" class="alert alert-danger">
                                    <p>Error:</p>
                                    <span></span>
                                </div>
                                    
                                <div class="form-group">
                                    <label for="firstname" class="col-md-3 control-label">First Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="firstname" placeholder="Enter First Name" value='<%=userProfile.getFirstName() %>'>
                                    </div>
                                        <div class="signupMessageContainer"></div>
                                </div>
                                  
                                <div class="form-group">
                                    <label for="lastname" class="col-md-3 control-label">Last Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="lastname" placeholder="Enter Last Name" value='<%=userProfile.getLastName() %>'>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="email" class="col-md-3 control-label">Email</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="email" placeholder="Enter Email Address" value='<%=userProfile.getEmailId() %>'>
                                    </div>
                                </div>
                                    
                                <div class="form-group">
                                    <label for="phone" class="col-md-3 control-label">Phone</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="phone" placeholder="Phone Number" value='<%=userProfile.getPhone()%>'>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="dob" class="col-md-3 control-label">Date of Birth</label>
                                    <div class="col-md-9">
                                      <input type="date" class="form-control" name="dob" placeholder="DateOfBirth" maxlength="10">
                                    </div>
                                </div>
                                
                                
                                <div class="form-group">
                                    <label for="address1" class="col-md-3 control-label">Street Name</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="address1" placeholder="Enter Street Name" value='<%=userProfile.getAddress1()%>'>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="address2" class="col-md-3 control-label">Apartment Details</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="address2" placeholder="Enter Apartment Details" value='<%=userProfile.getAddress1()%>'>
                                    </div>
                                </div>    
                                
                                <div class="form-group">
                                    <label for="city" class="col-md-3 control-label">City</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="city" placeholder="Enter City" value='<%=userProfile.getCity()%>'>
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
                                        <input type="text" class="form-control" name="zipcode" placeholder="Enter Zip Code" value='<%=userProfile.getZipcode()%>'>
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
                                        <button id="updateprofile" type="submit" class="btn btn-info"><i class="icon-hand-right"></i>Update Profile</button>
                                         
                                    </div>
                                </div>
                              
                            </form>
                         </div>
                    </div>
			 </div>
			 </div>