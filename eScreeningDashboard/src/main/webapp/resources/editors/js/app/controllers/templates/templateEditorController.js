Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', 'TemplateService', 'template', 
                                          function($scope, $state, $stateParams, TemplateService, template) {

    console.log("In templateEditorController");
    
    $scope.template = template;
    //TODO: change $stateParams to be more abstract (i.e. use relObj, relObjName, relObjType) so this can be reused for battery templates
    $scope.relatedObj = {
        id : $stateParams.selectedSurveyId,
        name : decodeURIComponent($stateParams.selectedSurveyName),
        type : "module"
    };
    
    $scope.save = function(){
        console.log("Save template");
    };
    
    $scope.cancel = function(){
        console.log("Cancel edit");
        if($scope.relatedObj.type == "module"){
            $state.go('modules.templates', $stateParams);
        }
    };
    
    $scope.addBlock = function(){
        console.log("Add block");
    };
    
}]);