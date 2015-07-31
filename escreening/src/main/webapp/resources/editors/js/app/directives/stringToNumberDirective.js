(function(angular) {
	"use strict";

	/**
	 * This directive is used to translate from the string representation of a number to a number and vic versa. 
	 * This is used when the server's representation is a string but we want to enforce number form validations.
	 * see: https://docs.angularjs.org/error/ngModel/numfmt
	 * and very importantly this: http://stackoverflow.com/questions/25658089/angular-how-to-change-the-data-type-using-formatters-and-parsers
	 * @author Robin Carnow
	 */
	angular.module('Editors').directive('stringToNumber', function(){
		return {
			require: 'ngModel',
			priority: 1,
			link: function(scope, element, attrs, ngModel) {
				ngModel.$parsers.push(function(value) {
					return '' + value;
				});
				ngModel.$formatters.push(function(value) {
					return parseFloat(value, 10);
				});
			}
		};
	});

})(angular);
