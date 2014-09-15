/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    $scope.setTextFormatDropDownMenuOptions(textFormatTypeMenuOptions);
    $scope.selectedQuestionUIObject.textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedQuestionUIObject, $scope.textFormatDropDownMenuOptions);
    $scope.showExactLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) && $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) && $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    $scope.parentSave = $scope.save;
    $scope.parentAddToPageQuestion = $scope.addToPageQuestion;
    $scope.parentResetForm = $scope.resetForm;
    $scope.parentEditQuestion = $scope.editQuestion;

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

    $scope.$watch('selectedQuestionUIObject.textFormatDropDownMenu', function (currentlySelectedTextFormatItem, previouslySelectedTextFormatItem) {
        if (currentlySelectedTextFormatItem === previouslySelectedTextFormatItem) {
            return;
        } else {
            if(Object.isDefined(currentlySelectedTextFormatItem) && currentlySelectedTextFormatItem.value === "number") {
                $scope.showValidation();
                if(Object.isDefined($scope.selectedPageQuestionItem) && Object.isDefined($scope.selectedPageQuestionItem.getItem())) {
                    $scope.exactLengthField = convertFieldValueToNumber($scope.selectedPageQuestionItem.getItem().findValidation("name", "exactLength").toUIObject());
                    $scope.minLengthField = convertFieldValueToNumber($scope.selectedPageQuestionItem.getItem().findValidation("name", "minLength").toUIObject());
                    $scope.maxLengthField = convertFieldValueToNumber($scope.selectedPageQuestionItem.getItem().findValidation("name", "maxLength").toUIObject());
                    $scope.minValueField = convertFieldValueToNumber($scope.selectedPageQuestionItem.getItem().findValidation("name", "minValue").toUIObject());
                    $scope.maxValueField = convertFieldValueToNumber($scope.selectedPageQuestionItem.getItem().findValidation("name", "maxValue").toUIObject());
                }
            } else {
                $scope.hideValidation();
            }
        }
    }, true);
	
	$scope.blur = function(){
		$scope.isDirty = true;
	};

    $scope.showValidation = function() {
        $scope.showExactLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxLengthField = (!Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) || $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinValueField = (Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) && $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxValueField = (Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu) && $scope.selectedQuestionUIObject.textFormatDropDownMenu.value === "number")? true : false;
    };

    $scope.hideValidation = function() {
        $scope.showExactLengthField = false;
        $scope.showMinLengthField = false;
        $scope.showMaxLengthField = false;
        $scope.showMinValueField = false;
        $scope.showMaxValueField = false;
    };

	$scope.save = function(){
        $scope.selectedQuestionUIObject.validations = [];

        if(Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu)) {
            if ($scope.selectedQuestionUIObject.textFormatDropDownMenu.name === "dataType" && Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.selectedQuestionUIObject.textFormatDropDownMenu);
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

    $scope.addToPageQuestion = function(resetFormFunction, softReset, state){
        resetFormFunction = (Object.isDefined(resetFormFunction))? resetFormFunction: $scope.resetForm;
        /*softReset = (Object.isBoolean(softReset))? softReset : true;
        state = (Object.isDefined(state))? state: {
            name: "modules.detail.questions.blank",
            params: {selectedQuestionId: -1},
            doTransition: false
        };*/

        $scope.selectedQuestionUIObject.validations = [];

        if(Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu)) {
            if ($scope.selectedQuestionUIObject.textFormatDropDownMenu.name === "dataType" && Object.isDefined($scope.selectedQuestionUIObject.textFormatDropDownMenu.value)) {
                $scope.selectedQuestionUIObject.validations.push($scope.selectedQuestionUIObject.textFormatDropDownMenu);
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

        $scope.parentAddToPageQuestion(resetFormFunction, softReset, state);
    };

    $scope.resetForm = function (softReset, state) {
        softReset = (Object.isBoolean(softReset))? softReset : false;

        $scope.selectedQuestionUIObject.textFormatDropDownMenu = null;
        $scope.exactLengthField.selected = false;
        $scope.exactLengthField.value = null;
        $scope.minLengthField.selected = false;
        $scope.minLengthField.value = null;
        $scope.maxLengthField.selected = false;
        $scope.maxLengthField.value = null;
        $scope.minValueField.selected = false;
        $scope.minValueField.value = null;
        $scope.maxValueField.selected = false;
        $scope.maxValueField.value = null;
        $scope.showExactLengthField = false;
        $scope.showMinLengthField = false;
        $scope.showMaxLengthField = false;
        $scope.showMinValueField = false;
        $scope.showMaxValueField = false;

        $scope.parentResetForm(softReset, state);
    };
	
	$scope.cancel = function(){
		// Not sure exactly what we need to be doing here quite yet, either.
		$state.go('^');
	};
}]);