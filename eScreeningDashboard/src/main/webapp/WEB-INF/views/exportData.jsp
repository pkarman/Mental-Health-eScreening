<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html ng-app="exportDataFormApp">
<head>
<title>Export Data</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
	<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
	<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
    
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
    
	<script type="text/javascript" src="resources/js/jquery/ui/jquery-ui.min.js"></script>       
    
	<script type="text/javascript" src="resources/js/angular/angular.min.js"></script>
   	<script type="text/javascript" src="resources/js/dashboard/myAccount.js"></script>
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>

	<link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
	<link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/userManagement.css" rel="stylesheet" type="text/css">
	<link href="resources/css/formButtons.css" rel="stylesheet" type="text/css">
	<link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
	
	<link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
    
	<!-- Bootstrap -->
	<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
	
<script type="text/javascript">	
   
    
    /*  
	$.ajax({
	    type: 'POST',
	    url: 'http://localhost:8080/escreeningdashboard/dashboard/exportData/services/exports/filterOptions',
	    data: '{}', // 
	    success: function(data) { alert(JSON.stringify(data)); },
	    contentType: "application/json",
	    dataType: 'json'
	});
*/  
/*
$.ajax({
	type: 'POST',
	headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	url: 'http://localhost:8080/escreeningdashboard/dashboard/exportData/services/user/clinicians',
	data: $.param({ "includeAll": true }),
	success: function(data) { alert(JSON.stringify(data)); } 
});
*/
/*
$.ajax({
    type: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    url: 'http://localhost:8080/escreeningdashboard/dashboard/exportData/services/user/assessmentCreators',
    data: $.param({ "includeAll": true }),
    success: function(data) { alert(JSON.stringify(data)); } 
});
*/
/*
$.ajax({
    type: 'POST',
    url: 'http://localhost:8080/escreeningdashboard/dashboard/exportData/services/exports/exportLog/30',
    data: '{}', // 
    success: function(data) { alert(JSON.stringify(data)); },
    contentType: "application/json",
    dataType: 'json'
});
*/
/*
$.ajax({
    type: 'POST',
    url: 'http://localhost:8080/escreeningdashboard/dashboard/exportData/services/user/programs',
    data: '{}', // 
    success: function(data) { alert(JSON.stringify(data)); },
    contentType: "application/json",
    dataType: 'json'
});
*/

</script>
<style type="text/css">
.wrap{
	white-space: normal !important;
}
</style>
</head>
<body>

    
<a href="#skip" class="offscreen">Skip to main content</a>
<div id="outerPageDiv" >
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
    
