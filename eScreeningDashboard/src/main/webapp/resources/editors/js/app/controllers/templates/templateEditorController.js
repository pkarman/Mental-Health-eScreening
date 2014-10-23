Editors.controller('templateEditorController', ['$rootScope', '$scope', '$state', '$stateParams', 'TemplateService', 'TemplateTypeService', 'template', 
                                          function($rootScope, $scope, $state, $stateParams, TemplateService, TemplateTypeService, template) {

    console.log("In templateEditorController");
    
    $scope.template = template;
    $scope.hasChanged = false;
    
    //TODO: change $stateParams to be more abstract (i.e. use relObj, relObjName, relObjType) so this can be reused for battery templates
    $scope.relatedObj = {
        id : $stateParams.selectedSurveyId,
        name : decodeURIComponent($stateParams.selectedSurveyName),
        type : "module"
    };    
    
    $scope.save = function(){
        console.log("Save clicked");
        
        $scope.template.saveFor($scope.relatedObj).then(function(response){
            $scope.done(true);
        });       
    };
    
    $scope.done = function(wasSaved){
        console.log("Redirecting to module templates");
        if($scope.relatedObj.type == "module"){
            var stateParams = angular.copy($stateParams);
            if(wasSaved){
                stateParams.saved = wasSaved;
            }
            $state.go('modules.templates', stateParams);
        }
    };
    
    /**
     * @param parent is optional. If undefined then the block is added to the bottom of the template.
     */
    $scope.addBlock = function(parentScope){
        if(Object.isDefined(parentScope)){
            var parent = parentScope.$modelValue;
            console.log("Add block under parent block: " + parent.title);
        }
        else{
            console.log("Add block to bottom of template");
        }
        $scope.templateChanged();
    };
    
    $scope.removeBlock = function(blockScope){
        var block = blockScope.$modelValue;
        console.log("removing block: " + block.title);
        blockScope.remove();
        $scope.templateChanged();
        
    };
    
    $scope.editBlock = function(blockScope){
        var block = blockScope.$modelValue;
        console.log("edit block: " + block.title);
        $scope.templateChanged();
    };
    
    //ng-tree options
    $scope.treeOptions = {
            dropped : function(event){
                $scope.templateChanged();
            },
    };
    
    //this could use $watch but this might be overkill for large templates since there are 
    //only a couple of places that a change occurs and after one has happened we don't care about new updates 
    $scope.templateChanged = function(){
        console.log("template changed");
        $scope.template.updateSections();
        $scope.hasChanged = true;
    } 
    
    //if we have lost state redirect
    if(!Object.isDefined(template) 
            || !Object.isDefined(template.type)){
        console.log("There is no currently selected template type.");
        
        if(!Object.isDefined($rootScope.messageHandler)){
            console.log("rootScope has been reset. Redirecting to Editors page.")
            $state.go("home");
        }
        else{
            $scope.done();
        }
    }
    
}]);