Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', '$modal', 'TemplateService', 'TemplateTypeService', 'template',
                                          function($scope, $state, $stateParams, $modal, TemplateService, TemplateTypeService, template) {

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
          console.log("Cancel edit");
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
          console.log("remove block: " + block.title);
          $scope.templateChanged();
      };

      $scope.editBlock = function(blockScope){
          var block = blockScope.$modelValue;
          console.log("edit block: " + block.title);
      };

      //ng-tree options
      $scope.treeOptions = {

      };

      //this could use $watch but this might be overkill for large templates since there are
      //only a couple of places that a change occurs and afer one has happened we don't care about new updates
      $scope.templateChanged = function(){
          console.log("template changed");
          $scope.hasChanged = true;
      }

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

}]);
