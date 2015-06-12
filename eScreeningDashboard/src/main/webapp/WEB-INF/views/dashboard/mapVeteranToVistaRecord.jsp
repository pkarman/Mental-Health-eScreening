<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Map Veteran to VistA Record</title>

  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
  <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
  <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">
  <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
  <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon" /> 
  <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
  <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
  <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
  <link href="resources/css/veteranSearch.css" rel="stylesheet" type="text/css"/>
  <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
  
  <!-- Bootstrap -->
  <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
  

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
                   $('.mod_suffix').empty().html(r['suffix']);
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
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      </div>
      <nav class="navbar-collapse collapse">
        <ul class="nav navbar-nav" id="tabs">
        </ul>
      </nav>
      <!--/.nav-collapse --> 
    </div>
  </div>
  <div class="clearfix"></div>
  <div class="container left-right-shadow">
    <div class="row">
      <div class="col-md-12">
        <div class="row">
          <div class="col-md-8 pull-left"><a name="skip" > </a >
            <h1>Map Veteran to VistA Record</h1>
          </div>
        </div>
        <c:if test="${not empty errMsg}">
          <div class="alert alert-danger">
            <c:out value="${errMsg}"/>
          </div>
        </c:if>

        <div class="border-radius-main-form">
          <div class="row">
            <div class="col-md-6"> Name (Last, First Middle)
              <div class="txt_lable_lg">
                <c:out value="${veteran.fullName}" />
              </div>
            </div>
            <div class="col-md-2"> Status
              <div class="txt_lable_lg">
                <c:choose>
                  <c:when test="${(!empty veteran.veteranId) && (empty veteran.veteranIen)}">
                    <div>Only Exists in DB</div>
                  </c:when>
                  <c:when test="${(!empty veteran.veteranIen) && (empty veteran.veteranId)}">
                    <div>Only Exists in VistA</div>
                  </c:when>
                  <c:when test="${(!empty veteran.veteranIen) && (!empty veteran.veteranId)}">
                    <div>Mapped Veteran</div>
                  </c:when>
                  <c:otherwise> </c:otherwise>
                </c:choose>
              </div>
            </div>
            <div class="col-md-2"> Date of Birth
              <div class="txt_lable_lg">
                <fmt:formatDate type="date" pattern="MM/dd/yyyy"
                    value="${veteran.birthDate}" />
              </div>
            </div>
            <div class="col-md-2 text-right"> SSN-4
              <div class="txt_lable_lg text-right">
                <c:out value="${veteran.ssnLastFour}" />
              </div>
            </div>
          </div>
          <hr />
          <div class="row">
            <div class="col-md-2"> Phone
              <div class="txt_lable_md">
                <c:out value="${veteran.phone}" />
              </div>
            </div>
            <div class="col-md-2"> Work
              <div class="txt_lable_md">
                <c:out value="${veteran.workPhone}" />
              </div>
            </div>
            <div class="col-md-2"> Cell
              <div class="txt_lable_md">
                <c:out value="${veteran.cellPhone}" />
              </div>
            </div>
            <div class="col-md-4"> Email
              <div class="txt_lable_md">
                <c:out value="${veteran.email}" />
              </div>
            </div>
            <div class="col-md-2 text-right"> VistA IEN
              <div class="txt_lable_md">
                <c:out value="${veteran.veteranIen}" />
              </div>
            </div>
          </div>
        </div>
        <br/>
        <div class="clearfix"></div>
        <form:form modelAttribute="mapVeteranToVistaRecordFormBean" autocomplete="off" method="get">
          <div class="border-radius-main-form gray-lighter">
            <h2>Search Criteria</h2>
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
            <form:hidden path="veteranId"/>
            <div class="row">
              <div class="col-md-7">
                <div class="form-group">
                  <form:label path="lastName">Last Name</form:label>
                  <form:input path="lastName" maxlength="30" cssClass="form-control focus_input" cssErrorClass="form-control" placeholder="Enter Veteran Last Name"  />
                  <form:errors path="lastName"  cssClass="help-inline"/>
                </div>
              </div>
              <div class="col-md-3">
                <div class="form-group">
                  <form:label path="ssnLastFour">SSN-4 *</form:label>
                  <form:input path="ssnLastFour" maxlength="4" cssClass="form-control" cssErrorClass="form-control" placeholder="Enter Veteran Last SSN-4"  />
                  <form:errors path="ssnLastFour"  cssClass="help-inline"/>
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
          <c:if test="${not empty searchResultList}">
          <h2>Search Result</h2>
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th scope="col" class="col-md-1">SSN-4</th>
                <th scope="col">Last Name</th>
                <th scope="col">First Name</th>
                <th scope="col">Middle Name</th>
                <th scope="col">Date of Birth</th>
                <th scope="col">Status</th>
                <th scope="col" class="text-right">Action</th>
              </tr>
            </thead>
            <tfoot>
              <tr>
                <c:if test="${empty searchResultList}">
                  <td colspan="8">No record found</td>
                </c:if>
                <c:if test="${not empty searchResultList}">
                  <td colspan="8"><c:out value="${searchResultListSize}" />
                    record(s) found</td>
                </c:if>
              </tr>
            </tfoot>
            <tbody>
              <c:if test="${not empty searchResultList}">
                <c:forEach var="item" items="${searchResultList}">
                  <tr>
                    <td class="text-right"><c:out value="${item.ssnLastFour}" /></td>
                    <td><c:out value="${item.lastName}" /></td>
                    <td><c:out value="${item.firstName}" /></td>
                    <td><c:out value="${item.middleName}" /></td>
                    <td class="text-right"><fmt:formatDate type="date" pattern="MM/dd/yyyy" value="${item.birthDate}" /></td>
                    <td><c:choose>
                        <c:when test="${(!empty item.veteranId) && (empty item.veteranIen)}">
                          <div>Only Exists in DB</div>
                        </c:when>
                        <c:when test="${(!empty item.veteranIen) && (empty item.veteranId)}">
                          <div>Only Exists in VistA</div>
                        </c:when>
                        <c:when test="${(!empty item.veteranIen) && (!empty item.veteranId)}">
                          <div>Mapped Veteran</div>
                        </c:when>
                        <c:otherwise> </c:otherwise>
                      </c:choose></td>
                    <td align="right"><form:form modelAttribute="mapVeteranToVistaRecordFormBean" method="post">
                        <form:hidden path="veteranId"/>
                        <form:hidden path="selectedVeteranIen" value="${item.veteranIen}"/>
                        <input id="selectVeteranButton" name="selectVeteranButton" value="Map" type="submit" class="btn btn-primary btn-xs pull-right" />
                      </form:form>
                      <a href="#" data-vid="${item.veteranId}" data-vien="${item.veteranIen}" class="btn btn-primary btn-xs  pull-right push btn_margin_right" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-chevron-right"></span> Quick View </a></td>
                  </tr>
                </c:forEach>
              </c:if>
            </tbody>
          </table>
          </c:if>
        </c:if>
        <br/>
        <br/>
        <!--  --> 
        
      </div>
    </div>
  </div>
  <div class="clear-fix"></div>
  <div class="nonPrintableArea">
    <%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Veteran Quick View</h4>
      </div>
      <div class="modal-body">
        <div class="mod_container quick_view">
          <div class="row">
            <div class="col-md-8">
              <div>Name (Last, First Middle)<br />
                <strong> <span class="mod_fullName"></span> </strong></div>
            </div>
            <div class="col-md-2">
              <div>SSN-4<br />
                <strong><span class="mod_ssnLastFour"></span></strong></div>
            </div>
          </div>
          <hr>
          <div class="row">
            <div class="col-md-4">
              <div>Date of Birth<br />
                <strong><span class="mod_birthDate"></span></strong></div>
            </div>
            <div class="col-md-2">
              <div>Gender<br />
                <strong><span class="mod_gender"></span></strong></div>
            </div>
            <div class="col-md-6">
              <div>Email<br />
                <strong><span class="mod_email"></span></strong></div>
            </div>
          </div>
          <hr/>
          <div class="row">
            <div class="col-md-4">
              <div>Phone<br />
                <strong><span class="mod_phone"></span></strong></div>
            </div>
            <div class="col-md-4">
              <div>Best Time To Call<br />
                <strong><span class="mod_bestTimeToCall"></span></strong></div>
            </div>
            <div class="col-md-4">
              <div>Best Time To Call Other<br />
                <strong><span class="mod_bestTimeToCallOther"></span></strong></div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-default-white" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

		
    

</body>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/mapVeteranToVistaRecord.js" />"></script>
</html>