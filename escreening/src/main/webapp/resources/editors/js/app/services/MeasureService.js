(function() {
	'use strict';

	angular.module('Editors').factory('MeasureService', ['Restangular', 'Question', function (Restangular, Question){

		var endpoint = 'questions';

		Restangular.extendModel(endpoint, function(model) {
			return Question.extend(model);
		});

		return Restangular.service(endpoint);

	}]);
})();
