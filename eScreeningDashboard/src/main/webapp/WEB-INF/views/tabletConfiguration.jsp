<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html  ng-app="tabletConfigurationApp">
<head>
    <title>Tablet Configuration</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
    <meta name="apple-mobile-web-app-capable" content="yes">
    <!-- FAVICON -->
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />    
    <link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
    <link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
    <meta name="msapplication-square310x310logo" content="resources/images/favico_va_310x310.png" />
    
    <link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/login.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/mobileStyle/mediaQueryMain.css" rel="stylesheet" type="text/css"/>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
</head>
<body>
    <%@ include file="/WEB-INF/views/partialpage/survey_header_new.jsp" %>
	<div id="clear-fix"></div>
	<div class="container left-right-shadow">
		<div class="row">
			<div class="col-md-12 main_container_login">
				<h1>Tablet Configuration</h1>
				<div class="alert alert-danger hide errorMsg" id="errorMsg" aria-hidden="true">Please select a program.</div>
				<div class="alert alert-success hide successMsg" id="successMsg" aria-hidden="true">Program has been selected.</div>
				<div class=""  ng-controller="tabletConfigurationController" >		
					<form id="tabletConfigurationForm" name="tabletConfigurationForm" novalidate>
						<fieldset class="result-border">
							<legend class="result-border">Setup</legend>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="programId">Select Program</label>								
											<select id="programId" class="fieldAlign form-control col-md-4" name="programId">
												<option value="">All Programs</option>
												<option ng-repeat="program in programList" value="{{program.stateId}}">{{program.stateName}}</option>
											</select>
										</div>
									</div>
									<div class="col-md-8">
									<div id="leftContent2" class="border-radius-main-form gray-lighter">
										Selected Program is
										<h2><div id="tabletProgramBlock">[NONE]</div></h2>
									</div>
									</div>
								</div>
						</fieldset>
						<div class="clearfix"></div>					
						<div class="row">
							<div class="col-md-12">
								<hr />
								<div class="form-group">
									<button type="button" class="btn btn-primary" id="update">Update</button>
									<button type="button" class="btn btn-default"  id="cancel">Back to login screen</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/partialpage/footer_login.jsp" %>
	<!-- Angular JS -->
	<script type="text/javascript" src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="resources/js/tabletConfiguration.js"></script>
</body>
</html>