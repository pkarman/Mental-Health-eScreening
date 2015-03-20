(function() {
	'use strict';

	angular.module('Editors').factory('RuleService', ['Restangular', 'Rule', 'AssessmentVariable', function (Restangular, Rule, AssessmentVariable){

		Restangular.extendModel('rules', function(model) {
			return angular.extend(model, new Rule(model, AssessmentVariable));
		});

		return Restangular.service('rules');

	}]);
})();
