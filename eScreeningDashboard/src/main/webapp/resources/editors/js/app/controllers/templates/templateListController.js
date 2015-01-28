/**
 * Created by Robin Carnow on 12/17/2014.
 */
Editors.controller('templateListController', 
        ['$rootScope', '$scope', '$state', '$stateParams', '$filter', '$timeout', 'ngTableParams', 'TemplateService', 'TemplateTypeService', 'templateTypes', 'relatedObj',
         function($rootScope, $scope, $state, $stateParams, $filter, $timeout, ngTableParams, TemplateService, TemplateTypeService, templateTypes, relatedObj) {

    if(!Object.isDefined($rootScope.messageHandler)){
        console.log("rootScope has been reset. Redirecting to Editors page.")
        $state.go("home");
    }
            
    //register types list
    TemplateTypeService.registerTypes($scope, templateTypes);  
    
    $scope.relatedObj = relatedObj;
        
    var goBack = function(){
        if(Object.isDefined($scope.relatedObj)){
        	var params;
        	if($scope.relatedObj.type == "module"){
        		params = {'selectedSurveyId': $scope.relatedObj.id};        		
        	}
        	else if($scope.relatedObj.type == "battery"){
        		params = {'batteryId': $scope.relatedObj.id};
        	}
	        $state.go('^.detail', params);
        }
        else{
            $state.go('^.list');
        }
    };

    /* ---- Button Actions ---- */    
    $scope.editTemplate = function(templateType){
        console.log('Opening Template Editor to edit template of type: ' + templateType.name);
        
        TemplateTypeService.setSelectedType(templateType);
        
        var editorParams = angular.extend({}, $stateParams, {
        		typeId: templateType.id,
            	templateId: Object.isDefined(templateType.templateId) ? templateType.templateId : ""
        	});
        
        $state.go('^.templateeditor', editorParams);
    };

    $scope.deleteTemplate = function(templateType){
        console.log('Deleting template type: ' + templateType.name + " which has ID: " + templateType.templateId);

        // send delete rest call
        TemplateService.remove(templateType.templateId).then(function(template){
            console.log("Successfully deleted template type: " + templateType.name + " which has ID: " + templateType.templateId);

            $rootScope.addMessage($rootScope.createSuccessDeleteMessage(templateType.name + " template was successfully deleted."));
            
            delete(templateType.templateId);
        });
    };

    /**
     * Should return the user to the "template-related-object" we are editing
     */
    $scope.cancel = function(){
        console.log("Canceling edit of template type list.");
        goBack();
    };
    
    /*  Check state to make sure we can proceed */
    
    //redirect if we have lost state
    if(!Object.isDefined($scope.templateTypes) || $scope.templateTypes.length == 0){
        console.log("No template types received from server.");
        goBack();
    }
    
    //set target object which is related to the templates we will be editing
    if(angular.isUndefined($stateParams) 
            || angular.isUndefined($stateParams.relatedObjId) 
            || angular.isUndefined($stateParams.relatedObjName)){
            //redirect back to module list
        console.log("No related object (battery or module) is selected.");
        goBack();
    }  
    
}]);