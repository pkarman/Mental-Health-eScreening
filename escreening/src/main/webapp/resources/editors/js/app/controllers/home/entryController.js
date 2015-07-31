(function() {
    'use strict';
    angular.module('Editors').controller('entryController', ['$scope', '$state', function($scope, $state) {

        $scope.entryApp = '/escreeningdashboard/';

        $scope.goToModuleNew = function () {
            $state.go('modules.detail');
        };

        $scope.goToModuleEdit = function () {
            $state.go('modules');
        };

        $scope.goToBatteryNew = function () {
            $state.go('batteries.detail');
        };

        $scope.goToBatteryEdit = function () {
            $state.go('batteries.list');
        };

        $scope.goToSections = function () {
            $state.go('sections');
        };

		$scope.goToRuleNew = function() {
			$state.go('rules.detail');
		};

		$scope.goToRuleEdit = function() {
			$state.go('rules');
		};

    }]);

})();
