(function(angular) {
    "use strict";

    Editors.directive('templateBlockEditor', [
		'limitToWithEllipsisFilter', 'TemplateBlockService', 'AssessmentVariableManager', 'AssessmentVariable', 'Rule',
		function(limitToWithEllipsisFilter, TemplateBlockService, AssessmentVariableManager) {

        // TODO Move to service or domain object to be shared and encapsulated elsewhere?
	    var blockTypes = [
		    { name: 'If', value: 'if' },
		    { name: 'Else If', value: 'elseif' },
		    { name: 'Else', value: 'else' },
		    { name: 'Text', value: 'text' },
			{ name: 'Table', value: 'table' }
	    ];

        function getBlockTypes(parentBlock) {
            var types = [blockTypes[0], blockTypes[3], blockTypes[4]];
            
            //dropping at root
            if(!Object.isDefined(parentBlock) || parentBlock.type == "else"){
                return types;
            }
            
            var grandParentType = parentBlock.getParent() ? parentBlock.getParent().type : null;
            var ifParent = null;
            if(parentBlock.type == "if"){
                ifParent = parentBlock;
            }
            else if(grandParentType == "if"){
                ifParent = parentBlock.getParent();
            }
            
            // if we can't find a parent block that is an If then if and text
            if(!ifParent){
                return types;
            }
            
            //if the parent is text and this isn't the last text, then we don't include else or elseif
            var lastText;
            if(parentBlock.type == 'text'){
                lastText = parentBlock.getParent().lastText().block;
                
                if(!lastText.equals(parentBlock)){
                    return types;
                }
            }
            
            //at this point we know that elseif is going to make it
            types.push(blockTypes[1]);
            
            // if there is another else then don't allow another one
            if(ifParent.getElse().index != -1){
                return types;
            }
            
            var lastElseIf = ifParent.lastElseIf().block;
            
            //if parent is text and there is an elseIf not include else
            if(parentBlock.type == 'text' && Object.isDefined(lastElseIf)){
                return types;
            }
            
            //if the parent is an elseif and this isn't the last elseif, then we don't include else
            if(parentBlock.type == 'elseif' && !lastElseIf.equals(parentBlock)){
                return types;
            }

            // add else
            types.push(blockTypes[2]);
            
            return types;
        }

        return {
            restrict: 'E',
	        require: '^form',
            scope: {
                block: '=',
                assessmentVariables: '=',
	            showValidationMessages: '=',
	            enableTypes: '=enableTypesDropdown'
            },
            templateUrl: 'resources/editors/views/templates/templateblockeditor.html',
            link: function(scope, element, attrs, formController) {

				scope.templateBlockEditorForm = formController;
				scope.showValidationMessages = false;
				scope.isRuleCondition = false;
				scope.showValidationMessages = false;
				scope.enableTypeDropdown = false;

				if (_.isFunction(scope.block.getTypeOf) && scope.block.getTypeOf() === 'Rule') {
					scope.block.type = 'if';
					scope.isRuleCondition = true;
				} else {
					scope.blockTypes = (scope.block) ? getBlockTypes(scope.block.getParent()) : blockTypes;
				}
                
                scope.$watch('block.type', function(newValue, oldValue) {
                    if(newValue !== null && newValue !== oldValue){
                        if (_.isFunction(scope.block.reset)) scope.block.reset();
                    }
                });

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push(TemplateBlockService.newBlock(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig));
                };

                scope.removeCondition = function(selectedBlockConditions, selectedCondition) {
                    var selectedConditionIndex = selectedBlockConditions.indexOf(selectedCondition);
                    if(selectedConditionIndex > -1) {
                        selectedBlockConditions.splice(selectedConditionIndex, 1);
                    }
                };
                
                scope.addAndCondition = function addAndCondition(item, form) {
                	scope.showValidationMessages = true;
	                if (form.$valid) {
						item.conditions = item.conditions || [];
						console.log(item.conditions);
						item.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig));
	                }
        		};

        		scope.addOrCondition = function addOrCondition(item, form) {
        			scope.showValidationMessages = true;
	                if (form.$valid) {
						item.conditions = item.conditions || [];
						item.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.OrConditionMinimumConfig));
	                }
        		};
            }
        };

    }]);
})(angular);
