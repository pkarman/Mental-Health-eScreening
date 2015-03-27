(function () {
	'use strict';

	Editors.controller('RulesController', ['$scope', '$state', '$filter', 'ngTableParams', 'rules', 'MessageFactory', function ($scope, $state, $filter, ngTableParams, rules, MessageFactory) {

		$scope.rules = rules;
		$scope.alerts = MessageFactory.get();

		$scope.tableParams = new ngTableParams({
			page: 1, // show first page
			count: 10, // count per page
			filter: {
				name: ''
			},
			sorting: {
				name: 'asc'
			}
		}, {
			total: $scope.rules.length,
			getData: function ($defer, params) {
				// use build-in angular filter
				params.total($scope.rules.length);
				var filteredData = params.filter() ?
					$filter('filter')($scope.rules, params.filter()) : $scope.rules;
				var orderedData = params.sorting() ?
					$filter('orderBy')(filteredData, params.orderBy()) : $scope.rules;
				//params.total(orderedData.length); // set total for recalc pagination
				$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			}
		});

		/* ---- Button Actions ---- */
		$scope.editRule = function (rule) {
			$scope.rule = rule;
			$state.go('rules.detail', {id: rule.id});
		};

		$scope.deleteRule = function deleteRule(ruleToRemove) {
			ruleToRemove.remove().then(function (response) {
				MessageFactory.clear();
				MessageFactory.success("Rule '" + ruleToRemove.name + "' has been deleted.");
				
				$scope.rules.splice($scope.rules.indexOf(ruleToRemove), 1);
				$scope.tableParams.reload();
			}, function (result) {
				MessageFactory.error("There was an error deleting the rule named: " + rule.name);
			});
		};

		$scope.cancel = function () {
			$state.go('home');
		};
	}]);

})();
