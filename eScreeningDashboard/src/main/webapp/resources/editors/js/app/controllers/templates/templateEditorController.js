Editors.controller('testModalCtrl', ['$scope', function($scope) {
}]);
Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', '$modal', 'AssessmentVariableService', 'template', function($scope, $state, $stateParams, $modal, AssessmentVariableService, template) {

    console.log("In templateEditorController");

    $scope.template = template;
    $scope.hasChanged = false;
    $scope.assessmentVariables = [];

    //TODO: change $stateParams to be more abstract (i.e. use relObj, relObjName, relObjType) so this can be reused for battery templates
    $scope.relatedObj = {
        id: $stateParams.selectedSurveyId,
        name: decodeURIComponent($stateParams.selectedSurveyName),
        type: "module"
    };

    if ($scope.relatedObj.type === "module") {
        $scope.assessmentVariables = AssessmentVariableService.query({surveyId: $scope.relatedObj.id})/*.then(function(assessmentVariables) {
            $scope.assessmentVariables = assessmentVariables;
        });*/
    }

    $scope.save = function () {
        console.log("Save clicked");

        $scope.template.saveFor($scope.relatedObj).then(function (response) {
            $scope.done(true);
        });
    };

    $scope.done = function (wasSaved) {
        console.log("Cancel edit");
        if ($scope.relatedObj.type == "module") {
            var stateParams = angular.copy($stateParams);
            if (wasSaved) {
                stateParams.saved = wasSaved;
            }
            $state.go('modules.templates', stateParams);
        }
    };

    /**
     * @param parent is optional. If undefined then the block is added to the bottom of the template.
     */
    $scope.addBlock = function (parentScope) {
        if (Object.isDefined(parentScope)) {
            var parent = parentScope.$modelValue;
            console.log("Add block under parent block: " + parent.title);
        }
        else {
            console.log("Add block to bottom of template");
        }
        $scope.templateChanged();
    };

    $scope.removeBlock = function (blockScope) {
        var block = blockScope.$modelValue;
        console.log("remove block: " + block.title);
        $scope.templateChanged();
    };

    $scope.editBlock = function (blockScope) {
        var block = blockScope.$modelValue;
        console.log("edit block: " + block.title);
    };

    //ng-tree options
    $scope.treeOptions = {

    };

    //this could use $watch but this might be overkill for large templates since there are
    //only a couple of places that a change occurs and afer one has happened we don't care about new updates
    $scope.templateChanged = function () {
        console.log("template changed");
        $scope.hasChanged = true;
    };

	$scope.deleteBlock = function (index) {
		$scope.template.blocks.splice(index, 1);
	};

	$scope.updateBlock = function(selectedBlock){

		// Defaulting to hasChanged for now
		$scope.templateChanged();

		// Create the modal
		var modalInstance = $modal.open({
			templateUrl: 'resources/editors/views/templates/templateblockmodal.html',
            scope: $scope,
            resolve: {
	            template:  function() {
		            return $scope.template;
	            }
			},
			controller: function($scope, $modalInstance, template) {

				$scope.templateName = template.name;

				// Copy the selected or new block so that potential changes in modal don't update object in page
				$scope.block = (selectedBlock) ? angular.copy(selectedBlock) : {};

				// Dismiss modal
				$scope.cancel = function() {
					$modalInstance.dismiss('cancel');
				};

				// Close modal and pass updated block to the page
				$scope.close = function() {

					if (selectedBlock) {
						selectedBlock = $scope.block;
					} else {
						template.blocks.push($scope.block)
					}
					$modalInstance.close();
				};
			}

		});
	};

}]);
