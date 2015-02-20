/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models") };

(function () {
	'use strict';

	var AssessmentVariable = function(obj) {
		this.id = obj.id || null;
		this.typeId = obj.typeId || null;
		this.answerId = obj.answerId || null;
		this.measureId = obj.measureId || null;
		this.measureTypeId = obj.measureTypeId || null;
		this.parentMeasureId = obj.parentMeasureId || null;
		this.name = obj.name || null;
		this.displayName = obj.displayName || null;
		this.type = obj.type || null;
		this.transformations = obj.transformations || null;

		if (obj.typeId) {
			this.setType();
		}
	};

	AssessmentVariable.prototype = {

		getMeasureTypeName: function getMeasureTypeName() {
			var type;

			switch (this.measureTypeId) {
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

			return type;
		},

		setType: function setType() {

			var types = {
				1: 'Question',
				2: 'Answer',
				3: 'Custom',
				4: 'Formula'
			};

			this.type = types[this.typeId] || 'Other';
		},

		setTransformations: function setTransformations(arr) {

			var av = this;

			var transformations = {
				delimit: {
					name: 'delimit',
					params: [',', 'and', '""', true, '']
				},
				yearsFromDate: { name: 'yearsFromDate' },
				delimitedMatrixQuestions: {
					name: 'delimitedMatrixQuestions',
					params: []
				},
				numberOfEntries: {
					name: 'numberOfEntries'
				},
				delimitTableField:	{
					name: 'delimitTableField',
					params: [',', 'and', '""', true, '']
				}
			};

			// If appointment (id 6 is reserved for appointment AV), add delimit
			if (av.id === 6) {
				av.transformations = [transformations.delimit];
			}

			// Freetext with date
			if (av.measureTypeId === 1) {
				_.each(arr, function(validation) {
					if (validation.value === 'date') {
						av.transformations = [transformations.yearsFromDate];
					}
				});
			}

			// If select multi, add delimit (for other answer types pull the veteran text)
			if (av.measureTypeId === 3) {
				av.transformations = [transformations.delimit];
				// Get the answer list for multi or single select questions
				_.each(arr, function(answer) {
					if (answer.answerType === 'Other') {
						// Pull veteran text
						// TODO confirm this is correct with Robin
						av.transformations[0].defaultValue = answer.answerText;
					}
				});
			}

			// If table, add delimitTableField and numberOfEntries
			if (av.measureTypeId === 4) {
				av.transformations = [transformations.delimitTableField, transformations.numberOfEntries];
			}

			// If select One and select multi matrix, add delimitedMatrixQuestions(rowAvIdToOutputMap, columnVarIds)
			if (av.measureTypeId === 6 || av.measureTypeId === 7) {
				av.transformations = [transformations.delimitedMatrixQuestions];
			}
		}
	};

	EScreeningDashboardApp.models.AssessmentVariable = AssessmentVariable;

})();
