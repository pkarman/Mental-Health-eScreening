Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', '$modal', 'TemplateService', 'TemplateTypeService', 'template', 
                                          function($scope, $state, $stateParams, $modal, TemplateService, TemplateTypeService, template) {

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
    
    $scope.updateBlock = function(selectedBlock, editing){
        // Create the modal
        var modalInstance = $modal.open({
            templateUrl: 'resources/editors/views/templates/templateblockmodal.html',
            resolve: {
                templateName: function() {
                    return $scope.relatedObj.name
                },
                block: function() {
                    // If we are creating a new block, create a new block instance from Restangular
                    return selectedBlock || {};
                },
                editing: editing
            },
            controller: function($scope, $modalInstance, templateName, block, editing) {

                $scope.templateName = templateName;

                // Copy the selected or new block so that potential changes in modal don't update object in page
                $scope.block = angular.copy(block);

                // Dismiss modal
                $scope.cancel = function() {
                    $modalInstance.dismiss('cancel');
                };

                // Close modal and pass updated block to the page
                $scope.close = function() {
                    $modalInstance.close($scope.block, editing);
                };
            }

        });

        // Update the dashboard on the scope
        modalInstance.result.then(
            function(block, editing) {
                // Update blocks array with updated or new block
                console.log('block', block, editing);
            },
            function() {
                // Error
            });
    };

    $scope.removeBlock = function(blockScope){
        var block = blockScope.$modelValue;
        console.log("remove block: " + block.title);
    };

    //ng-tree options
    $scope.treeOptions = {

    };

}]);
