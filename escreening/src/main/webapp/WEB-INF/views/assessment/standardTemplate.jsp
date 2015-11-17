<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!doctype html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->
	<head>
		<title>Measures - Identification</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
		
		<meta name="apple-mobile-web-app-capable" content="yes">
    <!-- <meta name="viewport" content="user-scalable=no,width=device-width" /> -->

		<!-- FAVICON -->
		<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
		<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />
		<link rel="apple-touch-icon" href="resources/images/favico_va_310x310.png" />
		<link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
		<link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
		<link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
		
		<link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/common-palette.css">
		<link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/fontfaces.css">
		<link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/forms.css">
		<link rel="stylesheet" type="text/css" href="resources/css/assessmenttemplate.css">
		<link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/assessment-measures-master.css">
		
		<!--  <link rel="stylesheet" type="text/css" href="resources/css/measuresWIP.css">  -->
		<link rel="stylesheet" type="text/css" href="resources/css/measuresWIP_new.css">
		
		<link rel="stylesheet" type="text/css" href="resources/css/checkswitch.css">
		<link rel="stylesheet" type="text/css" href="resources/css/formButtons.css">
		<link rel="stylesheet" type="text/css" href="resources/css/jquery/smoothness/jquery-ui.min.css">
		<link href="resources/css/mobileStyle/mediaQueryMain.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="resources/css/assessment_new.css">
		<link rel="stylesheet" type="text/css" href="resources/css/common.css">
		<link rel="stylesheet" type="text/css" href="resources/editors/vendors/textAngular/textAngular.css">
		
		<script src="resources/js/jquery/jquery-1.10.2.min.js"></script>
		<script src="resources/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
		<script type="text/javascript" src="resources/js/measures/measures-completion.js"></script>
		<script type="text/javascript" src="resources/js/measures/measures-formbuilder.js"></script>
		<script type="text/javascript" src="resources/js/measures/measures-validations.js?v=12"></script>
		<script type="text/javascript" src="resources/js/measures/measures-requestbuilder.js"></script>
		<script type="text/javascript" src="resources/js/measures/measures-integrated.js"></script>		
	</head>
	<body>
		<div class="top_header_message">
		    <div class="top_header">
		        
		    </div>
		</div>


		<a href="#skip" class="offscreen">Skip to main content</a>
		<div id="outerPageDiv">
			<%@ include file="/WEB-INF/views/partialpage/surveyHeader.jsp" %>
		</div>
		<div id="left" class="column assessment-column-left" aria-hidden="false">
        	<input id="veteranAssessmentId" type="hidden" value="<c:out value="${veteranAssessmentId}" />" />
        	
            <hr>
            <ul id="measuresProgress">
            
            <c:forEach var="section" items="${sections}" varStatus ="status">
            	
            	<li>
            		<div class="progressEntry">
            			<div class="progressLabel">
            			  <span for="progress_<c:out value="${section.surveySectionId}"/>" aria-label="<c:out value="${section.name}" />"><c:out value="${section.name}" /></span>
            			</div>
                		<div id="progress_<c:out value="${section.surveySectionId}"/>" class="progressBar" role="progressbar"></div>
            		</div>
                </li>
            </c:forEach>
            </ul>
        </div>
		<!-- Three columns, fixed-fluid-fixed -->
        <div id="center" class="column contentAreaGrayRadial padding-bottom" aria-hidden="false">
			 <a name="skip" > </a ><h1 id="viewTitle"></h1>
			<!-- Dynamic Content Region -->
			<div id="assessmentContainer" role="application" aria-describedby="assessmentNotes">
            	<!-- This is the active region container for the active view's (page) form controls.  When a page is selected, the question JSON 
            	returned, this will be populated with the form controls drawn by iteration over the response's JSON object. -->
            </div>
			<!-- End Dynamic Content Region -->
            <!-- <noscript> section with descriptive text to provide users without JavaScript enabled, or screen reader-equipped, directions
            	 for use.  -->
            <noscript id="assessmentNotes">
            	<!-- TODO:: Describe usage of the Measures/Assessment here. -->
            </noscript>
			 <!-- Navigation Control Buttons TOP. -->
             <div id="bottomNavigation" role="navigation" style="margin-top: -30px;">
                 <input id="backBtn" type="button" value="&#171; Back" class="searchBtn" style="visibility: visible;">
                 <!--<input id="saveExitBtn" type="button" value="Save &amp; Exit" class="anchorButton inputButton">-->
                 <button id="saveExitBtn" class="searchBtn" style="line-height:30px;">
                 	<img src="../resources/css/icons/SaveGlyph.png" alt="Icon - Save and Exit" class="buttonIcon">
                 	<span class="saveExitBtnText"> Save &amp; Exit</span>
                 </button>
                 	
                 <input id="nextBtn" class="searchBtn" type="button" value="Next &#187;">
				 
             </div>             
             <div id="savedTime"></div>
        
		<br>
		
		</div>
        
        <div id="right" class="column">
            <!-- Dead Space -->
        </div>
        
        <div id="dialogError" class="hidden">
        	<div class="dialogErrorMessageText"></div>
        </div>
        
        <div id="dialogRequired" class="hidden" title="Missing Required Fields" aria-hidden="true">
        	<div class="dialogSkipInfo"
                title="Required inputs can not be empty. Required fields are denoted with an * in each required questions. Please answer the required fields."
                aria-label="Required inputs can not be empty. Required fields are denoted with an * in each required questions. Please answer the required fields."
                role="alert"> Required inputs can not be empty. Required fields are denoted with an 
        		<span class="requiredIcon">* </span>in each required questions.
        		<div>Please answer the required fields.</div>
        	</div>
        </div>
        
        <div id="dialogSkip" class="hidden" title="Skipping Question(s)?" aria-hidden="true">
        	<div class="dialogSkipInfo" title="Some of the questions are not answered. Do you want to skip the question(s)?"  role="alert"  aria-hidden="false">
        		Some of the questions are not answered.
        		Do you want to skip the question(s)?
        	</div>
        </div>
        
        <div id="dialogLogoutTimer" class="hidden" title="Session will expire">
        	<div class="sessionExpireContainer">
        		<span id="sessionExpireMessageLabel" class="sessionExpireMessageLabel"></span>
        		<span id="counter"></span> 
        		<span class="sessionExpireMessageLabel">second(s).</span>
        		<div class="sessionExpireMessageLabel">Please respond quickly.</div>
        	</div>
        </div>
        
        <div id="dialogTableQuestionEntryClicked" class="hidden" title="Unanswered Questions"  aria-hidden="true">
        	<div class="dialogSkipInfo" title="Please answer each question before creating a new entry" aria-label="Please answer each question before creating a new entry"
               role="alert">
        		Please answer each question before creating a new entry
        	</div>
        </div>
         <div id="tableQuestionMaxEntry" class="hidden" title="Maximum Entries"  aria-hidden="true">
				<div class="dialogSkipInfo" title="The maximum entries allowed has been met" aria-label="The maximum entries allowed has been met"
					role="alert">
					The maximum entries allowed has been met
				</div>
         </div>
	</body>
</html>
