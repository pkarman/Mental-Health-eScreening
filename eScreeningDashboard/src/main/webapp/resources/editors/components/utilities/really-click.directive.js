(function() {
	"use strict";

	/**
	 * A generic confirmation for risky actions.
	 * Usage: Add attributes: ng-really-message="Are you sure"? ng-really-click="takeAction()" function
	 */
	angular.module('Editors')
		.directive('ngReallyClick', [function() {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				element.bind('click', function() {
					var message = attrs.ngReallyMessage;
					var shouldSkip = Object.isDefined(attrs.ngReallySkipWhen)  && attrs.ngReallySkipWhen.length > 0 && scope.$apply(attrs.ngReallySkipWhen);

					if (shouldSkip || (message && confirm(message))) {
						scope.$apply(attrs.ngReallyClick);
					}
				});
			}
		};
	}]);
})();
