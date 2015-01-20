<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html ng-app="assessmentDashboardApp">
    <head>
        <title>Assessment Dashboard</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
        <link href="resources/css/jquery/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css">

        <!-- FAVICON -->
        <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
        <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon" />
        <link rel="apple-touch-icon" href="resources/images/favico_va_310x310.png" />
        <link rel="apple-touch-icon" sizes="114x114" href="resources/images/favico_va_touch_114x114.png" />
        <link rel="apple-touch-icon" sizes="72x72" href="resources/images/favico_va_touch_72x72.png" />
        <link rel="apple-touch-icon" href="resources/images/favico_va_touch_57x57.png" />

        <link href="resources/css/partialpage/standardtopofpage-dashboard.css" rel="stylesheet" type="text/css">
        <link href="resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/partialpage/menu-partial.css" rel="stylesheet" type="text/css">
        <link href="resources/css/assessmentDashboard.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/formButtons.css" rel="stylesheet" type="text/css"/>
        
        <!-- Bootstrap -->
        <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet" type="text/css">
        
        <!-- Graphs -->
        <link href="<c:url value="/resources/css/partialpage/dashboard_graphs.css" />" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="resources/js/jquery/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="resources/js/jquery/jquery.dataTables.js"></script>
        <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
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
  <div class="container  left-right-shadow  noPrint">
  	
    <div class="row">
      <div class="col-md-12">
        <div ng-controller="assessmentDashboardController" class="mainDiv">
          <div class="pull-left">
            <a name="skip" > </a >
            <h1>Assessment Dashboard</h1>
          </div>
          <ul class="nav nav-tabs pull-right" id="dashboardTab">
            <li class="active text-right"><a href="#List">List</a></li>
            <li class="text-right" data-ng-click="updateGraphs()"><a href="#Charts">Charts</a></li>
            
          </ul>
          <div class="clear-fix"> </div>
          <br>
          <br>
          <br>
          <div id='content' class="tab-content">
            <div class="tab-pane active" id="List">
              <div class="row">
                <div class="col-md-12">
                  <div id="leftContent2" class="border-radius-main-form gray-lighter">
                    <div class="topContent1">
                      <form id="assessmentDashboardForm" name="assessmentDashboardForm" novalidate ng-submit="searchDatabase()">
                        <div class="row">
                          <div class="col-md-4">
                            <div class="form-group">
                              <label for="programId">Program</label>
                              <select id="programId" class="fieldAlign form-control col-md-4" name="programId" ng-options="program.stateId as program.stateName for program in programList" ng-model="assessmentDashboardFormBean.programId" >
                                <option value="">All Program</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-md-2">
                            <div class="form-group">
                              <div class="label_margin"></div>
                              <button class="btn btn-primary  form-control" name="searchAssessmentButton" type="submit">Search</button>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="checkbox pull-right"> <br>
                              <label for="auto-refresh">
                                <input type="checkbox" id="auto-refresh">Auto-refresh </label>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
              <div class="clear-fix"></div>
              <div class="row">
                <div class="col-md-12">
                  <div class="bottomContent" id="mainContent">
                    <table id="assessmentDashboardTable" name="assessmentDashboardTable" report-table="overrideOptions" fn-data-callback="getDataForSearch" class="table table-striped  table-hover table-responsive" summary="Assessment Dashboard Table">
                      <thead>
                        <tr>
                          <th scope="col">Assessment Changed Date</th>
                          <th scope="col">Veteran</th>
                          <th scope="col">SSN-4</th>
                          <th scope="col">Program</th>
                          <th scope="col">Clinician</th>
                          <th scope="col">Duration</th>
                          <th scope="col">Progress</th>
                          <th scope="col">Status</th>
                          <th scope="col">Alert</th>
                        </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                    <br>
                  </div>
                </div>
              </div>
            </div>
            <div class="tab-pane" id="Charts" >
              <div class="alert alert-danger error-json" style="display: none;"> Unable to connect to server. Please contact system administrator. </div>
              <div class="row">
                <div class="col-md-12">
                  <div id="leftContent2" class="border-radius-main-form gray-lighter">
                    <div class="topContent1">
                      <form id="assessmentDashboardGraphForm" name="assessmentDashboardGraphForm" novalidate ng-submit="updateGraphs()">
                        <div class="row">
                          <div class="col-md-4">
                            <div class="form-group">
                              <label for="programIdChart">Program</label>
                              <select id="programIdChart" class="fieldAlign form-control col-md-4" name="programId" ng-options="program.stateId as program.stateName for program in programList" ng-model="assessmentDashboardFormBean.programId" >
                                <option value="">All Program</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-md-2">
                            <div class="form-group">
                              <div class="label_margin"></div>
                              <button class="btn btn-primary  form-control" name="searchAssessmentButtonChart" type="submit">Search</button>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="checkbox pull-right"> <br>
                              <label>
                                <input type="checkbox" ng-model="autorefresh" ng-change="change()" >
                                Auto-refresh </label>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
              <br>
              <div class="row">
                <div class="col-md-6">
                  <div class="panel panel-default">
                    <div class="panel-heading"><strong>Alerts</strong></div>
                    <div class="panel-body">
                      <div class="chart_container"> 
                        <!-- Render the Pie Chart -->
                        <div id="pie_chart"></div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="panel panel-default">
                    <div class="panel-heading"><strong>Slow Moving Incomplete Assessments (Bottom 5)</strong></div>
                    <div class="panel-body"> 
                      <!-- Render the Bar Chart -->
                      <div class="chart_container">
                        <div id="bar_chart"></div>
                        <div class="text-center graphs-legend"> <span class="glyphicon glyphicon-stop color-red"> </span> With Alerts <span class="glyphicon glyphicon-stop color-green"> </span> No Alerts </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12">
                  <div class="panel panel-default">
                    <div class="panel-heading"><strong>Assessments Nearing Completion (Top 5)</strong></div>
                    <div class="panel-body">
                      <div> 
                        <!-- Render the Bar Horizontal Chart -->
                        <div id="bar_horizontal"></div>
                        <div class="text-center graphs-legend"> <span class="glyphicon glyphicon-stop color-red"> </span> With Alerts <span class="glyphicon glyphicon-stop color-green"> </span> No Alerts </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
    <div class="clear-fix"></div>
  </div>
</div>
<%@ include file="/WEB-INF/views/partialpage/footer.jsp" %>
</body>
    <!-- Bootstrap JS -->
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
    
    <!-- Angular JS -->
    <script type="text/javascript" src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/dashboard/assessmentDashboard.js?v=2" />"></script>
    
    <!-- D3 JS -->
    <script src="<c:url value="/resources/js/d3/d3.min.js" />"></script>
    <script src="<c:url value="/resources/js/d3/d3pie.js" />"></script>
    <script src="<c:url value="/resources/js/d3/d3.tip.v0.6.3.js" />"></script>
</html>