(function(angular) {
    "use strict";

    Editors.directive('templateBlockConditionEditor', ['$compile', function($compile) {
        return {
            restrict: 'E',
            scope: {
                condition: '=',
                parentBlock: '=',
                assessmentVariables: '='
            },
            templateUrl: 'resources/editors/js/app/directives/templateBlockConditionEditorDirective/templateBlockConditionEditor.html',
            link: function(scope, element) {

                var collectionTemplate = '<template-block-condition-editor condition="condition" parent-block="block" ng-repeat="condition in parentBlock.conditions" assessment-variables="assessmentVariables"></template-block-condition-editor>';

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

                scope.addAndConditionBlock = function(selectedCondition) {
                    var andConditionFactoryConfig = {
                        connector: "and",
                        left: {
                            type: "var",
                            content: {

                            }
                        }
                    };

                    if(selectedCondition.children) {
                        selectedCondition.children.push(new EScreeningDashboardApp.models.TemplateCondition(andConditionFactoryConfig));
                    }
                };

                scope.addOrConditionBlock = function(selectedCondition) {
                    var orConditionFactoryConfig = {
                        connector: "or",
                        left: {
                            type: "var",
                            content: {

                            }
                        }
                    };
                    if(selectedCondition.children) {
                        selectedCondition.children.push(new EScreeningDashboardApp.models.TemplateCondition(orConditionFactoryConfig));
                    }
                };

            }
        };

    }]);
})(angular);
