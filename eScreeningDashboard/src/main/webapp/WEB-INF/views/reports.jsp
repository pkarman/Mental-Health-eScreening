<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Reports</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/adminDashboardTabs.js" />"></script>
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
<body>
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
		<div class="col-md-12"><a name="skip" > </a ><h1>Reports</h1></div>
	</div>
	<div class="clear-fix"></div>

	<div id="mainContent">
		<table class="table table-striped table-hover" summary="Reports List View">
			<thead>
				<tr>
					<th class="ui-state-default text-left">Report Name</th>
					<th class="ui-state-default text-right col-md-1" >Action</th>
				</tr>
			</thead>
			<tr>
				<td class="text-left">Individual Statistic Reports</td>
				<td class="text-right"><a href="individualStatisticsReports" class="btn btn-primary btn-xs" name="Individual Statistic Reports" title="Individual Statistic Reports"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>
			<tr>
				<td class="text-left">Average Scores for Veterans by Clinic Report</td>
				<td class="text-right"><a href="averageScoresForPatientsByClinic" class="btn btn-primary btn-xs" name="View Average Scores for Veterans by Clinic Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>

			<tr>
				<td class="text-left bg_color_subheader"><h4>Clinic Statistic Reports</h4></td>
				<td class="text-righ bg_color_subheader"></td>
			</tr>


			<tr>
				<td class="text-left">Most Common Types of Alerts and Percentages Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPartIIMostCommonTypesOfAlertsPercentagesReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports PartIV: Average Time Per Module Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>


			<tr>
				<td class="text-left">Positive Screens Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPartVIPositiveScreensReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports PartIV: Average Time Per Module Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>


			<tr>
				<td class="text-left">List Of Top 20 Most Skipped Questions Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPartIIIList20MostSkippedQuestionsReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports PartIV: Average Time Per Module Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>
			
			<tr>
				<td class="text-left">eScreening Batteries Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPart1eScreeningBatteriesReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports Part 1: eScreening Batteries Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>
			<tr>
				<td class="text-left">Demographics Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPartVDemographicsReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports Part V: Demographics Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>
			<tr>
				<td class="text-left">Average Time Per Module Report</td>
				<td class="text-right"><a href="clinicStatisticReportsPartIVAverageTimePerModuleReport" class="btn btn-primary btn-xs" name="View Clinic Statistic Reports PartIV: Average Time Per Module Report"><span class="glyphicon glyphicon-chevron-right"></span> View </a></td>
			</tr>


		</table>
	</div>
</div>

	<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/reports/reportsCommon.js" />"></script>

</body>
</html>