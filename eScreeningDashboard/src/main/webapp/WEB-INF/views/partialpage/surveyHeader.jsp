
<%@ include file="/WEB-INF/views/partialpage/header.jsp" %>

<div id="welcomeDiv">
	<div id="loggedInUser"> 
		<span id="welcomeMessage">Welcome</span> 
		<span id="userNameText">${veteranFullName}</span> 
		<a href="handleLogoutRequest.html">
			<input class="buttonSignout" type="button" value="Sign Out">
		</a>
	</div>
</div>
<div class="clear-fix"></div>