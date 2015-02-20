Editors.controller('testModalCtrl', ['$scope', function($scope) {
}]);
Editors.controller('ModulesTemplatesEditController', ['$rootScope', '$scope', '$state', '$stateParams', '$modal', 'AssessmentVariableService', 'TemplateBlockService', 'template', 'relatedObj', 'MessageFactory',
                                                function($rootScope, $scope, $state, $stateParams, $modal, AssessmentVariableService, TemplateBlockService, template, relatedObj, MessageFactory) {

    console.log("In templateEditorController");

    $scope.template = template;
    $scope.hasChanged = false;
    $scope.assessmentVariables = [];
    $scope.variableHash = {};
    $scope.debug = false;
    $scope.logId=0;
    $scope.alerts = MessageFactory.get();
    $scope.relatedObj = relatedObj;

    var queryObj;
    if ($scope.relatedObj.type === "module") {
    	queryObj = {surveyId: $scope.relatedObj.id};
    }
    else if($scope.relatedObj.type === "battery") {
    	queryObj = {batteryId: $scope.relatedObj.id};
    }
    
    $scope.assessmentVariables = AssessmentVariableService.query(queryObj)
        .then(function(assessmentVariables) {
            assessmentVariables.forEach(function(variable){
                    $scope.variableHash[variable.id] = variable;
                });
                
                return assessmentVariables;
            });

    $scope.save = function () {
        console.log("Save clicked");

        $scope.template.saveFor($scope.relatedObj).then(function (response) {
            $scope.done(true).then(function(){
                MessageFactory.set('success', 'All template changes have been saved.');
            })
        });
    };

    $scope.done = function (wasSaved) {
        console.log("Redirecting to list of templates");
        var stateParams = angular.copy($stateParams);
        if (wasSaved) {
            stateParams.saved = wasSaved;
        }
        return $state.go('^.templates', stateParams);
    };

    //helper function for debugging drag and drop rules (only when we want tons of logs)
    function log(msg){
        if($scope.debug)
            console.log(msg);
    }
    
    //ng-tree options
    $scope.treeOptions = {
            dropped : function(event){
                //work around because of a bug that occurs in firefox where a text block is able to be dropped between two elseifs 
                var dragBlock = event.source.nodeScope.$modelValue;
                if(dragBlock.type == "text" && event.source.index != event.dest.index){
                    //look at dropped nodesScope and make sure the dropped text block is before the first elseif
                    var destNodesScope = event.dest.nodesScope;
                    var foundElse = false;
                    for(var i = 0; i < destNodesScope.$modelValue.length; i++){
                        var destBlock = destNodesScope.$modelValue[i];
                        if(destBlock.type == "elseif" || destBlock.type == "else"){
                            foundElse = true;
                        }
                        //TODO: We're using section here but really we should use scope ID. Unfortunately I'm unable to get to ui-tree-nodes.$nodes
                        // if ui-tree-nodes.$nodes becomes available in ui-tree then we should iterate over destNodesScope.$nodes in the for loop below
                        // and use .$id instead of section.
                        else if(destBlock.section == dragBlock.section){
                            if(foundElse){
                                log("found erroneous state allowed by ui-tree bug in firefox. Reverting drop");
                                
                                event.source.nodeScope.remove();
                                
                                var sourceBlocks = event.source.nodesScope.$modelValue;
                                
                                sourceBlocks.splice(event.source.index, 0, dragBlock);
                                
                                return;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
                $scope.templateChanged();
            },
            accept: function(dragNodeScope, destNodesScope, destIndex){
                log("****** LOG ID " + $scope.logId++ + " *******");
            	log("destIndex: " + destIndex);
            	
            	var destIsRoot = !Object.isDefined(destNodesScope.$nodeScope);
            	var destParent = destIsRoot ? null : destNodesScope.$nodeScope.$modelValue;
            	
            	log("destParent type: " + (Object.isDefined(destParent) ? destParent.type : "null" ));
            	
            	var dragType = dragNodeScope.$modelValue.type;
            	var dragIndex = dragNodeScope.$modelValue.index();
            	
            	
                if(!destIsRoot){
                    log("destNodesScope.nodeScope: " + destNodesScope.$nodeScope.$modelValue.title);
                }
                
                //allow dropping at the top of all blocks for text and if types
                if(destIsRoot){
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
                var destIsAfterDragInSameParent = false;
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
                    if(dragNodeScope.$modelValue.equals(destNodesScope.$modelValue[i])){
                        if(destIndex > i){
                            log("destIndex is greater than dragged index and in same parent");
                            destIsAfterDragInSameParent = true;
                        }
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
                        var firstElse = firstElseIndex; // destIsAfterDragInSameParent ? firstElseIndex - 1 : firstElseIndex;
                        log("adjusted firstElseIndex:" + firstElse);
                        log("source/drag index is: " + dragIndex);
                        log("returning:  destIndex: " + destIndex + " <= firstElseIndex: " + firstElse);
                        //PLEASE NOTE: there seems to be a bug in ui-tree which gives the wrong index if 
                        // you quickly move from the first node under the if to right under an elseif of the same if the index is still 1 which is incorrect
                        return destIndex <= firstElse;
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
                
                //There is also a bug in ui-tree which, when using Chrome, it doesn't allow for an else to be dragged from below an else even 
                //though this function allows it. 
                
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
            backdrop: 'static',
            keyboard: false,
            resolve: {
	            template:  function() {
		            return $scope.template;
	            }
			},
			controller: ['$scope', '$modalInstance', 'eventBus', 'template', 'textAngularManager', 
			             function($scope, $modalInstance, eventBus, template, textAngularManager) {

                $scope.templateName = template.name;

                if(template.json){
                	delete(template.json);
                }
                
				$scope.validationMessage = {
					show: false
				};

				// Copy the selected or new block so that potential changes in modal don't update object in page
				$scope.block = (selectedBlock && !isAdding) ? selectedBlock : TemplateBlockService.newBlock(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig, selectedBlock);
				$scope.isAdding = angular.isUndefined(isAdding) ? false : isAdding;
				
				$scope.block.setTextContent(TemplateBlockService);

				// Dismiss modal
				$scope.cancel = function () {
					$modalInstance.dismiss('cancel');
				};
				
				eventBus.onLocationChange($scope, function(next, current){
				    $scope.cancel();
				});

				// Close modal and pass updated block to the page
                $scope.close = function (form) {

                	eventBus.modalClosing('modules.templates.edit.controller:resources/editors/views/templates/templateblockmodal.html');
                	//console.debug("Inserted block content: ", $scope.block.content);
                	
	                if (form.$valid) {

		                $scope.block.transformTextContent(TemplateBlockService, $scope.variableHash);
		                $scope.block.autoGenerateFields($scope.variableHash);

		                if (isAdding) {

			                if (!selectedBlock) template.blocks.push($scope.block);
			                //TODO: If we have domain objects for each block type then we can move this "addBlock" logic into each of them.
			                else if (selectedBlock.type == 'if') {
				                if ($scope.block.type == 'if') {
					                insertAfterText(selectedBlock);
				                }
				                else if ($scope.block.type == 'elseif' || $scope.block.type == 'else') {
					                insertAfterTextAndElseIf(selectedBlock);
				                }
				                else if ($scope.block.type == 'text') {
					                //put it at top
					                selectedBlock.children.splice(0, 0, $scope.block);
				                }
				                else {
					                log.error("Unsupported type to insert");
				                }
			                }
			                else if (selectedBlock.type == 'elseif') {
				                if ($scope.block.type == 'if') {
					                insertAfterText(selectedBlock);
				                }
				                else if ($scope.block.type == 'elseif') {
					                //insert right after this else if
					                selectedBlock.getParent().children.splice(selectedBlock.index() + 1, 0, $scope.block);
				                }
				                else if ($scope.block.type == 'else') {
					                //insert into parent IF after text and else if
					                insertAfterTextAndElseIf(selectedBlock.getParent());
				                }
				                else if ($scope.block.type == 'text') {
					                //add it to top of elseif's children
					                selectedBlock.children.splice(0, 0, $scope.block);
				                }
				                else {
					                log.error("Unsupported type to insert");
				                }
			                }
			                else if (selectedBlock.type == 'else') {
				                if ($scope.block.type == 'if') {
					                insertAfterText(selectedBlock);
				                }
				                else if ($scope.block.type == 'text') {
					                //add it to top of elseif's children
					                selectedBlock.children.splice(0, 0, $scope.block);
				                }
				                else {
					                log.error("Unsupported type to insert");
				                }
			                }
			                else if (selectedBlock.type == 'text') {
				                if ($scope.block.type == 'if' || $scope.block.type == 'text') {
					                if (selectedBlock.getParent()) {
						                //if we have a parent place the if after the text in that parent
						                selectedBlock.getParent().children.splice(selectedBlock.index() + 1, 0, $scope.block);
					                }
					                else {
						                //if we have no parent then add block as next sibling in template's blocks array
						                var textIndex = -1;
						                template.blocks.every(function (block, i) {
							                if (selectedBlock.equals(block)) {
								                textIndex = i;
							                }
							                return textIndex == -1;
						                });
						                template.blocks.splice(textIndex + 1, 0, $scope.block);
					                }
				                }
				                else if ($scope.block.type == 'elseif') {
					                //insert after all text blocks in the parent If
					                insertAfterText(selectedBlock.getParent());
				                }
				                else if ($scope.block.type == 'else') {
					                insertAfterTextAndElseIf(selectedBlock.getParent());
				                }
				                else {
					                log.error("Unsupported type to insert");
				                }
			                }
		                }

		                $scope.templateChanged();
		                $modalInstance.close();
	                } else {
		                $scope.validationMessage.show = true;
	                }
				};
				
				function insertAfterTextAndElseIf(parent){
				    //put block after parent's elseifs and text
                    var lastElseIfIndex = parent.lastElseIf().index;
                    if(lastElseIfIndex != -1){
                        parent.children.splice(lastElseIfIndex + 1, 0, $scope.block);
                    }
                    else{
                        var lastText = parent.lastText().index;
                        if(lastText != -1){
                            parent.children.splice(lastText + 1, 0, $scope.block);
                        }
                        else{
                          //put it at top
                          parent.children.splice(0,0, $scope.block);
                        }
                    }
				    
				}
				
                function insertAfterText(parent){
                    //place after text if there are any
                    var lastText = parent.lastText().index;
                    if(lastText != -1){
                        parent.children.splice(lastText + 1, 0, $scope.block);
                    }
                    else{
                        //put it at top
                       parent.children.splice(0,0, $scope.block);
                    }
				}

			}]
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
