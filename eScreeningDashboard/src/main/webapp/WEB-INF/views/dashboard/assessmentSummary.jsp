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
    
    <link href="<c:url value="/resources/css/partialpage/assessmentSummary.css" />" rel="stylesheet" type="text/css" />
    <title>Assessment Summary</title>
</head>
<body>
<div class="nonPrintableArea">
<a href="#skip" class="offscreen">Skip to main content</a>
<div id="outerPageDiv" class="nonPrintableArea">
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

<div class="container left-right-shadow nonPrintableArea">
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
              	<c:if test="${veteranAssessmentInfo.isSensitive}">
              		<c:out value="XX/XX/XXXX"/>
				</c:if>
				<c:if test="${!veteranAssessmentInfo.isSensitive}">
                	<fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${veteranAssessmentInfo.birthDate}" />
                </c:if>
              </div>
            </div>
            <div class="col-md-2 text-right"> SSN-4
              <div class="txt_lable_lg text-right">
              		<c:if test="${veteranAssessmentInfo.isSensitive}">
              			<c:out value="XXXX"/>
					</c:if>
					<c:if test="${!veteranAssessmentInfo.isSensitive}">
                		<c:out value="${veteranAssessmentInfo.ssnLastFour}" />
                	</c:if>
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
						<div class="text-right">
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
                <c:if test="${not empty veteranAssessmentInfo.veteranIen}">
                  <c:if test="${isCprsVerified}">
                    <input id="saveToVistaModal" name="saveToVistaModal" value="Save To VistA" type="button" class="btn btn-primary" data-toggle="modal" data-target="#save_to_vista_modal" <c:if test="${veteranAssessmentInfo.assessmentStatusName != 'Complete'}">disabled="disabled"</c:if> />
                  </c:if>
                  <c:if test="${!isCprsVerified}">
                    <input id="saveToVistaModal" name="saveToVistaModal" value="Save To VistA" type="button" class="btn btn-primary" disabled data-toggle="modal" data-target="#save_to_vista_modal" />
                  </c:if>
                </c:if>
                <input id="healthFactorTitlesButton" name="healthFactorTitlesButton" value="Health Factor Titles" type="button" class="btn btn-primary"  data-toggle="modal" data-target="health_factor_titles_modal" />
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group text-right">
                <input id="saveButton" name="saveButton" value="Save" type="submit" class="btn btn-primary" />
                <input id="cancelButton" name="cancelButton" value="Cancel" type="submit" class="btn btn-default btn-default-black" />
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
                   

          <br />
        </div>
      </div>
    </div>
    <!-- row --> 
  </form:form>
</div>
</div>
         <!-- ### Modal Section Start Here ### -->
          
          


          <!-- Modal Veteran Summary  -->
          <div class="custom_modal veteran_summary_modal" >
            <div class="modal fade  modal-wide" id="VeteranSummaryModal" tabindex="-1" role="dialog" aria-labelledby="VeteranSummaryModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header nonPrintableArea">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="VeteranSummaryModalLabel">Veteran Summary</h4>
                  </div>
                  <div class="modal-body printableArea">
                      <div align="right" class="nonPrintableArea">
                        <button class="btn btn-primary print"><span class=" glyphicon glyphicon-print"></span> Print </button>
                      </div>
                      <div class="modal_contents">Loading...</div>
					  <div class="timeSeries"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
                    
                    
          <!-- Modal Review Assessment Preview -->
          <div class="custom_modal" >
            <div class="modal fade modal-wide" id="AssessmentReportPreview" tabindex="-1" role="dialog" aria-labelledby="AssessmentReportPreview" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header nonPrintableArea">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Review Assessment Preview</h4>
                  </div>
                  <div class="modal-body printableArea">
                      
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
                <div class="modal-header nonPrintableArea">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="healthFactorTitlesLabel">View Health Factors</h4>
                </div>
                <div class="modal-body printableArea">
                  <div class="modal_contents">Loading...</div>
                </div>
              </div>
            </div>
          </div>
          
<div class="clear-fix"></div>
<div class="nonPrintableArea">
  <%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</div>

  <!--  Work around to Solve Print Problem in Chrome  -->
  <div>-</div>
