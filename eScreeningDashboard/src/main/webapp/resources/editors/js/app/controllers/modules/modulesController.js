/**
 * Created by pouncilt on 8/4/14.
 */
/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('modulesController', ['$rootScope', '$scope', '$state', '$filter', '$timeout', 'ngTableParams', 'SurveyService', 'surveys', function($rootScope, $scope, $state, $filter, $timeout, ngTableParams, SurveyService, surveys) {
    $rootScope.surveyUIObjects = EScreeningDashboardApp.models.Survey.toUIObjects(surveys);
    
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


    //TODO: May need to watch for surveyUIObjects instead of surveys.
    $scope.$watch('surveys', function(newVal, oldVal){
    	console.log('Watch Surveys');
    	if ($scope.surveyUIObjects && $scope.surveyUIObjects.length && $scope.surveyUIObjects.length > 0){
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
            total:$scope.surveyUIObjects.length,
            getData: function ($defer, params) {
                console.log(
                    '\n\nngTable getData called now');
                // use build-in angular filter
                params.total($scope.surveyUIObjects.length);
                var filteredData = params.filter() ?
                    $filter('filter')($scope.surveyUIObjects, params.filter()) : $scope.surveyUIObjects;
                var orderedData = params.sorting() ?
                    $filter('orderBy')(filteredData, params.orderBy()) : $scope.surveyUIObjects;
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
        total: $scope.surveyUIObjects.length, // length of $scope.surveyUIObjects
        getData: function($defer, params) {
            // use build-in angular filter
            var filteredData = params.filter() ?
                $filter('filter')($scope.surveyUIObjects, params.filter()) : $scope.surveyUIObjects;
            var orderedData = params.sorting() ?
                $filter('orderBy')(filteredData, params.orderBy()) : $scope.surveyUIObjects;

            params.total(orderedData.length); // set total for recalc pagination
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });*/

    /* ---- Button Actions ---- */
    $scope.editModule = function(survey){
    	$scope.selectedSurveyUIObject = survey;
        $state.go('modules.detail', {selectedSurveyId: survey.id});
    };

    $scope.addModule = function(){
        $scope.selectedSurveyUIObject = $scope.createModule();
        $state.go('modules.detail.selectQuestionType');
    };

    $scope.goToAddEdit = function(){
    	console.log('Transition to Add/Edit Modules');
        $state.go('modules.detail.selectQuestionType');
    };

    $scope.cancel = function(){
        alert('Will take user back to Editors Entry View.');
    };
}]);