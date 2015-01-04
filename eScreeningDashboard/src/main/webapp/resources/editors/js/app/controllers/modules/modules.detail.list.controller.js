(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailListController', ['$scope', '$state', 'questionTypes', function($scope, $state, questionTypes){

        $scope.questionTypes = questionTypes;

        $scope.editQuestion = function() {
            var stateName = $scope.getStateName($scope.question.type);

            $state.go(stateName);
        };

    }]);
})();
