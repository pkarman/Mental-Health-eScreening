/**
 * Created by munnoo on 12/29/14.
 */
angular.module('EscreeningDashboardApp.services.manageformulas', ['restangular'])
    .factory('FormulasService', ['$log', 'Restangular', '$cacheFactory', function ($log, Restangular, $cacheFactory) {
        var formulasCache;
        var restAngular = Restangular.withConfig(function (config) {
            formulasCache = $cacheFactory('http');
            config.setDefaultHttpFields({cache: formulasCache});
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        restAngular.setResponseInterceptor(function (response, operation) {
            if (operation === 'post' || operation === 'delete') {
                formulasCache.removeAll();
            }
            return response;
        })

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
            verifyCurrentFormula: function () {
                $log.debug('verifying current formula ' + currentFormula.template);
                return restAngular.one('formulas/verify').customPUT({f2v: currentFormula.template});
            },
            testCurrentFormula: function () {
                $log.debug('testing current formula verifiedIds' + currentFormula.verifiedIds);
                return restAngular.one('formulas/test').customPUT({
                    template: currentFormula.template,
                    f2t: currentFormula.verifiedIds
                });
            },
            persistCurrentFormula: function () {
                $log.debug('persisting current formula ' + currentFormula);

                return formulasProxy.post(_.omit(currentFormula, 'verifiedIds'));
            },
            updateCurrentFormula: function (result) {
                $log.debug('updating current formula with recently saved formula =>CurrentFormula[' + currentFormula + '] RecentlySavedFormula[' + result + ']');
                currentFormula.avId = parseInt(result.data, 10);
            },

            setCurrentModule: function (module) {
                currentModule = module;
            },

            fetchCurrentModule: function () {
                return currentModule;
            },

            setCurrentFormula: function (formula) {
                currentFormula = formula;
            },

            attachWithCurrentFormula: function (verifiedIds) {
                currentFormula.verifiedIds = verifiedIds;
            },

            fetchCurrentFormula: function () {
                return currentFormula;
            },

            setCurrentFormulas: function (formulas) {
                currentFormulas = formulas;
            },

            fetchCurrentFormulas: function () {
                return currentFormulas;
            },

            processSuccessfulSave: function (isNew) {
                if (isNew) {
                    currentFormulas.unshift(currentFormula);
                }
            }
        };
        return service;
    }])
;
