/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace('gov.va.escreening.models') };

(function () {
	'use strict';

	function Rule(obj, Condition) {
		this.id = obj.id || null;
		this.name = obj.name || null;
		this.condition = obj.condition || {
			'type': 'if',
			'summary': '',
			'name': '',
			'section': '',
			'children': [],
			'operator': '',
			'left':{
				'type':'var',
				'content': { 'id': null,
					'name': '',
					'displayName': '',
					'typeId': null,
					'measureId': null,
					'measureTypeId': null,
					'measureAnswerId': null,
					'transformations':[]
				}
			},
			'right':{
				'type': '',
				'content': ''
			},
			'conditions': []
		};

		this.condition.getTypeOf = function() {
			return 'Rule';
		};
	}

	Rule.prototype.getName = function getName() {
		return this.name;
	};

	EScreeningDashboardApp.models.Rule = Rule;

})();
