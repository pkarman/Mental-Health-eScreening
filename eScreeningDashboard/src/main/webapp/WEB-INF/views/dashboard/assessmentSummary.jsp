<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/adminDashboardTabs.js" />"></script>
    <script src="<c:url value="/resources/js/d3/d3.min.js" />"></script>
    
    <link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon" />
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon" />
    <!--  <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard.css" />" rel="stylesheet" type="text/css" /> -->
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/veteranSearch.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/common-ui-styles/circle.css" />" rel="stylesheet" type="text/css" />
    
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />
    <title>Assessment Summary</title>
    <script type="text/javascript">
        $(document).ready(function() {
            tabsLoad("assessmentDashboard");
            $(".modal-wide").on("show.bs.modal", function() {
              var height = $(window).height() - 200;
               $(this).find(".modal-body").css("max-height", height);
              });
            });
    </script>
    <style type="text/css">
    
    
    .bar {
    }
    .pointer {
      width:2px; height:21px; background-color:#222c76; text-align:center; margin:0 auto;
    }
    .axis path, .axis line {
      fill: none;
      stroke: black;
      shape-rendering: crispEdges;
    }
    .axis text {
      font-family: sans-serif;
      font-size: 11px;
    }
    #tooltip {
      position: absolute;
      text-align: center;
      height: auto;
      -webkit-border-radius: 0px;
      -moz-border-radius: 0px;
      pointer-events: none;
    }
    #tooltip.hidden {
      display: none;
    }
    #tooltip p {
      margin: 0;
      font-family: sans-serif;
      font-size: 16px;
      line-height: 20px;
    }
    .value {
      color: #fff;
      background-color: #0f3a64;
      width: 40px;
      padding: 10px;
    }
    .hide{
      display:none;
    }
    
    .stackedBars {
    
    }
    .stackedBars svg{
      font-family: arial;
      background-color:#fff;
      font-size: 10px;
      position:absolute;
    }
    .scoreBlock{
      border-right:1px dashed #000000;
      min-height:200px;
    }
    .scoreBlock h3{
      font-size:14px;
      font-family: arial;
      font-weight:bold;
      margin:0px;
      padding:0px;
      text-decoration:underline;
    }
    .scoreBlock h4{
      font-size:70px;
      font-family: arial;
      font-weight:bold;
       margin:0px;
      padding:0px;
    }
    .scoreBlock h5{
      font-size:14px;
      font-family: arial;
      font-weight:bold;
       margin:0px;
      padding:0px;
    }

    .graphBlock{
      border-right:1px dashed #000000;
      min-height:200px;
    }

    .graphTitle{
      text-align:center;
      font-weight:bold;
    }
    .graphFooterNote{
      text-align:center;
    }
    
    
        /* for print preview styles  */
        @page { size: auto; margin: 17mm 0mm 16.12mm 0mm;}
        .container_main { width: 96%;  padding: 0 2%; margin: 0 auto;}
        .templateHeader { }
        .templateFooter { }
        .templateSectionTitle { font-weight:bold; font-size: 20px; margin: 10px 0; }
        .templateSection { }
        .moduleTemplateTitle { font-weight: bold; }
        .moduleTemplateText { margin: 10px 20px 20px ; }
        .matrixTableHeader{ width:200px; }
		.matrixTableData{ width:240px; }
		.justifyRtTableData{ text-align:right; }
		.justifyLftTableData{ text-align:left; }
		.justifyCtrTableData{ text-align:center; }
		.spacer1TableData{ width:500px; }
    
   #VeteranSummaryModal .moduleTemplateTitle { 
    font-weight: bold;
     margin: 10px 10px 20px 10px;
   }
    #VeteranSummaryModal .moduleTemplateText {
      margin: 10px 10px 20px 10px;
     
    }
    #VeteranSummaryModal .moduleTemplateText:first-line {
      // font-weight: bold;
    }
    
    #VeteranSummaryModal .matrixTableHeader{ width:200px; }
    
    #VeteranSummaryModal .moduleTemplate{
        display:inline-table;
		width:48%;
        border-top:1px dashed #000000;
    }
    
    #VeteranSummaryModal .moduleTemplate:nth-of-type(even){
      border-right:1px dashed #000000;
    }
    #VeteranSummaryModal .moduleTemplate:nth-of-type(odd){
      border-left:1px dashed #000000;
    }
    
    #VeteranSummaryModal .justifyRtTableData{
      text-align:right;
    }
    #VeteranSummaryModal .justifyLftTableData{
      text-align:left;
    }
    .modal.modal-wide .modal-dialog {
      width: 1000px;
    }
    .modal-wide .modal-body {
      overflow-y: auto;
    }
    #VeteranSummaryModal .modal-body p { margin-bottom: 900px;}
    #VeteranSummaryModal .moduleTemplateHeader h5{
      font-size:22px !important;
      font-weight:bold  !important;
      line-height: 35px;
    }
    #VeteranSummaryModal .graphicBlock{
      width:100% !important;
      border:none !important;
      border-top:1px dashed #000000 !important;
    }
    
    #VeteranSummaryModal .graphicBlock .scoreBlock{
      border:none !important;
      display: inline-table;
      width: 150px;
      border-right: 1px dashed #000 !important;
      padding-right: 5px;
    }
   #VeteranSummaryModal .graphicBlock  .moduleTemplateText{
      display: inline-table;
      width: 300px;
   }
    

    @media print {
    
    * {
      overflow: visible;
    }
    body * {
      visibility:hidden;
      overflow: visible !important;
      font-size: 11px;
      
      
    }
     
     
    .yesPrint, .yesPrint * {
    	visibility:visible !important;
    	min-height: none !important;
      	max-height: none !important;
      	height: auto !important;
    }
  
    .non-printable { display: none; }

   
    .custom_modal .modal-wide .modal-body{
      overflow: visible;
      position:absolute;
      left:0;
      top:0;
      width:750px !important;
      height: auto !important;
      background-color: #666;
    	visibility:visible !important;
    	min-height: none !important;
      	max-height: none !important;
      	padding: 0px;
      	margin: 0px;
    }
	.justifyCtrTableData img{
		display: block;
		text-align: center;
		margin: 0 auto;
		min-height: 100px;
		min-width: 100px;
	}
	.moduleTemplateText{margin: 0px; padding: 0px}
	
	.moduleTemplateHeader .col-md-6{
		width: 350px;
		float: left;
	}
	
    .moduleTemplateHeader img{
   		width: 130px;
   	}
   	.moduleTemplateHeader h5{
   		font-size: 16px;
   	}
   	#VeteranSummaryModal .moduleTemplateTitle{
   		margin: 5px;
   		padding: 0px;
   	}
    
    #VeteranSummaryModal .moduleTemplateText{
        margin: 5px;
   		padding: 0px;
    }
    }
    </style>
