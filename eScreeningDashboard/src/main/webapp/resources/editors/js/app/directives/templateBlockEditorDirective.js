(function(angular) {
    "use strict";

    Editors.directive('templateBlockEditor', ['$compile', function($compile) {

        // TODO Move to service or domain object to be shared and encapsulated elsewhere?
	    var blockTypes = [
		    { name: 'If', value: 'if' },
		    { name: 'Else If', value: 'elseif' },
		    { name: 'Else', value: 'else' },
		    { name: 'Text', value: 'text' }
	    ];

        function getBlockTypes(type) {
	        return (type) ? blockTypes.slice(3) : blockTypes;
        }

        return {
            restrict: 'E',
            scope: {
                block: '=',
                parentBlock: '=',
                assessmentVariables: '='
            },
            templateUrl: 'resources/editors/views/templates/templateblockeditor.html',
            link: function(scope, element) {

                var collectionTemplate = '<template-block-editor block="member" parent-block="block" ng-repeat="member in block.children | limitTo:2" assessment-variables="assessmentVariables"></template-block-editor>';

                /*
                 The compile function cannot handle directives that recursively use themselves
                 in their own templates or compile functions. Compiling these directives results
                 in an infinite loop and a stack overflow errors. This can be avoided by manually
                 using $compile in the postLink function to imperatively compile a directive's template
                 */
                $compile(collectionTemplate)(scope, function (clonedTemplate, scope) {
                    // Append the template and pass in the cloned scope
                    element.append(clonedTemplate);
                });

                scope.blockTypes = (scope.block) ? getBlockTypes(scope.block.type) : blockTypes;

                scope.$watch('parentBlock.type', function(type) {
                    scope.blockTypes = getBlockTypes(type);
                });

                // TODO Move to service to be shared elsewhere?
                scope.operators = [
	                { name: 'Equals',                    value: 'eq',    category: 'nonselect' },
	                { name: 'Doesn\'t Equals',           value: 'neq',   category: 'nonselect' },
	                { name: 'Is Less Than',              value: 'lt',    category: 'nonselect' },
	                { name: 'Is Greater Than',           value: 'gt',    category: 'nonselect' },
	                { name: 'Is Less Than or Equals',    value: 'lte',   category: 'nonselect' },
	                { name: 'Is Greater Than or Equals', value: 'gte',   category: 'nonselect' },

	                { name: 'Was Answered',     value: 'answered',   category: 'question' },
	                { name: 'Wasn\'t Answered', value: 'nanswered', category: 'question' },

	                { name: 'Has Result',      value: 'result',   category: 'formula' },
	                { name: 'Has No Result',   value: 'nresult', category: 'formula' },

	                { name: 'Response is',     value: 'response',  category: 'select' },
	                { name: 'Response isn\'t',  value: 'nresponse', category: 'select' }
                ];

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push(new EScreeningDashboardApp.models.TemplateBlock({left:{content: {}}}));
                };

                scope.addAndConditionBlock = function(selectedBlock) {
                    var andConditionFactoryConfig = {
                        connector: "and",
                        left: {
                            type: "var",
                            content: {

                            }
                        },
                        conditions: []
                    };

                    selectedBlock.conditions = selectedBlock.conditions || [];
                    selectedBlock.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(andConditionFactoryConfig));
                };

                scope.addOrConditionBlock = function(selectedBlock) {
                    var orConditionFactoryConfig = {
                        connector: "or",
                        left: {
                            type: "var",
                            content: {

                            }
                        },
                        conditions: []
                    };
                    selectedBlock.conditions = selectedBlock.conditions || [];
                    selectedBlock.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(orConditionFactoryConfig));
                };

            }
        };

    }]);
})(angular);
