(function() {
	'use strict';

	angular.module('Editors').factory('AlertFactory', function() {

		var alerts = [];

		function add(type, msg, clearExisting) {
			if (clearExisting) {
				alerts.length = 0;
			}
			alerts.push({type: type, msg: msg, close: close});
		}

		function close(index) {
			alerts.splice(index, 1);
		}

		function clear() {
			alerts.length = 0;
		}

		function get() {
			return alerts;
		}

		return {
			add: add,
			close: close,
			clear: clear,
			get: get
		};

	});
})();
