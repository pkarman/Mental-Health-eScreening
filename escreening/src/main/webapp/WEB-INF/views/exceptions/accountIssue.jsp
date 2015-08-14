<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<htmL>
<head>
	<title>See Clerk</title>
	<base href="${pageContext.request.contextPath}/"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

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
	<link href="resources/css/common-ui-styles/exception.css" rel="stylesheet" type="text/css"/>
	
</head>
<body>
<div id="outerPageDiv">
	<%@ include file="/WEB-INF/views/partialpage/header.jsp" %>
	
	<div id="center" class="column contentAreaGrayRadial">
		<div id="content-controls" class="contentAreaGrayHorizontal">
			<div id="viewTitle">
				<!--<span class="pageTitle">Please See A Clerk for Assistance</span>-->
			</div>
		</div>
		
		<div class="exceptionWrapper">
			<div class="exceptionLabel">
			  For further assistance, please contact the Healthcare System Technical Administrator.
			</div>
			<div class="exceptionContent">
				<span class="exceptionContentLabel"> </span>
				<div class="exceptionAddressDetails">
					<strong>Mon - Fri:</strong> 7:30 - 4:00<br />
					
					<c:forEach items="${techAdminList}" var="techAdmin">
					
						<c:if test="${not empty techAdmin.phoneNumber || not empty techAdmin.emailAddress}">
							
							<div class="listEntry">
								<div><strong>${techAdmin.firstName} ${techAdmin.lastName}</strong></div>
								<c:if test="${not empty techAdmin.phoneNumber}"> 
									<div><strong>Phone:</strong>${techAdmin.phoneNumber}</div>
								</c:if>
								<c:if test="${not empty techAdmin.emailAddress}"> 
					  				<div>
					  					<strong>Email:</strong> 
					  					<a href="mailto:${techAdmin.emailAddress}">${techAdmin.emailAddress}</a>
					  				</div>
					  			</c:if>
							</div>
						
					    </c:if>
					</c:forEach>
				</div>
			</div>
		</div>
		
	</div>
	
</div>
</body>

</html>