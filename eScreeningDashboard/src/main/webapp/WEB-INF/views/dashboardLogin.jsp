<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page session="true" %>
<!doctype html>
<html>
	<head>
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">

      <script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.js"></script>
      <meta name="viewport" content="width=device-width, initial-scale=1">
    
      <!-- FAVICON -->
      <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
      <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />    
      <link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
      <link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
      <link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />
      <meta name="msapplication-square310x310logo" content="resources/images/favico_va_310x310.png" />
    
      <title>Admin Login</title>
      <link href="resources/css/partialpage/standardtopofpage-partial.css" rel="stylesheet" type="text/css"/>
      <link href="resources/css/login.css" rel="stylesheet" type="text/css"/>
      <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
      <!--<link href="resources/css/common-ui-styles/forms.css" rel="stylesheet" type="text/css">-->
      <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css"/>
      <script type="text/javascript">
          $(function() {
            $("input[type=text]:first").focus(); 
            $("#dashboardLoginForm").submit(function(event){  // delay form submit for 3 sec.
              var formId = this.id;
                  var form = this;
                        mySpecialFunction(formId);
                        event.preventDefault();
                        form.submit();
            }); 
            
            //preload loading image
            $('<img src="resources/images/submitting.gif" alt="loading" id="loading_image" class="submitImg">')
              .hide()
              .appendTo($("#submitButtonDiv"));
          });
    
          function mySpecialFunction(formId){ // show loding indicator and disable submit button
            $("#submitButtonDiv")
              .removeClass("rightLogin")
              .addClass("loadingCenter")
              .find("#dashboardLogin").hide();
          
            $("#loading_image").show();
          }
      </script>
    
      <!-- Bootstrap -->
      <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
      <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
        
	</head>
	<body>
    <%@ include file="/WEB-INF/views/partialpage/header_new.jsp" %>
    <div class="clear-fix"></div>
 
    <div id="loginControlDiv">
      <div class="container left-right-shadow">
        <div class="row">
          <div class="col-md-6  col-md-offset-3 main_container_login">
            <form id="dashboardLoginForm" class="loginForm_" action="j_spring_security_check_admin" method="post" autocomplete="off">
              <h1 class="text-center">Staff Access | Please Login</h1>
              

              <div class="row">
                <div class="col-md-12">
                  <div class="dashboardLoginError">
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">
                            Your login attempt was not successful, try again.
                            <input type="hidden" id="errorType" value="${param.error}"/>
                        </div>
                    </c:if>
                  </div>
	            </div>
              </div>
              
              
              
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="field_">
                      <div id="userNameParamDiv" class="rightLogin_">
                      	<label for="userNameParam">Username </label>
                        <input name="userNameParam" id="userNameParam" type="text"  maxlength="30" placeholder="Enter username"  class="form-control input-lg" required>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div class="field_">
                      <div id="passwordParamDiv" class="rightLogin_">
                      	<label for="passwordParam">Password </label>
                        <input name="passwordParam" id="passwordParam" type="password"  maxlength="30" class="form-control input-lg" placeholder="Enter your password"  required>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <div id="submitButtonDiv" class="rightLogin_">
                      <input type="submit" id="dashboardLogin" value="Login" class="submitButton btn btn-primary  btn-lg btn-block form-control" />
                    </div>
                  </div>
                </div>
              </div>
              
            </form>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="/WEB-INF/views/partialpage/footer_login.jsp" %>
</body>
</html>