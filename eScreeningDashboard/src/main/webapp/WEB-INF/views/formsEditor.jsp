<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html ng-app="Editors">
<head lang="en">
    <title>VA Editors</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->
	<link href="resources/editors/vendors/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="resources/editors/vendors/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/editors/css/angular-ui-tree.min.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/vendors/textAngular/textAngular.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/standardtopofpage-dashboard.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/menu-partial.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/userManagement.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/formButtons.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/jquery-ui-1.10.3.custom.min.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/standardtopofpage-dashboard_new.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/section_surveys.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="resources/editors/css/main.css">
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
	<link rel="stylesheet" type="text/css" href="resources/editors/css/formsEditor.css">

    <!-- JS -->
	<!-- jQuery JS 1.10.2 -->
	<script src="resources/editors/vendors/jquery/1.10.2/jquery.min.js"></script>

    <script src="resources/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/jquery.insert-at-caret.js"></script>
    <script src="resources/editors/vendors/bootstrap/3.0.0/js/bootstrap.min.js"></script>

    <!-- Angular JS 1.2.25 -->
    <script type="text/javascript" src="resources/editors/vendors/angularjs/1.2.25/angular.min.js"></script>
    <script type="text/javascript" src="resources/editors/vendors/angularjs/1.2.25/angular-resource.min.js"></script>
    <script type="text/javascript" src='resources/editors/vendors/angularjs/1.2.25/angular-animate.min.js'></script>
    <script type="text/javascript" src="resources/vendor-libs/restangular/1.4.0/restangular.min.js"></script>

    <!-- vendors -->
    <script type="text/javascript" src="resources/editors/vendors/lodash/lodash.min.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/angular-drag-and-drop-lists/angular-drag-and-drop-lists.min.js"></script>

    <!-- Tabs -->
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>

    <!-- Utility classes and non-Angular, non-framework classes -->
    <script type="text/javascript" src="resources/editors/js/app/utils/HttpRejectionProcessor.js"></script>

    <!-- Vendor library JavaScript classes -->
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/Object.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ArrayExtention.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/DateExtention.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/BytePushersApplication.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/Errors.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/DateConverter.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/Message.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/MessageHandler.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/Response.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ResponseException.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ResponseExceptionStackTrace.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ResponseStatus.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ResponseStatusTransformer.js"></script>
    <script type="text/javascript" src="resources/vendor-libs/byte-pushers/js/ResponseTransformer.js"></script>

    <!-- Domain Object -->
    <script type="text/javascript" src="resources/editors/js/app/domains/EScreeningDashboardApp.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/Survey.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPage.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPageUIObjectItemWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPageTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPagesTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveysTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySection.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySectionTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySectionsTransformer.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Question.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionUIObjectItemWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionsTransformer.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Event.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/Rule.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/MenuItemSurveySectionUIObjectWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/MenuItemSurveySectionWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/PageQuestionItem.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Answer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/AnswerTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/AnswersTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TableAnswersTransformer.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/AssessmentVariable.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Validation.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/ValidationTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/ValidationsTransformer.js"></script>

	<!--  Batteries Domain -->
	<script type="text/javascript" src="resources/editors/js/app/domains/Battery.js"></script>
	<script type="text/javascript" src="resources/editors/js/app/domains/BatteryTransformer.js"></script> 
	<script type="text/javascript" src="resources/editors/js/app/domains/BatteriesTransformer.js"></script>
	
    <script type="text/javascript" src="resources/editors/js/app/domains/ClinicalReminder.js"></script>

	<!-- Template Domain -->

	<script type="text/javascript" src="resources/editors/js/app/domains/Template.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateBlock.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateCondition.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateConnector.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateTransformation.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateType.js"></script>
	
	<!-- Modules -->
    <script type="text/javascript" src="resources/editors/vendors/angularUtils/angularUtils.js"></script>
    <script type="text/javascript" src="resources/editors/vendors/angularUtils/directives/uiBreadcrumbs/uiBreadcrumbs.js"></script>
    <script type="text/javascript" src="resources/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/ui-bootstrap-tpls-0.10.0.min.js"></script>
    <script src='resources/editors/vendors/textAngular/textAngular-rangy.min.js'></script>
    <script src='resources/editors/vendors/textAngular/textAngular-sanitize.min.js'></script>
    <script src='resources/editors/vendors/textAngular/textAngular.min.js'></script>
    <script type="text/javascript" src="resources/bower_components/ng-table/ng-table.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/sortable.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/xeditable.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/angular-ui-tree.min.js"></script>
    
    <!-- Application Definition file -->
    <script type="text/javascript" src="resources/editors/main.js"></script>

    <!-- UI-Router View States definition -->
    <script type="text/javascript" src="resources/editors/states.js"></script>

    <!-- Services -->
    <script type="text/javascript" src="resources/editors/js/app/services/assessment-variable.manager.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/AssessmentVariableService.js"></script>
    <script type="text/javascript" src="resources/editors/components/alerts/message.factory.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/BatteryService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/ClinicalReminderService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/EventService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/RuleService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/SurveyService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/TemplateBlockService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/TemplateTypeService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/TemplateService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/SurveySectionService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/ManageSectionService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/QuestionService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/MeasureService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/EventBusService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/RecursionHelper.js"></script>

    <!-- Application filters -->
    <script type="text/javascript" src="resources/editors/js/app/filters/messageFilters.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/filters/freemarkerWhiteSpaceFilter.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/filters/limitToWithEllipsis.js"></script>
    <script type="text/javascript" src="resources/editors/components/utilities/on-enter.directive.js"></script>
    <script type="text/javascript" src="resources/editors/components/utilities/strip-html.filter.js"></script>
    
    <!-- Controllers -->
    
    <!-- Entry View -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/home/entryController.js"></script>
    
    <!-- Batteries View States -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesAbstractController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesSelectionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesAddEditController.js"></script>

    <!-- Formula Management files -->
    <script type="text/javascript" src="resources/editors/js/app/services/ManageFormulasService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/formulasEditController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/formulasListController.js"></script>


    <!-- Survey Sections View State -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/sections/sectionsController.js"></script>

    <!-- Rules Sections -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/rules/rules.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/rules/rules.detail.controller.js"></script>

    <!-- Modules View State(s) -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.detail.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.detail.list.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.detail.instructions.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.detail.table.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/modules/text-question.directive.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/modules/simple-question.directive.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/modules/matrix-question.directive.js"></script>
    <!-- Template View State(s) -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.templates.controller.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modules.templates.edit.controller.js"></script>
    
    <!--  Directives -->
    <script type="text/javascript" src="resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDropdownMenuDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTableDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/templateBlockEditorDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/modules/matrix-transformation.directive.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/updateHiddenDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/stringToNumberDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/templateBlockTextEditorDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/conditionEditorDirective.js"></script>
    <script type="text/javascript" src="resources/editors/components/utilities/really-click.directive.js"></script>


    <script type="text/javascript">	
        $(document).ready(function() {
            tabsLoad("formsEditor");
        });
    </script>
