(function() {
	'use strict';

	angular.module('Editors').factory('RuleService', ['Restangular', 'Rule', function (Restangular, Rule){

		Restangular.extendModel('rules', function(model) {
			return new Rule(model);
		});

		return Restangular.service('rules');

	}]);
})();
