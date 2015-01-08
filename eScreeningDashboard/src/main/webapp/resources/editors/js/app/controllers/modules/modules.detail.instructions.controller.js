(function() {
    'use strict';
    angular.module('Editors').controller('ModulesDetailInstructionsController', ['$scope', function ($scope) {

        $scope.taOptions = [
            ['bold', 'italics', 'underline'],
            ['ul', 'ol'],
            ['justifyLeft', 'justifyCenter', 'justifyRight'],
            ['indent', 'outdent'], ['insertLink', 'insertVideo'],
            ['redo', 'undo'],
            ['html']
        ];
        
    }]);

})();
