(function() {
    'use strict';

    angular.module('Editors').controller('RulesDetailController', ['$scope', 'rule', 'assessmentVariables', 'consults', 'healthFactors', 'dashboardAlerts', 'questions', 'MessageFactory', function($scope, rule, assessmentVariables, consults, healthFactors, dashboardAlerts, questions, MessageFactory){

        $scope.rule = rule;
		$scope.consults = consults;
		$scope.healthFactors = healthFactors;
		$scope.dashboardAlerts = dashboardAlerts;
		$scope.questions = questions;

		$scope.alerts = MessageFactory.get();

    }]);
})();

