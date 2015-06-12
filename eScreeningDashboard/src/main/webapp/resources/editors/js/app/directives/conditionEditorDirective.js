/**
 * This is a recursive directive which allows for a condition to have subconditions editable by this directive.
 * @author Robin Carnow
 */
(function(angular) {
    "use strict";

    Editors.directive('conditionEditor', ['MeasureService', 'RecursionHelper', 'AssessmentVariableManager', 
                                          function(MeasureService, RecursionHelper, AssessmentVariableManager) {
    	var operators = [
            { name: 'Equals',                    value: 'eq',    categories: ['numerical', 'equality'] },
            { name: 'Doesn\'t Equals',           value: 'neq',   categories: ['numerical', 'equality'] },
            { name: 'Is Less Than',              value: 'lt',    categories: ['numerical'] },
            { name: 'Is Greater Than',           value: 'gt',    categories: ['numerical'] },
            { name: 'Is Less Than or Equals',    value: 'lte',   categories: ['numerical'] },
            { name: 'Is Greater Than or Equals', value: 'gte',   categories: ['numerical'] },

            { name: 'Was Answered',     value: 'answered',   categories: ['question', 'table'] },
            { name: 'Wasn\'t Answered', value: 'nanswered', categories: ['question', 'table'] },

			{ name: 'Was Answer None',     value: 'none',   categories: ['table'] },
			{ name: 'Wasn\'t Answer None', value: 'nnone', categories: ['table'] },

            { name: 'Has Result',      value: 'result',   categories: ['formula', 'matrix', 'custom','result'] },
            { name: 'Has No Result',   value: 'nresult', categories: ['formula', 'matrix', 'custom','result'] },

            { name: 'Response is',     value: 'response',  categories: ['select'] },
            { name: 'Response isn\'t',  value: 'nresponse', categories: ['select'] }
        ];
    	
    	//This is where the business rules for what operators are available after a variable and transformation are picked
    	var filterOperators = function(operator) {
    		var variable = this;
        	var hasTransformation = variable.transformations && variable.transformations.length > 0 && variable.transformations[0].name !== "none";

        	if(hasTransformation){
        		return filterOperatorByTransformation(operator, variable.transformations[variable.transformations.length -1]);
        	}
        	return filterOperatorByQuestion(operator, variable);
        }
    	
    	function filterOperatorByTransformation(operator, transformation){
    		if (operator){
    			
	    		if(_.contains(operator.categories, 'numerical') 
	    				&& (transformation.name === 'numberOfEntries'
	    					|| transformation.name === 'yearsFromDate')){
	    			return true;
	    		}

	    		if(transformation.name.indexOf("delimit") == 0
	    				&& (_.contains(operator.categories, 'equality') 
	    					|| _.contains(operator.categories, 'result'))){
	    			return true;
	    		}
	    		
    		}    		
    		return false;
    	}
    	
    	function filterOperatorByQuestion(operator, variable){
    		var includeOperator = false;
    		
    		if (operator && variable.type) {
        		if(_.contains(operator.categories, 'numerical')) {
        			if(variable.type.toUpperCase() === 'CUSTOM'){
        				includeOperator = _.contains(operator.categories, 'equality');
        			} else if (variable.type.toUpperCase() === 'FORMULA') {
        				includeOperator = true;
        			} else if (variable.type.toUpperCase() === "QUESTION" 
        					&& variable.measureTypeId === 1) {
        				includeOperator = true;
        			}
        		} else if(variable.type.toUpperCase() === 'QUESTION' 
        				&& variable.getMeasureTypeName() !== 'single-matrix' 
        				&& variable.getMeasureTypeName() !== 'multi-matrix' 
        				&& _.contains(operator.categories, 'question')) {
        			includeOperator = true;
        		} else if((variable.type.toUpperCase() === 'FORMULA') 
        				&& _.contains(operator.categories, 'formula')) {
        			includeOperator = true;
        		} else if((variable.type.toUpperCase() === 'QUESTION') 
        				&& _.contains(operator.categories, 'select')) {
        			if(Object.isDefined(variable.measureTypeId) 
        					&& (variable.measureTypeId === 2 
        					 || variable.measureTypeId === 3)){
        				includeOperator = true;
        			}
        		}
        		else if(variable.measureTypeId === 4 
        				&& _.contains(operator.categories, 'table')) {
        			includeOperator = true;
        		}
        		else if((variable.getMeasureTypeName() === 'single-matrix' || variable.getMeasureTypeName() === 'multi-matrix') && _.contains(operator.categories, 'matrix')) {
        			includeOperator = true;
        		} else if (variable.type.toUpperCase() === 'CUSTOM' && _.contains(operator.categories, 'custom')) {
        			includeOperator = true;
        		}
        	}
        	return includeOperator;
    	}
    	
    	return {
            restrict: 'E',
            require: '^form',
            scope: {
                condition: '=',
                parentCondition: '=',
                assessmentVariables: '='
            },
            templateUrl: 'resources/editors/views/templates/conditioneditor.html',
            compile: function(element) {
                // Use the compile function from the RecursionHelper,
                // And return the linking function(s) which it returns
                return RecursionHelper.compile(element, function(scope, elementm, attrs, formController) {

	            	scope.conditionForm = formController;
	            	scope.operators = operators;
	            	
	            	scope.addAndCondition = function addAndCondition(item, form) {
		                if (form.$valid) {
							item.conditions = item.conditions || [];
							item.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig));
		                }
	        		};
	
	        		scope.addOrCondition = function addOrCondition(item, form) {
		                if (form.$valid) {
							item.conditions = item.conditions || [];
							item.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(EScreeningDashboardApp.models.TemplateCondition.OrConditionMinimumConfig));
		                }
	        		};
	        		
	        		scope.removeCondition = function(selectedBlockConditions, selectedCondition) {
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
		            
		            if(scope.condition) {
		                scope.updateLogicalOptions(scope.condition);
		            }
	
		        });
            }
        };
    }]);
})(angular);