<div class="" ng-controller="exportDataController" >
<form id="dataExportForm" name="dataExportForm"  ng-submit="searchDatabase()">


		<div id="bodyDiv" class="bgImgMiddle" align="center">
			<div id="exportDataFormBodyDiv"></div>
		</div>
		<div class="container  left-right-shadow  noPrint">
        
                
                
        <div class="row">
          <div class="col-md-12">
            <div class="mainDiv">
              <div class="row">
				<div class="col-md-12"><a name="skip" > </a ><h1>Export Data</h1></div>
				</div>
              <div class="row">
                <div class="col-md-12">
                  <div class="border-radius-main-form gray-lighter">
					
                    
			<div class="row">
				<div class="col-md-4">
                    <div class="form-group">
                      <label class="labelAlign" for="programId">Program</label>
                      <select id="programId" class="fieldAlign form-control" placeholder="Select Program"
                           name="programId"
                           ng-options="program.stateId as program.stateName for program in programList"
                           ng-model="exportDataFormBean.programId">
						<option value="">Select Program</option>
                      </select>
                    </div> 
				</div>
                <div class="col-md-4">
                    <div class="form-group">
                      <label class="labelAlign" for="fromAssessmentDate">From Assessment Date</label>
                      <input type="text"
                         id="fromAssessmentDate" class="dateField form-control" 
                         name="fromAssessmentDate" maxlength="10"
                         ng-model="exportDataFormBean.fromAssessmentDate"
                         placeholder="MM/DD/YYYY" autocomplete="off" />
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                      <label class="labelAlign" for="toAssessmentDate">To Assessment Date</label>
                      <input type="text"
                         id="toAssessmentDate" class="dateField form-control"
                         name="toAssessmentDate" maxlength="10"
                         ng-model="exportDataFormBean.toAssessmentDate"
                         placeholder="MM/DD/YYYY" autocomplete="off" />
                    </div>
                </div>
            </div>
            <div class="row">    
                <div class="col-md-4">
                    <div class="form-group">
                      <label class="labelAlign" for="clinicianId">Clinician</label>
                      <select id="clinicianId" class="fieldAlign form-control"  placeholder=""
                         name="clinicianId"
                         ng-options="clinician.stateId as clinician.stateName for clinician in clinicanList"
                         ng-model="exportDataFormBean.clinicianId">
                        <option value="">Select Clinician</option>
                      </select>
                      <div class="checkbox">
                        <input type="checkbox" class="checkBoxAlign"
                           id="showDeletedClinicians" name="showDeletedClinicians"
                           ng-change="refreshClinicianList()"
                           ng-model="exportDataFormBean.showDeletedClinicians" />
	                     <label for="showDeletedClinicians">Show deleted clinician users</label>
                      </div>
                    </div>
                    </div>
                    <div class="col-md-4">
                    
                    <div class="form-group">
                      <label class="labelAlign" for="createdByUserId">Created By</label>
                      <select id="createdByUserId" class="fieldAlign form-control"  placeholder="Created By"
                          name="createdByUserId"
                          ng-options="createdByUser.stateId as createdByUser.stateName for createdByUser in createdByUserList"
                          ng-model="exportDataFormBean.createdByUserId">
                        <option value="">Select Created By</option>
                      </select>
                      <div class="checkbox">
                        <input type="checkbox" class="checkBoxAlign"
	                         id="showDeletedAssessmentCreators"
	                         name="showDeletedAssessmentCreators"
	                         ng-change="alert('refresh the list');"
	                         ng-model="exportDataFormBean.showDeletedAssessmentCreators" />
                        <label for="showDeletedAssessmentCreators">Show deleted created by users</label>
                      </div>
                    </div>
                    
                     </div>
                    <div class="col-md-4">

	            	<div class="form-group">
					  <label class="labelAlign" for="veteranId">Veteran ID</label>
                      <input type="number" class="fieldAlign form-control" placeholder="Veteran ID"
                                            id="veteranId" name="veteranId" maxlength="10" min="0"
                                            max="99999999" ng-model="exportDataFormBean.veteranId"
                                            autocomplete="off" />
                    </div>    
                    
                     </div>

                  </div> 
                    
                    <div class="row">
	                  <div class="col-md-12">
	                    <div>
	                      <button ng-click="exportIdentifiedDataButton()" ng-model="exportIdentifiedDataButton" id="exportIdentifiedDataButton" name="exportIdentifiedDataButton" type="button" class="submitButton btn btn-primary" data-toggle="modal" data-target="#modal_confirmation"><span class="glyphicon glyphicon-circle-arrow-down"></span> Export Identified Data</button>
                        <button ng-click="exportDeidentifiedDataButton()"  ng-model="exportDeidentifiedDataButton" id="exportDeidentifiedDataButton" name="exportDeidentifiedDataButton" type="button" class="submitButton btn btn-primary" data-toggle="modal" data-target="#modal_confirmation"><span class="glyphicon glyphicon-circle-arrow-down" ></span> Export De-identified Data</button>
                        <button ng-click="launchDataDictionaryButton()"  ng-model="launchDataDictionaryButton" id="launchDataDictionaryButton" name="launchDataDictionaryButton" type="button" class="submitButton btn btn-primary" data-toggle="modal"  data-target="#modal_progress"><span class="glyphicon glyphicon-book" ></span> Launch Data Dictionary</button>
                      </div>

	                  </div>
	                </div>
                   
				
                	</div>
                </div>
              </div>
              <br/>
              <br/>

              
          <div class="row">
          <div class="col-md-12">
          	  	
                <div class="row">
                	<div class="col-md-8"><h2>Export Log  <a href="exportData" class="btn btn-success btn-lg active  btn-xs" role="button"><span class="glyphicon glyphicon-refresh"></span> Refresh</a></h2></div>
                    <div class="col-md-4">
                        <div class="form-horizontal">
                        	<div class="form-group">
                                <label class="labelAlign col-md-3 control-label" for="filterId">Filters</label>
                                <div class="col-md-9">
                                <select id="filterId" class="form-control"
                                    name="filterId"
                                    ng-options="filter.stateId as filter.stateName for filter in filterOptionList"
                                    ng-model="filter.stateId" ng-change="callFilters()">
                                    <option value="">Select Export Date</option>
                                </select>
           
                                </div>  
    	                    </div>
	                    </div>
                    
                    </div>
                </div>
                
                

                
                
                              
              <div id="mainContent">



                      
                      
                <table id="exportDataTable" name="exportDataTable" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover" width="100%" summary="Export Data Table">
                  <thead>
                    <tr>
                      <th scope="col">Last Export On <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Date the file was downloaded"></span></th>
                      <th scope="col">Executed <br>By  <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="User who downloaded the file"></span></th>
                      <th scope="col">Clinician <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Selected assigned clinician which was used to recored for the export" ></span></th>
                      <th scope="col">Created By <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="User who created the assessment which used to the exported data"></span></th>
                      <th scope="col">Export Type <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Type of export, identied contained contains PPI data de-identied does not contain PPI data" ></span></th>
                      <th scope="col">Assessment Date <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Start/End assessment completion date used to data for export" ></span></th>
                      <th scope="col">Program <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Selected Program" ></span></th>
                      <th scope="col">Veteran ID <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Selected Veteran ID" ></span></th>
                      <th scope="col">Export Comment <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="User comment entered when download initiated" ></span></th>
                      <th scope="col">Download Again <span class="id_header_tooltip glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="top" title="Download the exported data again based on the listed criteria in this row" ></span></th>
                    </tr>
                  </thead>
                  <tbody>
                  </tbody>
                  
                </table>
            
              </div>
              
              </div>
              
              
              
              </div>
                
              
            </div>
          </div>
        </div>
        <div class="clear-fix"></div>
      </div>





    
    
    
