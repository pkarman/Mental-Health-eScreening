<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html ng-app="Editors">
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10">
    <link rel="icon" href="resources/images/valogo.ico" type="image/x-icon">
    <link rel="SHORTCUT ICON" href="resources/images/valogo.ico" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>VA Editors</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="resources/editors/vendors/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/editors/css/angular-ui-tree.min.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/vendors/textAngular/textAngular.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/standardtopofpage-dashboard.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/menu-partial.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/userManagement.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/formButtons.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/jquery-ui-1.10.3.custom.min.css" type="text/css">
    <link rel="stylesheet" href="resources/editors/css/escreening/standardtopofpage-dashboard_new.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="resources/editors/css/main.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="resources/js/adminDashboardTabs.js"></script>
    <script src="resources/js/jquery/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/jquery.insert-at-caret.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-resource.min.js"></script>
    <script type="text/javascript" src='//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-animate.min.js'></script>
    <script type="text/javascript" src="resources/vendor-libs/restangular/1.4.0/restangular.min.js"></script>
    <script type="text/javascript" src="resources/editors/vendors/lodash/lodash.min.js"></script>
    
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
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPage.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPageUIObjectItemWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPageTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyPagesTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/Survey.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveyTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveysTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySection.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySectionTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/SurveySectionsTransformer.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Question.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionUIObjectItemWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/QuestionsTransformer.js"></script>


    <script type="text/javascript" src="resources/editors/js/app/domains/MenuItemSurveySectionUIObjectWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/MenuItemSurveySectionWrapper.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/PageQuestionItem.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Answer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/AnswerTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/AnswersTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TableAnswersTransformer.js"></script>

    <script type="text/javascript" src="resources/editors/js/app/domains/Validation.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/ValidationTransformer.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/ValidationsTransformer.js"></script>

	<!--  Batteries Domain -->
	<script type="text/javascript" src="resources/editors/js/app/domains/Battery.js"></script>
	<script type="text/javascript" src="resources/editors/js/app/domains/BatteryTransformer.js"></script> 
	<script type="text/javascript" src="resources/editors/js/app/domains/BatteriesTransformer.js"></script>
	
	<!-- Template Domain -->

	<script type="text/javascript" src="resources/editors/js/app/domains/Template.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateBlock.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateCondition.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateConnector.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateLeftVariable.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateRightVariable.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateTransformation.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateVariableContent.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/domains/TemplateType.js"></script>
	
	<!-- Modules -->
    <script type="text/javascript" src="resources/editors/vendors/angularUtils/angularUtils.js"></script>
    <script type="text/javascript" src="resources/editors/vendors/angularUtils/directives/uiBreadcrumbs/uiBreadcrumbs.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/angular-routing.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/ui-bootstrap-tpls-0.10.0.min.js"></script>
    <script src='resources/editors/vendors/textAngular/textAngular-rangy.min.js'></script>
    <script src='resources/editors/vendors/textAngular/textAngular-sanitize.min.js'></script>
    <script src='resources/editors/vendors/textAngular/textAngularSetup.js'></script>
    <script src='resources/editors/vendors/textAngular/textAngular.js'></script>
    <script type="text/javascript" src="resources/editors/js/directives/ngTable/ng-table.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/sortable.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/xeditable.min.js"></script>
    <script type="text/javascript" src="resources/editors/js/directives/angular-ui-tree.min.js"></script>
    
    <!-- Services -->
    <script type="text/javascript" src="resources/editors/js/app/services/BatteryService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/SurveyPageService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/SurveyService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/TemplateTypeService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/TemplateService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/SurveySectionService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/QuestionService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/MeasureService.js"></script>

    <!-- Application filters -->
    <script type="text/javascript" src="resources/editors/js/app/filters/messageFilters.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/filters/freemarkerWhiteSpaceFilter.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/filters/limitToWithEllipsis.js"></script>
    
    <!-- Application Definition file -->
    <script type="text/javascript" src="resources/editors/main.js"></script>
    
    <!-- Controllers -->
    
    <!-- Entry View -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/home/entryController.js"></script>
    
    <!-- Batteries View States -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesAbstractController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesSelectionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/batteries/batteriesAddEditController.js"></script>
    
    <!-- Survey Sections View State -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/sections/sectionsController.js"></script>
    
    <!-- Modules View State(s) -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/moduleController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modulesController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/modulesEditController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/moduleSelectController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/modules/ModuleTemplateListController.js"></script>

    <!-- Questions View State(s) -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/questions/questionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/questions/freeTextReadOnlyQuestionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/questions/selectMultipleQuestionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/questions/selectMultipleMatrixQuestionController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/controllers/questions/instructionQuestionController.js"></script>
    
    <!-- Template View State(s) -->
    <script type="text/javascript" src="resources/editors/js/app/controllers/templates/templateEditorController.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/services/AssessmentVariableService.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDropdownMenuDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTableDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/templateBlockEditorDirective.js"></script>
    <script type="text/javascript" src="resources/editors/js/app/directives/templateBlockConditionEditorDirective/templateBlockConditionEditorDirective.js"></script>

    <!-- UI-Router View States definition -->
    <script type="text/javascript" src="resources/editors/states.js"></script>
    
    <style type="text/css">
	    @media (min-width:1024px) { 
			.navbar > .container {
			    text-align: center;
			}
			.navbar-header,.navbar-brand,.navbar .navbar-nav,.navbar .navbar-nav > li {
			    float: none;
			    display: inline-block;
			}
			.collapse.navbar-collapse {
			    float: none;
			    display: inline-block!important;
			    width: auto;
			    clear: none;
			}
			/* Promote these to main.css when done. - JBH */
			
			.btnHeader{
				color:#ffffff;
				background-color:#003f72;
				margin-left:-4px;
				margin-right:-4px;
			}
			
			.btnHeader:hover{
				color:#ffffff;
				background-color:#001441;
			}
			
			.btnHeader:active{
				color:#ffae00;
				background-color:#001441;
				
			}
			
			.btnHeaderMid{
				border-radius:0px;
			}
			
			.btnHeaderLeft{
				border-top-right-radius:0px;
				border-bottom-right-radius:0px;
			}
			
			.btnHeaderRight{
				border-top-left-radius:0px;
				border-bottom-left-radius:0px;
			}
			
			.btn-default:hover, .btn-default:focus, .btn-default:active, .btn-default.active, .open .dropdown-toggle.btn-default{
				color: #ffae00;
				background-color: #001441;
				border-color: #adadad;
			}
		}
    </style>
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
                 <div class="row">
                 <div class="col-md-12  text-center">
                <div class="button-group" ng-show="$state.current.name!=='home'">
                	<a ng-class="{active: $state.includes('batteries')}" class="btn btn-default btnHeader btnHeaderLeft" ui-sref="batteries.batteryselection">Manage Batteries</a>
                	<a ng-class="{active: $state.includes('modules')}" class="btn btn-default btnHeader btnHeaderMid" ui-sref="modules.list">Manage Module</a>
                	<a ng-class="{active: $state.includes('sections')}" class="btn btn-default btnHeader btnHeaderRight" ui-sref="sections">Manage Sections</a>
                </div>
                </div>
                </div>
            <!-- </div>-->
            		 <div class="row">
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
</body>
</html>
