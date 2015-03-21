(function(angular) {
    "use strict";

    Editors.directive('templateBlockEditor', ['$compile', 'limitToWithEllipsisFilter', 'TemplateBlockService', 'MeasureService', 'AssessmentVariableManager',
                                              function($compile, limitToWithEllipsisFilter, TemplateBlockService, MeasureService, AssessmentVariableManager) {

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

                var collectionTemplate = '<template-block-editor block="member" ng-repeat="member in block.children | limitTo:2" assessment-variables="assessmentVariables" show-validation-messages="false" enable-types-dropdown="false"></template-block-editor>';

				scope.templateBlockEditorForm = formController;

				scope.showValidationMessages = false;

				scope.isRuleCondition = false;

				if (_.isFunction(scope.block.getTypeOf) &&  scope.block.getTypeOf() === 'Rule') {
					scope.block.type = 'if';
					scope.isRuleCondition = true;
				} else {
					scope.blockTypes = (scope.block) ? getBlockTypes(scope.block.getParent()) : blockTypes;
				}

                /*
                 The compile function cannot handle directives that recursively use themselves
                 in their own templates or compile functions. Compiling these directives results
                 in an infinite loop and a stack overflow errors. This can be avoided by manually
                 using $compile in the postLink function to imperatively compile a directive's template
                 */
				if (scope.isRuleCondition) {
					$compile(collectionTemplate)(scope, function (clonedTemplate, scope) {
						// Append the template and pass in the cloned scope
						element.append(clonedTemplate);
					});
				}

                // TODO Move to service to be shared elsewhere?
                scope.operators = [
	                { name: 'Equals',                    value: 'eq',    categories: ['numerical'] },
	                { name: 'Doesn\'t Equals',           value: 'neq',   categories: ['numerical'] },
	                { name: 'Is Less Than',              value: 'lt',    categories: ['numerical'] },
	                { name: 'Is Greater Than',           value: 'gt',    categories: ['numerical'] },
	                { name: 'Is Less Than or Equals',    value: 'lte',   categories: ['numerical'] },
	                { name: 'Is Greater Than or Equals', value: 'gte',   categories: ['numerical'] },

	                { name: 'Was Answered',     value: 'answered',   categories: ['question', 'table'] },
	                { name: 'Wasn\'t Answered', value: 'nanswered', categories: ['question', 'table'] },

					{ name: 'Was Answer None',     value: 'none',   categories: ['table'] },
					{ name: 'Wasn\'t Answer None', value: 'nnone', categories: ['table'] },

	                { name: 'Has Result',      value: 'result',   categories: ['formula', 'matrix', 'custom'] },
	                { name: 'Has No Result',   value: 'nresult', categories: ['formula', 'matrix', 'custom'] },

	                { name: 'Response is',     value: 'response',  categories: ['select'] },
	                { name: 'Response isn\'t',  value: 'nresponse', categories: ['select'] }
                ];

                var filterOperators = function(operator) {
                    var includeOperator = false;

	                if (operator && this.type) {
		                if(_.contains(operator.categories, 'numerical')) {
							if (this.measureTypeId === 4 && this.transformations && this.transformations[0].name === 'numberOfEntries') {
								includeOperator = true;
							}
			                if(this.type.toUpperCase() === 'CUSTOM' || this.type.toUpperCase() === 'FORMULA') {
				                includeOperator = true;
			                } else if (this.type.toUpperCase() === "QUESTION" && this.measureTypeId === 1) {
				                includeOperator = true;
			                }
		                } else if((this.type.toUpperCase() === 'QUESTION') && (this.getMeasureTypeName() !== 'single-matrix' && this.getMeasureTypeName() !== 'multi-matrix') && _.contains(operator.categories, 'question')) {
			                includeOperator = true;
		                } else if((this.type.toUpperCase() === 'FORMULA') && _.contains(operator.categories, 'formula')) {
			                includeOperator = true;
		                } else if((this.type.toUpperCase() === 'QUESTION') && _.contains(operator.categories, 'select')) {
			                if(Object.isDefined(this.measureTypeId) && (this.measureTypeId === 2 || this.measureTypeId === 3)){
				                includeOperator = true;
			                }
		                }
						else if((this.measureTypeId === 4) && _.contains(operator.categories, 'table')) {
							includeOperator = true;
						}
						else if((this.getMeasureTypeName() === 'single-matrix' || this.getMeasureTypeName() === 'multi-matrix') && _.contains(operator.categories, 'matrix')) {
							includeOperator = true;
						} else if (this.type.toUpperCase() === 'CUSTOM' && _.contains(operator.categories, 'custom')) {
							includeOperator = true;
						}
	                }
                    return includeOperator;
                };
                
                scope.$watch('block.type', function(newValue, oldValue) {
                    if(newValue !== null && newValue !== oldValue){
                        if (_.isFunction(scope.block.reset)) scope.block.reset();
                    }
                });

	            scope.filterOperators = function() {
		            return filterOperators;
	            };

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push(TemplateBlockService.newBlock(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig));
                };

                scope.addAndConditionBlock = function(selectedBlock, form) {
	                scope.showValidationMessages = true;
	                if (form.$valid) {
						if (scope.isRuleCondition) {
							// TODO Add conditions somewhere other than selectedBlock.conditions
						}
						selectedBlock.conditions = selectedBlock.conditions || [];
						selectedBlock.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig));

	                }
                };

                scope.addOrConditionBlock = function(selectedBlock, form) {
	                scope.showValidationMessages = true;
	                if (form.$valid) {
						if (scope.isRuleCondition) {
							// TODO Add conditions somewhere other than selectedBlock.conditions
						}
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
		            var result = (operatorValue);
		            angular.forEach(scope.operators, function(operator) {
			            if (operator.value === "" + operatorValue) {
				            if (_.contains(operator.categories, 'formula') || _.contains(operator.categories, 'question') || _.contains(operator.categories, 'table')) {
					            result = false;
					            return;
				            }
			            }
		            });
		            return result;
	            };

	            scope.updateLogicalOptions = function(item) {

		            var av = (item.left && item.left.content) ? item.left.content : {};

		            // (Re-)initialize the answers and validations
		            item.measureAnswers = [];
		            item.measureValidations = {};

		            // Filter the operators and add the results to the item
		            item.operators = scope.operators.filter(filterOperators, av);

		            scope.dt = new Date();

		            if (av.type && av.type.toLowerCase() === "formula") {
			            // Assessment Variable is a formula, which must be a number
			            item.measureValidations.number = 'number';
			            item.measureValidations.minValue = '';
			            item.measureValidations.maxValue = '';

		            } else if (av.measureId && av.measureTypeId) {
		                // Assessment variable is a question
			            if (av.measureTypeId == 1 && (av.transformations[0] && av.transformations[0].name !== 'yearsFromDate')) {

				            // Get the validations for freetext
				            MeasureService.one(av.measureId).getList('validations').then(function (validations) {
					            angular.forEach(validations, function(validation) {
									AssessmentVariableManager.setValidations(item.measureValidations, validation);
					            });
				            });

			            } else if (av.measureTypeId === 2 || av.measureTypeId === 3) {

				            // Get the answer list for multi or single select questions
				            MeasureService.one(av.measureId).getList('answers').then(function (answers) {
					            item.measureAnswers = answers;
				            });

				            // Ensure right variable is numeric
				            if (item.right) item.right.content = parseInt(item.right.content);
			            }

		            }

	            };

	            if(scope.block) {
		            scope.updateLogicalOptions(scope.block);
	            }
            }
        };

    }]);
})(angular);
