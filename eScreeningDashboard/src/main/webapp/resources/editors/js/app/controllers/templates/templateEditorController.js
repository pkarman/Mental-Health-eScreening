Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', 'TemplateService', 'template', '$modal', function($scope, $state, $stateParams, TemplateService, template, $modal) {

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

                // TODO Move to service to be shared elsewhere?
                $scope.blockTypes = [
                    { name: 'If', value: 'If', selected: false },
                    { name: 'Else If', value: 'Else If', selected: false },
                    { name: 'Else', value: 'Else', selected: false },
                    { name: 'Text', value: 'Text', selected: false }
                ];

                // TODO Move to service to be shared elsewhere?
                $scope.operators = [
                    { name: 'Equals', value: 'eq', category: 'all' },
                    { name: 'Doesn\'t Equals', value: 'neq', category: 'all' },
                    { name: 'Is Less Than', value: 'lt', category: 'all' },
                    { name: 'Is Greater Than', value: 'gt', category: 'all' },
                    { name: 'Is Less Than or Equals', value: 'lte', category: 'all' },
                    { name: 'Is Greater Than or Equals', value: 'gte', category: 'all' },
                    { name: 'Was Answered', value: 'answered', category: 'question' },
                    { name: 'Wasn\'t Answered', value: 'nanswered', category: 'question' },
                    { name: 'Has Result', value: 'result', category: 'formula' },
                    { name: 'Has No Result', value: 'nresult', category: 'formula' },
                    { name: 'Response is', value: 'response', category: 'select' },
                    { name: 'Response isn\t', value: 'nresponse', category: 'select' }
                ];

                $scope.addBlock = function(selectedBlock) {
                    if (selectedBlock.children) {
                        selectedBlock.children.push({});
                    } else {
                        selectedBlock.children = [];
                        selectedBlock.children.push({});
                    }
                };

                // Dismiss modal
                $scope.cancel = function() {
                    $modalInstance.dismiss('cancel');
                };

                // Close modal and pass updated block to the page
                $scope.close = function() {
                    $modalInstance.close($scope.block);
                };
            }

        });

        // Update the dashboard on the scope
        modalInstance.result.then(
            function(block) {
                // Update blocks array with updated or new block
                console.log('block', block);
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
