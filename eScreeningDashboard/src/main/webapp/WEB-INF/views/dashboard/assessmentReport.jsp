<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!doctype html>
<html ng-app="assessmentReportFormApp">
<head>
<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
<!-- <script type="text/javascript" src="resources/js/jquery/jquery.dialogextend.min.js"></script> -->
<script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
<script type="text/javascript" src="resources/js/jquery/ui/jquery-ui.min.js"></script>
<link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" />

<link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
<link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css" />
<link href="resources/css/formButtons.css" rel="stylesheet" type="text/css" />


<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="resources/css/partialpage/standardtopofpage-dashboard_new.css" rel="stylesheet" type="text/css">

<title>Assessment Report</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		tabsLoad("assessmentReport");
		$("#fromAssessmentDate").datepicker({
			showOn : 'button',
			buttonImage : "../resources/images/calendar1.png",
			buttonImageOnly : true
		});
		$("#toAssessmentDate").datepicker({
			showOn : 'button',
			buttonImage : "../resources/images/calendar1.png",
			buttonImageOnly : true
		});
		$('.ui-datepicker-trigger').mouseover(function() {
			$(this).attr("title", "click to open calendar");

		});
	
		

	});

	function showInDialog(obj) {
		var wWidth = $(window).width();
		var dWidth = wWidth * 0.98;
		var wHeight = $(window).height();
		var dHeight = wHeight * 0.97;
		$("#assessmentPreview").dialog(
				{
					autoOpen : false,
					modal : true,
					width : dWidth,
					height : 635,
					open : function(ev, ui) {
						$('#assessmentPreviewIframe').attr('src',
								obj.getAttribute('href'));
					}
				});
		$('#assessmentPreview').dialog('open');
		$('#assessmentPreview').show();
	}   	
</script>



</head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a> 
<div id="outerPageDiv" class="noPrint">
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
<div class="container  left-right-shadow  noPrint">
  <div class="row">
    <div class="col-md-12">
      <div ng-controller="assessmentReportController" class="mainDiv">
        
        <a name="skip" > </a >
        <h1>Assessment Report</h1>
        <div class="row">
          <div class="col-md-12">
            <div id="leftContent2" class="border-radius-main-form gray-lighter">
              <form id="assessmentReportForm" name="assessmentReportForm" novalidate ng-submit="searchDatabase()">
                <div class="row">
                  <div class="col-md-2">
                    <div class="form-group">
                      <label class="labelAlign" for="veteranAssessmentId">Assessment ID</label>
                      <input type="number" class="fieldAlign form-control"  placeholder="Assessment ID"
                                            id="veteranAssessmentId" name="veteranAssessmentId" maxlength="10" min="0" max="99999999"
                                            ng-model="assessmentReportFormBean.veteranAssessmentId" autocomplete="off" />
                      <span class="error" ng-show="assessmentReportForm.veteranAssessmentId.$invalid && assessmentReportForm.submitted">*</span> </div>
                  </div>
                  <div class="col-md-2">
                    <div class="form-group">
                      <label class="labelAlign" for="veteranId">Veteran ID</label>
                      <input type="number" class="fieldAlign form-control" placeholder="Veteran ID"
                                            id="veteranId" name="veteranId" maxlength="10" min="0"
                                            max="99999999" ng-model="assessmentReportFormBean.veteranId"
                                            autocomplete="off" />
                      <span class="error"
                                            ng-show="assessmentReportForm.veteranId.$invalid && assessmentReportForm.submitted">*</span> </div>
                  </div>
                  <div class="col-md-2">
                    <div class="form-group">
                      <label class="labelAlign" for="programId">Program</label>
                      <select id="programId" class="fieldAlign form-control" placeholder="Program"
                                            name="programId"
                                            ng-options="program.stateId as program.stateName for program in programList"
                                            ng-model="assessmentReportFormBean.programId">
                        <option value="">Select Program</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <div class="form-group">
                      <label class="labelAlign" for="fromAssessmentDate">From Assessment Date</label>
                      <input type="text"
                                                id="fromAssessmentDate" class="dateField form-control" 
                                                name="fromAssessmentDate" maxlength="10"
                                                ng-model="assessmentReportFormBean.fromAssessmentDate"
                                                placeholder="MM/DD/YYYY" autocomplete="off" />
                      <span class="error"
                                                ng-show="assessmentReportForm.fromAssessmentDate.$invalid && assessmentReportForm.submitted">*</span> </div>
                  </div>
                  <div class="col-md-3">
                    <div class="form-group">
                      <label class="labelAlign" for="toAssessmentDate">To Assessment Date</label>
                      <br />
                      <input type="text"
                                                    id="toAssessmentDate" class="dateField form-control"
                                                    name="toAssessmentDate" maxlength="10"
                                                    ng-model="assessmentReportFormBean.toAssessmentDate"
                                                    placeholder="MM/DD/YYYY" autocomplete="off" />
                      <span  class="error" ng-show="assessmentReportForm.toAssessmentDate.$invalid && assessmentReportForm.submitted">*</span> </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="labelAlign" for="clinicianId">Clinician</label>
                      <select id="clinicianId" class="fieldAlign form-control"  placeholder=""
                                                    name="clinicianId"
                                                    ng-options="clinician.stateId as clinician.stateName for clinician in clinicanList"
                                                    ng-model="assessmentReportFormBean.clinicianId">
                        <option value="">Select Clinician</option>
                      </select>
                      <div class="checkbox">
                        <input type="checkbox" class="checkBoxAlign"
                                                    id="showDeletedClinicians" name="showDeletedClinicians"
                                                    ng-change="refreshClinicianList()"
                                                    ng-model="assessmentReportFormBean.showDeletedClinicians" />
                        <label for="showDeletedClinicians">Show deleted clinician users</label>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="labelAlign" for="createdByUserId">Created By</label>
                      <select id="createdByUserId" class="fieldAlign form-control"  placeholder="Created By"
                                        name="createdByUserId"
                                        ng-options="createdByUser.stateId as createdByUser.stateName for createdByUser in createdByUserList"
                                        ng-model="assessmentReportFormBean.createdByUserId">
                        <option value="">Select Created By</option>
                      </select>
                      <div class="checkbox">
                        <input type="checkbox" class="checkBoxAlign"
                                        id="showDeletedAssessmentCreators"
                                        name="showDeletedAssessmentCreators"
                                        ng-change="refreshCreatedByUserList()"
                                        ng-model="assessmentReportFormBean.showDeletedAssessmentCreators" />
                        <label for="showDeletedAssessmentCreators">Show deleted created by users</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div>
                      <button id="searchAssessmentButton" name="searchAssessmentButton" type="submit"  class="submitButton btn btn-primary">Search</button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="clear-fix"></div><br />
        <div class="row">
          <div class="col-md-12">
            <div id="mainContent">
              <div align="right"> <a href="#" class="btn btn-default btn-xs" ng-click="exportDataGrid('<c:url value="/dashboard/assessmentReport/services/assessments/search/csv" />')"><img src="resources/images/csvIcon2.png" class="exportImg" alt="CSV File"> <strong>CSV</strong></a> <a href="#" class="btn btn-default btn-xs"  ng-click="exportDataGrid('<c:url value="/dashboard/assessmentReport/services/assessments/search/xls" />')"><img src="resources/images/xlsIcon1.png" class="exportImg" alt="Excel File">&nbsp;Excel</a> <a href="#" class="btn btn-default btn-xs" ng-click="exportDataGrid('<c:url value="/dashboard/assessmentReport/services/assessments/search/pdf" />')"><img src="resources/images/pdfIcon4.png" class="exportImg" alt="PDF File">&nbsp;PDF</a> </div>
              <table id="assessmentReportTable" name="assessmentReportTable" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover" summary="Assessment Report Table">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Program</th>
                    <th scope="col">Clinician</th>
                    <th scope="col">Created By</th>
                    <th scope="col">Assessment Date</th>
                    <th scope="col">Veteran ID</th>
                    <th scope="col">Veteran</th>
                    <th scope="col">Status</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
          </div>
        </div>
       
        
         <div id="assessmentPreview" title="Assessment Report Preview" style="display:none;">
    	 <iframe id="assessmentPreviewIframe" width="99%" height="99%" src=""></iframe> </div>
         
         
      </div>
      <br>
    </div>
  </div>
