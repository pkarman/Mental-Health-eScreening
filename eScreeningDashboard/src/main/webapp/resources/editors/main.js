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
