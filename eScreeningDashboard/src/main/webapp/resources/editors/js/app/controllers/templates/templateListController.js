/**
 * Created by Robin Carnow on 9/26/2014.
 */
Editors.controller('templateListController', 
    ['$scope', '$state', '$filter', '$timeout', 'ngTableParams', 'TemplateService', 'templateTypes', 
    function($scope, $state, $filter, $timeout, ngTableParams, TemplateService, templateTypes) {
        
        //set target object which is related to the templates we will be editing
        if(Object.isDefined($scope.selectedSurveyUIObject)){
            if(templateTypes.length == 0){
                //redirect back to module list
                $state.go('modules.list');
            }
            
            $scope.relatedObj = {
                    id : $scope.selectedSurveyUIObject.id,
                    name : $scope.selectedSurveyUIObject.name,
                    type : "module"
            };
        }
       //TODO: add a check here for a Battery as the related object
                
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
            
            if($scope.relatedObj && $scope.relatedObj.type == "module"){
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
            //TODO: add logic to see if we are editing a battery or a survey
            if($scope.relatedObj && $scope.relatedObj.type == "module"){
                console.log("Canceling edit of module's template. Going back to editor for module " + $scope.relatedObj.name);
                $state.go('modules.detail.question', {surveyId: $scope.relatedObj.id});
                return;
            }
            //TODO: add a check here for returning to the currently edited Battery
            
            alert('Error: there is not contex object to return to.');
        };
}]);