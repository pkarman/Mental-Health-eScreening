(function() {
	'use strict';

	angular.module('Editors').directive('instructionQuestion', [function() {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/instruction-question.html',
			link: function(scope) {

				scope.taOptions = [
					['bold', 'italics', 'underline'],
					['ul', 'ol'],
					['justifyLeft', 'justifyCenter', 'justifyRight'],
					['indent', 'outdent'], ['insertLink', 'insertVideo'],
					['redo', 'undo'],
					['html']
				];
			}
		};

	}]);

})();
