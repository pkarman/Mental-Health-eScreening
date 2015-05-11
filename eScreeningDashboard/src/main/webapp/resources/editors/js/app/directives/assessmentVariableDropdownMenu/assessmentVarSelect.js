(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('assessmentVarSelect', ['$document', function($document) {
	        return {
	            restrict: 'EA',
	            scope: {
	                assessmentVariable: '=',
	                editorType: '@',  //e.g. "text", "table", "condition" (used for both condition blocks and rule expression), "graphicalTemplate"
					allowTransformations: '='
	            },
		        require: '^form',
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableSelect.html',
	            link: function(scope, element, attributes, formController) {
		            // These are used for form validation from block and condition contexts
		            scope.templateBlockEditorForm = formController;
		            scope.conditionForm = formController;

		            scope.avMenu = {
			            show: false
		            };

		            /* This has been commented out for now because we need to force the user to make 
		             * a decision on what variable they are selecting and what transformations to apply.
		             * When time permits we can add this back in but we will have to:
		             * 1. save the current variable that is selected before allowing for a new one to be selected
		             * 2. we will have to reinstat this variable if the user is allowed to cancel the operation by clicking away.
		             $document.bind('click', function(e) {
			            scope.$apply(function() {
				            scope.avMenu.show = false;
			            });
		            });
		            */

		            scope.openSelections = function(e) {
			            // Stop bubbling
			            if (e) e.stopPropagation();

			            scope.avMenu.show = true;
		            };
	            }
	        };

	    }]
    );

})(angular);