</head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a>
<div id="outerPageDiv" class="noPrint">
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
<div class="container left-right-shadow noPrint">
  <form:form modelAttribute="assessmentSummaryFormBean" autocomplete="off" method="post">
    <div class="row">
      <div class="col-md-5">
      	<a name="skip" > </a >
        <h1>Assessment Summary</h1>
      </div>
      <div class="col-md-7">
        <div class="text-right h1_align"> <strong>Current Status: </strong> <span class="label label-success">${veteranAssessmentInfo.assessmentStatusName}</span>
          <c:if test="${not empty assessmentStatusList}"> |
            <div class="h1_status form-inline">
              <form:label path="selectedAssessmentStatusId">Update Status to:</form:label>
              <form:select path="selectedAssessmentStatusId" cssClass="form-control">
                <form:option value="${veteranAssessmentInfo.assessmentStatusId}" label="Please Select a Status"/>
                <form:options items="${assessmentStatusList}" itemValue="stateId" itemLabel="stateName" />
              </form:select>
              <div class="text-right">
                <form:errors path="selectedAssessmentStatusId" cssClass="help-inline" />
              </div>
            </div>
          </c:if>
          <c:if test="${empty assessmentStatusList}">
            <form:hidden path="selectedAssessmentStatusId"/>
          </c:if>
        </div>
      </div>
    </div>
    <form:hidden path="selectedVeteranAssessmentId"/>
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <br />
    <div class="row">
      <div class="col-md-12">
        <c:if test="${!isCprsVerified}">
          <div class="alert alert-danger"> Your VistA account information needs to be verified before you can save or read any data from VistA. </div>
        </c:if>
        <c:if test="${callResult.hasError}">
          <div class="alert alert-danger">
            <c:out value="${callResult.userMessage}"/>
          </div>
          <div class="panel-danger-system">
            <div class="panel-group" id="accordion">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> System Error <span class="label label-danger">Click here for more error details</span> </a> </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse">
                  <div class="panel-body">
                    <c:out value="${callResult.systemMessage}"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </c:if>
        <c:if test="${!callResult.hasError}">
          <c:if test="${not empty callResult.userMessage}">
            <div class="alert alert-info">
              <c:out value="${callResult.userMessage}"/>
            </div>
          </c:if>
        </c:if>
        <div class="border-radius-main-form">
          <div class="row">
            <div class="col-md-2"> Program Name
              <div class="txt_lable_lg">
                <c:out value="${veteranAssessmentInfo.programName}" />
              </div>
            </div>
            <div class="col-md-6"> Name (Last, First Middle)
              <div class="txt_lable_lg">
                <c:out value="${veteranAssessmentInfo.veteranFullName}" />
              </div>
            </div>
            <div class="col-md-2"> Date of Birth
              <div class="txt_lable_lg">
                <fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${veteranAssessmentInfo.birthDate}" />
              </div>
            </div>
            <div class="col-md-2 text-right"> SSN-4
              <div class="txt_lable_lg text-right">
                <c:out value="${veteranAssessmentInfo.ssnLastFour}" />
              </div>
            </div>
          </div>
          <hr />
          <div class="row">
            <div class="col-md-2"> Phone
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.phone}" />
              </div>
            </div>
            <div class="col-md-2"> Work
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.officePhone}" />
              </div>
            </div>
            <div class="col-md-2"> Cell
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.cellPhone}" />
              </div>
            </div>
            <div class="col-md-4"> Email
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.email}" />
              </div>
            </div>
            <div class="col-md-2 text-right"> VistA IEN
				<div class="txt_lable_md">
					<c:out value="${veteranAssessmentInfo.veteranIen}" />
				</div>
				
				<c:if test="${isCprsVerified}">
					<c:if test="${empty veteranAssessmentInfo.veteranIen}">
						<s:url var="mapVeteranToVistaUrl" value="/dashboard/mapVeteranToVistaRecord" htmlEscape="true">
							<s:param name="vid" value="${veteranAssessmentInfo.veteranId}" />
						</s:url>
						<div class="text-left">
							<a href="${mapVeteranToVistaUrl}">Map to VistA Record</a>
						</div>
					</c:if>
				</c:if>
              
            </div>
          </div>
          <hr />
          <div class="row">
            <div class="col-md-2"> Battery Name
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.batteryName}" />
              </div>
            </div>
            <div class="col-md-2"> Created By
              <div class="txt_lable_md">
                <c:out value="${veteranAssessmentInfo.createdByUserFullName}" />
              </div>
            </div>
            <div class="col-md-2"> Date Created
              <div class="txt_lable_md">
                <fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${veteranAssessmentInfo.dateCreated}" />
              </div>
            </div>
            <div class="col-md-2"> Date Completed
              <div class="txt_lable_md">
                <fmt:formatDate type="date" pattern="MM/dd/yyyy HH:mm:ss" value="${veteranAssessmentInfo.dateCompleted}" />
              </div>
            </div>
            <div class="col-md-4 text-right"> Veteran Summary
              <div class="txt_lable_md"> <a href="#"  data-toggle="modal" id="VeteranSummaryButton" >View Veteran Summary</a> </div>
            </div>
          </div>
        </div>
        <br/>
        <c:if test="${not empty alertList}">
          <c:set var="completeness_col" scope="session" value="8"/>
        </c:if>
        <c:if test="${empty alertList}">
          <c:set var="completeness_col" scope="session" value="12"/>
        </c:if>
        <div class="row">
          <div class="col-md-${completeness_col}">
            <div class="panel panel-default min-height-175">
              <div class="panel-heading">
                <h3 class="panel-title"><strong>Completeness</strong></h3>
              </div>
              <div class="panel-body">
                <div class="progress_list">
                  <c:if test="${not empty progressList}">
                    <ul>
                      <c:forEach var="item" items="${progressList}">
                        <li class="col-md-2">
                          <div class="text-center circle_progress_wrapper">
                            <div class="c100 p<c:out value="${item.percentComplete}" /> small"> <span>
                              <c:out value="${item.percentComplete}" />
                              %</span>
                              <div class="slice">
                                <div class="bar"></div>
                                <div class="fill"></div>
                              </div>
                            </div>
                          </div>
                          <div class="clear-fix"></div>
                          <div class="circle_progress_title">
                            <c:out value="${item.surveySectionName}" />
                          </div>
                        </li>
                      </c:forEach>
                    </ul>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
          <c:if test="${not empty alertList}">
            <div class="col-md-4">
              <div class="panel panel-default min-height-175">
                <div class="panel-heading">
                  <h3 class="panel-title"><strong>Alerts</strong></h3>
                </div>
                <div class="panel-body">
                  <ul class="alert_group_danger">
                    <c:forEach var="item" items="${alertList}">
                      <li><i class="glyphicon glyphicon-warning-sign"></i>
                        <c:out value="${item.alertName}" />
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </div>
            </div>
          </c:if>
        </div>
        <div>
          <div class="row">
            <div class="col-md-4">
              <div class="form-group">
                <form:label path="selectedClinicId">VistA Clinic *</form:label>
                <form:select path="selectedClinicId" cssClass="form-control">
                  <form:option value="" label="Please Select a Clinic"/>
                  <form:options items="${clinicList}" itemValue="stateId" itemLabel="stateName"/>
                </form:select>
                <form:errors path="selectedClinicId" cssClass="help-inline"/>
              </div>
            </div>
            <div class="col-md-4">
              <div class="form-group">
                <form:label path="selectedNoteTitleId">Note Title *</form:label>
                <form:select path="selectedNoteTitleId" cssClass="form-control">
                  <form:option value="" label="Please Select a Note Title"/>
                  <form:options items="${noteTitleList}" itemValue="stateId" itemLabel="stateName"/>
                </form:select>
                <form:errors path="selectedNoteTitleId" cssClass="help-inline"/>
              </div>
            </div>
            <div class="col-md-4">
              <div class="form-group">
                <form:label path="selectedClinicianId">Clinician *</form:label>
                <form:select path="selectedClinicianId" cssClass="form-control">
                  <form:option value="" label="Please Select a Clinician"/>
                  <form:options items="${clinicianList}" itemValue="stateId" itemLabel="stateName"/>
                </form:select>
                <form:errors path="selectedClinicianId" cssClass="help-inline"/>
              </div>
            </div>
          </div>
          <hr />
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <input id="reviewAssessmentButton" name="reviewAssessmentButton" value="Review Assessment" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#assessment_reminders_modal" />
                <c:if test="${isCprsVerified}">
                  <input id="saveToVistaModal" name="saveToVistaModal" value="Save To VistA" type="button" class="btn btn-primary" data-toggle="modal" data-target="#save_to_vista_modal" <c:if test="${veteranAssessmentInfo.assessmentStatusName != 'Complete'}">disabled="disabled"</c:if> />
                </c:if>
                <c:if test="${!isCprsVerified}">
                  <input id="saveToVistaModal" name="saveToVistaModal" value="Save To VistA" type="button" class="btn btn-primary" disabled data-toggle="modal" data-target="#save_to_vista_modal" />
                </c:if>
                <input id="healthFactorTitlesButton" name="healthFactorTitlesButton" value="Health Factor Titles" type="button" class="btn btn-primary"  data-toggle="modal" data-target="health_factor_titles_modal" />
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group text-right">
                <input id="saveButton" name="saveButton" value="Save" type="submit" class="btn btn-primary" />
                <input id="cancelButton" name="cancelButton" value="Cancel" type="submit" class="btn btn-default" />
              </div>
            </div>
          </div>
          
         


          



          
          <!-- Modal Save to VistA -->
          <div class="modal fade" id="save_to_vista_modal" tabindex="-1" role="dialog" aria-labelledby="save_to_vista_modal_label" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="save_to_vista_modal_label">Save to VistA</h4>
                </div>
                <div class="modal-body">
                  <div class="alert alert-warning">Are you sure you want to Save Data to VistA?</div>
                </div>
                <div class="modal-footer">
                  <input id="saveToVistaButton" name="saveToVistaButton" value="Save" type="submit" class="btn btn-primary" />
                  <button type="button" class="btn btn-default" data-dismiss="modal" id="btn_close">Close</button>
                </div>
              </div>
            </div>
          </div>
          
          <!-- Modal Review Summary 
                    <div class="modal fade" id="assessment_reminders_modal" tabindex="-1" role="dialog" aria-labelledby="assessment_reminders_modal_label" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="assessment_reminders_modal_label">Assessment Reminders</h4>
                          </div>
                          <div class="modal-body">
                            Assessment Reminders Contents
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    --> 



		          <!-- Modal Review Summary 
                    <div class="modal fade" id="timeout_modal" tabindex="-1" role="dialog" aria-labelledby="timeout_modal_label" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="timeout_modal_label">Assessment Reminders</h4>
                          </div>
                          <div class="modal-body">
                            Timeout
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    --> 

          <br />
        </div>
      </div>
    </div>
    <!-- row --> 
  </form:form>
