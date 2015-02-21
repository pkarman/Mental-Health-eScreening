/**
 * Created by munnoo on 12/29/14.
 */
angular.module('EscreeningDashboardApp.services.manageformulas', ['restangular'])
    .factory('FormulasService', ['$log', 'Restangular', '$cacheFactory', function ($log, Restangular, $cacheFactory) {
        //var formulasCache;
        var restAngular = Restangular.withConfig(function (config) {
            //formulasCache = $cacheFactory('http');
            config.setDefaultHttpFields({cache: false});
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        //restAngular.setResponseInterceptor(function (response, operation) {
        //    if (operation === 'post' || operation === 'delete') {
        //        formulasCache.removeAll();
        //    }
        //    return response;
        //})

        restAngular.addResponseInterceptor(function (data, operation, what, url, response, deferred) {
            return data;
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
            loadVarsByModuleId: function () {
                $log.debug('getting all variable promise for module with an id of' + currentModule.id);
                //formulasCache.removeAll()

                return restAngular.all('avs2MngFormulas').getList({moduleId: currentModule.id});
            },
            hasValidModule: function () {
                return currentModule != undefined && currentModule.id != undefined;
            },
            getModuleById: function (moduleId) {
                $log.debug('getting module details promise for module with an id of ' + moduleId);
                return restAngular.one('formulas/modules', moduleId).get();
            },
            verifySelectedTokens: function (tokens) {
                $log.debug('verifying current tokens ' + tokens);
                return restAngular.one('formulas/verifySelectedTokens').customPUT(tokens);
            },
            testSelectedTokens: function (tokens) {
                $log.debug('testing selected tokens using verifiedIds' + currentFormula.verifiedIds);
                return restAngular.one('formulas/testSelectedTokens').customPUT({
                    tokens: tokens,
                    verifiedIds: currentFormula.verifiedIds
                });
            },
            persistSelectedTokens: function (tokens) {
                $log.debug('persisting selected Tokens ' + tokens);

                return formulasProxy.post({
                    tokens: tokens,
                    av: _.omit(currentFormula, ['verifiedIds', 'selectedTokens'])
                });
            },
            updateCurrentFormula: function (result) {
                $log.debug('updating current formula with recently saved formula =>CurrentFormula[' + currentFormula + '] RecentlySavedFormula[' + result + ']');
                currentFormula.id = parseInt(result.data, 10);
            },

            setCurrentModule: function (module) {
                currentModule = module;
            },

            fetchCurrentModule: function () {
                return currentModule;
            },

            setCurrentFormula: function (formula) {
                currentFormula = formula;
                if (currentFormula.id === undefined) {
                    currentFormula.selectedTokens = [];
                }
            },

            attachWithCurrentFormula: function (verifiedIds) {
                currentFormula.verifiedIds = verifiedIds;
            },

            loadCurrentFormula: function () {
                // now retrieve the details of this formula, that will include the selectedTokens
                $log.debug('getting details of current formula for ' + currentFormula.id);
                return restAngular.one('formulas', currentFormula.id).get().then(function (formula) {
                    currentFormula = formula;
                }, function error(reason) {
                    $log.error(reason);
                })
            },
            isNewFormula: function () {
                return currentFormula.id === undefined;
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
            },

            guid: function (prefix) {
                return _.uniqueId('udk[' + prefix + '][') + ']';
            }
        };
        return service;
    }])
;
