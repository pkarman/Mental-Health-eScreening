/**
 * Created by Bryan Henderson on 4/16/2014.
 */
var Editors = angular.module("Editors",
    [
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
        'angularUtils.directives.uiBreadcrumbs',
        'EscreeningDashboardApp.services.battery',
        'EscreeningDashboardApp.services.survey',
        'EscreeningDashboardApp.services.surveypage',
        'EscreeningDashboardApp.services.surveysection',
        'EscreeningDashboardApp.services.question',
        'EscreeningDashboardApp.services.template',
        'EscreeningDashboardApp.services.templateType',
        'EscreeningDashboardApp.services.assessmentVariable',
        'EscreeningDashboardApp.services.question',
        'EscreeningDashboardApp.filters.messages',
        'EscreeningDashboardApp.filters.freemarkerWhiteSpace',
        'EscreeningDashboardApp.filters.limitToWithEllipsis'
    ]);

/**
 * A generic confirmation for risky actions.
 * Usage: Add attributes: ng-really-message="Are you sure"? ng-really-click="takeAction()" function
 */
Editors.directive('ngReallyClick', [function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                var message = attrs.ngReallyMessage;
                var shouldSkip = Object.isDefined(attrs.ngReallySkipWhen) 
                    && attrs.ngReallySkipWhen.length > 0 
                    && scope.$apply(attrs.ngReallySkipWhen);
                    
                if (shouldSkip || (message && confirm(message))) {
                    scope.$apply(attrs.ngReallyClick);
                }
            });
        }
    };
}]);

Editors.config(function(RestangularProvider, $provide) {

    $provide.decorator('taOptions', ['taRegisterTool', 'taSelectableElements', 'taCustomRenderers', '$delegate', '$modal', function(taRegisterTool, taSelectableElements, taCustomRenderers, $delegate, $modal){

	    // Add <code></code> as selectable element
	    taSelectableElements.push('code');

	    // Add code with class attribute ta-insert-variable as custom renderer
	    taCustomRenderers.push({
		    selector: 'code',
		    customAttribute: 'ta-insert-variable',
		    renderLogic: function(element){}
	    });

		// Register the custom addVariable tool with textAngular
	    // $delegate is the taOptions we are decorating
		taRegisterTool('addVariable', {
			display: '<button title="Add Variable" class="btn btn-default"><i class="fa fa-plus"></i> Add Variable</button>',
			tooltiptext: 'Insert / edit assessment variable',
			action: function() {

				var addVariableTool = this;

				var embed = '<code class="ta-insert-variable">(no_selection)</code>&nbsp;';

				var modalInstance = $modal.open({
					templateUrl: 'resources/editors/views/templates/assessmentvariablemodal.html',
					controller: ['$scope', '$modalInstance', 'AssessmentVariableService', function($scope, $modalInstance, AssessmentVariableService) {

						$scope.assessmentVariables = AssessmentVariableService.list;

						$scope.assessmentVariable = {};

						$scope.close = function() {
							$modalInstance.close($scope.assessmentVariable);
						};

						$scope.cancel = function() {
							$modalInstance.dismiss();
						};

					}]
				});

				modalInstance.result.then(function(assessmentVariable) {

					var variableName;

					if (assessmentVariable.id) {
						// TODO add this logic to domain object
						variableName = assessmentVariable.name || assessmentVariable.displayName || 'var' + assessmentVariable.id;

						embed = '<code class="ta-insert-variable">(' + variableName  + ')</code>&nbsp;';

					} else {
						embed = '<code class="ta-insert-variable">(updated_empty)</code>&nbsp;';
					}

					console.log(embed);

					addVariableTool.$editor().wrapSelection('insertHTML', embed, true);

				});

				return addVariableTool;

			},
			activeState: function(commonElement){
				var result = false;
				return this.$editor().queryCommandState('ta-insert-variable');
			},
			onElementSelect: {
				element: 'code',
				action: function (event, $element, editorScope) {
					// Setup the editor toolbar
					// Edit bar logic based upon http://hackerwins.github.io/summernote
					event.preventDefault();
					editorScope.displayElements.popover.css('width', '100px');
					var container = editorScope.displayElements.popoverContainer;
					container.empty();
					container.css('line-height', '28px');
					var buttonGroup = angular.element('<div class="btn-group">');
					var reLinkButton = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on"><i class="fa fa-edit icon-edit"></i></button>');
					reLinkButton.on('click', function (event) {

						event.preventDefault();

						var modalInstance = $modal.open({

							templateUrl: 'resources/editors/views/templates/assessmentvariablemodal.html',
							controller: function($scope, $modalInstance, $timeout) {

								$timeout(function() {
									$modalInstance.close();
								}, 5000);

								$scope.updateVariable = function() {
									$element.text('updated_var');
									editorScope.updateTaBindtaTextElement();
								}
							}
						});
						editorScope.hidePopover();
					});
					buttonGroup.append(reLinkButton);
					var unLinkButton = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on"><i class="fa fa-unlink icon-unlink"></i></button>');
					// directly before this click event is fired a digest is fired off whereby the reference to $element is orphaned off
					unLinkButton.on('click', function (event) {
						event.preventDefault();
						$element.replaceWith($element.contents());
						editorScope.updateTaBindtaTextElement();
						editorScope.hidePopover();
					});
					buttonGroup.append(unLinkButton);
					container.append(buttonGroup);
					editorScope.showPopover($element);
				}
			}
		});
		// add the button to the default toolbar definition
		$delegate.toolbar[$delegate.toolbar.length] = ['addVariable'];
		return $delegate;
	}]);
});


Editors.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});

Editors.run(['$rootScope', '$state', '$stateParams', '$modal', 'Restangular', function ($rootScope,   $state,   $stateParams,  $modal, Restangular) {
    Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {

        if(Object.isDefined(response) && Object.isDefined(response.data)){
            if(Object.isArray(response.data.errorMessages)){
                response.data.errorMessages.forEach(function (errorMessage) {
                    $rootScope.addMessage(new BytePushers.models.Message({type: BytePushers.models.Message.ERROR, value: errorMessage.description}));
                });
            }
        }

        return true; // error not handled
    });
    // It's very handy to add references to $state and $stateParams to the $rootScope
    // so that you can access them from any scope within your applications.For example,
    // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
    // to active whenever 'assessments.list' or one of its descendents is active.
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

}]);
Editors.value('MessageHandler', new BytePushers.models.MessageHandler());
Editors.value('TemplateType', new EScreeningDashboardApp.models.TemplateType());
Editors.value('Template', new EScreeningDashboardApp.models.Template());
Editors.value('AssessmentVariableInfo', new EScreeningDashboardApp.models.AssessmentVariableInfo());
