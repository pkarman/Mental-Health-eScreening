/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace('gov.va.escreening.models') };

(function () {
	'use strict';

	function Rule(obj, AssessmentVariable) {
		this.id = obj.id || null;
		this.name = obj.name || '';
		this.condition = obj.condition || {
			type: 'if',
			summary: '',
			name: '',
			section: '',
			children: [],
			operator: '',
			left: {
				type:'var',
				content: new AssessmentVariable({
					id: null,
					name: '',
					displayName: '',
					typeId: '',
					measureId: '',
					measureTypeId: '',
					measureAnswerId: '',
					transformations:[]
				})
			},
			'right': {
				'type': 'text',
				'content': ''
			},
			'conditions': []
		};

		this.condition.getTypeOf = function() {
			return 'Rule';
		};

		// Initialize existing condition content (AV)
		if (obj.condition && obj.condition.left && obj.condition.left.content) {
			this.condition.left.content = new AssessmentVariable(obj.condition.left.content);
		}
	}

	Rule.prototype.getName = function getName() {
		return this.name;
	};

	EScreeningDashboardApp.models.Rule = Rule;

})();
