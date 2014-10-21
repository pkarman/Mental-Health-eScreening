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
                if (message && confirm(message)) {
                    scope.$apply(attrs.ngReallyClick);
                }
            });
        }
    };
}]);

Editors.config(function(RestangularProvider, $provide) {

    $provide.decorator('taOptions', ['taRegisterTool', 'taSelectableElements', 'taCustomRenderers', '$delegate', '$modal', function(taRegisterTool, taSelectableElements, taCustomRenderers, $delegate, $modal){

	    taSelectableElements.push('code');

	    taCustomRenderers.push({
		    // Parse back out: '<div class="ta-insert-video" ta-insert-video src="' + urlLink + '" allowfullscreen="true" width="300" frameborder="0" height="250"></div>'
		    // To correct video element. For now only support youtube
		    selector: 'code',
		    customAttribute: 'ta-insert-variable',
		    renderLogic: function(element){
			    var iframe = angular.element('<iframe></iframe>');
			    var attributes = element.prop("attributes");
			    // loop through element attributes and apply them on iframe
			    angular.forEach(attributes, function(attr) {
				    iframe.attr(attr.name, attr.value);
			    });
			    iframe.attr('src', iframe.attr('ta-insert-variable'));
			    element.replaceWith(iframe);
		    }
	    });

		// $delegate is the taOptions we are decorating
		// register the tool with textAngular
		taRegisterTool('addVariable', {
			display: '<button title="Add Variable" class="btn btn-default"><i class="fa fa-plus"></i> Add Variable</button>',
			tooltiptext: {
				tooltip: 'Insert / edit variable'
			},
			action: function(){

				var assessmentVariable = {
					id: 287,
					typeId: '1',
					name: 'demo_phone',
					displayName: 'phone',
					answerId: 15,
					measureId: 62,
					measureTypeId: 93
				};

				var modalInstance = $modal.open({
					template: '<mhe-assessment-variables assessment-variable="assessmentVariable"></mhe-assessment-variables>',
					controller: function($scope, $modalInstance, $timeout) {

						$timeout(function() {
							$modalInstance.close();
						}, 5000);

					}
				});

				var embed = '<code class="ta-insert-variable">(' + assessmentVariable.name + ')</code>';

				return this.$editor().wrapSelection('insertHTML', embed, true);

			},
			activeState: function(commonElement){
				var result = false;
				console.log('commonElement', commonElement);
				return this.$editor().queryCommandState('ta-insert-variable');
			},
			onElementSelect: {
				element: 'code',
				action: function (event, $element, editorScope) {
					// setup the editor toolbar
					// Credit to the work at http://hackerwins.github.io/summernote/ for this editbar logic
					event.preventDefault();
					editorScope.displayElements.popover.css('width', '435px');
					var container = editorScope.displayElements.popoverContainer;
					container.empty();
					container.css('line-height', '28px');
					var link = angular.element('<a href="' + $element.attr('href') + '" target="_blank">' + $element.attr('href') + '</a>');
					link.css({
						'display': 'inline-block',
						'max-width': '200px',
						'overflow': 'hidden',
						'text-overflow': 'ellipsis',
						'white-space': 'nowrap',
						'vertical-align': 'middle'
					});
					//container.append(link);
					var buttonGroup = angular.element('<div class="btn-group pull-right">');
					var reLinkButton = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on"><i class="fa fa-edit icon-edit"></i></button>');
					reLinkButton.on('click', function (event) {
						event.preventDefault();
						var urlLink = window.prompt(taTranslations.insertLink.dialogPrompt, $element.attr('href'));
						if (urlLink && urlLink !== '' && urlLink !== 'http://') {
							$element.attr('href', urlLink);
							editorScope.updateTaBindtaTextElement();
						}
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
					var targetToggle = angular.element('<button type="button" class="btn btn-default btn-sm btn-small" tabindex="-1" unselectable="on">Open in New Window</button>');
					if ($element.attr('target') === '_blank') {
						targetToggle.addClass('active');
					}
					targetToggle.on('click', function (event) {
						event.preventDefault();
						$element.attr('target', ($element.attr('target') === '_blank') ? '' : '_blank');
						targetToggle.toggleClass('active');
						editorScope.updateTaBindtaTextElement();
					});
					buttonGroup.append(targetToggle);
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

Editors.run(['$rootScope', '$state', '$stateParams', '$modal', function ($rootScope,   $state,   $stateParams,  $modal) {
    // It's very handy to add references to $state and $stateParams to the $rootScope
    // so that you can access them from any scope within your applications.For example,
    // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
    // to active whenever 'assessments.list' or one of its descendents is active.
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

}]);
Editors.value('MessageHandler', new BytePushers.models.MessageHandler());
Editors.value('TemplateType', new EScreeningDashboardApp.models.TemplateType());
