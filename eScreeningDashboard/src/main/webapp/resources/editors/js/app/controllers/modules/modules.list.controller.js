(function(){
    angular.module('Editors').controller('ModulesListController', ['$scope', '$state', '$filter', '$timeout', 'ngTableParams', 'surveys', function($scope, $state, $filter, $timeout, ngTableParams, surveys) {

        $scope.surveys = surveys;

        $scope.tableParams = new ngTableParams({
            page: 1, // show first page
            count: 10, // count per page
            filter: {
                name: '' // initial filter
            },
            sorting: {
                name: 'asc'
            }
        }, {
            total:$scope.surveys.length,
            getData: function ($defer, params) {
                // use build-in angular filter
                params.total($scope.surveys.length);
                var filteredData = params.filter() ?
                    $filter('filter')($scope.surveys, params.filter()) : $scope.surveys;
                var orderedData = params.sorting() ?
                    $filter('orderBy')(filteredData, params.orderBy()) : $scope.surveys;
                //params.total(orderedData.length); // set total for recalc pagination
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });

        /* ---- Button Actions ---- */
        $scope.editModule = function(survey){
            $scope.survey = survey;
            $state.go('modules.detail', {surveyId: survey.id});
        };

        $scope.addModule = function(){
            $scope.survey = {};
            $state.go('modules.detail.list');
        };

        $scope.goToAddEdit = function(){
            $state.go('modules.detail.list');
        };

        $scope.cancel = function(){
            $state.go('home');
        };
    }]);
})();
