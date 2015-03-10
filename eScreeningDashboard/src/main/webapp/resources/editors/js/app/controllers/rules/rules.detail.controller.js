(function() {
    'use strict';

    angular.module('Editors').controller('RulesDetailController', ['$scope', 'MessageFactory', function($scope, MessageFactory){

        $scope.rule = {};

		$scope.alerts = MessageFactory.get();

    }]);
})();

