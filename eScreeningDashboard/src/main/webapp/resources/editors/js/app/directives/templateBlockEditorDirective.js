(function(angular) {
    "use strict";

    Editors.directive('templateBlockEditor', ['$compile', function($compile) {

        return {
            restrict: 'E',
            scope: {
                block: '='
            },
            templateUrl: 'resources/editors/views/templates/templateblockeditor.html',
            link: function(scope, element) {

                var collectionTemplate = '<template-block-editor block="block" ng-repeat="block in block.children"></template-block-editor>';

                /*
                 The compile function cannot handle directives that recursively use themselves
                 in their own templates or compile functions. Compiling these directives results
                 in an infinite loop and a stack overflow errors. This can be avoided by manually
                 using $compile in the postLink function to imperatively compile a directive's template
                 */
                $compile(collectionTemplate)(scope, function (clonedTemplate, scope) {
                    console.log('element', element);
                    // Append the template and pass in the cloned scope
                    element.append(clonedTemplate);
                });

                // TODO Move to service to be shared elsewhere?
                scope.blockTypes = [
                    { name: 'If', value: 'If', selected: false },
                    { name: 'Else If', value: 'Else If', selected: false },
                    { name: 'Else', value: 'Else', selected: false },
                    { name: 'Text', value: 'Text', selected: false }
                ];

                // TODO Move to service to be shared elsewhere?
                scope.operators = [
                    { name: 'Equals', value: 'eq', category: 'all' },
                    { name: 'Doesn\'t Equals', value: 'neq', category: 'all' },
                    { name: 'Is Less Than', value: 'lt', category: 'all' },
                    { name: 'Is Greater Than', value: 'gt', category: 'all' },
                    { name: 'Is Less Than or Equals', value: 'lte', category: 'all' },
                    { name: 'Is Greater Than or Equals', value: 'gte', category: 'all' },
                    { name: 'Was Answered', value: 'answered', category: 'question' },
                    { name: 'Wasn\'t Answered', value: 'nanswered', category: 'question' },
                    { name: 'Has Result', value: 'result', category: 'formula' },
                    { name: 'Has No Result', value: 'nresult', category: 'formula' },
                    { name: 'Response is', value: 'response', category: 'select' },
                    { name: 'Response isn\t', value: 'nresponse', category: 'select' }
                ];

                scope.addBlock = function(selectedBlock) {
                    selectedBlock.children = selectedBlock.children || [];
                    selectedBlock.children.push({});
                };

            }
        };

    }]);
})(angular);