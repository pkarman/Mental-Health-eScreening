(function() {

    "use strict";

    angular.module('demo.redactor', [
        'common.redactor'
    ])

    .controller('DemoRedactorCtrl', ['$scope', function($scope) {
        $scope.myText = "Hello you!";
        $scope.codeExample = "<textarea\n" +
        "   redactor\n" +
        "   ng-model='myText'></textarea>";
    }]);

})();