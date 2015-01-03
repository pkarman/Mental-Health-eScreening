(function() {
    'use strict';
    angular.module('Editors').controller('ModulesDetailInstructionsController', ['$scope', '$state', function ($scope, $state) {

        if (!$scope.question) {
            // Look up the selected question by the id passed into the parameter
            MeasureService.one($stateParams.questionId).get().then(function (question) {
                $scope.question = question;
            });
        }

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
