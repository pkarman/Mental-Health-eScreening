/**
 * Created by pouncilt on 8/6/14.
 */
Editors.controller('ModulesDetailInstructionsController', ['$rootScope', '$scope', '$state', function($rootScope, $scope, $state){
    $scope.htmlVariable = '<b>I am</b> an example of an <b><i>Instruction!!!</i></b>';
    $scope.htmlcontent = $scope.htmlVariable;


    $scope.blur = function(){
        $scope.isDirty = true;
    };

    $scope.save = function(){
        $scope.parentSave();
    };

    $scope.cancel = function(){
        $state.go('^');
    };
}]);
