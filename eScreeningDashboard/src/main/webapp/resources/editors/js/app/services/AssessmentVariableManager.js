(function () {
	'use strict';

	angular.module('Editors')
		.factory('AssessmentVariableManager', ['MeasureService', '$q', function(MeasureService, $q) {

			function setTransformations(av, editorType) {
				var deferred = $q.defer();

				av.transformations = [];
				
				if (av.measureTypeId === 1) {
					// Get the validations for freetext
					MeasureService.one(av.measureId).getList('validations').then(function (validations) {
						av.setTransformations(validations);
						deferred.resolve(av.transformations);
					});
				}

				//if this is a select multi then we only allow trans when in a text editor (else set default transformations)
				else {
					if (av.measureTypeId !== 3
						|| editorType === 'text'){
					
						av.setTransformations();
					}
					deferred.resolve(av.transformations);
				}

				return deferred.promise;
			}

			function setValidations(map, validation) {

				switch(validation.validateId) {
					case 1:
						map[validation.value] = validation.value;
						break;
					case 4:
						map.minLength = validation.value || 0;
						break;
					case 5:
						map.maxLength = validation.value || 150;
						break;
					case 6:
						map.minValue = validation.value;
						break;
					case 7:
						map.maxValue = validation.value;
						break;
					case 9:
						map.exactLength = validation.value;
						break;
				}

				return map;
			}

			return {
				setTransformations: setTransformations,
				setValidations: setValidations
			};

		}]
	);
})();
