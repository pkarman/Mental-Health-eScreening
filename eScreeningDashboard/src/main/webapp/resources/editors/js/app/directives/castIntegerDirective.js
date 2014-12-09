(function(angular) {
	"use strict";

	/*
		This directive is used to cast a form input value from a string to an integer
	 */
	Editors.directive('castInteger', function(){
		return {
			require: 'ngModel',
			link: function(scope, ele, attr, ctrl){
				ctrl.$parsers.unshift(function(viewValue){
					console.log(viewValue);
					return parseInt(viewValue, 10);
					console.log(viewValue);
				});
			}
		};
	});

})(angular);
