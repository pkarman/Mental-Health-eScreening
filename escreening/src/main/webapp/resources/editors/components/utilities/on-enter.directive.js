(function () {
	'use strict';

	angular.module('Editors')
		.directive('onEnter', function() {
			return {
				link: function (scope, elements, attrs) {
					elements.bind('keydown keypress', function (event) {
						if (event.which === 13) {
							scope.$apply(function () {
								scope.$eval(attrs.onEnter);
							});
							event.preventDefault();
						}
					});
				}
			};
		}
	);

})();
