(function() {
	'use strict';

	angular.module('Editors').factory('RuleService', ['Restangular', 'Rule', 'TemplateBlock', function (Restangular, Rule, TemplateBlock){

		Restangular.extendModel('rules', function(model) {
			return angular.extend(model, new Rule(model, TemplateBlock));
		});

		return Restangular.service('rules');

	}]);
})();
