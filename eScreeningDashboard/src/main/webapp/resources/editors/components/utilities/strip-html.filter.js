(function () {
	'use strict';

	angular.module('Editors').
		filter('stripHTML', function() {
			return function stripHTML(text) {
				return String(text).replace(/<[^>]+>/gm, '');
			};
		}
	);
})();
