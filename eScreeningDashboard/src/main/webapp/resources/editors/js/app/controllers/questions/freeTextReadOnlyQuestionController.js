/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    $scope.setTextFormatDropDownMenuOptions(textFormatTypeMenuOptions);
    $scope.textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedPageQuestionItem.getItem(), $scope.textFormatDropDownMenuOptions);
    $scope.showExactLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.parentResetForm = $scope.resetForm;
    var parentEditQuestion = $scope.editQuestion;

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

    $scope.$watch('selectedPageQuestionItem', function(currentlySelectedPageQuestionItem, previouslySelectedPageQuestionItem){
        if(currentlySelectedPageQuestionItem === previouslySelectedPageQuestionItem) {
            return;
        } else {
            $scope.textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedPageQuestionItem.getItem(), $scope.textFormatDropDownMenuOptions);
        }
    });

    $scope.$watch('textFormatDropDownMenu', function (currentlySelectedTextFormatMenuItem, previouslySelectedTextFormatMenuItem) {
        if (currentlySelectedTextFormatMenuItem === previouslySelectedTextFormatMenuItem) {
            return;
        } else {
            if(Object.isDefined(currentlySelectedTextFormatMenuItem) && $scope.selectedPageQuestionItem.isQuestion()) {
                var selectedQuestionUIObject = $scope.selectedPageQuestionItem.getItem(),
                    textFormatValidation = selectedQuestionUIObject.textFormatDropDownMenu;

                if (Object.isDefined(selectedQuestionUIObject) && ((Object.isDefined(textFormatValidation) && textFormatValidation.value === "number"))) {
                    $scope.showValidation();
                    if (Object.isDefined(selectedQuestionUIObject)) {
                        $scope.exactLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "exactLength", selectedQuestionUIObject.validations).toUIObject());
                        $scope.minLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minLength", selectedQuestionUIObject.validations).toUIObject());
                        $scope.maxLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxLength", selectedQuestionUIObject.validations).toUIObject());
                        $scope.minValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minValue", selectedQuestionUIObject.validations).toUIObject());
                        $scope.maxValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxValue", selectedQuestionUIObject.validations).toUIObject());
                    }
                } else {
                    $scope.hideValidation();
                }
            } else {
                $scope.hideValidation();
            }
        }
    }, true);

    $scope.showValidation = function() {
        $scope.showExactLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMinValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
        $scope.showMaxValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    };

    $scope.hideValidation = function() {
        $scope.showExactLengthField = false;
        $scope.showMinLengthField = false;
        $scope.showMaxLengthField = false;
        $scope.showMinValueField = false;
        $scope.showMaxValueField = false;
    };

    /*$scope.editQuestion = function(selectedPageQuestionItem){
        var selectedQuestionUIObject = selectedPageQuestionItem.getItem(),
            textFormatValidationMenuItem = $scope.textFormatDropDownMenu;

        $scope.textFormatDropDownMenu = $scope.getDefaultTextFormatType(selectedQuestionUIObject, $scope.textFormatDropDownMenuOptions);
        parentEditQuestion(selectedPageQuestionItem);
    };*/

    $scope.setTextFormatValidation = function () {
        var selectedQuestionUIObject = $scope.selectedPageQuestionItem.getItem(),
            textFormatValidationMenuItem = $scope.textFormatDropDownMenu;

        if(Object.isDefined(textFormatValidationMenuItem)) {
            if (textFormatValidationMenuItem.name === "dataType") {
                var existingValidation = selectedQuestionUIObject.validations.find(function (validation, index, array) {
                    if(validation.name === textFormatValidationMenuItem.name && validation.value === textFormatValidationMenuItem.value) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {

                } else {
                    $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu = textFormatValidationMenuItem;
                    $scope.selectedPageQuestionItem.getItem().validations.push(textFormatValidationMenuItem);
                }
            }
        } else {
            $scope.textFormatDropDownMenu = null;
            $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu = null;
            $scope.selectedPageQuestionItem.getItem().validations.forEach(function (validation, index, array) {
                if(Object.isDefined(validation)){
                    if(validation.name === "dataType" && (validation.value === "email" || validation.value === "date" || validation.value === "number")){
                        array.splice(index, 1);
                    }
                }
            });
        }
    };

    $scope.setExactLengthValidation = function (){
        if(Object.isDefined($scope.exactLengthField)) {
            if ($scope.exactLengthField.selected && Object.isDefined($scope.exactLengthField.value)) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.textFormatDropDownMenu.name &&
                        validation.value === $scope.textFormatDropDownMenu.value) {
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
                    if(validation.name === $scope.textFormatDropDownMenu.name &&
                        validation.value === $scope.textFormatDropDownMenu.value) {
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
                    if(validation.name === $scope.textFormatDropDownMenu.name &&
                        validation.value === $scope.textFormatDropDownMenu.value) {
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
                    if(validation.name === $scope.textFormatDropDownMenu.name &&
                        validation.value === $scope.textFormatDropDownMenu.value) {
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
                    if(validation.name === $scope.textFormatDropDownMenu.name &&
                        validation.value === $scopeestionItem.getItem().textFormatDropDownMenu.value) {
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