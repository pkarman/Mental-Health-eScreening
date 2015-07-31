(function() {

    "use strict";

    angular.module('demo.skype', [
        'common.skype'
    ])

    .controller('DemoSkypeCtrl', ['$scope', function($scope) {
        $scope.phoneNumber = "(425) 897 - 7897";
        $scope.codeExample = "<a href='{{ phoneNumber | skype }}'>\n" +
        "   {{ phoneNumber | skype }}\n" +
        "</a>";
    }]);

})();