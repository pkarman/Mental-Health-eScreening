(function() {
    'use strict';

    angular.module('Editors').controller('RulesDetailController', ['$scope', '$filter', 'rule', 'assessmentVariables', 'consults', 'healthFactors', 'dashboardAlerts', 'questions', 'MessageFactory', function($scope, $filter, rule, assessmentVariables, consults, healthFactors, dashboardAlerts, questions, MessageFactory) {

		var removeQueue = [], addQueue = [];

        $scope.rule = rule;
		$scope.consults = consults;
		$scope.healthFactors = healthFactors;
		$scope.dashboardAlerts = dashboardAlerts;
		$scope.questions = questions;
		$scope.assessmentVariables = assessmentVariables;
		$scope.selectedEvents = {};
		$scope.alerts = MessageFactory.get();
		$scope.showValidationMessages = false;
		$scope.enableTypeDropdown = false;

		rule.getList('events').then(function(events) {
			$scope.selectedEvents.consults = $filter('filter')(events, {type: 1});
			$scope.selectedEvents.healthFactors = $filter('filter')(events, {type: 2});
			$scope.selectedEvents.dashboardAlerts = $filter('filter')(events, {type: 3});
			$scope.selectedEvents.questions = $filter('filter')(events, {type: 4});
		});

		$scope.removeEvent = function removeEvent(event) {
			removeQueue.push(event.id);
		};

		$scope.addEvent = function addEvent(event) {
			addQueue.push(event);
		};

		$scope.addAndCondition = function addAndCondition() {
			$scope.rule.condition.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig));
		};

		$scope.addOrCondition = function addOrCondition() {
			$scope.rule.condition.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.OrConditionMinimumConfig));
		};

		$scope.saveRule = function saveRule() {
			$scope.rule.save().then(function(response) {

				$scope.rule.id = response.id;

				_.each(removeQueue, function(id) {
					$scope.rule.customDELETE('events/' + id);
				});

				_.each(addQueue, function(event) {
					$scope.rule.customPOST(event, 'events');
				});

				MessageFactory.success('Rule successfully saved.');
			}, function(response) {
				MessageFactory.error('There was a problem saving the rule. Please try again.');
			});

		};

    }]);
})();