</div>

<!-- Modal Veteran Summary  -->
<div class="custom_modal" >
  <div class="modal fade  modal-wide" id="VeteranSummaryModal" tabindex="-1" role="dialog" aria-labelledby="VeteranSummaryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header noPrint">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="VeteranSummaryModalLabel">Veteran Summary</h4>
        </div>
        <div class="modal-body yesPrint">
            <div align="right" class="non-printable">
              <button class="btn btn-primary" onClick="window.print();"><span class=" glyphicon glyphicon-print"></span> Print Review Note</button>
            </div>
          <div class="modal_contents">Loading...</div>
        </div>
      </div>
    </div>
  </div>
</div>
          
          
<!-- Modal Review Assessment Preview -->
<div class="custom_modal" >
  <div class="modal fade" id="AssessmentReportPreview" tabindex="-1" role="dialog" aria-labelledby="AssessmentReportPreview" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header noPrint">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel">Review Assessment Preview</h4>
        </div>
        <div class="modal-body yesPrint">
          <div class="modal_contents">Loading...</div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Health Factor Titles Modal -->

<div class="modal fade" id="healthFactorTitles" tabindex="-1" role="dialog" aria-labelledby="healthFactorTitles" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header noPrint">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="healthFactorTitlesLabel">View Health Factors</h4>
      </div>
      <div class="modal-body yesPrint">
        <div class="modal_contents">Loading...</div>
      </div>
    </div>
  </div>
