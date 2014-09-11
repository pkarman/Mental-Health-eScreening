/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    $scope.currentlySelectedTextFormatDropDownMenuOptions = textFormatTypeMenuOptions;
    $scope.currentlySelectedTextFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedQuestionUIObject, $scope.currentlySelectedTextFormatDropDownMenuOptions);
    $scope.showExactLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) && $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) && $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    $scope.parentSave = $scope.save;
    var selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
    var convertFieldValueToNumber = function (validationUIObject) {
        if (Object.isDefined(validationUIObject) && Object.isDefined(validationUIObject.value)) {
            validationUIObject.value = parseInt(validationUIObject.value);
        }
        return validationUIObject;
    };

    $scope.exactLengthField = convertFieldValueToNumber(selectedQuestionDomainObject.findValidation("name", "exactLength").toUIObject());
    $scope.minLengthField = convertFieldValueToNumber(selectedQuestionDomainObject.findValidation("name", "minLength").toUIObject());
    $scope.maxLengthField = convertFieldValueToNumber(selectedQuestionDomainObject.findValidation("name", "maxLength").toUIObject());
    $scope.minValueField = convertFieldValueToNumber(selectedQuestionDomainObject.findValidation("name", "minValue").toUIObject());
    $scope.maxValueField = convertFieldValueToNumber(selectedQuestionDomainObject.findValidation("name", "maxValue").toUIObject());


	$scope.isDirty = false;
	
	$scope.blur = function(){
		$scope.isDirty = true;
	};

    $scope.showValidation = function() {
        $scope.showExactLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxLengthField = (!Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) || $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinValueField = (Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) && $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxValueField = (Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu) && $scope.currentlySelectedTextFormatDropDownMenu.value === "number")? true : false;
    };

	$scope.save = function(){
        $scope.selectedQuestionUIObject.validations = [];

        if(Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu)) {
            if ($scope.currentlySelectedTextFormatDropDownMenu.name === "dataType" && Object.isDefined($scope.currentlySelectedTextFormatDropDownMenu.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.currentlySelectedTextFormatDropDownMenu);
            }
        }

        if(Object.isDefined($scope.exactLengthField)) {
            if ($scope.exactLengthField.selected && Object.isDefined($scope.exactLengthField.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.exactLengthField);
            }
        }

        if(Object.isDefined($scope.minLengthField)) {
            if ($scope.minLengthField.selected && Object.isNumber($scope.minLengthField.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.minLengthField);
            }
        }

        if(Object.isDefined($scope.maxLengthField)) {
            if ($scope.maxLengthField.selected && Object.isNumber($scope.maxLengthField.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.maxLengthField);
            }
        }

        if(Object.isDefined($scope.minValueField)) {
            if ($scope.minValueField.selected && Object.isNumber($scope.minValueField.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.minValueField);
            }
        }

        if(Object.isDefined($scope.maxValueField)) {
            if ($scope.maxValueField.selected && Object.isNumber($scope.maxValueField.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.maxValueField);
            }
        }

        var updatedSelectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
        console.info("freeTextReadOnlyQuestionController.save() method:\n" + updatedSelectedQuestionDomainObject);

        $scope.parentSave();
	};
	
	$scope.cancel = function(){
		// Not sure exactly what we need to be doing here quite yet, either.
		$state.go('^');
	};
}]);