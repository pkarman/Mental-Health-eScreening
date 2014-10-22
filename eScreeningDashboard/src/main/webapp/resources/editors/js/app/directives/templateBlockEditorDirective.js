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
            switch(type) {
                case 'if':
                    return blockTypes.slice(1);
                    break;
                case 'elseif':
                    return blockTypes.slice(2);
                    break;
                case 'else':
                    return blockTypes.slice(3);
                    break;
                default:
                    return blockTypes;
            }
        }

        return {
            restrict: 'E',
            scope: {
                block: '=',
                parentBlock: '='
            },
            templateUrl: 'resources/editors/views/templates/templateblockeditor.html',
            link: function(scope, element) {

                var collectionTemplate = '<template-block-editor block="member" parent-block="block" ng-repeat="member in block.children"></template-block-editor>';

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

                scope.blockTypes = getBlockTypes(scope.block.type);

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
	                { name: 'Response isn\t',  value: 'nresponse', category: 'select' }
                ];

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push({});
                };

            }
        };

    }]);
})(angular);
