(function() {
	'use strict';

	angular.module('Editors').factory('RuleService', ['Restangular', 'Rule', function (Restangular, Rule){

		Restangular.extendModel('rules', function(model) {
			return angular.extend(model, new Rule(model));
		});

		return Restangular.service('rules');

	}]);
})();
