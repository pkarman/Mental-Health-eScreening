
Editors.controller('ModulesController', ['$scope', '$state', '$filter', 'SurveyService', 'surveys', 'ngTableParams', 'MessageFactory', function ($scope, $state, $filter, SurveyService, surveys, ngTableParams, MessageFactory) {

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

    $scope.deleteModule = function deleteModule(index) {
        var survey = $scope.surveys[index];
        survey.markedForDeletion = true;
        survey.save().then(function(response) {
            MessageFactory.success('The select module has been marked for deletion.');
        }, function(result) {
            MessageFactory.error('There was an error deleting the selected module.');
        });
    };

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.list');
    };

    $scope.cancel = function(){
        $state.go('home');
    };
    
    $scope.editTemplates = function(surveyId, surveyName){
        $state.go('modules.templates', 
                {selectedSurveyId: surveyId,
                 selectedSurveyName: encodeURIComponent(surveyName)});
    };
}]);
