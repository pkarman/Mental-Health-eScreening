<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="userEditViewApp">
<head>
    <title>User Edit View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon" />
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/veteranSearch.css" />" rel="stylesheet" type="text/css" />

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/adminDashboardTabs.js" />"></script>
</head>
<body>
	<a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">
		<%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp" %>

		<div class="navbar navbar-default navbar-update" role="navigation">
			<div class="container bg_transparent">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
				</div>
				<nav class="navbar-collapse collapse">
					<ul class="nav navbar-nav" id="tabs">
					</ul>
				</nav><!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container left-right-shadow">

		<c:if test="${callResult.hasError}">
			<div class="alert alert-danger">
				<c:out value="${callResult.userMessage}" />
			</div>

			<c:if test="${not empty callResult.systemMessage}">
				<div class="alert alert-danger">
					<c:out value="${callResult.systemMessage}" />
				</div>
			</c:if>
		</c:if>

		<div class="row">
			<div class="col-md-6">
				<c:if test="${userEditViewFormBean.isCreateMode}">
					<a name="skip" > </a ><h1>Create User</h1>
				</c:if>
				<c:if test="${!userEditViewFormBean.isCreateMode}">
					<a name="skip" > </a ><h1>Edit User</h1>
				</c:if>
			</div>
			<c:if test="${!userEditViewFormBean.isCreateMode}">
				<div class="col-md-6 text-right">
					<a id="changePasswordBtn" href="#" class="btn btn-warning btn-xs h1_button " role="button"  data-toggle="modal" data-target="#changePasswordModal"><i class="glyphicon glyphicon-lock"></i> Click here to Change Password</a>
				</div>
			</c:if>
		</div>

		<c:if test="${!userEditViewFormBean.isCreateMode}">
			<div class="border-radius-main-form">
				<div class="row">
					<div class="col-md-3">
						CPRS Verified
						<div class="txt_lable_lg">
							<c:choose><c:when test="${userEditViewFormBean.cprsVerified}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
						</div>
					</div>
					<div class="col-md-3">
						VistA DUZ
						<div class="txt_lable_lg">
							<c:out value="${userEditViewFormBean.vistaDuz}" />
						</div>
					</div>
					<div class="col-md-3">
						VistA VPID
						<div class="txt_lable_lg">
							<c:out value="${userEditViewFormBean.vistaVpid}" />
						</div>
					</div>
					<div class="col-md-3">
						VistA Division
						<div class="txt_lable_lg">
							<c:out value="${userEditViewFormBean.vistaDivision}" />
						</div>
					</div>
				</div>
	
				<hr />
	
				<div class="row">
					<div class="col-md-3">
						Date Password Changed
						<div class="txt_lable_lg">
							<fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${userEditViewFormBean.datePasswordChanged}" />
						</div>
					</div>
					<div class="col-md-3">
						Last Login Date
						<div class="txt_lable_lg">
							<fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${userEditViewFormBean.lastLoginDate}" />
						</div>
					</div>
					<div class="col-md-3">
						Date Created
						<div class="txt_lable_lg">
							<fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${userEditViewFormBean.dateCreated}" />
						</div>
					</div>
				</div>
			</div>
		</c:if>

		<div>
			<form:form modelAttribute="userEditViewFormBean" autocomplete="off" method="post">
				<form:hidden path="userId"/>
				<br />
				<form:errors path="*" element="div" cssClass="alert alert-danger" />
				<br />

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="loginId">Login ID *</form:label>
							<form:input path="loginId" 
								maxlength="30" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter the Login ID for the user" required="required" />
							<form:errors path="loginId" cssClass="help-inline"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="selectedRoleId">Role *</form:label>
							<form:select path="selectedRoleId" cssClass="form-control"  required="required">
								<form:option value="" label="Please Select a Role"/>
								<form:options items="${roleList}" itemValue="stateId" itemLabel="stateName"/>
							</form:select>
							<form:errors path="selectedRoleId" cssClass="help-inline"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="selectedUserStatusId">Status *</form:label>
							<form:select path="selectedUserStatusId" cssClass="form-control"  required="required">
								<form:option value="" label="Please Select a Status"/>
								<form:options items="${userStatusList}" itemValue="stateId" itemLabel="stateName"/>
							</form:select>
							<form:errors path="selectedUserStatusId" cssClass="help-inline"/>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="firstName">First Name *</form:label>
							<form:input path="firstName" 
								maxlength="30" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter the First Name"
                required="required"
                />
							<form:errors path="firstName" cssClass="help-inline"  required="required" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="middleName">Middle Name</form:label>
							<form:input path="middleName" 
								maxlength="30" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter the Middle Name" />
							<form:errors path="middleName" cssClass="help-inline"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="lastName">Last Name *</form:label>
							<form:input path="lastName" 
								maxlength="30" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter the Last Name" required="required" />
							<form:errors path="lastName" cssClass="help-inline"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="phoneNumber">Phone Number *</form:label>
							<form:input path="phoneNumber" 
								maxlength="10" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter a Phone Number"
                required="required"
                />
							<form:errors path="phoneNumber" cssClass="help-inline"  required="required" />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:label path="phoneNumberExt">Ext</form:label>
							<form:input path="phoneNumberExt" 
								maxlength="4" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Ext" />
							<form:errors path="phoneNumberExt" cssClass="help-inline" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="phoneNumber2">Alternate Phone Number</form:label>
							<form:input path="phoneNumber2" 
								maxlength="10" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter an alternate Phone Number" />
							<form:errors path="phoneNumber2" cssClass="help-inline"/>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<form:label path="phoneNumber2Ext">Ext</form:label>
							<form:input path="phoneNumber2Ext" 
								maxlength="4" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Ext" />
							<form:errors path="phoneNumberExt" cssClass="help-inline" />
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="emailAddress">Email Address *</form:label>
							<form:input path="emailAddress" 
								maxlength="50" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter an Email Address"
                required="required"
                type="email"
                />
							<form:errors path="emailAddress" cssClass="help-inline"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<form:label path="emailAddress2">Alternate Email Address</form:label>
							<form:input path="emailAddress2" 
								maxlength="50" 
								cssClass="inputStyle form-control"
								cssErrorClass="errorInputStyle  form-control"
								placeholder="Enter an alternate Email Address"
                
                type="email"
                />
							<form:errors path="emailAddress2" cssClass="help-inline"/>
						</div>
					</div>
				</div>

				<c:if test="${userEditViewFormBean.isCreateMode}">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<form:label path="password">Password *</form:label>
								<form:input path="password" 
									maxlength="50" 
									cssClass="inputStyle form-control"
									cssErrorClass="errorInputStyle  form-control"
									placeholder="Enter a Password" type="password"
                  required="required" 
                  />
								<form:errors path="password" cssClass="help-inline"/>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<form:label path="passwordConfirmed">Reenter Password *</form:label>
								<form:input path="passwordConfirmed" 
									maxlength="50" 
									cssClass="inputStyle form-control"
									cssErrorClass="errorInputStyle  form-control"
									placeholder="Reenter the Password" type="password"
                  required="required" 
                  />
								<form:errors path="passwordConfirmed" cssClass="help-inline"  />
							</div>
						</div>
					</div>
				</c:if>

				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
              <div class="strong">Program</div>
							<div class="program_list checkbox">
								<ul id="dev-table">
									<c:if test="${not empty programList}">
										<c:forEach var="item" items="${programList}">
											<li class="col-md-4"><form:checkbox path="selectedProgramIdList" value="${item.stateId}" label="${item.stateName}" /></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>

				<div class="clear-fix"></div>	
				<hr />

				<div class="row">
					<div class="col-md-12">
						<button type="submit" id="saveButton" name="saveButton" class="btn btn-primary">Save</button>
						<a href="userListView" class="btn btn-default btn-md" role="button"  id="cancelButton" name="cancelButton">Cancel</a>
					</div>
				</div>
			</form:form>
		</div>

	</div>

	  <!-- Modal for ChangeP assword-->
    <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="changePasswordLabel">Change Password</h4>
          </div>
          <form id="changePasswordForm" name="changePasswordForm" autocomplete="off">
          <div class="modal-body">
            <div class="row">
              <div class="col-md-12">
                <div class="form-group">
                  <div class="alert hide" id="verification_message" ></div>
                    <div class="row" id="changePasswordInputBlock">
                      <div class="col-md-6">
                        <div class="form-group">
                          <label for="chnPassword" >Password *</label>
                          <input type="password" name="chnPassword" id="chnPassword" class="form-control" maxlength="30"  placeholder="Enter Password" required="required" >
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-group">
                          <label for="chnPasswordConfirmed" >Reenter Password *</label>
                          <input type="password" name="chnPasswordConfirmed" id="chnPasswordConfirmed" class="form-control" maxlength="30" placeholder="Reenter Password"  required="required" >
                        </div>
                      </div>
                    </div>
                </div>
              </div>	
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" id="btnSaveChangePassword" data-text-loading="Loading...">Save</button>
            <button type="button" class="btn btn-default" data-dismiss="modal" id="btn_close" >Cancel</button>
          </div>
          </form>			
        </div>
      </div>
    </div>

	<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/adminTab/userEditView.js" />"></script>

</html>