<!-- Modal -->
<div class="modal fade" id="modal_confirmation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Exporting Identified Data Confirmation</h4>
      </div>
      <div class="modal-body">
        <div class="alert alertType" id="alertType">Your are about exporting <strong id="alertData"> - </strong>, Are you sure you want to proceed?</div>        
        <div class="alert alert-info">Please enter a comment for this export and click ok to download the export file. <br />Please do not do any operations while downland is in progress.</div>
          
		  <div class="form-group">
		    <label for="comment">Export Comment *</label>
		    <textarea class="form-control" rows="3"  placeholder="Enter a comment" ng-model="exportDataFormBean.comment" id="comment"
            required data-validation-required-message="Please enter a comment" minlength="5" data-validation-minlength-message="Min 5 characters" 
                        maxlength="100"></textarea>
            <input class="form-control" name="exportDataType" id="exportDataType" value="identified" type="hidden" ng-model="exportDataFormBean.exportDataType" >
		    <div class="help-block">Maxlength 100 characters</div>
            
		  </div>

      </div>
      <div class="modal-footer">
      	<button id="exportDataButton" name="exportDataButton" type="submit" class="btn btn-primary" >Confirm Export Data Now</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
    </div>
  </div>
  
</div>


<!-- Modal -->
<div class="modal fade" id="modal_progress" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">Loading...</h4>
      </div>
      <div class="modal-body">
        <strong id="progressMessages"></strong>
        <div class="progress">
          <div class="progress-bar progress-bar-striped active"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
            <span class="sr-only">100% Complete</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</div>


</form>

</div>
</div>

<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>

</body>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/dashboard/exportData.js?v=1" />"></script>
</html>
