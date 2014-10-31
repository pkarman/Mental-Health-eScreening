Editors.controller('testModalCtrl', ['$scope', function($scope) {
}]);
Editors.controller('templateEditorController', ['$rootScope', '$scope', '$state', '$stateParams', '$modal', 'AssessmentVariableService', 'template', function($rootScope, $scope, $state, $stateParams, $modal, AssessmentVariableService, template) {

    console.log("In templateEditorController");

    $scope.template = template;
    $scope.hasChanged = false;
    $scope.assessmentVariables = [];
    $scope.variableHash = {};
    //remove as soon as possible
    $scope.variableNamedHash = {};
    $scope.debug = false;

    //TODO: change $stateParams to be more abstract (i.e. use relObj, relObjName, relObjType) so this can be reused for battery templates
    $scope.relatedObj = {
        id: $stateParams.selectedSurveyId,
        name: decodeURIComponent($stateParams.selectedSurveyName),
        type: "module"
    };    

    if ($scope.relatedObj.type === "module") {
        
        $scope.assessmentVariables = AssessmentVariableService.query({surveyId: $scope.relatedObj.id})
            .then(function(assessmentVariables) {
                assessmentVariables.forEach(function(variable){
                        $scope.variableHash[variable.id] = variable;
                        $scope.variableNamedHash[variable.getName()] = variable;
                    });
                    
                    return assessmentVariables;
                });
    }

    $scope.save = function () {
        console.log("Save clicked");

        $scope.template.saveFor($scope.relatedObj).then(function (response) {
            $scope.done(true).then(function(){
                $rootScope.addMessage(
                  $rootScope.createSuccessSaveMessage("All template changes have been saved."));
            })
        });
    };

    $scope.done = function (wasSaved) {
        console.log("Redirecting to module templates");
        if ($scope.relatedObj.type == "module") {
            var stateParams = angular.copy($stateParams);
            if (wasSaved) {
                stateParams.saved = wasSaved;
            }
            return $state.go('modules.templates', stateParams);
        }
    };

    //helper function for debugging drag and drop rules (only when we want tons of logs)
    function log(msg){
        if($scope.debug)
            console.log(msg);
    }
    
    //ng-tree options
    $scope.treeOptions = {
            dropped : function(event){
                $scope.templateChanged();
            },
            accept: function(dragNodeScope, destNodesScope, destIndex){
                log("*************");
            	log("destIndex: " + destIndex);
            	
            	var destIsRoot = !Object.isDefined(destNodesScope.$nodeScope);
            	var destParent = destIsRoot ? null : destNodesScope.$nodeScope.$modelValue;
            	
            	log("destParent type: " + (Object.isDefined(destParent) ? destParent.type : "null" ));
            	
            	var dragType = dragNodeScope.$modelValue.type;
            	
            	
                if(!destIsRoot){
                    log("destNodesScope.nodeScope: " + destNodesScope.$nodeScope.$modelValue.title);
                }
                
                //allow dropping at the top of all blocks for text and if types
                if(destIsRoot ){
                    log("In root; if dragging text or if then drop, otherwise no drop");
                    return dragType == "text" || dragType == "if";
                }
               
                //text cannot have children 
                if(!destIsRoot && destParent.type == "text"){
                    log("No root and parent is text.  No drop");
                    return false;
                }
                
                //holds the first else or elseif index found
                var firstElseIndex = Number.MAX_VALUE;
                var firstElseIf = -1;
                var lastElseIf = -1;
                var elseIndex = -1;
                var lastTextBlock = -1;
                for(var i = 0; i < destNodesScope.$modelValue.length; i++){
                    
                    var type = destNodesScope.$modelValue[i].type;
                    if(type == "elseif"){
                        lastElseIf = i;
                        if(firstElseIf == -1){
                            firstElseIf = i;
                        }
                    }
                    else if(type == "else" && elseIndex == -1){
                        elseIndex = i;
                    }
                    else if(type == "text"){
                        lastTextBlock = i;
                    }
                }
                
                //set firstElseIndex
                if(firstElseIf > -1){
                    firstElseIndex = firstElseIf;
                }
                if(elseIndex > -1){
                    firstElseIndex = Math.min(firstElseIndex, elseIndex);
                }
                log("First else: " + elseIndex);
                log("First elseif: " + firstElseIf);
                log("Last elseif: " + lastElseIf);
                log("First else or elseif: " + firstElseIndex);
                log("Last text block: "+ lastTextBlock)
                
                //When text is being dropped it can only be dropped at: 
                if(dragType == "text"){
                    //1. as a child of an else
                    //2. as a child of an elseif
                    if(destParent.type == "else" || destParent.type == "elseif"){
                        log("text is allowed to be dropped under else and elseif")
                        return true;
                    }
                    //3. between an If and the first else or else if
                    if(destParent.type == "if"){
                        log("destIndex: " + destIndex + " <= firstElseIndex: " + firstElseIndex);
                        //PLEASE NOTE: there seems to be a bug in ui-tree which gives the wrong index if 
                        // you quickly move from the first node under the if to right under an elseif of the same if the index is still 1 which is incorrect
                        return destIndex <= firstElseIndex;
                    }
                }
                
                //else and else if block cannot be dropped where the parent is not an if
                if((dragType == "elseif" || dragType == "else") && destParent.type != "if"){
                    log("else or elseif and parent is not if. No drop.");
                    return false;
                }
                
                //elseif cannot be dropped below an else
                if(dragType == "elseif" && elseIndex != -1 && destIndex >= elseIndex){
                    log("elseif cannot be dropped below an else");
                    return false;
                }
                
                //else cannot be dropped at an index less than the index of the last elseif
                if(dragType == "else" && lastElseIf != -1 &&  destIndex <= lastElseIf){
                    log("else cannot be dropped above an elseif");
                    return false;
                }
                
                //else and else if blocks cannot be dropped above an If's text entries (if text node has parent of if)
                // or if dest is text and is sibling 
                if((dragType == "elseif" || dragType == "else") && destIndex <= lastTextBlock){
                    log("cannot drop else or elseif over an if's text blocks");
                    return false;
                }
                
                //else cannot be dropped where there exists another else
                if(dragType == "else" && elseIndex != -1 && !destNodesScope.isParent(dragNodeScope)){
                    log("cannot drop else from other if when the destination If has one already");
                    return false;
                }
                
                //PLEASE READ: dragging an else into another If which has an elseif as its last block is not 
                //being allowed by ui-tree because it either:
                // 1. if we drag a little higher, then this function is passed that we are trying to drop above the elseif (not allowed)
                // 2. if we drag a little lower, then this function is passes a destNodesScope of the root block list (not allowed)
                // So even though this operation should be allowed it is not at this time. The strange thing is that if the last 
                // element of the If block is a text block, it will allow you to drop the else as a sibling
                
                
                log("Allowed to drop here");
                return true;
            }
    };

    //this could use $watch but this might be overkill for large templates since there are
    //only a couple of places that a change occurs and after one has happened we don't care about new updates 
    $scope.templateChanged = function () {
        console.log("template changed");
        $scope.template.updateSections();
        $scope.hasChanged = true;
    };

	$scope.deleteBlock = function (blockScope) {
	    var block = blockScope.$modelValue;
	    console.log("removing block: " + block.name);
	    blockScope.remove();
	    $scope.templateChanged();
	};

	$scope.updateBlock = function(selectedBlock, isAdding){

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
				$scope.block = (selectedBlock && !isAdding) ? selectedBlock : new EScreeningDashboardApp.models.TemplateBlock(null, selectedBlock);
				
				$scope.block.sentTextContent();

				// Dismiss modal
				$scope.cancel = function () {
					$modalInstance.dismiss('cancel');
				};

				// Close modal and pass updated block to the page
				$scope.close = function () {

					$scope.block.transformTextContent($scope.variableNamedHash);
					$scope.block.autoGenerateFields($scope.variableNamedHash);

					if (isAdding) {
						if (!angular.isArray(selectedBlock.children)) selectedBlock.children = [];
						selectedBlock.children.push($scope.block);
					}

					if (!selectedBlock) template.blocks.push($scope.block);

					$scope.templateChanged();
					$modalInstance.close();
				};

			}
		});
	};

	//if we have lost state redirect
    if(!Object.isDefined(template) 
            || !Object.isDefined(template.type)){
        console.log("There is no currently selected template type.");
        
        if(!Object.isDefined($rootScope.messageHandler)){
            console.log("rootScope has been reset. Redirecting to Editors page.")
            $state.go("home");
        }
        else{
            $scope.done();
        }
    }
}]);
