
Editors.controller('ModulesController', ['$scope', '$state', '$filter', 'SurveyService', 'surveys', 'ngTableParams', 'AlertService', function ($scope, $state, $filter, SurveyService, surveys, ngTableParams, AlertService) {

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

    $scope.formReset = false;

    $scope.organizePages = function () {
        var organizedPages = [];

        // Loop through the pageQuestionItems domain objects
        $scope.pageQuestionItems.forEach(function(item) {
            // Check if the item is a page using the isPage accessor
            if(item.isPage()){
                // Add the page to the organizedPages array
                organizedPages.push(item.getItem());
                // Clear out any existing questions (needed when saving/updating surveys with existing pages
                organizedPages[organizedPages.length-1].questions = [];
            } else if(organizedPages.length > 0){
                // Item is a question, add it to last page in the organizedPages array
                organizedPages[organizedPages.length-1].questions.push(item.getItem());
            }
        });

        // Return empty array if no questions exist on the first page (seems brute force)
        if(organizedPages.length === 1 &&
            Object.isArray(organizedPages[0].questions) &&
            organizedPages[0].questions.length === 0) {
            organizedPages = [];
        }

        return organizedPages;
    };

    $scope.setFormReset = function(formReset) {
        $scope.formReset = formReset;
    };

    $scope.resetForm = function(softReset, state) {
        softReset = (Object.isBoolean(softReset))? softReset: false;

        if(!softReset) {
            $scope.setFormReset(true);
        }

        $scope.selectedPageQuestionItem = null;

        if(Object.isDefined(state) && Object.isBoolean(state.doTransition)) {
            if(state.doTransition) {
                if (Object.isString(state.name)) {
                    if (Object.isDefined(state.params)) {
                        $state.go(state.name, state.params);
                    } else {
                        $state.go(state.name);
                    }
                }
            }
        }
    };

    /* ---- Button Actions ---- */
    $scope.editModule = function(survey){
        $scope.survey = survey;
        $state.go('modules.detail', {surveyId: survey.id});
    };

    $scope.deleteModule = function deleteModule(index) {
        var survey = $scope.surveys[index];
        survey.markedForDeletion = true;
        survey.save().then(function(response) {
            AlertService.add('success', 'The select module has been marked for deletion.', true);
        }, function(result) {
            AlertService.add('danger', 'There was an error deleting the selected module.', true);
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
