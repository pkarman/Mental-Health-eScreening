(function() {
	'use strict';

	angular.module('Editors').factory('RuleService', ['Restangular', 'Rule', 'AssessmentVariable', function (Restangular, Rule, AssessmentVariable){

		Restangular.extendModel('rules', function(model) {
			if(angular.isObject(model)){
				return angular.extend(model, new Rule(model, AssessmentVariable));
			}
			return model;
		});

		return Restangular.service('rules');

	}]);
})();
