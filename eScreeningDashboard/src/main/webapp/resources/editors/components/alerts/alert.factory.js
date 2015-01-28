(function() {
	'use strict';

	angular.module('Editors').factory('AlertFactory', function($rootScope) {

		// Create an array of alerts available globally
		// This is a seldom case where using $rootScope is appropriate
		$rootScope.alerts = [];

		function add(type, msg) {
			$rootScope.alerts.push({type: type, msg: msg, close: close});
		}

		function close(index) {
			$rootScope.alerts.splice(index, 1);
		}

		function clear() {
			$rootScope.alerts.length = 0;
		}

		return {
			add: add,
			close: close,
			clear: clear
		};

	});
})();
