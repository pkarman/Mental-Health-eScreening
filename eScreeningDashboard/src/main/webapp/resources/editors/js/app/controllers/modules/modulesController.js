/**
 * Created by pouncilt on 8/4/14.
 */
/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('modulesController', ['$rootScope', '$scope', '$state', '$filter', '$timeout', 'ngTableParams', 'SurveyService', 'surveys', function($rootScope, $scope, $state, $filter, $timeout, ngTableParams, SurveyService, surveys) {
    $rootScope.surveys = EScreeningDashboardApp.models.Survey.toUIObjects(surveys);
    
    $scope.refreshTable = function () {
        console.log('\n\n refreshing table');
        /*$scope['tableParams'] = {
            reload: function () {},
            settings: function () {
                return {};
            }*/
        //};
        $timeout(setTable, 100);
    };
    //$scope.refreshTable();
    
    $scope.$watch('surveys', function(newVal, oldVal){
    	console.log('Watch Surveys');
    	if ($scope.surveys && $scope.surveys.length && $scope.surveys.length > 0){
    		$timeout($scope.refreshTable, 500);
    	}
    });

    function setTable(arguments) {
    	console.log('SetTable');
        $scope.tableParams = new ngTableParams({
            page: 1, // show first page
            count: 10, // count per page
            filter: {
                name: '' // initial filter
            },
            sorting: {
                name: 'asc'
            },
            reload: function () {},
            settings: function () {
                return {};
            }
        }, {
            total:$scope.surveys.length,
            getData: function ($defer, params) {
                console.log(
                    '\n\nngTable getData called now');
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
    };
    
    /*$scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
        filter: {
            title: ''       // initial filter
        },
        sorting: {
            title: 'asc'     // initial sorting
        }
    }, {
        total: $scope.surveys.length, // length of $scope.surveys
        getData: function($defer, params) {
            // use build-in angular filter
            var filteredData = params.filter() ?
                $filter('filter')($scope.surveys, params.filter()) : $scope.surveys;
            var orderedData = params.sorting() ?
                $filter('orderBy')(filteredData, params.orderBy()) : $scope.surveys;

            params.total(orderedData.length); // set total for recalc pagination
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });*/

    /* ---- Button Actions ---- */
    $scope.editModule = function(survey){
    	console.log('Transition to Add/Edit Modules');
        $scope.module = survey;
        console.log(JSON.stringify(survey));
        $state.go('modules.detail.question', {surveyId: survey.id});
    };

    $scope.addModule = function(){
        $scope.module = $rootScope.createModule();
        $state.go('modules.detail.question');
    };

    $scope.goToAddEdit = function(){
    	console.log('Transition to Add/Edit Modules');
        $state.go('modules.detail.question');
    };

    $scope.cancel = function(){
        alert('Will take user back to Editors Entry View.');
    };
}]);