/**
 * 
 */
Editors.controller('entryController', ['$scope', '$state', function($scope, $state){

    $scope.entryApp = '/escreeningdashboard/';

    $scope.goToModuleNew = function () {
        $state.go('modules.detail', {selectedSurveyId: -1});
    };

    $scope.goToModuleEdit = function () {
        $state.go('modules.list');
    };

    $scope.goToBatteryNew = function () {
        $state.go('batteries.detail');
    };

    $scope.goToBatteryEdit = function () {
        $state.go('batteries.batteryselection');
    };

    $scope.goToSections = function () {
        $state.go('sections');
    };

}]);
