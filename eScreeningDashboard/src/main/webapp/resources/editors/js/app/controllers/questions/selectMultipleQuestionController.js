/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('selectMultipleQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'answerTypeMenuOptions', function($rootScope, $scope, $state,QuestionService, answerTypeMenuOptions){
	$scope.question = EScreeningDashboardApp.models.Question.toUIObjects($rootScope.selectedQuestion);
    //$scope.currentlySelectedAnswerTypeDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedQuestionUIObject, $scope.currentlySelectedTextFormatDropDownMenuOptions);
	$scope.answerTypeMenuOptions = answerTypeMenuOptions;
    $scope.parentSave = $scope.save;
	$scope.isDirty = false;
	
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