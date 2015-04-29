/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models") };

(function () {
	'use strict';
	
	var types = {
		1: 'Question',
		2: 'Answer',
		3: 'Custom',
		4: 'Formula'
	};
	
	function AssessmentVariable(obj) {
		this.id,
		this.typeId,
		this.answerId,
		this.measureId,
		this.measureTypeId,
		this.parentMeasureId,
		this.name,
		this.displayName,
		this.type,
		this.transformations = [];
		
		if(obj){
			this.id = obj.id;
			this.typeId = obj.typeId;
			this.answerId = obj.answerId;
			this.measureId = obj.measureId;
			this.measureTypeId = obj.measureTypeId;
			this.parentMeasureId = obj.parentMeasureId;
			this.name = obj.name;
			this.displayName = obj.displayName;
			this.type = obj.type;
			this.transformations = obj.transformations || [];
			
			this.setType();
		}
	}

	AssessmentVariable.prototype.getName = function getName() {
		return this.name ?  this.name : this.displayName ? this.displayName: 'var' + this.id;
	};

	AssessmentVariable.prototype.getMeasureTypeName = function getMeasureTypeName() {
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
			case 5:
				type = 'read-only';
				break;
			case 6:
				type = 'single-matrix';
				break;
			case 7:
				type = 'multi-matrix';
		}

		return type;
	};

	AssessmentVariable.prototype.setType = function setType() {
		if (this.typeId){
			this.type = types[this.typeId] || 'Other';
		}
	};

	AssessmentVariable.prototype.setTransformations = function setTransformations(arr) {

		var av = this;

		// NOTE: displayName property must be removed before persisting
		var transformations = {
			delimit: {
				name: 'delimit',
				displayName: 'Delimit',
				params: [',', 'and', '', true, '']
			},
			yearsFromDate: {
				name: 'yearsFromDate',
				displayName: 'Years from Date'
			},
			delimitedMatrixQuestions: {
				name: 'delimitedMatrixQuestions',
				displayName: 'Delimited Matrix Questions',
				params: []
			},
			numberOfEntries: {
				name: 'numberOfEntries',
				displayName: 'Number of Entries'
			},
			delimitTableField:	{
				name: 'delimitTableField',
				displayName: 'Delimited Table Field',
				params: ['0', ',', 'and', '', true, '']
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
			else if (av.measureTypeId === 3) {
			av.transformations = [transformations.delimit];
		}

		// If table, add delimitTableField and numberOfEntries
			else if (av.measureTypeId === 4) {
			av.transformations = [transformations.delimitTableField, transformations.numberOfEntries];
		}

		// If select One and select multi matrix, add delimitedMatrixQuestions(rowAvIdToOutputMap, columnVarIds)
			else if (av.measureTypeId === 6 || av.measureTypeId === 7) {
			av.transformations = [transformations.delimitedMatrixQuestions];
		}
	};

	EScreeningDashboardApp.models.AssessmentVariable = AssessmentVariable;

})();
