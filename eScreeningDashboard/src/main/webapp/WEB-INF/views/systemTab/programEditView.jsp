<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="programEditFormApp">
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
        
        <!-- Bootstrap -->
        <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css" />
        <title>Program Edit View</title>
        <script type="text/javascript">
            $(document).ready(function() {
                tabsLoad("programManagement");
            });
        </script>    
    </head>
<body>
<a href="#skip" class="offscreen">Skip to main content</a>
<div id="outerPageDiv">
    <%@ include file="/WEB-INF/views/partialpage/standardtopofpage-partial.jsp" %>
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


 
              
              
              
<div class="container left-right-shadow">

  <div class="row">
    <div class="col-md-12">
      <a name="skip" > </a ><h1>Program Edit View</h1>
      <div>
        <form:form modelAttribute="programEditViewFormBean" autocomplete="off" method="post">
          <form:hidden path="programId"/>
          <form:errors path="*" element="div" cssClass="alert alert-danger" />
          <br />
          <div class="row">
            <div class="col-md-8">
              <div class="form-group">
                <form:label path="name">Program Name *</form:label>
                <form:input path="name" maxlength="50" 
										cssClass="inputStyle form-control"
										cssErrorClass="errorInputStyle  form-control"
										placeholder="Enter the Name of the program" />
                <form:errors path="name" cssClass="help-inline"/>
              </div>
            </div>
            <div class="col-md-4">
              <div class="form-group">
              	<div class="label_margin"></div>
                <div class="margin_top_10">
                <form:checkbox path="isDisabled" label=" Mark this Program as Disabled" />
                <!-- <p class="help-block">By checking disabled this program will not populate in the program list</p> -->
                <form:errors path="isDisabled" cssClass="help-inline"/>
                </div>
              </div>
            </div>
          </div>
          <div class="row filter-panels">
            
            <!-- Battery -->
            <div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title"><strong>Battery</strong><!--<form:label path="selectedBatteryIdList"></form:label>--></h2>
						<div class="pull-right">
							<span class="clickable filter" data-toggle="tooltip" title="Toggle Filter" data-container="body">
								<span class="label label-info"><i class="glyphicon glyphicon-filter"></i> Filters</span>
							</span>
						</div>
					</div>
					<div class="panel-body">
						<input type="text" class="form-control" id="battery-list-table-filter" data-action="filter" data-filters="#battery-list-table" placeholder="Filter Battery" />
					</div>
					
                    <div class=" vertical_scoll_list">
                      
                    
                    <table class="table  table-striped table-hover" id="battery-list-table">
						<c:if test="${not empty batteryList}">
							<c:forEach var="item" items="${batteryList}">
								<tr>
									<td><form:checkbox path="selectedBatteryIdList" value="${item.batteryId}" label="${item.batteryName}" /></td>                                    
								</tr>
							</c:forEach>
	                	</c:if>
					</table>
                    </div>
				</div>
            </div>
            
            <div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title"><strong>VistA Clinic</strong><!-- <form:label path="selectedClinicIdList"></form:label>--></h2>
						<div class="pull-right">
							<span class="clickable filter" data-toggle="tooltip" title="Toggle Filter" data-container="body">
								<span class="label label-info"><i class="glyphicon glyphicon-filter"></i> Filters</span>
							</span>
						</div>
					</div>
					<div class="panel-body">
						<input type="text" class="form-control" id="clinic-list-table-filter" data-action="filter" data-filters="#clinic-list-table" placeholder="Filter Clinic" />
					</div>
					
                    <div class=" vertical_scoll_list">
                      
                    
                    <table class="table  table-striped table-hover" id="clinic-list-table">
						<c:if test="${not empty clinicList}">
							<c:forEach var="item" items="${clinicList}">
								<tr>
									<td><form:checkbox path="selectedClinicIdList" value="${item.clinicId}" label="${item.clinicName}" /></td>                                    
								</tr>
							</c:forEach>
	                	</c:if>
					</table>
                    </div>
				</div>
            </div>
            <div class="col-md-4">

            	<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="panel-title"><strong>Note List</strong><!-- <form:label path="selectedClinicIdList"></form:label>--></h2>
						<div class="pull-right">
							<span class="clickable filter" data-toggle="tooltip" title="Toggle Filter" data-container="body">
								<span class="label label-info"><i class="glyphicon glyphicon-filter"></i> Filters</span>
							</span>
						</div>
					</div>
					<div class="panel-body">
						<input type="text" class="form-control" id="note-list-table-filter" data-action="filter" data-filters="#note-list-table" placeholder="Filter Note List" />
					</div>
					
                    <div class=" vertical_scoll_list">
                      
                    
                    <table class="table  table-striped table-hover" id="note-list-table">
						<c:if test="${not empty noteTitleList}">
							<c:forEach var="item" items="${noteTitleList}">
								<tr>
									<td><form:checkbox path="selectedNoteTitleIdList" value="${item.stateId}" label="${item.stateName}" /></td>                                    
								</tr>
							</c:forEach>
	                	</c:if>
					</table>
                    </div>
				</div>
            
            
              
            </div>
          </div>
          <br />
          <input id="saveButton" name="saveButton" value="Save" type="submit" class="btn btn-primary" />
          <input id="cancelButton" name="cancelButton" value="Cancel" type="submit" class="btn btn-default" />
        </form:form>
      </div>
    </div>
  </div>
</div>







      
              
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/program_edit_view.js?v=1" />"></script>
</html>