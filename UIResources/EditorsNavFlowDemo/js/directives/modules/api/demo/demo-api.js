(function() {

    "use strict";

    angular.module('demo.api', [
        'common.api'
    ])

    .controller('DemoApiCtrl', ['$scope', 'Api', function($scope, Api) {
        Api.setBase('http://api.randomuser.me/');
        $scope.results = 1;

        $scope.loadRandomUser = function() {
            Api.get('', {
                results: $scope.results
            }).success(function(data) {
                $scope.randomUsers = data;
            });
        };

        $scope.codeExample = "Api.setBase('/api/v1/');\n\n" +

        "$scope.loadUser = function(userId, filters) {\n" +
        "   Api.get('user/' + userId, {\n" +
        "       filters: filters\n" +
        "   }).success(function(data) {\n" +
        "       $scope.data = data;\n" +
        "   });\n" +
        "};";
    }]);

})();