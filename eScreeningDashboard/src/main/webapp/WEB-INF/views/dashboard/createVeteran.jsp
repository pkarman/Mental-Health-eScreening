<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
	<script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
	<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
	<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" />
	<link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
	<link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
	<link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/formButtons.css" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/js/bootstrap/css/plugins/bootstrap-datetimepicker.css" />" rel="stylesheet" type="text/css" />
		
	<title>Veteran Search</title>
  	<!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
</head>
<body>
  <!-- Do we need this div?? -->
  <a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">
		<%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp"%>
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
	<div class="clearfix"></div>

	<div class="container left-right-shadow">
		<div class="row">
			<div class="col-md-12">

				<a name="skip" > </a >
        <h1>Create Veteran</h1>
				<fieldset>
					<!--  <legend>Create Veteran</legend>  -->
					<form:form modelAttribute="createVeteranFormBean"
						autocomplete="off" method="post">

						<form:errors path="*" element="div" cssClass="alert alert-danger" />
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="firstName">First Name</form:label>
									<form:input path="firstName" maxlength="30"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran First Name" />
									<form:errors path="firstName" cssClass="help-inline" />

								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="middleName">Middle Name</form:label>
									<form:input path="middleName" maxlength="30"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Middle Name" />
									<form:errors path="middleName" cssClass="help-inline" />
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="lastName">Last Name *</form:label>
									<form:input path="lastName" maxlength="30"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Last Name" />
									<form:errors path="lastName" cssClass="help-inline" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="ssnLastFour">SSN-4 *</form:label>
									<form:input path="ssnLastFour" maxlength="4"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Last SSN-4" />
									<form:errors path="ssnLastFour" cssClass="help-inline" />
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="birthDateString">Date of Birth</form:label>
									<div class="input-group date form_date col-md-12" data-date=""
										data-date-format="mm/dd/yyyy" data-link-field="dtp_birthDate"
										data-link-format="mm-dd-yyyy">
										<form:input path="birthDateString" maxlength="10" cssClass="form-control" cssErrorClass="form-control" placeholder="Enter Veteran Date of Birth mm/dd/yyyy" />
										<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
										<div class="clearfix"></div>
										
									</div>
									<form:errors path="birthDateString" cssClass="help-inline" />
									<input type="hidden" id="dtp_birthDate" value="" /><br />
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="email">Email</form:label>
									<form:input path="email" maxlength="255"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Email" />
									<form:errors path="email" cssClass="help-inline" />
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="phone">Phone</form:label>
									<form:input path="phone" maxlength="10"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Phone Number. Ex: 1234567890" />
									<form:errors path="phone" cssClass="help-inline" />
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="officePhone">Work</form:label>
									<form:input path="officePhone" maxlength="10"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Work Phone Number. Ex: 1234567890" />
									<form:errors path="officePhone" cssClass="help-inline" />
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="cellPhone">Cell</form:label>
									<form:input path="cellPhone" maxlength="10"
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter Veteran Cell Phone Number. Ex: 1234567890" />
									<form:errors path="cellPhone" cssClass="help-inline" />
								</div>
							</div>
						</div>


						<br />

						<input id="saveButton" name="saveButton" value="Save"
							type="submit" class="btn btn-primary" />
						<input id="cancelButton" name="cancelButton" value="Cancel"
							type="submit" class="btn btn-default" />
						<br>
						<br>
					</form:form>
				</fieldset>

			</div>
		</div>
	</div>


	<!-- Do we need this div?? -->
	<div class="mainDiv"></div>


	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/plugins/bootstrap-datetimepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="resources/js/dashboard/createVeteran.js" />"></script>

    <%@ include file="/WEB-INF/views/partialpage/footer.jsp"%>
</body>
</html>