</head>
<body class="form_editor">
<!-- Top Nav Content -->
<div id="outerPageDiv">
    <div id="headerDiv" class="top_header">
        <div class="container bg_transparent">
            <div class="row">
                <div class="col-md-9">
                    <div id="vaLogoDiv"><a href="../home"><img src="resources/images/dva_eha_logo.png" alt="Department of Veterans Affairs | eScreening Program" border="0"></a></div>
                </div>
                <div class="col-md-3 text-right">
                    <div id="welcomeDiv">
                        <div id="loggedInUser">
                        	<sec:authorize access="isAuthenticated()">
								<span id="welcomeMessage" ><span class="glyphicon glyphicon-user"></span> Welcome <sec:authentication property="principal.fullName"/> | </span> 
								<a  href="handleLogoutRequest">
								<!--   <input  class="buttonSignout" type="button" value="Sign Out">-->
								<a href="handleLogoutRequest">Logout <span class="glyphicon glyphicon-log-out"></span></a>
								</a>
							</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<div class="header_support">
									<div>Support and Problems</div>
									<span class="label label-primary"><a href="#">Email US</a> OR (800) 827-1000</span>
								</div>						
							</sec:authorize>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div class="clearfix"></div>
    <div class="navbar navbar-default navbar-update" role="navigation">
        <div class="container bg_transparent">
        	    <nav class="navbar-collapse collapse">
        			<ul class="nav navbar-nav" id="tabs">
        			</ul>
      			</nav>
            
        </div>
    </div>
    <!-- /Top Nav Content -->
    <!-- Application Active Region Content - Wraps the active navigation module -->
    	<div id="bodyDiv" class="bgImgMiddle">
	        <div class="left-right-shadow main-container container" style="padding:0px;margin:0 auto;">
	        	<div class="container">
            
            		 <!-- <div class="navbar-header">-->
                <!-- Here you can see ui-sref in action again. Also notice the use of $state.includes, which
                         will set the links to 'active' if, for example on the first link, 'assessment-editor' or any of
                         its descendant states are activated. -->
                <br> 
                 <div class="row editorTopNav">
                 <div class="col-md-12  text-center">
                <div class="button-group" ng-show="$state.current.name!=='home'">
                	<a ng-class="{active: $state.includes('batteries')}" class="btn btn-default btnHeader btnHeaderLeft" ui-sref="batteries.list">Manage Batteries</a>
                	<a ng-class="{active: $state.includes('modules')}" class="btn btn-default btnHeader btnHeaderMid" ui-sref="modules">Manage Module</a>
                	<a ng-class="{active: $state.includes('sections')}" class="btn btn-default btnHeader btnHeaderMid" ui-sref="sections">Manage Sections</a>
                    <a ng-class="{active: $state.includes('rules')}" class="btn btn-default btnHeader btnHeaderRight" ui-sref="rules">Manage Rules</a>
                </div>
                </div>
                </div>
            <!-- </div>-->
            		 <div class="row">

                         <div class="col-md-12">
                             <!-- Alerts and Messages -->
                             <alert ng-repeat="message in flashMessages" type="message.type" close="message.close($index)"><p ng-bind="message.msg"></p></alert>
                         </div>

            		    <div class="col-md-12" ui-view></div>
                    </div>
        		</div>
	        </div>
        </div>

    <div class="container" style="padding:0px;">
        <footer>
            <div class="footer_container">
                <div class="container bg_transparent">
                    <div class="row">
                        <div class="col-md-12"> U.S. Department of Veterans Affairs | eScreening Program (Ver 1.0) </div>
                    </div>
                </div>
            </div>
        </footer>
    </div>
    
    
    <div class="modal fade modal-custom-sm" id="idletimeout" title="Your session is about to expire!" role="dialog" aria-labelledby="idletimeout" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">Session Time out</h4>
          </div>
          <div class="modal-body">
            <p class="sessionTimeOutMessage">You will be logged off in <span class="countdown"><!-- countdown place holder --></span>&nbsp;seconds due to inactivity.</p>
      
          </div>
          <div class="modal-footer">
            <div class="text-center">
              <a id="idletimeout-resume" href="#"  data-dismiss="modal" class="btn btn-primary">Continue using this page</a>
              <a href="handleLogoutRequest" class="btn btn-default" id="btn-logout-timeout">Logout</a>
            </div>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    
    <script src="resources/js/lib/jquery.idletimer.js" type="text/javascript"></script> 	 	
    <script src="resources/js/lib/jquery.idletimeout.js" type="text/javascript"></script> 	 	
    <script src="resources/js/dashboard/dashboard_common.js" type="text/javascript"></script> 	    


    <!-- angular-ui-select for mng formulas (starts after this line)-->
    <!-- Select2 theme -->
    <link rel="stylesheet" href="resources/bower_components/angular-ui-select/dist/select.min.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/vendors/ajax/select2/3.4.5/select2.css">
    <!--
      Selectize theme Less versions are available at https://github.com/brianreavis/selectize.js/tree/master/dist/less
    -->
    <link rel="stylesheet" href="resources/editors/vendors/ajax/selectize.js/0.8.5/css/selectize.default.css">
    <style>
        /* body {padding: 15px;} */
        .select2 > .select2-choice.ui-select-match {
            /* Because of the inclusion of Bootstrap */
            height: 29px;
        }
        .selectize-control > .selectize-dropdown {
            top: 36px;
        }
    </style>
    <script type="text/javascript" src="resources/bower_components/angular-ui-select/dist/select.min.js"></script>
    <!-- angular-ui-select for mng formulas (finishes before this line)-->
</body>
</html>
