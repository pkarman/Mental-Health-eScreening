Editors.controller('batteryAbstractController',['$scope','$state', 'batteries', 'sections',function($scope, $state, batteries, sections){

    $scope.batteries = batteries;
    $scope.sections = sections;

    $scope.save = function(battery){
        $state.go('home');
    };

    $scope.cancel = function(){
        $state.go('home');
    };

}]);
