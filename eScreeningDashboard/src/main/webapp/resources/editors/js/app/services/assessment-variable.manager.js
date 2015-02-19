(function () {
	'use strict';

	angular.module('Editors')
		.factory('AssessmentVariableManager', ['MeasureService', function(MeasureService) {

			var transformations = {
				delimit: {
					name: 'delimit',
					params: {
						prefix: ',',
						lastPrefix: 'and',
						suffix: '""',
						includeSuffixAtEnd: true,
						defaultValue: ''
					}
				},
				yearsFromDate: { name: 'yearsFromDate' },
				delimitedMatrixQuestions: {
					name: 'delimitedMatrixQuestions',
					params: {
						lastPrefix: 'and'
					}
				},
				numberOfEntries: {
					name: 'numberOfEntries'
				},
				delimitTableField:	{
					name: 'delimitTableField',
					params: {
						prefix: ',',
						lastPrefix: 'and',
						suffix: '""',
						includeSuffixAtEnd: true,
						defaultValue: ''
					}
				}
			};

			function getType(av) {
				var type;

				if (av.typeId === 3) {

					type = 'custom';

				} else {

					switch (av.measureTypeId) {
						case 1:
							type = 'freetext';
							break;
						case 2:
							type = 'single-select';
							break;
						case 3:
							type = 'multi-select';
							break;
						case 4:
							type = 'table';
							break;
						case 6:
							type = 'single-matrix';
							break;
						case 7:
							type = 'multi-matrix';
					}
				}

				return type;
			}

			function setTransformations(av) {

				console.log(av.measureTypeId, av.type);

				// If free text and date, add yearsFromDate transformation
				if (av.measureTypeId === 1) {
					// Get the validations for freetext
					MeasureService.one(av.measureId).getList('validations').then(function (validations) {
						var validationMap = {};
						angular.forEach(validations, function(validation) {
							setValidations(validationMap, validation);
						});
						if (validationMap.date) {
							av.transformations = [transformations.yearsFromDate];
						}
					});
				}

				// If custom variable and appointment, add delimit
				if (av.typeId === 3) {
					av.transformations = [transformations.delimit];

				}

				// If select multi, add delimit (for other answer types pull the veteran text)
				if (av.measureTypeId === 3) {
					av.tranformations = [transformations.delimit];
					// Get the answer list for multi or single select questions
					MeasureService.one(av.measureId).getList('answers').then(function (answers) {
						angular.forEach(answers, function(answer) {
							if (answer.answerType === 'Other') {
								// Pull veteran text
								// TODO confirm this is correct with Robin
								av.transformations[0].defaultValue = answer.answerText;
							}
						});
					});
				}

				// If table, add delimitTableField and numberOfEntries
				if (av.measureTypeId === 4) {
					av.tranformations = [transformations.delimitTableField, transformations.numberOfEntries];
				}

				// If select One and select multi matrix, add delimitedMatrixQuestions(rowAvIdToOutputMap, columnVarIds)
				if (av.measureTypeId === 6 || av.measureTypeId === 7) {
					av.transformations = [transformations.delimitedMatrixQuestions];
				}

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
				getType: getType,
				setTransformations: setTransformations,
				setValidations: setValidations
			};

		}]
	);
})();
