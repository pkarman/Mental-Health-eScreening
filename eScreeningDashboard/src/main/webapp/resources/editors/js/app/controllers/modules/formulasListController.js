/**
 * Created by Khalid Rizvi @ 01/10/2015
 */
Editors.controller('ModuleFormulasListController', ['$log', '$scope', '$stateParams', 'ManageFormulasService', function ($log, $scope, $stateParams, ManageFormulasService) {
    $scope.module = {
        name: decodeURIComponent($stateParams.selectedSurveyName),
        id: $stateParams.selectedSurveyId
    };

    var data = [];
    $scope.pagination = {currentPage: 1, itemsPerPage: 10, maxSize: 10};

    ManageFormulasService.getList($scope.module.id)
        .then(function (formulas) {
            data = formulas;
            $log.debug($scope.module.id + ' has [' + data.length + '] formulas: ' + JSON.stringify(data));
            $scope.pagination.totalItems = data.length;
            $scope.formulas = data.slice(($scope.pagination.currentPage - 1) * $scope.pagination.itemsPerPage, $scope.pagination.itemsPerPage);
        });

    $scope.$watch('pagination.currentPage', function (newValue, oldValue) {
        if (oldValue != newValue) {
            $scope.formulas = data.slice(($scope.pagination.currentPage - 1) * $scope.pagination.itemsPerPage, (($scope.pagination.currentPage - 1) * $scope.pagination.itemsPerPage) + $scope.pagination.itemsPerPage);
        }
    });

    $scope.setPage = function (pageNo) {
        $scope.pagination.currentPage = pageNo;
    };

    $scope.pageChanged = function () {
        $log.log('Page changed to: ' + $scope.pagination.currentPage);
    };

    $scope.delete = function (formula) {
        $log.debug(JSON.stringify(formula) + ' is being deleted');
    }
    $scope.edit = function (formula) {
        $log.debug(JSON.stringify(formula) + ' is being edited');

    }


}]);