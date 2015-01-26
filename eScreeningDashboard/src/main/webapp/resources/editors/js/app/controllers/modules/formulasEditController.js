/**
 * Created by Khalid Rizvi @ 01/24/2015
 */
Editors.controller('ModuleFormulasEditController', ['$log', '$scope', '$stateParams', 'FormulasService', function ($log, $scope, $stateParams, FormulasService) {
    $scope.formula = FormulasService.fetchCurrentFormula();
    $log.debug($scope);
}]);