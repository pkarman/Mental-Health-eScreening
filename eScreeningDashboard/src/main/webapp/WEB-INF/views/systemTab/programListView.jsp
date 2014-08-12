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

<link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet" type="text/css" />

<link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon" />
<link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon" />

<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/veteranSearch.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/formButtons.css" />" rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />


<title>Program List View</title>

<script type="text/javascript">
	$(document).ready(function() {
		tabsLoad("programManagement");
	});
</script>

<style type="text/css">

	
</style>
</head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a>
	<div id="outerPageDiv">
		<%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp"%>

		<div class="navbar navbar-default navbar-update" role="navigation">
			<div class="container bg_transparent">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
				</div>
				<nav class="navbar-collapse collapse">
					<ul class="nav navbar-nav" id="tabs">
					</ul>
				</nav><!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="mainDiv">
	</div>

	<div class="container left-right-shadow">
		<div class="row">



			<div class="col-md-4 pull-right">
					<s:url var="addProgramUrl" value="/dashboard/programEditView" htmlEscape="true"></s:url>
					<a href="${addProgramUrl}" class="btn btn-primary btn-md form-control h1_button"><span class="glyphicon glyphicon-plus"></span> Add New Program</a>
			</div>
			<div class="col-md-8 pull-left"><a name="skip" > </a ><h1>Program List View</h1></div>
			</div>
			<div class="clear-fix"></div>
				
				<div>
			

					<table class="table table-striped table-hover" summary="Program List View">
						<thead>
                            <tr>
                                <th scope="col"  class="col-md-1 text-right">ID</th>
                                <th>Name</th>
                                <th class="col-md-1 text-center">Disabled</th>
                                <th class="col-md-1 text-right">Action</th>
                            </tr>
                        </thead>
						<c:forEach var="item" items="${programList}">
							<tr>
								<td class="col-md-1 text-right"><c:out value="${item.programId}" /></td>
								<td><c:out value="${item.name}" /></td>
								<td class="col-md-1 text-center"><c:out value="${item.isDisabled}" /></td>
								<td class="text-right">
									
                                    <s:url var="editProgramUrl" value="/dashboard/programEditView" htmlEscape="true">
										<s:param name="pid" value="${item.programId}" />
									</s:url>
									<a href="${editProgramUrl}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-chevron-right"></span> Edit</a>								
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			

	</div>
 
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>

</html>

