<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
<head>
  <title>Admin</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="user-scalable=no,width=device-width" >

	<!-- CSS -->
    <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/userManagement.css" rel="stylesheet" type="text/css">
    <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css">
    <link href="resources/css/partialpage/createUser.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">

	<!-- JS -->
    <script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script src="resources/js/lib/jquerytools/1.2.6/jquery.tools.min.js" type="text/javascript"></script>
    <script src="resources/js/lib/jqueryui/1.8.22/jquery-ui.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
    <script type="text/javascript" src="resources/js/userManagement.js"></script> 
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>


    <script type="text/javascript">
    function loadCreateUserForm(docMode,userId){ 
        if (docMode == "edit") { // Edit User form
            $.ajax({
                url : "editUser?mode=edit&userId="+userId,
                type : 'GET',
                success : function(data) {
                    $('#adminBodyDiv').empty();
                    $('#adminBodyDiv').append(data);
                    $("#formHeader").html("<h2>Edit User</h2>");
                    $('#userForm').attr('action', "editUser");
                    $('#changePwordBtn').show();
                    $('#passwordLI, #password2LI').hide();
                } 
            });
        }
        if (docMode == 'create') { // create user form
            $.ajax({
                url : "createUser",
                type : 'GET',
                success : function(data) {
                    $('#adminBodyDiv').empty();
                    $('#adminBodyDiv').append(data);
                    $("#formHeader").html("<h2>Create New User</h2>");
                    $('#userForm').attr('action', "createUser");
                    $('#changePwordBtn').hide();
    
                }
            });
        }
    }
    function createUser(docMode){ // save method for both create and edit user.
        $('#createUserStatusMessage').css('display','none');	
        if (validateForm()) {
            $('#validationErrorMessage').css('display', 'block');
            $('#userRoleParam').val($('#userRoleParamSelect').val());
            $('#userStatusParam').val($('#userStatusParamSelect').val());
            if(docMode == "edit"){
                var url = "editUser";
                $.post(url,$("#userForm").serialize(),function(data){
                    $("#adminBodyDiv").html(data);
                        $("#formHeader").html("<h2>Edit User</h2>");
                        $('#userForm').attr('action', "editUser");
                        $('#changePwordBtn').show();
                        $('#passwordLI, #password2LI').hide();
                        
                    });	
            }else{
                var url = "createUser";
                $.post(url,$("#userForm").serialize(),function(data){
                    $("#adminBodyDiv").html(data);
                    $("#formHeader").html("<h2>Create New User</h2>");
                    $('#userForm').attr('action', "createUser");
                    $('#changePwordBtn').hide();
                    
                });
            }
        } else {
            $('#validationErrorMessage').css('display', 'block');
        }	
    }
    function resetForm(){
        $('#firstNameParam').val("");
        $('#middleInitialParam').val("");
        $('#lastNameParam').val("");
        $('#ssnParam').val("");
        $('#userIdParam').val("");
        $('#passwordParam').val("");
        $('#password2Param').val("");
        $('#emailAddressParam').val("");
        $('#emailAddress2Param').val("");
        $('#phoneNumberParam').val("");
        $('#phoneNumber2Param').val("");
    
        $('input[type="text"]').removeClass('error')
                .removeAttr('title');
        $('input[type="password"]')
                .removeClass('error').removeAttr('title');
    
        // remove any selected checkboxes
        $('input:checkbox').removeAttr('checked');
    }
    function loadSysConfigBody(){
        
        $('#userManagementTab').removeClass("subTabVisited");
        $('#systemConfigurationTab').addClass("subTabVisited");
        $.ajax({
            url : "systemConfiguration",
            type : 'GET',
            success : function(data) {
                $('#adminBodyDiv').empty();
                $('#adminBodyDiv').html(data);
            }
        });
    }
    </script>

</head>
<body>
<div id="outerPageDiv" >
	<%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp" %>
	<div class="navbar navbar-default navbar-update" role="navigation">
		<div class="container bg_transparent">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
			</div>
			<nav class="navbar-collapse collapse">
				<ul class="nav navbar-nav" id="tabs">
				</ul>
			</nav>
			<!--/.nav-collapse --> 
		</div>
	</div>
	<div class="container left-right-shadow">
		<div class="row">
			<div class="col-md-7 pull-left">
				<h1>User Management</h1>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12">
				<div id="bodyDiv">
					<div id="adminBodyDiv">
						<div id="userManagementBodyDiv">
							<div id="userManagementContainer"> <span id="createUserContainer">
								<button type='button' title='Click to create new user' class='indexButtons btn btn-primary' onClick='loadCreateUserForm("create","");' > <span class="glyphicon glyphicon-plus"></span> Create New User</button>
								</span>
								<div class="clea-fix"></div>
								<div  id="mainContent">
									<div class="userTableContainer">
										<table  class="display jqueryDataTable table" summary="User Management Table">
											<thead>
												<tr>
													<th id="loginIdColumnHeader">Login id</th>
													<th id="lastNameColumnHeader">Last name</th>
													<th id="firstNameColumnHeader">First name</th>
													<th id="statusColumnHeader">Status</th>
													<th id="roleColumnHeader">Role</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="cprs_validation_diag" title="CPRS validation"></div>
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>

</body>
</html>