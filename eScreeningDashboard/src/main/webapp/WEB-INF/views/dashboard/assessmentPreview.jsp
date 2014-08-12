<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
<link href="resources/css/partialpage/assessmentPreview.css" rel="stylesheet" type="text/css">
<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" />
<link href="resources/css/common-ui-styles/fonts.css" rel="stylesheet" type="text/css">
<link href="resources/css/print-preview.css" rel="stylesheet" type="text/css">
<title>Assessment Preview</title>
<style type="text/css">
/* for print preview styles  */
	@page  
	{ 
	   
	
	    /* this affects the margin in the printer settings */ 
	    size:10in 12in; margin: 2cm 4cm 2cm 2cm;
	} 	 
   @media print
    {
    	.non-printable { display: none;}
    	body{
            		  overflow:visible !important;
            		  float: none !important;	
            		  position:relative !important;	
            		  font-size: 20px;
             }
    } 
</style>
<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="mainDiv" class="mainDiv">
<div id="print-modal-controls_" class="non-printable">
<div align="right" class="non-printable">
					<button class="btn btn-primary" onClick="window.print();"><span class=" glyphicon glyphicon-print"></span> Print Assessment</button>
				</div>
</div><br/><br/>
<div id="logo-container" class="logoPos">
                <!-- Link Back to Home, wrapping Logo -->
                <a href="#">
                        <img src="resources/images/VALOGO_clear.png" alt="Department of Veterans Affairs" class="valogo">
                </a>
</div><br/>
<!-- End Logo Link -->
            <!-- Headline and Application text -->
            <div class="headerText" >
				<span id="agencyName"> 
					Department of Veterans Affairs
				</span>
			</div>
			<div class="headerText">
				<span id="applicationName"> 
					OEF/OIF Health Assessment
				</span>
					<div class="cesamhLogo">
						<img src="resources/images/cesamh_new_logo.png" class="cesamh">
					</div>
</div>
<br/>
<br/>
<hr class="hrWidth">
<br/>
<table class="reportTable">
    <tr>
        <th class="section" colspan="2">Veteran Information</th>
    </tr>
    <tr>
        <th>Last</th>
        <td>${veteranAssessment.veteran.lastName}</td>
    </tr>
    <tr>
        <th>First</th>
        <td>${veteranAssessment.veteran.firstName}</td>
    </tr>
    <tr>
        <th>Middle</th>
        <td>${veteranAssessment.veteran.middleName}</td>
    </tr>
    <tr>
        <th>Last 4 SSN</th>
        <td>${veteranAssessment.veteran.ssnLastFour}</td>
    </tr>
    <tr>
        <th class="section" colspan="2">Assessment Information</th>
    </tr>
    <tr>
        <th>Status</th>
        <td>${veteranAssessment.assessmentStatus.name}</td>
    </tr>
    <tr>
        <th>Date</th>
        <td>${veteranAssessment.dateCompleted}</td>
    </tr>
    <tr>
        <th>Duration</th>
        <td>${veteranAssessment.duration}</td>
    </tr>
    <tr>
        <th>Progress</th>
        <td>${veteranAssessment.percentComplete}%</td>
    </tr>
</table>
<br/>
<c:forEach var="survey" items="${surveyList}">
    <h1>${survey.name}</h1>

    <c:forEach var="surveyPage" items="${survey.surveyPageList}">
        <h2>${surveyPage.title}</h2>
		<hr class="hrWidth">
        <c:forEach var="measure" items="${surveyPage.measures}" varStatus="surveyPageMeasureStatus">
           <div class="left" class="measureTxtDiv">
           <span class="measureText">Q. ${measure.measureText}</span></div>
            <c:choose>
                <c:when test="${measure.measureType.name == 'freeText' or measure.measureType.name == 'readOnlyText'}">
                <div class="left0">
                    <c:forEach var="measureAnswer" items="${measure.measureAnswerList}">
                        <span class="answerTextValue">${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].textValue}</span>
                        <span class="answerTextValue">${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].numberValue}</span>
                    </c:forEach>
                </div>
                </c:when>
                <c:when test="${measure.measureType.name == 'selectMulti'}">
                    <div class="left"><span class="measureText">(mark all that apply)</span></div><br/>
                    
                    <ul class="answerTypeSelectMulti">
                        <c:forEach var="measureAnswer" items="${measure.measureAnswerList}">
                            <li>
                                <div class="left1">
                                <c:choose>
                                    <c:when test="${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].booleanValue}">
                                        <img src="<c:url value="/resources/images/toggle_yes.png" />" alt="Yes"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/resources/images/toggle_no.png" />" alt="No"/>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                <div class="right1">
                                <span class="optionText">${measureAnswer.answerText}</span>

                                <span class="answerOtherValue">${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].otherValue}</span>
                           		</div>
                            </li><br/>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${measure.measureType.name == 'selectOne'}">
                 <div class="left">   <span class="measureText">(mark one)</span></div><br/>

                    <ul class="answerTypeSelectOne">
                        <c:forEach var="measureAnswer" items="${measure.measureAnswerList}">
                            <li>
                            	<div class="left2">
                                <c:choose>
                                	
                                    <c:when test="${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].booleanValue}">
                                        <img src="<c:url value="/resources/images/radio_yes.png" />" alt="Yes"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/resources/images/radio_no.png" />" alt="No"/>
                                    </c:otherwise>
                                   
                                </c:choose>
                                 </div>
                               <div class="right1"> <span class="optionText">${measureAnswer.answerText}</span>
                                	<span class="answerOtherValue">${(surveyMeasureResponseMap[measureAnswer.measureAnswerId])[0].otherValue}</span>
                                </div>
                            </li><br/>
                        </c:forEach>
                    </ul>
                </c:when>


                <c:when test="${measure.measureType.name == 'tableQuestion'}">
                    <ul class="answerTypeSelectOne">
                        <c:forEach var="measureAnswer" items="${measure.measureAnswerList}">
                            <c:forEach var="response" items="${surveyMeasureResponseMap[measureAnswer.measureAnswerId]}">
                                <li>
                                 <c:choose>
                                	<c:when test="${response.textValue == true}">
                                		<span class="answerOtherValue">None</span>
                                	</c:when>
                                	<c:otherwise>
                                		<span class="answerTextValue">${response.textValue}</span>
                                 </c:otherwise>
                                 </c:choose>
                                </li>
                            </c:forEach>
                        </c:forEach>
                    </ul>
                </c:when>

            </c:choose>
            <br/>
        </c:forEach>

    </c:forEach>
</c:forEach>
</div>
</body>
</html>