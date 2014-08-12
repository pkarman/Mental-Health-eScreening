<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<htmL>
<head>
	<title>Welcome Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" name="viewport" content="user-scalable=no,width=device-width" />
	
	<!-- FAVICON -->
	<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
	<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />
	<link rel="apple-touch-icon" href="resources/images/favico_va_310x310.png" />
	<link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
	<link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
	<link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
    	
	
	<link href="resources/css/home.css" rel="stylesheet" type="text/css">
	<link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css"/>
	<link href="resources/css/login.css" rel="stylesheet" type="text/css">
	<link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
	<link href="resources/css/mobileStyle/mediaQueryMain.css" rel="stylesheet" type="text/css"/>
	
	<!-- Bootstrap -->
	<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
	<link href="resources/css/partialpage/standardtopofpage-dashboard_new.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script> 
	<script type="text/javascript">
		$(function() {
		    $('.wecomePageDiv3 img:gt(0)').hide(); // to hide all but the first image when page loads
		
		    setInterval(function()
		    {
		        $('.wecomePageDiv3 :first-child').fadeOut(1000)
		            .next().fadeIn(1000).end().appendTo('.wecomePageDiv3');
		    },5000);  
		});
	</script>

</head>
<body>
<%@ include file="/WEB-INF/views/partialpage/header_home.jsp" %>
<div class="clearfix"></div>
<div class="landing_page main_container">
	<div class="container bg_main">
		<div class="row">
			<div class="col-md-12">
				<div class="main_box_landing">
				<h1>Welcome to the <STRONG>Mental Health</STRONG> <fmt:message key="app.name"/></h1>
				</div>
                
                
                <div class="row">
                	<div class="col-md-5 col-md-offset-1 veteran_block">
                    	<div class="container_box"><h2>VETERAN ACCESS</h2></div>
                    	<div class="container_box">
                            
                        	<div><a class="btn btn-primary btn-lg btn_skin_landing btn-block" href="<c:url value="/assessmentLogin"/>">Start eScreening <span class="glyphicon glyphicon-chevron-right"></span></a></div>
                        	<div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="col-md-5 staff_block">
                    	<div class="container_box"><h2>STAFF ACCESS</h2></div>
                    	<div class="container_box">
                           
                        	<div><a class="btn btn-primary btn-lg btn_skin_landing btn-block" href="<c:url value="/dashboard/login"/>">Staff Login <span class="glyphicon glyphicon-chevron-right"></span></a></div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
    			</div>
                <div class="clearfix"></div>
                <div class="row">
                	<div class="col-md-10 col-md-offset-1">
                    	<div class="container_box_white">
                       		
                            
                            <div class="row">
                				<div class="col-md-4 text-center">
                    				<img src="resources/images/logo_cesamh.png" alt="CESAMH STRESS AND MENTAL HEALTH" border="0">
                   				 </div>
                                 <div class="col-md-4 text-center">
                    				<img src="resources/images/logo_va_healthcare.png" alt="VA HEALTH CARE" border="0">
                   				 </div>
                                 <div class="col-md-4 text-center">
                    				<img src="resources/images/ci_logo.png" alt="US DEPARTMENT OF VETERANS AFFAIRS | CENTER FOR INNOVATION" border="0">
                   				 </div>
    						</div>
                            
                        </div>
                    </div>
    			</div>
                
                
                
			</div>
		</div>
	</div> 
</div>
<%@ include file="/WEB-INF/views/partialpage/footer_login.jsp" %>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
</htmL>
