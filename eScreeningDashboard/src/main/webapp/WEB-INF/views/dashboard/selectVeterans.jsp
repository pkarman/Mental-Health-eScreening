<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
<head>
    <title>Veterans Search</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
	<script src="resources/js/bootstrap-datepicker/bootstrap-datepicker.js"></script>    
    <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
    <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
    <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
    <link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="resources/css/bootstrap-datepicker/datepicker.css" />">

	<style type="text/css">
	.loading{
		background-image: url("resources/images/ajax-loader.gif");
		background-repeat: no-repeat;
		background-position: center;
		min-height:100px;
		background-color: red;
	}
	
	</style>
	<script type="text/javascript">
	   
	   $(document).ready(function() {
		  
		   $(".something").addClass("loading");
		   $(".focus_input").select(); 
		   
		   $('.push').click(function(){
				  
				  var vid = $(this).attr('data-vid');
				  var vien = $(this).attr('data-vien');
		
				   $.ajax({
					  type : 'post',
					   url : 'selectVeteran/veterans', // in here you should put your query 
					   data :  'vid='+ vid + '&vien=' + vien, // here you pass your id via ajax . vid & vien
				   success : function(r)
					   {
						
					   $(".mod_container").removeClass("loading");
						 $('#mymodal').show();  // put your modal id 
						 $('.mod_veteranId').empty().html(r['veteranId']);
						 $('.mod_veteranIen').empty().html(r['veteranIen']);
						 $('.mod_fullName').empty().html(r['fullName']);
						 $('.mod_ssnLastFour').empty().html(r['ssnLastFour']);
						 $('.mod_birthDate').empty().html(r['birthDateString']);
						 $('.mod_bestTimeToCall').empty().html(r['bestTimeToCall']);
						 $('.mod_bestTimeToCallOther').empty().html(r['bestTimeToCallOther']);
						 $('.mod_email').empty().html(r['email']);
						 $('.mod_phone').empty().html(r['phone']);
						 $('.mod_gender').empty().html(r['gender']);
					   }
				});
			});
		  
	   });
	   
	</script>

<!-- Bootstrap -->
<link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">

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

<div class="clearfix"></div>

