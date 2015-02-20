/**
 * Created by Bryan Henderson on 4/16/2014.
 */
var Editors = angular.module('Editors', [
	'ngResource',
	'ui.router',
	'ui.bootstrap',
	'ngTable',
	'xeditable',
	'ui.tree',
	'ui.sortable',
	'ngAnimate',
	'textAngular',
	'restangular',
	'dndLists',
	'angularUtils.directives.uiBreadcrumbs',
	'EscreeningDashboardApp.services.battery',
	'EscreeningDashboardApp.services.surveysection',
	'EscreeningDashboardApp.services.managesection',
	'EscreeningDashboardApp.services.question',
	'EscreeningDashboardApp.services.template',
	'EscreeningDashboardApp.services.templateType',
	'EscreeningDashboardApp.services.assessmentVariable',
	'EscreeningDashboardApp.services.question',
	'EscreeningDashboardApp.services.templateBlock',
	'EscreeningDashboardApp.services.eventBus',
	'EscreeningDashboardApp.filters.messages',
	'EscreeningDashboardApp.filters.freemarkerWhiteSpace',
	'EscreeningDashboardApp.filters.limitToWithEllipsis'
]);

Editors.value('Answer', EScreeningDashboardApp.models.Answer);
Editors.value('MessageHandler', new BytePushers.models.MessageHandler());
Editors.value('Question', EScreeningDashboardApp.models.Question);
Editors.value('Template', new EScreeningDashboardApp.models.Template());
Editors.value('TemplateType', new EScreeningDashboardApp.models.TemplateType());
Editors.value('Survey', EScreeningDashboardApp.models.Survey);
Editors.value('SurveyPage', EScreeningDashboardApp.models.SurveyPage);
Editors.value('TemplateVariableContent', new EScreeningDashboardApp.models.TemplateVariableContent());

Editors.config(function(RestangularProvider, $provide) {

    RestangularProvider.setBaseUrl('services/');
    RestangularProvider.setRequestSuffix('.json');
    // Explicitly setting cache to false because requests were becoming stale
    RestangularProvider.setDefaultHttpFields({cache: false});

    RestangularProvider.addResponseInterceptor(function(data, operation, what) {

        var newResponse;
        // List of array collection endpoints that do not conform to response.payload[resource]
        var listExceptions = ['validations', 'templateTypes', 'sections', 'assessmentVariables', 'answers'];
        var saveExceptions = ['template', 'answers'];

        if (operation === 'getList' && !_.contains(listExceptions, what)) {
            // Add the array directly on the response
            // Pages response does NOT match the endpoint
            newResponse = (what === 'pages') ? data.payload.surveyPages || data : data.payload[what] || data.payload;
            // Add the status as a meta property on the array
            newResponse.status = data.status;
        }

        if(operation === 'put' || operation === 'post') {

            // The saved object is returned on data.payload using the singular form
            // Transform the response by adding the saved object directly on the response
            newResponse = (_.contains(saveExceptions, what) || what.indexOf('batteries/') === 0 || what.indexOf('surveys/') === 0) ? data : data.payload[what.slice(0,-1)] || data.payload;
        }

        if (operation === 'get') {
            // Add the payload directly on the response
            _.extend(data, data.payload);
        }

        return newResponse || data.payload || data;
    });
    
    $provide.decorator('taOptions', ['taRegisterTool', 'taCustomRenderers', 'taSelectableElements', 'textAngularManager', '$delegate', '$modal', 'TemplateBlockService',
                                     function(taRegisterTool, taCustomRenderers, taSelectableElements, textAngularManager, $delegate, $modal, TemplateBlockService){
	    
	    $delegate.setup.textEditorSetup = function($element){
	        $element.attr('template-block-text-editor', '');
	    };
	    
		// Register the custom addVariable tool with textAngular
	    // $delegate is the taOptions we are decorating
		taRegisterTool('insertVariable', {
			display: '<button title="Add Variable" class="btn btn-default"><i class="fa fa-plus"></i> Add Variable</button>',
			tooltiptext: 'Insert Assessment Variable',
			action: function(deferred, restoreSelection) {

				var addVariableTool = this;
		        
		        deferred.promise.then(function(result){
		            addVariableTool.$editor().updateTaBindtaTextElement();
		            
		            return addVariableTool.$editor().updateTaBindtaHtmlElement();
		        });

				var modalInstance = $modal.open({
					templateUrl: 'resources/editors/views/templates/assessmentvariablemodal.html',
					controller: ['$scope', '$modalInstance', 'AssessmentVariableService', function($scope, $modalInstance) {

						$scope.selections = {
							show: true
						};

						$scope.assessmentVariable = {};

						$scope.$watch('assessmentVariable.id', function(newValue, oldValue) {

							if (newValue !== oldValue && $scope.assessmentVariable && $scope.assessmentVariable.id) {
							    var embed = TemplateBlockService.createAVElement($scope.assessmentVariable.id, $scope.assessmentVariable.getName());
							    
								$modalInstance.close(embed);
	                        }

                        }, true);

						$scope.cancel = function() {
						    $modalInstance.close("");
						};

					}]
				});

				modalInstance.result.then(function(embed) {                    
					var $taEl = $("div[id^='taTextElement']");
					
					$taEl.focus();					
					$taEl.find(".rangySelectionBoundary").first().before($(embed));
					
					restoreSelection();
				    
					deferred.resolve();
					
				});

				return false;

			}
		});
		// DO NOT add the button to the default toolbar definition, but if you did, this is how you would:
		//$delegate.toolbar[$delegate.toolbar.length] = ['insertVariable'];
		return $delegate;
	}]);
});

