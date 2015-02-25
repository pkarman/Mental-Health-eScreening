/**
 * Created by Khalid Rizvi @ 01/10/2015
 */
Editors.controller('ModuleFormulasListController', ['$state', '$filter', '$log', '$scope', '$stateParams', 'FormulasService', 'ngTableParams', function ($state, $filter, $log, $scope, $stateParams, FormulasService, ngTableParams) {

    var formulas = [];
    $scope.module = {};
    $data = [];

    FormulasService.getModuleById($stateParams.moduleId)
        .then(function (module) {
            FormulasService.setCurrentModule(module);
            $scope.module = module;

            FormulasService.loadCurrentFormulas()
                .then(function (formulas) {

                    FormulasService.setCurrentFormulas(formulas);
                    formulas = FormulasService.fetchCurrentFormulas();
                    $log.debug($scope.module.name + ' has [' + formulas.length + '] formulas: ' + JSON.stringify(_.pluck(formulas, 'name')));

                    $scope.tableParams = new ngTableParams({
                        page: 1,            // show first page
                        count: 10           // count per page
                        //filter: {
                        //    name: '' // initial filter
                        //},
                        //sorting: {
                        //    name: 'asc'
                        //}
                    }, {
                        total: formulas.length, // length of data
                        getData: function ($defer, params) {
                            //params.total(formulas.length);
                            //var filteredData = params.filter() ?
                            //    $filter('filter')(formulas, params.filter()) : formulas;
                            //var orderedData = params.sorting() ?
                            //    $filter('orderBy')(filteredData, params.orderBy()) : formulas;
                            $defer.resolve(formulas.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        }
                    });

                }, function error(reason) {
                    $log.error(reason);
                });
        });

    $scope.edit = function (formula) {
        $log.debug(JSON.stringify(formula) + ' is being edited');
        FormulasService.setCurrentFormula(formula);
        $state.go('modules.formulasEdit');
    };
    $scope.create = function () {
        $log.debug('Request for new formula to be created');
        FormulasService.setCurrentFormula({});
        $state.go('modules.formulasEdit');
    };
}]);