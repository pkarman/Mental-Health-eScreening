<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html ng-app="selectVeteransFormApp">
<head>
	<title>Batch Complete</title>
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
        		<h1>Batch Complete</h1>
				

					

					<c:set var="countSucceed" value="0" scope="page" />
					<c:set var="countError" value="0" scope="page" />
					<c:forEach var="item" items="${veterans}">
							<c:choose>
								<c:when test="${item.succeed == 'true'}">
									<c:set var="countSucceed" value="${countSucceed + 1}" scope="page"/>
								</c:when>
								<c:otherwise>
									<c:set var="countError" value="${countError + 1}" scope="page"/>
								</c:otherwise>								
							</c:choose>
					</c:forEach>



					<c:if test="${countSucceed > 0}">
						<div class="alert alert-success">Battery Creation Successful for the Below Veterans.</div>
					</c:if>
					<c:if test="${countError > 0}">
  						<div class="alert alert-danger"> Battery Creation Failed for the Below Veterans.</div>
					</c:if>

					<c:if test="${callResult.hasError}">
					  <div class="alert alert-danger">
						<!-- MESSAGE | Battery Creation Failed for the Below Veterans. -->
						<c:out value="${callResult.userMessage}"/>
					  </div>
					  <div class="panel-danger-system">
						<div class="panel-group" id="accordion">
						  <div class="panel panel-default">
							<div class="panel-heading">
							  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> System Error <span class="label label-danger">Click here for more error details</span> </a> </h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse">
							  <div class="panel-body">
								<c:out value="${callResult.systemMessage}"/>
							  </div>
							</div>
						  </div>
						</div>
					  </div>
					</c:if>
					<c:if test="${!callResult.hasError}">
					  <c:if test="${not empty callResult.userMessage}">
						<div class="alert alert-success">
						  <!-- MESSAGE | Battery Creation Successful for the Below Veterans. -->
						  <c:out value="${callResult.userMessage}"/>
						</div>
					  </c:if>
					</c:if>
		
		
					
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					  <div class="panel panel-default">
						<div class="panel-heading" role="tab" id="headingOne">
						  <h3 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
							 <strong>Selected Veterans</strong>
							</a>
						  </h3>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						  <div class="panel-body">

								<div id="mainContent">
									<table id="selectVeteransTable" name="selectVeterans" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover" width="100%" summary="Export Data Table">
									  <thead>
										<tr>
											<th scope="col" class="col-md-1">SSN-4</th>
											<th scope="col" class="col-md-2">Last Name</th>
											<th scope="col" class="col-md-2">First Name</th>
											<th scope="col" class="col-md-2">Middle Name</th>
											<th scope="col" class="col-md-1">Date of Birth</th>
											<th scope="col" class="col-md-1">Appointment Date</th>
											<th scope="col" class="col-md-1">Appointment Time</th>
											<th scope="col" class="col-md-1">Clinical Reminders</th>
											<th scope="col" class="col-md-1 text-center">Status</th>
											<th scope="col" class="col-md-1 text-center">Action</th>
										</tr>
									  </thead>
										<tbody>
											<c:if test="${not empty veterans}">
												<c:forEach var="item" items="${veterans}">
													<tr>
														<td class="text-left"><c:out value="${item.vet.ssnLastFour}" /></td>
														<td><c:out value="${item.vet.lastName}" /></td>
														<td><c:out value="${item.vet.firstName}" /></td>
														<td><c:out value="${item.vet.middleName}" /></td>
														<td><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${item.vet.birthDate}" /></td>
														<td class="text-right"><c:out value="${item.vet.apptDate}" /></td>
														<td class="text-right"><c:out value="${item.vet.apptTime}" /></td>
														<td class="text-center text-capitalize"><c:out value="${item.vet.dueClinicalReminders}" /></td>
														<td class="text-center text-capitalize <c:choose><c:when test="${item.succeed == 'true'}">item-label-success</c:when><c:otherwise>item-label-error</c:otherwise></c:choose>">
														<c:choose>
														<c:when test="${item.succeed == 'true'}">
																Succeed
															</c:when>
															<c:otherwise>
																Failed
															</c:otherwise>
														</c:choose>
														
														</td>
														<td class="text-right">
														<c:if test="${isCprsVerified}">
															<c:if test="${not empty item.vet.veteranId}">
																<s:url var="mapVeteranToVistaUrl" value="/dashboard/veteranDetail" htmlEscape="true">
																	<s:param name="vid" value="${item.vet.veteranId}" />
																</s:url>
																<div class="text-left">
																	<a href="${mapVeteranToVistaUrl}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-chevron-right"></span> View</a>
																</div>
															</c:if>
														</c:if>		
														</td>		
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
						
									</table>
								</div>
						  </div>
						</div>
					  </div>
					</div>			

					<div class="pull-right">
						<button id="dashbaordBtn" name="dashbaordBtn" type="submit" class="btn btn-primary">Dashboard</button>
						<button id="returnToAppointmentsBtn" name="returnToAppointmentsBtn" type="submit" class="btn btn-primary">Return to Appointments</button>
					</div>	
			</div>
		</div>
	</div>

	<!-- Do we need this div?? -->
	<div class="mainDiv"></div>

	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dashboard/batchComplete.js?v=1" />"></script>

    <%@ include file="/WEB-INF/views/partialpage/footer.jsp"%>
</body>
</html>