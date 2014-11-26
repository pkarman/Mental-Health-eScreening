(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarDropdownMenuDir', ['$document', function($document) {
	        return {
	            restrict: 'EA',
	            scope: {
	                assessmentVariable: '='
	            },
		        require: '^form',
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDropdownMenu.html',
	            link: function(scope, element, attributes, formController) {
		            // These are used for form validation from block and condition contexts
		            scope.templateBlockEditorForm = formController;
		            scope.conditionForm = formController;

		            scope.avMenu = {
			            show: false
		            };

		            $document.bind('click', function(e) {
			            scope.$apply(function() {
				            scope.avMenu.show = false;
			            });
		            });

		            scope.openSelections = function(e) {
			            // Stop bubbling
			            e.stopPropagation();

			            scope.avMenu.show = true;
		            };
	            }
	        };

	    }]
    );

})(angular);