</div>
<div class="clearfix"></div>
<!-- Modal -->
<div class="custom_modal">
    <div class="modal fade" id="AssessmentReportPreview" tabindex="-1" role="dialog" aria-labelledby="AssessmentReportPreview" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header noPrint">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modal-title">Assessment Report Preview</h4>
          </div>
          <div class="modal-body">
            <div class="modal_contents yesPrint">Loading...</div>
          </div>
        </div>
      </div>
    </div>
</div>


<!-- Modal -->
<div class="custom_modal">
    <div class="modal fade" id="AssessmentReportPreviewIFrame" tabindex="-1" role="dialog" aria-labelledby="AssessmentReportPreviewIFrame" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header noPrint">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modal-title-iframe">Assessment Report Preview</h4>
          </div>
          <div class="modal-body">
            <div class="yesPrint" style="height:450px; overflow:auto; ">
                <iframe src="" width="100%" height="99%"></iframe></div>
          </div>
     
        </div>
      </div>
    </div>
</div>

<div class="noprint">
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</div>
</body>
<script type="text/javascript" src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/assessmentReport.js?v=2" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script>
$(document).ready(function() {
	$(this).on("click", '.assessmentNotePush', function(e){
		  e.preventDefault();
		  console.log("assessmentNotePush");
		  var vaid = $(this).attr('vaid');
		  var title = $(this).attr('data-title');

	
		  $('#AssessmentReportPreview').modal('show');
		  $('.modal_contents').html('<i class="ajax_loading text-center"></i> Loading...');
		   $.ajax({
			  type : 'get',
			   url : 'assessmentNote?vaid='+ vaid, // in here you should put your query 
			   //data : 'vaid='+ 49, // here you pass your id via ajax .
						 // in php you should use $_POST['post_id'] to get this value 
		   success : function(r)
			   {
				  // now you can show output in your modal 
				  // $('#AssessmentReportPreview').show();  // put your modal id 
				  
				 $('.modal_contents').show().html(r);
				 $("#modal-title").html(title);
			   }
		});
	
	});
	
	$(this).on("click", '.assessmentPreviewIFramePush', function(e){
			
		  e.preventDefault();
		  console.log("assessmentPreviewIFramePush");
		  var vaidurl = $(this).attr('vaidurl');
		  //vaidurl = 'https://www.yahoo.com/?hps=263';
		  var title = $(this).attr('data-title');

		  $('#AssessmentReportPreviewIFrame iframe').attr('src', vaidurl);
		  $('#AssessmentReportPreviewIFrame').modal('show');
		  $("#modal-title-iframe").html(title);

	});
		
	});
</script>
</html>
