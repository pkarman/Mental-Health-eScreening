(function() {

    "use strict";

    angular.module('demo.mediaelement', [
        'common.mediaelement'
    ])

    .controller('DemoMediaelementCtrl', ['$scope', function($scope) {
        $scope.mp4Video = 'http://playground.html5rocks.com/samples/html5_misc/chrome_japan.mp4';
        $scope.codeExample = "<video\n" +
        "   height='240'\n" +
        "   width='100%'\n" +
        "   preload='auto'\n" +
        "   controls='controls'\n" +
        "   poster='/images/square.png'\n" +
        "   mediaelement>\n" +
        "   <source type='video/mp4' ng-src='{{ mp4Video }}' />\n" +
        "</video>";
    }]);

})();