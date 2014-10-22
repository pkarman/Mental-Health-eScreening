Editors.controller('templateEditorController', ['$scope', '$state', '$stateParams', '$modal', 'TemplateTypeService', 'template',
                                          function($scope, $state, $stateParams, $modal, TemplateTypeService, template) {

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

	var temp = {
		"templateId": 1,
		"isGraphical": false,
		"templateType": {
			"id": 1,
			"name": "CPRS Note Header",
			"description": "Template used to generate the header of a CPRS Note. Associated with a Battery.",
			"templateId": null
		},
		"templateVariables": [],
		"nodes": [
			{
				"title": null,
				"section": null,
				"type": "Include",
				"content": "<#include \"clinicalnotefunctions\"/>",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "Depression Screening:\nDepression Screen:\n------------------\nPHQ-9\nA PHQ-9 screen was performed.\n",
				"children": []
			},
			{
				"title": "TEST TITLE",
				"section": null,
				"type": "ConditionalBlock",
				"content": null,
				"children": [
					{
						"title": null,
						"section": null,
						"type": "ifBlock",
						"content": "<#if (var1599)?? && isSet(var1599.value)>",
						"children": [
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": "      The score was ",
								"children": []
							},
							{
								"title": null,
								"section": null,
								"type": "DollarVariable",
								"content": "${var1599.value}",
								"children": []
							},
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": " which is suggestive of\n",
								"children": []
							},
							{
								"title": null,
								"section": null,
								"type": "ConditionalBlock",
								"content": null,
								"children": [
									{
										"title": null,
										"section": null,
										"type": "ifBlock",
										"content": "<#if (var1599.value?number < 1)>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "         no\n",
												"children": []
											}
										]
									},
									{
										"title": null,
										"section": null,
										"type": "elseIfBlock",
										"content": "<#elseif (var1599.value?number <= 4)>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "         minimal\n",
												"children": []
											}
										]
									},
									{
										"title": null,
										"section": null,
										"type": "elseIfBlock",
										"content": "<#elseif (var1599.value?number <= 9)>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "         mild\n",
												"children": []
											}
										]
									},
									{
										"title": null,
										"section": null,
										"type": "elseIfBlock",
										"content": "<#elseif (var1599.value?number <= 14)>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "         moderate\n",
												"children": []
											}
										]
									},
									{
										"title": null,
										"section": null,
										"type": "elseIfBlock",
										"content": "<#elseif (var1599.value?number <= 19)>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "        moderatelysevere\n",
												"children": []
											}
										]
									},
									{
										"title": null,
										"section": null,
										"type": "elseIfBlock",
										"content": "<#else>",
										"children": [
											{
												"title": null,
												"section": null,
												"type": "TextBlock",
												"content": "        severe\n",
												"children": []
											}
										]
									}
								]
							},
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": "depression.\n",
								"children": []
							}
						]
					},
					{
						"title": null,
						"section": null,
						"type": "elseIfBlock",
						"content": "<#else>",
						"children": [
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": "An insufficient number of responses were given to calculate a PHQ-9 score.\n",
								"children": []
							}
						]
					}
				]
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n1. Little interest or pleasure in doing things\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var2960)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n2. Feeling down, depressed, or hopeless\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var2970)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n3. Trouble falling or staying asleep, or sleeping too much\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var2980)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n4. Feeling tired or having little energy\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var2990)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n5. Poor appetite or overeating\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var3000)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n6. Feeling bad about yourself or that you are a failure or\nhave let yourself or your family down\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var1550)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n7. Trouble concentrating on things, such as reading the\nnewspaper or watching television\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var1560)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n8. Moving or speaking so slowly that other people could have\nnoticed. Or the opposite being so fidgety or restless that you\nhave been moving around a lot more than usual\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var1570)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n9. Thoughts that you would be better off dead or of hurting\nyourself in some way\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var1580)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\n10. If you checked off any problems, how DIFFICULT have these\nproblems made it for you to do your work, take care of things\nat home or get along with other people?\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "DollarVariable",
				"content": "${getSelectOneDisplayText(var1590)}",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "TextBlock",
				"content": "\n\nWas the patient's PHQ-2 score greater than 2, or PHQ-9 score greater\nthan 9?\n\n",
				"children": []
			},
			{
				"title": null,
				"section": null,
				"type": "ConditionalBlock",
				"content": null,
				"children": [
					{
						"title": null,
						"section": null,
						"type": "ifBlock",
						"content": "<#if (var1599)?? && isSet(var1599.value) && (var1599.value?number > 9)>",
						"children": [
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": "Yes. \n\nNURSING/NON-PROVIDER: Follow-up:\nThe following action was taken: Patient's provider,\n(Assigned Clincian), was notified for immediate intervention.\n",
								"children": []
							}
						]
					},
					{
						"title": null,
						"section": null,
						"type": "elseIfBlock",
						"content": "<#else>",
						"children": [
							{
								"title": null,
								"section": null,
								"type": "TextBlock",
								"content": "No.\n",
								"children": []
							}
						]
					}
				]
			}
		]
	};

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
					return selectedBlock || temp.nodes[2];
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