Editors.run(['$rootScope', '$state', '$stateParams', 'editableOptions', 'MessageFactory', function ($rootScope, $state, $stateParams, editableOptions, MessageFactory) {

    // It's very handy to add references to $state and $stateParams to the $rootScope
    // so that you can access them from any scope within your applications.For example,
    // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
    // to active whenever 'assessments.list' or one of its descendents is active.
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

	// Set Angular-xeditable module theme to Bootstrap 3
	editableOptions.theme = 'bs3';

	// Get all flash messages to be added globally
    $rootScope.flashMessages = MessageFactory.get(true);

    $rootScope.messageHandler = new BytePushers.models.MessageHandler();

    /* -------------All this needs to go away --------------------- */

    $rootScope.addMessage = function(message) {
        if(Object.isDefined(message)) {
            $rootScope.messageHandler.addMessage(message);
        }
    };

    /**
     * Adds a message which will stay around for the next state.
     * Needed if you want to:
     *  - pass a message to another state because the action both makes a change and also transitions state
     *  - you want to set a message when a controller is being initialized
     */
    $rootScope.addInterstateMessage = function(message) {
        if(Object.isDefined(message)) {
            $rootScope.messageHandler.addMessage(message, null, 1);
        }
    };

    $rootScope.createSuccessDeleteMessage = function(message) {
        var msg = BytePushers.models.Message.SUCCESS_DELETE_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }

        return new BytePushers.models.Message({type: BytePushers.models.Message.SUCCESSFUL_DELETE, value: msg});
    };

    $rootScope.createSuccessSaveMessage = function (message) {
        var msg = BytePushers.models.Message.SUCCESS_SAVE_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }

        return new BytePushers.models.Message({type: BytePushers.models.Message.SUCCESSFUL_SAVE, value: msg});
    };

    $rootScope.createErrorMessage = function (message) {
        var msg = BytePushers.models.Message.ERROR_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }

        return new BytePushers.models.Message({type: BytePushers.models.Message.ERROR, value: msg});
    };

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.messageHandler.clearMessages();

		MessageFactory.empty();

    });

    //some error logging to reduce the amount of hair I pull out of my head :)
    $rootScope.$on('$stateChangeError',
        function(event, toState, toParams, fromState, fromParams, error){
            console.log("Error transitioning from " + JSON.stringify(fromState) + "\n to state: " + JSON.stringify(toState) + "\n with error: " + JSON.stringify(error));
        });

}]);
