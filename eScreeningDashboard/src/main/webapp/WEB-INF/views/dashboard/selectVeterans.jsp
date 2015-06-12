<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
<head>
    <title>Veterans Search</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
	<script type="text/javascript" src="resources/js/angular/angular.min.js"></script>
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>

    <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
    <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
    <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="resources/css/bootstrap-datepicker/datepicker.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="resources/js/lib/ladda-bootstrap/dist/ladda-themeless.min.css" />">

	<!-- BEGIN syntax highlighter -->
	<script type="text/javascript" src="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/sh/shCore.js"></script>
	<script type="text/javascript" src="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/sh/shBrushJScript.js"></script>
	<link type="text/css" rel="stylesheet" href="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/sh/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/sh/shThemeDefault.css"/>
	<script type="text/javascript">
		SyntaxHighlighter.all();
	</script>
	<!-- END syntax highlighter -->
	<script type="text/javascript" src="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script type="text/javascript" src="resources/vendor-libs/jquery.searchabledropdown-v1.0.8/jquery.searchabledropdown-1.0.8.src.js"></script>
	
	<!-- Bootstrap -->
	<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">

	<link rel="stylesheet" type="text/css" href="resources/js/lib/silviomoreto-bootstrap-select/dist/css/bootstrap-select.css">
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

	<div class="clearfix"></div>

	<div class="container left-right-shadow" >
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-12"><a name="skip" > </a ><h1>Select Veteran</h1></div>
				</div>	
				<c:if test="${!isCprsVerified}">
					<div class="alert alert-danger">
						Your VistA account information needs to be verified before you can save or read any data from VistA. 
						Search result will not include any data from VistA. 
					</div>
				</c:if>
				
				<div class="clearfix"></div>
				<ul class="nav nav-tabs" role="tablist">
					<li><a href="selectVeteran" role="tab"><strong>Create Assessment for Unscheduled Visit</strong></a></li>
					<li class="active"><a href="selectVeterans" role="tab"><strong>Create Assessment(s) for Appointment(s)</strong></a></li>
				</ul>
				<br>          
					
				<form:form modelAttribute="selectVeteranFormBean" autocomplete="off" method="post">
					<div class="border-radius-main-form gray-lighter">
						<h2>Search Criteria</h2>



	
							
						<form:errors path="*" element="div" cssClass="alert alert-danger" />

						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<form:label path="selectedClinic">VistA Clinic *</form:label>
									<form:select path="selectedClinic" cssClass="form-control"   id="myselect" required="true"  data-live-search="true" title="Please select a VistA Clinic ...">
										<form:option value="" label="Please Select VistA Clinic"/>
										<form:options items="${clinics}" itemValue="stateId" itemLabel="stateName" />
									</form:select>
									<form:errors path="selectedClinic" cssClass="help-inline"/>						 
								</div>
							</div>			
							
							<div class="col-md-3">
								<div class="form-group">
										<form:label path="startDate">From Date *</form:label>
									<div class="input-group date" id="fromAssessmentDateGroup">
										<form:input path="startDate" maxlength="10" cssClass="form-control startDate" cssErrorClass="form-control" placeholder="MM/DD/YYYY" required="true"   />
										<form:errors path="startDate"  cssClass="help-inline"/>
										<div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
									</div>
									<div class="help-inline hide startDateError">Enter a valid date</div>
								</div>
							</div>
	
							<div class="col-md-3">
								<div class="form-group">
									<form:label path="endDate">To Date *</form:label>
									<div class="input-group date" id="toAssessmentDateGroup">
										<form:input path="endDate" maxlength="10" cssClass="form-control endDate" cssErrorClass="form-control" placeholder="MM/DD/YYYY"  required="true" />
										<form:errors path="endDate"  cssClass="help-inline"/>
										<div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
									</div>
									<div class="help-inline hide endDateError">Enter a valid date</div>
								</div>							
							</div>
							
							<div class="col-md-2">
								<div class="form-group">
									<label for="searchButton">&nbsp;</label>
									<input id="searchButton" name="searchButton" value="Search" type="submit" class="btn btn-primary form-control" />
								</div>
							</div>
	
							<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</form:form>
						
				<c:if test="${isPostBack}">
					<h2>Search Result</h2>
					<input type='checkbox' name='selectAll' id="selectAll" /> <label for="selectAll">Select All</label>
					<form:form method="post"  modelAttribute="editVeteranAssessmentFormBean">
					<input name="clinicId" value="${param.selectedClinic}" id="clinicId" type="hidden">
					<table class="table table-striped table-hover" summary="Search Result Table">
						<thead>
							<tr>
								<th scope="col" class="col-md-2">Select Veteran</th>
								<th scope="col" class="col-md-2">Last Name</th>
								<th scope="col" class="col-md-2">First Name</th>
								<th scope="col" class="col-md-2">Middle Name</th>
								<th scope="col" class="col-md-2">Appointment Date</th>
								<th scope="col" class="col-md-2 text-right">Appointment Time</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<c:if test="${empty searchResult}">
									<td colspan="6">No record found</td>
								</c:if>
								<c:if test="${not empty searchResult}">
									<td colspan="6"><c:out value="${searchResultListSize}" /> record(s) found</td>
								</c:if>
							</tr>
						</tfoot>
						<tbody>
							<c:if test="${not empty searchResult}">
								<c:forEach var="item" items="${searchResult}">
									<tr>
										<td class="text-left"><input type='checkbox' name='vetIens' value="${item.veteranIen}" class="vetIensCheckbox" /></td>
										<td><c:out value="${item.lastName}" /></td>
										<td><c:out value="${item.firstName}" /></td>
										<td><c:out value="${item.middleName}" /></td>
										<td class="text-right"><c:out value="${item.apptDate}" /></td>
										<td class="text-right"><c:out value="${item.apptTime}" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<div class="row">
						<div class="col-md-12">					
							<button id="createAssessmentButton" name="selectVeterans" type="submit" class="btn btn-primary pull-right  ladda-button createAssessmentButton" data-style="expand-right" disabled>
							<span class="ladda-label">Select Veterans</span>
							</button>
						</div>
					</div>
					</form:form>
				</c:if>
				<br/><br/>
			</div>
		</div>
	</div>
</div>

	<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
	
	<!-- Scripts -->
	<script type="text/javascript" src="<c:url value="/resources/js/lib/silviomoreto-bootstrap-select/js/bootstrap-select.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script src="resources/js/lib/silviomoreto-bootstrap-select/js/bootstrap.min.js"></script>
	<script src="resources/js/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		
	<script src="resources/js/lib/ladda-bootstrap/dist/spin.min.js"></script>
	<script src="resources/js/lib/ladda-bootstrap/dist/ladda.min.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dashboard/selectVeterans.js?v=2" />"></script>
</body>	
</html>