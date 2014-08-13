/**
 * Created by Bryan Henderson on 5/13/2014.
 */
FreemarkApp.controller('freemarkController', ['$rootScope', '$scope', 'freemarkHTTP', function ($rootScope, $scope, freemarkHTTP) {
    $scope.ftlFileString = "templates/CN_IntroductionSection.ftl";
    $scope.ftlOutput = "";
    $scope.loadFTLFile = function(){

        //var xml = new XMLParser( $scope.ftlFileString );
        //var data = xml.parse();
        freemarkHTTP.getFTL($scope.ftlFileString).then(function(data){
            $scope.ftlOutput = JSON.stringify(data);
        })
    }
}]);
