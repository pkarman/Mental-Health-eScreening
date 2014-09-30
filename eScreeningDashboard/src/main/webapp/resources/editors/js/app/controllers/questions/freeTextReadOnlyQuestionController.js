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

    var selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedPageQuestionItem.getItem());
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
                    validationDomainObjects = [],
                    textFormatValidation = selectedQuestionUIObject.textFormatDropDownMenu;

                selectedQuestionUIObject.validations.forEach(function(validationUIObject, index, array){
                    if(Object.isDefined(validationUIObject)){
                        validationDomainObjects.push(new EScreeningDashboardApp.models.Validation(validationUIObject));
                    }
                });

                if (Object.isDefined(selectedQuestionUIObject) && ((Object.isDefined(textFormatValidation) && textFormatValidation.value === "number"))) {
                    $scope.showValidation();

                    $scope.exactLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "exactLength", validationDomainObjects).toUIObject());
                    $scope.minLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minLength", validationDomainObjects).toUIObject());
                    $scope.maxLengthField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxLength", validationDomainObjects).toUIObject());
                    $scope.minValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "minValue", validationDomainObjects).toUIObject());
                    $scope.maxValueField = convertFieldValueToNumber(EScreeningDashboardApp.models.Question.findValidation("name", "maxValue", validationDomainObjects).toUIObject());

                } else {
                    $scope.hideValidation();
                    //TODO: Remove validations from validation array.
                }
            } else {
                $scope.hideValidation();
                //TODO: Remove validations from validation array.
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

    $scope.setTextFormatValidation = function () {
        var selectedQuestionUIObject = $scope.selectedPageQuestionItem.getItem(),
            selectTextFormatValidationMenuItem = $scope.textFormatDropDownMenu;

        if(Object.isDefined(selectTextFormatValidationMenuItem)) {
            if (selectTextFormatValidationMenuItem.name === "dataType") {
                var existingValidation = selectedQuestionUIObject.validations.find(function (validation, index, array) {
                    if(validation.name === selectTextFormatValidationMenuItem.name && validation.value === selectTextFormatValidationMenuItem.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    selectedQuestionUIObject.validations.push(selectTextFormatValidationMenuItem);
                }

                selectedQuestionUIObject.textFormatDropDownMenu = selectTextFormatValidationMenuItem;
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
        var validation;
        if(Object.isDefined($scope.exactLengthField)) {
            if ($scope.exactLengthField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.exactLengthField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    existingValidation.value = $scope.exactLengthField.value;
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.exactLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.setMinLengthValidation = function() {
        var validation;
        if (Object.isDefined($scope.minLengthField)) {
            if ($scope.minLengthField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.minValueField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    existingValidation.value = $scope.minValueField.value;
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.minLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.setMaxLengthValidation = function (){
        var validation;
        if(Object.isDefined($scope.maxLengthField)) {
            if ($scope.maxLengthField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.minValueField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    existingValidation.value = $scope.minValueField.value;
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.maxLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.setMinValueValidation = function () {
        var validation;
        if(Object.isDefined($scope.minValueField)) {
            if ($scope.minValueField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.minValueField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    existingValidation.value = $scope.minValueField.value;
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.minValueField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.setMaxValueValidation = function() {
        var validation;
        if(Object.isDefined($scope.maxValueField)) {
            if ($scope.maxValueField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.maxValueField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    existingValidation.value = $scope.maxValueField.value;
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.maxValueField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };
}]);