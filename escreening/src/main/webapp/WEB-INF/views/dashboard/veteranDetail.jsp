<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!doctype html>
<html>
<head>
	<title>Veteran Search</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="resources/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
<link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css"
	rel="stylesheet" type="text/css">
<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
<link rel="SHORTCUT ICON" href="resources/images/valogo.ico"
	type="image/x-icon" />
<link href="resources/css/partialpage/standardtopofpage-dashboard.css"
	rel="stylesheet" type="text/css">
<link href="resources/css/jquery.dataTables.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/partialpage/menu-partial.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/veteranSearch.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/formButtons.css" rel="stylesheet"
	type="text/css" />

<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">



<script type="text/javascript">
	$(document).ready(function() {
		tabsLoad("createBattery");
	});
</script>

<style type="text/css">
	.badge{
		background-color: #EBEBEB;
		padding: 4px 7px;
		color: #000;
	}
	
	.label{
		font-size: inherit;
	}
</style>


</head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">
		<%@ include
			file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp"%>
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

<form:form modelAttribute="veteranDetailFormBean" method="post">

		<div class="row">
			<div class="col-md-12">


				<a name="skip" > </a ><h1>Veteran Detail</h1>

				<c:if test="${!isCprsVerified}">
					<div class="alert alert-danger">
						Your VistA account information needs to be verified before you can save or read any data from VistA. 
						Some data may not be available and some features that rely on VistA may not be enabled. 
					</div>
				</c:if>

				<c:if test="${empty veteranDetailFormBean.veteranId}">
					<div class="alert alert-info">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&times;</button>
						This veteran only exists in VistA. If you would like to create a
						battery for this veteran, please <strong>import the
							veteran from VistA</strong> first.
								<input id="importVeteranButton" name="importVeteranButton"
												value="Import Veteran from VistA" type="submit"
												class="btn btn-primary" />
					</div>
				</c:if>

				<c:if test="${empty veteranDetailFormBean.veteranIen}">
					<div class="alert alert-info">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						This veteran has not been mapped to a VistA record. Use the <strong>Map
							to VistA Record</strong> link to map the veteran to a VistA record in
						order to view VistA data, such as appointments and clinical
						reminders.
						
					
					</div>
				</c:if>

				<c:if test="${(!ibc) && (veteranDetailFormBean.hasActiveAssessment)}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						A <strong>clean</strong> or <strong>an incomplete</strong> battery
						already exists for this veteran. 
					</div>
				</c:if>
				<c:if test="${ibc}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						Battery creation successful.
					</div>
				</c:if>

				<div class="row">
					<div class="col-md-6">
						<h2>Veteran Information</h2>
					</div>
					<div class="col-md-6 text-right h1_button_12">


						Date Refreshed from VistA  
							<span class="badge badge_content">
								<fmt:formatDate type="both" pattern="MM/dd/yyyy HH:mm:ss" value="${veteran.dateRefreshedFromVista}" /> 

								<c:if test="${isCprsVerified}">
									<c:if test="${not empty veteran.veteranIen}">
										<s:url var="refreshDataUrl" value="/dashboard/veteranDetail"
											htmlEscape="true">
											<s:param name="vid" value="${veteran.veteranId}" />
											<s:param name="vien" value="${veteran.veteranIen}" />
											<s:param name="frefresh" value="1" />
										</s:url>
										<a href="${refreshDataUrl}" class="btn btn-primary btn-xs active" role="button">Refresh Data from VistA</a> 
									</c:if>
								</c:if>
							</span>

							<c:if test="${empty veteran.veteranIen}">
								<span class="label label-success">Not mapped to VistA record.</span>
							</c:if>
					</div>
				</div>

				<div class="border-radius-main-form">
					<div class="row">
						
						<div class="col-md-6">
							Name (Last, First Middle)
							<div class="txt_lable_lg">
								<c:out value="${veteran.fullName}" />
							</div>
						</div>
						<div class="col-md-3">
							Status
							<div class="txt_lable_lg">
								<c:choose>
								      <c:when test="${(!empty veteran.veteranId) && (empty veteran.veteranIen)}">
								      	<div>Only Exists in DB</div>
								      </c:when>
									  <c:when test="${(!empty veteran.veteranIen) && (empty veteran.veteranId)}">
								      	<div>Only Exists in VistA</div>
								      </c:when>
								       <c:when test="${(!empty veteran.veteranIen) && (!empty veteran.veteranId)}">
								      	<div>Mapped Veteran</div>
								      </c:when>
								      <c:otherwise>
								      </c:otherwise>
								</c:choose>	
							</div>
						</div>
						<div class="col-md-2">
							Date of Birth
							<div class="txt_lable_lg">
								<c:if test="${veteran.isSensitive}"><c:out value="XX/XX/XXXX"/></c:if>
								<c:if test="${!veteran.isSensitive}"><fmt:formatDate type="date" pattern="MM/dd/yyyy"
									value="${veteran.birthDate}" /></c:if>
							</div>
						</div>
						<div class="col-md-1 text-right">
							SSN-4
							<div class="txt_lable_lg text-right">
							<c:if test="${veteran.isSensitive}"><c:out value="XXXX"/></c:if>
							<c:if test="${!veteran.isSensitive}"><c:out value="${veteran.ssnLastFour}" /></c:if>
							</div>
						</div>

					</div>
					<hr />
					<div class="row">
						<div class="col-md-2">
							Phone
							<div class="txt_lable_md">
								<c:out value="${veteran.phone}" />
							</div>
						</div>
						<div class="col-md-2">
							Work
							<div class="txt_lable_md">
								<c:out value="${veteran.workPhone}" />
							</div>
						</div>
						<div class="col-md-2">
							Cell
							<div class="txt_lable_md">
								<c:out value="${veteran.cellPhone}" />
							</div>
						</div>
						<div class="col-md-4">
							Email
							<div class="txt_lable_md">
								<c:out value="${veteran.email}" />
							</div>
						</div>
						<div class="col-md-2 text-right">
							VistA IEN
							<div class="txt_lable_md">
								<c:out value="${veteran.veteranIen}" />
								
								<c:if test="${isCprsVerified}">
									<c:if test="${not empty veteran.veteranId}">
										<s:url var="mapVeteranToVistaUrl" value="/dashboard/mapVeteranToVistaRecord" htmlEscape="true">
											<s:param name="vid" value="${veteran.veteranId}" />
										</s:url>
										<div class="text-left">
											<a href="${mapVeteranToVistaUrl}">Map to VistA Record</a>
										</div>
									</c:if>
								</c:if>
							</div>
						</div>

					</div>
				</div>


				<br />


				<div class="row">
					<div class="col-md-6">

						<h3>Upcoming Appointment List</h3>
						<table class="table table-striped table-hover" summary="Upcoming Appointment List">
							<thead>
								<tr>
									<th scope="col">Date</th>
									<th scope="col">VistA Clinic</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty veteranAppointmentList}">
									<c:forEach var="item" items="${veteranAppointmentList}">
										<tr>
											<td><fmt:formatDate type="both"
													pattern="MM/dd/yyyy HH:mm" value="${item.appointmentDate}" /></td>
											<td><c:out value="${item.clinicName}" /></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty veteranAppointmentList}">
								<tr>
									<td colspan="2">No Upcoming Appointment Found.</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<%--<div class="col-md-6">--%>


						<%--<h3>Clinical Reminder List</h3>--%>
						<%--<table class="table table-striped table-hover" summary="Clinical Reminder List">--%>
							<%--<thead>--%>
								<%--<tr>--%>
									<%--<th scope="col">Date</th>--%>
									<%--<th scope="col">Name</th>--%>
								<%--</tr>--%>
							<%--</thead>--%>
							<%--<tbody>--%>
								<%--<c:if test="${not empty veteranClinicalReminderList}">--%>
									<%--<c:forEach var="item" items="${veteranClinicalReminderList}">--%>
										<%--<tr>--%>

											<%--<td><c:out value="${item.dueDateString}" /></td>--%>
											<%--<td><c:out value="${item.name}" /></td>--%>

										<%--</tr>--%>
									<%--</c:forEach>--%>
								<%--</c:if>--%>
								<%--<c:if test="${empty veteranClinicalReminderList}">--%>
								<%--<tr>--%>
									<%--<td colspan="2">No Clinical Reminder Found.</td>--%>
								<%--</tr>--%>
								<%--</c:if>--%>
							<%--</tbody>--%>
						<%--</table>--%>
						<%----%>
					<%--</div>--%>
					<%----%>
				</div>
				
				<hr />
				<div class="row">
				<div class="col-md-12">

						<div class="row">
							<div class="col-md-6">
								<h3>Batteries</h3>
							</div>
							<div class="col-md-6 text-right h1_button_16">
								
									<form:hidden path="veteranId" />
									<form:hidden path="veteranIen" />
									<form:hidden path="hasActiveAssessment" />

									<c:choose>
										<c:when test="${empty veteranDetailFormBean.veteranId}">
											
											<input id="createAssessmentButton"
												name="createAssessmentButton" value="Create New Assessment"
												type="submit" class="btn btn-primary" disabled="disabled" />
										</c:when>
										<c:otherwise>
						
											<input id="createAssessmentButton"
												name="createAssessmentButton" value="Create New Battery"
												type="submit" class="btn btn-primary" />
										</c:otherwise>
									</c:choose>

								
							</div>
						</div>





						<table class="table table-striped table-hover" summary="Batteries Table">
							<thead>
								<tr>
									<th scope="col">Date Created</th>
									<th scope="col">Status</th>
									<th scope="col">Battery</th>
									<th scope="col">Created By (Last, First Middle)</th>
									<th scope="col" class="text-right">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty veteranAssessmentList}">
									<c:forEach var="item" items="${veteranAssessmentList}">
										<tr>
											<td><fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${item.dateCreated}" /></td>
											<td><c:out value="${item.assessmentStatus}" /></td>
											<td><c:out value="${item.batteryName}" /></td>
											<td><c:out value="${item.createdByUserLastName}" />, <c:out value="${item.createdByUserFirstName}" /> <c:out value="${item.createdByUserMiddleName}" /></td>
											<td class="text-right">
												<s:url var="editVeteranAssessmentUrl" value="/dashboard/editVeteranAssessment" htmlEscape="true">
													<s:param name="vaid" value="${item.veteranAssessmentId}" />
												</s:url>
												<a href="${editVeteranAssessmentUrl}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-chevron-right"></span> View</a>
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
		</form:form>
	</div>

	<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>

<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/veteranDetails.js" />"></script>
</body>
</html>