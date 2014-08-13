/**
 * Created by Bryan Henderson on 4/16/2014.
 */
var Editors = angular.module("Editors", ['ui.router', 'ui.bootstrap', 'ngAnimate','textAngular','angularUtils.directives.uiBreadcrumbs']);

Editors.run(
    [        '$rootScope', '$state', '$stateParams', '$modal',
        function ($rootScope,   $state,   $stateParams,  $modal) {

            // It's very handy to add references to $state and $stateParams to the $rootScope
            // so that you can access them from any scope within your applications.For example,
            // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
            // to active whenever 'assessments.list' or one of its descendents is active.
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;

        }]);
