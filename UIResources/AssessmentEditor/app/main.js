/**
 * Created by Bryan on 2/3/14.
 */
var AssessmentEditor = angular.module("AssessmentEditor", ['ui.router', 'ui.bootstrap', 'ngAnimate', 'textAngular', 'dd.directives.dragdrop', 'ngGrid', 'xeditable']);

AssessmentEditor.run(
    [        '$rootScope', '$state', '$stateParams',
        function ($rootScope,   $state,   $stateParams) {

            // It's very handy to add references to $state and $stateParams to the $rootScope
            // so that you can access them from any scope within your applications.For example,
            // <li ng-class="{ active: $state.includes('assessments.list') }"> will set the <li>
            // to active whenever 'assessments.list' or one of its descendents is active.
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
            $rootScope.currAssessment = {};
        }]);