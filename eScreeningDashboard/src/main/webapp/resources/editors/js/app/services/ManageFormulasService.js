/**
 * Created by munnoo on 12/29/14.
 */
angular.module('EscreeningDashboardApp.services.manageformulas', ['restangular'])
    .factory('FormulasService', ['$log', 'Restangular', function ($log, Restangular) {

        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setDefaultHttpFields({cache: true});
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var formulasProxy = restAngular.all('formulas');

        var currentFormula = {};
        var currentFormulas = [];
        var currentModule = {};

        // service to perform CRUD
        var service = {
            loadCurrentFormulas: function () {
                $log.debug('getting formulas promise for module with an id of' + currentModule.id);
                return formulasProxy.getList({moduleId: currentModule.id});
            },

            getModuleById: function (moduleId) {
                $log.debug('getting module details promise for module with an id of ' + moduleId);
                return restAngular.one('formulas/modules', moduleId).get();
            },

            saveCurrentModule: function (module) {
                currentModule = module;
            },

            fetchCurrentModule: function () {
                return currentModule;
            },

            saveCurrentFormula: function (formula) {
                currentFormula = formula;
            },

            fetchCurrentFormula: function () {
                return currentFormula;
            },

            saveCurrentFormulas: function (formulas) {
                currentFormulas = formulas;
            },

            fetchCurrentFormulas: function () {
                return currentFormulas;
            }
        };
        return service;
    }]);
