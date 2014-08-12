<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="true" %>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">

    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
    <title>eScreening Create/Edit User</title>
    <script type="text/javascript" src="resources/js/querystring.js"></script>
    <script type="text/javascript" src="resources/js/createUser.js"></script>

    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>


</head>
<body>
<div id="createUserFormContainer">
  <div id="formHeader">
    <h3>Create New User</h3>
  </div>
  <!-- action="createUser" -->
  <form id="userForm" autocomplete="off">
    <input type="hidden" id="userRoleParam" value="${selectedUserRoleId}">
    <input type="hidden" id="userStatusParam" value="${selectedUserStatusId}">
    <input type="hidden" name="idOfUserParam" id="idOfUserParam" value="${idParamVal}">
    <div id="createUserStatusMessage"> <span> ${createUserStatusMessage} </span> </div>
    <div id="validationErrorMessage">
      <div class="alert alert-danger">Validation Error - <span>Please correct the following marked fields in error:</span></div>
    </div>
    <!-- Create User Form, no tables allowed :) -->
    <div>
      <div class="row">
        <div class="col-md-4">
          <div class="form-group">
            <label for="firstNameParam">First Name *</label>
            <span class="field">
            <input type="text" name="firstNameParam" id="firstNameParam" class="inputfield form-control" size="30" maxlength="30" value="${firstNameValue}" tabindex="1" autocomplete="off">
            </span> </div>
        </div>

        <div class="col-md-4">
          <div class="form-group">
            <label for="middleInitialParam">Middle Initial</label>
            <span class="field">
            <input name="middleInitialParam" id="middleInitialParam" type="text" class="inputfield form-control" size="30" maxlength="30" value="${middleInitialValue}" tabindex="2" autocomplete="off">
            </span> </div>
        </div>
        
        <div class="col-md-4">
          <div class="form-group">
            <label for="lastNameParam">Last Name *</label>
            <span class="field">
            <input name="lastNameParam" id="lastNameParam" type="text" class="inputfield form-control" size="30" maxlength="30" value="${lastNameValue}" tabindex="3" autocomplete="off">
            </span> </div>
        </div>

      </div>
      
      <div class="row">
        <div class="col-md-4">
          <div class="form-group">
            <label for="">Phone Number *</label>
            <span class="field">
            <input name="phoneNumberParam" id="phoneNumberParam" type="text" class="inputfield form-control" size="30" maxlength="10" value="${phoneNumberValue}" tabindex="4" autocomplete="off"></span> </div>
        </div>
        <div class="col-md-4">
          <div class="form-group">
            <label for="phoneNumber2Param">Second Phone Number</label>
            <span class="field">
            <input name="phoneNumber2Param" id="phoneNumber2Param" type="text" class="inputfield form-control" size="30" maxlength="10" value="${phoneNumber2Value}" tabindex="5" autocomplete="off">
            </span> </div>
        </div>
        <div class="col-md-4">
          <div class="form-group">
            <label for="userRoleParamSelect">User Role</label>
            <span class="field">
            <select id="userRoleParamSelect" name="userRoleSelectParam" class="inputField  selectField form-control" tabindex="6" autocomplete="off">
              <!-- Fill with option tags from retrieved data. 
                   The selected option value will be bound as ${selectedUserRoleId}.Notes: Value should be readable in a POST to createUser/editUser, 
                   dependent on whether user is being added, or edited -->
            </select>
            </span> </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label for="emailAddressParam">Email Address *</label>
            <span class="field">
            <input name="emailAddressParam" id="emailAddressParam" type="text" class="inputfield form-control" size="30" maxlength="50" value="${emailAddressValue}" tabindex="7" autocomplete="off"></span> </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label for="emailAddress2Param">Second Email Address</label>
            <span class="field">
            <input name="emailAddress2Param" id="emailAddress2Param" type="text" class="inputfield form-control" size="30" maxlength="50" value="${emailAddress2Value}" tabindex="8" autocomplete="off">
            </span> </div>
        </div>
      </div>
      
      <div class="row">
      	<div class="col-md-4">
          <div class="form-group">
            <div id="newUsername">
              <label for="userIdParam">Username *</label>
              <span class="field">
              <input name="userIdParam" id="userIdParam" type="text" class="inputfield form-control" size="30" maxlength="30" value="${userIdValue}" tabindex="9" autocomplete="off"></span> </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="form-group">
            <label for="cprsVerifiedParam">CPRS Verified</label>
            <span class="field">
            <input type="text" name="cprsVerifiedParam" id="cprsVerifiedParam" class="inputfield form-control" size="30" maxlength="30" value="${cprsVerifiedValue}" tabindex="10" autocomplete="off" readonly>
            </span> </div>
        </div>
        <div class="col-md-4">
          <div class="form-group">
            <label for="userStatusParamSelect">User Status</label>
            <span class="field">
            <select id="userStatusParamSelect" name="userStatusSelectParam" class="inputField selectField form-control" tabindex="11" autocomplete="off">
              <!-- Fill with option tags from retrieved data. 
                    The selected option value will be bound as ${selectedUserStatusId}. Notes: Value should be readable in a POST to createUser/editUser, 
                    dependent on whether user is being added, or edited -->
            </select>
            </span> </div>
        </div>
      </div>
      

      <div class="row">
      	<div class="col-md-12">
            <label for="programs">Programs</label>
            
            <div class="form-inline">
                <div class="checkbox col-md-3">
                    <label>
                      <input type="checkbox"> Progam 1
                    </label>
                  </div>
                 <div class="checkbox col-md-3">
                    <label>
                      <input type="checkbox"> Progam 1
                    </label>
                  </div>
                  <div class="checkbox col-md-3">
                    <label>
                      <input type="checkbox"> Progam 1
                    </label>
                  </div>
                  <div class="checkbox col-md-3">
                    <label>
                      <input type="checkbox"> Progam 1
                    </label>
                  </div>              
              </div>
        </div>
      </div>
	  <br>


      <div class="row">
      	<div class="col-md-12">
      		<div class="form-group">
                <span class="field"><a id="changePwordBtn" href="#" class="indexButtons_ wideButton_ btn btn-primary  btn-xs"  role="button">Change Password</a>
                <a id="changePasswordBtn" href="#" class="btn btn-primary  btn-xs"  role="button"  data-toggle="modal" data-target="#changePasswordModal">Change Password</a></span>
            </div>
      	</div>
      </div>
      <div class="row">
        <div class="col-md-4">
            <div id="passwordLI">
              <label for="passwordParam">Password *</label>
              <span class="field">
              <input name="passwordParam" id="passwordParam" class="inputfield form-control" size="30" maxlength="30" type="password" tabindex="12">
              </span></div>
        </div>
        <div class="col-md-4">
          <div class="form-group">
            
            <div id="password2LI">
              <label for="password2Param">Reenter Password</label>
              <span class="field">
              <input name="password2Param" id="password2Param" type="password" class="inputfield form-control" size="30" maxlength="30" tabindex="13" autocomplete="off">
              </span></div>
              <div class="changePwdLi">
              <label for="nothing" class="changePwdLabel">Change Password <span class="req" title="${password2StatusMsg}">*</span></label>
            </div>
          </div>
        </div>
      </div>
      
      
      
      
      
      
    </div>
    
   
    <!-- Modal -->
    <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Modal title</h4>
          </div>
          <div class="modal-body">
            ...
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>


    
    
    <!-- End Create User Form -->
    <div> 
      <!-- Check ID for the field --> 
      <!-- hidden field to keep the id of the clinics to display -->
      <input name="clinicsParam" id="clinicsParam" type="hidden" value="${selectedUserClinicDelimitedIDs}">
      <input name="idStorage" id="idStorage" type="hidden" value="${loginIdStatusMsg}">
    </div>
  </form>
  
  
  <!-- -->
  <div class="alert alert-success"><Strong>Please ( <img src="resources/images/checkIcon1.png" class="strongImg">) the "Assign Clinic" box for each Clinic that the user requires access to.</Strong></div>
  <div  id="mainContent">
    <table class="jqueryDataTable table">
      <thead>
        <tr>
          <th id="clinicIdColumnHeader">Clinic Id</th>
          <th id="checkboxNameColumnHeader">Assign Clinic</th>
          <th id="clinicNameColumnHeader">Name</th>
          <th id="programIdColumnHeader">Program id (hide)</th>
          <th id="programNameColumnHeader">Program</th>
          <th id="locationIdColumnHeader">Location id (hide)</th>
          <th id="locationNameColumnHeader">Location</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
  
  
  
  <input type="hidden" id="docMode" value="${userManagementPageMode}">
  <div id="formButton">
  <a onClick="createUser('${userManagementPageMode}')" class="searchBtn btn btn-primary">Save</a>
  <a onClick="resetForm()" class="searchBtn btn btn-default">Reset</a> </div>
</div>

<script>

$(document).ready(function() {
	//console.log("IN");
	$('#changePasswordModal').modal();				
});
</script>


					



</body>
</html>