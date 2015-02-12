(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailListController', ['$scope', '$state', '$stateParams', 'questionTypes', function($scope, $state, $stateParams, questionTypes){

		if (!$scope.question) {
			$state.go('modules.detail', { surveyId: $stateParams.surveyId });
		}

        $scope.questionTypes = questionTypes;

        $scope.editQuestion = function() {
            var stateName = $scope.getStateName($scope.question.type);

            $state.go(stateName);
        };

		// Using this to require clicking next before clicking save
		$scope.next = '';

    }]);
})();
