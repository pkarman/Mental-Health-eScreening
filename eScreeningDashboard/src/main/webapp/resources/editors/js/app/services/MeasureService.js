(function() {
	'use strict';

	angular.module('Editors').factory('MeasureService', ['Restangular', 'Question', function (Restangular, Question){

		var endpoint = 'questions';

		Restangular.extendModel(endpoint, function(model) {
			return Question.create(model);
		});

		return Restangular.service(endpoint);

	}]);
})();
