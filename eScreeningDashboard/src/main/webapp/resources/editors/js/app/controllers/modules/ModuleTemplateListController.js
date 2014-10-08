/**
 * Created by Robin Carnow on 9/26/2014.
 */
Editors.controller('ModuleTemplateListController', 
        ['$scope', '$state', '$stateParams', '$filter', '$timeout', 'ngTableParams', 'TemplateService', 'templateTypes', '$modal',
         function($scope, $state, $stateParams, $filter, $timeout, ngTableParams, TemplateService, templateTypes, $modal) {
    
    //set target object which is related to the templates we will be editing
    if(!Object.isDefined($stateParams) 
            || !Object.isDefined($stateParams.selectedSurveyId) 
            || !Object.isDefined($stateParams.selectedSurveyName)){
            //redirect back to module list
        console.warn("No module is selected. Redirecting to module list.")
        $state.go('modules.list');
    }        

    $scope.relatedObj = {
        id : $stateParams.selectedSurveyId,
        name : $scope.decode($stateParams.selectedSurveyName),
    };
    
    var backToModule = function() {
        $state.go('modules.detail', {selectedSurveyId: $scope.relatedObj.id});
    }
    
    if(templateTypes.length == 0){
        console.error("No template types received from server. Redirecting back to module.");
        backToModule();
    }

    $scope.templateTypeUIObj = EScreeningDashboardApp.models.TemplateType.toUIObjects(templateTypes);

    $scope.refreshTable = function () {
        console.log('\n\n refreshing template table');
        /*$scope['tableParams'] = {
         reload: function () {},
         settings: function () {
         return {};
         }*/
        //};
        $timeout(setTable, 100);
    };
    //$scope.refreshTable();


    //TODO: May need to watch for templateTypes but shouldn't this just update via data binding?
    //    $scope.$watch('templateTypes', function(newVal, oldVal){
    //    	console.log('Template type list has changed');
    //    	if ($scope.templateTypes && $scope.templateTypes.length && $scope.templateTypes.length > 0){
    //    		$timeout($scope.refreshTable, 500);
    //    	}
    //    });

    function setTable(arguments) {
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

    /* ---- Button Actions ---- */
    $scope.editTemplate = function(templateTypeUIObj){
        console.log('Opening Template Editor for type: ' + templateTypeUIObj.name);

        if($scope.relatedObj){
            $state.go('template.editor', {typeId: templateTypeUIObj.id, moduleId: $scope.relatedObj.id});
        }

        //TODO: move to the template.editor state (or maybe the modules.templateeditor state):
        //TemplateService.getTemplateByModuleAndTemplateType($scope.relatedObj.id, templateTypeUIObj.id);


    };

    $scope.deleteTemplate = function(templateTypeUIObj){
        console.log('Deleting template type: ' + templateTypeUIObj.name);

        //todo remove this when we are wired up
        alert("Deleted template type: " + templateType.name);

        // send delete rest call
        // TemplateService.delete({moduleId: $scope.relatedObj.id, templateType: templateType});

        //update list of template types
        // $scope.refreshTable();
    };

    /**
     * Should return the user to the "template-related-object" we are editing
     */
    $scope.cancel = function(){
        
        if(Object.isDefined($scope.relatedObj)){
            console.log("Canceling edit of module's template. Going back to editor for module " + $scope.relatedObj.name);
            backToModule();
        }
    };

    $scope.openBlockModal = function() {

     // Create the modal
     var modalInstance = $modal.open({
         templateUrl: 'resources/editors/views/templates/templateblockmodal.html',
         resolve: {
             relatedObj: function() {
                 return $scope.relatedObj;
             }
         },
         controller: function($scope, $timeout, $modalInstance, relatedObj) {

             $scope.relatedObj = angular.copy(relatedObj);

             $scope.block = {};

             $scope.cancel = function() {
                 $modalInstance.dismiss('cancel');
             };

             $scope.close = function() {
                 $modalInstance.close($scope.relatedObj, $scope.block);
             };
         }

     });
    };
}]);