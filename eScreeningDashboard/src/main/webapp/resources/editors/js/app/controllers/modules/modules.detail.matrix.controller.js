(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailMatrixController', ['$scope', '$state', function ($scope, $state) {

		if (!$scope.question) {
			// Look up the selected question by the id passed into the parameter
			/* TODO
			 $scope.survey.one('questions', $stateParams.questionId).get().then(function(question) {
			 console.log(question);
			 });
			 */
			$scope.question =_.find($scope.surveyPages[0].questions, function(question) {
				return question.id === +$stateParams.questionId;
			});
		}
	}]);

})();
