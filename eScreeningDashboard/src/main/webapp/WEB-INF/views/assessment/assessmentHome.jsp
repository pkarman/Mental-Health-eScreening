<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html>
<head>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="viewport" content="user-scalable=no,width=device-width" />
	
  <!-- FAVICON -->
  <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
  <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />
  <link rel="apple-touch-icon" href="resources/images/favico_va_310x310.png" />
  <link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
  <link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
  <link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
	<title>eScreening Dashboard</title>
	
	<link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/common-palette.css">
        <link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/fontfaces.css">
        <link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/forms.css">
		<link rel="stylesheet" type="text/css" href="resources/css/assessmenttemplate.css">
        <link rel="stylesheet" type="text/css" href="resources/css/common-ui-styles/assessment-measures-master.css">
        <link rel="stylesheet" type="text/css" href="resources/css/measuresWIP.css">
        
        <link rel="stylesheet" type="text/css" href="resources/css/checkswitch.css">
         <link rel="stylesheet" type="text/css" href="resources/css/formButtons.css">
         <link href="resources/css/mobileStyle/mediaQueryMain.css" rel="stylesheet" type="text/css"/>


  <script src="resources/js/jquery/jquery-1.10.2.min.js"></script>
  <script src="resources/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
  <script type="text/javascript" src="resources/js/measures/assessmentHome.js"></script>
</head>
<body>
	<a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">

		<%@ include file="/WEB-INF/views/partialpage/surveyHeader.jsp" %>
		
		<div id="bodyDiv">
			<div id="homeBodyDiv">
            	<div class="main_contents_landing">
                    <div class="assessmentStartBody">
                    <br /><br />
                    <a name="skip" > </a >
                    <s:url var="imgUrl"
                            value="/resources/images/assessment_start_screen_capture_logo_01.png" />
                        <img src="${imgUrl}" name="VA HEALTH CARE | Defining Excellence in the 21st Century" title="VA HEALTH CARE | Defining Excellence in the 21st Century" alt="VA HEALTH CARE | Defining Excellence in the 21st Century" />
                      
                      <div id="assessmentWelcome">
                        Loading...
                      </div>
                        <div class="startAssessmentButton">
                            <a href="start" class="startAssessment">
                                <input id="startAssessmentButton" class="indexButtons inputButton startAssessmentButton" type="button" value="Start Assessment">
                            </a>
                    	</div>
                    	<br /><br />
                        <s:url var="imgUrl"
                            value="/resources/images/assessment_start_screen_capture_logo_02.png" />
                        <img src="${imgUrl}" name="VA Center of Excellence CESAMH | Stress and Mental Health" title="VA Center of Excellence CESAMH | Stress and Mental Health" alt="VA Center of Excellence CESAMH | Stress and Mental Health" />
                    </div>
                </div>
            </div>
		</div>
	</div>
</body>
</html>
