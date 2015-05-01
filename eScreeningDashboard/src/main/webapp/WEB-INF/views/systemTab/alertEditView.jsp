<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="programEditFormApp">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
    <title>Alert Edit View</title>

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
<div class="container left-right-shadow">
  <div class="row">
    <div class="col-md-12"> <a name="skip"> </a >
      <h1 id=hTitle>Edit Alert</h1>

		<div class="row">
			<div class="col-md-12">
				<!-- <div class="alert alert-success hide" role="alert" id="successMsg">Alert Saved Successfully.</div> -->
				<div class="alert alert-danger hide" role="alert" id="errorMsg"></div>
			</div>
		</div>
					  
      <div>
		<form>
			<div class="border-radius-main-form gray-lighter">
				<div class="form-group">
					<label for="exampleInputEmail1">Alert Name *</label>
					<input type="text" class="form-control" id="alertName" placeholder="Enter Alert Name" maxlength="100">
					<p class="help-block">Maxlength 100 characters</p>
				</div>
			</div><br>
			
			<div class="form-group pull-right">
				<button type="button" class="btn btn-primary" id="save" data-aid="">Save</button>
				<button type="button" class="btn btn-default" id="cancel">Cancel</button>
			</div>
		</form>
      </div>
    </div>
  </div>
</div>        
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/systemTab/alertEditView.js?v=1" />"></script>
</body>
</html>