/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('selectMultipleQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'answerTypeMenuOptions', function($rootScope, $scope, $state,QuestionService, answerTypeMenuOptions){
	$scope.question = EScreeningDashboardApp.models.Question.toUIObjects($rootScope.selectedQuestion);
	$scope.answerTypeMenuOptions = answerTypeMenuOptions;
	$scope.isDirty = false;
	
	$scope.blur = function(){
		$scope.isDirty = true;
	};
	
	$scope.save = function(){
		// Not sure exactly what we need to be doing here quite yet.
		EscreeningDashboardApp.services.QuestionService.update(QuestionService.setUpdateQuestionRequestParameter($scope.question)).then(function(question){
			// Process this.
		});
	};
	
	$scope.cancel = function(){
		// Not sure exactly what we need to be doing here quite yet, either.
		$state.go('^');
	};
}]);