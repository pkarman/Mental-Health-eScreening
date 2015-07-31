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
		};

		this.condition.getTypeOf = function() {
			return 'Rule';
		};

		// Initialize condition operands and operators
		angular.extend(this.condition, new EScreeningDashboardApp.models.TemplateCondition(this.condition));
	}

	EScreeningDashboardApp.models.Rule = Rule;

})();
