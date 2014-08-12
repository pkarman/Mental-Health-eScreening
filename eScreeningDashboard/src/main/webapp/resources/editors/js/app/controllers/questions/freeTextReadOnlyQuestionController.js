/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', function($rootScope, $scope, $state,QuestionService){
	$scope.question = EScreeningDashboardApp.models.Question.toUIObjects($rootScope.selectedQuestion);
    $scope.isExactLength = false;
    $scope.isMinLength = false;
    $scope.minLengthVal = "";
    $scope.minLenObj = {name:'minLength', value:$scope.minLengthVal};
    $scope.vistaDisable = true;

    $scope.isMaxLength = false;
    $scope.maxLengthVal = "";
    $scope.maxLenObj = {name:'maxLength', value:$scope.maxLengthVal};

    $scope.isRequired = false;
    $scope.isReqObj = {name:'exactLength', value:$scope.isRequired};
	
	$scope.isDirty = false;
	$scope.types = [
	                {name:"Free Text", value:"freeText"},
	                {name:"Read Only", value:"readOnly"}
	                ];
	
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