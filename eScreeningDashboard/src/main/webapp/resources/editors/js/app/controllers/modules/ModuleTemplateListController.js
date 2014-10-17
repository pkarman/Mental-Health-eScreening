/**
 * Created by Robin Carnow on 9/26/2014.
 */
Editors.controller('ModuleTemplateListController', 
        ['$rootScope', '$scope', '$state', '$stateParams', '$filter', '$timeout', 'ngTableParams', 'TemplateService', 'templateTypes',
         function($rootScope, $scope, $state, $stateParams, $filter, $timeout, ngTableParams, TemplateService, templateTypes) {

    $scope.templateTypes = templateTypes;

    if($scope.templateTypes.length == 0){
        console.log("No template types received from server. Redirecting back to module.");
        backToModule();
    }

    //set target object which is related to the templates we will be editing
    if(!Object.isDefined($stateParams) 
            || !Object.isDefined($stateParams.selectedSurveyId) 
            || !Object.isDefined($stateParams.selectedSurveyName)){
            //redirect back to module list
        console.log("No module is selected. Redirecting to module list.");
        $state.go('modules.list');
    }        

    var setTable = function(arguments) {
        console.log('template list setTable');
        $scope.tableParams = new ngTableParams({
            page: 1, // show first page
            count: 10, // count per page
            sorting: {
                name: 'asc'
            }
        }, {
            total:$scope.templateTypes.length,
            getData: function ($defer, params) {
                console.log(
                    '\n\nngTable getData called now');
                // use build-in angular filter
                params.total($scope.templateTypes.length);
                var filteredData = params.filter() ?
                    $filter('filter')($scope.templateTypes, params.filter()) : $scope.templateTypes;
                var orderedData = params.sorting() ?
                    $filter('orderBy')(filteredData, params.orderBy()) : $scope.templateTypes;
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });
    };
    
    var backToModule = function(){
        if(Object.isDefined($scope.relatedObj)){
            console.log("Redirecting back to editor for module " + $scope.relatedObj.name);
            $state.go('modules.detail', {selectedSurveyId: $scope.relatedObj.id});
        }
        else{
            console.log('No module selected. Redirecting back to module list');
            $state.go('modules.list');
        }
    };
    
    $scope.relatedObj = {
        id : $stateParams.selectedSurveyId,
        name : decodeURIComponent($stateParams.selectedSurveyName)
    };

    /* ---- Button Actions ---- */    
    $scope.editTemplate = function(templateType){
        console.log('Opening Template Editor to edit template of type: ' + templateType.name);
        var editorParams =
           {selectedSurveyId: $stateParams.selectedSurveyId,
            selectedSurveyName: $stateParams.selectedSurveyName,
            typeId: templateType.id,
            templateId: Object.isDefined(templateType.templateId) ? templateType.templateId : ""
           };
        
        $state.go('template.moduleeditor', editorParams);
    };

    $scope.deleteTemplate = function(templateType){
        console.log('Deleting template type: ' + templateType.name + " which has ID: " + templateType.templateId);

        // send delete rest call
        TemplateService.one(templateType.templateId).remove().then(function(template){
            console.log("Successfully deleted template type: " + templateType.name + " which has ID: " + templateType.templateId);
            
            $rootScope.addMessage(
                    $rootScope.createSuccessDeleteMessage(templateType.name + " template was successfully deleted."));
            
            delete(templateType.templateId);
        });
    };

    /**
     * Should return the user to the "template-related-object" we are editing
     */
    $scope.cancel = function(){
        console.log("Canceling edit of module's template. Going back to editor for module");
        backToModule();
    };
    
}]);