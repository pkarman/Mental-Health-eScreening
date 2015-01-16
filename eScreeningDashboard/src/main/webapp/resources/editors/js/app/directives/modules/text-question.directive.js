(function() {
	'use strict';

	angular.module('Editors').directive('textQuestion', [function() {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/text-question.html',
			link: function(scope) {

				var validations = [
					{ name: 'exactLength', displayName: 'Exact Length', value: undefined, type: 'common' },
					{ name: 'minLength', displayName: 'Min Length', value: undefined, type: 'common' },
					{ name: 'maxLength', displayName: 'Max Length', value: undefined, type: 'common' },
					{ name: 'minValue', displayName: 'Min Value', value: undefined },
					{ name: 'maxValue', displayName: 'Max Value', value: undefined }
				];

				scope.validations;

				scope.textFormatOptions = [
					{id: null, code: null, name: "dataType", value: "email", description: null, dataType: null, createdDate: null},
					{id: null, code: null, name: "dataType", value: "date", description: null, dataType: null, createdDate: null},
					{id: null, code: null, name: "dataType", value: "number", description: null, dataType: null, createdDate: null}
				];

				scope.$watch('question.textFormat.value', function(val) {
					// Filter validations if text format is unformatted (i.e. undefined)
					scope.validations = _.filter(validations, function(validation) {
						return (!val) ? validation.type === 'common' : true;
					});
					if (scope.question) {
						initializeValidations();
					}
				});

				// Update the validations on the question when changed in the UI
				scope.updateValidations = function updateValidations() {
					// Reset the validations array to only include the textFormat validation
					scope.question.validations = [scope.question.textFormat];
					if (!scope.question.textFormat || scope.question.textFormat.value === 'number') {
						// Rebuild the validations array with selected validations
						angular.forEach(scope.validations, function (validation) {
							if (validation.checked) {
								scope.question.validations.push(validation);
							}
						});
					}
				};

				function initializeValidations() {
					// Initialize the numberValidations array with the question validations from the server
					angular.forEach(scope.validations, function(validation, index) {
						var match = _.find(scope.question.validations, function(questionValidation) {
							console.log('val name', validation.name);
							console.log('ques val name', questionValidation.name);
							return validation.name === questionValidation.name;
						});
						if (match) {
							_.merge(scope.validations[index], match);
							scope.validations[index].checked = true;
						}
						// Cast value to number
						scope.validations[index].value = +scope.validations[index].value;
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
