<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

<head>
<title>Program List View</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon" />
  <link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon" />
  <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/veteranSearch.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/formButtons.css" />" rel="stylesheet" type="text/css" />

  <!-- Bootstrap -->
  <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />
  
  <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/adminDashboardTabs.js" />"></script>

</head>
<body ng-app="alertViewApp">
<a href="#skip" class="offscreen">Skip to main content</a>
<div id="outerPageDiv">
  <%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp"%>
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
</div>
<div class="mainDiv"> </div>
<div class="container left-right-shadow">
  <div class="row">
  
    <div class="col-md-4 pull-right">
      <s:url var="addAlertUrl" value="/dashboard/alertEditView" htmlEscape="true"></s:url>
      <a href="${addAlertUrl}" class="btn btn-primary btn-md h1_button pull-right"><span class="glyphicon glyphicon-plus"></span> Add New Alert</a> </div>
    <div class="col-md-8 pull-left"><a name="skip" > </a >
      <h1>Manage Alert</h1>
    </div>
  </div>
  <div class="clear-fix"></div>


	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-success hide" role="alert" id="successMsg">Alert Saved Successfully.</div>
		</div>
	</div>
		
		
  

	<div  ng-controller="alertsController"  class="bottomContent" id="mainContent" >
		<table id="alertsTable" name="alertsTable" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover" summary="Alert List View">
		  <thead>
			<tr>
			  <th scope="col" class="col-md-10">Name</th>
			  <th scope="col" class="col-md-2 text-right">Action</th>
			</tr>
		  </thead>
		  <tbody>
		  </tbody>
		</table>
	</div>
</div>


<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Delete Alert</h4>
            </div>
            <div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="alert alert-danger" role="alert">Are you sure you want to delete alert?</div>
					</div>
				</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger confirmDelete" data-dismiss="modal" data-delete-id="">Delete</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>


 <%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/systemTab/alertListView.js?v=1" />"></script>
</html>