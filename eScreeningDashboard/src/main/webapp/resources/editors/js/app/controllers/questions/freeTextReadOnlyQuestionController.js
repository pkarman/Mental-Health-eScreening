/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    $scope.textFormatTypeMenuOptions = textFormatTypeMenuOptions;
    $scope.currentlySelectedTextFormatMenuOption = $scope.getDefaultTextFormatType($scope.selectedQuestionUIObject, $scope.textFormatTypeMenuOptions);
    $scope.showExactLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatMenuOption) || $scope.currentlySelectedTextFormatMenuOption.value === "number")? true : false;
    $scope.showMinLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatMenuOption) || $scope.currentlySelectedTextFormatMenuOption.value === "number")? true : false;
    $scope.showMaxLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatMenuOption) || $scope.currentlySelectedTextFormatMenuOption.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.currentlySelectedTextFormatMenuOption) && $scope.currentlySelectedTextFormatMenuOption.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.currentlySelectedTextFormatMenuOption) && $scope.currentlySelectedTextFormatMenuOption.value === "number")? true : false;

    var selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
    $scope.exactLengthField = selectedQuestionDomainObject.findValidation("name", "exactLength").toUIObject();
    $scope.minLengthField = selectedQuestionDomainObject.findValidation("name", "minLength").toUIObject();
    $scope.maxLengthField = selectedQuestionDomainObject.findValidation("name", "maxLength").toUIObject();
    $scope.minValueField = selectedQuestionDomainObject.findValidation("name", "minValue").toUIObject();
    $scope.maxValueField = selectedQuestionDomainObject.findValidation("name", "maxValue").toUIObject();


	$scope.isDirty = false;
	
	$scope.blur = function(){
		$scope.isDirty = true;
	};
	
	$scope.save = function(){
		// Not sure exactly what we need to be doing here quite yet.
		QuestionService.update(QuestionService.setUpdateQuestionRequestParameter($scope.question)).then(function(question){
			// Process this.
		});
	};
	
	$scope.cancel = function(){
		// Not sure exactly what we need to be doing here quite yet, either.
		$state.go('^');
	};
}]);