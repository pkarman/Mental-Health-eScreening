Editors.controller('testModalCtrl', ['$scope', function($scope) {
    /*$scope.assessmentVariable = {
        id: null,
        answerId: null,
        measureId: null,
        measureTypeId: null,
        typeId: null,
        name: null,
        displayName: null,
        getName: function () {
            return (Object.isDefined(this.name))? this.name : (Object.isDefined(this.displayName)? this.displayName: 'var' + this.id);
        },
        setType: function () {
            switch (this.typeId) {
                case 1:
                    this.type = "Measure";
                    break;
                case 2:
                    this.type = "Measure Answer"
                    break;
                case 3:
                    this.type = "Custom";
                    break;
                case 4:
                    this.type = "Formula";
                    break;
                default:
                    this.type = "Other";
            }
        },
        toString: function () {
            return "AssessmentVariable [id: " + this.id +
                ", answerId: " + this.answerId +
                ", measureId: " + this.measureId +
                ", measureTypeId: " + this.measureTypeId +
                ", typeId: " + this.typeId +
                ", name: " + this.name +
                ", displayName: " + this.displayName + "]";
        }
    };*/

    // Passing in true to third param of $watch for deep collection checking
    $scope.$watch('assessmentVariable', function(newValue, oldValue) {
        if(newValue === oldValue) {
            console.log("Inside testModalCtrl: assessmentVariable: newValue === oldValue:\n" + oldValue.toString());
        } else {
            console.log('Inside testModalCtrl: assessmentVariable newValue: ', newValue.toString());
            console.log('Inside testModalCtrl: assessmentVariable oldValue: ', oldValue.toString());
        }
    }, true);
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

	var tempBlock = {
		section: "1",
		name: "if_example",
		type: 'if',
		summary: "if some_name > 9",
	      left: {
	          type: "var",
	          content: {
	              "id" : 123,
	              "typeId" : 2,
	              "name": "demo_email",
	              "displayName":  "This is a longer body of text",
	              "answerId": "##",
	              "measureId": "##",
	              "measureTypeId" : "3",
	              "transformations":  [
	                  { name: "func1", params: [''] } ,
	                  { name: "func2", params: ['', ''] }
	              ]
	          }
	      },
	      operator: 'gt',
	          right: {
	          type: "text",
	              content: "8"
	      },
	      conditions: [
	          {
	              connector: 'and',
	              left: {
	                  type: "var",
	                  content: {
	                      "id" : 123,
	                      "typeId" : 2,
	                      "name": "demo_email",
	                      "displayName":  "This is a longer body of text",
	                      "answerId": "##",
	                      "measureId": "##",
	                      "measureTypeId" : "3",
	                      "transformations":  [
	                          { name: "func1", params: [''] } ,
	                          { name: "func2", params: ['', ''] }
	                      ]
	                  }
	              },
	              operator: 'lt',
	              right: {
	                  type: "text",
	                  content: "7"
	              },
	              conditions: [
	                  {
	                      connector: 'and',
	                      left: {
	                          type: "var",
	                          content: {
	                              "id" : 123,
	                              "typeId" : 2,
	                              "name": "demo_email",
	                              "displayName":  "This is a longer body of text",
	                              "answerId": "##",
	                              "measureId": "##",
	                              "measureTypeId" : "3",
	                              "transformations":  [
	                                  { name: "func1", params: [''] } ,
	                                  { name: "func2", params: ['', ''] }
	                              ]
	                          }
	                      },
	                      operator: 'gt',
	                      right: {
	                          type: "text",
	                          content: "6"
	                      }
	                  }
	              ]
	          },

	          {
	              connector: 'or',
	              left: {
	                  type: "var",
	                  content: {
	                      "id" : 123,
	                      "typeId" : 2,
	                      "name": "demo_email",
	                      "displayName":  "This is a longer body of text",
	                      "answerId": "##",
	                      "measureId": "##",
	                      "measureTypeId" : "3",
	                      "transformations":  [
	                          { name: "func1", params: [''] } ,
	                          { name: "func2", params: ['', ''] }
	                      ]
	                  }
	              },
	              operator: 'gt',
	              right: {
	                  type: "text",
	                  content: "5"
	              },
	              conditions: []
	          }
	      ]
	  };

	$scope.updateBlock = function(selectedBlock, editing){
		// Create the modal
		var modalInstance = $modal.open({
			templateUrl: 'resources/editors/views/templates/templateblockmodal.html',
            scope: $scope,
            resolve: {
				templateName: function() {
					return $scope.relatedObj.name
				},
				block: function() {
					// If we are creating a new block, create a new block instance from Restangular
					return selectedBlock || tempBlock;
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

}]);
