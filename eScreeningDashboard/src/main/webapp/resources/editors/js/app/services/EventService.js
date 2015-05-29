(function() {
	'use strict';

	angular.module('Editors').factory('EventService', ['Restangular', 'Event', function (Restangular, Event){

		Restangular.extendModel('events', function(model) {
			if(angular.isObject(model)){
				return angular.extend(model, new Event(model));
			}
			return model;
		});

		return Restangular.service('events');

	}]);
})();
