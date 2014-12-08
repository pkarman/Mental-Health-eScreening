(function(angular) {
	"use strict";

	/*
		This directive is used to bind and trigger changes on hidden input fields.
		By default, Angular ignores hidden fields.
	 */
	Editors.directive('updateHidden',function() {
		return {
			scope: {
				ngModel: '=',
				ngChange: '&'
			},
			link: function (scope, element) {

				// Watch the model for changes
				scope.$watch('ngModel', function (val) {
					element.val(val);

					// Call ngChange()
					scope.ngChange();
				}, true);

			}
		}
	});

})(angular);
