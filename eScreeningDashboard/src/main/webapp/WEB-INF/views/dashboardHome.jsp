<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
<head>
    <title>My Account</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" />
    <script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>

	<!-- FAVICON -->
	<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
	<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />    
	<link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
	<link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
	<link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
    <meta name="msapplication-square310x310logo" content="resources/images/favico_va_310x310.png" />
    
    <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/userManagement.css" rel="stylesheet" type="text/css">
    <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css">
    <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">	
        $(document).ready(function() {
            tabsLoad("home");
        });
    </script>
    
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
</head>
<body>
<div id="outerPageDiv" >
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
  <div id="bodyDiv" class="bgImgMiddle" align="center">
    <div class="container left-right-shadow main-container">
      <div class="row">
        <div class="col-md-12"> <img  src="resources/images/landing_bg.gif" class="img-responsive" /> </div>
      </div>
    </div>
  </div >
</div>
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>
</html>