<div class="container left-right-shadow">
	<div class="row">
 		<div class="col-md-12">
 		
 		
 		<div class="row">
 			
 			<div class="col-md-4 pull-right">
 				<form:form method="post">
			      <button id="createButton" name="createButton" type="submit" class="btn btn-primary form-control h1_button"><span class="glyphicon glyphicon-plus"></span> Create Veteran Record in DB</button>
			    </form:form>
			</div>
			<div class="col-md-8 pull-left"><a name="skip" > </a ><h1>Select Veteran</h1></div>
 		</div>

			<c:if test="${!isCprsVerified}">
				<div class="alert alert-danger">
					Your VistA account information needs to be verified before you can save or read any data from VistA. 
					Search result will not include any data from VistA. 
				</div>
			</c:if>

        <div class="clearfix"></div>

				<ul class="nav nav-tabs" role="tablist">
					<li><a href="#home" role="tab" data-toggle="tab"><strong>Create Assessment for Unscheduled Visit</strong></a></li>
					<li class="active"><a href="#" role="tab"><strong>Create Assessment(s) for Appointment(s)</strong></a></li>
				</ul>
				<br>          
                
                <form:form modelAttribute="selectVeteranFormBean" autocomplete="off" method="post">
					<div class="border-radius-main-form gray-lighter">
					<h2>Search Criteria</h2>
					
					<form:errors path="*" element="div" cssClass="alert alert-danger" />

					<div class="row">
					
						<div class="col-md-4">
							<div class="form-group">
			                	<form:label path="lastName">VistA Clinic *</form:label>
			                	<form:input path="lastName" maxlength="30" cssClass="form-control focus_input" cssErrorClass="form-control" placeholder="Enter Veteran VistA Clinic"  />
			                	<form:errors path="lastName"  cssClass="help-inline" />
                			</div>
                		</div>

                      <div class="col-md-3">
                        <div class="form-group">
                            <label class="labelAlign" for="fromAssessmentDate">From Date</label>
                          <div class="input-group date" id="fromAssessmentDateGroup">
                            <input type="text"
                             id="fromAssessmentDate" class="dateField form-control" 
                             name="fromAssessmentDate" maxlength="10"
                             ng-model="exportDataFormBean.fromAssessmentDate"
                             placeholder="MM/DD/YYYY" autocomplete="off" />
                             
                             <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
                         </div>
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="form-group">
                              <label class="labelAlign" for="toAssessmentDate">To Date</label>
                            <div class="input-group date" id="toAssessmentDateGroup">
                              <input type="text"
                               id="toAssessmentDate" class="form-control"
                               name="toAssessmentDate" maxlength="10"
                               ng-model="exportDataFormBean.toAssessmentDate"
                               placeholder="MM/DD/YYYY" autocomplete="off" />
                               
                               <div class="input-group-addon"><i class="glyphicon glyphicon-calendar" title="Click to open calendar"></i><div class="add-on"  title="Click to open calendar"></div></div>
                            </div>
                        </div>
                      </div>
                		
                		<div class="col-md-2">
                			<div class="form-group">
                				<label for="searchButton">&nbsp;</label>
                				<input id="searchButton" name="searchButton" value="Search" type="submit" class="btn btn-primary form-control" />
                			</div>
                		</div>
                		
                		<div class="clearfix"></div>
                	</div>
                	<div class="clearfix"></div>
                	</div>
                </form:form>

			<c:if test="${isPostBack}">
	            <h2>Search Result</h2>
	            <table class="table table-striped table-hover" summary="Search Result Table">
	                <thead>
	                    <tr>
	                        
	                        <th scope="col" class="col-md-1">SSN-4</th>
	                        <th scope="col" class="col-md-2">Last Name</th>
	                        <th scope="col" class="col-md-2">First Name</th>
	                        <th scope="col" class="col-md-2">Middle Name</th>
	                        <th scope="col">Date of Birth</th>
	                        <th scope="col">Appointment Date</th>
	                        <th scope="col" class="text-right">Appointment Time</th>
	                    </tr>
	                </thead>
	                <tfoot>
	                	<tr>
		                	<c:if test="${empty searchResultList}">
	                			<td colspan="8">No record found</td>
	                		</c:if>
		                	<c:if test="${not empty searchResultList}">
	                			<td colspan="8"><c:out value="${searchResultListSize}" /> record(s) found</td>
	                		</c:if>
	                	</tr>
	                </tfoot>
	                <tbody>
	                	<c:if test="${not empty searchResultList}">
							<c:forEach var="item" items="${searchResultList}">
								<tr>
									<td class="text-right"><c:if test="${item.isSensitive}"><c:out value="XXXX"/></c:if><c:if test="${!item.isSensitive}"><c:out value="${item.ssnLastFour}" /></c:if></td>
									<td><c:out value="${item.lastName}" /></td>
									<td><c:out value="${item.firstName}" /></td>
									<td><c:out value="${item.middleName}" /></td>
									<td class="text-right"><c:if test="${item.isSensitive}"><c:out value="XX/XX/XXXX"/></c:if><c:if test="${!item.isSensitive}"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${item.birthDate}" /></c:if></td>
									
									<td>
										<c:choose>
									      <c:when test="${(!empty item.veteranId) && (empty item.veteranIen)}">
									      	<div>Only Exists in DB</div>
									      </c:when>
										  <c:when test="${(!empty item.veteranIen) && (empty item.veteranId)}">
									      	<div>Only Exists in VistA</div>
									      </c:when>
									       <c:when test="${(!empty item.veteranIen) && (!empty item.veteranId)}">
									      	<div>Mapped Veteran</div>
									      </c:when>
									      <c:otherwise>
									      </c:otherwise>
										</c:choose>	
									</td>
									<td align="right">
										<s:url var="veteranDetailUrl" value="/dashboard/veteranDetail" htmlEscape="true">
											<s:param name="vid" value="${item.veteranId}" />
											<s:param name="vien" value="${item.veteranIen}" />
										</s:url>
										<a href="#" data-vid="${item.veteranId}" data-vien="${item.veteranIen}" class="btn btn-primary btn-xs push" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-chevron-right"></span> Quick View</a>
										<a href="${veteranDetailUrl}"  class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-chevron-right"></span>  Select</a>
									</td>
								</tr>
							</c:forEach>
	                	</c:if>
	                </tbody>
	            </table>
			</c:if>
            <br/><br/>
            <!--  -->

		</div>
	</div>
</div>

</div>

<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dashboard/selectVeterans.js?v=2" />"></script>
</html>