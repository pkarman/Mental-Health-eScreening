(function() {
	'use strict';

	angular.module('Editors').directive('textQuestion', ['MeasureService', function(MeasureService) {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/text-question.html',
			link: function(scope) {

				var validations = [
					{ name: 'exactLength', displayName: 'Exact Length', value: 0, type: 'common' },
					{ name: 'minLength', displayName: 'Min Length', value: 0, type: 'common' },
					{ name: 'maxLength', displayName: 'Max Length', value: 0, type: 'common' },
					{ name: 'minValue', displayName: 'Min Value', value: 0 },
					{ name: 'maxValue', displayName: 'Max Value', value: 0 }
				];

				// Set all the validations on the scope (to be filtered when appropriate)
				scope.validations = validations;

				scope.validations.show = false;

				scope.textFormatOptions = [
					{id: undefined, name: "dataType", value: "unformatted"},
					{id: undefined,  name: "dataType", value: "email" },
					{id: undefined,  name: "dataType", value: "date" },
					{id: undefined, name: "dataType", value: "number" }
				];

				scope.$watch('question', function(question) {
					// Initialize after second digest when question is present
					if (question) {
						initializeValidations();
					}
				});

				// Update the validations on the question when changed in the UI
				scope.updateValidations = function updateValidations() {
					// Reset the question validations
					scope.question.validations = [];

					// Initialize the array with the textFormat
					if(scope.textFormat && scope.textFormat.value !== 'unformatted') {
						scope.question.validations.push(scope.textFormat);
					}

					// Filter validations if text format is unformatted
					scope.validations = _.filter(validations, function(validation) {
						return (scope.textFormat && scope.textFormat.value === 'unformatted') ? validation.type === 'common' : true;
					});

					// Maintain validations for number and unfromatted questions
					if (scope.textFormat.value === 'number' || scope.textFormat.value === 'unformatted') {
						// Show the validations
						scope.validations.show = true;
						_.each(scope.validations, function(validation) {
							if (validation.checked) {
								scope.question.validations.push(validation);
							}
						})
					} else {
						// Hide the validations
						scope.validations.show = false;
					}
				};

				function initializeValidations() {
					// Initialize the validations array with the question validations from the server
					_.each(scope.question.validations, function(questionValidation) {
						_.each(scope.validations, function(scopeValidation) {
							if (questionValidation.name === scopeValidation.name) {
								// Merge the values from the server with the default validations
								_.merge(scopeValidation, questionValidation);
								// Mark validation as checked
								scopeValidation.checked = true;
								// Cast value to number
								scopeValidation.value = +questionValidation.value;
							}
						});
					});

					// Populate the question's textFormat value with the selected text format option
					scope.textFormat = _.find(scope.question.validations, function(questionValidation) {
						return _.find(scope.textFormatOptions, function(textFormatOption){
							return textFormatOption.value === questionValidation.value;
						});
					});

					// Set the unformatted option from the textFormatOptions array if the textFormat is undefined
					if (!scope.textFormat) {
						scope.textFormat = scope.textFormatOptions[0];
					}

					scope.updateValidations();

				}

			}
		};

	}]);

})();
