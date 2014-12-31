(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailFreetextController', ['$scope', '$state', '$stateParams', 'SurveyService', 'MeasureService', 'textFormatOptions', function($scope, $state, $stateParams, SurveyService, MeasureService, textFormatOptions){

        $scope.textFormatOptions = textFormatOptions;
        $scope.parentResetForm = $scope.resetForm;
        $scope.isDirty = false;

        if (!$scope.question) {
            // Look up the selected question by the id passed into the parameter
            MeasureService.one($stateParams.selectedQuestionId).get().then(function(question){
                $scope.question = question;
                initValidations();
            });
        } else {
            initValidations();
        }

        $scope.numberValidations = [
            { name: 'exactLength', displayName: 'Exact Length', value: null },
            { name: 'minLength', displayName: 'Min Length', value: null },
            { name: 'maxLength', displayName: 'Max Length', value: null },
            { name: 'minValue', displayName: 'Min Value', value: null },
            { name: 'maxValue', displayName: 'Max Value', value: null }
        ];

        // Update the validations on the question when changed in the UI
        $scope.updateValidations = function updateValidations() {
            // Reset the validations array to only include the textFormat validation
            $scope.question.validations = [$scope.question.textFormat];
            if ($scope.question.textFormat.value === 'number') {
                // Rebuild the validations array with selected number validations
                angular.forEach($scope.numberValidations, function (numberValidation) {
                    if (numberValidation.checked) {
                        $scope.question.validations.push(numberValidation);
                    }
                });
            }
        };

        function initValidations() {
            // Initialize the numberValidations array with the question validations from the server
            angular.forEach($scope.numberValidations, function(numberValidation, index) {
                var match = _.find($scope.question.validations, function(questionValidation) {
                    return numberValidation.name === questionValidation.name;
                });
                if (match) {
                    _.merge($scope.numberValidations[index], match);
                    $scope.numberValidations[index].checked = true;
                }
                // Cast value to number
                $scope.numberValidations[index].value = +$scope.numberValidations[index].value;
            });

            // Populate the question's textFormat value with the selected text format option
            $scope.question.textFormat = _.find($scope.question.validations, function(questionValidation) {
                return _.find(textFormatOptions, function(validation){
                    return validation.value === questionValidation.value;
                });
            });
        }

    }]);

})();
