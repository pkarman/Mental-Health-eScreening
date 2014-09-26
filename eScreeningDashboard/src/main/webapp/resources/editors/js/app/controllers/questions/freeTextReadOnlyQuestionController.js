/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    $scope.setTextFormatDropDownMenuOptions(textFormatTypeMenuOptions);
    $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedPageQuestionItem, $scope.textFormatDropDownMenuOptions);
    $scope.showExactLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    $scope.parentResetForm = $scope.resetForm;
    $scope.parentEditQuestion = $scope.editQuestion;

    var selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedPageQuestionItem);
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

    $scope.$watch('selectedPageQuestionItem.getItem().textFormatDropDownMenu', function (currentlySelectedTextFormatItem, previouslySelectedTextFormatItem) {
        if (currentlySelectedTextFormatItem === previouslySelectedTextFormatItem) {
            return;
        } else {
            if(Object.isDefined(currentlySelectedTextFormatItem) && currentlySelectedTextFormatItem.value === "number") {
                $scope.showValidation();
                if(Object.isDefined($scope.selectedPageQuestionItem) && Object.isDefined($scope.selectedPageQuestionItem.getItem())) {
                    $scope.exactLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "exactLength", $scope.selectedPageQuestionItem.getItem().validations).toUIObject());
                    $scope.minLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minLength", $scope.selectedPageQuestionItem.getItem().validations).toUIObject());
                    $scope.maxLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxLength", $scope.selectedPageQuestionItem.getItem().validations).toUIObject());
                    $scope.minValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minValue", $scope.selectedPageQuestionItem.getItem().validations).toUIObject());
                    $scope.maxValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxValue", $scope.selectedPageQuestionItem.getItem().validations).toUIObject());
                }
            } else {
                $scope.hideValidation();
            }
        }
    }, true);

    $scope.showValidation = function() {
        $scope.showExactLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxLengthField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinValueField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxValueField = (Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu) && $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value === "number")? true : false;
    };

    $scope.hideValidation = function() {
        $scope.showExactLengthField = false;
        $scope.showMinLengthField = false;
        $scope.showMaxLengthField = false;
        $scope.showMinValueField = false;
        $scope.showMaxValueField = false;
    };

    //TODO: SEEM TO BE A PROBLEM SAVING OR RETRIVING AND SETTING TEXT FORMAT DROPDOWN MENU.
    $scope.setTextFormatValidation = function () {
        if(Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu)) {
            if ($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name === "dataType" && Object.isDefined($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu);
                }
            }
        } else {
            //TODO: REMOVE ANY EXISTING TEXT FORMAT VALIDATION FORM VALIDATIONS ARRAY.
            /*var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                if(validation.name === "dataType" && (validation.value === "email" || validation.value === "date" || validation.value === "number"){
                    return true;
                }

                return false;
            });*/
        }
    };

    $scope.setExactLengthValidation = function (){
        if(Object.isDefined($scope.exactLengthField)) {
            if ($scope.exactLengthField.selected && Object.isDefined($scope.exactLengthField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.exactLengthField);
                }
            }
        }
    };

    $scope.setMinLengthValidation = function() {
        if (Object.isDefined($scope.minLengthField)) {
            if ($scope.minLengthField.selected && Object.isNumber($scope.minLengthField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.minLengthField);
                }
            }
        }
    };

    $scope.setMaxLengthValidation = function (){
        if(Object.isDefined($scope.maxLengthField)) {
            if ($scope.maxLengthField.selected && Object.isNumber($scope.maxLengthField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.maxLengthField);
                }
            }
        }
    };

    $scope.setMinValueValidation = function () {
        if(Object.isDefined($scope.minValueField)) {
            if ($scope.minValueField.selected && Object.isNumber($scope.minValueField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.minValueField);
                }
            }
        }
    };

    $scope.setMaxValueValidation = function() {
        if(Object.isDefined($scope.maxValueField)) {
            if ($scope.maxValueField.selected && Object.isNumber($scope.maxValueField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.name &&
                        validation.value === $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    $scope.selectedPageQuestionItem.getItem().validations.push($scope.maxValueField);
                }
            }
        }
    };
}]);