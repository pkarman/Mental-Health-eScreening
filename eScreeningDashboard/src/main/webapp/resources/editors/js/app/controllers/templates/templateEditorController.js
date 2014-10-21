Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', 'TemplateService', 'TemplateTypeService', 'template', 
                                          function($scope, $state, $stateParams, TemplateService, TemplateTypeService, template) {

    console.log("In templateEditorController");
    
    $scope.template = template;
    
    //TODO: change $stateParams to be more abstract (i.e. use relObj, relObjName, relObjType) so this can be reused for battery templates
    $scope.relatedObj = {
        id : $stateParams.selectedSurveyId,
        name : decodeURIComponent($stateParams.selectedSurveyName),
        type : "module"
    };
    
    $scope.save = function(){
        console.log("Save clicked");
        
        $scope.template.saveFor($scope.relatedObj).then(function(response){
            $scope.done();
        });       
    };
    
    $scope.done = function(){
        console.log("Cancel edit");
        if($scope.relatedObj.type == "module"){
            $state.go('modules.templates', $stateParams);
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
    };
    
    $scope.removeBlock = function(blockScope){
        var block = blockScope.$modelValue;
        console.log("remove block: " + block.title);
    };
    
    $scope.editBlock = function(blockScope){
        var block = blockScope.$modelValue;
        console.log("edit block: " + block.title);
    };
    
    //ng-tree options
    $scope.treeOptions = {
    
    };
    
}]);