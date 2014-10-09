Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', 'TemplateService', 'template', '$modal',
                                          function($scope, $state, $stateParams, TemplateService, template, $modal) {

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