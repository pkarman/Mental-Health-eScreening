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

                $scope.blockTypes = [
                    { name: 'If', value: 'if', selected: false },
                    { name: 'Else If', value: 'elseif', selected: false },
                    { name: 'Else', value: 'else', selected: false },
                    { name: 'Text', value: 'text', selected: false }
                ];

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