</div>
<div class="clear-fix"></div>
<div  class="noPrint">
  <%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</div>

<!--  Work arounf to Solve Print Problem in Chrome  -->
<div>-</div>
</body>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/assessmentSummary.js" />"></script>
<script>
        
	        $(document).ready(function() {
	        	$(this).on("click", '#VeteranSummaryButton', function(e){
	        		  e.preventDefault();

	        		  var modal_contents 	= $("#VeteranSummaryModal .modal_contents");

	        		  $('#VeteranSummaryModal').modal('show');
	        		  $(modal_contents).html('<i class="ajax_loading text-center"></i> Loading...');
								
	        		  var vaid = ${veteranAssessmentInfo.veteranAssessmentId};
								var modal_url 			= 'assessmentSummary/assessments/' + vaid + '/veteranSummary' ;
	        		   $.ajax({
	        			  	type : 'get',
	        			  	contentType: 'application/json',
	        			   	url : modal_url,
	        		   		success : function(r)
	        				 {  
	        					 $(modal_contents).show().html(r);
                      
                      // $(".graphicBody").length;
                      // console.log("graphArea.length" + $(".graphicBody").length);
                      
                      $(".graphicBody").each(function(){
                        var $this = $(this);
                        var json      = $.parseJSON($this.html());

                        //clear the graph area
                        $this.html("");
                        
                        graphStacked(json, $(".graphicBody"));
                        $('.bars > g').each(function() {
                          $(this).prependTo(this.parentNode);
                        });
                     });

	        				 }
                        
            	});
	        	
            
            });
						
						
						
						$(this).on("click", '#reviewAssessmentButton', function(e){
	        		  e.preventDefault();
	        		  var modal_contents = $("#AssessmentReportPreview .modal_contents");
	        		  $('#AssessmentReportPreview').modal('show');
	        		  $(modal_contents).html('<i class="ajax_loading text-center"></i> Loading...');
	        		  
	        		  var vaid = ${veteranAssessmentInfo.veteranAssessmentId};
	        		   $.ajax({
	        			  	type : 'get',
	        			  	contentType: 'application/json',
	        			   	url : 'assessmentSummary/assessments/' + vaid + '/cprsNote',
	        		   		success : function(r)
	        				 {  
	        					 $(modal_contents).show().html(r);
	        				 }
	        		});
	        	});
						

	        	$(this).on("click", '#healthFactorTitlesButton', function(e){
	        		  e.preventDefault();
	        		  $('#healthFactorTitles').modal('show'); 
	        		  
	        		  var vaid = ${veteranAssessmentInfo.veteranAssessmentId};
	        		   $.ajax({
	        			  	type : 'get',
	        			  	contentType: 'application/json',
	        			   	url : 'assessmentSummary/assessments/' + vaid + '/healthFactorTitles',
	        		   		success : function(r)
	        				 {
								  console.log(r);
	        					 
	        					 $('#healthFactorTitles .modal_contents').show().html(r);
	        				 }
	        		});
	        	});
	        	
	        	
	        	
	        	function printElement(elem, append, delimiter) {
	        		var domClone = elem.cloneNode(true);
	        		var $printSection = document.getElementById("yesPrint");
	        		if (!$printSection) {
                var $printSection = document.createElement("div");
                  $printSection.id = "yesPrint";
                  document.body.appendChild($printSection);
              }
                if (append !== true) {
                  $printSection.innerHTML = "";
                }
                else if (append === true) {
                if (typeof (delimiter) === "string") {
                  $printSection.innerHTML += delimiter;
                }
                else if (typeof (delimiter) === "object") {
                  $printSection.appendChlid(delimiter);
                }
	        		}
	        			$printSection.appendChild(domClone);
	        		}
	        });
        
        </script>
        
        
        
        <script>
      //TODO: graphStart has to be used to make the bar values start from non-zero numbers 
      //TODO: for the colors of each bar, what happens when we have more than 6 intervals?
       
     function graphStacked(graphObj, containerDivClass){
       
       var container = $(containerDivClass);
       // Load Objects
       var graphparams = graphObj.data;
       //graphStart is never used
       var graphStart   = graphparams.graphStart;
       var ticks        = graphparams.ticks;
       
       var legends = [];
       var d3DataSet = [];
       var scoresInterval;
       var lastInterval = graphStart;
       $.each(graphparams.intervals, function(name, intervalEnd){
            legends.push(name);
            d3DataSet.push([{x:name, y:intervalEnd}]);  // adds 	[Object { x="None", y=1}]
            if(graphparams.score > lastInterval && graphparams.score <= intervalEnd){
                scoresInterval = name;
            }
            lastInterval = intervalEnd;
       }); 
       
       
       //get the name of the interval where the score ended up
       
       //TODO: Can we use the moduleTemplateTitle block to hold the score stuff?  Also the wrong title is being used in this block 
       //it should be the title in the moduleTemplateTitle above the score and the graphObj.title should be over the graph.
       
       // Update body with title block
       var titleBlock = "<div class='scoreBlock text-center'><h3>"+ graphObj.title +"</h3><h4>"+graphparams.score+"</h4><h5>" + scoresInterval +"</h5></div>";
       container.append(titleBlock);
       console.log("--------");
       $(".graphicBody").parent().parent().addClass("graphicBlock");
       
       // $( ".scoreBlock" ).insertBefore( ".graphSection" );

 
        var margins = {
                top: 46,
                left: 15,
                right: 15,
                bottom: 0
            },
            containerWidth    = 550,
            containerHeight   = 100,
            legendPanel       = {
                                  width: containerWidth - margins.left - margins.right
                                 },
            width       = containerWidth - margins.left - margins.right,
            height      = containerHeight - margins.top - margins.bottom,
            value;
            
            // Settings
            xMax            = d3.max(ticks),
            xCurrent        = graphparams.score, //4,
            ticks           = ticks, //[0, 4, 10, 20, 27],
            colors          = ['#cfd8e0'  , '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65'],
            series          = legends,
            dataset         = d3DataSet,
            stack = d3.layout.stack();
           

        stack(dataset);
        var dataset = dataset.map(function(group) {
            return group.map(function(d) {
                // Invert the x and y values, and y0 becomes x0
                return {
                    x: d.y,
                    y: d.x,
                    x0: d.y0
                };
            });
        }),
        svg = d3.select(containerDivClass)
            .append('svg')
                .attr('width', width + margins.left + margins.right)
                .attr('height', height + margins.top + margins.bottom + 60)
            .append('g')
                .attr('class', 'bars')
                .attr('transform', 'translate(' + margins.left + ',' + margins.top + ')'),
       
        xScale = d3.scale.linear()
            .domain([0, xMax])
            .range([0, width]),
        notes = dataset[0].map(function(d) { return d.y; }),
        _ = console.log(notes),
        yScale = d3.scale.ordinal()
            .domain(notes)
            .rangeRoundBands([0, height], .1),
        xAxis = d3.svg.axis()
            .scale(xScale).tickValues(ticks)
            .orient('bottom'),
        yAxis = d3.svg.axis()
            .scale(yScale)
            .orient('left'),
        
        
        // Bars Start
        groups = svg.selectAll('g')
            .data(dataset)
            .enter()
              .append('g')
                .attr('class', function(d, i) {
                    return "bar_" + [i];
                })
                .style('fill', function(d, i) {
                    return colors[i];
                })
                
        rects = groups.selectAll('rect')
            .data(function(d) { return d; })
            .enter()
                .append('rect')
                    .attr('x', 0)
                    .attr('y', 0)
                    .attr('height', function(d) { return yScale.rangeBand(); })
                    .attr('width', function(d) { return xScale(d.x); })

      var xPos = parseFloat(width / xMax) * xCurrent;
      var yPos = parseFloat(xCurrent) + yScale.rangeBand() /7;


      d3.select('#tooltip')
          .style('margin-left', xPos + 'px')
          .style('margin-top', yPos + 'px')
          .style('position', 'absolute')
          .select('#value')
          .text(xCurrent);

        // xAxis postion
        svg.append('g')
            .attr('class', 'axis')
            .attr('transform', 'translate(0,' + height + ')')
            .call(xAxis);

        
        // yAxis postion
        svg.append('g')
            .attr('class', 'axis')
            .call(yAxis);

        // legend Rect
        svg.append('rect')
            .attr('fill', 'white')
            .attr('width', legendPanel.width)
            .attr('height', 5 * dataset.length)
            .attr('x', 0)
            .attr('y', 100);

         // legend Text & Box
        series.forEach(function(s, i) {
            svg.append('text')
                .attr('fill', 'black')
                .attr('x', i * 105 + 15)
                .attr('y', 100)
                .text(s);
            svg.append('rect')
                .attr('fill', colors[i])
                .attr('width', 10)
                .attr('height', 10)
                .attr('x', i * 105)
                .attr('y', 90);
        });
        
        //add footer if we were given one
        if(graphObj.footer != null && graphObj.footer != ""){
            container.append("<div class='text-center'><h5>" + graphObj.footer +"</h5></div>");
        }
     }
      
        
        
        $( document ).ready(function() {
            //var graphArea = $(".graphicBody");
            //var json = $.parseJSON(graphArea.html());
            //clear the graph area
            //graphArea.html("");
            
            //graphStacked(json, ".graphicBody");
            //  $('.bars > g').each(function() {
            //  $(this).prependTo(this.parentNode);
            //});
          });




    </script>
</html>
