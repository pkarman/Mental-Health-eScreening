(function(angular) {
    "use strict";

    Editors.directive('templateBlockEditor', ['$compile', 'limitToWithEllipsisFilter', function($compile, limitToWithEllipsisFilter) {

        // TODO Move to service or domain object to be shared and encapsulated elsewhere?
	    var blockTypes = [
		    { name: 'If', value: 'if' },
		    { name: 'Else If', value: 'elseif' },
		    { name: 'Else', value: 'else' },
		    { name: 'Text', value: 'text' }
	    ];

        function getBlockTypes(parentBlock) {
            var types = [blockTypes[0], blockTypes[3]];
            
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
	            showValidationMessages: '='
            },
            templateUrl: 'resources/editors/views/templates/templateblockeditor.html',
            link: function(scope, element, attrs, formController) {

                /* Temporarily disabled until further notice: 11/03/14
                 var collectionTemplate = '<template-block-editor block="member" ng-repeat="member in block.children | limitTo:2" assessment-variables="assessmentVariables"></template-block-editor>';
                  */

                /*
                 The compile function cannot handle directives that recursively use themselves
                 in their own templates or compile functions. Compiling these directives results
                 in an infinite loop and a stack overflow errors. This can be avoided by manually
                 using $compile in the postLink function to imperatively compile a directive's template
                 */
				/* Temporarily disabled until further notice: 11/03/14
                $compile(collectionTemplate)(scope, function (clonedTemplate, scope) {
                    // Append the template and pass in the cloned scope
                    element.append(clonedTemplate);
                });
                */

	            scope.templateBlockEditorForm = formController;

	            scope.showValidationMessages = false;

                scope.blockTypes = (scope.block) ? getBlockTypes(scope.block.getParent()) : blockTypes;

                // TODO Move to service to be shared elsewhere?
                scope.operators = [
	                { name: 'Equals',                    value: 'eq',    category: 'numerical' },
	                { name: 'Doesn\'t Equals',           value: 'neq',   category: 'numerical' },
	                { name: 'Is Less Than',              value: 'lt',    category: 'numerical' },
	                { name: 'Is Greater Than',           value: 'gt',    category: 'numerical' },
	                { name: 'Is Less Than or Equals',    value: 'lte',   category: 'numerical' },
	                { name: 'Is Greater Than or Equals', value: 'gte',   category: 'numerical' },

	                { name: 'Was Answered',     value: 'answered',   category: 'question' },
	                { name: 'Wasn\'t Answered', value: 'nanswered', category: 'question' },

	                { name: 'Has Result',      value: 'result',   category: 'formula' },
	                { name: 'Has No Result',   value: 'nresult', category: 'formula' },

	                { name: 'Response is',     value: 'response',  category: 'select' },
	                { name: 'Response isn\'t',  value: 'nresponse', category: 'select' }
                ];

                var globalOperators = [];

                scope.getOperators = function (blockId) {
                    if(Object.isDefined(globalOperators[blockId])){
                        return globalOperators[blockId];
                    } else {
                        globalOperators[blockId] = scope.operators;
                        return globalOperators[blockId];
                    }
                };

                scope.setOperators = function (blockId, operators) {
                    globalOperators[blockId] = operators;
                };

                var filterOperators = function(operator) {
                    var includeOperator = false;
                    if(operator.category.toLowerCase() === "numerical") {
                        if(this.type.toUpperCase() === "CUSTOM" || this.type.toUpperCase() === "FORMULA") {
                            includeOperator = true;
                        } else if (this.type.toUpperCase() === "QUESTION" && this.measureTypeId === 1) {
                            includeOperator = true;
                        }
                    } else if(operator.category.toLowerCase() === "question" && (this.type.toUpperCase() === "QUESTION")) {
                        includeOperator = true;
                    } else if(operator.category.toLowerCase() === "formula" && (this.type.toUpperCase() === "FORMULA")) {
                        includeOperator = true;
                    } else if(operator.category.toLowerCase() === "select" && (this.type.toUpperCase() === "QUESTION")) {
                        if(Object.isDefined(this.measureTypeId) && (this.measureTypeId === 2 || this.measureTypeId === 3)){
                            includeOperator = true;
                        }
                    }
                    return includeOperator;
                };

                scope.$on('closeAssessmentVariableMenuRequested', function(event, data) {
                    if(!Object.isDefined(data)) {
                        throw new BytePushers.exceptions.NullPointerException("data parameter can not be undefined or null.");
                    }
                    if(!Object.isDefined(data.guid)) {
                        throw new BytePushers.exceptions.NullPointerException("data.guid parameter can not be undefined or null.");
                    }
                    if(!Object.isDefined(data.selectedAssessmentVariable)) {
                        throw new BytePushers.exceptions.NullPointerException("data.selectedAssessmentVariable parameter can not be undefined or null.");
                    }

                    scope.setOperators(data.guid, scope.operators.filter(filterOperators, data.selectedAssessmentVariable));
                });

                scope.$on('filterOperators', function(event, data) {
                    if(!Object.isDefined(data)) {
                        throw new BytePushers.exceptions.NullPointerException("data parameter can not be undefined or null.");
                    }
                    if(!Object.isDefined(data.guid)) {
                        throw new BytePushers.exceptions.NullPointerException("data.guid parameter can not be undefined or null.");
                    }
                    if(!Object.isDefined(data.selectedAssessmentVariable)) {
                        throw new BytePushers.exceptions.NullPointerException("data.selectedAssessmentVariable parameter can not be undefined or null.");
                    }

                    $(".assessmentVariableSelection[guid=\""+data.guid+"\"]").find("#assessmentVariableMenuLabel").text(" " + limitToWithEllipsisFilter(data.selectedAssessmentVariable.name, 20));
                    scope.setOperators(data.guid, scope.operators.filter(filterOperators, data.selectedAssessmentVariable));
                });

                scope.filterOperators = function() {
                    return filterOperators;
                };

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push(new EScreeningDashboardApp.models.TemplateBlock(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig));
                };

                scope.addAndConditionBlock = function(selectedBlock, form) {
	                scope.showValidationMessages = true;
	                if (form.$valid) {
		                selectedBlock.conditions = selectedBlock.conditions || [];
		                selectedBlock.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig));
	                }
                };

                scope.addOrConditionBlock = function(selectedBlock, form) {
	                scope.showValidationMessages = true;
	                if (form.$valid) {
		                selectedBlock.conditions = selectedBlock.conditions || [];
		                selectedBlock.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.OrConditionMinimumConfig));
	                }
                };

                scope.removeConditionBlock = function(selectedBlockConditions, selectedCondition) {
                    var selectedConditionIndex = selectedBlockConditions.indexOf(selectedCondition);
                    if(selectedConditionIndex > -1) {
                        selectedBlockConditions.splice(selectedConditionIndex, 1);
                    }
                };

	            scope.showBlockConditionRight = function(operatorValue) {
		            var result = true;
		            angular.forEach(scope.operators, function(operator) {
			            if (operator.value === "" + operatorValue) {
				            if (operator.category === 'formula' || operator.category === 'question') {
					            result = false;
					            return true;
				            }
			            }
		            });
		            return result;
	            }

            }
        };

    }]);
})(angular);
