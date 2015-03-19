<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Reports | Clinic Statistic Reports: Average Time Per Module Report</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/adminDashboardTabs.js" />"></script>
    <script type="text/javascript" src="resources/editors/vendors/lodash/lodash.min.js"></script>
    <script type="text/javascript" src="resources/js/angular/angular.min.js"></script>
    <script src="resources/js/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		
	<link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon" />
	<link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon" />
	
	<link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css" />
	
	<!-- Bootstrap -->
	<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />

	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker/datepicker.css" />">
</head>
<body ng-app="reportsModule" ng-controller="mostCommonTypesOfAlertsPercentagesCtrl">
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


<div class="container left-right-shadow">
	<div class="row">
		<div class="col-md-12"><h1>Average Time Per Module Report</h1></div>
	</div>
	
	<div class="clear-fix"></div>

	<div id="avgScoresForPatientsByClinic">
		<div class="border-radius-main-form gray-lighter">		
			<form name="reportForm" novalidate>
				<div class="row">

					<div class="col-md-3">
						<div class="checkbox_group_label"><strong>Select VistA Clinics</strong></div>
						<div class="clearfix"></div>
						<div class="border-radius-main-form gray-lighter">
							
							
							
							
							
							<!-- Program Evaluator Role only 602 / 603
							<div>
								<div class="checkbox">
									<input type="checkbox" class="selectAllClinic" id="selectAllClinic" name="selectAllClinic" ng-change="selectAllClinic()" ng-model="report.selectAllClinic" data-cbgroup="checkbox_group_clinic" />
									<label class="labelAlign" for="selectAllClinic" data-cbgroup="checkbox_group_clinic">Select All Clinics</label>
								</div>
							</div>
							<hr class="hr_margin_5">
							-->
							
							
							
							

							
							<div class="vertical_scoll_list_b module_list">
								 <ul ng-repeat="clinic in clinicsList">
									<li><div class="checkbox"><input type="checkbox" id="module_{{clinic.clinicId}}" checklist-model="report.clinicsList" checklist-value="clinic.clinicId" name="module_{{clinic.clinicId}}" class="checkbox_group_clinic" checkbox-group  />  <label class="labelAlign" for="module_{{clinic.clinicId}}">{{clinic.clinicName}}</label></div></li>
								 </ul>
							</div>
						</div>
					</div>
										
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group"   show-errors='{showSuccess: true}'>
									<label class="labelAlign" for="fromDate">From Date</label>
								  <div class="input-group date" id="fromDateGroup">
									<input type="text" id="fromDate" class="dateField form-control" 
									 name="fromDate" ng-model="report.fromDate" maxlength="10" 
									 placeholder="MM/DD/YYYY" autocomplete="off" required />
									 <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
								 </div>
								 <p class="help-block" ng-if="reportForm.fromDate.$error.required">The From Date is required</p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group"  show-errors='{showSuccess: true}'>
									  <label class="labelAlign" for="toDate">To Date</label>
									<div class="input-group date" id="toDateGroup">
									  <input type="text" id="toDate" class="form-control"
									   name="toDate" ng-model="report.toDate" maxlength="10" 
									   placeholder="MM/DD/YYYY" autocomplete="off" required  />
									   <div class="input-group-addon"><i class="glyphicon glyphicon-calendar" title="Click to open calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
									</div>
									<p class="help-block" ng-if="reportForm.fromDate.$error.required">The To Date is required</p>									
								</div>
							</div>
						</div>								
					</div>
					
					

				</div>
				<hr />
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<button type="submit" class="btn btn-primary"  ng-click="save()">Generate  PDF Report</button>
							<button type="button" class="btn btn-default" id="backToReports">Back to Reports</button>
						</div>
					</div>
				</div>
			</form>
		</div>		
	</div>
	
</div>

<div class="graphWrapper hide" id="graph_1"></div>
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/reports/reportsCommon.js" />"></script>
<script src="<c:url value="/resources/js/d3/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/d3/chart.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/js/d3/chart.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/bower_components/checklist-model/checklist-model.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/reports/reportsModule.js" />"></script></body>
</html>