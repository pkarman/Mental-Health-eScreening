<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html ng-app="selectVeteransFormApp">
<head>
    <title>Veterans Search</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
	<script type="text/javascript" src="resources/js/angular/angular.min.js"></script>

    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
	<script src="resources/js/bootstrap-datepicker/bootstrap-datepicker.js"></script>    

    <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
    <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
    <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="resources/css/bootstrap-datepicker/datepicker.css" />">

	<style type="text/css">
	.loading{
		background-image: url("resources/images/ajax-loader.gif");
		background-repeat: no-repeat;
		background-position: center;
		min-height:100px;
		background-color: red;
	}
	
	</style>

<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">

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

<div class="container left-right-shadow">
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
					<li class="active"><a href="#" role="tab"><strong>Create Assessment(s) for Appointment(s)</strong></a></li>
				</ul>
				<br>          
                
                <div class="" ng-controller="selectVeteransController" >
				
				
				
				<form id="veteranSearchForm" name="veteranSearchForm" novalidate ng-submit="searchDatabase()">
					<div class="border-radius-main-form gray-lighter">
					<h2>Search Criteria</h2>
					
					<form:errors path="*" element="div" cssClass="alert alert-danger" />

					<div class="row">
						<div class="col-md-3">
							<div class="form-group">
								<label class="labelAlign" for="clinicId">VistA Clinic *</label>
								<select id="clinicId" class="fieldAlign form-control"  placeholder=""
								name="clinicId"
								ng-options="clinic.clinicId as clinic.clinicName for clinic in clinicsList"
								ng-model="veteranSearchFormBean.clinicId">
								<option value="">Select VistA Clinic</option>
								</select>										 
                			</div>
                		</div>

                      <div class="col-md-3">
                        <div class="form-group">
                            <label class="labelAlign" for="fromAssessmentDate">From Date</label>
                          <div class="input-group date" id="fromAssessmentDateGroup">
                            <input type="text"
                             id="fromAssessmentDate" class="dateField form-control" 
                             name="fromAssessmentDate" maxlength="10"
                             ng-model="veteranSearchFormBean.fromAssessmentDate"
                             placeholder="MM/DD/YYYY" autocomplete="off" />
                             
                             <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
                         </div>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="form-group">
                              <label class="labelAlign" for="toAssessmentDate">To Date</label>
                            <div class="input-group date" id="toAssessmentDateGroup">
                              <input type="text"
                               id="toAssessmentDate" class="form-control"
                               name="toAssessmentDate" maxlength="10"
                               ng-model="veteranSearchFormBean.toAssessmentDate"
                               placeholder="MM/DD/YYYY" autocomplete="off" />
                               <div class="input-group-addon"><i class="glyphicon glyphicon-calendar" title="Click to open calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
                            </div>
                        </div>
                      </div>

                      <div class="col-md-3">
                        <div class="form-group">
                              <label class="labelAlign" for="lastname">Last Name</label>
                              <input type="text"
                               id="lastname" class="form-control"
                               name="lastname" 
                               ng-model="veteranSearchFormBean.lastname"
                               placeholder="Last Name" autocomplete="off" />
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
                </form>
				</div>
			
	            
				<form:form method="post" >
				<h2>Search Result</h2>
				<div id="mainContent">
					<table id="selectVeteransTable" name="selectVeterans" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover" width="100%" summary="Export Data Table">
					  <thead>
						<tr>
							<th scope="col" class="col-md-1">Select Vet</th>
							<!--<th scope="col" class="col-md-1">SSN-4</th>-->
							<th scope="col">Last Name</th>
							<th scope="col">First Name</th>
							<th scope="col">Middle Name</th>
							<!--<th scope="col">Date of Birth</th>-->
							<th scope="col">Appointment Date</th>
							<th scope="col">Appointment Time</th>
						</tr>
					  </thead>
					  <tbody>
					  </tbody>
					</table>	
					<div class="row">
						<div class="col-md-12">
						<button id="selectVeteransBtn" name="selectVeteransBtn" type="submit" class="btn btn-primary pull-right">Select Vaterans</button>
						</div>			
					</div>
				</div>
				</form:form>
            <br/><br/>
            <!--  -->

		</div>
	</div>
</div>

</div>

<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dashboard/selectVeterans.js?v=2" />"></script>
</html>