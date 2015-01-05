(function() {
	'use strict';

	angular.module('Editors').directive('textQuestion', [function() {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/text-question.html',
			link: function(scope, elem, attrs) {

				scope.textFormatOptions = [
					{id: null, code: null, name: "dataType", value: "email", description: null, dataType: null, createdDate: null},
					{id: null, code: null, name: "dataType", value: "date", description: null, dataType: null, createdDate: null},
					{id: null, code: null, name: "dataType", value: "number", description: null, dataType: null, createdDate: null}
				];

				scope.numberValidations = [
					{ name: 'exactLength', displayName: 'Exact Length', value: undefined },
					{ name: 'minLength', displayName: 'Min Length', value: undefined },
					{ name: 'maxLength', displayName: 'Max Length', value: undefined },
					{ name: 'minValue', displayName: 'Min Value', value: undefined },
					{ name: 'maxValue', displayName: 'Max Value', value: undefined }
				];

				scope.$watch('question', function(question) {
					if (question) {
						initializeValidations();
					}
				});

				// Update the validations on the question when changed in the UI
				scope.updateValidations = function updateValidations() {
					// Reset the validations array to only include the textFormat validation
					scope.question.validations = [scope.question.textFormat];
					if (scope.question.textFormat.value === 'number') {
						// Rebuild the validations array with selected number validations
						angular.forEach(scope.numberValidations, function (numberValidation) {
							if (numberValidation.checked) {
								scope.question.validations.push(numberValidation);
							}
						});
					}
				};

				function initializeValidations() {
					// Initialize the numberValidations array with the question validations from the server
					angular.forEach(scope.numberValidations, function(numberValidation, index) {
						var match = _.find(scope.question.validations, function(questionValidation) {
							return numberValidation.name === questionValidation.name;
						});
						if (match) {
							_.merge(scope.numberValidations[index], match);
							scope.numberValidations[index].checked = true;
						}
						// Cast value to number
						scope.numberValidations[index].value = +scope.numberValidations[index].value;
					});

					// Populate the question's textFormat value with the selected text format option
					scope.question.textFormat = _.find(scope.question.validations, function(questionValidation) {
						return _.find(scope.textFormatOptions, function(validation){
							return validation.value === questionValidation.value;
						});
					});
				}

			}
		};

	}]);

})();
