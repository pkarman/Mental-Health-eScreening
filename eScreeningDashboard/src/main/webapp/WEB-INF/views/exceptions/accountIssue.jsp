<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<htmL>
<head>
<base href="${pageContext.request.contextPath}/"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10" name="viewport" content="user-scalable=no,width=device-width" />

	<!-- FAVICON -->
	<link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
	<link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />
	<link rel="apple-touch-icon" href="resources/images/favico_va_310x310.png" />
	<link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
	<link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
	<link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
    

<title>See Clerk</title>
<link href="resources/css/home.css" rel="stylesheet" type="text/css">
<link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/login.css" rel="stylesheet" type="text/css">
<link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/mobileStyle/mediaQueryMain.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/common-ui-styles/exception.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div id="outerPageDiv">
	<%@ include file="/WEB-INF/views/partialpage/header.jsp" %>
	
	<div id="center" class="column contentAreaGrayRadial">
		<div id="content-controls" class="contentAreaGrayHorizontal">
			<div id="viewTitle">
				<span class="pageTitle">Please See A Clerk for Assistance</span>
			</div>
		</div>
		
		<div class="exceptionWrapper">
			<div class="exceptionLabel">
				For assistance, please contact the Help Desk. 
			</div>
			<div class="exceptionContent">
				<span class="exceptionContentLabel"> VA San Diego Healthcare System </span>
				<div class="exceptionAddressDetails">
					1234  Mission Center Rd <br />
					San Diego, CA 92108 <br />
					Mon - Fri: (123) 456-7890 <br />
					Toll-Free : 1-(855) 123-4567 <br />
					Email: healthcareVA@vahealthcare.gov
				</div>
			</div>
		</div>
		
	</div>
	
</div>
</body>

</htmL>
