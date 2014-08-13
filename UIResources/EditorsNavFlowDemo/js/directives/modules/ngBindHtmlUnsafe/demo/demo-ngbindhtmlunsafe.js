(function() {

    "use strict";

    angular.module('demo.ngBindHtmlUnsafe', [
        'common.ngBindHtmlUnsafe'
    ])

    .controller('DemoNgBindHtmlUnsafeCtrl', ['$scope', function($scope) {
        $scope.html = "<pre><strong>Come at me bro!</strong></pre>";

        $scope.codeExample = "<div ng-bind-html-unsafe='html'></div>";
    }]);

})();