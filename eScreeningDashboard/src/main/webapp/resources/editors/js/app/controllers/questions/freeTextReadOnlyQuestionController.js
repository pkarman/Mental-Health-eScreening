/**
 * Created by Bryan Henderson - 08/07/2014
 */
Editors.controller('freeTextReadOnlyQuestionController', ['$rootScope', '$scope', '$state','QuestionService', 'textFormatTypeMenuOptions', function($rootScope, $scope, $state, QuestionService, textFormatTypeMenuOptions){
    var parentEditQuestion = $scope.editQuestion,
        selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedPageQuestionItem.getItem()),
        validationsArrayConfiguration = [
            {name: 'dataType',      value: 'email'},
            {name: 'dataType',      value: 'date'},
            {name: 'dataType',      value: 'number'},
            {name: 'exactLength',   value: undefined},
            {name: 'minLength',     value: undefined},
            {name: 'maxLength',     value: undefined},
            {name: 'minValue',      value: undefined},
            {name: 'maxValue',      value: undefined}
        ];
    var convertFieldValueToNumber = function (validationUIObject) {
            if (Object.isDefined(validationUIObject) && Object.isDefined(validationUIObject.value)) {
                validationUIObject.value = parseInt(validationUIObject.value);
            }
            return validationUIObject;
        },
        removeValidations = function(validationsToBeRemovedArray) {
            var someValidationToBeRemoved,
                validations = $scope.selectedPageQuestionItem.getItem().validations,
                existingValidation = null,
                i = 0;

            if(Object.isArray(validationsToBeRemovedArray) && validationsToBeRemovedArray.length > 0) {
                for (i = 0; i < validations.length; i++) {
                    existingValidation = validations[i];
                    if (Object.isDefined(existingValidation)) {
                        someValidationToBeRemoved = validationsToBeRemovedArray.find(function (validationToBeRemoved, index) {
                            if (Object.isDefined(validationToBeRemoved)) {
                                if (validationToBeRemoved.name === "dataType" && existingValidation.name === "dataType" &&
                                    validationToBeRemoved.value === existingValidation.value) {
                                    return true;
                                } else if (validationToBeRemoved.name === existingValidation.name &&
                                    validationToBeRemoved.name !== "dataType" && existingValidation.name !== "dataType") {
                                    return true;
                                }
                            }
                            return false;
                        });

                        if (Object.isDefined(someValidationToBeRemoved)) {
                            validations.splice(i--, 1);
                        }
                    }
                }
            }
        };

    $scope.setTextFormatDropDownMenuOptions(textFormatTypeMenuOptions);
    $scope.textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedPageQuestionItem.getItem(), $scope.textFormatDropDownMenuOptions);
    $scope.showExactLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxLengthField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMinValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.showMaxValueField = (Object.isDefined($scope.textFormatDropDownMenu) && $scope.textFormatDropDownMenu.value === "number")? true : false;
    $scope.parentResetForm = $scope.resetForm;


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
            if(Object.isDefined($scope.selectedPageQuestionItem)){
                $scope.textFormatDropDownMenu = $scope.getDefaultTextFormatType($scope.selectedPageQuestionItem.getItem(), $scope.textFormatDropDownMenuOptions);
            }
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
            if (selectTextFormatValidationMenuItem.name === "dataType" &&
                selectTextFormatValidationMenuItem.value !== "number") {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === "dataType" &&
                            validationConfig.value === "number"){

                            return true;
                        } else if (validationConfig.name !== "dataType") {
                            return true;
                        }
                    }

                    return false;
                }));
            }

            if (selectTextFormatValidationMenuItem.name === "dataType" ){
                var existingValidation = selectedQuestionUIObject.validations.find(function (validation, index, array) {
                    if(validation.name === selectTextFormatValidationMenuItem.name && validation.value === selectTextFormatValidationMenuItem.value) {
                        return true;
                    }

                    return false;
                });

                if(!Object.isDefined(existingValidation)) {
                    selectedQuestionUIObject.validations.push(selectTextFormatValidationMenuItem);
                }

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === "dataType" &&
                            selectTextFormatValidationMenuItem.name === "dataType" &&
                            validationConfig.value !== selectTextFormatValidationMenuItem.value){

                            return true;
                        }
                    }

                    return false;
                }));

                selectedQuestionUIObject.textFormatDropDownMenu = selectTextFormatValidationMenuItem;
            }
        } else {
            $scope.textFormatDropDownMenu = null;
            $scope.selectedPageQuestionItem.getItem().textFormatDropDownMenu = null;
            removeValidations(validationsArrayConfiguration);
        }
    };

    $scope.removeExactLengthValidation = function () {
        if(Object.isDefined($scope.exactLengthField)) {
            if (!$scope.exactLengthField.selected) {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === $scope.exactLengthField.name){
                            return true;
                        }
                    }

                    return false;
                }));
            }
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
                    if (Object.isDefined($scope.exactLengthField.value)) {
                        existingValidation.value = $scope.exactLengthField.value;
                    } else {
                        removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                            if(Object.isDefined(validationConfig)) {
                                if(validationConfig.name === $scope.exactLengthField.name){
                                    return true;
                                }
                            }

                            return false;
                        }));
                    }

                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.exactLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.removeMinLengthValidation = function () {
        if(Object.isDefined($scope.minLengthField)) {
            if (!$scope.minLengthField.selected) {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === $scope.minLengthField.name){
                            return true;
                        }
                    }

                    return false;
                }));
            }
        }
    };

    $scope.setMinLengthValidation = function() {
        var validation;
        if (Object.isDefined($scope.minLengthField)) {
            if ($scope.minLengthField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.minLengthField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    if (Object.isDefined($scope.minLengthField.value)) {
                        existingValidation.value = $scope.minLengthField.value;
                    } else {
                        removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                            if(Object.isDefined(validationConfig)) {
                                if(validationConfig.name === $scope.minLengthField.name){
                                    return true;
                                }
                            }

                            return false;
                        }));
                    }
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.minLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.removeMaxLengthValidation = function () {
        if(Object.isDefined($scope.maxLengthField)) {
            if (!$scope.maxLengthField.selected) {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === $scope.maxLengthField.name){
                            return true;
                        }
                    }

                    return false;
                }));
            }
        }
    };

    $scope.setMaxLengthValidation = function (){
        var validation;
        if(Object.isDefined($scope.maxLengthField)) {
            if ($scope.maxLengthField.selected) {
                var existingValidation = $scope.selectedPageQuestionItem.getItem().validations.find(function (validation, index, array) {
                    if(validation.name === $scope.maxLengthField.name) {
                        return true;
                    }

                    return false;
                });

                if(Object.isDefined(existingValidation)) {
                    if(Object.isDefined($scope.maxLengthField.value)) {
                        existingValidation.value = $scope.maxLengthField.value;
                    } else {
                        removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                            if(Object.isDefined(validationConfig)) {
                                if(validationConfig.name === $scope.maxLengthField.name){
                                    return true;
                                }
                            }

                            return false;
                        }));
                    }
                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.maxLengthField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.removeMinValueValidation = function () {
        if(Object.isDefined($scope.minValueField)) {
            if (!$scope.minValueField.selected) {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === $scope.minValueField.name){
                            return true;
                        }
                    }

                    return false;
                }));
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
                    if(Object.isDefined($scope.minValueField.value)){
                        existingValidation.value = $scope.minValueField.value;
                    } else {
                        removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                            if(Object.isDefined(validationConfig)) {
                                if(validationConfig.name === $scope.minValueField.name){
                                    return true;
                                }
                            }

                            return false;
                        }));
                    }

                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.minValueField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };

    $scope.removeMaxValueValidation = function () {
        if(Object.isDefined($scope.maxValueField)) {
            if (!$scope.maxValueField.selected) {

                removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                    if(Object.isDefined(validationConfig)) {
                        if(validationConfig.name === $scope.maxValueField.name){
                            return true;
                        }
                    }

                    return false;
                }));
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
                    if(Object.isDefined($scope.maxValueField.value)) {
                        existingValidation.value = $scope.maxValueField.value;
                    } else {
                        removeValidations(validationsArrayConfiguration.filter(function(validationConfig) {
                            if(Object.isDefined(validationConfig)) {
                                if(validationConfig.name === $scope.maxValueField.name){
                                    return true;
                                }
                            }

                            return false;
                        }));
                    }

                } else {
                    validation = new EScreeningDashboardApp.models.Validation($scope.maxValueField);
                    $scope.selectedPageQuestionItem.getItem().validations.push(validation.toUIObject());
                }
            }
        }
    };
}]);