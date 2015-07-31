<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!doctype html>
<html ng-app="veteranSearchFormApp">
<head>
<title>Veteran Search</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css"
	rel="stylesheet" type="text/css">
<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
<link rel="SHORTCUT ICON" href="resources/images/valogo.ico"
	type="image/x-icon" />

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
<link href="resources/vendor-libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="resources/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		tabsLoad("veteranSearch");
	});
</script>
</head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">
		<%@ include
			file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp"%>
		<div class="navbar navbar-default navbar-update" role="navigation">
			<div class="container bg_transparent">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<nav class="navbar-collapse collapse">
					<ul class="nav navbar-nav" id="tabs">

					</ul>
				</nav>
				<!--/.nav-collapse -->
			</div>
		</div>




		<div ng-controller="veteranSearchController" class="mainDiv">

			<div class="container left-right-shadow">
				<a name="skip" > </a >
        <h1>Veteran Search</h1>
				<div class="row">
					<div class="col-md-12">
						<div id="leftContent_delete"
							class="border-radius-main-form gray-lighter">
							<form id="veteranSearchForm" name="veteranSearchForm" novalidate
								ng-submit="searchDatabase()">

								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label class="labelAlign" for="veteranId">Veteran ID</label>
											<br /> <input type="number" class="fieldAlign form-control"
												id="veteranId" name="veteranId" maxlength="10" min="0"
												max="99999999" ng-model="veteranSearchFormBean.veteranId"
												autocomplete="off" placeholder="Enter Veteran ID" /> <span
												class="error"
												ng-show="veteranSearchForm.veteranId.$invalid && veteranSearchForm.submitted">*</span>
										</div>
									</div>
									<div class="col-md-5">
										<div class="form-group">
											<label class="labelAlign" for="lastName">Last Name</label> <br />
											<input type="text" class="fieldAlign form-control"
												id="lastName" name="lastName" maxlength="30"
												ng-model="veteranSearchFormBean.lastName" autocomplete="off"
												placeholder="Enter Last Name" /> <span class="error"
												ng-show="veteranSearchForm.lastName.$invalid && veteranSearchForm.submitted">*</span>
										</div>
									</div>
									<div class="col-md-2">
										<div class="form-group">
											<label class="labelAlign" for="ssnLastFour">SSN-4</label> <br />
											<input type="text" class="fieldAlign form-control"
												id="ssnLastFour" name="ssnLastFour" maxlength="4"
												ng-model="veteranSearchFormBean.ssnLastFour"
												autocomplete="off" placeholder="Enter SSN-4" /> <span
												class="error"
												ng-show="veteranSearchForm.ssnLastFour.$invalid && veteranSearchForm.submitted">*</span>
										</div>
									</div>
									<div class="col-md-2">
										<div class="form-group">
											<div class="label_margin"></div>
											<button type="submit"
												class="submitButton_delete  btn btn-primary form-control">Search
												Veteran</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="clear-fix"></div>
				<br />
				<div class="row">
					<div class="col-md-12">
						<div id="mainContent">
							<div align="right">
								<a href="#" class="btn btn-default btn-xs"
									ng-click="exportDataGrid('<c:url value="/dashboard/veteranSearch/services/veterans/search/report/csv" />')"><i class="fa fa-file-o" title="CSV File"></i> CSV </a>
                  
                  <a href="#" class="btn btn-default btn-xs"
									ng-click="exportDataGrid('<c:url value="/dashboard/veteranSearch/services/veterans/search/report/xls" />')">
                  <i class="fa fa-file-excel-o" title="Excel File"></i> &nbsp;Excel</a>
                  <a href="#" class="btn btn-default btn-xs"
									ng-click="exportDataGrid('<c:url value="/dashboard/veteranSearch/services/veterans/search/report/pdf" />')">
                  <i class="fa fa-file-pdf-o" title="PDF"></i> &nbsp;PDF </a>
							</div>
							<table class="jqueryDataTable table table-striped  table-hover"
								id="veteranSearchTable" name="veteranSearchTable"
								report-table="overrideOptions"
								fn-data-callback="getDataForSearch "  summary="Veteran Search Table">
								<thead>
									<tr>
										<th scope="col">ID</th>
										<th scope="col">Veteran</th>
										<th scope="col">SSN-4</th>
										<th scope="col">Email</th>
										<th scope="col">Gender</th>
										<th scope="col">Last Assessment Date</th>
										<th scope="col">Total Assessments</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="clear-fix"></div>
						<br />
					</div>
				</div>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/views/partialpage/footer.jsp"%>
</body>
<script type="text/javascript"
	src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/dashboard/veteranSearch.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
</html>
