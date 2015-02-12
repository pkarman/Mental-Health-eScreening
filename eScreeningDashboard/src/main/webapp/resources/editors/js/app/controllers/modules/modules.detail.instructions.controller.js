(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailInstructionsController', ['$scope', function ($scope) {

		$scope.taToolbar = [
			['h1','h2','h3'],
			['bold','italics', 'underline'],
			['ol', 'ul'],
			['justifyLeft', 'justifyCenter', 'justifyRight'],
			['indent', 'outdent'],
			['insertLink', 'insertImage', 'insertVideo'],
			['html']
		];
    }]);

})();