</div>
<div class="printable"></div>
</body>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/assessmentSummary.js" />"></script>
<script>
//TODO: move all the below JS of to assessmentSummary.js
// Cache all classes and IDs  
$(document).ready(function() {              
    $(this).on("click", '#VeteranSummaryButton', function(e){
        e.preventDefault();
        var modal_contents 	= $("#VeteranSummaryModal .modal_contents");
        $('#VeteranSummaryModal').modal('show');
        $(modal_contents).html('<i class="ajax_loading text-center"></i> Loading...');

 		var vaid = ${veteranAssessmentInfo.veteranAssessmentId};
 		var vid = ${veteranAssessmentInfo.veteranId};
 		 
 	    var modal_url = 'assessmentSummary/assessments/' + vaid + '/veteranSummary' ;
 	    $.ajax({
		  	type : 'get',
		  	contentType: 'application/json',
		   	url : modal_url,
	   		success : function(r){  
	   		    $(modal_contents).show().html(r);
	            $(".graphicBody").each(function(graphId){
	            	
	                //TODO: mayb we should add a "loading..." icon while we get data from server
	                
	            	var $this = $(this);
	                var graphObj  = $.parseJSON($this.html());
					
	                $this.html(""); //clear the graph area
	        		var stackGraphParams = processIntervals(graphObj);
	        		var timeSeriesParams = processIntervals(graphObj);
	                
					
	                var parentDiv = $this.parents(".moduleTemplate");
	                parentDiv.addClass("graphicBlock");
	                var titleContainer = parentDiv.children(".moduleTemplateTitle");
	                var graphContainer = parentDiv.children(".graphSection");
	                var descriptionContainer = parentDiv.children(".moduleTemplateText");
	            	  
	        		// Update title block to contain the scoring
	        		titleContainer.wrap("<div class='scoreBlock text-center'>");
	        		titleContainer.parent().append("<div><h4>" + stackGraphParams.score + "</h4><h5>" + stackGraphParams.scoresInterval + "</h5></div>");
	        		
	        		//Start adding to the graphic block with the graph's title 
	        		graphContainer.prepend("<div class='graphHeader'>" + graphObj.title + "</div>");
	        		
	        		//Add d3 graph
	                var graphContainerId = "graph_" + graphId;
	                var graphSelector = "#" + graphContainerId;
	                graphContainer.children(".graphicBody").prop("id", graphContainerId)
	                
	                //get time series for the variable
	                // Call timeSeries JSON
					$.ajax({
						type : 'get',
						url : 'assessmentSummary/assessmentvarseries/' + vid + '/' + graphObj.varId + '/' + graphObj.numberOfMonths,
						success : function(points){  
							
							//append correct graph type given the number of historical results for the variable
							if(hasMoreThanOne(points)){
								appendTimeSeries(graphSelector, timeSeriesParams, points);
							}
							else{
								appendStackGraph(graphSelector, stackGraphParams);
							}
			                
							//Add footer if we were given one
							if(graphObj.footer != null && graphObj.footer != ""){
								graphContainer.append("<div class='graphFooter text-center'>" + graphObj.footer +"</div>");
							}									
						},
				  		error: handleError
					});
	            });
	            
			},
          	error: handleError
 	    });
 	    
 	   function handleError(xhr, exception, errorThrown) {
           data = "[" + xhr.responseText + "]";
           data = $.parseJSON(data);
     
           var userMessage       = [];
           var developerMessage  = [];
           for (var i = 0; i < data.length; ++i) {                    
             for (var j = 0; j < data[i].errorMessages.length; j++) {
               errorMessages = data[i].errorMessages[j];
               userMessage.push("<div class='userErrorMessage'>" + [errorMessages.description] + "</div>");
             }
             if(data[i].developerMessage.length > 0){
               result =          "<div class='developerErrorIDMessage'>" + "<strong>ID:</strong> " + [data[i].id] + "</div>";
               result = result + "<div class='developerErrorMessage'>" + "<strong>Developer Message:</strong> " + [data[i].developerMessage] + "</div>";
               result = result + "<div class='logErrorMessage'>" + "<strong>Log Message:</strong> " + [data[i].logMessage] + "</div>";
               developerMessage.push(result);
             }
           }
           var panelTemplate = userMessage;
               panelTemplate = panelTemplate + '<div class="panel-danger-system detailedErrorMessageBlock"><div class="panel-group" id="veteranSummaryAccordion"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title"> <a data-toggle="collapse" data-parent="#veteranSummaryAccordion" href="#collapseOne2"> System Error <span class="label label-danger">Click here for more error details</span> </a> </h4></div><div id="collapseOne2" class="panel-collapse collapse"><div class="panel-body"><div class="detailedErrorMessage">';
               panelTemplate = panelTemplate + developerMessage;
               panelTemplate = panelTemplate + '</div></div></div></div></div></div>'
           
           $(modal_contents).show().html(panelTemplate);
 	   }
 	   
 	  function hasMoreThanOne(obj) {
 		    
 		    if (Object.keys) { 
 		        return Object.keys(obj).length > 1;
 		    }

 		    var c = 0;
 		    for (field in obj) {
 		        if(c > 1){ return true; }
 		        c++;
 		    }

 		    return false;
 		}
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
   				 },
          error: function (xhr, exception, errorThrown) {
                data = "[" + xhr.responseText + "]";
                data = $.parseJSON(data);
          
                var userMessage       = [];
                var developerMessage  = [];
                for (var i = 0; i < data.length; ++i) {                    
                  for (var j = 0; j < data[i].errorMessages.length; j++) {
                    errorMessages = data[i].errorMessages[j];
                    userMessage.push("<div class='userErrorMessage'>" + [errorMessages.description] + "</div>");
                  }
                  if(data[i].developerMessage.length > 0){
                    result =          "<div class='developerErrorIDMessage'>" + "<strong>ID:</strong> " + [data[i].id] + "</div>";
                    result = result + "<div class='developerErrorMessage'>" + "<strong>Developer Message:</strong> " + [data[i].developerMessage] + "</div>";
                    result = result + "<div class='logErrorMessage'>" + "<strong>Log Message:</strong> " + [data[i].logMessage] + "</div>";
                    developerMessage.push(result);
                  }
                }
                var panelTemplate = userMessage;
                    panelTemplate = panelTemplate + '<div class="panel-danger-system detailedErrorMessageBlock"><div class="panel-group" id="veteranSummaryAccordion"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title"> <a data-toggle="collapse" data-parent="#veteranSummaryAccordion" href="#collapseOne2"> System Error <span class="label label-danger">Click here for more error details</span> </a> </h4></div><div id="collapseOne2" class="panel-collapse collapse"><div class="panel-body"><div class="detailedErrorMessage">';
                    panelTemplate = panelTemplate + developerMessage;
                    panelTemplate = panelTemplate + '</div></div></div></div></div></div>'
                
                $(modal_contents).show().html(panelTemplate);
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
   	       success : function(r){
   			$('#healthFactorTitles .modal_contents').show().html(r);
   		   }
   		});
   	});
	   	
	   	
	   // Todo - Need to check if that's used anywhere 	
   	function printElement(elem, append, delimiter) {
   		var domClone = elem.cloneNode(true);
   		var $printSection = document.getElementById("printableArea");
   		if (!$printSection) {
          var $printSection = document.createElement("div");
            $printSection.id = "printableArea";
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
	/**
	 * Initializes basic graph parameter using given graph parameters.
	 * Uses fields: 
	 *	 intervals - array of map from interval name to the starting value for that interval
	 *	 score - the score for the assessment we are summarizing
	 *	 maxXPoint - the maximum value of any score (end of last interval), this can be undefined if the last interval will be the end of the graph
	 * Sets fields:
	 *	 legends - names for each interval
	 *	 dataset - the array of objects containg x,y which are used by the stack graph to set intervals
	 *	 scoresInterval - name of the interval where the score is found
	 *	 graphStart - the minimum value of any score (start of first interval)
	 */
	function processIntervals(graphParams){
		graphParams.legends = [];
		graphParams.dataset = [];
		graphParams.scoresInterval;
		graphParams.graphStart = 0;
		
		var prevInterval, prevName;
		
		$.each(graphParams.intervals, function(name, intervalStart){
			graphParams.legends.push(name);

			//skip the first one
			if(prevInterval != null){
				graphParams.dataset.push([{x:"", y:intervalStart}]);
			}
			else{
				graphParams.graphStart = intervalStart;
			}
			
			if(prevInterval != null && graphParams.score >= prevInterval && graphParams.score < intervalStart){
				graphParams.scoresInterval = prevName;
		    }
			prevInterval = intervalStart;
			prevName = name;
		}); 
		
		//check if scoreInterval is in the last interval
		if(graphParams.scoresInterval == null && graphParams.score >= prevInterval){
			graphParams.scoresInterval = prevName;
		}
		
		//if this is null then that means the final interval will end the graph (there must be a tick for this last point)
		if(graphParams.maxXPoint != null){
			graphParams.dataset.push([{x:"", y:graphParams.maxXPoint}]);
		}
		return graphParams;
	}
	
	
    //TODO:
	  // 1. for the colors of each bar, what happens when we have more than 6 intervals?  We need the start color and then end color and then
	     // we take the number of intervals and calculate the colors needed to get from the start color to the end color.
	  // 2. the y axis label should not be given 
	  // 3. the score is not showing up in the graph
       
    var colors		= ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65'];
	
	function appendStackGraph(parentSelector, graphParams){

		var ticks = graphParams.ticks;
		
        //Set d3 graph attributes
	    var margins = {
		          top: 46,
		          left: 15,
		          right: 15,
		          bottom: 0
		      },
		      containerWidth    = 450,
		      containerHeight   = 100,
		      legendPanel       = {
		                            width: containerWidth - margins.left - margins.right
		                           },
		      width       = containerWidth - margins.left - margins.right,
		      height      = containerHeight - margins.top - margins.bottom,
		      value;
		      
		      // Settings
		      xMax            = d3.max(ticks),
		      xCurrent        = graphParams.score, //4,
		      ticks           = ticks, //[0, 4, 10, 20, 27],
		      // colors          = ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65', '#0d3054', '#0a2845', '#082038', "#000000"],
		      series          = graphParams.legends,
		      dataset         = graphParams.dataset,
		      pointerColor    = '#0f3a65',
		      pointerWidth    = 36,
		      pointerHeight   = 36,
		      stack = d3.layout.stack();
		
	    stack(dataset);
	    var dataset = dataset.map(
		    function(group) {
			    return group.map(function(d) {
			        // Invert the x and y values, and y0 becomes x0
			        return {
			            x: d.y,
			            y: d.x,
			            x0: d.y0
			        };
			    });
			}),
	
			svg = d3.select(parentSelector)
			    .append('svg')
			        .attr('width', width + margins.left + margins.right)
			        .attr('height', height + margins.top + margins.bottom + 60)
			    .append('g')
			        .attr('class', 'bars')
			        .attr('transform', 'translate(' + margins.left + ',' + margins.top + ')'),
			
			xScale = d3.scale.linear()
			    .domain([graphParams.graphStart, xMax])
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
			            .attr('width', function(d) { return xScale(d.x); });

		var xPos = parseFloat(width / (xMax - graphParams.graphStart)) * ( xCurrent - graphParams.graphStart ) ;
		var yPos = 0;
		
		pointer = svg.append('rect')
        .attr('fill', pointerColor)
        .attr('width', pointerWidth)
        .attr('height', pointerHeight)
        .attr('x', xPos - (pointerWidth/2))
        .attr('y', -40)
        .attr('class', 'pointerBlock');
    

	    svg.append('rect')
	        .attr('fill', 'black')
	        .attr('width', 1)
	        .attr('height', 5)
	        .attr('x', xPos)
	        .attr('y', -5)
	        .attr('class', 'pointerLine');
	
	    svg.append('text')
	            .attr('fill', 'white')
	            .attr('font-size', '20')
	            .attr('font-weight', 'bold')
	            .style("text-anchor", "middle")
	            .attr('x', xPos)
	            .attr('y', -15)
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
		  textWidth = 15;
		   series.forEach(function(s, i) {
			   text = svg.append('text')
		          .attr('fill', 'black')
				  .attr('width', 100)
		          .attr('x', textWidth)
		          .attr('y', 100)
		          .attr('font-size', '10')
		          .text(s);
			   box = svg.append('g')
			   	  .append('rect')
		          .attr('fill', colors[i])
		          .attr('width', 10)
		          .attr('height', 10)
		          .attr('x', textWidth - 15)
		          .attr('y', 90);
		      	  textWidth += parseFloat(text.node().getComputedTextLength())  + 30;
              console.log("Parse 8----");
              console.log(text.node().getComputedTextLength());
		  });
		  
		  // Fix graphic bar issue
		  $(parentSelector).find('.bars > g')
		      .each(function() {
		          $(this).prependTo(this.parentNode);
		  });
	}

   
	function appendTimeSeries(parentSelector, graphParams, points){
		
    	$(parentSelector).addClass("timeSeries");

		var ticks = [];
		var maxValue;
		
		var series   = graphParams.legends;
		
		$.each(points, function(date, valueStr){
			//TODO: Add check if can't be parsed
			var value = parseFloat(valueStr);
			if(maxValue == null || maxValue < value){
				maxValue = value;
			}
			
			ticks.push({
				'date': date.split(" ")[0], //parse out the date (removing time)
				'value': value
			});
		});
		
		ticks.reverse(); // reverse the order on the ticks 
		//var maxValue = d3.max(ticks , function(d) { return +d.value;} );
		
		// Vars
		var w = 450,
			h = 300,
			margin = {
				top: 20,
				bottom: 60,
				left: 30,
				right: 140
			},
			xRangeStart = 25,		// Move the x axis to right
			yStartPoint = 0; 		// Start Point for y axis
			
					
		// var colors          = ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65']; // TODO - May need to swap with this list 
		colors.reverse();
		
		// In progress
		//var graphparams.intervals = {"2015-01-23T02:31:09.000+0000":"14.0"};
			
		// Static Vars
		var legendTitle = "My Score"; 
		
		var xLegendTextPosition = w - 115,
			xLegendRectPosition = w - 130;
	
		var width = w - margin.left - margin.right,
			height = h - margin.top - margin.bottom;
	
		var svg = d3.select(parentSelector).append("svg")
			.attr("id", "chart")
			.attr("width", w)
			.attr("height", h);
	
		var chart = svg.append("g")
			.classed("display", true)
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
		var dateParser = d3.time.format("%m/%d/%Y").parse;
		var dateArr = [];
		
		// Format date
		function formatDate (input) {
		  var datePart = input.match(/\d+/g),
		  month = datePart[0],
		  day = datePart[1],
		  year 	= datePart[2].substring(2);
		  return month+'/'+day+'/'+year;
		}

		for (var i = 0; i < ticks.length; i++) {
			dateArr.push(formatDate(ticks[i].date));
		}
		
		var x = d3.scale.linear().domain([0, ticks.length]).range([xRangeStart, width]);

		var y = d3.scale.linear()
			.domain([yStartPoint, d3.max(ticks, function (d) {
			return +d.value;
		})])
			.range([height, 0]);
		
		var xAxis = d3.svg.axis()
			.scale(x)
			.orient("bottom")
			.tickFormat(function(d, i) {
				return dateArr[i];
			}).ticks(ticks.length);

		var yAxis = d3.svg.axis()
			.scale(y)
			.orient("left")
			.ticks(5);
	

		var line = d3.svg.line().interpolate("cardinal")
			.x(function (d, i) {
			return x(i);
		})
			.y(function (d) {
			return y(d);
		});		
		
		function plot(params) {
			
			var intervals 	= params.data.intervals,
				ticks 		= params.data.ticks;

			var valueArr = [];
			
			for (var i = 0; i < ticks.length; i++) {
				valueArr.push(ticks[i].value);
			}

						
			this.append("g")
				.classed("x axis", true)
				.attr("transform", "translate(0," + height + ")")
				.call(params.axis.x)
				.selectAll("text")  
				.style("text-anchor", "end")
				.attr("dx", "-.8em")
				.attr("dy", ".15em")
				.attr("transform", function(d) {
					return "rotate(-65)" 
				});
	
			this.append("g")
				.classed("y axis", true)
				.attr("transform", "translate(0,0)")
				.call(params.axis.y);
			
			
			// Enter Plot Started Here
			this.selectAll(".trendline")
				.data(ticks)
				.enter()
				.append("path")
				.classed("trendline", true);
	
			this.selectAll(".point")
				.data(ticks)
				.enter()
				.append("circle")
				.classed("point", true)
				.attr("r", 5);
	
			this.selectAll(".pointText")
				.data(ticks)
				.enter()
				.append("text")
				.classed("pointTextValue", true)
				.text( function (d) {
					return +d.value;
				});
	

			// Update Plot Started Here
			this.selectAll(".trendline")
				.attr("d", function (d) {
				return line(valueArr);
			});
			this.selectAll(".point")
				.attr("cx", function (d, i) {
				return x(i);
			})
				.attr("cy", function (d) {
				return y(+d.value);
			});
			this.selectAll(".pointTextValue")
				.attr("x", function (d, i) {
				return x(i);
			})
				.attr("y", function (d) {
				return y(+d.value - 3);
			});
	
			// Exit Plot Started Here
			this.selectAll(".trendline")
				.data(ticks)
				.exit()
				.remove();
			this.selectAll(".point")
				.data(ticks)
				.exit()
				.remove();
	
			// Add Legend Started Here 
			var legend = this.append("g")
				.attr("class", "legend")
				.attr("height", h)
				.attr("width", w)
				.attr('transform', 'translate(-20, 10)');
	
			legend.selectAll('rect')
				.data(graphParams.dataset)
				.enter()
				.append("rect")
				.attr("x", xLegendRectPosition)
				.attr("y", function (d, i) {
				return i * 35;
			})
				.attr("width", 8)
				.attr("height", 30)
				.style('fill', function(d, i) {
			            return colors[i];
			    });
	
			// Add Legend Started Here
			graphParams.legends.reverse();
			
			legend.append("g")
				.attr("class", "legendBar")
				.attr("transform", "translate(" + xLegendTextPosition + ", 0)")
				.selectAll('text')
				.data(graphParams.dataset)
				.enter()
				.append("text")
				.attr("x", xLegendTextPosition)
				.attr("y", function (d, i) {
				return i * 35 + 9;
			})
				.attr("dy", 0)
				.text(function (d, i) {
					
					var text = graphParams.legends[i];
					return text;
				})
			.call(wrap, 100);
	
			legend.append("circle")
				.classed("point", true)
				.attr("r", 5)
				.attr("cx", xLegendRectPosition+4 )
				.attr("cy", -12);
			
			 legend.append("text")
				.classed("pointTextValue", true)
				.attr("x", xLegendTextPosition)
				.attr("y", -2)
				.attr("width", 100)
				.text(legendTitle);
	
	
			// Bar Legend Start Here
			// Create X Scale for bar graph
			var xScale = d3.scale.ordinal()
				.domain([20])
				.rangeRoundBands([0, 20]);
	
			//Create Y Scale for bar graph
			var yScale = d3.scale.linear()
				 .domain([yStartPoint, d3.max(ticks, function (d) {
				 return +d.value;
			})])
				.range([height, 0]);
	
			// Add Rectangles
			this.append('g')
				.attr("class", "bars")
				.selectAll(".bar")
				.data(graphParams.dataset.reverse())
				.enter()
				.append("rect")
				.attr("class", "bar")
				.style('fill', function(d, i) {
			            return colors[i];
			     })
				.attr("x", 0)
				.attr("y", function (d, i) {
					return yScale(+d[0].y);
			})
				.attr("width", xScale.rangeBand()) //returns rangeRoundBands width
				.attr("height", function (d) {
					if( maxValue >= +d[0].y){
						return height - yScale(+d[0].y) + 0;
					}else{
						return height - yScale(+maxValue) + 0 ;
					}
				});
	
	
			// Text Wrapper
			function wrap(text, width) {
				text.each(function () {
					var text = d3.select(this),
						words = text.text().split(/\s+/).reverse(),
						word,
						line = [],
						lineNumber = 0,
						lineHeight = 1.1, // ems
						y = text.attr("y"),
						dy = parseFloat(text.attr("dy")),
						tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
					while (word = words.pop()) {
						line.push(word);
						tspan.text(line.join(" "));
						if (tspan.node().getComputedTextLength() > width) {
							line.pop();
							tspan.text(line.join(" "));
							line = [word];
							tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
						}
					}
				});
			}
	
	
	
		} // End of plot function
	
	
		// Call Plot
		plot.call(chart, {
			data: {
				'intervals': graphParams.intervals,
				'ticks': ticks
			},
			axis: {
				x: xAxis,
				y: yAxis
			}
		});
		// Reverse bars	
		//$('.bars > rect').each(function () {
		//	$(this).prependTo(this.parentNode);
		//});
	}
		
</script>
</html>