(function() {
	'use strict';

	angular.module('Editors').factory('EventService', ['Restangular', 'Event', function (Restangular, Event){

		Restangular.extendModel('events', function(model) {
			return new Event(model);
		});

		return Restangular.service('events');

	}]);
})();
