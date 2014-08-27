/**
 *  Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('selectMultipleMatrixQuestionController', ['$rootScope', '$scope', '$state', 'QuestionService', 'answerTypeMenuOptions', function($rootScope, $scope, $state,QuestionService, answerTypeMenuOptions){
	$scope.question = EScreeningDashboardApp.models.Question.toUIObjects($rootScope.selectedQuestion);
    $scope.answerTypeMenuOptions = answerTypeMenuOptions;
	$scope.isDirty = false;
    $scope.parentSave = $scope.save;
	
	$scope.blur = function(){
		$scope.isDirty = true;
	};
	
	$scope.save = function(){
        $scope.parentSave();
	};
	
	$scope.cancel = function(){
		$state.go('^');
	};
}]);