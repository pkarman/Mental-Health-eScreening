(function() {

    "use strict";

    angular.module('demo.print', [
        'common.print'
    ])

        .controller('DemoPrintCtrl', ['$scope', function($scope) {
            $scope.codeExample = "<button\n" +
            "   class='btn btn-default'\n" +
            "   print='#print-demo'\n" +
            "   print-title='Print Demo'>\n" +
            "   Print\n" +
            "</button>\n\n" +
            "<div id='print-demo'>Hey you there!</div>";
        }]);

})();